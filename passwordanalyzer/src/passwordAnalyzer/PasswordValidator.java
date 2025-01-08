public class PasswordValidator {
    private String correctPassword;
    private int minimumStrengthScore;
    private passwordStrengthChecker strengthChecker;


public PasswordValidator(String correctPassword, int minimumStrengthScore){
    this.correctPassword = correctPassword;
    this.minimumStrengthScore = minimumStrengthScore;
    this.strengthChecker = new passwordStrengthChecker();
}

public boolean isPasswordValidat(String inputPassword) {
    strengthChecker.checkPassword(inputPassword);
    int strengthScore =strengthChecker.getScore();
    boolean isStrongEnough = strengthScore >= minimumStrengthScore;
    return isStrongEnough && inputPassword.equals(correctPassword);
}

// For integration with a React frontend, we can add methods to expose validation results
    public String validateForFrontend(String inputPassword) {
        strengthChecker.checkPassword(inputPassword);
        int strengthScore = strengthChecker.getScore();
        boolean isStrongEnough = strengthScore >= minimumStrengthScore;

        if (!isStrongEnough) {
            return "Password is not strong enough.";
        } else if (!inputPassword.equals(correctPassword)) {
            return "Password is incorrect.";
        }

        return "Password is valid and strong enough.";
    }
}
