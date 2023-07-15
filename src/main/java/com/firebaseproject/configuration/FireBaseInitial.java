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
                new FileInputStream("path/to/file.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("url_DataBase")
                .build();

        FirebaseApp.initializeApp(options);
    }
}
