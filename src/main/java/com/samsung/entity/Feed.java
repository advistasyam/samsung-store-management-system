package com.samsung.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@Entity
@Table(name = "feeds")
public class Feed {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(nullable = false, name = "admin_id")
  private Admin admin;

  @Column(nullable = false, columnDefinition = "text")
  private String content;

  @Column(name = "created_at")
  private Timestamp createdAt;
}
