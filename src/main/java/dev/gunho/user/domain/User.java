package dev.gunho.toooy.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.gunho.toooy.appointment.entity.UserAppointment;
import dev.gunho.toooy.global.entity.BaseTimeEntity;
import dev.gunho.toooy.user.constant.UserRole;
import dev.gunho.toooy.user.dto.ResultStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity(name = "user")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    @Column(name = "user_id", nullable = false, unique = true, length = 32)
    private String userId;

    @Column(nullable = false, length = 128)
    private String password;

    @Column(length = 64)
    private String email;

    @Column(length = 32)
    private String nick;

    @Column(length = 4)
    private String os;

    @Column(length = 128)
    private String uuid;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Auth auth;

    // 관계 추가
    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAppointment> userAppointments = new ArrayList<>();

    // 유저 승패 통계
    public ResultStatus getCalculateResultStatus() {
        if (userAppointments == null || userAppointments.isEmpty()) {
            return new ResultStatus(0, 0); // 비어 있는 경우 기본값 반환
        }

        long wins = getResultStatus(true);
        long losses = getResultStatus(false);

        return new ResultStatus(wins, losses);
    }


    private long getResultStatus(boolean isWin) {
        return userAppointments.stream()
                .map(Optional::ofNullable) // Optional 변환
                .flatMap(Optional::stream) // Null 제거
                .filter(appointments -> appointments.getResultStatus() != null)
                .filter(appointments -> appointments.getResultStatus().equals(isWin))
                .count();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return idx == that.idx;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idx);
    }
}
