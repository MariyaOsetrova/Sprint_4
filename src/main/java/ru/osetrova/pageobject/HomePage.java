package ru.osetrova.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {
    private final WebDriver driver;
    // блок вопросов о важном
    private final By questionBlock = By.xpath("//div[@data-accordion-component='Accordion']");
    //кнопки заказать
    private final By orderButton = By.xpath("//button[text()='Заказать']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public String getAnswerText(int idQuestion) {
        new WebDriverWait(driver, Duration.ofSeconds(3)).
                until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id =\"accordion__panel-" + idQuestion + "\"]/p")));
        return driver.findElement(By.xpath("//*[@id =\"accordion__panel-" + idQuestion + "\"]/p")).getText();

    }

    //Клик по полю с вопросом
    public HomePage clickQuestionButton(String question) {
        WebElement elementQuestionBlock = driver.findElement(questionBlock);
        // скрол к элементу
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", elementQuestionBlock);

        driver.findElement(By.xpath("//*[text() = '" + question + "']")).click();

        return this;
    }

    //получить кнопку "Заказать"
    public List<WebElement> getOrderButton() {
        return driver.findElements(orderButton);
    }

    // проскролить к нужной кнопке "Заказать"
    public void scrollToOrderButton(int id) {
        WebElement elementToOrderButton = driver.findElements(orderButton).get(id);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", elementToOrderButton);

    }
}
