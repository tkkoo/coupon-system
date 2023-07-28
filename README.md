# [실습으로 배우는 선착순 이벤트 시스템 - 인프런 강의](https://www.inflearn.com/course/%EC%84%A0%EC%B0%A9%EC%88%9C-%EC%9D%B4%EB%B2%A4%ED%8A%B8-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EC%8B%A4%EC%8A%B5/dashboard)

## 상황

선착순 100명에게 할인쿠폰을 제공하는 이벤트를 진행하고자 한다.

## 이슈

- 쿠폰이 100개 보다 많이 발급
- 이벤트 페이지 접속 X
- 서버 전체가 느려짐

## 요구사항

- 할인 쿠폰을 첫 100명의 고객에게만 제공하도록 보장하며, 100개 이상의 쿠폰 발행을 방지
- 이벤트의 영향을 이벤트 페이지로만 제한하여 참여하지 않는 사용자의 사용자 경험을 보호
- 급격한 트래픽 정증가를 효율적으로 처리하여 서버 안정성과 이벤트 성공을 보장

## 아래 로직의 문제점

```java
public void apply(Long userId){
    long count=couponRepository.count();
    if(count>100){
        return;
    }
    couponRepository.save(new Coupon(userId));
}
```
- 경쟁 상황(Race Condition) 발생
  - 여러 요청이 동시에 apply 메서드를 호출할 경우
  - 쿠폰 수량을 확인(count())하고 쿠폰을 발급(save)하는 사이에 다른 요청이 쿠폰을 발급받을 수 있습니다.
  - 이로 인해 총 쿠폰 수량이 100개를 초과할 수 있습니다. 
- 경쟁 상황이란: 2개 이상의 스레드가 공유 데이터에 엑세스할 하고 동시에 작업을 할 때 발생하는 문제