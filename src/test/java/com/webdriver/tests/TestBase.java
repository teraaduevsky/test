package com.webdriver.tests;


import com.webdriver.pages.*;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public abstract class TestBase {
    private static final Logger log = LogManager.getLogger(TestBase.class);

    WebDriver driver;
    TestPage testPage;

    @BeforeClass
    public static void setupClass() {
        ChromeDriverManager.getInstance().setup();
    }

    @Before
    public void setupTest() {
        log.info("loading property for env:" + System.getenv("ENV"));
        driver = new ChromeDriver();
        // it's better to parametrize dimension to test page scaling
        driver.manage().window().setSize(new Dimension(1200, 850));
        driver.manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);


        //page object init
        testPage = PageFactory.initElements(driver, TestPage.class);
    }

    @After
    public void tearDownTest() {
        driver.quit();
    }

}
