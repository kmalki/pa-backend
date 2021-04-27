package com.esgi.flexges.rest;

import com.esgi.flexges.model.Enterprise;
import com.esgi.flexges.model.UserApp;
import com.esgi.flexges.repository.UserRepository;
import com.esgi.flexges.service.EnterpriseService;
import com.esgi.flexges.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/enterprise")
public class EnterpriseController {

    final static Logger logger = LoggerFactory.getLogger(EnterpriseController.class);

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/addEnterprise")
    public Enterprise addEnterprise(@RequestParam("file") MultipartFile file, @RequestParam("json") String jsonEnterprise) throws Exception {
        Enterprise enterprise = new ObjectMapper().readValue(jsonEnterprise, Enterprise.class);
        enterpriseService.configureEnterprise(file, enterprise);
        userService.createUser(new UserApp(enterprise.getAdminEmail(), enterprise.getAdminPassword(), enterprise.getName(), true));
        return enterprise;
    }

    @PostMapping("/configureEnterprise")
    public ResponseEntity<String> configureEnterprise(@RequestParam("file") MultipartFile file, @RequestBody Enterprise enterprise) throws Exception {
        enterpriseService.configureEnterprise(file, enterprise);
        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
    }

    @GetMapping("/getEmployees")
    public ResponseEntity<List<String>> getEmployees(@RequestBody Enterprise enterprise) throws Exception {
        List<String> employees = enterpriseService.getEmployees(enterprise);
        return ResponseEntity.status(HttpStatus.CREATED).body(employees);
    }

    @GetMapping("/test")
    public UserApp test() throws ExecutionException, InterruptedException {
        UserApp u = userRepository.findByEmail("molotov");
        logger.info(String.valueOf(u == null));
        return u;
    }

    @PostMapping("/addEmployees")
    public ResponseEntity<String> addEmployees(@RequestBody List<UserApp> users) throws Exception {
        enterpriseService.addEmployees(users);
        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
    }
}
