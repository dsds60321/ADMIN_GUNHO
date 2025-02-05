package dev.gunho.user.entity;

import dev.gunho.global.entity.BaseTimeEntity;
import dev.gunho.user.constant.InviteStatus;
import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.*;

import java.time.LocalDateTime;

@Description("친구 초대 관리 테이블")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Table(name = "invite")
public class Invite extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    // 초대 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inviter_id", nullable = false)
    private User inviter;

    // 초대받은 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitee_id")
    private User invitee;

    // 대상 이메일
    @Column(length = 64)
    private String email;

    @Enumerated(EnumType.STRING)
    private InviteStatus status;

    @Column(nullable = false)
    private LocalDateTime invitationTime;

    @Column(length = 64, nullable = false)
    private String token;

    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = InviteStatus.PENDING;
        }

        if (this.invitationTime == null) {
            this.invitationTime  = LocalDateTime.now();
        }
    }

    public void updateInvite(User invitee, InviteStatus status) {
        this.invitee = invitee; // 초대받은 유저
        this.status = status;   // 초대 상태
    }

}
