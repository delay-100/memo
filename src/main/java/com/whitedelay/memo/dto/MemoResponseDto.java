package com.whitedelay.memo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MemoResponseDto {
    private long id;
    private String contents;
    private LocalDateTime createdAt;

}
