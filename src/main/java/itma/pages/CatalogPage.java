package itma.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CatalogPage extends BasePage {
    private final By categoryCards = By.xpath("//div[@class='inverted-catalog-category__content-wrapper' and .//h3[text()='Супермаркет']]");

    private final By autoGoodsCategory = By.xpath("//a[contains(@href, '/catalog/avtotovary/') and .//h3[normalize-space(text())='Автотовары']]");
    private final By tyresAndDisksCategory = By.xpath("//h3[text()='Шины и диски']/ancestor::a");
    private final By tyresCategory = By.xpath("//h3[text()='Шины']/ancestor::a");


    // В наличии — toggle switch
//    By.xpath("//span[text()='В наличии']/ancestor::div[contains(@class, 'pui-toggle')]//div[contains(@class,'pui-toggle-control')]"
    private final By inStockToggle = By.xpath("//span[text()='В наличии']/ancestor::div[contains(@class, 'pui-toggle')]//div[contains(@class,'pui-toggle-control')]");

    // Проверка, что "В наличии" применён
    private final By inStockLabelActive = By.xpath("//div[contains(@class,'pui-toggle')]//span[text()='В наличии']");

    // Поля ввода цены
    private final By minPriceInput = By.xpath("//div[@class='range-inputs']/label[span[text()='от']]/input");
    private final By maxPriceInput = By.xpath("//div[@class='range-inputs']/label[span[text()='до']]/input");

    private final By diameter20 = By.xpath("//div[contains(@class,'pui-checkbox__label') and normalize-space(text())='20']");

    // Кнопка "Показать ещё"
    private final By showMoreSpeedIndex = By.xpath("//div[contains(@class, 'filter__show-more')]");

    // Пример: ZR (свыше 240 км/ч)
    private final By speedIndexZR = By.xpath("//div[contains(@class,'pui-checkbox__label') and contains(text(),'ZR')]");

    private final By productCards = By.xpath("//div[@data-test='product-item']");


    public CatalogPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void open() {
        driver.get("https://megamarket.ru/catalog/");
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

    }


    public boolean isCategoryCardsVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(categoryCards)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }


    public void clickAutoGoods() {
        wait.until(ExpectedConditions.elementToBeClickable(autoGoodsCategory)).click();
    }

    public void clickTyresAndDisks() {
        wait.until(ExpectedConditions.elementToBeClickable(tyresAndDisksCategory)).click();
    }

    public void clickTyres() {
        wait.until(ExpectedConditions.elementToBeClickable(tyresCategory)).click();
    }

    public boolean isOnUrl(String expectedUrl) {
        return wait.until(driver -> driver.getCurrentUrl().startsWith(expectedUrl));
    }


    public void clickInStockToggle() {
        WebElement toggle = wait.until(ExpectedConditions.elementToBeClickable(inStockToggle));
        toggle.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(inStockLabelActive));
    }

    public void setPriceRange() {
        WebElement rightSlider = driver.findElement(By.xpath("//button[contains(@class, 'range-ctrl-right')]"));

        Actions actions = new Actions(driver);

        actions.clickAndHold(rightSlider).moveByOffset(-50, 0).pause(Duration.ofMillis(200))
                .moveByOffset(-30, 0).pause(Duration.ofMillis(200))
                .release().perform();

    }


    public void selectDiameter20() {
        WebElement checkbox20 = wait.until(ExpectedConditions.elementToBeClickable(diameter20));
        checkbox20.click();
    }


    public boolean isProductListNotEmpty() {
        try {
            List<WebElement> cards = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productCards));
            return cards.size() > 0;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void buyProduct(){
//        WebElement buyButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-test='buy-button']")));
//        buyButton.click();

//        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-test='buy-button']"))).click();
        int attempts = 0;
        while (attempts < 2) {
            try {
                WebElement buyButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-test='buy-button']")));
                buyButton.click();
                break;
            } catch (StaleElementReferenceException e) {
                attempts++;
            }
        }

    }


}
