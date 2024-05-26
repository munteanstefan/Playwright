import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class PlaywrightExample  {
    private static Playwright playwright;
    private static Browser browser;
    private static Page page;
    private MainPage mainPage;
    private PromotionPage promotionPage;
    private PokerStarsRewardsPage pokerStarsRewardsPage;
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
    public void startTestsAndHandleCookies() {
        promotionPage = new PromotionPage(page);
        pokerStarsRewardsPage = new PokerStarsRewardsPage(page);
        mainPage = new MainPage(page);
        mainPage.navigateToHomePage();

        // Check if the accept cookies button is visible before clicking it
        if (page.isVisible(mainPage.acceptCookiesButton)) {
            page.click(mainPage.acceptCookiesButton);
        } else {
            System.out.println("Accept cookies button is not visible. Skipping click.");
        }
    }

    @Test
    public void assertMainPageTitle(){
        mainPage.navigateToHomePage();
        mainPage.shouldSeeExpectedTitle();
    }

    @Test
    public void promotionPageSaveAllLinks() throws IOException {
        promotionPage.navigateToPromotions();
        assertEquals(promotionPage.getExpectedTitle(), promotionPage.getTitle(), "Page title is not as expected");
        testHelper.saveLinksToFile(page);
    }

    @Test
    public void pokerStarsRewardsPageSaveAllLinks() throws IOException {
        pokerStarsRewardsPage.navigateToPokerStarsRewards();
        assertEquals(pokerStarsRewardsPage.getExpectedTitle(), pokerStarsRewardsPage.getTitle(), "Page title is not as expected");
        testHelper.saveLinksToFile(page);
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        testHelper.captureScreenshot(page, testInfo.getDisplayName());
    }
}
