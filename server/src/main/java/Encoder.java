import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Encoder {
    public byte[] doHash(String pwd) {
        byte[] hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(pwd.getBytes(StandardCharsets.UTF_8));
        } catch (Exception exception) {
            Server.logger.error(exception.getMessage());
        }
        return hash;
    }
}
