package com.example.demo.controller;

import com.example.demo.dto.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*")
@RestController
public class LogoutController {

    @RequestMapping(value = "/apiLogout", method = RequestMethod.GET)
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            return new ResponseEntity<>(new ResponseMessage("Đăng xuất thành công!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseMessage("Đăng xuất thất bại"), HttpStatus.BAD_REQUEST);
    }
}
