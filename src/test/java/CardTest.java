import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardTest {
    public void setup () {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }
    public String generateDate(long days, String pattern) {
        return LocalDate.now().plusDays (days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
    @Test
    void shouldSendForm() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Уфа");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(4)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").setValue(verificationDate);
        $("[data-test-id=name] input").setValue("Долгая Аля Карловна");
        $("[data-test-id=phone] input").setValue("+79123456789");
        $("[data-test-id=agreement]").click();
        $(".button").shouldHave(text("Забронировать")).click();
        $("[data-test-id=notification]")
                .shouldHave(text("Успешно! Встреча успешно забронирована на " + verificationDate),
                        Duration.ofSeconds(15));
    }
}


