import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class TestHelper {


    public static void captureScreenshot(Page page, String testName) {
        if (page != null) {
            File screenshotDir = Paths.get("screenshots").toFile(); // Create a directory named "screenshots" near the class
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }
            //Remove last 2 characters from test "()"
            String screenshotPath = Paths.get("screenshots", testName.substring(0, testName.length() - 2) + ".png").toString();
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)));
        }
    }

    public static void saveLinksToFile(Page page) throws IOException {
        List<ElementHandle> linkElements = page.querySelectorAll("//a[@href]");

        // Create the "texts" folder if it doesn't exist
        File folderPath = Paths.get("texts").toFile();
        if (!folderPath.exists()) {
            folderPath.mkdirs();
        }

        // Write links to a text file inside the "texts" folder
        FileWriter writer = new FileWriter("texts/All_Promo_links.txt");
        for (ElementHandle link : linkElements) {
            // write the href value
            writer.write(link.getAttribute("href") + "\n");
        }
        writer.close();
    }
}



