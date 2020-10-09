package com.cffilter.backend.controllers;



import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cffilter.backend.models.ERole;
import com.cffilter.backend.models.Role;
import com.cffilter.backend.models.User;
import com.cffilter.backend.payload.request.LoginRequest;
import com.cffilter.backend.payload.request.SignupRequest;
import com.cffilter.backend.payload.response.JwtResponse;
import com.cffilter.backend.payload.response.MessageResponse;
import com.cffilter.backend.repository.RoleRepository;
import com.cffilter.backend.repository.UserRepository;
import com.cffilter.backend.security.jwt.JwtUtils;
import com.cffilter.backend.security.service.UserDetailsImpl;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item->item.getAuthority())
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles,userDetails.getHandle()));
		
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest){
		
		if(userRepository.existsUserByUsername(signupRequest.getUsername())) {
			return ResponseEntity
					.badRequest().
					body(new MessageResponse("Error : Username is already taken!"));
		}
		
		if(userRepository.existsUserByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Error : Email is already in use!"));
		}
		
		
		User user = new User(signupRequest.getUsername(),signupRequest.getEmail(),
				passwordEncoder.encode(signupRequest.getPassword()),signupRequest.getHandle());
		
		Set<String> strRoles = signupRequest.getRole();
		
		Set<Role> roles = new HashSet<>();
		
		if(strRoles==null) {
			Role role = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(()->new RuntimeException("Error : Role is not found"));
			
			roles.add(role);
		}
		else {
			strRoles.forEach(role->{
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
								.orElseThrow(()->new RuntimeException("Error : Role is not found"));
					roles.add(adminRole);
					break;

				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
									.orElseThrow(()->new RuntimeException("Error : Role is not found"));
					roles.add(userRole);
					break;
				}
			});
		}
		
		user.setRoles(roles);
		userRepository.save(user);
		
		return ResponseEntity.ok(new MessageResponse("User Registered Successfully!!!"));	
		
	}
}
