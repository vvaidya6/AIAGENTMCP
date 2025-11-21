import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

public class AddIphoneXToCartTest {
    @Test
    public void loginAddIphoneXToCartAndCheckout() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();

            // Navigate to login page
            page.navigate("https://rahulshettyacademy.com/loginpagePractise/");

            // Fill in login credentials
            page.fill("#username", "rahulshettyacademy");
            page.fill("#password", "learning");
            page.click("#signInBtn");

            // Wait for navigation to shop page
            page.waitForURL("**/angularpractice/shop");

            // Find the iPhone X card and click 'Add'
            int count = page.locator(".card-title").count();
            for (int i = 0; i < count; i++) {
                String title = page.locator(".card-title").nth(i).textContent();
                if (title != null && title.contains("iphone X")) {
                    page.locator("button.btn-info").nth(i).click();
                    break;
                }
            }

            // Go to cart
            page.click("a.nav-link.btn.btn-primary");

            // Assert iPhone X is in the cart
            int cartCount = page.locator("h4.media-heading").count();
            boolean found = false;
            for (int i = 0; i < cartCount; i++) {
                String cartTitle = page.locator("h4.media-heading").nth(i).textContent();
                if (cartTitle != null && cartTitle.contains("iphone X")) {
                    found = true;
                    break;
                }
            }
            Assertions.assertTrue(found, "iPhone X should be in the cart");

            // Proceed to checkout
            page.click("button.btn-success");

            // Optionally, confirm checkout page loaded
            // page.waitForSelector("input[placeholder='Select Country']");

            browser.close();
        }
    }
}
