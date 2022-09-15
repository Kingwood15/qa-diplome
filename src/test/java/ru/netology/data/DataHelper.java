package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    //final private static String databaseType = "postgresql";
    final private static String databaseType = "mysql";
    final private static String testDataValidCardNumber = "4444444444444441";
    final private static String testDataInvalidCardNumber = "4444444444444442";

    final static String orderCreditId = "SELECT credit_id FROM order_entity ORDER BY created DESC";
    final static String creditId = "SELECT bank_id FROM credit_request_entity ORDER BY created DESC";
    final static String orderPaymentId = "SELECT payment_id FROM order_entity ORDER BY created DESC";
    final static String paymentId = "SELECT transaction_id FROM payment_entity ORDER BY created DESC";

    @Value
    public static class CardInfo {
        private String cardNumber;
        private String cardMonth;
        private String cardYear;
        private String cardOwner;
        private String cardCVV;
    }

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

    public static String getIdFromPaymentEntity() {
        return requestSQL(paymentId);
        //return requestMySQL(paymentId);
        //return requestTransactionIdFromPayment();
    }

    public static String getPaymentIdFromOrderEntity() {
        return requestSQL(orderPaymentId);
        //return requestMySQL(orderPaymentId);
        //return requestTransactionIdFromPayment();
    }

    /*@SneakyThrows
    private static String requestTransactionIdFromPayment() {
        //ожидание создания кода верификации
        Thread.sleep(500);
        var runner = new QueryRunner();
        //берём последний созданный transaction_id
        var sqlRequestSortByTime = "SELECT transaction_id FROM payment_entity ORDER BY created DESC";

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );

        ) {
            return runner.query(conn, sqlRequestSortByTime, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    private static String requestPaymentIdFromOrder() {
        //ожидание создания кода верификации
        Thread.sleep(500);
        var runner = new QueryRunner();
        //берём последний созданный transaction_id
        var sqlRequestSortByTime = "SELECT payment_id FROM order_entity ORDER BY created DESC";

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );

        ) {
            return runner.query(conn, sqlRequestSortByTime, new ScalarHandler<>());
        }
    }*/

    public static String getIdFromCreditRequestEntity() {
        return requestSQL(creditId);
        //return requestMySQL(creditId);
        //return requestBankIdFromCredit();
    }

    public static String getCreditIdFromOrderEntity() {
        return requestSQL(orderCreditId);
        //return requestMySQL(orderCreditId);
        //return requestCreditIdFromOrder();
    }

    /*@SneakyThrows
    private static String requestBankIdFromCredit() {
        //ожидание создания кода верификации
        Thread.sleep(500);
        var runner = new QueryRunner();
        //берём последний созданный transaction_id
        var sqlRequestSortByTime = "SELECT bank_id FROM credit_request_entity ORDER BY created DESC";

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );

        ) {
            return runner.query(conn, sqlRequestSortByTime, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    private static String requestCreditIdFromOrder() {
        //ожидание создания кода верификации
        Thread.sleep(500);
        var runner = new QueryRunner();
        //берём последний созданный transaction_id
        var sqlRequestSortByTime = "SELECT credit_id FROM order_entity ORDER BY created DESC";

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );

        ) {
            return runner.query(conn, sqlRequestSortByTime, new ScalarHandler<>());
        }
    }*/

    //универсальный вариант
    @SneakyThrows
    private static String requestMySQL(String requestSQL) {
        //ожидание создания кода верификации
        Thread.sleep(500);
        var runner = new QueryRunner();

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );

        ) {
            return runner.query(conn, requestSQL, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    private static String requestPostgreSQL(String requestSQL) {
        //ожидание создания кода верификации
        Thread.sleep(500);
        var runner = new QueryRunner();

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/app", "app", "pass"
                );

        ) {
            return runner.query(conn, requestSQL, new ScalarHandler<>());
        }
    }

    //супер универсальный запрос для МУ и ПОСТГРЕСС
    @SneakyThrows
    private static String requestSQL(String requestSQL) {
        //ожидание создания кода верификации
        Thread.sleep(500);
        var runner = new QueryRunner();

        if (databaseType == "mysql") {
            try (
                    var conn = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/app", "app", "pass"
                    );

            ) {
                return runner.query(conn, requestSQL, new ScalarHandler<>());
            }
        } else if (databaseType == "postgresql") {
            {
                try (
                        var conn = DriverManager.getConnection(
                                "jdbc:postgresql://localhost:5432/app", "app", "pass"
                        );

                ) {
                    return runner.query(conn, requestSQL, new ScalarHandler<>());
                }
            }
        }
        return "0";
    }

    //вычистка данных за SUT
    @SneakyThrows
    public static void clearSUTData() {
        var runner = new QueryRunner();
        var sqlDeleteAllOrders = "DELETE FROM order_entity;";
        var sqlDeleteAllCreditRequest = "DELETE FROM credit_request_entity;";
        var sqlDeleteAllPaymentRequest = "DELETE FROM payment_entity;";

        if (databaseType == "mysql") {
            try (
                    var conn = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/app", "app", "pass"
                    );
            ) {
                runner.update(conn, sqlDeleteAllOrders);
                runner.update(conn, sqlDeleteAllCreditRequest);
                runner.update(conn, sqlDeleteAllPaymentRequest);
            }
        } else if (databaseType == "postgresql") {
            try (
                    var conn = DriverManager.getConnection(
                            "jdbc:postgresql://localhost:5432/app", "app", "pass"
                    );
            ) {
                runner.update(conn, sqlDeleteAllOrders);
                runner.update(conn, sqlDeleteAllCreditRequest);
                runner.update(conn, sqlDeleteAllPaymentRequest);
            }
        }

        /*try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            runner.update(conn, sqlDeleteAllOrders);
            runner.update(conn, sqlDeleteAllCreditRequest);
            runner.update(conn, sqlDeleteAllPaymentRequest);
        }*/
    }
}