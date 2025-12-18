# 🛒 MUNOVA - 이커머스 온라인 신발 쇼핑몰




###  🎯 프로젝트 개요

<div style="border-left: 4px solid #808080; padding-left: 16px; margin-left: 0;">

**MUNOVA**는 국내 유명 이커머스 플랫폼(무신사, 29CM 등)을 벤치마킹하여 구축한 신발 전문 온라인 쇼핑몰입니다.  
DAU 200,000명, 피크 유저 2,000명을 목표로 설정하고, **대용량 트래픽 처리**와 **성능 최적화**에 중점을 두었습니다.
</div>

---
<BR>



### 🎬 시연 영상

<div align="center">

<a href="https://www.youtube.com/watch?v=NCUD25v__2g">
  <img src="https://img.youtube.com/vi/NCUD25v__2g/maxresdefault.jpg" alt="MUNOVA 시연 영상" width="600">
</a>

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



### 🌏 서버 아키텍처

<div align="center">
<img src="https://github.com/dltjdska32/my-resume-img/blob/main/munova_imgs/architecture/%EC%84%9C%EB%B2%84.png?raw=true" alt="서버 아키텍처" width="600">
</div>

---
<BR>

## 🏗️ 담당 도메인: 상품(Product)

### 핵심 기능 구현

<table>
<tr>
<td width="33%" style="border-left: 4px solid #2563eb; padding: 20px; background-color: #f0f9ff; border-radius: 8px; vertical-align: top;">
<strong style="color: #2563eb; font-size: 18px;">🔍 상품 조회</strong><br>
<small style="color: #64748b;">CQRS Read Model</small>
<hr style="margin: 12px 0; border: none; border-top: 1px solid #e2e8f0;">
<div style="margin-top: 12px;">
<strong style="color: #1e293b;">전체 상품 조회</strong><br>
<small style="color: #475569;">• 카테고리, 브랜드, 가격 범위 필터링<br>• 인기순/최신순/가격순 정렬<br>• Cursor 기반 페이지네이션</small>
</div>
<div style="margin-top: 16px;">
<strong style="color: #1e293b;">상품 상세 조회</strong><br>
<small style="color: #475569;">• Redis 캐싱 (재고/좋아요/조회수)<br>• MongoDB 이미지 조회<br>• Elasticsearch 연관 상품 추천</small>
</div>
</td>
<td width="33%" style="border-left: 4px solid #10b981; padding: 20px; background-color: #f0fdf4; border-radius: 8px; vertical-align: top;">
<strong style="color: #10b981; font-size: 18px;">👤 사용자 기능</strong><br>
<small style="color: #64748b;">Command Model</small>
<hr style="margin: 12px 0; border: none; border-top: 1px solid #e2e8f0;">
<div style="margin-top: 12px;">
<strong style="color: #1e293b;">좋아요 관리</strong><br>
<small style="color: #475569;">• Redis Lua Script 동시성 제어<br>• MySQL 트랜잭션 저장<br>• Spring Batch 주기적 동기화</small>
</div>
<div style="margin-top: 16px;">
<strong style="color: #1e293b;">장바구니 관리</strong><br>
<small style="color: #475569;">• 실시간 재고 검증<br>• 아이템 추가/삭제/수정<br>• 품절 상품 자동 필터링</small>
</div>
</td>
<td width="33%" style="border-left: 4px solid #f59e0b; padding: 20px; background-color: #fffbeb; border-radius: 8px; vertical-align: top;">
<strong style="color: #f59e0b; font-size: 18px;">🛠️ 판매자 기능</strong><br>
<small style="color: #64748b;">Product Management</small>
<hr style="margin: 12px 0; border: none; border-top: 1px solid #e2e8f0;">
<div style="margin-top: 12px;">
<strong style="color: #1e293b;">상품 등록</strong><br>
<small style="color: #475569;">• S3 다중 이미지 업로드<br>• 사이즈별 재고 설정<br>• MySQL → MongoDB → ES 동기화</small>
</div>
<div style="margin-top: 16px;">
<strong style="color: #1e293b;">상품 수정/삭제</strong><br>
<small style="color: #475569;">• Outbox 패턴 분산 DB 동기화<br>• Soft Delete 데이터 보존<br>• 이벤트 기반 연관 데이터 정리</small>
</div>
</td>
</tr>
</table>

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


### 💻 테스트 환경

<div align="center">

<img src="https://github.com/dltjdska32/my-resume-img/blob/main/munova_imgs/architecture/%ED%85%8C%EC%8A%A4%ED%8A%B8%ED%99%98%EA%B2%BD.png?raw=true" alt="테스트 환경" width="600">

</div>

---
<BR>