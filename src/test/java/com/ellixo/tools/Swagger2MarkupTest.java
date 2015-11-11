package com.ellixo.tools;

import com.ellixo.healthcare.Application;
import io.github.robwin.markup.builder.MarkupLanguage;
import io.github.robwin.swagger2markup.Swagger2MarkupConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest
@WebAppConfiguration
public class Swagger2MarkupTest {

    @Test
    public void convertRemoteSwaggerToMarkdown() throws IOException {
        Swagger2MarkupConverter.from("http://localhost:8080/v2/api-docs")
                .withMarkupLanguage(MarkupLanguage.MARKDOWN).build()
                .intoFolder("src/docs/markdown/generated");

        // Then validate that three Markdown files have been created
        String[] files = new File("src/docs/markdown/generated").list();
        System.out.println(files.length);
    }
}