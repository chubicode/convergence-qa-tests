import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import org.openqa.selenium.WebDriver
import org.openqa.selenium.Alert
import org.openqa.selenium.WebElement

println("===== START VIEW FILE =====")

// ============================
// DISMISS ANY LEFTOVER ALERTS
// ============================
try {
    WebDriver driver = DriverFactory.getWebDriver()
    Alert leftoverAlert = driver.switchTo().alert()
    println("⚠️ Found leftover alert: " + leftoverAlert.getText())
    leftoverAlert.dismiss()
    println("✅ Dismissed leftover alert")
} catch (Exception e) {
    // No alert — that's fine
}

WebUI.waitForPageLoad(20)
WebUI.delay(2)

// ============================
// CLICK VIEW ICON (with JS click)
// ============================
TestObject viewBtn = new TestObject('View icon')
viewBtn.addProperty('xpath', ConditionType.EQUALS,
    "(//a[@title='View'] | //*[@title='View'])[1]")

WebUI.waitForElementPresent(viewBtn, 15)

WebElement viewEl = WebUI.findWebElement(viewBtn, 10)
WebUI.executeJavaScript('arguments[0].scrollIntoView({block:"center"});', [viewEl])
WebUI.delay(1)
WebUI.executeJavaScript('arguments[0].click();', [viewEl])
println("✅ View clicked")

WebUI.delay(5)

// ============================
// CLOSE VIEWER (chevron-right icon)
// ============================
TestObject closeBtn = new TestObject('Close viewer')
closeBtn.addProperty('xpath', ConditionType.EQUALS,
    "//i[contains(@class,'fa-chevron-right')] | //*[contains(@class,'close-viewer')] | //*[@title='Close']")

try {
    WebUI.waitForElementPresent(closeBtn, 15)
    WebElement closeEl = WebUI.findWebElement(closeBtn, 10)
    WebUI.executeJavaScript('arguments[0].click();', [closeEl])
    println("✅ Viewer closed")
} catch (Exception e) {
    println("⚠️ Close button not found, pressing Escape")
    WebUI.executeJavaScript("document.dispatchEvent(new KeyboardEvent('keydown', {key:'Escape', keyCode:27}));", null)
}

WebUI.delay(2)

println("===== END VIEW FILE =====")