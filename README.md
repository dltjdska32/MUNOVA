# 🛒 MUNOVA - 이커머스 온라인 신발 쇼핑몰



###  🎯 프로젝트 개요

<div style="border-left: 4px solid #808080; padding-left: 16px; margin-left: 0;">

**MUNOVA**는 국내 유명 이커머스 플랫폼(무신사, 29CM 등)을 벤치마킹하여 구축한 신발 전문 온라인 쇼핑몰입니다.  
**DAU 200,000명, 피크 유저 2,000명, p(95) 3초내외**를 목표로 설정하고, **대용량 트래픽 처리**와 **성능 최적화**에 중점을 <br> 두었습니다.
</div>

---
<BR>



### 🎬 시연 영상

<div align="center">

<a href="https://www.youtube.com/watch?v=NCUD25v__2g">
  <img src="https://img.youtube.com/vi/NCUD25v__2g/maxresdefault.jpg" alt="MUNOVA 시연 영상" width="600">
</a>

</div>


<BR>





### 🌏 서버 아키텍처

<div align="center">
<img src="https://github.com/dltjdska32/my-resume-img/blob/main/munova_imgs/architecture/%EC%84%9C%EB%B2%84.png?raw=true" alt="서버 아키텍처" width="700">
</div>


<BR>


### 💻 테스트 환경

<div align="center">

<img src="https://github.com/dltjdska32/my-resume-img/blob/main/munova_imgs/architecture/%ED%85%8C%EC%8A%A4%ED%8A%B8%ED%99%98%EA%B2%BD.png?raw=true" alt="테스트 환경" width="700">

</div>


<BR>



### 🔄 개발 프로세스

**애자일(Agile) 방법론 적용**

<div align="center">

<img src="https://github.com/dltjdska32/my-resume-img/blob/main/munova_imgs/architecture/%EC%95%A0%EC%9E%90%EC%9D%BC.png?raw=true" alt="애자일 개발 프로세스" width="700">

</div>

이러한 **반복적인 개발 → 테스트 → 개선 사이클**을 통해 안정적이고 고성능의 시스템을 구축했습니다.

---
<BR>





## 🏗️ 담당 도메인: 상품(Product)

### 1. 핵심 기능 구현

<table width="100%" cellpadding="12" cellspacing="12" style="width: 100%; table-layout: fixed;">
<tr>
<td width="33.33%" valign="top" style="padding: 14px; font-size: 12px; line-height: 1.3; width: 33.33%;">
<strong>📦 상품</strong><br>
<hr style="margin: 8px 0;">
<ul>
  <li><strong>상품 전체 조회 (R)</strong>
    <ul>
      <li> <strong>키워드 기반 검색:</strong> 상품명, 브랜드명, 카테고리명, 옵션명을 통한 검색</li>
      <li><strong>필터 기능:</strong> 카테고리, 옵션 조건을 통한 검색 결과 필터링</li>
      <li><strong>정렬 기능:</strong> 최신순, 조회수순, 판매량순, 좋아요순 정렬</li>
    </ul>
  </li>
  <li>상품 상세 조회 (R)</li>
  <li>판매자 등록 상품 조회 (R)</li>
  <li><strong>상품 조회수 업데이트 (U)</strong>
    <ul>
      <li> <strong> 동시성 제어:</strong> Redis Lua Script를 통한 안전한 조회수 증가</li>
    </ul>
  </li>
  <li>상품 등록, 수정, 삭제 (C, U, D)</li>
</ul>
</td>
<td width="33.33%" valign="top" style="padding: 14px; font-size: 12px; line-height: 1.3; width: 33.33%;">
<strong>❤️ 좋아요</strong><br>
<hr style="margin: 8px 0;">
<ul>
  <li>좋아요 리스트 조회 (R)</li>
  <li>좋아요 등록, 삭제 (C, D)</li>
  <li><strong>상품 좋아요수 업데이트 (U)</strong>
    <ul>
      <li> <strong>동시성 제어:</strong> Redis Lua Script를 통한 안전한 좋아요 수 증가</li>
    </ul>
  </li>
</ul>
</td>
<td width="33.33%" valign="top" style="padding: 14px; font-size: 12px; line-height: 1.3; width: 33.33%;">
<strong>🛒 장바구니</strong><br>
<hr style="margin: 8px 0;">
<ul>
  <li>장바구니 리스트 조회 (R)</li>
  <li>장바구니 등록, 수정, 삭제 (C, U, D)</li>
</ul>
</td>
</tr>
</table>

<br>

### 2. 적용 아키텍처 및 패턴

<table cellpadding="12" cellspacing="12">
<tr>
<td valign="top" style="padding: 16px;">
<strong>🏛️ DDD 아키텍처</strong><br>
<hr style="margin: 8px 0;">
• 비즈니스 본질에 집중하는 설계<br>
• 에그리게이트 패턴<br>
&nbsp;&nbsp;&nbsp; (상품, 장바구니, 좋아요)
</td>
<td valign="top" style="padding: 16px;">
<strong>🔌 Hexagonal 아키텍처</strong><br>
<hr style="margin: 8px 0;">
• Port & Adapter 패턴<br>
• 외부 기술적 요소에 의존적이지<br>
&nbsp;&nbsp;&nbsp; 않은 핵심 로직 구현
</td>
</tr>
<tr>
<td valign="top" style="padding: 16px;">
<strong>📖 CQRS 패턴</strong><br>
<hr style="margin: 8px 0;">
• 읽기와 쓰기 분리<br>
• 읽기 성능 향상<br>
• 복잡한 조회 로직 단순화
</td>
<td valign="top" style="padding: 16px;">
<strong>📦 Outbox 패턴</strong><br>
<hr style="margin: 8px 0;">
• 분산 트랜잭션 데이터 정합성 보장<br>
• 재시도 로직 보장<br>
• 이벤트 기반 비동기 처리
</td>
</tr>
</table>

<br>

### 3. 사용 기술 스택


<div align="center">

<img src="https://github.com/dltjdska32/my-resume-img/blob/main/munova_imgs/architecture/%EC%83%81%ED%92%88%EB%8F%84%EB%A9%94%EC%9D%B8%20%EA%B8%B0%EC%88%A0%EC%8A%A4%ED%83%9D.png?raw=true" alt="상품도메인 기술스택" width="600">

</div>

<div style="margin-top: 20px; padding-left: 24px; font-family: 'Segoe UI', 'Malgun Gothic', sans-serif; font-size: 16px; font-weight: 700; line-height: 1.6; color: #2d3748;">

**→** 각각의 기술들에 적절한 역할을 설정하여 하나의 기술에 집중될 수 있는 부하를 분산하기 위해 노력했습니다.

- **MySQL**: 최종 데이터 일관성 유지
  - 트랜잭션이 필요한 쓰기 작업의 최종 저장소
  - 상품 등록, 수정, 삭제 등 데이터 변경 작업 처리

- **Redis**: 버퍼, 캐시, 동시성 제어
  - **버퍼**: 통계 데이터(좋아요수, 조회수, 판매량) 1시간 간격 배치를 통한 데이터 동기화용 버퍼
  - **캐시**: 상품 상세 조회 시 좋아요수, 조회수, 판매량 등을 빠르게 조회
  - **동시성 제어**: Lua Script를 통한 안전한 통계 데이터 증가 처리
  - **분산 배치 처리**: 통계 데이터 동기화 시 Redis에 청크별 ID를 미리 할당하여 분산 서버 환경에서도 별도의 분산 락 없이 각 서버가 고유한 청크 ID를 할당받아 동시성 문제 없이 병렬 동기화 가능하도록 설계

- **Elasticsearch**: 전문 검색
  - 상품 전체 조회 시 키워드(검색)가 존재할 경우 전문 검색 수행

- **MongoDB**: 읽기 전용 조회 및 상세 정보 저장
  - **상품 전체 조회**: 키워드가 없는 경우 카테고리, 옵션 등을 통한 필터링 조회
  - **상품 상세 조회**: 상세 정보 조회 처리
  - **문서 구조**: 상세 조회와 상품 전체 조회에 필요한 데이터를 하나의 Document로 통합하여 조회 성능 최적화


</div>

---
<br>



## 🎨 기술적 도전 및 문제 해결

- 최소한의 인프라(ES 노드 1대, MongoDB 1대, MySQL 1대, Redis 1대, 애플리케이션 서버 1대)로 실제 서비스 수준 트래픽을 처리하기 위해 성능 병목을 예측·검증하고 개선한 과정 정리

### 1. 상품 전체 조회 1차 성능 개선 - RDB(MySQL) 쿼리 튜닝

- 테스트 환경
  - 상품 관련 총 데이터 수 (약 3,000만)
    - 상품: 300만
    - 상품 디테일: 700만
    - 상품 디테일 - 옵션 매핑: 1,500만
    - 기타 상품 관련 데이터: 500만 이상
  - 커넥션 타임아웃: 30초
  - 서버 스레드: 50
  - 커넥션 스레드: 40
  - MySQL 스레드: 151 (기본)
  - VU: 1, 요청: 1회

- 문제 상황
  - 약 3,000만 건의 데이터 중 20개 상품 조회 시 WAS와 DB 서버 간 커넥션 끊김 발생
    - ``LIKE "%keyword%"`` 검색으로 인한 풀 테이블 스캔
    - 인덱스 미적용으로 인한 비효율적인 조회
    - 검색 옵션 확인용 DISTINCT 함수 사용으로 인한 추가 정렬/그룹핑 비용
    - 페이징 처리 시 카운트 쿼리로 인한 추가 지연

- 해결 방법
  - FULL-TEXT INDEX 도입
    - 상품명, 카테고리명, 브랜드명, 옵션명 등에 FULL-TEXT INDEX 적용으로 풀 테이블 스캔 방지
  - 적절한 복합 인덱스 및 커버링 인덱스 적용
    - 자주 사용하는 조건/정렬 컬럼 조합으로 복합 인덱스 구성
    - 커버링 인덱스로 테이블 접근 최소화
  - DISTINCT 제거를 위한 서브쿼리 최적화
    - 검색 옵션 확인용 서브쿼리로 DISTINCT 사용 제거
  - 커서 기반 페이징 도입
    - 기존 COUNT + LIMIT-OFFSET 방식에서 커서 기반 페이징으로 전환하여 COUNT 쿼리 비용 제거
    - LIMIT-OFFSET 방식의 단점인 페이지가 뒤로 갈수록 느려지는 문제 해소 및 일관된 응답 시간 보장

- 결과

  - 개선 전: P6Spy 기준, **상품 전체 조회 1회 요청 (쿼리 실행 + 네트워크 왕복 시간 포함)** -> 약 106초

    <div align="center">
      <img src="https://github.com/dltjdska32/my-resume-img/blob/main/munova_imgs/test_img/%ED%8E%98%EC%9D%B4%EC%A7%95,%20%EC%A1%B0%ED%9A%8C%20%EC%BF%BC%EB%A6%AC%20%EC%8B%9C%EA%B0%84.png?raw=true" alt="페이징 및 조회 쿼리 시간 - 개선 전" width="700">
    </div>

  - 개선 후: **쿼리 튜닝 및 인덱스/페이징 최적화 적용 후 순수 쿼리 실행 시간** ->  0.03초

    <div align="center">
      <img src="https://github.com/dltjdska32/my-resume-img/blob/main/munova_imgs/test_img/%EA%B2%B0%EA%B3%BC%200.03%EC%B4%88%20%EA%B0%9C%EC%84%A0.png?raw=true" alt="결과 0.03초로 개선" width="700">
    </div>

  - 종합적으로, **네트워크 왕복 시간이 포함된 기존 응답 시간 약 106초 → 순수 쿼리 기준 0.03초 수준으로 개선** <br>✅ **(네트워크 왕복 비용 제외 기준 약 99.97% 성능 개선)**


<br>

### 2. TCP 커넥션 유실 문제 발생

- 테스트 환경
  - 가상유저(VU): 9,000명
  - 요청 패턴: 각 VU가 1초에 1회 요청 (이론적 처리량: 9,000건/초)
  - 애플리케이션 서버: 1대

- 문제 상황
  - **9,000명의 VU가 1초에 1회 요청 시 이론적으로 9,000건의 요청이 처리되어야 하나, 실제로는 3,837건만 처리됨**
  - **약 57.3%의 요청이 TCP 커넥션 단계에서 유실** (9,000 - 3,837 = 5,163건 유실)
  - `http_req_failed: 0.00%`로 HTTP 레벨 에러는 없으나, 대부분의 요청이 TCP 커넥션 수립 단계에서 실패
  - 처리된 요청의 응답 시간도 매우 느림 (평균 14.89초, p(95) 26.63초)

- 원인 분석
  - **Accept Queue 포화**
    - 애플리케이션 서버의 `acceptCount` 설정값이 부족하여 커넥션 요청을 대기할 수 있는 큐 크기 부족
    - 커널의 `somaxconn` (Socket Maximum Connection) 값이 낮아 커널 레벨에서의 대기 큐 크기 제한
  - **SYN Queue 포화**
    - TCP 3-way handshake 과정에서 SYN 패킷을 대기하는 커널의 SYN Queue가 포화 상태
    - `tcp_max_syn_backlog` 파라미터로 제어되는 SYN Queue 크기 부족
  - **커널 영역의 큐 구조**
    - **SYN Queue**: 클라이언트의 SYN 패킷을 대기하는 큐 (3-way handshake 1단계)
    - **Accept Queue**: 3-way handshake 완료 후 애플리케이션에서 `accept()` 호출을 대기하는 큐
    - 두 큐 중 하나라도 포화되면 새로운 커넥션 수립 실패

- 해결 방법
  - **애플리케이션 레벨 튜닝**
    - Tomcat의 `acceptCount` 값 증가 (기본값 100 → 1000 이상)
    - 애플리케이션 서버가 더 많은 커넥션 요청을 대기할 수 있도록 설정
  - **커널 레벨 튜닝**
    - `somaxconn` 값 증가: `/proc/sys/net/core/somaxconn` (기본값 128 → 2048 이상)
      - Accept Queue의 최대 크기를 결정하는 커널 파라미터
    - `tcp_max_syn_backlog` 값 증가: `/proc/sys/net/ipv4/tcp_max_syn_backlog` (기본값 512 → 2048 이상)
      - SYN Queue의 최대 크기를 결정하는 커널 파라미터
    - `tcp_syncookies` 활성화: `/proc/sys/net/ipv4/tcp_syncookies` (1로 설정)
      - SYN Queue 포화 시 SYN Cookie를 사용하여 SYN Flooding 공격 방어 및 큐 포화 완화
  - **커넥션 풀 최적화**
    - 데이터베이스 커넥션 풀 크기 조정
    - HTTP 클라이언트 커넥션 풀 설정 최적화

- 결과
  - **개선 전**: 9,000 VU 요청 시 3,837건만 처리 (약 57.3% 커넥션 유실)
  - **개선 후**: 커널 파라미터 및 애플리케이션 설정 최적화 후 9,000 VU 동시 요청 시에도 커넥션 유실 없이 정상 처리
  - Accept Queue와 SYN Queue 크기 증가로 대량의 동시 커넥션 요청을 안정적으로 처리 가능
  - `acceptCount`, `somaxconn`, `tcp_max_syn_backlog` 튜닝을 통해 TCP 커넥션 수립 단계의 병목 해소


### 2. 상품 관련 데이터 (약 3,000만) 조회시 p(95) 30초 문제 발생 - ElasticSearch, MongoDB 도입, CQRS 적용

- 문제 상황 및 문제 예측
  - **문제 상황**
    - 약 3,000만 건의 데이터에서 가상유저(VU) 9,000명이 1초에 1회 요청 시 p(95) 약 26초로 목표 성능(p(95) 3초)을 크게 초과
    - TCP 커넥션 튜닝 후에도 응답 시간이 여전히 높아 애플리케이션 레벨의 병목 존재 확인
  - **예상 문제점**
    - **읽기/쓰기 경합**: 단일 DB(MySQL)에서 읽기와 쓰기 작업이 동시에 빈번히 발생할 경우 락 경합으로 인한 성능 저하 예상
    - **단일 DB 부하 집중**: 상품 조회뿐만 아니라 채팅, 결제, 추천 등 다른 도메인에서도 동일한 MySQL을 바라보는 상황에서 DB 부하 집중 예상
    - **확장성 제약**: 단일 RDB에 모든 부하가 집중되어 수평 확장이 어려운 구조
    - **읽기 성능 병목**: 복잡한 검색 및 필터링 쿼리가 트랜잭션 데이터와 동일한 DB에서 실행되어 읽기 성능 저하 예상

 - 해결 방법
  - CQRS 도입을 통한 






---
<br>


## 📊 성능 최적화 전략

### ⚡ 캐싱 전략
- **Write-Behind Cache**: 조회수/좋아요 Redis 저장 후 배치 동기화
  - 즉시 반영이 필요 없는 통계 데이터를 배치로 처리하여 DB 부하 감소


### 🔄 비동기 처리 & 배치
- **이벤트 기반 아키텍처**: 
  - Domain Event 발행으로 비즈니스 로직 결합도 감소
  - Outbox 스케줄러로 At-Least-Once 전달 보장


### 🎯 데이터베이스 분리
- **폴리글랏 퍼시스턴스**
  - MySQL: 트랜잭션 데이터 (상품, 주문, 재고)
  - MongoDB: 대용량 이미지 URL, 상품 설명
  - Redis: 실시간 통계, 세션
  - Elasticsearch: 전문 검색, 필터링

---




---



### 기술적 강점
- Clean Architecture 기반 확장 가능한 시스템 설계
- 분산 시스템 데이터 일관성 보장 (Outbox 패턴)
- Redis 기반 고성능 동시성 제어 (Lua Script)
- DDD/CQRS 패턴 실전 적용

