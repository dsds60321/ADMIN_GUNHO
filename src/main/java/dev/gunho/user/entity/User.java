package dev.gunho.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.gunho.global.entity.BaseTimeEntity;
import dev.gunho.rule.entity.Rule;
import dev.gunho.stock.entity.Stock;
import dev.gunho.user.constant.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
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
    private String status;

    @Column(length = 128)
    private String uuid;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Auth auth;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<Stock> stocks = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<Rule> rules = new ArrayList<>();

    @OneToMany(mappedBy = "inviter", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<Invite> sentInvites = new ArrayList<>(); // 내가 보낸 초대

    @OneToMany(mappedBy = "invitee", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<Invite> receivedInvites = new ArrayList<>(); // 내가 받은 초대


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
