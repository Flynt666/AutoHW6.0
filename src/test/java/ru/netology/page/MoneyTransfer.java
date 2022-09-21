package ru.netology.page;

import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.Keys.SHIFT;

public class MoneyTransfer {
    public void transferMoney(DataHelper.CardInfo from, String money) {
        $("span[data-test-id= amount] input").click();
        $("span[data-test-id= amount] input").sendKeys(Keys.chord(SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("span[data-test-id= amount] input").setValue(money);
        $("[data-test-id=\"from\"] input").click();
        $("[data-test-id=\"from\"] input").sendKeys(Keys.END, Keys.chord(SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=\"from\"] input").setValue(from.getNumber());
        $("[data-test-id= action-transfer]").click();
    }
}