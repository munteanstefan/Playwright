import com.microsoft.playwright.Page;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PromotionPage {
    private final Page page;

    private final String promotions = "//*[@href='https://www.dafabet.com/en/promotions']";
    private final String promotionsPageTitle = "Dafabet offers the best online betting promotions!";

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

    public void assertHomePageTitle() {
        assertEquals(getExpectedTitle(), getTitle(), "Page title is not as expected");
    }
}
