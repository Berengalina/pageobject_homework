package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");

    VerificationPage(){codeField.shouldBe(Condition.visible);}

    public DashboardPage validVerify (DataHelper.VerificationCode code){
        codeField.setValue(code.getCode());
        verifyButton.click();
        return new DashboardPage();
    }

}
