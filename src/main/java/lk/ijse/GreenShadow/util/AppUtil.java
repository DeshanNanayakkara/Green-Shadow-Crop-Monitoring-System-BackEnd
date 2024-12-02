package lk.ijse.GreenShadow.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class AppUtil {
    public static String generateId(String prefix) {
        return prefix + "-" + UUID.randomUUID();
    }

    public static String toBase64Pic(MultipartFile image) {
        try{
            byte[] picBytes = image.getBytes();
            return Base64.getEncoder().encodeToString(picBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date getCurrentDateTime() {
        return new Date();
    }
}
