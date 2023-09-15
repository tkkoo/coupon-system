package com.example.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.api.repository.CouponRepository;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplyServiceTest {

  @Autowired private ApplyService applyService;

  @Autowired private CouponRepository couponRepository;

  @Test
  public void 한번만응모() {
    applyService.apply(1L);

    long count = couponRepository.count();
    assertThat(count).isEqualTo(1L);
  }

  @Test
  public void 여러번응모() throws InterruptedException {
    int theadCount = 1000;
    ExecutorService executorService = Executors.newFixedThreadPool(32);
    CountDownLatch latch = new CountDownLatch(theadCount);

    for (int i = 0; i < theadCount; i++) {
      long userId = i;
      executorService.submit(
          () -> {
            try {
              applyService.apply(userId);
            } finally {
              latch.countDown();
            }
          });
    }
    latch.await();

    Thread.sleep(10000);
    long count = couponRepository.count();
    assertThat(count).isEqualTo(100L);
  }

  @Test
  public void 한명당_하나의쿠폰발급() throws InterruptedException {
    int theadCount = 1000;
    ExecutorService executorService = Executors.newFixedThreadPool(32);
    CountDownLatch latch = new CountDownLatch(theadCount);

    for (int i = 0; i < theadCount; i++) {
      long userId = i;
      executorService.submit(
          () -> {
            try {
              applyService.apply(1L);
            } finally {
              latch.countDown();
            }
          });
    }
    latch.await();

    Thread.sleep(10000);
    long count = couponRepository.count();
    assertThat(count).isEqualTo(1L);
  }

}
