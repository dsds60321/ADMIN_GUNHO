package dev.gunho.temp;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.gunho.user.entity.QUser;
import dev.gunho.user.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static dev.gunho.user.entity.QUser.*;

@SpringBootTest
@Transactional
public class QueryDSLTest {

    @Autowired
    JPAQueryFactory queryFactory;

    @Test
    @DisplayName("유저 검색 테스트")
    public void userSearch () throws Exception{
        // given
        User user1 = queryFactory.select(user)
                .from(user)
                .where(user.idx.eq(1L)
                        .and(user.userId.eq("dsds601")))
                .fetchOne();

        // when

        // then
        Assertions.assertThat(user1.getIdx()).isEqualTo(1L);
    }
}
