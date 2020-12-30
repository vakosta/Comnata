package tv.comnata.comnata.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import tv.comnata.comnata.entities.Cat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void index() throws Exception {
        Cat cat = new Cat(123, "Kekus", 18);

        ResultActions actions = mockMvc.perform(get("/main/{id}", cat.getId())).andDo(print());

        actions.andExpect(status().isOk());
        actions.andDo(MockMvcRestDocumentation.document("{class-name}/{method-name}"));
    }
}