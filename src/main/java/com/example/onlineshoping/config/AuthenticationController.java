package com.example.onlineshoping.config;

import com.example.onlineshoping.entity.User;
import com.example.onlineshoping.responce.ApiResponse;
import com.example.onlineshoping.exception.UserExitByEmailException;
import com.example.onlineshoping.repo.UserRepository;
import com.example.onlineshoping.responce.AuthResponse;
import com.example.onlineshoping.wrapperclasses.AuthResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class  AuthenticationController {

    @Autowired
    private  UserRepository userRepository;
    private final  AuthenticationManager authenticationManager;
    @Autowired
    private  JwtService jwtService;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserExitByEmailException("User", "email", user.getEmail(), "1005");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        AuthResponse authResponse = new AuthResponse();
        BeanUtils.copyProperties(savedUser, authResponse);

        var jwtToken = jwtService.generateToken(savedUser);
        authResponse.setToken(jwtToken);

        ApiResponse apiResponse = new ApiResponse();
        AuthResponseWrapper authResponseWrapper = new AuthResponseWrapper();
        authResponseWrapper.setUser(authResponse);
        apiResponse.setData(authResponseWrapper);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow();

            String jwtToken = jwtService.generateToken(user);
            AuthResponse authResponse=new AuthResponse();
            BeanUtils.copyProperties(user,authResponse);
            authResponse.setToken(jwtToken);
            AuthResponseWrapper authResponseWrapper=new AuthResponseWrapper();
            authResponseWrapper.setUser(authResponse);
            ApiResponse apiResponse=new ApiResponse();
            apiResponse.setData(authResponseWrapper);

            return ResponseEntity.ok(apiResponse);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).build();
        } catch (AuthenticationException e) {
            return ResponseEntity.status(500).build();
        }
    }

}



