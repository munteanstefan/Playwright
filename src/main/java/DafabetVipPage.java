import com.microsoft.playwright.Page;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DafabetVipPage {

    private final String vipPage = "//*[contains(@href, '/vip')]";
    private final String vipPageTitle = "Be a Dafabet VIP today! Join Dafabet now!";

    private Page page;

    public DafabetVipPage(Page page) {
        this.page = page;
    }

    public void navigateToDafabetVip() {
        page.click(vipPage);
    }

    public String getExpectedTitle() {
        return vipPageTitle;
    }

    public String getTitle() {
        return page.title();
    }

    public void assertHomePageTitle() {
        assertEquals(getExpectedTitle(), getTitle(), "Page title is not as expected");
    }
}
