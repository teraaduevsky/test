package com.webdriver.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

//I must split this class to several "logical" class but i did it in one to save mine time
public class TestPage {

    private WebDriver driver;
    public static int wait;

    private static final String provinceNameXpath = "//div[@class = 'dropdown-menu'][@id=\"provinces\"]//div[contains(text(),'%s')]";
    private static final String regionXpath = "//ul[@select_key='region_id']//a[text()='%s']";
    private static final String imgCheckerXpath = "//img[@itemprop='image']";
    private static final String cityXpath = "//span[@class=\"desc__info\"][contains(text(),'%s')]";
    private static final String textFinderXpath = "//div[@class=\"text\"][contains(text(),'%s')]";

    @FindBy(id = "selected_region_name")
    private WebElement selectRegion;
    @FindBy(id = "more-filters")
    private WebElement moreFiltersButton;
    @FindBy(xpath = "//label[@for='has_photos']")
    private WebElement hasPhotos;
    // can be parametrize as province and region
    @FindBy(xpath = "//label[@for='city_vancouver']")
    private WebElement vancouver;
    @FindBy(id = "form-search")
    private WebElement searchForm;
    // xpath here is more stable locator
    @FindBy(xpath = "//button[text()='Show Ads']")
    private WebElement showAdsButton;
    @FindBy(id = "view-cont")
    private WebElement resultSet;

    private WebElement waitElement(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    //    just to show that province select can be parametrize if it needed
    private WebElement province(String value) {
        String locator = String.format(provinceNameXpath, value);
        return driver.findElement(By.xpath(locator));
    }

    private WebElement region(String value) {
        String locator = String.format(regionXpath, value);
        return driver.findElement(By.xpath(locator));
    }

    private boolean checkImage(WebElement element) {
        return !element.findElements(By.xpath(imgCheckerXpath)).isEmpty();
    }

    private boolean checkCity(WebElement element, String cityName) {
        String locator = String.format(cityXpath, cityName);
        return !element.findElements(By.xpath(locator)).isEmpty();
    }

    private boolean checkContainsText(WebElement element, String text) {
        String locator = String.format(textFinderXpath, text);
        return !element.findElements(By.xpath(locator)).isEmpty();
    }

    private List<WebElement> resultList() {
        return waitElement(resultSet).findElements(By.className("group"));
    }

    public boolean elementExists(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    public void selectRegionAndProvince(String province, String region) {
        clickSelectRegion();
        waitElement(province(province)).click();
        waitElement(region(region)).click();
    }

    public void search(String value) {
//        waitElement(searchForm).click();
//        searchForm.sendKeys(value);
        Actions actions = new Actions(driver);
        actions.moveToElement(waitElement(searchForm));
        actions.click();
        actions.sendKeys(value);
        actions.build().perform();
    }

    // just to show that i can write in different ways
    public boolean checkAllHaveImage() {
        WebElement withoutImg;
        withoutImg = resultList().stream()
                .filter(element -> !checkImage(element))
                .findAny()
                .orElse(null);

        return withoutImg == null;
    }

    // just to show that i can write in different ways
    public boolean checkAllCity(String city) {

        for (WebElement element: resultList()) {
            if (!checkCity(element, city)) return false;
        }
        return true;
    }

    public boolean checkAllText(String text) {
        for (WebElement element: resultList()) {
            if (!checkContainsText(element, text)) return false;
        }
        return true;
    }

    public TestPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickSelectRegion() {
        waitElement(selectRegion).click();
    }

    public void clickMoreFilters() {
        waitElement(moreFiltersButton).click();
    }

    public void clickHasPhotos() {
        waitElement(hasPhotos).click();
    }

    public void clickVancouver() {
        waitElement(vancouver).click();
    }

    public  void clickShowAdsButton() {
        waitElement(showAdsButton).click();
    }

}