package com.example.onlineshoping.controller;

import com.example.onlineshoping.config.JwtUtils;
import com.example.onlineshoping.dto.LoginDto;
import com.example.onlineshoping.dto.UserDto;
import com.example.onlineshoping.entity.ERole;
import com.example.onlineshoping.entity.Role;
import com.example.onlineshoping.entity.User;
import com.example.onlineshoping.exception.UserExitByEmailException;
import com.example.onlineshoping.exception.UserRoleNotFoundException;
import com.example.onlineshoping.repo.RoleRepository;
import com.example.onlineshoping.repo.UserRepository;
import com.example.onlineshoping.responce.ApiResponse;
import com.example.onlineshoping.service.UserDetailsImpl;
import com.example.onlineshoping.wrapperclasses.UserWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200,http://localhost:4401")
public class  AuthenticationController {

    @Autowired
    private  UserRepository userRepository;
    private final  AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody UserDto userDto) {

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserExitByEmailException("User","email", userDto.getEmail(),"1005");
        }

        User user = new User();
        BeanUtils.copyProperties(userDto,user);
        List<String> strRoles = userDto.getRoles();
        List<Role> roles = new ArrayList<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new UserRoleNotFoundException("Role","In database","Plz add roles"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new UserRoleNotFoundException("Role","In database","Plz add roles"));
                        roles.add(adminRole);
                    }
                    case "prod" -> {
                        Role prodRole = roleRepository.findByName(ERole.ROLE_PROD)
                                .orElseThrow(() -> new UserRoleNotFoundException("Role","In database","Plz add roles"));
                        roles.add(prodRole);
                    }
                    default -> {
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new UserRoleNotFoundException("Role","In database","Plz add roles"));
                        roles.add(userRole);
                    }
                }
            });
        }
        user.setRoles(roles);
        String jwt=jwtUtils.generateJwtToken(UserDetailsImpl.build(user));
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setToken(jwt);
        User savedUser=userRepository.save(user);
        ApiResponse apiResponse=new ApiResponse();
        UserWrapper userWrapper=new UserWrapper();
        BeanUtils.copyProperties(savedUser,userDto);
        List<Role> roleList = user.getRoles();
        List<String> roleNamesList = roleList.stream()
                .map(Role::getName)
                .map(ERole::name)
                .collect(Collectors.toList());
        userDto.setRoles(roleNamesList);
        userWrapper.setUser(userDto);
        apiResponse.setData(userWrapper);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        UserDto userDto=new UserDto();
        //BeanUtils.copyProperties(userDetails,userDto);
        userDto.setName(userDetails.getUsername());
        userDto.setId(userDetails.getId());
        userDto.setEmail(userDetails.getEmail());
        userDto.setAddress(userDetails.getAddress());
        userDto.setPassword(userDetails.getPassword());
        userDto.setMobileNo(userDetails.getMobileNo());
        userDto.setToken(jwt);
        userDto.setRoles(roles);
        UserWrapper userWrapper=new UserWrapper();
        userWrapper.setUser(userDto);
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setData(userWrapper);
        return ResponseEntity.ok(apiResponse);
    }

}



