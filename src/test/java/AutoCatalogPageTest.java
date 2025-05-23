import itma.pages.AutoCatalogPage;
import itma.utils.WebDriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AutoCatalogPageTest {
    WebDriver driver;
    AutoCatalogPage autoCatalogPage;
    private WebDriverWait wait;

    @BeforeEach
    void setup() {
        driver = WebDriverFactory.createDriver(System.getProperty("browser", "chrome"));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        autoCatalogPage = new AutoCatalogPage(driver, wait);
    }


    // каталог страница -> есть ли категории
    @Test
    @DisplayName("категории товаров на странице каталогов")
    void testBannerVisible() {
        autoCatalogPage.open();

        assertTrue(autoCatalogPage.isCategoryVisible("Автотовары"), "нет категорий товаров на странице каталога");
    }


    // каталог страница -> автотовары -> шины и диски -> шины -> фильтр
    // -> в корзину
    @Test
    @DisplayName("категории товаров на странице каталогов")
    void testBuyTyre() {
        autoCatalogPage.open();

        autoCatalogPage.clickCategory("Автотовары");
        assertTrue(
                autoCatalogPage.isOnUrl("https://megamarket.ru/catalog/avtotovary/"),
                "Не перешли в категорию 'Автотовары'"
        );
        autoCatalogPage.openTyresAndDisks();
        assertTrue(
                autoCatalogPage.isOnUrl("https://megamarket.ru/catalog/shiny-i-diski/"),
                "Не перешли в категорию 'Шины и диски'"
        );

        autoCatalogPage.openTyres();
        assertTrue(
                autoCatalogPage.isOnUrl("https://megamarket.ru/catalog/shiny/"),
                "Не перешли в категорию 'Шины'"
        );


        autoCatalogPage.clickInStockToggle();
        autoCatalogPage.setPriceRange(20,  30);
        autoCatalogPage.selectDiameter(20);


        assertTrue(autoCatalogPage.isProductListNotEmpty(), "Нет товаров после фильтрации");

        autoCatalogPage.buyFirstProduct();
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
