package ru.netology.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.PurchasePage;

import static com.codeborne.selenide.Selenide.open;

public class PurchaseTest {
    //Todo 1: сравнить (order_entity поле payment_id) с таблицей (payment_entity поле transaction_id)

    @BeforeEach
    void shouldStart() {
        open("http://localhost:8080/");
    }

    //Позитивные сценарии
    @Test
    void shouldBuyWithValidCard() {
        //открыть страницу (уже в аннотации BeforeEach
        //выбрать купить
        //заполнить поля
        //выбрать продолжить
        // сверить появившееся окно
        //проверить запись в БД
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectPaymentWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.checkSuccessPay();
    }

    @Test
    void shouldBuyWithInvalidCard() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setInvalidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectPaymentWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.checkUnsuccessPay();
    }

    //негативные сценарии
    @Test
    void shouldBuyWithEmptyCardNumber() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        String emptyCardNumber = "";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setCard(emptyCardNumber, month, year, owner, cvv);
        var paymentPage = PurchasePage.selectPaymentWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldBuyWithShortCardNumber() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        String shortCardNumber = "4444 4444 4444 444";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setCard(shortCardNumber, month, year, owner, cvv);
        var paymentPage = PurchasePage.selectPaymentWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldBuyWithNo0CardNumber() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        String zeroCardNumber = "0";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setCard(zeroCardNumber, month, year, owner, cvv);
        var paymentPage = PurchasePage.selectPaymentWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldBuyWithCardPastMonth() {
        String[] date = DataHelper.generateDate(-30);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectPaymentWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldCardDateError();
    }

    @Test
    void shouldBuyWithCardNo00Month() {
        String[] date = DataHelper.generateDate(30);
        String month = "00", year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectPaymentWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldCardDateError();
    }

    @Test
    void shouldBuyWithCardNo13Month() {
        String[] date = DataHelper.generateDate(30);
        String month = "13", year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectPaymentWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldCardDateError();
    }

    @Test
    void shouldBuyWithCardEmptyMonth() {
        String[] date = DataHelper.generateDate(30);
        String month = "", year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectPaymentWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldBuyWithCardShortMonth() {
        String[] date = DataHelper.generateDate(30);
        String month = "3", year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectPaymentWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldBuyWithCardEmptyYear() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = "", owner = DataHelper.generateOwner("En"), cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectPaymentWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldBuyWithCardPastYear() {
        String[] date = DataHelper.generateDate(-365);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectPaymentWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldCardDateExpiredError();
    }

    @Test
    void shouldBuyWithCardFutureOn10Year() {
        String[] date = DataHelper.generateDate(3650);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectPaymentWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldCardDateError();
    }

    @Test
    void shouldBuyWithCardEmptyOwner() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = "", cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectPaymentWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldCardRequiredField();
    }

    @Test
    void shouldBuyWithCardCyrillicOwner() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("Ru"), cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectPaymentWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldBuyWithCardSpecSymbolsOwner() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = "!\"№;?\"%", cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectPaymentWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldBuyWithCardNumbersOwner() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = "1234567890", cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectPaymentWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldBuyWithCardNo0Owner() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = "0", cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectPaymentWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldBuyWithCardEmptyCVV() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectPaymentWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldBuyWithCardOneNumberCVV() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "1";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectPaymentWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldBuyWithCardNo0CVV() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "0";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectPaymentWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldBuyWithCardTwoNumberCVV() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "12";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectPaymentWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }
}