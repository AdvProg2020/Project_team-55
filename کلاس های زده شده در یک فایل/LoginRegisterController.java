package Controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginRegisterController {

    //for  password and  userName
    public static boolean checkPattern(String string) {
        Pattern pattern = Pattern.compile("\\w");
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    public static boolean checkNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("(\\+98|0)?9\\d{9}");
        Matcher matcher = pattern.matcher(phoneNumber);
        if (matcher.find()) {
            return true;
        }
        return false;
    }


    public static boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile("  ([a-zA-Z0-9_\\-\\.]+)@gmail||yahoo\\.com");
        Matcher matcher = pattern.matcher(email);
        if (matcher.find()) {
            return true;
        }
        return false;
    }


}
