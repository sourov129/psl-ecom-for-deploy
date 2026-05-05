package com.gach.core.util;

import java.util.Random;

public class OtpGenerator {
    public static String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }
}
