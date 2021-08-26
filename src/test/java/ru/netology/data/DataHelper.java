package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

public class DataHelper {
    private static Faker faker = new Faker();

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getInvalidAuthInfo() {
        return new AuthInfo(faker.name().username(), faker.internet().password());
    }

    public static AuthInfo getInvalidPassword(){
        return new AuthInfo("vasya",faker.internet().password());
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static String getInvalidVerificationCode() {
        return String.valueOf(faker.random().nextLong());
    }
}