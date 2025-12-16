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
**상품 도메인**을 담당하여 성능 최적화 및 아키텍처 개선을 주도했습니다.

### 🎯 주요 성과



## 🏗️ 담당 도메인: 상품(Product)

### 핵심 기능

#### 🔍 조회 기능
- **상품 전체 조회** - 다양한 필터링 및 정렬 옵션 지원
- **상품 상세 조회** - 실시간 재고, 좋아요 수, 조회수 제공

#### 👤 사용자 기능
- **좋아요 관리**
  - 상품 좋아요 리스트 조회
  - 좋아요 추가/삭제
  - Redis 기반 동시성 제어
- **장바구니 관리**
  - 장바구니 리스트 조회
  - 장바구니 추가/삭제

#### 🛠️ 판매자 기능
- **등록 상품 조회** - 판매자가 등록한 상품 리스트 조회
- **상품 등록** - 다중 이미지 업로드, 사이즈별 재고 설정
- **상품 수정** - 가격, 재고, 상품 정보 수정
- **상품 삭제** - Soft Delete 방식

---

## 🎨 기술적 도전과 해결

### 1️⃣ **CQRS 패턴 적용**
```
📖 Problem: 복잡한 조회 요구사항(필터링, 정렬, 검색) 처리 어려움
✅ Solution: 읽기/쓰기 모델 분리로 조회 성능 대폭 향상
```

### 2️⃣ **Redis 동시성 제어**
```
📖 Problem: 좋아요/조회수 동시 요청 시 데이터 정합성 문제
✅ Solution: Redis Lua Script를 활용한 원자적 연산 보장
```

### 3️⃣ **Port & Adapter 패턴**
```
📖 Problem: 외부 기술(DB, 검색엔진) 변경 시 도메인 로직 영향
✅ Solution: 의존성 역전으로 핵심 로직 보호 및 테스트 용이성 확보
```

### 4️⃣ **OUTBOX 패턴**
```
📖 Problem: 트랜잭션 커밋과 이벤트 발행 간 불일치
✅ Solution: OUTBOX 테이블 도입으로 데이터 일관성 보장
```

---

## 🛠️ 기술 스택

### Backend
- **Framework**: Spring Boot 3.x
- **Language**: Java 17
- **ORM**: JPA (Hibernate)

### Database
- **RDB**: MySQL (트랜잭션 데이터)
- **NoSQL**: MongoDB (상품 이미지, 리뷰 데이터)
- **Cache**: Redis (조회수, 좋아요, 세션)
- **Search**: Elasticsearch (상품 검색)

### Architecture
- **Design Pattern**: DDD, CQRS, Port & Adapter, OUTBOX
- **API**: RESTful API

---

## 📊 성능 최적화 전략

### 🚀 쿼리 최적화
- **N+1 문제 해결**: Fetch Join 활용
- **Covering Index**: 조회 쿼리에 필요한 모든 컬럼 인덱스 포함
- **페이지네이션**: Offset 대신 Cursor 기반 페이징 적용

### ⚡ 캐싱 전략
- **Look-Aside 캐시**: 상품 상세 정보 (TTL: 10분)
- **Write-Behind 캐시**: 조회수/좋아요 (비동기 배치 업데이트)

### 🔄 비동기 처리
- **이벤트 기반 아키텍처**: 도메인 이벤트 발행 및 구독
- **스케줄링**: 인기 상품 통계 집계 (매 1시간)

---

## 📂 프로젝트 구조

```
product-domain/
├── domain/              # 도메인 엔티티 및 비즈니스 로직
│   ├── model/          # Product, Like, Cart 엔티티
│   ├── service/        # 도메인 서비스
│   └── event/          # 도메인 이벤트
├── application/         # 애플리케이션 서비스 (유스케이스)
│   ├── command/        # 명령 처리 (CUD)
│   └── query/          # 조회 처리 (R)
├── adapter/            # 외부 연동 어댑터
│   ├── in/            # Controller (REST API)
│   └── out/           # Repository, 외부 API 연동
└── infrastructure/     # 기술 인프라
    ├── config/        # 설정
    ├── cache/         # Redis 캐시
    └── search/        # Elasticsearch
```

---

## 🎬 시연 영상

프로젝트의 실제 동작 모습을 확인하세요!

[![MUNOVA 시연 영상](https://img.youtube.com/vi/NCUD25v__2g/maxresdefault.jpg)](https://www.youtube.com/watch?v=NCUD25v__2g)

---

## 👨‍💻 개발자

**이성남** - Backend Developer (상품 도메인 담당)

- 📧 Email: dltjdska32@naver.com
- 🐙 GitHub: [@dltjdska32](https://github.com/dltjdska32)


