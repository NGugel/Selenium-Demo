public class Funktionalitaetstest extends Selenium {

    public Funktionalitaetstest(int threadNumber) {
        super(threadNumber);
        init();
    }

    public void runUItest() {
        logger.info(progress, "##### Thread {} starts a sequence in a UI-test (Funktionalitätstest)!", threadNumber);
        clickAlleAblehnenOnCookiePopup();
        searchFor("software qualität");
        waitInMillis(10000);
        clickOnTrends();
        waitInMillis(10000);
        cleanUpDrivers();
    }

}
