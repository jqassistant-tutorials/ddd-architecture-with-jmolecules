package org.jqassistant.demo.architecture.hexagonal.user.config.thymeleaf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
class UserTemplateResolver {

    @Bean
    ITemplateResolver uiTemplateResolver() {
        ClassLoaderTemplateResolver userTemplateResolver = new ClassLoaderTemplateResolver();
        userTemplateResolver.setPrefix("/user/adapters/primary/ui/");
        userTemplateResolver.setSuffix(".html");
        userTemplateResolver.setTemplateMode(TemplateMode.HTML);
        userTemplateResolver.setCharacterEncoding("UTF-8");
        userTemplateResolver.setOrder(1);
        userTemplateResolver.setCheckExistence(true);
        return userTemplateResolver;
    }

}
