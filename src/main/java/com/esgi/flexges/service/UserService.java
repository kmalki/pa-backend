package com.esgi.flexges.service;

import com.esgi.flexges.model.Login;
import com.esgi.flexges.model.UserApp;
import com.esgi.flexges.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

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

    public UserApp getUser(Login user) throws ExecutionException, InterruptedException {
        UserApp userApp = userRepository.findByEmail(user.getLogin());
        if(userApp != null){
            if(userApp.getPassword().equals(bCryptPasswordEncoder.encode(user.getPassword()))){
                return userApp;
            }
        }
        return null;
    }

}
