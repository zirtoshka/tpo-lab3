package itma.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By productCards = By.cssSelector(".product-card__image-wrap");



    private By favoritesA = By.xpath("//a[contains(@class, 'navigation-tabs__item') and @href='/personal/favorites']");


    private By searchTab = By.xpath("//div[contains(@class, 'search-tab') and contains(@class, 'navigation-tabs__item_search')]");
    private By searchTextarea = By.xpath("//textarea[contains(@class, 'search-input__textarea')]");
    private By notFoundMessage = By.xpath("//div[contains(@class,'catalog-listing-not-found-regular__title') and contains(text(),'Мы это не нашли')]");
    private By firstProductCard = By.xpath("//div[contains(@class, 'catalog-item-regular-desktop') and @data-test='product-item']");


    public HomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
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



}
