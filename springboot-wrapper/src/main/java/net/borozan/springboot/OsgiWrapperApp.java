package net.borozan.springboot;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Springboot OSGI werapper application
 * 
 * @author jurica
 *
 */
@Configuration
@ImportResource({"file:${CONFIG_PATH}/springboot-wrapper.xml"}) // ClassPathXmlApplicationContext will not work with servlets
public class OsgiWrapperApp {

    /*
     * main 
     */
    public static void main(String[] args) {
        
        new SpringApplicationBuilder(OsgiWrapperApp.class)
        .web(WebApplicationType.SERVLET) // use WebApplicationType.NONE if no servlets are required
        .run(args);   
    }
    
    /**
     * Required for servlets to work
     */
    @Bean
    public ServletWebServerFactory servletContainer() {

        return new JettyServletWebServerFactory(); // alternatively TomcatServletWebServerFactory()
    }
}
