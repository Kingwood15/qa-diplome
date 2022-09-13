package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    private static String  testDataValidCardNumber = "4444444444444441";
    private static String  testDataInvalidCardNumber = "4444444444444442";

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
    //создать валидную карту
    public static CardInfo generateValidCard(String month, String year, String owner, String cvv) {
        return new CardInfo(testDataValidCardNumber, month, year, owner, cvv);
    }

    //создать невалидную карту
    public static CardInfo generateInvalidCard(String month, String year, String owner, String cvv) {
        return new CardInfo(testDataInvalidCardNumber, month, year, owner, cvv);
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