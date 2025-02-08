package mg.working.cryptomonnaie.utils;

import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;  
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

public class ImgurUploader {
    private static final String IMGUR_CLIENT_ID = "0980b9756ddc695"; 
    private static final String IMGUR_CLIENT_SECRET = "77b038e6044dcc5a01e592ced6bd477d88454d88"; 
    private static final String UPLOAD_URL = "https://api.imgur.com/3/image";

     public static String uploadImage(MultipartFile file) {
        try {
            System.out.println("Uploading file: " + file.getOriginalFilename() + ", Size: " + file.getSize() + " bytes");

            System.out.println("Converting file to Base64...");
            String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
            System.out.println("File converted to Base64");

            HttpHeaders headers = new HttpHeaders(); 
            headers.set("Authorization", "Client-ID " + IMGUR_CLIENT_ID);
            System.out.println("Headers set with Client-ID");

            Map<String, String> body = new HashMap<>();
            body.put("image", base64Image);
            System.out.println("Request body prepared");

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
            System.out.println("HttpEntity created");

            RestTemplate restTemplate = new RestTemplate();
            System.out.println("RestTemplate initialized");

            System.out.println("Sending POST request to Imgur...");
            ResponseEntity<Map> response = restTemplate.exchange(UPLOAD_URL, HttpMethod.POST, entity, Map.class);
            System.out.println("POST request sent");

            Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
            String imageUrl = (String) data.get("link");
            System.out.println("Image uploaded successfully. Image URL: " + imageUrl);

            return imageUrl;

        } catch (HttpClientErrorException e) {
            // Handle the error if the request fails
            System.err.println("Error during the request: " + e.getMessage());
            e.printStackTrace();
            return "Error uploading image to Imgur";
        } catch (Exception e) {
            // Handle other exceptions
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
            return "An error occurred";
        }
    }
}
