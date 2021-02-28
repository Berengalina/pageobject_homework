package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCard() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingCards();
        val initialBalanceToCard = dashboardPage.getFirstCardBalance();
        val initialBalanceFromCard = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.validChoosePay1();
        transferPage.checkHeadingPopolnenie();
        val amount = 1234;
        transferPage.setCardNumber(DataHelper.getSecondCard(), amount);
        val dashboardPage1 = transferPage.validPayCard();
        val actual1 = dashboardPage1.getFirstCardBalance();
        val actual2 = dashboardPage1.getSecondCardBalance();
        val expected1 = initialBalanceToCard + amount;
        val expected2 = initialBalanceFromCard - amount;
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    void shouldTransferMoneyFromFirstCardToSecond() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingCards();
        val initialBalanceFromCard = dashboardPage.getFirstCardBalance();
        val initialBalanceToCard = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.validChoosePay2();
        transferPage.checkHeadingPopolnenie();
        val amount = 999;
        transferPage.setCardNumber(DataHelper.getFirstCard(), amount);
        val dashboardPage1 = transferPage.validPayCard();
        val actual1 = dashboardPage1.getSecondCardBalance();
        val actual2 = dashboardPage1.getFirstCardBalance();
        val expected1 = initialBalanceToCard + amount;
        val expected2 = initialBalanceFromCard - amount;
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    void shouldNotTransferMoneySameCard() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingCards();
        val transferPage = dashboardPage.validChoosePay1();
        transferPage.checkHeadingPopolnenie();
        val amount = 1000;
        transferPage.setCardNumber(DataHelper.getFirstCard(), amount);
        transferPage.invalidPaySameCard();
    }

    @Test
    void shouldNotTransferInvalidCard() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingCards();
        val transferPage = dashboardPage.validChoosePay1();
        transferPage.checkHeadingPopolnenie();
        val amount = 1000;
        transferPage.setCardNumber(DataHelper.getInvalidCard(), amount);
        transferPage.invalidPayNotExistCard();
    }

    @Test
    void shouldNotTransferExtendLimit() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingCards();
        val initialBalanceFromCard = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.validChoosePay1();
        transferPage.checkHeadingPopolnenie();
        val amount = 1 + initialBalanceFromCard;
        transferPage.setCardNumber(DataHelper.getSecondCard(), amount);
        transferPage.invalidPayExtendAmout();

    }


}
