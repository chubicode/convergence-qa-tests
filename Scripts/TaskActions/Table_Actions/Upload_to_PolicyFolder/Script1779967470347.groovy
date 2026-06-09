import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.WebDriver
import org.openqa.selenium.Alert
import org.openqa.selenium.WebElement

println("===== START UPLOAD TO POLICY FOLDER =====")

// Dismiss leftover alerts
try {
    WebDriver driver = DriverFactory.getWebDriver()
    Alert leftoverAlert = driver.switchTo().alert()
    println("⚠️ Dismissed leftover alert: " + leftoverAlert.getText())
    leftoverAlert.dismiss()
} catch (Exception e) { }

WebUI.switchToWindowTitle('Work Order | View- Workforce Manager')
WebUI.waitForPageLoad(20)
WebUI.switchToDefaultContent()
WebUI.delay(3)

// ============================
// STEP 1: CLICK UPLOAD TO POLICY FOLDER LINK
// ============================
TestObject uploadLink = new TestObject('Upload to Policy Folder')
uploadLink.addProperty(
    'xpath',
    ConditionType.EQUALS,
    "//a[@title='Upload To Policy Folder'] | //img[@alt='file upload icon']/parent::a | //img[@alt='file upload icon']"
)

WebUI.waitForElementPresent(uploadLink, 20)

WebElement uploadEl = WebUI.findWebElement(uploadLink, 10)
WebUI.executeJavaScript('arguments[0].scrollIntoView({block:"center"});', [uploadEl])
WebUI.delay(1)
WebUI.executeJavaScript('arguments[0].click();', [uploadEl])
println("✅ STEP 1: Upload to Policy Folder clicked")

WebUI.delay(5)

// ============================
// STEP 2: SWITCH INTO GOV DRIVE IFRAME
// ============================
TestObject iframe = new TestObject('cde iframe')
iframe.addProperty(
    'xpath',
    ConditionType.EQUALS,
    "//iframe[contains(@id,'cde') or contains(@src,'cde') or contains(@class,'cde')] | //iframe[contains(@src,'GovDrive')] | //iframe[contains(@src,'1gov')] | //iframe[1]"
)

WebUI.waitForElementPresent(iframe, 20)
WebUI.switchToFrame(iframe, 10)
println("✅ STEP 2: Inside Gov Drive iframe")

WebUI.delay(5)

// ============================
// STEP 3: CLICK FOLDER CARD (specifically the bg-white card div)
// ============================
String folderName = GlobalVariable.policyFolderName
println("📁 Looking for folder containing: " + folderName)

// Use Groovy string interpolation to embed folderName into the JS code
String clickScript = """
    var folderName = '${folderName}'.toUpperCase();
    var clicked = false;
    var clickedInfo = 'NOT_FOUND';
    
    // Strategy 1: Find the bg-white folder CARD with cursor:pointer
    var allDivs = document.querySelectorAll('div');
    for (var i = 0; i < allDivs.length; i++) {
        var el = allDivs[i];
        var text = el.textContent.trim().toUpperCase();
        
        if (text.indexOf(folderName) === -1) continue;
        if (el.offsetParent === null) continue;
        
        var rect = el.getBoundingClientRect();
        var style = window.getComputedStyle(el);
        var classes = el.className || '';
        
        // Folder card: bg-white, has cursor pointer, reasonable size, max-w-[350px] (300-400 wide)
        if (style.cursor === 'pointer' && 
            rect.width > 200 && rect.width < 500 && 
            rect.height > 50 && rect.height < 120 &&
            classes.indexOf('bg-white') !== -1) {
            
            clickedInfo = 'CARD ' + Math.round(rect.width) + 'x' + Math.round(rect.height) + ' class=' + classes.substring(0, 60);
            el.click();
            clicked = true;
            break;
        }
    }
    
    // Strategy 2: Any clickable element with cursor pointer and the text
    if (!clicked) {
        for (var i = 0; i < allDivs.length; i++) {
            var el = allDivs[i];
            var text = el.textContent.trim().toUpperCase();
            
            if (text.indexOf(folderName) === -1) continue;
            if (el.offsetParent === null) continue;
            
            var rect = el.getBoundingClientRect();
            var style = window.getComputedStyle(el);
            
            if (style.cursor === 'pointer' && rect.width > 100 && rect.height > 30) {
                clickedInfo = 'STRATEGY2 ' + el.tagName + ' ' + Math.round(rect.width) + 'x' + Math.round(rect.height);
                el.click();
                clicked = true;
                break;
            }
        }
    }
    
    return clicked ? 'SUCCESS: ' + clickedInfo : 'FAILED: no clickable card found';
"""

def clickResult = WebUI.executeJavaScript(clickScript, null)
println("🖱️ " + clickResult)

WebUI.delay(5)

// ============================
// VERIFY WE'RE INSIDE THE FOLDER
// ============================
def verifyContent = WebUI.executeJavaScript("""
    // Look for "Files" or breadcrumb showing we're inside folder
    var hasFilesHeader = document.body.textContent.indexOf('Files') !== -1;
    var hasFoldersHeader = document.body.textContent.indexOf('Folders') !== -1;
    var bodyText = document.body.textContent.substring(0, 800);
    
    return 'hasFiles=' + hasFilesHeader + ' | hasFolders=' + hasFoldersHeader + ' | text=' + bodyText;
""", null)
println("📋 After click: " + verifyContent)

WebUI.delay(2)

// ============================
// STEP 4: CLICK SAVE PATH BUTTON
// ============================
TestObject savePath = new TestObject('Save Path')
savePath.addProperty(
    'xpath',
    ConditionType.EQUALS,
    "//button[normalize-space()='Save Path'] | //a[normalize-space()='Save Path'] | //*[contains(text(),'Save Path')]"
)

try {
    WebUI.waitForElementPresent(savePath, 20)
    
    WebElement saveEl = WebUI.findWebElement(savePath, 10)
    WebUI.executeJavaScript('arguments[0].scrollIntoView({block:"center"});', [saveEl])
    WebUI.delay(1)
    WebUI.executeJavaScript('arguments[0].click();', [saveEl])
    println("✅ STEP 4: Save Path clicked")
} catch (Exception e) {
    println("⚠️ Save Path button not found")
    throw e
}

WebUI.delay(3)

// Switch back to main page
WebUI.switchToDefaultContent()
println("===== END UPLOAD TO POLICY FOLDER =====")