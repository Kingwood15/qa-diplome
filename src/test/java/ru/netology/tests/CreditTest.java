package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.page.PurchasePage;

import static com.codeborne.selenide.Selenide.open;

public class CreditTest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void shouldStart() {
        DataHelper.clearSUTData();
        open("http://localhost:8080/");
    }

    @AfterEach
    void afterCase() {
        DataHelper.clearSUTData();
    }

    @Test
    void shouldCreditWithValidCard() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);

        paymentPage.checkSuccessPay();

        String actual = DataHelper.getCreditIdFromOrderEntity();
        String expected = DataHelper.getIdFromCreditRequestEntity();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldCreditWithNotRegisteredCard() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123";

        var cardInfo = DataHelper.setCard("4444 4444 4444 4444", month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);

        paymentPage.checkUnsuccessPay();

        String actual = DataHelper.getCreditIdFromOrderEntity();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldCreditWithInvalidCard() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123";

        var cardInfo = DataHelper.setInvalidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);

        paymentPage.checkUnsuccessPay();

        String actual = DataHelper.getCreditIdFromOrderEntity();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldCreditWithEmptyCardNumber() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123",
                emptyCardNumber = "";

        var cardInfo = DataHelper.setCard(emptyCardNumber, month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();

        String actual = DataHelper.getCreditIdFromOrderEntity();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldCreditWithShortCardNumber() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123",
                shortCardNumber = "4444 4444 4444 444";

        var cardInfo = DataHelper.setCard(shortCardNumber, month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();

        String actual = DataHelper.getCreditIdFromOrderEntity();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldCreditWithNo0CardNumber() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123",
                zeroCardNumber = "0";

        var cardInfo = DataHelper.setCard(zeroCardNumber, month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();

        String actual = DataHelper.getCreditIdFromOrderEntity();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldCreditWithCardPastMonth() {
        String[] date = DataHelper.generateDate(-30);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldCardDateError();

        String actual = DataHelper.getCreditIdFromOrderEntity();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldCreditWithCardNo00Month() {
        String[] date = DataHelper.generateDate(30);
        String month = "00",
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldCardDateError();

        String actual = DataHelper.getCreditIdFromOrderEntity();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldCreditWithCardNo13Month() {
        String[] date = DataHelper.generateDate(30);
        String month = "13",
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldCardDateError();

        String actual = DataHelper.getCreditIdFromOrderEntity();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldCreditWithCardEmptyMonth() {
        String[] date = DataHelper.generateDate(30);
        String month = "",
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();

        String actual = DataHelper.getCreditIdFromOrderEntity();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldCreditWithCardShortMonth() {
        String[] date = DataHelper.generateDate(30);
        String month = "3",
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();

        String actual = DataHelper.getCreditIdFromOrderEntity();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldCreditWithCardEmptyYear() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = "",
                owner = DataHelper.generateOwner("En"),
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();

        String actual = DataHelper.getCreditIdFromOrderEntity();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldCreditWithCardPastYear() {
        String[] date = DataHelper.generateDate(-365);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldCardDateExpiredError();

        String actual = DataHelper.getCreditIdFromOrderEntity();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldCreditWithCardFutureOn10Year() {
        String[] date = DataHelper.generateDate(3650);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldCardDateError();

        String actual = DataHelper.getCreditIdFromOrderEntity();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldCreditWithCardEmptyOwner() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = "",
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldCardRequiredField();

        String actual = DataHelper.getCreditIdFromOrderEntity();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldCreditWithCardCyrillicOwner() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("Ru"),
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();

        String actual = DataHelper.getCreditIdFromOrderEntity();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldCreditWithCardSpecSymbolsOwner() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = "!\"№;?\"%",
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();

        String actual = DataHelper.getCreditIdFromOrderEntity();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldCreditWithCardNumbersOwner() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = "1234567890",
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();

        String actual = DataHelper.getCreditIdFromOrderEntity();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldCreditWithCardNo0Owner() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = "0",
                cvv = "123";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();

        String actual = DataHelper.getCreditIdFromOrderEntity();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldCreditWithCardEmptyCVV() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();

        String actual = DataHelper.getCreditIdFromOrderEntity();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldCreditWithCardOneNumberCVV() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "1";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();

        String actual = DataHelper.getCreditIdFromOrderEntity();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldCreditWithCardNo0CVV() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "0";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();

        String actual = DataHelper.getCreditIdFromOrderEntity();
        Assertions.assertNull(actual);
    }

    @Test
    void shouldCreditWithCardTwoNumberCVV() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1],
                year = date[2],
                owner = DataHelper.generateOwner("En"),
                cvv = "12";

        var cardInfo = DataHelper.setValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();

        paymentPage.cleanAllFields();
        paymentPage.enterCardData(cardInfo);

        paymentPage.verifyFieldError();

        String actual = DataHelper.getCreditIdFromOrderEntity();
        Assertions.assertNull(actual);
    }
}
