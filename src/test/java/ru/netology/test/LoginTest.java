package ru.netology.test;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.pageObject.LoginPage;
import ru.netology.pageObject.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.cleanDataBase;

public class LoginTest {

    @AfterAll
    static void teardown() {
        cleanDataBase();
    }

    @Test
    void shouldLogin() {
        open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = LoginPage.validLogin (authInfo);
        VerificationPage.verifyVerificationPageVisibility ();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    void shouldGetErrorIfUserNotExist () {
        open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.generateRandomUser();
        LoginPage.validLogin (authInfo);
        LoginPage.verifyErrorNotificationVisibility();
    }

    @Test
    void shouldGetErrorIfVerificationCodeIsWrong () {
        open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = LoginPage.validLogin (authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotificationVisibility();
    }
}
