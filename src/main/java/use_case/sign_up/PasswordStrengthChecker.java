package use_case.sign_up;


/**
 * This class contains a checker for the strength of the user's password
 * */
public class PasswordStrengthChecker {
    /**
     * Checks the strength of the password
     * @param username the name of the user
     * @param email the email of the user
     * @param password the password of the user
     * @throws IllegalArgumentException the message that the password is weak
     * */
    public static void checkStrength(String username, String email, String password) throws IllegalArgumentException{
        checkWithWeakPasswords(password);
        checkWithUsername(username, password);
        checkWithEmail(email, password);
        checkCharacters(password);
    }

    /**
     * Checks the strength of the user's password with a small set of weak passwords.
     * Checks if the password contains these weak passwords.
     * @param password the password of the user
     * @throws IllegalArgumentException the message that the password is weak
     * */
    private static void checkWithWeakPasswords (String password) throws IllegalArgumentException {
        if (password.contains("12345") || password.contains("54321") || password.contains("11111")){
            throw new IllegalArgumentException("Your password contains weak sequences of characters.");
        }
        if (password.toLowerCase().contains("password") || password.toLowerCase().contains("drowssap") || password.toLowerCase().contains("admin")){
            throw new IllegalArgumentException("Your password contains weak sequences of characters.");
        }
        if (password.toLowerCase().contains("qwerty") || password.toLowerCase().contains("ytrewq")){
            throw new IllegalArgumentException("Your password contains weak sequences of characters.");
        }
    }

    /**
     * Checks the strength of the user's password with the username
     * Checks if the password contains the username or a part of the username
     * @param username the name of the user
     * @param password the password of the user
     * @throws IllegalArgumentException the message that the password is weak
     * */
    private static void checkWithUsername(String username, String password) throws IllegalArgumentException{
        String usernameLower = username.toLowerCase();
        if (password.toLowerCase().contains(usernameLower)){
            throw new IllegalArgumentException("Your password contains your username.");
        }
        for (int i = 0; i < usernameLower.length() - 4; i++){
            String curser = ""
                    + usernameLower.charAt(i)
                    + usernameLower.charAt(i+1)
                    + usernameLower.charAt(i+2)
                    + usernameLower.charAt(i+3);

            if (password.toLowerCase().contains(curser)){
                throw new IllegalArgumentException("Your password contains your username.");
            }
        }
    }

    /**
     * Checks the strength of the user's password with the user's email
     * Checks if the password contains the email or a part of the user's email
     * @param email the email of the user
     * @param password the password of the user
     * @throws IllegalArgumentException the message that the password is weak
     * */
    private static void checkWithEmail(String email, String password) throws IllegalArgumentException{
        String emailSection = email.toLowerCase().substring(email.toLowerCase().indexOf('@'));
        if (password.toLowerCase().contains(emailSection)){
            throw new IllegalArgumentException("Your password contains your username.");
        }
        for (int i = 0; i < emailSection.length() - 4; i++){
            String curser = ""
                    + emailSection.charAt(i)
                    + emailSection.charAt(i+1)
                    + emailSection.charAt(i+2)
                    + emailSection.charAt(i+3);

            if (password.toLowerCase().contains(curser)){
                throw new IllegalArgumentException("Your password contains your username.");
            }
        }
    }

    /**
     * Checks the strength of the user's password to see if it contains every type of characters
     * @param password the password of the user
     * @throws IllegalArgumentException the message that the password is weak
     * */
    private static void checkCharacters(String password) throws IllegalArgumentException{
        password = password.toLowerCase();
        for (int i = 0; i < password.length(); i++){
            char c = password.charAt(i);
            if (!Character.isDigit(c)){
                if (!Character.isLetter(c)){
                    if (!"!@#$%^&*(){}[]||?/".contains("" + c)){
                        throw new IllegalArgumentException("Your password is weak.");
                    }
                }
            }
        }
    }
}
