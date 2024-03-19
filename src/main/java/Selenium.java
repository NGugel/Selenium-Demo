import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.time.Duration;

abstract class Selenium implements Runnable {

    final int timeoutThresholdInMillis = 20000;
    final int waitIntervallTimerinMillis = 500;
    final int maximumAmountOfTriesToExecuteMethod = 40;
    final String url = "https://youtube.com";


    int threadNumber;
    int amountOfTriesToExecuteMethodCounter;
    long start;
    long end;

    Marker progress = MarkerFactory.getMarker("PROGRESS");
    Logger logger;
    WebDriver driver;
    FluentWait wait;
    Actions actions;

    public Selenium(int threadNumber) {
        this.threadNumber = threadNumber;
        logger = LoggerFactory.getLogger(Selenium.class);
    }

    @Override
    public void run() {
        runUItest();
    }

    public void runUItest() {
        System.out.println("test");
        logger.info("##### Thread {} can't run a UI-test, since there is no sequence defined!", threadNumber);
    }

    public void init() {
        start = System.currentTimeMillis();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origin=*");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(chromeOptions);
        wait = new FluentWait(driver);
        //actions = new Actions(driver);
        wait.withTimeout(Duration.ofMillis(timeoutThresholdInMillis));
        wait.pollingEvery(Duration.ofMillis(waitIntervallTimerinMillis));
        wait.ignoring(NoSuchElementException.class);
        driver.get(url);
        driver.manage().window().maximize();
    }

    public void clickAlleAblehnenOnCookiePopup() {
        try {
            String xpathAlleAblehnenButton = "//button[@aria-label='Verwendung von Cookies und anderen Daten zu den beschriebenen Zwecken ablehnen']";
            //fluent wait
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathAlleAblehnenButton)));
            driver.findElement(By.xpath(xpathAlleAblehnenButton)).click();
            logger.info(progress, "Thread {} clicked on 'Alle ablehnen'!", threadNumber);
        } catch(Exception e) {
            logger.error("Thread {} had the following error: ", threadNumber, e);
        }
    }

    public void searchFor(String input) {
        try {
            if(amountOfTriesToExecuteMethodCounter >= maximumAmountOfTriesToExecuteMethod) {
                amountOfTriesToExecuteMethodCounter = 0;
                return;
            }
            String xpathSearchInput = "//input[@id='search']";
            String xpathSearchButton = "//button[@id='search-icon-legacy']";
            //fluent wait
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathSearchInput)));
            driver.findElement(By.xpath(xpathSearchInput)).sendKeys(input);
            logger.info(progress, "Thread {} typed {} into the search field!", threadNumber, input);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathSearchButton)));
            driver.findElement(By.xpath(xpathSearchButton)).click();
            logger.info(progress, "Thread {} clicked on search button!", threadNumber);

        } catch(Exception e) {
            logger.error("Thread {} had the following error: ", threadNumber, e);
            amountOfTriesToExecuteMethodCounter++;
            searchFor(input);
        }
    }

    public void cleanUpDrivers() {
        driver.close();
        driver.quit();
        end = System.currentTimeMillis();
        long timeToExecuteUItest = end - start;
        logger.info(progress, "Thread {} terminated successfully!", threadNumber);
        logger.info(progress, "+++++ Thread {} terminated in {} milliseconds ({} seconds)", threadNumber, timeToExecuteUItest, timeToExecuteUItest/1000);
    }

    public void waitInMillis(long timeInMillis) {
        try {
            Thread.sleep(timeInMillis);
            logger.info(progress, "Thread {} waited {} milliseconds ({} seconds)!", threadNumber, timeInMillis, timeInMillis/1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void clickOnTrends() {
        try {
            if(amountOfTriesToExecuteMethodCounter >= maximumAmountOfTriesToExecuteMethod) {
                amountOfTriesToExecuteMethodCounter = 0;
                return;
            }
            String xpathTrends = "//yt-formatted-string[text()='Trends']";
            //fluent wait
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathTrends)));
            driver.findElement(By.xpath(xpathTrends)).click();
            logger.info(progress, "Thread {} clicked on Trends!", threadNumber);

        } catch(Exception e) {
            logger.error("Thread {} had the following error: ", threadNumber, e);
            amountOfTriesToExecuteMethodCounter++;
            clickOnTrends();
        }
    }
}
