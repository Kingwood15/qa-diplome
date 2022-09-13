package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class PurchasePage {

    private static SelenideElement buttonBuy = $x("//*[.='Купить']");
    private static SelenideElement buttonBuyInCredit = $x("//*[.='Купить в кредит']");

    public static PaymentPage selectPaymentWay() {
        buttonBuy.click();
        PaymentPage.verifyBue();
        return new PaymentPage();
    }

    public static PaymentPage selectCreditWay() {
        buttonBuyInCredit.click();
        PaymentPage.verifyCredit();
        return new PaymentPage();
    }
}