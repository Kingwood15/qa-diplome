package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class PurchasePage {

    final private static SelenideElement buttonBuy = $x("//*[.='Купить']");
    final private static SelenideElement buttonCredit = $x("//*[.='Купить в кредит']");

    public static PaymentPage selectPaymentWay() {
        buttonBuy.click();
        PaymentPage.verifyBue();
        return new PaymentPage();
    }

    public static PaymentPage selectCreditWay() {
        buttonCredit.click();
        PaymentPage.verifyCredit();
        return new PaymentPage();
    }
}