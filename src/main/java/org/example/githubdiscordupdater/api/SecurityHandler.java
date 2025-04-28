package org.example.githubdiscordupdater.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.nio.charset.StandardCharsets;

public class SecurityHandler {
    public static boolean isSignatureValid(String signature, String payload) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new File("secrets.json"));
            String SECRET = root.get("ApiKey").asText();
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKey);
            byte[] expectedHash = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            String expectedSignature = "sha256=" + bytesToHex(expectedHash);
            return expectedSignature.equals(signature);
        } catch (Exception e) {
            throw new RuntimeException("Error validating signature", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    public static boolean isValidApiKey(String apiKey) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new File("secrets.json"));
            String SECRET = root.get("ApiKey").asText();
            if (SECRET.equals(apiKey)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error validating signature", e);
        }
    }
}
