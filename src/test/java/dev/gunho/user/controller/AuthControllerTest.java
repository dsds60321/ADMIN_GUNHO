package dev.gunho.toooy.user.controller;

import dev.gunho.toooy.BaseControllerTest;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // 테스트 실행 순서 지정
class AuthControllerTest extends BaseControllerTest {

    @Order(1)
    @Test
    @Transactional
    @DisplayName("회원가입 성공 테스트")
    void 회원가입성공() throws Exception {

        // given
        String userJson = """
        {
          "userId": "%s",
          "password": "%s",
          "email": "%s@naver.com",
          "nick": "게스트"
        }
        """;


        userJson = String.format(userJson, "test", "1234", "dsds601");

        // when - then
        mockMvc.perform(RestDocumentationRequestBuilders.post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())  // 가입 성공 시 HTTP 200을 기대
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.log())  // 요청 및 응답을 더 자세히 콘솔에 로그로 출력
                .andDo(MockMvcResultHandlers.print())  // 요청 및 응답을 콘솔에 출력
                .andDo(document("sign-up-success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Order(2) // 두 번째로 실행될 테스트
    @Test
    @Transactional
    @DisplayName("로그인 성공 테스트")
    void 로그인성공() throws Exception {
        // given - 회원가입
        String userJson = """
        {
          "userId": "%s",
          "password": "%s",
          "email": "%s@naver.com",
          "nick": "게스트"
        }
        """;
        // 로그인 요청 데이터
        String loginJson = """
        {
          "userId": "%s",
          "password": "%s"
        }
        """;

        userJson = String.format(userJson, "test", "1234", "dsds601");
        // 회원가입
        mockMvc.perform(RestDocumentationRequestBuilders.post("/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk());

        // when - then
        loginJson = String.format(loginJson, "test", "1234");
        mockMvc.perform(RestDocumentationRequestBuilders.post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())  // 로그인 성공 시 HTTP 200 기대
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.log())  // 요청 및 응답을 상세히 로그로 출력
                .andDo(MockMvcResultHandlers.print())  // 콘솔에 출력
                .andDo(document("sign-in-success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()))); // REST Docs 문서화
    }

}