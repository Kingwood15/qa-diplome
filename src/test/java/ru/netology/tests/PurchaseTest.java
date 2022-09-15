package ru.netology.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.PurchasePage;

import static com.codeborne.selenide.Selenide.open;

public class PurchaseTest {
    //Todo: привести код в порядок
    @BeforeEach
    void shouldStart() {
        //DataHelper.clearSUTData();
        open("http://localhost:8080/");
    }

    @AfterEach
    void afterCase() {
        DataHelper.clearSUTData();
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

        String actual = DataHelper.getPaymentIdFromOrderEntity();
        String expected = DataHelper.getIdFromPaymentEntity();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldBuyWithNotRegisteredCard() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.setCard("4444 4444 4444 4444", month, year, owner, cvv);
        var paymentPage = PurchasePage.selectPaymentWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.checkUnsuccessPay();

        String actual = DataHelper.getPaymentIdFromOrderEntity();
        Assertions.assertNull(actual);
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

        String actual = DataHelper.getPaymentIdFromOrderEntity();
        Assertions.assertNull(actual);
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

        String actual = DataHelper.getPaymentIdFromOrderEntity();
        Assertions.assertNull(actual);
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

        String actual = DataHelper.getPaymentIdFromOrderEntity();
        Assertions.assertNull(actual);
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

        String actual = DataHelper.getPaymentIdFromOrderEntity();
        Assertions.assertNull(actual);
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

        String actual = DataHelper.getPaymentIdFromOrderEntity();
        Assertions.assertNull(actual);
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

        String actual = DataHelper.getPaymentIdFromOrderEntity();
        Assertions.assertNull(actual);
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

        String actual = DataHelper.getPaymentIdFromOrderEntity();
        Assertions.assertNull(actual);
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

        String actual = DataHelper.getPaymentIdFromOrderEntity();
        Assertions.assertNull(actual);
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

        String actual = DataHelper.getPaymentIdFromOrderEntity();
        Assertions.assertNull(actual);
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

        String actual = DataHelper.getPaymentIdFromOrderEntity();
        Assertions.assertNull(actual);
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

        String actual = DataHelper.getPaymentIdFromOrderEntity();
        Assertions.assertNull(actual);
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

        String actual = DataHelper.getPaymentIdFromOrderEntity();
        Assertions.assertNull(actual);
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

        String actual = DataHelper.getPaymentIdFromOrderEntity();
        Assertions.assertNull(actual);
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

        String actual = DataHelper.getPaymentIdFromOrderEntity();
        Assertions.assertNull(actual);
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

        String actual = DataHelper.getPaymentIdFromOrderEntity();
        Assertions.assertNull(actual);
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

        String actual = DataHelper.getPaymentIdFromOrderEntity();
        Assertions.assertNull(actual);
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

        String actual = DataHelper.getPaymentIdFromOrderEntity();
        Assertions.assertNull(actual);
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

        String actual = DataHelper.getPaymentIdFromOrderEntity();
        Assertions.assertNull(actual);
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

        String actual = DataHelper.getPaymentIdFromOrderEntity();
        Assertions.assertNull(actual);
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

        String actual = DataHelper.getPaymentIdFromOrderEntity();
        Assertions.assertNull(actual);
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

        String actual = DataHelper.getPaymentIdFromOrderEntity();
        Assertions.assertNull(actual);
    }
}