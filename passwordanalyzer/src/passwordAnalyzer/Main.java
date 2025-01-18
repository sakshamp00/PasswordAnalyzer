import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println(
                    "Would you like to type in your own password? (Y for Yes, N if you'd like the system to generate one for you)");
            String inputChoice = scanner.nextLine().toUpperCase();
            String newPassword = "";
            boolean madePassword = false;

            while (!madePassword) {
                if (inputChoice.equals("Y")) {
                    System.out.println("Please enter a password:");
                    newPassword = scanner.nextLine();
                    System.out.println("Password: " + newPassword);
                    madePassword = true;
                } else if (inputChoice.equals("N")) {
                    newPassword = passwordGenerator.generatePassword();
                    System.out.println("Generated Password: " + newPassword);
                    madePassword = true;
                } else {
                    System.out.println("Invalid choice. Please enter 'Y' for Yes or 'N' for No:");
                    inputChoice = scanner.nextLine().toUpperCase();
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

            System.out.print("Enter a password to validate: ");
            String inputPassword = scanner.nextLine();

            System.out.println("Password Strength Level: " + strengthChecker.getPasswordStrengthLevel(inputPassword));

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
