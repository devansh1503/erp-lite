package com.devansh.erp_lite.services;

import com.devansh.erp_lite.dto.AuthenticationDTO;
import com.devansh.erp_lite.dto.PaginatedResponse;
import com.devansh.erp_lite.dto.UserResponseDTO;
import com.devansh.erp_lite.models.Permission;
import com.devansh.erp_lite.models.Role;
import com.devansh.erp_lite.models.User;
import com.devansh.erp_lite.repositories.RoleRepo;
import com.devansh.erp_lite.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final JwtService jwtService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public UserResponseDTO authenticate(AuthenticationDTO authenticationDTO) {
        if(authenticationDTO.getIsNew()){
            return register(authenticationDTO);
        }
        User user = userRepo.findByEmail(authenticationDTO.getEmail())
                .orElseThrow(()-> new RuntimeException("User not found"));
        if(!bCryptPasswordEncoder().matches(user.getPassword(), authenticationDTO.getPassword())){
            throw new RuntimeException("Wrong password");
        }
        return createUserReponse(user);
    }

    public UserResponseDTO register(AuthenticationDTO authenticationDTO) {
        User user = new User();
        user.setEmail(authenticationDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder().encode(authenticationDTO.getPassword()));
        user.setFullName(authenticationDTO.getName());
        user.setRole(roleRepo.findByRole(authenticationDTO.getRole()));

        return createUserReponse(userRepo.save(user));
    }

    public UserResponseDTO createUserReponse(User user){
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setFullName(user.getFullName());
        userResponseDTO.setId(user.getUserId());
        userResponseDTO.setRole(user.getRole().getTitle());
        userResponseDTO.setPermissions(user.getRole().getPermissions().stream().map(Permission::getTitle).collect(Collectors.toList()));
        userResponseDTO.setToken(jwtService.generateToken(user));

        return userResponseDTO;
    }

    public List<Role> getRoles(){
        return roleRepo.findAll();
    }

    public PaginatedResponse<UserResponseDTO> getPaginatedUsers(int page, int size, String sortBy, String sortDirection){
        Sort sort = sortDirection.equals("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<User> userPage = userRepo.findAll(pageable);
        List<UserResponseDTO>dtoList = userPage.getContent().stream().map(this::createUserReponse).collect(Collectors.toList());
        PaginatedResponse<UserResponseDTO> response = new PaginatedResponse<>();
        response.setItems(dtoList);
        response.setPage(userPage.getNumber());
        response.setPageSize(userPage.getSize());
        response.setTotal(userPage.getTotalElements());
        response.setTotalPages(userPage.getTotalPages());

        return response;
    }

}
