import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import org.openqa.selenium.WebDriver
import org.openqa.selenium.Alert
import org.openqa.selenium.WebElement

println("===== START DOWNLOAD FILE =====")

WebUI.waitForPageLoad(20)
WebUI.delay(2)

// ============================
// CLICK DOWNLOAD ICON
// ============================
TestObject downloadBtn = new TestObject('Download icon')
downloadBtn.addProperty('xpath', ConditionType.EQUALS,
    "(//a[@title='Download'] | //*[@title='Download'])[1]")

WebUI.waitForElementPresent(downloadBtn, 15)

WebElement downloadEl = WebUI.findWebElement(downloadBtn, 10)
WebUI.executeJavaScript('arguments[0].scrollIntoView({block:"center"});', [downloadEl])
WebUI.delay(1)
WebUI.executeJavaScript('arguments[0].click();', [downloadEl])
println("✅ Download clicked")

// ============================
// HANDLE NATIVE DOWNLOAD ALERT (if it appears)
// ============================
WebUI.delay(3)

try {
    WebDriver driver = DriverFactory.getWebDriver()
    Alert alert = driver.switchTo().alert()
    String alertText = alert.getText()
    println("⚠️ Browser alert: " + alertText)
    alert.accept()    // Click OK on the alert
    println("✅ Alert accepted (download confirmed)")
} catch (Exception e) {
    println("ℹ️ No browser alert appeared (might be auto-downloaded)")
}

WebUI.delay(3)

println("===== END DOWNLOAD FILE =====")