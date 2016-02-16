package org.apache.atlas.seleniumtests;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.gargoylesoftware.htmlunit.javascript.host.media.rtc.webkitRTCPeerConnection;

public class PaginationTest {
	int Total = 0;
	int no_of_Pages = 1;
	boolean firstPageValidation;
	boolean MiddlePageValidation;
	boolean lastPageValidation;
	boolean row_PerPage;
	int no_of_rows_exceptLastPage;

	private static final Logger LOGGER = Logger.getLogger(PaginationTest.class);
	WebDriver driver = new FirefoxDriver();
	WebDriverWait wait = new WebDriverWait(driver, 10);

	// ...........................Function.........

	public void paginationFunction(int no_of_rows) {

		WebElement noNext1 = driver.findElement(By
				.xpath("//*[contains(@ng-class, 'noNext')]"));
		WebElement noPrevious1 = driver.findElement(By
				.xpath("//*[contains(@ng-class, 'noPrevious')]"));

		if (noNext1.getAttribute("class").toString().contains("disabled") == true
				&& noPrevious1.getAttribute("class").toString()
						.contains("disabled") == true) // Condition to check
														// whether result has
														// single page or not.
		{
			System.out.println("There is only one Page");

			List<WebElement> rowCount2 = driver.findElements(By
					.cssSelector(".datatable"));

			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (WebElement row : rowCount2) {

				int cellsInRow = row.findElements(By.cssSelector("tr")).size() - 1;

				no_of_rows_exceptLastPage = cellsInRow;
				Total = cellsInRow + Total;
				System.out.println("No of rows in only have one page:"
						+ cellsInRow);
			}

			firstPageValidation = true;
			MiddlePageValidation = true;
			lastPageValidation = true;

		}

		else {

			WebElement next = wait
					.until(ExpectedConditions.elementToBeClickable(By
							.xpath("//li[@class='ng-scope']/a[@ng-click='selectPage(page + 1)']")));

			WebElement noNext = driver.findElement(By
					.xpath("//*[contains(@ng-class, 'noNext')]"));
			WebElement noPrevious = driver.findElement(By
					.xpath("//*[contains(@ng-class, 'noPrevious')]"));

			while (noNext.getAttribute("class").toString().contains("disabled") == false) {

				// on First Page Previous Button should disabled and next page
				// enabled.
				if (no_of_Pages == 1) {

					try {
						Assert.assertEquals(noPrevious.getAttribute("class")
								.toString().contains("disabled"), true);
						Assert.assertEquals(noNext.getAttribute("class")
								.toString().contains("disabled"), false);
						firstPageValidation = true;

						LOGGER.info("Previous Button is  Disabled on Page no: "
								+ no_of_Pages
								+ "=="
								+ noPrevious.getAttribute("class").toString()
										.contains("disabled"));
						LOGGER.info("Next Button is Disabled on Page No. : "
								+ no_of_Pages
								+ "=="
								+ noNext.getAttribute("class").toString()
										.contains("disabled"));
					}

					catch (AssertionError e) {
						firstPageValidation = false;
					}

				}

				else {
					// on Middle Page, Previous and Next button should be
					// enabled.

					try {

						Assert.assertEquals(noPrevious.getAttribute("class")
								.toString().contains("disabled"), false);
						Assert.assertEquals(noNext.getAttribute("class")
								.toString().contains("disabled"), false);

						MiddlePageValidation = true;

					}

					catch (AssertionError e) {
						MiddlePageValidation = false;
					}

				}

				List<WebElement> rowCount1 = driver.findElements(By
						.cssSelector(".datatable"));

				try {
					Thread.sleep(4000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				for (WebElement row : rowCount1) {

					int cellsInRow = row.findElements(By.cssSelector("tr"))
							.size() - 1;

					no_of_rows_exceptLastPage = cellsInRow; // Storing number of
															// rows per page
															// Value
															// in variable
															// except
															// last page. In
															// future,
															// there can be
															// chances
															// that each Page
															// can
															// have other than
															// 10
															// rows.
					Total = cellsInRow + Total;
					System.out
							.println("No of rows in each Page except Last Page"
									+ cellsInRow);

					try {

						Assert.assertEquals(10, +cellsInRow); // checking
																// eachPage
																// Should have
																// 10
																// rows. If it
																// Success,
																// boolean
																// variable set
																// to
																// true else set
																// to
																// false.
						row_PerPage = true;

					} catch (AssertionError e) {
						row_PerPage = false;
					}

				}

				next.click();
				no_of_Pages++;

			}

			// below for loop to get number of rows on last Page.
			List<WebElement> rowCount2 = driver.findElements(By
					.cssSelector(".datatable"));

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			for (WebElement row : rowCount2) {

				int cellsInRow = row.findElements(By.cssSelector("tr")).size() - 1;

				System.out
						.println("Number of Rows in Last Page :" + cellsInRow);

				Total = cellsInRow + Total;

			}

			LOGGER.info("Number of rows in fINALTotal:" + Total);
			Assert.assertEquals(no_of_rows, Total);

			// On Last Page, Next button should be disabled.
			try {
				Assert.assertEquals(noPrevious.getAttribute("class").toString()
						.contains("disabled"), false);
				Assert.assertEquals(noNext.getAttribute("class").toString()
						.contains("disabled"), true);
				lastPageValidation = true;

				LOGGER.info("Previous Button is Disabled on Page No. (Last Page): "
						+ no_of_Pages
						+ "=="
						+ noPrevious.getAttribute("class").toString()
								.contains("disabled"));

				LOGGER.info("Next Button is Disabled on Page No. (Last Page) "
						+ no_of_Pages
						+ "=="
						+ noNext.getAttribute("class").toString()
								.contains("disabled"));

			} catch (AssertionError e) {
				lastPageValidation = false;
			}


			LOGGER.info("ENDED: Validate_NumberOfRows");

		}

	}

	// ................................
	// verify number of rows in table equal to the value from Result label.
	@Test(priority = 0)
	public void Validate_NumberOfRows() throws InterruptedException {

		LOGGER.info("STARTED: Validate_NumberOfRows");

		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);

		// driver.get("http://52.33.160.226:3042/");
		// driver.get("http://162.249.6.39:3232/");
		driver.get("http://162.249.6.39:3232/");

		WebElement wb = driver
				.findElement(By.cssSelector("input[type='text']"));
		wb.sendKeys("Fact");
		// wb.sendKeys("JdbcAccess");

		wb = driver.findElement(By.cssSelector("button[type='submit']"));
		wb.click();

		String result = driver.findElement(By.className("tabsearchResult"))
				.getText();

		System.out.println("String is:" + result);

		String arr[] = result.split(" ", 2);

		String firstWord = arr[0]; // First String
		String theRest = arr[1]; // Remaining String

		int no_of_rows = Integer.parseInt(firstWord);
		System.out.println("Number of rows in Table:" + no_of_rows);

		paginationFunction(no_of_rows);
	}

	// Below test case test the number of boxes at below equals to the number of
	// rows/10+1.
	@Test(priority = 1)
	public void Validate_NeumericboxAtBottom() {
		int no_of_boxes;
		LOGGER.info("\nSTARTED: Validate_NeumericboxAtBottom");

		// Calculating number of boxed listed at bottom for pagination.
		if (Total % no_of_rows_exceptLastPage == 0) {
			no_of_boxes = Total / no_of_rows_exceptLastPage;

		}

		else {
			no_of_boxes = Total / no_of_rows_exceptLastPage;
			no_of_boxes = no_of_boxes + 1;
		}

		Assert.assertEquals(no_of_boxes, no_of_Pages);

		LOGGER.info("Number of Boxes" + no_of_boxes);
		LOGGER.info("Number of Pages= " + no_of_Pages);

		LOGGER.info("ENDED: Validate_NeumericboxAtBottom");

	}

	@Test(priority = 2)
	public void Validate_FirstPagePagination() {
		LOGGER.info("\nSTARTED: Validate_FirstPagePagination");
		Assert.assertEquals(true, firstPageValidation);
		LOGGER.info("ENDED: Validate_FirstPagePagination");

	}

	@Test(priority = 3)
	public void Validate_LastPagePagination() {
		LOGGER.info("\nSTARTED: Validate_FirstPagePagination");
		Assert.assertEquals(true, lastPageValidation);
		LOGGER.info("ENDED: Validate_LastPagePagination");
	}

	@Test(priority = 4)
	public void MiddlePageOfPaginationVarification() {
		LOGGER.info("\nSTARTED: Varification of Middle Page");
		Assert.assertEquals(true, MiddlePageValidation);
		LOGGER.info("ENDED: Varification of Middle Page");

	}

	@Test(priority = 5)
	public void Validate_NumberOfRowsPerPage() {
		LOGGER.info("\nSTARTED: Validate_NumberOfRowsPerPage");
		Assert.assertEquals(true, row_PerPage);
		LOGGER.info("ENDED: Validate_NumberOfRowsPerPage");

	}

}