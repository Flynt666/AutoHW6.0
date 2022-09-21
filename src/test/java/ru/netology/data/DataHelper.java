package ru.netology.data;

import lombok.Value;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardInfo {
        String number;
        String balance;
        public String id;
    }

    public static CardInfo[] cards = {new CardInfo("5559_0000_0000_0001", "10_000", "92df3f1c-a033-48e6-8390-206f6b1f56c0"),
            new CardInfo("5559_0000_0000_0002", "10_000", "0f3f5c2a-249e-4c3d-8287-09f7a039391d")};
}