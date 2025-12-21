    package com.space.munova.product.infra.batch;

    import com.space.munova.product.application.product.command.port.OutboxCommandPort;
    import com.space.munova.product.application.product.query.port.ProductMongoSyncPort;
    import com.space.munova.product.domain.Repository.ProductRepository;
    import com.space.munova.product.infra.batch.dto.ProductStatsSyncDto;
    import com.space.munova.product.infra.elasticsearch.ProductEsDocument;
    import com.space.munova.product.infra.elasticsearch.query.ProductEsSyncRepo;
    import com.space.munova.product.infra.mongo.ProductMongoDocument;
    import com.space.munova.product.infra.mongo.query.ProductMongoSyncRepo;
    import com.space.munova.product.infra.mysql.adepter.OutboxCommandAdapter;
    import jakarta.annotation.PostConstruct;
    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.batch.item.Chunk;
    import org.springframework.batch.item.ItemWriter;
    import org.springframework.stereotype.Component;
    import org.springframework.transaction.annotation.Transactional;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.concurrent.*;


    @Component
    @RequiredArgsConstructor
    @Slf4j
    public class ProductStatsWriter implements ItemWriter<ProductStatsSyncDto> {

        private final ProductRepository productRepository;
        private final ProductMongoSyncRepo productMongoSyncRepo;
        private final ProductEsSyncRepo productEsSyncRepo;
        private final OutboxCommandPort outboxCommandPort;



        @Override
        @Transactional(rollbackFor = Exception.class) /// 예외 발생시 mysql 롤백
        public void write(Chunk<? extends ProductStatsSyncDto> chunk) throws Exception {

            List<ProductStatsSyncDto> items = (List<ProductStatsSyncDto>) chunk.getItems();

            /// 롤백용 아이템 리스트
            List<ProductMongoDocument> befMongoDocs = new ArrayList<>();
            List<ProductEsDocument> befEsDocs = new ArrayList<>();

        
            try{

                for (ProductStatsSyncDto dto : items) {

                    ProductMongoDocument mongoDocBefore = null;
                    ProductEsDocument esDocBefore = null;


                    updateMysql(dto);

                    mongoDocBefore = productMongoSyncRepo.findById(dto.getProductId()).orElse(null);
                    updateMongoDoc(dto);

                    esDocBefore = productEsSyncRepo.findById(dto.getProductId()).orElse(null);
                    updateEsDoc(dto);

                    /// 혹시모를 예외(롤백)시 데이터 정합성을 위해 이전 데이터를 저장.
                    befMongoDocs.add(mongoDocBefore);
                    befEsDocs.add(esDocBefore);

                }
                
            } catch (Exception e) {
                ///  예외 발생시  아웃박스 패턴으로 기존 몽고, 엘라로 싱크 세이브 메시지를 저장하여
                /// 기존 문서 다시저장.
                /// 예외가 터질경우 이전 문서 정보를 담은 리스트에 담긴 데이터를 아웃박스에 넣어줌. (롤백용)

                /// 해당 작업은 비동기로 불가 -> 아웃박스 저장은 하나의 트랜잭션에 묶여야하기 때문에
                rollBackDocs(befMongoDocs, befEsDocs);


                throw new RuntimeException(String.format("상품 통계 동기화 실패: Chunk 크기=%d, 롤백 대상 문서 수(Mongo=%d, ES=%d)",
                                items.size(), befMongoDocs.size(), befEsDocs.size()), e);

            } finally {

                /// 메모리 누수를 방지하기 위해 명시적으로 clear() 해줌.
                befEsDocs.clear();
                befMongoDocs.clear();
            }
            
            
        }

        private void rollBackDocs(List<ProductMongoDocument> befMongoDocs, List<ProductEsDocument> befEsDocs) {
            rollBackMongoDocs(befMongoDocs);
            rollBackEsDocs(befEsDocs);
        }

        /// 몽고 문서 롤백을 위해 아웃박스에 저장.
        private void rollBackMongoDocs(List<ProductMongoDocument> befMongoDocs) {

            if(befMongoDocs.isEmpty()) return;

            for (ProductMongoDocument doc : befMongoDocs) {
                outboxCommandPort.syncSaveMongoEvent(doc);
            }
        }

        /// 엘랏틱 문서 롤백을 위해 아웃박스에 저장.
        private void rollBackEsDocs(List<ProductEsDocument> befEsDocs) {

            if(befEsDocs.isEmpty()) return;

            for (ProductEsDocument doc : befEsDocs) {
                outboxCommandPort.syncSaveEsEvent(doc);
            }
        }



        private void updateMysql(ProductStatsSyncDto dto) {
            productRepository.updateProductStats(
                    dto.getProductId(),
                    dto.getLikeCount(),
                    dto.getViewCount(),
                    dto.getSalesCount()
            );
        }

        private void updateMongoDoc(ProductStatsSyncDto dto) {
            ProductMongoDocument doc = productMongoSyncRepo.findById(dto.getProductId())
                    .orElse(null);

            if (doc != null) {
                doc.updateStats(dto.getLikeCount(), dto.getViewCount(), dto.getSalesCount());
                productMongoSyncRepo.save(doc);
            }
        }

        private void updateEsDoc(ProductStatsSyncDto dto) {
            ProductEsDocument doc = productEsSyncRepo.findById(dto.getProductId())
                    .orElse(null);

            if (doc != null) {
                doc.updateStats(dto.getLikeCount(), dto.getViewCount(), dto.getSalesCount());
                productEsSyncRepo.save(doc);
            }
        }
    }
