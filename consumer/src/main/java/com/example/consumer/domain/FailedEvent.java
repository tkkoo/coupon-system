package com.example.consumer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class FailedEvent {
  @Id @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  private Long id;
  private Long userId;

  public FailedEvent() {
  }

  public FailedEvent(Long userId) {
    this.userId = userId;
  }
}
