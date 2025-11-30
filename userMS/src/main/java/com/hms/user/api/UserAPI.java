package com.hms.user.api;

import com.hms.user.dto.LoginDTO;
import com.hms.user.dto.ResponseDTO;
import com.hms.user.dto.UserDTO;
import com.hms.user.exception.HMSException;
import com.hms.user.jwt.JwtUtil;
import com.hms.user.service.UserService;
import com.hms.user.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Validated
@RequiredArgsConstructor
public class UserAPI {

    private final UserService userService;

    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody @Valid UserDTO userDTO) throws HMSException{ // ---->
        // @Valid is used to validate the userDTO
        userService.registerUser(userDTO);
        return new ResponseEntity<ResponseDTO>(new ResponseDTO("Account Created"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody @Valid LoginDTO loginDTO ) throws  HMSException{
       try{
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),
            loginDTO.getPassword()));
       }catch (AuthenticationException e){
        throw new HMSException("INVALID_CREDENTIALS");
       }
       final UserDetails userDetails=userDetailsService.loadUserByUsername(loginDTO.getEmail());
       final String jwt=jwtUtil.generateToken(userDetails);
       return new ResponseEntity<>(jwt,HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return new ResponseEntity<>("Test",HttpStatus.OK);
    }
    

}
