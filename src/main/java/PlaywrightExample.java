import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;


public class PlaywrightExample extends XpathVaraibles{
    private static Playwright playwright;
    private static Browser browser;
    private static String baseURL;
    private static Page page;

    @BeforeAll
    public static void Setup(){
    playwright = Playwright.create();
    BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(false);
    browser = playwright.firefox().launch(options);
    baseURL = "https://www.pokerstars.com/";
    page = browser.newPage();
    }

    @BeforeEach
    public void handleCookies(){
        page.navigate(baseURL);
        page.click(AcceptCookisButton);
    }

    @Test
    public void firstTest(){
        page.navigate(baseURL);

    }



    @AfterAll
    public static void tearDown() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
