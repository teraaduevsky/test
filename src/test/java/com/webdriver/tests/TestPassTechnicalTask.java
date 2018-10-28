package com.webdriver.tests;

import org.junit.Before;
import org.junit.Test;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.runner.RunWith;
import org.assertj.core.api.SoftAssertions;

@RunWith(JUnitParamsRunner.class)
public class TestPassTechnicalTask extends TestBase{

    private SoftAssertions softAssertions;

    @Before
    public void initPageObjects(){
        driver.navigate().to("https://www.leolist.cc/motors/parts/calgary");
        softAssertions = new SoftAssertions();
    }

    @Test
    @Parameters({"British Columbia, Metro Vancouver, Scrap Car, Vancouver"})
    public void testMyTask(String province, String region, String search, String city) throws InterruptedException {

        testPage.selectRegionAndProvince(province, region);
        testPage.clickMoreFilters();
        testPage.clickHasPhotos();
        testPage.clickVancouver();
        testPage.search(search);
        testPage.clickShowAdsButton();

        softAssertions.assertThat(testPage.checkAllCity(city)).isTrue();
        softAssertions.assertThat(testPage.checkAllText(search)).isTrue();
        softAssertions.assertThat(testPage.checkAllHaveImage()).isTrue();
    }


}
