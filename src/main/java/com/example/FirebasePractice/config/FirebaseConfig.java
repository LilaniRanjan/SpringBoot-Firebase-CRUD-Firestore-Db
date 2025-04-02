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

    /**
     * This method configures and initializes Firebase connection for Firestore.
     * It loads the Firebase service account key and sets up Firebase options
     * for authenticating and interacting with Firestore.
     *
     * @return Firestore instance to interact with the Firestore database.
     * @throws IOException if there is an issue reading the service account file.
     */
    @Bean
    public Firestore firestore() throws IOException {
        // Path to the default Firebase service account key JSON file
        String service_acc_key = "E:/Gray Corp/FirebasePractice/src/main/resources/firebase-service-account.json";
        String service_key;

        // Check if the environment variable for service account key is set
        if (System.getenv("SERVICE_KEY_PATH") != null)
            service_key = System.getenv("SERVICE_KEY_PATH"); // Use environment variable if present
        else
            service_key = service_acc_key; // Fall back to the default path

        // Load the service account key from the specified file path
        FileInputStream fileInputStream = new FileInputStream(service_key);

        // Build Firebase options with credentials from the service account key
        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(fileInputStream)) // Set authentication credentials
                .build();

        // Initialize FirebaseApp if it hasn't been initialized yet (prevents multiple initializations)
        if (FirebaseApp.getApps().isEmpty()){
            FirebaseApp.initializeApp(firebaseOptions); // Initialize the Firebase app with the provided options
        }

        // Return the Firestore client instance to interact with Firestore
        return FirestoreClient.getFirestore();
    }
}
