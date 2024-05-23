import com.microsoft.playwright.Page;


public class PokerStarsRewardsPage {
    private Page page;

    public PokerStarsRewardsPage(Page page) {
        this.page = page;
    }

    public void navigateToPokerStarsRewards() {
        page.click("//*[contains(@href, '/stars-rewards/')]");
    }

    public String getExpectedTitle() {
        return "Online Poker – Play Poker Games at PokerStars™";
    }

    public String getTitle() {
        return page.title();
    }
}
