package dev.gunho.rule.entity;

import dev.gunho.global.entity.BaseTimeEntity;
import dev.gunho.stock.entity.Stock;
import dev.gunho.user.entity.User;
import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.*;

@Description("Rule 테이블")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Table(name = "rule")
public class Rule extends BaseTimeEntity {

    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    private String id;

    // 증가
    private Double buyPrice; // 매도 발생 금액 기준

    private Double buyPercentage; // 매도 발생 퍼센트 기준

    // 매도
    private Double sellPrice; // 매도 발생 금액 기준

    private Double sellPercentage; // 매도 발생 퍼센트 기준

    // Stock와의 N:1 관계 추가
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_idx") // 외래 키
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx") // 외래 키
    private User user;

}
