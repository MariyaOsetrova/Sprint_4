package ru.osetrova.pageobject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {
    private final WebDriver driver;

    //заголовок форм создания заказа
    private final String orderNameLocator = "//div[@class='Order_Header__BZXOb'  and text()='%s']";
    // локатор имени
    private final By nameLocator = By.xpath("//input[@placeholder='* Имя']");
    // локатор фамилии
    private final By surnameLocator = By.xpath("//input[@placeholder='* Фамилия']");
    // локатор адреса доставки
    private final By addressLocator = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    // локатор поля станции метро
    private final By metroStationLocator = By.xpath("//input[@placeholder='* Станция метро']");
    // локатор станции метро из списка, как строка
    private final String metroStationMenuLocator = "//div[text()='%s']";
    // локатор телефона
    private final By telephoneLocator = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    //локатор кнопки "Далее"
    private final By nextButtonLocator = By.xpath("//button[text()='Далее']");
    // локатор даты привоза
    private final By dateOfDeliveryLocator = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    // локатор поля "Срок аренды"
    private final By rentalPeriodLocator = By.xpath("//div[text()='* Срок аренды']");
    //локатор списка "Срока аренды"
    private final String rentalPeriodListLocator = "//div[@class='Dropdown-option' and text()='%s']";
    //локатор кнопки 'Заказать'
    private final By orderButtonLocator = By.xpath("//div[@class='Order_Buttons__1xGrp']/button[text()='Заказать']");
    //локатор кнопки "Да" в оформлении заказа
    private final By yesOrder = By.xpath("//button[text()='Да']");
    // локатор о подтверждении заказа
    private final By orderCreateLocator = By.xpath("//*[text()='Заказ оформлен']");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void checkForWhomName(String name) {
        driver.findElement(By.xpath(String.format(orderNameLocator, name))).getText();
    }

    // метод заполнения формы "Для кого самокат"
    public void fillingFormWhoIsTheScooterOrder(String name, String surname, String address, String metroStation, String telephone) {
        WebElement nameInput = driver.findElement(nameLocator);
        nameInput.sendKeys(name);

        WebElement surnameInput = driver.findElement(surnameLocator);
        surnameInput.sendKeys(surname);

        WebElement addressInput = driver.findElement(addressLocator);
        addressInput.sendKeys(address);

        WebElement metroInput = driver.findElement(metroStationLocator);
        metroInput.click();
        //проскролить к нужной станции и кликнуть
        WebElement elementStation = driver
                .findElement(By.xpath(String.format(metroStationMenuLocator, metroStation)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", elementStation);
        elementStation.click();

        WebElement telephoneInput = driver.findElement(telephoneLocator);
        telephoneInput.sendKeys(telephone);

    }

    public void clickNextButton() {
        driver.findElement(nextButtonLocator).click();
    }

    // метод заполнения формы "Про аренду"
    public void fillingAboutRentForm(String date, String rentalPeriod) {
        WebElement dateInput = driver.findElement(dateOfDeliveryLocator);
        dateInput.sendKeys(date, Keys.ENTER);

        WebElement rentalPeriodInput = driver.findElement(rentalPeriodLocator);
        rentalPeriodInput.click();
        WebElement elementRentalPeriod = driver
                .findElement(By.xpath(String.format(rentalPeriodListLocator, rentalPeriod)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", elementRentalPeriod);
        elementRentalPeriod.click();
    }

    public void orderСonfirmation() {
        driver.findElement(orderButtonLocator).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).
                until(ExpectedConditions.presenceOfElementLocated(yesOrder));
        driver.findElement(yesOrder).click();
    }

    public void isCreateOrder() {
        driver.findElement(orderCreateLocator).isDisplayed();
    }
}
