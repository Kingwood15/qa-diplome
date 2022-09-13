package ru.netology.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.PurchasePage;

import static com.codeborne.selenide.Selenide.open;

public class PurchaseTest {
    //Todo: проверить записи в БД, также, добавить очистку БД

    @BeforeEach
    void shouldStart() {
        open("http://localhost:8080/");
    }

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

        var cardInfo = DataHelper.generateValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectPaymentWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.checkSuccessPay();
    }

    @Test
    void shouldCreditWithValidCard() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.generateValidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();
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

        var cardInfo = DataHelper.generateInvalidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectPaymentWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.checkUnsuccessPay();
    }

    @Test
    void shouldCreditWithInvalidCard() {
        String[] date = DataHelper.generateDate(30);
        String month = date[1], year = date[2], owner = DataHelper.generateOwner("En"), cvv = "123";
        // Проверка входных данных
        System.out.println("!!!Check enter data!!! month = " + month + "; year = " + year + "; owner = " + owner + "; cvv = " + cvv);

        var cardInfo = DataHelper.generateInvalidCard(month, year, owner, cvv);
        var paymentPage = PurchasePage.selectCreditWay();
        paymentPage.cleanAllFields();

        paymentPage.enterCardData(cardInfo);

        paymentPage.checkUnsuccessPay();
    }
}