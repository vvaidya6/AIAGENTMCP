import { test, expect } from '@playwright/test';

test('login, add iPhone X to cart, and checkout', async ({ page }) => {
  // Navigate to login page
  await page.goto('https://rahulshettyacademy.com/loginpagePractise/');

  // Fill in login credentials
  await page.fill('#username', 'rahulshettyacademy');
  await page.fill('#password', 'learning');
  await page.click('#signInBtn');

  // Wait for navigation to shop page
  await page.waitForURL('**/angularpractice/shop');

  // Find the iPhone X card and click 'Add'
  const products = page.locator('.card-title');
  const count = await products.count();
  for (let i = 0; i < count; i++) {
    if ((await products.nth(i).textContent())?.includes('iphone X')) {
      await page.locator('button.btn-info').nth(i).click();
      break;
    }
  }

  // Go to cart
  await page.click('a.nav-link.btn.btn-primary');

  // Assert iPhone X is in the cart
  const cartItems = page.locator('h4.media-heading');
  const cartCount = await cartItems.count();
  let found = false;
  for (let i = 0; i < cartCount; i++) {
    if ((await cartItems.nth(i).textContent())?.includes('iphone X')) {
      found = true;
      break;
    }
  }
  expect(found).toBeTruthy();

  // Proceed to checkout
  await page.click('button.btn-success');

  // Confirm checkout page loaded
  await expect(page.locator('input[placeholder="Select Country"]')).toBeVisible();
});
