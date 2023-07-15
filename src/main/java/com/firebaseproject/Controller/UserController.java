package com.firebaseproject.Controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @GetMapping
    public String hello (){
        return "Hello Brother";
    }
    @PostMapping("signUp")
    public UserRecord Register (@RequestBody UserRecord.CreateRequest userRecord) throws FirebaseAuthException {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        UserRecord userRecord1=firebaseAuth.createUser(userRecord);
        Map<String, Object> roles = new HashMap<>();
        roles.put("role", "user");
        firebaseAuth.setCustomUserClaims(userRecord1.getUid(),roles);
        return firebaseAuth.getUser(userRecord1.getUid());
    }
    @PostMapping("signUpAdmin")
    public UserRecord RegisterAdmin (@RequestBody UserRecord.CreateRequest userRecord) throws FirebaseAuthException {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        UserRecord userRecord1=firebaseAuth.createUser(userRecord);
        Map<String, Object> roles = new HashMap<>();
        roles.put("role", "ROLE_ADMIN");
        firebaseAuth.setCustomUserClaims(userRecord1.getUid(),roles);
        return firebaseAuth.getUser(userRecord1.getUid());
    }
}
