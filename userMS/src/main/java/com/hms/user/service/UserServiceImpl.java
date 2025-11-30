package com.hms.user.service;

import com.hms.user.clients.ProfileClient;
import com.hms.user.dto.Roles;
import com.hms.user.dto.UserDTO;
import com.hms.user.entity.User;
import com.hms.user.exception.HMSException;
import com.hms.user.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("userService")
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final ProfileClient profileClient;


    @Override
    public void registerUser(UserDTO userDTO) throws HMSException {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Long profileId =null;
        if(userDTO.getRole().equals(Roles.DOCTOR)){
            profileId=profileClient.addDoctor(userDTO);
        }else if(userDTO.getRole().equals(Roles.PATIENT)){
            profileId=profileClient.addPatient(userDTO);
        }
        System.out.println("Profile id " + profileId);
        userDTO.setProfileId(profileId);
        userRepository.save(userDTO.toUserEntity());
    }

    @Override
    public UserDTO loginUser(UserDTO userDTO) throws HMSException {
        User user = userRepository.findByEmail(userDTO.getEmail()).orElseThrow(() -> new HMSException("USER_NOT_FOUND"));
        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            throw new HMSException("INVALID_CREDENTIALS");
        }
        user.setPassword(null);//  ---> making it null so that the password do not go to frontend
        return user.toUserDTO();
    }

    @Override
    public UserDTO getUserById(Long id) throws HMSException {
        return userRepository.findById(id).orElseThrow(() -> new HMSException("USER_NOT_FOUND")).toUserDTO();
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public UserDTO getUserByEmail(String email) throws HMSException {
        return userRepository.findByEmail(email).orElseThrow(() -> new HMSException("USER_NOT_FOUND")).toUserDTO();
    }
}
