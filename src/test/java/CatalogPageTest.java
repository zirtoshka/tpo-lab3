import itma.pages.CatalogPage;
import itma.utils.WebDriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CatalogPageTest {
    WebDriver driver;
    CatalogPage catalogPage;
    private WebDriverWait wait;

    @BeforeEach
    void setup() {
        driver = WebDriverFactory.createDriver(System.getProperty("browser", "firefox"));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        catalogPage = new CatalogPage(driver, wait);
    }


    // каталог страница -> есть ли категории
    @Test
    @DisplayName("категории товаров на странице каталогов")
    void testBannerVisible() {
        catalogPage.open();
        assertTrue(catalogPage.isCategoryCardsVisible(), "нет категорий товаров на странице каталога");
    }


    // каталог страница -> автотовары -> шины и диски -> шины -> фильтр
    // -> в корзину
    @Test
    @DisplayName("категории товаров на странице каталогов")
    void testBuyTyre() {
        catalogPage.open();

        catalogPage.clickAutoGoods();
        assertTrue(
                catalogPage.isOnUrl("https://megamarket.ru/catalog/avtotovary/"),
                "Не перешли в категорию 'Автотовары'"
        );

        catalogPage.clickTyresAndDisks();
        assertTrue(
                catalogPage.isOnUrl("https://megamarket.ru/catalog/shiny-i-diski/"),
                "Не перешли в категорию 'Шины и диски'"
        );

        catalogPage.clickTyres();
        assertTrue(
                catalogPage.isOnUrl("https://megamarket.ru/catalog/shiny/"),
                "Не перешли в категорию 'Шины'"
        );


        catalogPage.clickInStockToggle();
        catalogPage.setPriceRange();
        catalogPage.selectDiameter20();


        assertTrue(catalogPage.isProductListNotEmpty(), "Нет товаров после фильтрации");

        catalogPage.buyProduct();
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
