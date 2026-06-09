import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import org.openqa.selenium.WebElement

// ============================
// START
// ============================
println("===== START UPDATE TASK =====")

WebUI.switchToWindowTitle('Work Order | View- Workforce Manager')
WebUI.waitForPageLoad(20)
WebUI.delay(2)

// ============================
// SAVE CURRENT TASK URL
// ============================
String originalTaskUrl = WebUI.getUrl()

println("📌 Original Task URL:")
println(originalTaskUrl)

// ============================
// STEP 1: CLICK MORE DROPDOWN
// ============================
TestObject moreBtn = new TestObject('More button')

moreBtn.addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//a[.//span[contains(@class,'more-actions-label')]]"
)

WebUI.waitForElementPresent(moreBtn, 15)

WebElement moreEl = WebUI.findWebElement(moreBtn, 10)

WebUI.executeJavaScript(
	"arguments[0].scrollIntoView({block:'center'});",
	[moreEl]
)

WebUI.delay(1)

WebUI.executeJavaScript(
	"arguments[0].click();",
	[moreEl]
)

println("✅ STEP 1: More dropdown opened")

WebUI.delay(3)

// ============================
// STEP 2: CLICK UPDATE TASK
// ============================
TestObject updateTaskLink = new TestObject('Update Task')

updateTaskLink.addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//a[@id='dMore']"
)

WebUI.waitForElementPresent(updateTaskLink, 15)

WebElement updateEl = WebUI.findWebElement(updateTaskLink, 10)

WebUI.executeJavaScript(
	"arguments[0].click();",
	[updateEl]
)

println("✅ STEP 2: Update Task clicked")

WebUI.waitForPageLoad(20)
WebUI.delay(3)

// ============================
// STEP 3: UPDATE TASK TITLE
// ============================
String newTitle = "TEST_" + System.currentTimeMillis()

WebUI.setText(
	findTestObject(
		'Pages_NEW/WorkOrder_View/Page_Work Order  Update- Workforce Manager/input_Task Title _'
	),
	newTitle
)

println("✅ STEP 3: Updated title to -> " + newTitle)

WebUI.delay(1)

// ============================
// STEP 4: CLICK UPDATE BUTTON
// ============================
WebUI.click(
	findTestObject(
		'Pages_NEW/WorkOrder_View/Page_Work Order  Update- Workforce Manager/button_Update'
	)
)

println("✅ STEP 4: Update submitted")

WebUI.waitForPageLoad(20)
WebUI.delay(5)

// ============================
// STEP 5: DISMISS SUCCESS MESSAGE
// ============================
try {

	TestObject dismissBtn = findTestObject(
		'Pages_NEW/WorkOrder_View/Page_Work Order  Update- Workforce Manager/button_message-dismiss'
	)

	boolean visible = WebUI.waitForElementPresent(
		dismissBtn,
		5,
		FailureHandling.OPTIONAL
	)

	if (visible) {

		try {

			WebUI.enhancedClick(dismissBtn)

			println("✅ STEP 5: Success message dismissed")

		} catch (Exception ex) {

			WebElement dismissEl = WebUI.findWebElement(dismissBtn, 5)

			WebUI.executeJavaScript(
				"arguments[0].click();",
				[dismissEl]
			)

			println("✅ STEP 5: Dismissed using JavaScript")
		}
	}

} catch (Exception e) {

	println("⚠️ No success message found")
}

// ============================
// STEP 6: RETURN TO ORIGINAL TASK
// ============================
println("🔄 Returning to original task page")

WebUI.navigateToUrl(originalTaskUrl)

WebUI.waitForPageLoad(20)

WebUI.delay(3)

println("✅ Returned to original task")

// ============================
// END
// ============================
println("===== UPDATE TASK COMPLETE =====")
println("Ready for next test case")