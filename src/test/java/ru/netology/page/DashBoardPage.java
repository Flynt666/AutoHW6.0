package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.data.DataHelper;

import java.util.Objects;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashBoardPage {

    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashBoardPage() {
        heading.shouldBe(Condition.visible);
    }

    public MoneyTransfer moneyTransfer(DataHelper.CardInfo to) {
        $("[data-test-id=\"" + to.getId() + "\"]>button").click();
        return new MoneyTransfer();
    }

    public int getCardBalance(String dataTestId) {
        for (SelenideElement element : cards) {
            val attr = element.attr("data-test-id");
            if (Objects.equals(attr, dataTestId)) {
                return extractBalance(element.text());
            }
        }
        return -1;
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}

