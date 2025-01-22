package dev.gunho.toooy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
public abstract class BaseControllerTest {

    protected MockMvc mockMvc; // MockMvc 테스트 객체
    protected RestDocumentationResultHandler restDocs; // RestDocs 문서화 핸들러

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.restDocs = MockMvcRestDocumentation.document(
                "{class-name}/{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        );

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(restDocs) // 항상 RestDocs 결과 기록
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 한글 깨짐 방지 필터 추가
                .build();
    }

    /**
     * JSON 템플릿의 데이터를 포맷팅하는 유틸 메서드
     */
    protected String formatJson(String jsonTemplate, Object... args) {
        return String.format(jsonTemplate, args);
    }
}