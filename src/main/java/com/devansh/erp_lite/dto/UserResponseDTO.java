package com.devansh.erp_lite.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String role;
    private List<String> permissions;
    private String token;
}
