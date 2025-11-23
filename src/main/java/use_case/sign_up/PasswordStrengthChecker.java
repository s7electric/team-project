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
        checkLength(password);
        checkWithWeakPasswords(password);
        checkWithUsername(username, password);
        checkWithEmail(email, password);
        checkCharacters(password);
    }

    /**
     * Checks the strength of the password with its length
     * @param password the password of the user
     * @throws IllegalArgumentException if the password is shorter than 10 characters
     */
    private static void checkLength (String password) throws IllegalArgumentException{
        if (password.length() < 10) {
            throw new IllegalArgumentException("Your password is shorter than 10 characters");
        }
    }

    /**
     * Checks the strength of the user's password with a small set of weak passwords.
     * Checks if the password contains these weak passwords.
     * @param password the password of the user
     * @throws IllegalArgumentException the message that the password is weak
     * */
    private static void checkWithWeakPasswords (String password) throws IllegalArgumentException {
        int numCharSeq = 3;
        String errorMessage = "Your password contains weak sequences of characters.";
        checkSeqChars(password, "123456789",  errorMessage, numCharSeq);
        checkSeqChars(password, "987654321", errorMessage , numCharSeq);
        checkSeqChars(password, "111", errorMessage , numCharSeq);
        checkSeqChars(password, "abcdefghijklmnopqrstuvwxyz", errorMessage , numCharSeq);
        checkSeqChars(password, "zyxwvutsrqponmlkjihgfedcba", errorMessage , numCharSeq);
        numCharSeq = 4;
        checkSeqChars(password, "password", errorMessage , numCharSeq);
        checkSeqChars(password, "drowssap", errorMessage , numCharSeq);
        checkSeqChars(password, "qwertyuiopasdfghjklzxcvbnm", errorMessage , numCharSeq);
        checkSeqChars(password, "mnbvcxzlkjhgfdsapoiuytrewq", errorMessage , numCharSeq);
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
        String errorMessage = "Your password contains your username or \n" +
                "a 4-character sequence from the username.";
        int numCharSeq = 4;
        checkSeqChars(password, usernameLower, errorMessage, numCharSeq);
    }

    /**
     * Checks the strength of the user's password with the user's email
     * Checks if the password contains the email or a part of the user's email
     * @param email the email of the user
     * @param password the password of the user
     * @throws IllegalArgumentException the message that the password is weak
     * */
    private static void checkWithEmail(String email, String password) throws IllegalArgumentException{
        String emailSection = email.toLowerCase().substring(0, email.toLowerCase().indexOf('@'));
        String errorMessage = "Your password contains your email or \n" +
                "a 4-character sequence from the email.";
        int numCharSeq = 4;
        checkSeqChars(password, emailSection, errorMessage, numCharSeq);
    }

    /**
     * Checks the strength of the password with n-character sequences from a string
     * to see if the password contains the sequence
     * @param password the password of the user
     * @param fullString the string that n-character sequences come from
     * @param errorMessage the error message that will be thrown if the password contains the sequence
     * @param numCharSeq the number of characters that the sequence should have
     * @throws IllegalArgumentException if the password contains the n-character sequence
     * */
    private static void checkSeqChars(String password, String fullString, String errorMessage, int numCharSeq) throws IllegalArgumentException {
        if (password.toLowerCase().contains(fullString)) {
            throw new IllegalArgumentException(errorMessage + " (" + fullString + ") ");
        }
        for (int i = 0; i < fullString.length() - numCharSeq; i++) {
            StringBuilder curser = new StringBuilder();
            for (int n = 0; n < numCharSeq; n++) {
                curser.append(fullString.charAt(i + n));
            }
            if (password.toLowerCase().contains(curser.toString())) {
                throw new IllegalArgumentException(errorMessage + " (" + curser + ")");
            }
        }
    }

    /**
     * Checks the strength of the user's password to see if it contains every type of characters
     * @param password the password of the user
     * @throws IllegalArgumentException the message that the password is weak
     * */
    private static void checkCharacters(String password) throws IllegalArgumentException{
        boolean hasDigit = false;
        boolean hasLowerCase = false;
        boolean hasUpperCase = false;
        boolean hasSpecial = false;
        for (int i = 0; i < password.length(); i++){
            char c = password.charAt(i);
            if (Character.isDigit(c)){
                hasDigit = true;
            }
            if (Character.isLowerCase(c)){
                hasLowerCase = true;
            }
            if (Character.isUpperCase(c)){
                hasUpperCase = true;
            }
            if ("!@#$%^&*()_+{}|?/[]-=~:<>,.;".contains("" + c)) {
                hasSpecial = true;
            }
        }
        if (!hasDigit || !hasLowerCase || !hasUpperCase || !hasSpecial) {
            throw new IllegalArgumentException("Your password should have a digit, \n" +
                    "a lower case letter, an upper case letter, \n" +
                    "and a special character like: \n" +
                    "!@#$%^&*()_+{}|?/[]-=~:<>,.;");
        }
    }
}
