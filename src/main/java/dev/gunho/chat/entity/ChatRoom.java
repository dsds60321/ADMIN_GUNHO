package dev.gunho.chat.entity;

import dev.gunho.chat.constant.ChatStatus;
import dev.gunho.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chat_room")
public class ChatRoom {

    private static final Logger log = LoggerFactory.getLogger(ChatRoom.class);
    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    // 방 이름
    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private ChatStatus status;

    // 방장 유저
    @ManyToOne
    @JoinColumn(name = "host_user_id", nullable = false) // 필수 설정
    private User host;


    @ManyToMany
    @JoinTable(
            name = "chat_room_user", // 중간 테이블 이름 지정
            joinColumns = @JoinColumn(name = "chat_room_id"), // 현재 엔티티의 외래 키
            inverseJoinColumns = @JoinColumn(name = "user_id") // 연관된 엔티티의 외래 키
    )
    @Builder.Default
    private List<User> users = new ArrayList<>();


    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = ChatStatus.ACTIVE;
        }
    }

    // 기타 메서드
    public void addUser(User user) {
        this.users.add(user);
        user.getChatRooms().add(this); // 양방향 관계 설정
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ChatRoom chatRoom = (ChatRoom) o;
        return idx == chatRoom.idx && Objects.equals(title, chatRoom.title) && status == chatRoom.status && Objects.equals(host, chatRoom.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idx, title, status, host);
    }
}
