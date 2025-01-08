public class passwordStrengthChecker {

    private int score;
    private int countSpecialChars;
    private boolean[] checkList;
  
    // constructor for initializing the score
    public passwordStrengthChecker() {
      this.score = 0;
      this.countSpecialChars = 0;
    }
  
    // getter method for score
    public int getScore() {
      return score;
    }
  
    // method to check the password strength
    public void checkPassword(String password) {
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
          System.out.println(c);
          countSpecialChars++;
        }
      }
      for (char c : password.toCharArray()) {
        if (Character.isDigit(c)) { // check if number
          checkList[1] = true;
        } else if (Character.isLowerCase(c)) { // check if lowercase
          checkList[2] = true;
        } else if (Character.isUpperCase(c)) { // check if uppercase
          checkList[3] = true;
        } else if (!Character.isLetterOrDigit(c)) { // check if special character
          checkList[4] = true;
          countSpecialChars++;
        }
      }
      score += countSpecialChars * 5;
    }
  
    public boolean isStrongEnough() {
      if (score >= 20 && checkList[0] && checkList[1] && checkList[2] && checkList[3] && checkList[4]) {
        return true;
      } else if (!checkList[0]) {
        System.out.println("Password must contain at least 8 characters");
      } else if (!checkList[1]) {
        System.out.println("Password must contain at least one digit");
      } else if (!checkList[2]) {
        System.out.println("Password must contain at least one lowercase character");
      } else if (!checkList[3]) {
        System.out.println("Password must contain at least one uppercase character");
      } else if (!checkList[4]) {
        System.out.println("Password must contain at least one special character");
      }
      return false;
    }
  
    public static void main(String[] args) {
      passwordStrengthChecker checker = new passwordStrengthChecker();
      String password = "Welcomee1$";
      checker.checkPassword(password);
      System.out.println("Password Strength Score: " + checker.getScore());
      System.out.println("Is password strong enough? " + checker.isStrongEnough());
    }
  }
  
