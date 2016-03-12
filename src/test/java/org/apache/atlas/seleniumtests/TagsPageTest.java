package org.apache.atlas.seleniumtests;

import org.apache.atlas.objectwrapper.WebDriverWrapper;
import org.apache.atlas.utilities.AtlasDriverUtility;
import org.apache.log4j.Logger;
import org.atlas.testHelper.AtlasConstants;
import org.atlas.ui.pages.TagsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

public class TagsPageTest extends WebDriverWrapper {

	private static final Logger LOGGER = Logger.getLogger(TagsPageTest.class);
	private TagsPage tagsPage = null;
	long testExecutionStartTime;

	@BeforeClass(description = "TagsPage Test Setup")
	public void loadTagsTest(XmlTest config) {
		tagsPage = new TagsPage();
		tagsPage.launchApp();
	}

	@BeforeMethod
	public void beforeMethod() {
		AtlasConstants.START_TIME = System.currentTimeMillis();
	}

	@Test(priority = 25)
	public void testPageElementsFromTagsTab() {
		LOGGER.info("STARTED: Test testPageElementsFromTagsTab");
		homePage.verifyPageLoadSuccessfully();
		LOGGER.info("ENDED: Test testPageElementsFromTagsTab");
	}

	@Test(priority = 26)
	public void validateTagsPage() {
		LOGGER.info("STARTED: Test validateTagsPage");
		tagsPage.navigateToTagsTab();
		boolean isTagsSectionExist = tagsPage.validateTagsSections();
		Assert.assertTrue(isTagsSectionExist, "Validating tags page sections");
		LOGGER.info("ENDED: Test validateTagsPage");
	}

/*	@Test(priority = 27)
	public void ValidateTagNameWithAddAttribute() {
	//HDPDGI-352 
		LOGGER.info("STARTED: Validate disable of attribute when Tag Name field is empty");

		tagsPage.validateAttributeEnable();
		LOGGER.info("ENDED: Validate disable of attribute when Tag Name field is empty");

	}*/

/*	@Test(priority = 28)
	public void duplicateTagName() {
		LOGGER.info("STARTED: Test duplicateTagName");
		tagsPage.createExistingTag();
		String actualMsg = tagsPage.getNotificationMessage().trim();
		Assert.assertEquals(actualMsg,
				"Cannot redefine type " + tagsPage.getTagName());
		
		
		LOGGER.info("ENDED: Test duplicateTagName");
	}*/

	@Test(priority = 28)
	public void createTagWithAttribute() {
		LOGGER.info("STARTED: Test Create Attribute");
		tagsPage.navigateToTagsTab();
		tagsPage.enterTagName("TestInput").selectParentTag("Dimension")
				.addAddtribute().enterAttributeName("TestAttr").saveTagName();
		LOGGER.info("ENDED: Test Create Attribute");
	}

	@Test(priority = 29)
	public void addFunctionalTestTag() {
		LOGGER.info("STARTED: Test add Tag");
		tagsPage.navigateToTagsTab();
		tagsPage.enterTagName("FunctionalTestTag").saveTagName();
		LOGGER.info("ENDED: Test add Tag");
	}
	
	@Test(priority = 30)
	public void duplicateTagName() {
		LOGGER.info("STARTED: Test duplicateTagName");
		tagsPage.createExistingTag();
		String actualMsg = tagsPage.getNotificationMessage().trim();
		Assert.assertEquals(actualMsg,
				"Cannot redefine type " + tagsPage.getTagName());
		
		
		LOGGER.info("ENDED: Test duplicateTagName");
	}

	@Test(priority = 31)
	public void validateFunctionalTestTag() {
		LOGGER.info("STARTED: Test validateFunctionalTestTag");
		tagsPage.navigateToTagsTab();
/*		Assert.assertTrue(tagsPage.validateParentTag("FunctionalTestTag"),
				"Validating tag in tags page sections");*/
		Assert.assertTrue(tagsPage.validateParentTag("PII"),
				"Validating tag in tags page sections");
		LOGGER.info("ENDED: Test validateFunctionalTestTag");
	}

	@Test(priority = 32)
	public void VisibilityOfXButtonOnAttribute() {
		LOGGER.info("STARTED: Test X Button visibility after clicking on +ADD Attribute");

		tagsPage.visibilityOfDeleteAttribute();

		LOGGER.info("ENDED: Test X Button visibility after clicking on +ADD Attribute");

	}

	@Test(priority = 33)
	public void ValidateOfXButtonOnADDAtrribute() {
		LOGGER.info("STARTED: Test X Button functionality after clicking on +ADD Attribute");

		tagsPage.clickOnDeleteAttribute();

		LOGGER.info("ENDED: Test X Button functionality after clicking on +ADD Attribute");

	}

/*	@Test(priority = 34)
	public void ValidateMessageDisappear() {
		// HDPDGI-353

		LOGGER.info("STARTED: Notification message disappear while creating New Tag");
		tagsPage.notificationDisappear();
		LOGGER.info("ENDED:  Notification message disappear while creating New Tag");

	}*/

	@Test(priority = 35)
	public void ValidateAutoRefreshForNewTagCreated() {

		LOGGER.info("STARTED: Validate Auto Refresh Parent tag in Tag Page");

		tagsPage.autoRefreshParentTag();
		AtlasDriverUtility.customWait(2);

		LOGGER.info("ENDED: Validate Auto Refresh Parent tag in Tag Page");
	}

	@Test(priority = 36)
	public void WebElementCollection() {

		tagsPage.webElementTextCollection();
	}

	@Test(dataProvider = AtlasConstants.SPELL_CHECKER, dataProviderClass = TagsPage.class, priority = 37)
	public void validateSpellChecker(String token) {
		LOGGER.info("STARTED: Test validate Spell Checker");
		LOGGER.info(token.toString());

		Boolean val = tagsPage.checkTokenExist(token.toString());

		Assert.assertTrue(val, "Spelling is correct!");

		LOGGER.info("ENDED: Test validate Spell Checker");
	}

}
