
import com.microsoft.playwright.Page;

public class PromotionPage {
    private final Page page;

    private final String promotions = "//*[@href='https://www.pokerstars.com/poker/promotions/']";
    private final String promotionsPageTitle = "Poker Promotions - Online Poker Deposit, Reload and Sign Up Bonuses";

    public PromotionPage(Page page) {
        this.page = page;
    }

    public void navigateToPromotions() {
        page.click(promotions);
    }

    public String getTitle() {
        return page.title();
    }

    public String getExpectedTitle() {
        return promotionsPageTitle;
    }
}
