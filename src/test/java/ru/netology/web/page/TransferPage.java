package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private SelenideElement headingPopolnenie = $(byText("Пополнение карты"));

    private SelenideElement amount = $("[data-test-id=amount] input");
    private SelenideElement fromCard = $("[data-test-id=from] input");
    private SelenideElement buttonPay = $("[data-test-id=action-transfer]");

    public void checkHeadingPopolnenie(){
        headingPopolnenie.shouldBe(Condition.visible);
    }

    public void setCardNumber (String card, int payment){
        amount.setValue(String.valueOf(payment));
        fromCard.setValue(card);
        buttonPay.click();
    }

    public DashboardPage validPayCard(){
        return new DashboardPage();
    }

    public void invalidPayNotExistCard(){
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Произошла ошибка"));
    }

    public void invalidPaySameCard(){
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Невозможно осуществить перевод на ту же самую карту"));
    }

    public void invalidPayExtendAmout(){
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Вы не можете перевести средств больше, чем есть на карте"));
    }


}
