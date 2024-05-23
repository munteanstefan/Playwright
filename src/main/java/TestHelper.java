import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestHelper {

    public static void captureScreenshot(Page page, String testName) {
        if (page != null) {
            File screenshotDir = Paths.get("screenshots").toFile(); // Create a directory named "screenshots" near the class
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }
            String timestamp = getTimestamp();
            String screenshotPath = Paths.get("screenshots", testName.substring(0, testName.length() - 2) + "_" + timestamp + ".png").toString();
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)));
        }
    }

    public static void saveLinksToFile(Page page) throws IOException {
        List<ElementHandle> linkElements = page.querySelectorAll("//a[@href]");
        Set<String> uniqueLinks = new HashSet<>();

        for (ElementHandle link : linkElements) {
            String href = link.getAttribute("href");
            if (href != null && !href.trim().isEmpty()) {
                uniqueLinks.add(href.trim());
            }
        }

        File folderPath = Paths.get("texts").toFile();
        if (!folderPath.exists()) {
            folderPath.mkdirs();
        }

        String timestamp = getTimestamp();
        try (FileWriter writer = new FileWriter("texts/All_Links_" + timestamp + ".txt")) {
            for (String link : uniqueLinks) {
                writer.write(link + "\n");
            }
        }
    }

    private static String getTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"));
    }
}
