package com.space.munova.product.infra.mongo.query;


import com.space.munova.product.infra.mongo.ProductMongoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ProductMongoQueryRepo extends MongoRepository<ProductMongoDocument, Long>, ProductMongoQueryRepoCustom {

    ///  통계필드 제외 조회
    @Query(value = "{ _id: ?0 }",
            fields = "{ _id: 0,"
                    + " productId: 1,"
                    + " categoryId: 1,"
                    + " brandName: 1,"
                    + " name: 1,"
                    + " info: 1,"
                    + " price: 1,"
                    + " mainImage: 1,"
                    + " sideImages: 1,"
                    + " productDetails: 1,"
                    + " optionNames: 1"
                    + " }")
    Optional<ProductMongoDocument> findDetailProjectedById(Long productId);
}
