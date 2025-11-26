package com.devansh.erp_lite.dto;

import lombok.Data;

@Data
public class AuthenticationDTO {
    private String email;
    private String password;
    private String name;
    private String role;
    private Boolean isNew;
}
