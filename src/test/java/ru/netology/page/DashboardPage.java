package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import lombok.Data;
import lombok.val;

import java.rmi.server.SkeletonNotFoundException;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static java.lang.Integer.parseInt;

@Data
public class DashboardPage {
    private SelenideElement head = $("[data-test-id=dashboard]");
    private SelenideElement buttonCard1 = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']>[data-test-id='action-deposit']");
    private SelenideElement buttonCard2 = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']>[data-test-id='action-deposit']");
    private SelenideElement balanceCard1 = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']");
    private SelenideElement balanceCard2 = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']");

    private SelenideElement paymentPage = $(byText("Пополнение карты"));
    private SelenideElement amount = $("[data-test-id='amount'] input");
    private SelenideElement fromCard = $("[data-test-id='from'] input");
    private SelenideElement transfer = $("[data-test-id='action-transfer']");
    private SelenideElement errorTransfer = $("[data-test-id='error-notification']");

    public DashboardPage() {
        head.shouldBe(visible);
    }

    public int getBalanceCard1() {
        val text = balanceCard1.text();
        val start = text.indexOf("баланс: ");
        val finish = text.indexOf(" р.");
        val balance = parseInt(text.substring(start + 8, finish));
        return balance;
    }

    public int getBalanceCard2() {
        val text = balanceCard2.text();
        val start = text.indexOf("баланс: ");
        val finish = text.indexOf(" р.");
        val balance = parseInt(text.substring(start + 8, finish));
        return balance;
    }

    public void cardClick(int buttonNum) {
        if (buttonNum == 1) {
            buttonCard1.click();
        } else {
            buttonCard2.click();
        }
    }

    public SelenideElement setAmount (String sum) {
        return amount.setValue(sum);
    }

    public SelenideElement setFromCard (String card) {
        return fromCard.setValue(card);
    }

    public DashboardPage getTransfer () {
        transfer.click();
        return new DashboardPage();
    }
}