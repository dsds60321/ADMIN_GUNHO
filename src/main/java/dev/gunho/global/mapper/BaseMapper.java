package dev.gunho.global.mapper;

import dev.gunho.global.dto.PagingDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public interface BaseMapper<E,D> {

    // E: 엔티티, D: DTO

    // 엔티티를 DTO로 변환
    D toDTO(E entity);

    // DTO 리스트 변환 (필요할 경우 추가적으로 사용 가능)
    default List<D> toDTOList(List<E> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // Page<E>를 PagingDTO<D>로 변환
    default PagingDTO<D> toPagingDTO(Page<E> page) {
        return new PagingDTO<>(
                page.getContent().stream().map(this::toDTO).collect(Collectors.toList()), // DTO 변환된 콘텐츠
                page.getSize(),             // 페이지 크기
                page.getTotalElements(),    // 총 데이터 개수
                page.getNumber(),           // 현재 페이지 번호
                page.getTotalPages()        // 총 페이지 수
        );
    }

}
