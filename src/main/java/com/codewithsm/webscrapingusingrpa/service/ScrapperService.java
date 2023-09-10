package com.codewithsm.webscrapingusingrpa.service;


import com.codewithsm.webscrapingusingrpa.model.ImdbModel;
import com.codewithsm.webscrapingusingrpa.repository.MyRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ScrapperService {

    @Autowired
    public MyRepository repository;


    public ChromeDriver driver(){
        return new ChromeDriver();
    }

    private String url = "https://www.imdb.com/chart/top/";

    public String scrapeAndSaveData() {

        int count = 1;

        List<ImdbModel> entityList = new ArrayList<>();
        // Configure Selenium WebDriver
        System.setProperty("webdriver.chrome.driver","/RPA_Projects/ChromeDriver/chromedriver-win64/chromedriver.exe");

        WebDriver driver = driver();

        driver.get(url);

        while (count<=250){

            //            movies name scrapping
            final WebElement names = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[3]/section/div/div[2]/div/ul/li["+count+"]/div[2]/div/div/div[1]/a/h3"));
            String strName = names.getText();
            String [] strArr = strName.split("[.]");

            //            released year
            final String years= driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[3]/section/div/div[2]/div/ul/li["+count+"]/div[2]/div/div/div[2]/span[1]")).getText();

            //              duration
            final String duration = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[3]/section/div/div[2]/div/ul/li["+count+"]/div[2]/div/div/div[2]/span[2]")).getText();

            //              guidance_rating
            String guidanceRating;
            try {
                guidanceRating = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[3]/section/div/div[2]/div/ul/li[" + count + "]/div[2]/div/div/div[2]/span[3]")).getText();
            }catch (Exception e){
                guidanceRating = "null";
            }

            //            star rating
            final WebElement starRating = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[3]/section/div/div[2]/div/ul/li["+count+"]/div[2]/div/div/span"));
            String starText = starRating.getText();
            String [] starArr = starText.split("[ ]");

            //            extract link
            final String link = driver.findElement(By.linkText(strName)).getAttribute("href");

            entityList.add(new ImdbModel(strArr[1],years,link,duration,guidanceRating,starArr[0]));

            count++;

        }

        for (ImdbModel link: entityList) {
            driver.get(link.getDescription());

            // get an instance of WebDriverWait
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

            // Scroll to the bottom of the page
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, 3500);");

            try {

                final String description = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-testid='storyline-plot-summary'] div>div>div"))).getText();
                link.setDescription(description);

            }catch (Exception e){
                link.setDescription("Time Out");
            }
        }
        repository.saveAll(entityList);
        // Close the WebDriver
        driver.quit();

        return "All Scraped Data Is Saved to DataBase";

    }

}
