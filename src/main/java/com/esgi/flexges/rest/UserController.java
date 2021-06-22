package com.esgi.flexges.rest;


import com.esgi.flexges.model.Login;
import com.esgi.flexges.model.Room;
import com.esgi.flexges.model.UserApp;
import com.esgi.flexges.repository.UserRepository;
import com.esgi.flexges.service.RoomService;
import com.esgi.flexges.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/users")
public class UserController {

    final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody UserApp user) throws Exception {
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                "ok");
    }

    @PostMapping("/login")
    public ResponseEntity<UserApp> login(@RequestBody Login user) throws Exception {
        UserApp userApp = userService.getUser(user);
        if(userApp!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(userApp);
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new UserApp());
        }
    }

    @PostMapping("/getRooms")
    public ResponseEntity<List<Room>> getRooms(@RequestBody UserApp user) throws ExecutionException, InterruptedException {
        List<Room> rooms = roomService.getRooms(user.getEnterpriseId());
        return ResponseEntity.status(HttpStatus.OK).body(rooms);
    }
}
