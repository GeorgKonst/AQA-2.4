package ru.netology.tests;

import com.codeborne.selenide.Condition;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PayTest {

    private String amount = "1000";


    @BeforeEach
    void RoadToDashboard(){
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validUser(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

    }

    @Test
    void shouldTransferForCard1ToCard2(){
        DashboardPage dashboardPage = new DashboardPage();
        int expected1 = dashboardPage.getBalanceCard1() - Integer.parseInt(amount);
        int expected2 = dashboardPage.getBalanceCard2() + Integer.parseInt(amount);
        dashboardPage.cardClick(2);
        dashboardPage.setAmount(amount);
        dashboardPage.setFromCard(DataHelper.getCard1());
        dashboardPage.getTransfer();
        assertEquals(expected1, dashboardPage.getBalanceCard1());
        assertEquals(expected2, dashboardPage.getBalanceCard2());
    }

    @Test
    void shouldTransferForCard2ToCard1(){
        DashboardPage dashboardPage = new DashboardPage();
        int expected1 = dashboardPage.getBalanceCard1() + Integer.parseInt(amount);
        int expected2 = dashboardPage.getBalanceCard2() - Integer.parseInt(amount);
        dashboardPage.cardClick(1);
        dashboardPage.setAmount(amount);
        dashboardPage.setFromCard(DataHelper.getCard2());
        dashboardPage.getTransfer();
        assertEquals(expected1, dashboardPage.getBalanceCard1());
        assertEquals(expected2, dashboardPage.getBalanceCard2());
    }
    @Test
    void shouldTransferFromInvalidCard(){
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.cardClick(1);
        dashboardPage.setAmount(amount);
        dashboardPage.setFromCard(DataHelper.getWrongCard());
        dashboardPage.getTransfer();
        dashboardPage.getErrorTransfer().shouldBe(Condition.visible);
    }

    @Test
    void shouldTransferZeroAmount(){
        DashboardPage dashboardPage = new DashboardPage();
        int expected1 = dashboardPage.getBalanceCard1();
        int expected2 = dashboardPage.getBalanceCard2();
        dashboardPage.cardClick(2);
        dashboardPage.setAmount("0");
        dashboardPage.setFromCard(DataHelper.getCard1());
        dashboardPage.getTransfer();
        assertEquals(expected1, dashboardPage.getBalanceCard1());
        assertEquals(expected2, dashboardPage.getBalanceCard2());
    }

    @Test
    void shouldTransferForCard1ToCard1(){
        DashboardPage dashboardPage = new DashboardPage();
        int expected1 = dashboardPage.getBalanceCard1();
        int expected2 = dashboardPage.getBalanceCard2();
        dashboardPage.cardClick(1);
        dashboardPage.setAmount(amount);
        dashboardPage.setFromCard(DataHelper.getCard1());
        dashboardPage.getTransfer();
        assertEquals(expected1, dashboardPage.getBalanceCard1());
        assertEquals(expected2, dashboardPage.getBalanceCard2());
    }

    @Test
    void shouldTransferOverLimitMounts(){
        DashboardPage dashboardPage = new DashboardPage();
        int overLimitMounts = dashboardPage.getBalanceCard1() + 1000;
        dashboardPage.cardClick(1);
        sleep(5000);
        dashboardPage.setAmount(String.valueOf(overLimitMounts));
        dashboardPage.setFromCard(DataHelper.getCard2());
        sleep(5000);
        dashboardPage.getTransfer();
        dashboardPage.getErrorTransfer().shouldBe(Condition.visible);
    }



}
