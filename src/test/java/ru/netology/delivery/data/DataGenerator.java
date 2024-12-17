package ru.netology.delivery.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity() {
        String[] sities = new String[]{"Анадырь", "Архангельск", "Астрахань", "Барнаул", "Белгород",
                "Владикавказ", "Биробиджан", "Благовещенск", "Брянск", "Великий Новгород", "Владивосток", "Владикавказ",
                "Владимир", "Волгоград", "Вологда", "Воронеж", "Калининград", "Калуга", "Краснодар", "Курск",
                "Салехард", "Самара", "Саранск", "Саратов", "Хабаровск", "Южно-Сахалинск", "Рязань", "Чебоксары",
                "Москва", "Санкт-Петербург", "Мурманск", "Новосибирск", "Сыктывкар"};
        return sities[new Random().nextInt(sities.length)];
    }

    public static String generateName() {
        Faker faker = new Faker(new Locale(locale));
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generatePhone() {
        Faker faker = new Faker(new Locale(locale));
        return faker.phoneNumber().phoneNumber();
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            return new UserInfo(generateCity(), generateName(), generatePhone());
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;

        public UserInfo(String s, String s1, String s2) {
        }
    }
}