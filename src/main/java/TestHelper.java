import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.*;
import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class TestHelper {

    public static void captureScreenshot(Page page, String testName) {
        if (page != null) {
            File screenshotDir = Paths.get("screenshots").toFile(); // Create a directory named "screenshots" near the class
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }
            String timestamp = getTimestamp();

            // remove the braces from the  TestCase name
            String screenshotPath = Paths.get("screenshots", testName.substring(0, testName.length() - 2) + "_" + timestamp + ".png").toString();
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)));
        }
    }

    public static void saveLinksToFile(Page page) throws IOException {
        // Use an event listener to wait for the load event
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        // Query all <a> elements with an 'href' attribute on the page
        List<ElementHandle> linkElements = page.querySelectorAll("//a[@href]");

        // Create a set to store unique links
        Set<String> uniqueLinks = new HashSet<>();

        for (ElementHandle link : linkElements) {
            // Get the value of the 'href' attribute
            String href = link.getAttribute("href");

            // Check if the 'href' value is not null or empty after trimming
            if (href != null && !href.trim().isEmpty()) {
                // Add the trimmed 'href' value to the set of unique links
                uniqueLinks.add(href.trim());
            }
        }

        createTextsDirectoryIfNotExists();
        String timestamp = getTimestamp();
        writeLinksToFile(uniqueLinks, timestamp);
    }

    private static void createTextsDirectoryIfNotExists() {
        File textsDirectory = Paths.get("texts").toFile();
        if (!textsDirectory.exists()) {
            textsDirectory.mkdirs();
        }
    }

    private static void writeLinksToFile(Set<String> uniqueLinks, String timestamp) throws IOException {
        try (FileWriter writer = new FileWriter("texts/All_Links_" + timestamp + ".txt")) {
            for (String link : uniqueLinks) {
                writer.write(link + "\n");
            }
        }
    }

    private static String getTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss HH_dd_MM_yyyy"));
    }

    public static void assertElementContainsText(Page page, String xpath, String expectedText) {
        // Find the element by XPath
        String actualText = page.textContent(xpath);

        // Assert that the element contains the expected text
        if (actualText.contains(expectedText)) {
            System.out.println("Element contains the expected text: " + expectedText);
        } else {
            System.err.println("Element does not contain the expected text: " + expectedText);
        }
    }

    public static void getQuote() throws JSONException {
        // Make a GET request to the API endpoint
        Response response = RestAssured.get("https://api.quotable.io/random");

        // Check if the response is successful
        if (response.getStatusCode() == 200) {
            // Get the JSON response body
            String responseBody = response.getBody().asString();

            // Parse the JSON response
            JSONObject jsonObject = new JSONObject(responseBody);

            // Extract the "content" field from the JSON object
            String content = jsonObject.getString("content");

            System.out.println("Quote of the Day: " + content);
        } else {
            System.err.println("Failed to fetch quote. Status code: " + response.getStatusCode());
        }
    }
}
