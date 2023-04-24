import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CardTest {
    @BeforeEach
    public void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    public String generateDate(long days, String ignoredPattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void shouldSendForm() {
        $(LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        $("[data-test-id='city'] input").setValue("Уфа");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        String date = generateDate( 3,"dd.MM.yyyy");
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Долгая Аля Карловна");
        $("[data-test-id='phone'] input").setValue("+79123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id='notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.ownText(date));
    }
}


