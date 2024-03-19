public class Steuerung {
    public static void main(String[] args) {
        int anzahlThreads = 1;
        for (int i = 1; i <= anzahlThreads; i++) {
            if (anzahlThreads > 1) {
                Selenium uiTest = new Lasttest(i);
                Thread thread = new Thread(uiTest);
                thread.start();
            } else {
                Selenium uiTest = new Funktionalitaetstest(i);
                Thread thread = new Thread(uiTest);
                thread.start();
            }
        }
    }
}
