public class passwordStrengthChecker {

    private int score;
    private int countSpecialChars;
    private boolean[] checkList;

    // Constructor for initializing the score
    public passwordStrengthChecker() {
        this.score = 0;
        this.countSpecialChars = 0;
    }

    // Getter method for score
    public int getScore() {
        return score;
    }

    // Method to check the password strength
    public void checkPassword(String password) {
        try {
            if (password == null || password.isEmpty()) {
                throw new IllegalArgumentException("Password cannot be null or empty.");
            }

            checkList = new boolean[5];
            countSpecialChars = 0;

            if (password.length() >= 8) {
                score += 10;
                checkList[0] = true;
            }
            if (password.length() >= 12) {
                score += 10;
            }

            for (char c : password.toCharArray()) {
                if (!Character.isLetter(c)) {
                    countSpecialChars++;
                }
            }

            for (char c : password.toCharArray()) {
                if (Character.isDigit(c)) { // Check if number
                    checkList[1] = true;
                } else if (Character.isLowerCase(c)) { // Check if lowercase
                    checkList[2] = true;
                } else if (Character.isUpperCase(c)) { // Check if uppercase
                    checkList[3] = true;
                } else if (!Character.isLetterOrDigit(c)) { // Check if special character
                    checkList[4] = true;
                    countSpecialChars++;
                }
            }

            score += countSpecialChars * 5;

        } catch (IllegalArgumentException e) {
            System.err.println("Input Error: " + e.getMessage());
            score = 0; // Reset score if the input is invalid
        } catch (Exception e) {
            System.err.println("Unexpected error occurred: " + e.getMessage());
        }
    }

    public boolean isStrongEnough(String inputPassword) {
        checkPassword(inputPassword);
        try {
            if (score >= 20 && checkList[0] && checkList[1] && checkList[2] && checkList[3] && checkList[4]) {
                return true;
            } else if (!checkList[0]) {
                System.out.println("Password must contain at least 8 characters.");
            } else if (!checkList[1]) {
                System.out.println("Password must contain at least one digit.");
            } else if (!checkList[2]) {
                System.out.println("Password must contain at least one lowercase character.");
            } else if (!checkList[3]) {
                System.out.println("Password must contain at least one uppercase character.");
            } else if (!checkList[4]) {
                System.out.println("Password must contain at least one special character.");
            }
        } catch (Exception e) {
            System.err.println("Unexpected error in isStrongEnough method: " + e.getMessage());
        }
        return false;
    }

    public String getPasswordStrengthLevel(String inputPassword) {
        try {
            checkPassword(inputPassword);
            int strengthScore = getScore();

            if (strengthScore >= 50) {
                return "High";
            } else if (strengthScore >= 30) {
                return "Medium";
            } else if (strengthScore >= 20) {
                return "Low";
            } else {
                return "Invalid";
            }
        } catch (Exception e) {
            System.err.println("Error calculating password strength level: " + e.getMessage());
            return "Error";
        }
    }

    // public String validateForFrontend(String inputPassword) {
    // checkPassword(inputPassword);
    // int strengthScore = getScore();
    // if (){
    // return "Password is not strong enough.";
    // } else if (!inputPassword.equals(correctPassword)) {
    // return "Password is incorrect.";
    // }
    // return "Password is valid and strong enough.";
    // }

    public static void main(String[] args) {
        passwordStrengthChecker checker = new passwordStrengthChecker();

        // Test cases
        String password = "Welcomee1$";

        try {
            checker.checkPassword(password);
            System.out.println("Password Strength Score: " + checker.getScore());
            System.out.println("Is password strong enough? " + checker.isStrongEnough(password));
            System.out.println("Password Strength Level: " + checker.getPasswordStrengthLevel(password));
        } catch (Exception e) {
            System.err.println("Unexpected error in main method: " + e.getMessage());
        }
    }
}
