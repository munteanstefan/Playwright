import com.microsoft.playwright.Page;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainPage {
    private final Page page;

    String acceptCookiesButton = "//button[@id='onetrust-accept-btn-handler']";
    private final String baseURL = "https://www.pokerstars.com/";
    private final String homePageTitle = "Online Poker – Play Poker Games at PokerStars™";

    public MainPage(Page page) {
        this.page = page;
    }

    public void navigateToHomePage() {
        page.navigate(baseURL);
    }

    public String getTitle() {
        return page.title();
    }

    public String getExpectedTitle() {
        return homePageTitle;
    }

    public void shouldSeeExpectedTitle(){
        assertEquals(getExpectedTitle(), getTitle(), "Page title is not as expected");
    }
}