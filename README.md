# 🛒 MUNOVA - 이커머스 온라인 신발 쇼핑몰

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)
![Elasticsearch](https://img.shields.io/badge/Elasticsearch-005571?style=for-the-badge&logo=elasticsearch&logoColor=white)

### Domain-Driven Design 기반 고성능 이커머스 플랫폼

[📽️ 시연 영상](https://www.youtube.com/watch?v=NCUD25v__2g)

</div>

---

## 📌 프로젝트 개요

**MUNOVA**는 DDD(Domain-Driven Design) 아키텍처를 기반으로 구축된 신발 전문 이커머스 플랫폼입니다.  
**상품 도메인**을 전담하여 클린 아키텍처 설계, 성능 최적화, 데이터 일관성 보장을 주도했습니다.

### 🎯 주요 성과

- **클린 아키텍처 도입**: Port & Adapter 패턴으로 비즈니스 로직과 인프라 계층 완전 분리
- **데이터 일관성 보장**: Transactional Outbox 패턴으로 분산 시스템 간 데이터 동기화 안정성 확보
- **동시성 제어**: Redis Lua Script 기반 원자적 연산으로 좋아요/조회수 동시성 문제 해결
- **배치 최적화**: Spring Batch를 활용한 대용량 통계 데이터 동기화 (5,000건/chunk)



## 🏗️ 담당 도메인: 상품(Product)

### 핵심 기능 구현

#### 🔍 상품 조회 (CQRS Read Model)
- **전체 상품 조회**
  - 카테고리, 브랜드, 가격 범위 등 다양한 필터링
  - 인기순, 최신순, 가격순 정렬 지원
  - Cursor 기반 페이지네이션으로 대용량 데이터 처리
- **상품 상세 조회**
  - 실시간 재고, 좋아요 수, 조회수 Redis 캐싱
  - MongoDB 기반 상품 이미지 조회
  - Elasticsearch 연관 상품 추천

#### 👤 사용자 기능 (Command Model)
- **좋아요 관리**
  - Redis Lua Script 기반 원자적 연산으로 동시성 제어
  - MySQL 트랜잭션으로 좋아요 데이터 저장
  - Redis에 실시간 통계 업데이트 (likeCount)
  - Spring Batch로 주기적 동기화하여 데이터 일관성 보장
- **장바구니 관리**
  - 실시간 재고 검증 (ProductDetail 재고 확인)
  - 장바구니 아이템 추가/삭제/수정
  - 품절 상품 자동 필터링

#### 🛠️ 판매자 기능
- **상품 등록**
  - S3 기반 다중 이미지 업로드
  - 사이즈별 재고 설정 (ProductDetail)
  - MySQL → MongoDB → Elasticsearch 동기화
- **상품 수정**
  - 가격, 재고, 상품 정보 수정
  - Outbox 패턴으로 분산 데이터베이스 동기화
- **상품 삭제**
  - Soft Delete 방식으로 데이터 보존
  - 연관 데이터(좋아요, 장바구니) 이벤트 기반 정리

---

## 🎨 기술적 도전과 해결

### 1️⃣ **Port & Adapter 패턴으로 클린 아키텍처 구현**

#### 문제 상황
- 비즈니스 로직이 JPA, Redis, Elasticsearch 등 인프라 기술에 직접 의존
- 기술 스택 변경 시 도메인 로직까지 수정 필요
- 단위 테스트 작성이 어렵고 Mock 객체 설정 복잡

#### 해결 방법
```java
// Port 정의 (application layer)
public interface ProductRedisCommandPort {
    Long updateViewCount(Long productId, int input);
}

// Adapter 구현 (infrastructure layer)
@Repository
public class ProductRedisCommandAdapter implements ProductRedisCommandPort {
    // Redis 구현 세부사항
}
```

#### 성과
- 도메인 로직과 인프라 완전 분리로 테스트 커버리지 향상
- 기술 스택 교체 시 Adapter만 수정하면 되는 유연한 구조

---

### 2️⃣ **Redis Lua Script로 동시성 제어**

#### 문제 상황
- 좋아요/조회수 동시 요청 시 Race Condition 발생
- Redis GET → 연산 → SET 방식은 원자성 보장 안 됨
- 분산 Lock은 성능 오버헤드가 큼

#### 해결 방법
```lua
-- Redis Lua Script (원자적 실행 보장)
local statsKey = KEYS[1];
local field = ARGV[1];
local input = tonumber(ARGV[2]) or 0;

if redis.call('EXISTS', statsKey) == 0 then
    redis.call('HSET', statsKey, 'likeCount', 0, 'viewCount', 0, 'salesCount', 0);
end;

local current = tonumber(redis.call('HGET', statsKey, field)) or 0;
local newValue = current + input;

if newValue < 0 then
    return -1
end;

redis.call('HSET', statsKey, field, newValue);
return newValue
```

#### 성과
- 단일 원자적 연산으로 Race Condition 완벽 해결
- 분산 Lock 대비 높은 성능 유지

---

### 3️⃣ **Transactional Outbox 패턴으로 데이터 일관성 보장**

#### 문제 상황
- MySQL 트랜잭션 커밋 후 이벤트 발행 실패 시 데이터 불일치
- 상품 등록 → MongoDB/Elasticsearch 동기화 실패 시 복구 불가
- 이벤트 발행 실패 시 데이터 유실

#### 해결 방법

**Outbox 테이블 설계**
```java
@Entity
public class ProductOutbox {
    private Long id;
    private EventType eventType;      // PRODUCT_LIKE, SAVE_PRODUCT 등
    private String eventValue;        // JSON 직렬화된 이벤트 데이터
    private OutboxStatus status;      // PENDING, PUBLISHED, FAILED
}
```

**스케줄러 기반 이벤트 발행**
```java
@Scheduled(fixedDelay = 1000)  // 1초마다 실행
public void publishPendingEvents() {
    List<ProductOutbox> pendingEvents = 
        productOutboxRepository.findTop1000ByStatusOrderByCreatedAtAsc(PENDING);
    
    for (ProductOutbox outbox : pendingEvents) {
        try {
            eventPublisher.publishEvent(deserialize(outbox));
            outbox.changePublishStatus();
        } catch (Exception e) {
            outbox.changeFailedStatus();
        }
    }
}
```

#### 성과
- MySQL 트랜잭션과 이벤트 발행을 원자적으로 처리
- 실패한 이벤트 자동 재시도로 데이터 일관성 보장
- At-Least-Once 전달 보장

---

### 4️⃣ **Spring Batch로 대용량 통계 데이터 동기화**

#### 문제 상황
- 조회수/좋아요 통계가 Redis에만 존재하여 영구 저장 필요
- 실시간 동기화는 DB 부하 증가
- 수백만 건의 상품 통계 동기화 필요

#### 해결 방법
```java
@Configuration
public class ProductStatsSyncBatchConfig {
    
    @Bean
    public Step syncProductStatsStep() {
        return new StepBuilder("syncProductStatsStep", jobRepository)
            .<Long, ProductStatsSyncDto>chunk(5000, transactionManager)
            .reader(productStatsReader)      // Redis에서 통계 읽기
            .processor(productStatsProcessor) // 변환 로직
            .writer(productStatsWriter)       // MySQL/MongoDB/ES 동기화
            .build();
    }
}
```

**1시간마다 배치 실행**
- Redis → MySQL → MongoDB → Elasticsearch 순차 동기화
- Chunk 크기 5,000건으로 메모리 효율성 확보
- 실패 시 재시작 지점부터 재실행

#### 성과
- 실시간 통계 조회는 Redis 캐시 사용으로 빠른 응답 속도
- 배치 동기화로 영구 데이터 저장 및 분석 가능

---

### 5️⃣ **CQRS 패턴으로 읽기/쓰기 최적화**

#### 문제 상황
- 복잡한 필터링/정렬 쿼리가 쓰기 모델(JPA Entity)에 의존
- 조회 성능 최적화를 위해 도메인 모델이 오염됨
- N+1 문제 빈번히 발생

#### 해결 방법

**Command Service (쓰기 전용)**
```java
@Service
public class ProductCommandService {
    public Product saveProduct(AddProductRequestDto dto, Member seller, 
                               Brand brand, Category category) {
        Product product = Product.createDefaultProduct(...);
        return productRepository.save(product);
    }
}
```

**Query Service (읽기 전용)**
```java
@Service
@Transactional(readOnly = true)
public class ProductQueryService {
    public PagingResponse<FindProductResponseDto> findProductBySeller(
        Pageable pageable, Long sellerId) {
        // QueryDSL 최적화 쿼리
        return productRepository.findProductBySeller(pageable, sellerId);
    }
}
```

#### 성과
- 읽기 모델은 DTO 프로젝션으로 쿼리 최적화
- 쓰기 모델은 비즈니스 로직에 집중
- 서비스 레이어 책임 명확히 분리

---

## 🛠️ 기술 스택

### Backend Core
- **Framework**: Spring Boot 3.x
- **Language**: Java 17
- **ORM**: JPA (Hibernate), QueryDSL
- **Batch**: Spring Batch (통계 동기화)
- **Event**: Spring ApplicationEventPublisher

### Database & Cache
- **RDB**: MySQL 8.0 (트랜잭션 데이터, Outbox 테이블)
- **NoSQL**: MongoDB (상품 이미지, 대용량 비정형 데이터)
- **Cache**: Redis (좋아요/조회수 실시간 통계, Lua Script 동시성 제어)
- **Search**: Elasticsearch (한글 형태소 분석, 상품 검색/필터링)

### Architecture & Design Pattern
- **Clean Architecture**: Hexagonal Architecture (Port & Adapter)
- **DDD**: Aggregate Root, Domain Service, Domain Event
- **CQRS**: Command/Query 분리로 읽기/쓰기 최적화
- **Event-Driven**: Transactional Outbox 패턴
- **API**: RESTful API, Swagger UI

### Infrastructure
- **Storage**: AWS S3 (상품 이미지)
- **Scheduler**: Spring @Scheduled (Outbox 발행, 배치 실행)

---

## 📊 성능 최적화 전략

### 🚀 쿼리 최적화
- **N+1 문제 해결**: QueryDSL Fetch Join으로 연관 엔티티 일괄 조회
- **DTO 프로젝션**: Entity 대신 필요한 컬럼만 조회하여 메모리 사용량 감소
- **Covering Index**: 조회 쿼리에 필요한 모든 컬럼을 인덱스에 포함
- **커서 기반 페이징**: Offset 방식 대비 대용량 데이터 조회 성능 향상

### ⚡ 캐싱 전략
- **Look-Aside Cache**: 상품 상세 정보 Redis 캐싱 (TTL: 10분)
- **Write-Behind Cache**: 조회수/좋아요 Redis 저장 후 배치 동기화
  - 즉시 반영이 필요 없는 통계 데이터를 배치로 처리하여 DB 부하 감소
- **Cache Warming**: 인기 상품 사전 캐싱

### 🔄 비동기 처리 & 배치
- **이벤트 기반 아키텍처**: 
  - Domain Event 발행으로 비즈니스 로직 결합도 감소
  - Outbox 스케줄러로 At-Least-Once 전달 보장
- **Spring Batch 통계 동기화**:
  - 1시간마다 Redis → MySQL/MongoDB/ES 동기화
  - Chunk Size 5,000건으로 메모리 효율성 확보
  - 실패 시 재시작 지점부터 재실행

### 🎯 데이터베이스 분리
- **폴리글랏 퍼시스턴스**
  - MySQL: 트랜잭션 데이터 (상품, 주문, 재고)
  - MongoDB: 대용량 이미지 URL, 상품 설명
  - Redis: 실시간 통계, 세션
  - Elasticsearch: 전문 검색, 필터링

---

## 📂 프로젝트 구조 (Hexagonal Architecture)

```
product/
├── domain/                           # 도메인 계층 (핵심 비즈니스 로직)
│   ├── Product.java                 # 상품 Aggregate Root
│   ├── ProductDetail.java           # 사이즈별 재고 관리
│   ├── ProductLike.java             # 좋아요 Entity
│   ├── Cart.java                    # 장바구니 Entity
│   ├── enums/                       # 도메인 Enum
│   │   ├── EventType.java           # 이벤트 타입
│   │   ├── OutboxStatus.java        # Outbox 상태
│   │   └── ProductCategory.java     # 상품 카테고리
│   └── Repository/                  # Repository 인터페이스 (Port)
│       ├── ProductRepository.java
│       ├── ProductRepositoryCustom.java
│       └── ProductLikeRepository.java
│
├── application/                      # 애플리케이션 계층 (유스케이스)
│   ├── product/
│   │   ├── command/                 # 상품 Command (CUD)
│   │   │   ├── ProductCommandService.java
│   │   │   ├── ProductCommandFacadeService.java
│   │   │   ├── dto/                 # Command DTO
│   │   │   ├── event/               # 도메인 이벤트
│   │   │   │   ├── ProductLikeEventDto.java
│   │   │   │   ├── ProductUpdateEventDto.java
│   │   │   │   └── CartEventListener.java
│   │   │   └── port/                # Outbound Port 인터페이스
│   │   │       ├── ProductRedisCommandPort.java
│   │   │       ├── ProductEsCommandPort.java
│   │   │       └── OutboxCommandPort.java
│   │   └── query/                   # 상품 Query (R)
│   │       ├── ProductQueryService.java
│   │       ├── ProductQueryFacadeService.java
│   │       ├── dto/                 # Query DTO (읽기 최적화)
│   │       └── port/                # Query Port 인터페이스
│   ├── like/                        # 좋아요 도메인
│   │   ├── command/
│   │   │   └── ProductLikeCommandService.java
│   │   └── query/
│   │       └── ProductLikeQueryService.java
│   └── cart/                        # 장바구니 도메인
│       ├── command/
│       └── query/
│
├── infra/                           # 인프라 계층 (Adapter 구현체)
│   ├── mysql/                       # MySQL Adapter
│   │   ├── ProductOutbox.java       # Outbox 패턴 구현
│   │   ├── ProductOutboxRepository.java
│   │   └── adapter/
│   │       ├── OutboxCommandAdapter.java
│   │       └── ProductEsSyncFailedAdapter.java
│   ├── redis/                       # Redis Adapter
│   │   ├── command/
│   │   │   ├── ProductStatsRedisDataCommandRepo.java
│   │   │   └── ProductStatsRedisDataCommandRepoImpl.java  # Lua Script 구현
│   │   ├── query/
│   │   └── ProductRedisStatsData.java
│   ├── mongodb/                     # MongoDB Adapter
│   │   ├── ProductMongoDocument.java
│   │   ├── command/
│   │   │   └── ProductMongoCommandRepo.java
│   │   └── query/
│   ├── elasticsearch/               # Elasticsearch Adapter
│   │   ├── ProductEsDocument.java
│   │   ├── command/
│   │   │   └── ProductEsCommandRepoCustomImpl.java
│   │   └── query/
│   │       └── ProductEsQueryRepoCustomImpl.java
│   ├── batch/                       # Spring Batch 구현
│   │   ├── ProductStatsSyncBatchConfig.java  # 통계 동기화 배치
│   │   ├── ProductStatsReader.java           # Redis Reader
│   │   ├── ProductStatsProcessor.java        # 변환 로직
│   │   └── ProductStatsWriter.java           # 다중 DB Writer
│   └── scheduler/                   # 스케줄러
│       ├── OutboxPublishScheduler.java       # 1초마다 Outbox 발행
│       └── ProductStatsSyncBatchScheduler.java
│
└── ui/                              # 프레젠테이션 계층 (Inbound Adapter)
    ├── ProductController.java       # 상품 REST API
    ├── LikeController.java          # 좋아요 REST API
    └── CartController.java          # 장바구니 REST API
```

### 아키텍처 특징

**Hexagonal Architecture (Port & Adapter)**
- `domain`: 비즈니스 로직만 집중, 외부 기술 의존성 제로
- `application`: 유스케이스 구현, Port 인터페이스 정의
- `infra`: Port 구현체 (Adapter), 기술 세부사항 캡슐화
- `ui`: HTTP 어댑터, Controller 구현

**의존성 방향**: `ui` → `application` → `domain` ← `infra`  
인프라가 도메인에 의존하지 않고, 인터페이스(Port)를 통해 연결

---

## 💡 주요 기술 상세

### 1. Transactional Outbox 패턴 흐름도

```
1. 상품 등록 요청
   ↓
2. @Transactional 시작
   ↓
3. MySQL에 Product 저장
   ↓
4. Outbox 테이블에 이벤트 저장 (PENDING)
   ↓
5. Transaction Commit (원자성 보장)
   ↓
6. OutboxPublishScheduler (1초 주기)
   - Outbox에서 PENDING 이벤트 조회
   - Spring Event 발행
   - 상태를 PUBLISHED로 변경
   ↓
7. EventListener
   - MongoDB 동기화
   - Elasticsearch 동기화
   - Redis 캐시 업데이트
```

### 2. Redis Lua Script 동시성 제어

**기존 방식 (Race Condition 발생)**
```java
// Thread 1: GET likeCount = 100
// Thread 2: GET likeCount = 100
// Thread 1: SET likeCount = 101
// Thread 2: SET likeCount = 101 (누락 발생!)
```

**Lua Script 방식 (원자성 보장)**
```lua
-- Redis 서버에서 단일 명령으로 실행
local current = redis.call('HGET', statsKey, field)
local newValue = current + input
redis.call('HSET', statsKey, field, newValue)
return newValue
```

### 3. CQRS 데이터 흐름

```
[Command Side - 쓰기]
Client Request
   ↓
ProductCommandService
   ↓
MySQL (Source of Truth)
   ↓
Outbox Event
   ↓
MongoDB / Elasticsearch / Redis 동기화

[Query Side - 읽기]
Client Request
   ↓
ProductQueryService
   ↓
Redis Cache (있으면) → 즉시 반환
   ↓ (없으면)
Elasticsearch (검색/필터링) or MySQL (상세 조회)
   ↓
Redis에 캐싱 후 반환
```

---

## 🎬 시연 영상

프로젝트의 실제 동작 모습을 확인하세요!

[![MUNOVA 시연 영상](https://img.youtube.com/vi/NCUD25v__2g/maxresdefault.jpg)](https://www.youtube.com/watch?v=NCUD25v__2g)

---

## 🔍 핵심 코드 리뷰

### Redis Lua Script 동시성 제어 구현

```java
@Repository
public class ProductStatsRedisDataCommandRepoImpl {
    
    private static final String UPDATE_FIELD_SCRIPT =
        "local statsKey = KEYS[1]; " +
        "local field = ARGV[1]; " +
        "local input = tonumber(ARGV[2]) or 0; " +
        "if redis.call('EXISTS', statsKey) == 0 then " +
        "  redis.call('HSET', statsKey, 'likeCount', 0, 'viewCount', 0, 'salesCount', 0); " +
        "end; " +
        "local current = tonumber(redis.call('HGET', statsKey, field)) or 0; " +
        "local newValue = current + input; " +
        "if newValue < 0 then return -1 end; " +
        "redis.call('HSET', statsKey, field, newValue); " +
        "return newValue";
    
    public Long incrementLikeCount(Long productId, int input) {
        String key = "product-stats" + productId;
        return redisTemplate.execute(
            updateFieldScript,
            Collections.singletonList(key),
            "likeCount",
            String.valueOf(input)
        );
    }
}
```

### Outbox 스케줄러 구현

```java
@Component
public class OutboxPublishScheduler {
    
    @Scheduled(fixedDelay = 1000)  // 1초마다 실행
    @Transactional
    public void publishPendingEvents() {
        List<ProductOutbox> pendingEvents = 
            productOutboxRepository.findTop1000ByStatusOrderByCreatedAtAsc(PENDING);
        
        for (ProductOutbox outbox : pendingEvents) {
            try {
                Object eventDto = deserialize(outbox.getEventType(), outbox.getEventValue());
                eventPublisher.publishEvent(eventDto);
                outbox.changePublishStatus();  // PENDING → PUBLISHED
            } catch (Exception e) {
                outbox.changeFailedStatus();   // PENDING → FAILED
            }
        }
    }
    
    @Scheduled(fixedDelay = 10000)  // 10초마다 재시도
    public void publishFailedEvents() {
        // FAILED 이벤트 재발행
    }
}
```

### Domain 엔티티 비즈니스 로직

```java
@Entity
public class Product extends BaseEntity {
    
    // 도메인 불변식 검증
    public static Product createDefaultProduct(String name, String info, Long price,
                                               Brand brand, Category category, Member member) {
        if (price < 0) {
            throw new IllegalArgumentException("가격은 음수일 수 없습니다.");
        }
        if (info.length() < 10) {
            throw new IllegalArgumentException("상품 정보는 최소 10자 이상이어야 합니다.");
        }
        
        return Product.builder()
            .name(name)
            .info(info)
            .price(price)
            .brand(brand)
            .category(category)
            .member(member)
            .build();
    }
    
    // 도메인 로직 캡슐화
    public void plusLike() {
        this.likeCount += 1;
    }
    
    public void minusLike() {
        if (this.likeCount <= 0) {
            throw ProductException.badRequestException("좋아요 수는 음수일 수 없습니다.");
        }
        this.likeCount -= 1;
    }
}
```

---

## 👨‍💻 개발자

**이성남** - Backend Developer (상품 도메인 전담)

- 📧 Email: dltjdska32@naver.com
- 🐙 GitHub: [@dltjdska32](https://github.com/dltjdska32)

### 기술적 강점
- Clean Architecture 기반 확장 가능한 시스템 설계
- 분산 시스템 데이터 일관성 보장 (Outbox 패턴)
- Redis 기반 고성능 동시성 제어 (Lua Script)
- DDD/CQRS 패턴 실전 적용


