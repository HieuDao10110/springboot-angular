package com.example.springboot.controller;

import com.example.springboot.dto.LoginDTO;
import com.example.springboot.dto.SignupDTO;
import com.example.springboot.dto.TokenDTO;
import com.example.springboot.model.User;
import com.example.springboot.security.JwtToUserConverter;
import com.example.springboot.security.TokenGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;

@RestController
@RequestMapping(value = "/api/auth")
@Slf4j
public class AuthController {

    @Autowired
    UserDetailsManager userDetailsManager;

    @Autowired
    TokenGenerator tokenGenerator;

    @Autowired
    DaoAuthenticationProvider daoAuthenticationProvider;

    @Autowired
    @Qualifier("jwtRefreshTokenAuthProvider")
    JwtAuthenticationProvider refreshTokenAuthProvider;

    @Autowired
    JwtToUserConverter jwtToUserConverter;

    Jwt jwt;

    @PostMapping(value = "/register")
    public ResponseEntity register(@RequestBody SignupDTO signupDTO){
        User user = new User(signupDTO.getUsername(), signupDTO.getPassword());
        userDetailsManager.createUser(user);
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user, signupDTO.getPassword(), Collections.EMPTY_LIST);

//        log.info("auth2 id from authController {}", user.getAuthId());
        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO){
//        User user = new User(loginDTO.getUsername(), loginDTO.getPassword());
        Authentication authentication = daoAuthenticationProvider.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(loginDTO.getUsername(), loginDTO.getPassword()));
        TokenDTO token = tokenGenerator.createToken(authentication);
        String accessTokenKey = "access-token";
        String accessTokenValue = token.getAccessToken();

        String refreshTokenKey = "refresh-token";
        String refreshTokenValue = token.getRefreshToken();

        String urlKey = "url-login";
        String urlValue = "/api/users/login";

        String exposeHeadersKey = "Access-Control-Expose-Headers";
        String exposeHeadersValue = accessTokenKey + " ," + refreshTokenKey + " ," + urlKey;

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(accessTokenKey, accessTokenValue);
        responseHeaders.set(refreshTokenKey, refreshTokenValue);
        responseHeaders.set(exposeHeadersKey, exposeHeadersValue);
        responseHeaders.set(urlKey, urlValue);

        return ResponseEntity.ok().headers(responseHeaders).body("");
    }

    @PostMapping("/token")
    public ResponseEntity token(@RequestBody TokenDTO tokenDTO) {
        Authentication authentication = refreshTokenAuthProvider.authenticate(new BearerTokenAuthenticationToken(tokenDTO.getRefreshToken()));
        Jwt jwt = (Jwt) authentication.getCredentials();
        // check if present in db and not revoked, etc

        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

    @GetMapping("/re")
    public RedirectView re() {
        return new RedirectView("http://localhost:4200");
    }
}
