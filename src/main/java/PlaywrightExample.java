import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class PlaywrightExample extends StringsHelper {
    private static Playwright playwright;
    private static Browser browser;
    private static Page mainPage;
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
        mainPage = browser.newPage();

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
        promotionPage = new PromotionPage(mainPage);
        pokerStarsRewardsPage = new PokerStarsRewardsPage(mainPage);
        mainPage.navigate(baseURL);
        // Check if the accept cookies button is visible before clicking it
        if (mainPage.isVisible(acceptCookiesButton)) {
            mainPage.click(acceptCookiesButton);
        } else {
            System.out.println("Accept cookies button is not visible. Skipping click.");
        }
    }

    @Test
    public void promotionPageSaveAllLinks() throws IOException {
        promotionPage.navigateToPromotions();
        assertEquals(promotionPage.getExpectedTitle(), promotionPage.getTitle(), "Page title is not as expected");
        testHelper.saveLinksToFile(mainPage);
    }

    @Test
    public void pokerStarsRewardsPageSaveAllLinks() throws IOException {
        pokerStarsRewardsPage.navigateToPokerStarsRewards();
        assertEquals(pokerStarsRewardsPage.getExpectedTitle(), pokerStarsRewardsPage.getTitle(), "Page title is not as expected");
        testHelper.saveLinksToFile(mainPage);
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        testHelper.captureScreenshot(mainPage, testInfo.getDisplayName());
    }
}
