package vn.elca.training;

import org.h2.server.web.WebServlet;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

/**
 * @author gtn
 *
 */
@SpringBootApplication(scanBasePackages = "vn.elca.training")
//@ComponentScan(basePackageClasses = {
//        AbstractApplicationController.class,
//        ApplicationMapper.class,
//        ProjectService.class,
//        TaskValidator.class
//})

@PropertySource({"classpath:/application.properties", "classpath:/messages.properties"})

public class ApplicationWebConfig extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ApplicationWebConfig.class);
    }

    @Bean
    public ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
        registrationBean.addUrlMappings("/h2console/*");
        return registrationBean;
    }
}
