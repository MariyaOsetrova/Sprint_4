import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.osetrova.WebDriverFactory;
import ru.osetrova.pageobject.HomePage;
import ru.osetrova.pageobject.OrderPage;

import static ru.osetrova.config.config.URL;

@RunWith(Parameterized.class)
public class OrderTest {
    private WebDriver driver;

    @Before
    public void startUp() {

        driver = WebDriverFactory.getDriver("chrome");
        driver.manage().window().maximize();
        driver.get(URL);
    }

    private final int orderButton;
    private final String name;
    private final String surname;
    private final String address;
    private final String station;
    private final String telephone;
    private final String date;
    private final String rentalPeriod;

    public OrderTest(int orderButton, String name, String surname, String address, String station, String telephone, String date, String rentalPeriod) {
        this.orderButton = orderButton;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.station = station;
        this.telephone = telephone;
        this.date = date;
        this.rentalPeriod = rentalPeriod;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderParameters() {
        return new Object[][]{
                {0, "Анна", "Иванова", "ул. Калатушкина", "Комсомольская", "+79159999999", "15.06.2024", "трое суток"},
                {1, "Владимир", "Владимирович", "ул. Владимирская", "Измайловская", "+79158888888", "01.01.2025", "сутки"},
        };
    }

    @Test
    public void orderTest() {
        HomePage homePage = new HomePage(driver);
        OrderPage orderPage = new OrderPage(driver);
        homePage.scrollToOrderButton(orderButton);
        homePage.getOrderButton().get(orderButton).click();
        orderPage.checkForWhomName("Для кого самокат");
        orderPage.checkForWhomName("Для кого самокат");
        orderPage.fillingFormWhoIsTheScooterOrder(name, surname, address, station, telephone);
        orderPage.clickNextButton();
        orderPage.checkForWhomName("Про аренду");
        orderPage.fillingAboutRentForm(date, rentalPeriod);
        orderPage.orderСonfirmation();
        //в хроме упадет,т.к не возможно нажать на подтверждение
        orderPage.isCreateOrder();

    }

    @After
    public void closeWindow() {
        driver.quit();
    }
}
