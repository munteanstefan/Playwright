import com.microsoft.playwright.Page;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DafabetMainPage {
    String acceptCookiesButton = "//span[@class='cookie-notif-close']";
    String baseURL = "https://www.dafabet.com";
    String homePageTitle = "Dafabet is The Most Secure Online Betting Company in Asia";
    String loginErrorMessage = "Sorry the Username and Password you entered do not match. Please try again.";

    private Page page;

    public DafabetMainPage(Page page) {
        this.page = page;
    }

    public void handleCookies(){
        if (page.isVisible(acceptCookiesButton)) {
            page.click(acceptCookiesButton);
        } else {
            System.out.println("Accept cookies button is not visible. Skipping click.");
        }
    }
    public void navigateToHomePage() {
        page.navigate(baseURL);
    }

    public String getExpectedTitle() {
        return homePageTitle;
    }

    public String getTitle() {
        return page.title();
    }

    public void assertHomePageTitle() {
        assertEquals(getExpectedTitle(), getTitle(), "Page title is not as expected");
    }

    // Method to login
    public void login(String username, String password) {
        page.fill("//input[@id='LoginForm_username']", username);
        page.fill("//input[@id='LoginForm_password']", password);
        page.click("//button[@id='LoginForm_submit']");
    }

    // Method to get login error message
    public String getLoginErrorMessage() {
        return page.textContent("//div[@class='login-error']");
    }

    // Normalize string by trimming and collapsing whitespace
    private String normalizeString(String str) {
        return str.trim().replaceAll("\\s+", " ");
    }

    // Assertion method to validate login error message
    public void shouldSeeLoginErrorMessage() {
        String actualErrorMessage = getLoginErrorMessage();
        assertEquals(normalizeString(loginErrorMessage), normalizeString(actualErrorMessage), "Login error message is not as expected");
    }
}
