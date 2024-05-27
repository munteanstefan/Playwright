import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.util.Arrays;

public class PlaywrightExample {
    private static Playwright playwright;
    private static Browser browser;
    private static Page page;
    private PromotionPage promotionPage;
    private DafabetVipPage dafabetVipPage;
    private static DafabetMainPage dafabetMainPage;
    TestHelper testHelper = new TestHelper();

    @BeforeAll
    public static void setup() {
        playwright = Playwright.create();
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(false);
                //   .setProxy("socks5://185.74.81.25:61504") // Can be used if needed different legislation or context
                //.setArgs(Arrays.asList("--start-maximized"));
        browser = playwright.chromium().launch(options);
        page = browser.newPage();
    }

    @AfterAll
    public static void cleanup() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    @BeforeEach
    public void handleCookies() {
        DafabetMainPage dafabetMainPage = new DafabetMainPage(page);
        dafabetMainPage.navigateToHomePage();
        // Check if the accept cookies button is visible before clicking it
        dafabetMainPage.handleCookies();
    }

    @Test
    public void tryToLoginWrongCredentials(){
        dafabetMainPage = new DafabetMainPage(page);
        dafabetMainPage.assertHomePageTitle();
        dafabetMainPage.login("username", "password");
        dafabetMainPage.shouldSeeLoginErrorMessage();
    }

    @Test
    public void promotionPageSaveAllLinks() throws IOException {
        promotionPage = new PromotionPage(page);
        promotionPage.navigateToPromotions();
        promotionPage.assertHomePageTitle();
        testHelper.saveLinksToFile(page);
    }

    @Test
    public void vipPageSaveAllLinks() throws IOException {
        dafabetVipPage = new DafabetVipPage(page);
        dafabetVipPage.navigateToDafabetVip();
        dafabetVipPage.assertHomePageTitle();
        testHelper.saveLinksToFile(page);
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        testHelper.captureScreenshot(page, testInfo.getDisplayName());
    }
}
