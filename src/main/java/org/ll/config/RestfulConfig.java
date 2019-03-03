package org.ll.config;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestfulConfig {

	private static final Logger log = LoggerFactory.getLogger(RestfulConfig.class);

	@Value("${keystore.clientCert.file}")
    private String clientKeystore;
    
    @Value("${keystore.clientCert.password}")
    private String clientKeystorePwd;
    
    @Value("${keystore.clientCert.alias}")
    private String clientKeystoreAlias;
    
    @Autowired
    private OAuth2ProtectedResourceDetails apiClient;
    
    @Autowired
    private OAuth2ClientContext oauth2ClientContext;

	@Bean
    OAuth2RestOperations restTemplate(RestTemplateBuilder builder) {
    	
    	return builder.additionalCustomizers(new RestTemplateCustomizer(){

			@Override
			public void customize(RestTemplate restTemplate) {
				Certificate cert = null;
				try {
					KeyStore keystore = KeyStore.getInstance("JKS");
					keystore.load(new ClassPathResource(clientKeystore).getInputStream(), clientKeystorePwd.toCharArray());
					cert = keystore.getCertificate(clientKeystoreAlias);
					
					// header
					List<Header> headers = new ArrayList<>(1);
			    	headers.add(new BasicHeader("clientCert", new String(cert.getEncoded())));
			    	
					restTemplate.setRequestFactory(
							new HttpComponentsClientHttpRequestFactory(
								HttpClientBuilder.create()
									.setDefaultHeaders(Collections.unmodifiableList(headers))
								.build()
								));
				} catch (KeyStoreException | NoSuchAlgorithmException
						| CertificateException | IOException e) {
					log.error("fail to add client cert to header!!", e);
				}
		    	
				
			}
    		
    	}).configure(new OAuth2RestTemplate(apiClient, oauth2ClientContext));
    }
    
}
