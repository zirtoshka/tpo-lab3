package itma.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AutoCatalogPage extends CatalogPage {
    private final By tyresAndDisksCategory = By.xpath("//a[contains(@class, 'inverted-catalog-category__link') and .//h3[text()='Шины и диски']]");
    private final By tyresCategory = By.xpath("//a[contains(@class, 'inverted-catalog-category__link') and .//h3[text()='Шины']]");

    public AutoCatalogPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void openTyresAndDisks() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(tyresAndDisksCategory));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void openTyres() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(tyresCategory));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void selectDiameter(int value) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                WebElement element = driver.findElement(By.xpath("//div[contains(@class,'pui-checkbox__label') and normalize-space(text())='" + value + "']"));
                element.click();
                break;
            } catch (StaleElementReferenceException e) {
                attempts++;
            }
        }

    }
}
