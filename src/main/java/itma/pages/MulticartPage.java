package itma.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MulticartPage extends BasePage {

    private By removeButtonLocator = By.cssSelector("button.good__remove");
//    private By emptyCartTextLocator = By.cssSelector("p.cart-empty__description");
    private By emptyCartTextLocator = By.xpath("//p[contains(@class, 'cart-empty__description')]");

    public MulticartPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void open() {
        driver.get("https://megamarket.ru/multicart/");
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

    }

    public boolean isCartEmpty() {
        return !driver.findElements(emptyCartTextLocator).isEmpty();
    }

    public void removeAllItemsFromCart() {
        while (true) {
            if (isCartEmpty()) {
                break;
            }

            List<WebElement> removeButtons = driver.findElements(removeButtonLocator);
            if (removeButtons.isEmpty()) {
                break;
            }

            WebElement removeButton = removeButtons.get(0);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", removeButton);
            wait.until(ExpectedConditions.elementToBeClickable(removeButton)).click();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
