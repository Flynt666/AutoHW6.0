package ru.netology.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashBoardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferTest {
    DashBoardPage dashBoardPage;

    @BeforeEach
    void login() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashBoardPage = verificationPage.validVerify(verificationCode);
    }

    @AfterEach
    public void returnCardBalancesToDefault() {
        var balance = dashBoardPage.getCardBalance(DataHelper.cards[0].getId());
        if (balance > 10000) {
            var difference = balance - 10000;
            var transferPade = dashBoardPage.moneyTransfer(DataHelper.cards[1]);
            transferPade.transferMoney(DataHelper.cards[0], String.valueOf(difference));
        } else {
            var difference = 10000 - balance;
            var transferPade = dashBoardPage.moneyTransfer(DataHelper.cards[0]);
            transferPade.transferMoney(DataHelper.cards[1], String.valueOf(difference));
        }
    }

    @Test
    void shouldTransferFromFirstToSecondCardHappyPath() {
        var transferPage = dashBoardPage.moneyTransfer(DataHelper.cards[1]);
        transferPage.transferMoney(DataHelper.cards[0], "1500");
        var balance0 = dashBoardPage.getCardBalance(DataHelper.cards[0].getId());
        var balance1 = dashBoardPage.getCardBalance(DataHelper.cards[1].getId());
        assertEquals(8500, balance0);
        assertEquals(11500, balance1);
    }

    @Test
    void shouldTransferFromSecondToFirstCardHappyPath() {
        var transferPage = dashBoardPage.moneyTransfer(DataHelper.cards[0]);
        transferPage.transferMoney(DataHelper.cards[1], "1000");
        var balance0 = dashBoardPage.getCardBalance(DataHelper.cards[0].getId());
        var balance1 = dashBoardPage.getCardBalance(DataHelper.cards[1].getId());
        assertEquals(11000, balance0);
        assertEquals(9000, balance1);
    }


    @Test
    void shouldNotTransferOverCardBalance() {
        var transferPage = dashBoardPage.moneyTransfer(DataHelper.cards[0]);
        transferPage.transferMoney(DataHelper.cards[1], "135000");
        var balance0 = dashBoardPage.getCardBalance(DataHelper.cards[0].getId());
        var balance1 = dashBoardPage.getCardBalance(DataHelper.cards[1].getId());
        assertEquals(10000, balance0);
        assertEquals(10000, balance1);
    }

}