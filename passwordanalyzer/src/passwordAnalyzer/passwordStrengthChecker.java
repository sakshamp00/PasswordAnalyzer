public class passwordStrengthChecker {

    private int score;
    private boolean[] checkList;

    public passwordStrengthChecker() {
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public void checkPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }

        checkList = new boolean[5];
        score = 0;

        if (password.length() >= 8) {
            score += 10;
            checkList[0] = true;
        }
        if (password.length() >= 12) {
            score += 10;
        }

        int specialCharCount = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                checkList[1] = true;
            } else if (Character.isLowerCase(c)) {
                checkList[2] = true;
            } else if (Character.isUpperCase(c)) {
                checkList[3] = true;
            } else if (!Character.isLetterOrDigit(c)) {
                checkList[4] = true;
                specialCharCount++;
            }
        }

        score += specialCharCount * 5;
    }

    public boolean isStrongEnough(String inputPassword) {
        checkPassword(inputPassword);
        if (score >= 20 && checkList[0] && checkList[1] && checkList[2] && checkList[3] && checkList[4]) {
            return true;
        }
        if (!checkList[0]) System.out.println("Password must contain at least 8 characters.");
        if (!checkList[1]) System.out.println("Password must contain at least one digit.");
        if (!checkList[2]) System.out.println("Password must contain at least one lowercase character.");
        if (!checkList[3]) System.out.println("Password must contain at least one uppercase character.");
        if (!checkList[4]) System.out.println("Password must contain at least one special character.");
        return false;
    }

    public String getPasswordStrengthLevel(String inputPassword) {
        checkPassword(inputPassword);
        if (score >= 50) return "High";
        if (score >= 30) return "Medium";
        if (score >= 20) return "Low";
        return "Invalid";
    }

    public static void main(String[] args) {
        passwordStrengthChecker checker = new passwordStrengthChecker();
        String password = "Welcomee1$";

        checker.checkPassword(password);
        System.out.println("Password Strength Score: " + checker.getScore());
        System.out.println("Is password strong enough? " + checker.isStrongEnough(password));
        System.out.println("Password Strength Level: " + checker.getPasswordStrengthLevel(password));
    }
}
