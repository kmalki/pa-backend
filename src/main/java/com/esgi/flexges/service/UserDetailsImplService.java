package com.esgi.flexges.service;

import com.esgi.flexges.model.UserApp;
import com.esgi.flexges.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsImplService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(UserDetailsImplService.class);

    public UserDetailsImplService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException  {
        UserApp applicationUser = null;
        try {
            applicationUser = userRepository.findByEmail(username);
        } catch (ExecutionException | InterruptedException e) {
            logger.error(e.getMessage());
        }
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(applicationUser.getEmail(),
                applicationUser.getPassword(), emptyList());
    }
}