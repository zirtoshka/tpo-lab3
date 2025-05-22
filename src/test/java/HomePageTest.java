import itma.pages.HomePage;
import itma.utils.WebDriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomePageTest {
    WebDriver driver;
    HomePage homePage;
    private WebDriverWait wait;

    @BeforeEach
    void setup() {
        driver = WebDriverFactory.createDriver(System.getProperty("browser", "chrome"));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        homePage = new HomePage(driver, wait);
    }

    // главная страница -> есть ли карточки товаров
    @Test
    @DisplayName("Карточки товаров на клавной странице")
    void testBannerVisible() {
        homePage.open();
        assertTrue(homePage.isProductCardsVisible(), "нет карточек товаров на главной странице");
    }



    //    главная страница -> избранные товары
    @Test
    @DisplayName("Переход в Избранное с главной страницы")
    void shouldOpenFavoritesPageFromHome() {
        homePage.open();
        homePage.openFavorites();

        assertTrue(homePage.isFavoritesOpen(), "Страница избранного не открылась");
    }

    //главная страница -> клик по найти товар -> ввод буков -> ентер -> результат
    @Test
    @DisplayName("Поиск товара с главной страницы")
    void testSearchFunctionality() {
        homePage.open();
        homePage.openSearchTab();
        homePage.enterSearchQuery("ноутбук");
        assertTrue(homePage.isSearchResultCorrect(), "Результаты поиска отсутствуют или страница не отвечает");
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
