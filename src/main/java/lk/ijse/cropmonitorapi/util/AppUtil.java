package lk.ijse.cropmonitorapi.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class AppUtil {
    public static String generateId(String prefix) {
        return prefix + "-" + UUID.randomUUID();
    }

    public static String toBase64Pic(MultipartFile image) throws IOException {
            byte[] picBytes = image.getBytes();
            return Base64.getEncoder().encodeToString(picBytes);

    }

    public static Date getCurrentDateTime() {
        return new Date();
    }
}
