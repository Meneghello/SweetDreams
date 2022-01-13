package com.SweetDreams.sweetDreams.Controller;


import com.SweetDreams.sweetDreams.Security.JWTUtil;
import com.SweetDreams.sweetDreams.Security.UserSS;
import com.SweetDreams.sweetDreams.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value="/auth")
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping(value = "/refreshToken")
    public ResponseEntity<Object> refreshToken(HttpServletResponse response){
        UserSS userSS = UserService.authenticated();
        String token = jwtUtil.generateToken(userSS.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
