package br.assistentediscente.alexa.config;

import br.assistentediscente.alexa.handlers.custom.HandlerFactory;
import br.assistentediscente.alexa.main.ADStreamHandler;
import com.amazon.ask.servlet.ServletConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServlet;

@Configuration
public class WebConfig {

    @Value("${skillid}")
    private String skillId;

    @Autowired
    private HandlerFactory handlerFactory;

    @Bean
    public ServletRegistrationBean<HttpServlet> adAlexaServlet() {

        loadProperties();

        ADStreamHandler adStreamHandler = createAdStreamHandler(skillId, handlerFactory);


        ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
        servRegBean.setServlet(adStreamHandler);
        servRegBean.addUrlMappings("/alexa/*");
        servRegBean.setLoadOnStartup(1);
        return servRegBean;
    }
    private ADStreamHandler createAdStreamHandler(String skillId, HandlerFactory handlerFactory) {
        return new ADStreamHandler(skillId, handlerFactory);
    }

    private void loadProperties() {
        System.setProperty(ServletConstants.TIMESTAMP_TOLERANCE_SYSTEM_PROPERTY, "3600000");
        System.setProperty(ServletConstants.DISABLE_REQUEST_SIGNATURE_CHECK_SYSTEM_PROPERTY, "true");
    }
}
