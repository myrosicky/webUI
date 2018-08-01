package org.ll.service.common;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;

public class MyOAuth2RestTemplate extends OAuth2RestTemplate {
    
    private static final Logger log = LoggerFactory.getLogger(MyOAuth2RestTemplate.class);

    private volatile List<String> online_user = new ArrayList<String>();
    
    @Autowired
    TokenStore tokenStore;
    
    public MyOAuth2RestTemplate(OAuth2ProtectedResourceDetails resource) {
        super(resource);
    }

    public MyOAuth2RestTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context) {
        super(resource, context);
    }
    
    
    
//
//    @Override
//    protected OAuth2AccessToken acquireAccessToken(OAuth2ClientContext oauth2Context) throws UserRedirectRequiredException {
//        AccessTokenRequest accessTokenRequest = oauth2Context.getAccessTokenRequest();
//        if (accessTokenRequest != null) {
//            OAuth2Authentication  oAuth2Authentication = tokenStore.readAuthentication(getAccessToken());
//            if(oAuth2Authentication != null 
//                && !online_user.contains(oAuth2Authentication.getName())
//                ){
//                if(log.isDebugEnabled())
//                    log.debug("remove online_user [" + oAuth2Authentication.getName() + "]");
//                accessTokenRequest.getHeaders().remove("Authorization");
//            }
//        }
//        return super.acquireAccessToken(oauth2Context);
//    }

//    @Override
//    protected ClientHttpRequest createRequest(URI uri, HttpMethod method) throws IOException {
//        if(log.isDebugEnabled())
//            log.debug("online_user [" + online_user.toArray(new String[online_user.size()]) + "]");
//        ClientHttpRequest req = super.createRequest(uri, method);
//        OAuth2Authentication  oAuth2Authentication = tokenStore.readAuthentication(getAccessToken());
//        if(oAuth2Authentication != null 
//            && !online_user.contains(oAuth2Authentication.getName())
//            && uri.getPath().contains("/oauth/authorize")
//            ){
//            if(log.isDebugEnabled())
//                log.debug("remove online_user [" + oAuth2Authentication.getName() + "]");
//            req.getHeaders().remove("Authorization");
//        }
//        return req;
//    }
//
//    @Override
//    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor)
//        throws RestClientException {
//        T result = super.doExecute(url, method, requestCallback, responseExtractor);
//        if(url.getPath().contains("/oauth/authorize")){
//            OAuth2Authentication  oAuth2Authentication = tokenStore.readAuthentication(getAccessToken());
//            if(log.isDebugEnabled())
//                log.debug("add online_user [" + oAuth2Authentication.getName() + "]");
//            online_user.add(oAuth2Authentication.getName());
//            
//        }else if(url.getPath().contains("/logout")){
//            OAuth2Authentication  oAuth2Authentication = tokenStore.readAuthentication(getAccessToken());
//            if(log.isDebugEnabled())
//                log.debug("add online_user [" + oAuth2Authentication.getName() + "]");
//            online_user.remove(oAuth2Authentication.getName());
//        }
//        return result;
//    }
    
    
}
