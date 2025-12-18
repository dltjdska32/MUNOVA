# 🛒 MUNOVA - 이커머스 온라인 신발 쇼핑몰





### 🎬 시연 영상

<div align="center">

<a href="https://www.youtube.com/watch?v=NCUD25v__2g">
  <img src="https://img.youtube.com/vi/NCUD25v__2g/maxresdefault.jpg" alt="MUNOVA 시연 영상" width="600">
</a>

</div>

---
<BR>


###  🎯 프로젝트 개요

<div style="border-left: 4px solid #808080; padding-left: 16px; margin-left: 0;">

**MUNOVA**는 국내 유명 이커머스 플랫폼(무신사, 29CM 등)을 벤치마킹하여 구축한 신발 전문 온라인 쇼핑몰입니다.  
DAU 200,000명, 피크 유저 2,000명을 목표로 설정하고, **대용량 트래픽 처리**와 **성능 최적화**에 중점을 두었습니다.
</div>

---
<BR>


### 🌏 서버 아키텍처

<div align="center">
<img src="https://github.com/dltjdska32/my-resume-img/blob/main/munova_imgs/architecture/%EC%84%9C%EB%B2%84.png?raw=true" alt="서버 아키텍처" width="600">
</div>

---
<BR>


### 💻 테스트 환경

<div align="center">

<img src="https://github.com/dltjdska32/my-resume-img/blob/main/munova_imgs/architecture/%ED%85%8C%EC%8A%A4%ED%8A%B8%ED%99%98%EA%B2%BD.png?raw=true" alt="테스트 환경" width="600">

</div>

---
<BR>



### 🔄 개발 프로세스

**애자일(Agile) 방법론 적용**

<div align="center">

<img src="https://github.com/dltjdska32/my-resume-img/blob/main/munova_imgs/architecture/%EC%95%A0%EC%9E%90%EC%9D%BC.png?raw=true" alt="애자일 개발 프로세스" width="600">

</div>

이러한 **반복적인 개발 → 테스트 → 개선 사이클**을 통해 안정적이고 고성능의 시스템을 구축했습니다.

---
<BR>



## 🏗️ 담당 도메인: 상품(Product)

### 1. 핵심 기능 구현

<table>
<tr>
<td valign="top">
<strong>📦 상품</strong><br>
<hr>
• 상품 전체 조회 (R)<br>
• 상품 상세 조회 (R)<br>
• 판매자 등록 상품 조회 (R)<br>
• 상품 조회수 업데이트 (U)<br>
• 상품 등록, 수정, 삭제 (C, U, D)
</td>
<td valign="top">
<strong>❤️ 좋아요</strong><br>
<hr>
• 좋아요 리스트 조회 (R)<br>
• 좋아요 등록, 삭제 (C, D)<br>
• 상품 좋아요수 업데이트 (U)
</td>
<td valign="top">
<strong>🛒 장바구니</strong><br>
<hr>
• 장바구니 리스트 조회 (R)<br>
• 장바구니 등록, 수정, <br> &nbsp;  삭제 (C, U, D)
</td>
</tr>
</table>

### 2. 적용 아키텍처 및 패턴

<table>
<tr>
<td valign="top">
<strong>🏛️ DDD 아키텍처</strong><br>
<hr>
• 비즈니스 본질에 집중하는 설계<br>
• 에그리게이트 패턴 <br> &nbsp; &nbsp;(상품, 장바구니, 좋아요)
</td>
<td valign="top">
<strong>🔌 Hexagonal 아키텍처</strong><br>
<hr>
• Port & Adapter 패턴<br>
• 외부 기술적 요소에 의존적이지 않은 핵심 로직 구현
</td>
</tr>
<tr>
<td valign="top">
<strong>📖 CQRS 패턴</strong><br>
<hr>
• 읽기와 쓰기 분리<br>
• 읽기 성능 향상<br>
• 복잡한 조회 로직 단순화
</td>
<td valign="top">
<strong>📦 Outbox 패턴</strong><br>
<hr>
• 분산 트랜잭션 데이터 정합성 보장<br>
• 재시도 로직 보장<br>
• 이벤트 기반 비동기 처리
</td>
</tr>
</table>

### 3. 사용 기술 스택


<div align="center">

<img src="https://github.com/dltjdska32/my-resume-img/blob/main/munova_imgs/architecture/%EC%83%81%ED%92%88%EB%8F%84%EB%A9%94%EC%9D%B8%20%EA%B8%B0%EC%88%A0%EC%8A%A4%ED%83%9D.png?raw=true" alt="상품도메인 기술스택" width="600">

</div>

<div style="margin-top: 20px; padding-left: 24px; font-family: 'Segoe UI', 'Malgun Gothic', sans-serif; font-size: 16px; font-weight: 700; line-height: 1.6; color: #2d3748;">

**→** 각각의 기술들에 적절한 역할을 설정하여 하나의 기술에 집중될 수 있는 부하를 분산시키려고 노력했습니다.

</div>

---




## 🎨 기술적 도전과 해결












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

