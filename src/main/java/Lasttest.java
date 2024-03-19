public class Lasttest extends Selenium {
    public Lasttest(int threadNumber) {
        super(threadNumber);
        init();
    }

    public void runUItest() {
        logger.info(progress, "##### Thread {} starts a sequence in a UI-test (Lasttest)!", threadNumber);
        clickAlleAblehnenOnCookiePopup();
        searchFor("software qualität");
        waitInMillis(10000);
        clickOnTrends();
        waitInMillis(10000);
        cleanUpDrivers();
    }

}
