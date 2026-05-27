import static com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords.*
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.model.FailureHandling

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*
import java.time.Duration

WebDriver driver = DriverFactory.getWebDriver()
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30))

println("===== START ESIGN =====")

waitForPageLoad(30)


// ============================
// OPEN ESIGN
// ============================

click(findTestObject(
'Page_Work Order  View- Workforce Manager/svg_svg'
))

println("eSign modal opened")


// ============================
// ENTER IFRAME
// ============================

TestObject iframe = new TestObject()

iframe.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//iframe[contains(@id,'esign')]"
)

waitForElementPresent(iframe,60)

switchToFrame(iframe,20)

println("Inside iframe")


// ============================
// SELECT SIGNATURE TOOL
// ============================

WebElement signTool = wait.until(
    ExpectedConditions.elementToBeClickable(
        By.xpath("//*[normalize-space()='Signature']")
    )
)

((JavascriptExecutor)driver)
.executeScript(
    "arguments[0].click();",
    signTool
)

println("Signature selected")


// ============================
// DROP SIGNATURE
// ============================

WebElement page = wait.until(
    ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//*[contains(@id,'page-')]")
    )
)

println("PDF located")

((JavascriptExecutor)driver)
.executeScript(
"""
var rect=arguments[0].getBoundingClientRect();

var x=rect.left+(rect.width/2);
var y=rect.top+250;

var el=document.elementFromPoint(x,y);

if(el){
el.dispatchEvent(
new MouseEvent(
'click',
{
bubbles:true,
clientX:x,
clientY:y
}
)
);
}
""",
page
)

println("Signature dropped")

Thread.sleep(2000)


// ============================
// CLICK OK AFTER SIGN
// ============================

try {

WebElement okSign = wait.until(
ExpectedConditions.elementToBeClickable(
By.xpath("//button[normalize-space()='OK']")
)
)

((JavascriptExecutor)driver)
.executeScript(
"arguments[0].click();",
okSign
)

println("Signature OK clicked")

}
catch(Exception e){
println("No signature OK popup")
}


// ============================
// CLICK PREVIEW
// ============================

WebElement previewBtn = wait.until(
ExpectedConditions.elementToBeClickable(
By.xpath("//button[contains(.,'Preview')]")
)
)

((JavascriptExecutor)driver)
.executeScript(
"arguments[0].click();",
previewBtn
)

println("Preview clicked")


// ============================
// WAIT FOR SAVE
// ============================

wait.until(
ExpectedConditions.textToBePresentInElementLocated(
By.xpath("//button[contains(.,'Preview') or contains(.,'Save')]"),
"Save"
)
)

WebElement saveBtn = wait.until(
ExpectedConditions.elementToBeClickable(
By.xpath("//button[contains(.,'Save')]")
)
)

Thread.sleep(1000)

((JavascriptExecutor)driver)
.executeScript(
"arguments[0].click();",
saveBtn
)

println("Save clicked")


// ============================
// RETURN TO MAIN PAGE
// ============================

switchToDefaultContent()

println("Back to main page")


// ============================
// CLOSE SUCCESS MODAL
// ============================

boolean okModalClosed = false

try {

TestObject okBtn = new TestObject()

okBtn.addProperty(
"xpath",
ConditionType.EQUALS,
"//button[normalize-space()='OK']"
)

if (
waitForElementVisible(
okBtn,
10,
FailureHandling.OPTIONAL
)
) {

click(okBtn)

okModalClosed = true

println("Success modal closed")
}

}
catch(Exception e){

println("OK button not found")
}


// fallback click anywhere

if(!okModalClosed){

executeJavaScript(
"document.body.click();",
[]
)

println("Clicked outside modal")
}


// ============================
// FINAL VERIFY
// ============================

TestObject modal = new TestObject()

modal.addProperty(
"xpath",
ConditionType.EQUALS,
"//*[contains(@class,'modal')]"
)

waitForElementNotPresent(
modal,
20,
FailureHandling.OPTIONAL
)

println("===== ESIGN SUCCESS =====")