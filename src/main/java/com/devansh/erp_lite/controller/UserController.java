package com.devansh.erp_lite.controller;

import com.devansh.erp_lite.dto.AuthenticationDTO;
import com.devansh.erp_lite.dto.UserResponseDTO;
import com.devansh.erp_lite.models.Role;
import com.devansh.erp_lite.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/authenticate")
    public UserResponseDTO authenticate(@RequestBody AuthenticationDTO authenticationDTO){
        return userService.authenticate(authenticationDTO);
    }

    @GetMapping("/get-roles")
    public List<Role> getRoles(){
        return userService.getRoles();
    }
}
