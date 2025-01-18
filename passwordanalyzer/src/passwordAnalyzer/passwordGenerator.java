public class passwordGenerator {
    public static String generatePassword() {
        try {
            // Define character sets
            String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String lowerCase = "abcdefghijklmnopqrstuvwxyz";
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
            int passLength = random.nextInt(13) + 8; // Password length between 8 and 20

            for (int i = 4; i < passLength; i++) { // Start from 4 as we already added 4 characters
                newPass.append(allCharacters.charAt(random.nextInt(allCharacters.length())));
            }

            // Shuffle the characters for added randomness
            List<Character> chars = new ArrayList<>();
            for (char c : newPass.toString().toCharArray()) {
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
