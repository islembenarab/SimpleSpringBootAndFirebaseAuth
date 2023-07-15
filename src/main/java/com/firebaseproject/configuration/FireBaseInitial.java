package com.firebaseproject.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;

import java.io.IOException;

@Configuration
public class FireBaseInitial {
    @Bean
    public FirebaseAuth firebaseAuth ()  {
        return FirebaseAuth.getInstance();
    }
    @PostConstruct
    public void initialFireBase() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("C:\\Users\\islem\\IdeaProjects\\FireBaseProject\\src\\main\\resources\\fireflutter-e6741-firebase-adminsdk-odwdj-b85f50ecda.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://fireflutter-e6741-default-rtdb.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);
    }
}
