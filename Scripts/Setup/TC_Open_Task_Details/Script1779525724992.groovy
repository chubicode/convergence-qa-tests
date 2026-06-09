import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebElement
import java.util.Arrays

/*
 * TC_Open_Task_Details
 * Navigates to task list, reads Ticket ID from GlobalVariable, finds and opens the task
 */

// === NAVIGATE TO TASKS LIST PAGE FIRST ===
WebUI.navigateToUrl(GlobalVariable.workOrderIndexUrl)
WebUI.delay(5)
WebUI.waitForPageLoad(30)
println("✅ Navigated to: " + GlobalVariable.workOrderIndexUrl)

// === READ TICKET ID FROM GLOBAL VARIABLE ===
String ticketId = GlobalVariable.capturedTicketId

if (ticketId == null || ticketId.trim().isEmpty()) {
    throw new Exception('❌ No Ticket ID in GlobalVariable. Did TC_Create_Task run successfully first?')
}
println('📦 Using Ticket ID → ' + ticketId)

// === CLICK CREATED BY ME TAB ===
WebUI.click(findTestObject('Page_Work Order  Index- Workforce Manager/a_Created By Me'))
WebUI.waitForPageLoad(10)
WebUI.delay(3)

// === SEARCH BY TICKET ID ===
TestObject search = findTestObject('Page_Work Order  Index- Workforce Manager/input_Search')
WebUI.waitForElementVisible(search, 30)
WebUI.click(search)
WebUI.clearText(search)
WebUI.setText(search, ticketId)
WebUI.sendKeys(search, Keys.chord(Keys.ENTER))
println('🔍 Searching → ' + ticketId)
WebUI.delay(8)

// === WAIT FOR RESULT ROW ===
TestObject row = new TestObject()
row.addProperty('xpath', ConditionType.EQUALS, "//tr[.//*[contains(text(),'${ticketId}')]]")

boolean found = WebUI.waitForElementPresent(row, 30)

if (!found) {
    WebUI.takeScreenshot()
    throw new Exception('Ticket row not found → ' + ticketId)
}
println('✅ Ticket row found')

// === FIND TITLE INSIDE SAME ROW ===
TestObject titleLink = new TestObject()
titleLink.addProperty(
    'xpath',
    ConditionType.EQUALS,
    "(//tr[.//*[contains(text(),'${ticketId}')]]//td[3]//a) | (//tr[.//*[contains(text(),'${ticketId}')]]//td[3]//*[self::span or self::div])"
)

WebUI.waitForElementVisible(titleLink, 30)
WebUI.scrollToElement(titleLink, 10)

WebElement link = WebUI.findWebElement(titleLink, 20)
WebUI.executeJavaScript('arguments[0].scrollIntoView({block:"center"});', Arrays.asList(link))
WebUI.delay(2)

// === CLICK TITLE ===
println('Opening ticket')

try {
    WebUI.click(titleLink)
} catch (Exception e) {
    println('Normal click failed → using JS')
    WebUI.executeJavaScript('arguments[0].click();', Arrays.asList(link))
}

// === WAIT FOR TASK DETAILS ===
boolean opened = false
for (int i = 0; i < 30; i++) {
    WebUI.delay(1)
    TestObject details = new TestObject()
    details.addProperty('xpath', ConditionType.EQUALS, 
        "//*[contains(text(),'Priority')] | //*[contains(text(),'Task Details')] | //*[contains(text(),'Queue')]")
    
    if (WebUI.verifyElementPresent(details, 1, FailureHandling.OPTIONAL)) {
        opened = true
        break
    }
}

if (!opened) {
    WebUI.takeScreenshot()
    throw new Exception('Ticket clicked but details never opened')
}

println('✅ TASK DETAILS OPENED → ' + ticketId)