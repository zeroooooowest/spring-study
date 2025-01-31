
## 캐싱 전략
Cache-aside
- 항상 캐시를 먼저 조회한다.
  - 캐시에 데이터가 없다면 (cache miss) DB 등 원천 데이터 저장소를 조회하고 그 결과를 캐싱한다.
- 최초 접근은 DB 를 통하기 때문에 느리고 캐시 데이터가 항상 최신이 아닐 수 있다.
- but, 캐시 miss 시 DB 를 통해 데이터를 데이터를 가져올 수 있기 때문에 miss 가 치명적이지 않다.

Write-through
- 데이터를 쓸 때 항상 캐시를 갱신하면서 캐시의 상태를 최신으로 유지한다.
- 캐시와 DB 는 동기화되어 있기 때문에 일관성있는 캐싱된 데이터를 보장한다.
- 갱신되는 모든 데이터가 캐시에 저장되고 쓰기시 부담이 증가하는 단점이 있다.

Write-back
- 데이터 쓰기는 캐시에서만 발생한다.
  - DB 에는 일정 주기로 캐시의 데이터를 반영한다.
- 쓰기가 많은 환경의 경우 DB 쓰기 연산을 크게 줄일 수 있다.
- 단, 캐시 데이터가 DB에 반영되기 전 장애가 발생한다면 데이터가 유실될 수 있다.

---

## 직렬화 & 역직렬화

`GenericJasckson2JsonRedisSerializer`
- `"@class":"클래스 풀패스"`
- 위와 같이 클래스의 풀패스를 함께 저장하고 역직렬화시 역직렬화 대상 클래스는 반드시 동일한 경로를 갖고 있어야 한다.
  - 경로는 물론 해당 클래스의 이름도 변경되면 안 된다.
- msa 와 같은 아키텍처라면 a 서비스에서 캐싱한 데이터를 b 서비스에서 조회해서 사용해야 할 수도 있는데 이 때 경로를 맞춰야 하는 이 방식은 문제가 될 수 있다.


---


## reference
http://arahansa.github.io/docs_spring/redis.html