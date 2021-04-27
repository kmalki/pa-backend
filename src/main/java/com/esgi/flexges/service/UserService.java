package com.esgi.flexges.service;

import com.esgi.flexges.model.UserApp;
import com.esgi.flexges.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void createUser(UserApp user) throws Exception {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if(userRepository.findByEmail(user.getEmail()) == null){
            userRepository.addUser(user);
        }
        else{
            userRepository.updateUser(user);
        }
    }

}
