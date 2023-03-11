package ru.netology.pageObject;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class LoginPage {
    private static SelenideElement loginField = $("[data-test-id=login] input");
    private static SelenideElement passwordField = $("[data-test-id=password] input");
    private static SelenideElement loginButton = $("[data-test-id=action-login]");
    private static SelenideElement errorNotification = $("[data-test-id=error-notification]");

    public static void verifyErrorNotificationVisibility() {
        errorNotification.shouldBe(visible);
    }
    public static VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return page (VerificationPage.class);
    }
}