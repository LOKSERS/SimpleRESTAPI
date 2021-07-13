package com.example.demo.services;

import com.example.demo.Entity.User;
import com.example.demo.Entity.enums.ERole;
import com.example.demo.Exceptions.UserExistException;
import com.example.demo.payload.response.requests.SignUpRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JWTTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public static final Logger log = LoggerFactory.getLogger(JWTTokenProvider.class);

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(SignUpRequest userIn){
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setName(userIn.getFirstname());
        user.setLastname(userIn.getLastname());
        user.setUsername(userIn.getUsername());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.getRole().add(ERole.ROLE_USER);

        try{
            log.info("Saving new user");
            return userRepository.save(user);
        }catch (Exception ex){
            log.error("Error during registration, {}", ex.getMessage());
            throw new UserExistException("User "+ user.getUsername()+" is exist");
        }
    }

}
