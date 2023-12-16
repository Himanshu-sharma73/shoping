package com.example.onlineshoping.entity;


import com.example.onlineshoping.entity.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "userdetails")
@Builder
public class User implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;


    @NotNull(message = "User Name should not bee null")
    @Size(min = 3,message = "User name should have at least three Character")
    private String name;

    @Min(value = 1000000000l,message = "Mobile number must be at least 10 digit")
    @Max(value = 9999999999l,message = "Mobile number must not exceed 10 digit")
    private long mobileNo;

	@NotBlank(message = "Email address cannot be blank")
	@Email(message = "Invalid email address")
	private String email;

	@NotBlank(message = "Address should not be Blank")
	@NotNull(message = "Address should not be Null")
	private String address;
	@NotBlank(message = "Password should not be Blank")
	@NotNull(message = "Password should not be Null")
	private String password;


	@Enumerated(EnumType.STRING)
	private Role role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}