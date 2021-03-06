package org.ll.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@EnableDiscoveryClient
public class WebInitContext implements ServletContextInitializer {
    
    private static final Logger log = LoggerFactory.getLogger(WebInitContext.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.addListener(org.springframework.web.context.request.RequestContextListener.class);
        servletContext.addFilter("requestContextFilter", org.springframework.web.filter.RequestContextFilter.class);
//        ServletRegistration  servletRegistration  = servletContext.getServletRegistration("default");
//        log.debug("servletRegistration:" + servletRegistration);
//        log.debug("servletRegistration.getInitParameter(\"readonly\"): " + servletRegistration.getInitParameter("readonly"));
//        servletRegistration.setInitParameter("readonly", "false");
    }
}
