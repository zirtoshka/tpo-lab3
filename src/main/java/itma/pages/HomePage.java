package itma.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class HomePage extends BasePage {

    private final By productCards = By.cssSelector(".product-card__image-wrap");


    private final By favoritesA = By.xpath("//a[contains(@class, 'navigation-tabs__item') and @href='/personal/favorites']");


    private final By searchTab = By.xpath("//div[contains(@class, 'search-tab') and contains(@class, 'navigation-tabs__item_search')]");
    private final By searchTextarea = By.xpath("//textarea[contains(@class, 'search-input__textarea')]");
    private final By notFoundMessage = By.xpath("//div[contains(@class,'catalog-listing-not-found-regular__title') and contains(text(),'Мы это не нашли')]");
    private final By firstProductCard = By.xpath("//div[contains(@class, 'catalog-item-regular-desktop') and @data-test='product-item']");


    private final By addressButton = By.xpath("//button[contains(@class, 'address-button')]");
    private final By modalHeader = By.xpath("//div[contains(@class,'my-modal-header__title') and contains(text(),'Адрес доставки')]");
    private final By canvasMap = By.xpath("//canvas");
    private final By addressInput = By.xpath("//input[@name='address-string' and contains(@class, 'search-field-input')]");
    private final By addButton = By.xpath("//button[contains(@class, 'pui-button-element') and .//span[contains(text(),'Добавить')]]");
    private final By confirmedAddress = By.xpath("//button[contains(@class, 'address-button')]//span[contains(@class,'address-button__text')]");

    public HomePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }


    public void open() {
        driver.get("https://megamarket.ru/");
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    public boolean isProductCardsVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(productCards)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }


    public void openFavorites() {
        wait.until(ExpectedConditions.elementToBeClickable(favoritesA)).click();
        wait.until(ExpectedConditions.urlContains("/personal/favorites"));

    }

    public boolean isFavoritesOpen() {
        return driver.getCurrentUrl().contains("/personal/favorites");
    }


    public void openSearchTab() {
        wait.until(ExpectedConditions.elementToBeClickable(searchTab)).click();
    }

    public void enterSearchQuery(String query) {
        WebElement textarea = wait.until(ExpectedConditions.visibilityOfElementLocated(searchTextarea));
        textarea.clear();
        textarea.sendKeys(query + Keys.ENTER);
    }

    public boolean isSearchResultCorrect() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(notFoundMessage),
                    ExpectedConditions.visibilityOfElementLocated(firstProductCard)
            ));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }


    public void clickAddressButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addressButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(modalHeader));
    }


    public void clickOnMapCenter() {
        WebElement canvas = wait.until(ExpectedConditions.visibilityOfElementLocated(canvasMap));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", canvas);
        new Actions(driver)
                .moveToElement(canvas, 1, 1)
                .click()
                .perform();
    }

    public boolean isAddressInputFilled() {
        wait.until(driver -> !driver.findElement(addressInput).getAttribute("value").isEmpty());
        String value = driver.findElement(addressInput).getAttribute("value");
        System.out.println("jopa" +
                value
        );
        return value != null && !value.isEmpty() && !value.contains("Город, улица, номер дома");
    }

    public void clickAddButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
    }

    public String getConfirmedAddress() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmedAddress)).getText();
    }


}
