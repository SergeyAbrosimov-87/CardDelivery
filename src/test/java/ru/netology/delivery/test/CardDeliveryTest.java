package ru.netology.delivery.test;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class CardDeliveryTest {

    String city = DataGenerator.generateCity();
    String name = DataGenerator.generateName();
    String phone = DataGenerator.generatePhone();

    @Test
    void SubmitRequest() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(city);
        form.$("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(3));
        form.$("[data-test-id=name] input").setValue(name);
        form.$("[data-test-id=phone] input").setValue(phone);
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $(".notification_status_ok").shouldBe(visible);
        $("[data-test-id='success-notification'] .notification__content").shouldHave(exactText("Встреча успешно запланирована на " + DataGenerator.generateDate(4)));
        form.$("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(8));
        form.$(".button").click();
        $("[data-test-id=replan-notification]").shouldBe(visible, Duration.ofSeconds(15));
        $(withText("Перепланировать")).click();
        $(".notification_status_ok").shouldBe(visible);
        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + DataGenerator.generateDate(8)));
    }

    @Test
    void invalidFirstAndLastName() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(city);
        form.$("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(3));
        $("[data-test-id=name] input").setValue("Sergey Abrosimov");
        $("[data-test-id=phone] input").setValue(phone);
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void invalidCity() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Элиста");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(3));
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=city].input_invalid").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void invalidDate() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(city);
        form.$("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(-4));
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=date]").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void LeaveTheFieldsEmpty() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        $(".button").click();
        $("[data-test-id=city]").shouldHave(exactText("Поле обязательно для заполнения"));
    }
}