import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class PasswordGenerator { // At least 8 characters, 1 uppercase, 1 lowercase, 1 digit, 1 special character
    public static String generatePassword() {
        try {
            // Define character sets
            String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String lowerCase = "abcdefghijklmnopqrstuvdxwz";
            String numbers = "1234567890";
            String special = "!@#$%^&*()-_=+<>?/[]{}|~";
            String allCharacters = upperCase + lowerCase + numbers + special;

            // Initialize SecureRandom
            SecureRandom random = new SecureRandom();

            // Ensure initial password meets the minimum requirements
            StringBuilder newPass = new StringBuilder();
            newPass.append(upperCase.charAt(random.nextInt(upperCase.length())));
            newPass.append(lowerCase.charAt(random.nextInt(lowerCase.length())));
            newPass.append(numbers.charAt(random.nextInt(numbers.length())));
            newPass.append(special.charAt(random.nextInt(special.length())));

            // Generate the remaining characters (password length: 8-20)
            int passLength = random.nextInt(4, 20); // Add between 4 and 20 characters

            for (int i = 0; i < passLength; i++) {
                newPass.append(allCharacters.charAt(random.nextInt(allCharacters.length())));
            }

            String unshuffledPassword = newPass.toString();

            // Shuffle the characters for added randomness
            List<Character> chars = new ArrayList<>();
            for (char c : unshuffledPassword.toCharArray()) {
                chars.add(c);
            }
            Collections.shuffle(chars, random);

            // Build the shuffled password
            StringBuilder shuffledPassword = new StringBuilder();
            for (char c : chars) {
                shuffledPassword.append(c);
            }

            return shuffledPassword.toString();

        } catch (Exception e) {
            System.err.println("Error occurred during password generation: " + e.getMessage());
            return "Error: Unable to generate password.";
        }
    }

    public static void main(String[] args) {
        try {
            String password = generatePassword();
            if (password.startsWith("Error")) {
                System.out.println(password); // Display error message if something went wrong
            } else {
                System.out.println("Generated Password: " + password);
            }
        } catch (Exception e) {
            System.err.println("Unexpected error in main method: " + e.getMessage());
        }
    }
}
