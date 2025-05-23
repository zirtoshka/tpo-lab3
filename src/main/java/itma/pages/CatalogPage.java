package itma.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class CatalogPage extends BasePage {
    protected final By productCards = By.xpath("//div[@data-test='product-item']");
    protected final By inStockToggle = By.xpath("//span[text()='В наличии']/ancestor::div[contains(@class, 'pui-toggle')]//div[contains(@class,'pui-toggle-control')]");
    protected final By inStockLabelActive = By.xpath("//div[contains(@class,'pui-toggle')]//span[text()='В наличии']");

    public CatalogPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void open() {
        driver.get("https://megamarket.ru/catalog/");
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public boolean isProductListNotEmpty() {
        try {
            List<WebElement> cards = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productCards));
            return cards.size() > 0;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isCategoryVisible(String categoryName) {
        By categoryBy = By.xpath("//div[contains(@class,'catalog-category') and .//h3[text()='" + categoryName + "']]");
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(categoryBy)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void clickCategory(String categoryName) {
        By category = By.xpath("//a[contains(@class, 'inverted-catalog-category__link') and .//h3[text()='" + categoryName + "']]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(category));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void clickInStockToggle() {
        WebElement toggle = wait.until(ExpectedConditions.elementToBeClickable(inStockToggle));
        toggle.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(inStockLabelActive));
    }

    public void setPriceRange(int offsetRight, int offsetLeft) {
        WebElement rightSlider = driver.findElement(By.xpath("//button[contains(@class, 'range-ctrl-right')]"));
        WebElement leftSlider = driver.findElement(By.xpath("//button[contains(@class, 'range-ctrl-left')]"));

        new Actions(driver)
                .clickAndHold(rightSlider)
                .moveByOffset(-offsetRight, 0)
                .pause(Duration.ofMillis(300))
                .release()
                .perform();

        new Actions(driver)
                .clickAndHold(leftSlider)
                .moveByOffset(offsetLeft, 0)
                .pause(Duration.ofMillis(300))
                .release()
                .perform();
    }

    public void buyFirstProduct() {
        int attempts = 0;
        while (attempts < 2) {
            try {
                WebElement buyButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-test='buy-button']")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buyButton);
                break;
            } catch (StaleElementReferenceException e) {
                attempts++;
            }
        }
    }

    public boolean isOnUrl(String expectedUrl) {
        return wait.until(driver -> driver.getCurrentUrl().startsWith(expectedUrl));
    }
}