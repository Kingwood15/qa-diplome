package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class PaymentPage {
    private static SelenideElement headingBye = $x("//*[.='Оплата по карте']");
    private static SelenideElement headingCredit = $x("//*[.='Кредит по данным карты']");
    private SelenideElement notificationStatusOk = $(".notification_status_ok .notification__content");
    private SelenideElement buttonNotificationStatusOkClose = $(".notification_status_ok button");
    private SelenideElement notificationStatusError = $(".notification_status_error .notification__content");
    private SelenideElement buttonNotificationStatusErrorClose = $(".notification_status_error button");
    private static SelenideElement fieldCardNumber = $x("//*[.='Номер карты'] //input");
    private static SelenideElement fieldCardMonth = $x("//*[.='Месяц'] //input");
    private static SelenideElement fieldCardYear = $x("//*[.='Год'] //input");
    private static SelenideElement fieldCardOwner = $x("//*[.='Владелец'] //input");
    private static SelenideElement fieldCardCVC = $x("//*[.='CVC/CVV'] //input");
    private static SelenideElement buttonContinue = $(".form-field button");

    //проверка видимости нужных форм
    public static void verifyBue() {
        headingBye.shouldBe(Condition.visible);
    }

    public static void verifyCredit() {
        headingCredit.shouldBe(Condition.visible);
    }

    //проверка оплаты
    public void checkSuccessPay() {
        notificationStatusOk.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    public void checkUnsuccessPay() {
        notificationStatusError.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    //ввод данных карты
    public static void enterCardData(DataHelper.CardInfo info) {
        fieldCardNumber.setValue(info.getCardNumber());
        fieldCardMonth.setValue(info.getCardMonth());
        fieldCardYear.setValue(info.getCardYear());
        fieldCardOwner.setValue(info.getCardOwner());
        fieldCardCVC.setValue(info.getCardCVV());
        buttonContinue.click();
    }

    //метод очистки полей
    public static void cleanAllFields() {
        fieldCardNumber.sendKeys(Keys.LEFT_CONTROL + "A");
        fieldCardNumber.sendKeys(Keys.DELETE);
        fieldCardMonth.sendKeys(Keys.LEFT_CONTROL + "A");
        fieldCardMonth.sendKeys(Keys.DELETE);
        fieldCardYear.sendKeys(Keys.LEFT_CONTROL + "A");
        fieldCardYear.sendKeys(Keys.DELETE);
        fieldCardOwner.sendKeys(Keys.LEFT_CONTROL + "A");
        fieldCardOwner.sendKeys(Keys.DELETE);
        fieldCardCVC.sendKeys(Keys.LEFT_CONTROL + "A");
        fieldCardCVC.sendKeys(Keys.DELETE);
    }
}
