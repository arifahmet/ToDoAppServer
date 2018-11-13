package com.arif.backenddemo.controller;


import com.arif.backenddemo.domain.User;
import com.arif.backenddemo.model.AuthenticationRequest;
import com.arif.backenddemo.model.AuthenticationResponse;
import com.arif.backenddemo.model.RegisterRequest;
import com.arif.backenddemo.security.TokenUtils;
import com.arif.backenddemo.service.UserService;
import com.arif.backenddemo.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController{


  @Value("${backenddemo.token.header}")
  private String tokenHeader;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenUtils tokenUtils;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  private UserService userService;

  @RequestMapping(value = "/login",method = RequestMethod.POST)
  public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {

    // Perform the authentication
    Authentication authentication = this.authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        authenticationRequest.getUsername(), authenticationRequest.getPassword()
      )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // Reload password post-authentication so we can generate token

       UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
       String token = this.tokenUtils.generateToken(userDetails);

       // Return the token
       return ResponseEntity.ok(new AuthenticationResponse(token));
  }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity registerUser(@RequestBody @Validated RegisterRequest registerRequest) throws AuthenticationException {

        User user = new User(registerRequest);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        User newUser = new User(user.getName(), user.getUsername(), hashedPassword, "USER");

        user = userService.createUser(newUser);
        user.setPassword("");

        return new ResponseEntity(user, HttpStatus.OK);
    }

}
