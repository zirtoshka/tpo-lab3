import itma.pages.HomePage;
import itma.pages.MulticartPage;
import itma.utils.WebDriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MulticartPageTest {

    WebDriver driver;
    MulticartPage multicartPage;
    private WebDriverWait wait;





    @BeforeEach
    void setup() {
        driver = WebDriverFactory.createDriver(System.getProperty("browser", "chrome"));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        multicartPage = new MulticartPage(driver, wait);

        HomePage homePage = new HomePage(driver, wait);
        homePage.open();
        homePage.goToMegamayAndClickBuyButton();
    }


    @Test
    void testClearCartUntilEmpty() {
        multicartPage.open();
        multicartPage.removeAllItemsFromCart();
        assertTrue(multicartPage.isCartEmpty(), "Корзина должна быть пустой");
    }



    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
