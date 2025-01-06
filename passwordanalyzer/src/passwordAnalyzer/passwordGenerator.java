import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class passwordGenerator { // At least 8 characters, 1 uppercase, 1 lowercase, 1 digit, 1 special character
    public static String generatePassword() { // makes a new password
        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCase = "abcdefghijklmnopqrstuvdxwz";
        String numbers = "1234567890";
        String special = "!@#$%^&*()-_=+<>?/[]{}|~";
        String allCharacters = upperCase + lowerCase + numbers + special; // useful later

        SecureRandom random = new SecureRandom(); // whatever

        StringBuilder newPass = new StringBuilder(); // new empty string which we will add characters to
        newPass.append(upperCase.charAt(random.nextInt(upperCase.length())));
        newPass.append(lowerCase.charAt(random.nextInt(lowerCase.length())));
        newPass.append(numbers.charAt(random.nextInt(numbers.length())));
        newPass.append(special.charAt(random.nextInt(special.length())));

        int passLength = random.nextInt(4, 20); // integer for how many new characters will be added on the end once
                                                // initial requirements are met

        for (int i = 0; i < passLength; i++) { // at this point, password requirements have been met. So now you're just
                                               // adding a bunch of random characters to the end, between 4 and 20
                                               // times.
            newPass.append(allCharacters.charAt(random.nextInt(allCharacters.length())));
        }
        String unshuffledPassword = newPass.toString();

        // now we can shuffle everything just for an added layer of security
        List<Character> chars = new ArrayList<>(); // new empty ArrayList
        for (char c : unshuffledPassword.toCharArray()) {
            chars.add(c); // add characters from the previous unshuffled password to this new
                          // shuffledpassword
        }

        Collections.shuffle(chars, random); // shuffle it

        StringBuilder shuffledPassword = new StringBuilder(); // make a new StringBuilder, this string is what we'll be
                                                              // returned by this method (converted to String)

        for (char c : chars) {
            shuffledPassword.append(c); // add them to shuffledPassword
        }

        return shuffledPassword.toString(); // convert to string and return

    }

    public static void main(String[] args) { // can remove this if needed, was just used for testing
        String password = generatePassword();
        System.out.println("Generated Password: " + password);
    }

}