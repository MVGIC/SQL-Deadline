package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.sql.SqlRequest;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class AuthorizationTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void clear() {
        SqlRequest.clearDB();
    }

    @Test
    void shouldAuthWithValidData() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = SqlRequest.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.validFields();
    }

    @Test
    void shouldWarnIfAuthWithInvalidLogin() {
        val loginPage = new LoginPage();
        val invalidAuthInfo = DataHelper.getInvalidAuthInfo();
        loginPage.invalidLogin(invalidAuthInfo);
    }

    @Test
    void shouldWarnIfAuthWithInvalidCode() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val invalidVerificationCode = DataHelper.getInvalidVerificationCode();
        verificationPage.invalidVerify(invalidVerificationCode);
    }

    @Test
    void shouldBlockAfterThreeTimesInvalidPass() {
        val loginPage = new LoginPage();
        val invalidPassword = DataHelper.getInvalidPassword();
        loginPage.setInvalidPassword(invalidPassword);
        loginPage.clearFields();
        loginPage.setInvalidPassword(invalidPassword);
        loginPage.clearFields();
        loginPage.setInvalidPassword(invalidPassword);
        loginPage.errorAuth();
        loginPage.loginButtonShouldBeDisabled();
    }
}