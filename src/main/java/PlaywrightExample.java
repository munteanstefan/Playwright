import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PlaywrightExample extends StringsHelper {
    private static Playwright playwright;
    private static Browser browser;
    private static Page page;
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
    public void handleCookies() {
        page.navigate(baseURL);
        page.click(acceptCookisButton);
    }

    @Test
    public void promotionPageSaveAllLinks() throws IOException {
        page.click(promotions);
        assertEquals(promotionsPageTitle, page.title(), "Page title is not as expected");
        testHelper.saveLinksToFile(page);
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        testHelper.captureScreenshot(page, testInfo.getDisplayName());
    }
}
