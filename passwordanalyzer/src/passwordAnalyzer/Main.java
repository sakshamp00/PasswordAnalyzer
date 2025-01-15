import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try { // ERROR HANDLING
            System.out.println(
                    "Would you like to type in your own password? (Y for Yes, N if you'd like the system to generate one for you)");
            String inputChoice = scanner.nextLine().toUpperCase(); // Normalize input to uppercase for consistency
            String newPassword = "";
            boolean madePassword = false; // To keep track of when to end the password input loop

            while (!madePassword) {
                if (inputChoice.equals("Y")) { // User creates their own password
                    System.out.println("Please enter a password:");
                    newPassword = scanner.nextLine();
                    System.out.println("Password: " + newPassword);
                    madePassword = true;
                } else if (inputChoice.equals("N")) { // System creates password for user
                    newPassword = passwordGenerator.generatePassword();
                    System.out.println("Generated Password: " + newPassword);
                    madePassword = true;
                } else {
                    System.out.println("Invalid choice. Please enter 'Y' for Yes or 'N' for No:");
                    inputChoice = scanner.nextLine().toUpperCase(); // Update the input for the next iteration
                }
            }

            passwordStrengthChecker strengthChecker = new passwordStrengthChecker();
            strengthChecker.checkPassword(newPassword);
            System.out.println("Password Strength Score: " + strengthChecker.getScore());
            System.out.println("Is the password strong enough? " + strengthChecker.isStrongEnough(newPassword));

            passwordEncryptor encryptor = new passwordEncryptor();
            String encryptedPassword = encryptor.encrypt(newPassword);
            System.out.println("Encrypted Password: " + encryptedPassword);
            String decryptedPassword = encryptor.decrypt(encryptedPassword);
            System.out.println("Decrypted Password: " + decryptedPassword);

            System.out.print("Enter a password to validate: "); // yall can figure this part out because I'm not sure
                                                                // what you're planning here, idk if you want like a
                                                                // login system or what
            String inputPassword = scanner.nextLine();

            // String validationMessage = strengthChecker.isStrongEnough(newPassword);
            // System.out.println(validationMessage);

            System.out.println("Password Strength Level: " + strengthChecker.getPasswordStrengthLevel(inputPassword));

        } catch (Exception e) { // ERROR HANDLING
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
