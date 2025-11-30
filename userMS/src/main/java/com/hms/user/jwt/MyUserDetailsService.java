package com.hms.user.jwt;

import com.hms.user.dto.UserDTO;
import com.hms.user.exception.HMSException;
import com.hms.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            UserDTO userDTO = userService.getUserByEmail(email);
            return new CustomUserDetails(userDTO.getId(), userDTO.getEmail(), userDTO.getPassword(),
                    userDTO.getRole(),
                    userDTO.getName(), userDTO.getEmail(),userDTO.getProfileId(), null);
        } catch (HMSException e) {
           e.printStackTrace();
        }
        return null;
    }
}
