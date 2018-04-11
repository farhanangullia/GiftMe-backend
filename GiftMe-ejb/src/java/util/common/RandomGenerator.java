package util.common;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RandomGenerator {

    public RandomGenerator() {

    }

    public static String RandomDeliveryCode() throws NoSuchAlgorithmException {
        String deliveryCode = "";
        SecureRandom wheel = SecureRandom.getInstance("SHA1PRNG");
        char[] charset = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
        deliveryCode += "GM";
        deliveryCode += charset[wheel.nextInt(charset.length)];
        deliveryCode += charset[wheel.nextInt(charset.length)];
        deliveryCode += charset[wheel.nextInt(charset.length)];
        deliveryCode += charset[wheel.nextInt(charset.length)];
        deliveryCode += charset[wheel.nextInt(charset.length)];
        deliveryCode += charset[wheel.nextInt(charset.length)];

        return deliveryCode;
    }
}
