package dev.gunho.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PagingDTO<T> {
    private List<T> content;       // 현재 페이지 콘텐츠 데이터
    private int size;              // 현재 페이지 크기
    private long totalElements;    // 총 데이터 개수
    private int number;            // 현재 페이지 번호
    private int totalPages;        // 전체 페이지 수
}