package ru.netology.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.PurchasePage;

import static com.codeborne.selenide.Selenide.open;

public class CreditTest {
    //Todo 2: сравнить (order_entity поле credit_id) с таблицей (credit_request_entity поле bank_id)

    @BeforeEach
    void shouldStart() {
        open("http://localhost:8080/");
    }

    @Test
    void shouldCreditWithValidCard() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.checkSuccessPay();
    }

    @Test
    void shouldCreditWithInvalidCard() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setInvalidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.checkUnsuccessPay();
    }

    @Test
    void shouldCreditWithEmptyCardNumber() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        String emptyCardNumber = "";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setCard(emptyCardNumber, month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldCreditWithShortCardNumber() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        String shortCardNumber = "4444 4444 4444 444";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setCard(shortCardNumber, month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldCreditWithNo0CardNumber() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        String zeroCardNumber = "0";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setCard(zeroCardNumber, month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldCreditWithCardPastMonth() {
        String[] date = DataHelper.generateDate(-30);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldCardDateError();
    }

    @Test
    void shouldCreditWithCardNo00Month() {
        String[] date = DataHelper.generateDate(30);
        String month = "00", year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldCardDateError();
    }

    @Test
    void shouldCreditWithCardNo13Month() {
        String[] date = DataHelper.generateDate(30);
        String month = "13", year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldCardDateError();
    }

    @Test
    void shouldCreditWithCardEmptyMonth() {
        String[] date = DataHelper.generateDate(30);
        String month = "", year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldCreditWithCardShortMonth() {
        String[] date = DataHelper.generateDate(30);
        String month = "3", year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldCreditWithCardEmptyYear() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = "", owner = DataHelper.generateOwner("En"), cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldCreditWithCardPastYear() {
        String[] date = DataHelper.generateDate(-365);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldCardDateExpiredError();
    }

    @Test
    void shouldCreditWithCardFutureOn10Year() {
        String[] date = DataHelper.generateDate(3650);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldCardDateError();
    }

    @Test
    void shouldCreditWithCardEmptyOwner() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = "", cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldCardRequiredField();
    }

    @Test
    void shouldCreditWithCardCyrillicOwner() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("Ru"), cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldCreditWithCardSpecSymbolsOwner() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = "!\"№;?\"%", cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldCreditWithCardNumbersOwner() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = "1234567890", cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldCreditWithCardNo0Owner() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = "0", cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldCreditWithCardEmptyCVV() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldCreditWithCardOneNumberCVV() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "1";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldCreditWithCardNo0CVV() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "0";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }

    @Test
    void shouldCreditWithCardTwoNumberCVV() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "12";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();
    }
}
