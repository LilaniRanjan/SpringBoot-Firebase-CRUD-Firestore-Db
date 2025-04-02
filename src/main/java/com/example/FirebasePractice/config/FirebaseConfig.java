package com.example.FirebasePractice.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Bean
    public Firestore firestore() throws IOException{
        String service_acc_key = "E:/Gray Corp/FirebasePractice/src/main/resources/firebase-service-account.json";
        String service_key;

        if (System.getenv("SERVICE_KEY_PATH") != null)
            service_key = System.getenv("SERVICE_KEY_PATH");
        else service_key = service_acc_key;
        FileInputStream fileInputStream = new FileInputStream(service_key);
        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(fileInputStream))
                .build();
        if (FirebaseApp.getApps().isEmpty()){
            FirebaseApp.initializeApp(firebaseOptions);
        }
        return FirestoreClient.getFirestore();
    }
}
