package com.rashidi.billing.notifier.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Controller for online api.
 *
 * @author Mina Rashidi
 */
@Controller
public class DocConfig {

    @RequestMapping(value = "/api-doc")
    public String api() {
        return "redirect:/swagger-ui.html";
    }

    @Bean
    public Docket ProfileApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Notifier")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rashidi.billing.notifier.controller"))
                .paths(Predicates.not(PathSelectors.regex("/billings.*")))
                .build()
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Billing Notification API Guide")
                .description("Detailed information on how to use Billing Notification API.")
                .termsOfServiceUrl("http://www.minarashidi.com")
                .contact(new Contact("Mina Rashidi", "linkedin.com/in/minarashidi", "mina.rashidi.86@gmail.com"))
                .build();
    }
}
