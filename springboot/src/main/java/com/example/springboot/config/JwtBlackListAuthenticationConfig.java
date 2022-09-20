package com.example.springboot.config;

import com.example.springboot.service.BlackListingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@Slf4j
public class JwtBlackListAuthenticationConfig implements Filter {

    @Value("${jwt.key}")
    private String jwtKey;

    @Autowired
    private BlackListingService blackListingService;

    @Autowired
    private UserRequestScopedBean userRequestScopedBean;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURI();
        if(!path.contains("/api/auth")){
            log.info("filter uri contains ........................");
            String token = req.getHeader("Authorization");
            if(token != null){
                token = token.substring(7);
            }else {
                token = null;

            }
            log.info("filter after ....................."+token);
            String blackListedToken = blackListingService.getJwtBlackList(token);
            log.info("blacklistedtoken .................. {}", blackListedToken);
            if(blackListedToken != null){
                log.error("JwtInterceptor: Token is blacklisted");
                throw new AccessDeniedException("access denied");
            }else {
                userRequestScopedBean.setJwt(token);
            }
        }
        chain.doFilter(request, response);
    }
}
