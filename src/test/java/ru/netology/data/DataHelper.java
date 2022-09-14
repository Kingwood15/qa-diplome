package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    final private static String testDataValidCardNumber = "4444444444444441";
    final private static String testDataInvalidCardNumber = "4444444444444442";

    //Todo: хранить карты здесь? Или создать отдельный дата-класс

    @Value
    public static class CardInfo {
        private String cardNumber;
        private String cardMonth;
        private String cardYear;
        private String cardOwner;
        private String cardCVV;
    }

    //Todo: зарандомить карту валидную и невалидную, с помощью Faker
    //создать карту
    public static CardInfo setCard(String setCardNumber, String setCardMonth, String setCardYear, String setCardOwner, String setCardCVV) {
        return new CardInfo(setCardNumber, setCardMonth, setCardYear, setCardOwner, setCardCVV);
    }

    //создать валидную карту
    public static CardInfo setValidCard(String month, String year, String owner, String cvv) {
        return setCard(testDataValidCardNumber, month, year, owner, cvv);
    }

    //создать невалидную карту
    public static CardInfo setInvalidCard(String month, String year, String owner, String cvv) {
        return setCard(testDataInvalidCardNumber, month, year, owner, cvv);
    }

    //генерация даты
    public static String[] generateDate(int days) {
        String date = LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yy"));
        return date.split("\\.");
    }

    //генерация владельца
    public static String generateOwner(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.name().firstName() + " " + faker.name().lastName();
    }
}