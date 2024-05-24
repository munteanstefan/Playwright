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
    private DafabetMainPage dafabetMainPage;
    TestHelper testHelper = new TestHelper();

    @BeforeAll
    public static void Setup() {
        playwright = Playwright.create();
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(false)
                //   .setProxy("socks5://185.74.81.25:61504") // Can be used if needed different legislation or context
                .setArgs(Arrays.asList("--start-fullscreen"));
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
    public void initializePagesAndNavigateToHomePage() {
        dafabetMainPage = new DafabetMainPage(page);
        promotionPage = new PromotionPage(page);
        dafabetVipPage = new DafabetVipPage(page);
        dafabetMainPage.navigateToHomePage();

        // Check if the accept cookies button is visible before clicking it
//        if (mainPage.isVisible(acceptCookiesButton)) {
//            mainPage.click(acceptCookiesButton);
//        } else {
//            System.out.println("Accept cookies button is not visible. Skipping click.");
//        }
    }

    @Test
    public void promotionPageSaveAllLinks() throws IOException {
        promotionPage.navigateToPromotions();
        promotionPage.shouldSeeExpectedTitle();
        testHelper.saveLinksToFile(page);
    }

    @Test
    public void dafabetVipPageSaveAllLinks() throws IOException {
        dafabetVipPage.navigateToDafabetVip();
        dafabetVipPage.shouldSeeExpectedTitle();
        testHelper.saveLinksToFile(page);
    }

    @Test
    public void tryToLoginWrongCredentials(){
        dafabetMainPage.shouldSeeExpectedTitle();
        dafabetMainPage.login("username", "password");
        dafabetMainPage.shouldSeeLoginErrorMessage();
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        testHelper.captureScreenshot(page, testInfo.getDisplayName());
    }
}
