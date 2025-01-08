import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class passwordEncryptor {

    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 128; // AES key size

    private SecretKey secretKey;

    public passwordEncryptor() throws Exception {
        this.secretKey = generateKey();
    }

    /**
     * Constructor that uses an existing key.
     *
     * @param keyBytes A byte array representing the AES key.
     */
    public passwordEncryptor(byte[] keyBytes) {
        this.secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
    }

    /**
     * Encrypts a plain text password using AES encryption.
     *
     * @param plainText The password to encrypt.
     * @return The encrypted password in Base64 format.
     */
    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypts an AES-encrypted password.
     *
     * @param encryptedText The encrypted password in Base64 format.
     * @return The decrypted plain text password.
     */
    public String decrypt(String encryptedText) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    /**
     * Generates a new AES key.
     *
     * @return A SecretKey for AES encryption.
     */
    private SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(KEY_SIZE);
        return keyGenerator.generateKey();
    }

    /**
     * Returns the AES key as a Base64-encoded string (for saving or sharing).
     *
     * @return The Base64-encoded AES key.
     */
    public String getKeyAsString() {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * Creates an AES key from a Base64-encoded string.
     *
     * @param keyString The Base64-encoded AES key.
     * @return A SecretKey object.
     */
    public static SecretKey keyFromString(String keyString) {
        byte[] decodedKey = Base64.getDecoder().decode(keyString);
        return new SecretKeySpec(decodedKey, ALGORITHM);
    }

    public static void main(String[] args) {
        try {
            // Initialize encryptor
            passwordEncryptor encryptor = new passwordEncryptor();

            // Password to encrypt
            String password = "MySecurePassword123!";

            // Encrypt the password
            String encryptedPassword = encryptor.encrypt(password);
            System.out.println("Encrypted Password: " + encryptedPassword);

            // Decrypt the password
            String decryptedPassword = encryptor.decrypt(encryptedPassword);
            System.out.println("Decrypted Password: " + decryptedPassword);

            // Save and reuse the key
            String keyString = encryptor.getKeyAsString();
            System.out.println("AES Key (Base64): " + keyString);

            // Reinitialize with the same key
            SecretKey savedKey = passwordEncryptor.keyFromString(keyString);
            passwordEncryptor newEncryptor = new passwordEncryptor(savedKey.getEncoded());

            // Verify encryption and decryption with the reused key
            String reEncryptedPassword = newEncryptor.encrypt(password);
            System.out.println("Re-Encrypted Password: " + reEncryptedPassword);
            String reDecryptedPassword = newEncryptor.decrypt(reEncryptedPassword);
            System.out.println("Re-Decrypted Password: " + reDecryptedPassword);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
