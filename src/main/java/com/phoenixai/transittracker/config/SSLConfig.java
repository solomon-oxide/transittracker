package com.phoenixai.transittracker.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.File;

/**
 * SSL Configuration for the Transit Tracker application.
 * This class provides SSL configuration for both development and production environments.
 */
@Configuration
public class SSLConfig {

    /**
     * Custom SSL configuration for production environments.
     * This allows for more flexible SSL certificate management.
     */
    @Bean
    @Profile("production")
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> sslCustomizer() {
        return factory -> {
            Ssl ssl = new Ssl();
            
            // Configure SSL properties
            ssl.setEnabled(true);
            ssl.setKeyStoreType("PKCS12");
            ssl.setKeyStorePassword(System.getenv("SSL_KEYSTORE_PASSWORD"));
            ssl.setKeyStore(System.getenv("SSL_KEYSTORE_PATH"));
            ssl.setKeyAlias(System.getenv("SSL_KEY_ALIAS"));
            
            // Optional: Configure trust store for client certificates
            if (System.getenv("SSL_TRUSTSTORE_PATH") != null) {
                ssl.setTrustStore(System.getenv("SSL_TRUSTSTORE_PATH"));
                ssl.setTrustStorePassword(System.getenv("SSL_TRUSTSTORE_PASSWORD"));
                ssl.setTrustStoreType("PKCS12");
            }
            
            // SSL protocol configuration
            ssl.setProtocol("TLS");
            ssl.setEnabledProtocols(new String[]{"TLSv1.2", "TLSv1.3"});
            
            factory.setSsl(ssl);
            factory.setPort(8443);
        };
    }

    /**
     * Development SSL configuration using self-signed certificates.
     */
    @Bean
    @Profile("dev")
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> devSslCustomizer() {
        return factory -> {
            Ssl ssl = new Ssl();
            
            // Use classpath resources for development
            ssl.setEnabled(true);
            ssl.setKeyStoreType("PKCS12");
            ssl.setKeyStorePassword("changeit");
            ssl.setKeyStore("classpath:ssl/transittracker.p12");
            ssl.setKeyAlias("transittracker");
            
            factory.setSsl(ssl);
            factory.setPort(8443);
        };
    }
}
