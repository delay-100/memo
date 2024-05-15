package com.whitedelay.memo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity // Entity임을 명시, 기본 생성자 만들어줌
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 매개변수가 없는 기본 생성자를 사용할 수 없게 함
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1, 2, 3, 4와 같이 순차적으로 id값을 자동으로 증가시킴
    private Long id;

    private String contents;

    private LocalDateTime createdAt;
}
