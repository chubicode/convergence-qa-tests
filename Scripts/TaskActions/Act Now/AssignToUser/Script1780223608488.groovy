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
import org.openqa.selenium.Keys as Keys

println("===== START ASSIGN TO USER =====")

try {
    WebDriver driver = DriverFactory.getWebDriver()
    Alert leftoverAlert = driver.switchTo().alert()
    leftoverAlert.dismiss()
} catch (Exception e) { }

WebUI.waitForPageLoad(20)
WebUI.delay(2)

// STEP 1: ACT NOW DROPDOWN
WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Work Order  View- Workforce Manager/a_dLabel_1'))
WebUI.delay(2)
println("✅ STEP 1: Act Now opened")

// STEP 2: CLICK RE-ASSIGN
WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Work Order  View- Workforce Manager/a_Re-assign to user'))
WebUI.delay(4)
println("✅ STEP 2: Reassign modal opened")

// STEP 3: SEARCH
String userName = GlobalVariable.assignUserName
String searchKeyword = userName.split(' ')[0]
WebUI.setText(
    findTestObject('Pages_NEW/WorkOrder_View/Page_Work Order  View- Workforce Manager/input_Search users or roles'),
    searchKeyword
)
WebUI.delay(3)
println("✅ STEP 3: Searched '" + searchKeyword + "'")

// STEP 4: CLICK USER (WebUI.click works because XPath matches only ONE visible user)
TestObject userItem = new TestObject('user list item')
userItem.addProperty(
    'xpath',
    ConditionType.EQUALS,
    "//*[@id='assignUserForm_new']//li[contains(@class,'list-item') and .//p[contains(normalize-space(),'${userName}')]]"
)
WebUI.waitForElementPresent(userItem, 15)
WebUI.click(userItem)
WebUI.delay(3)
println("✅ STEP 4: User '" + userName + "' clicked")

// ============================
// STEP 5: CLICK VISIBLE "ADD COMMENT" (find by visibility — there are many hidden ones)
// ============================
WebElement addCmtEl = WebUI.executeJavaScript("""
    // Find the VISIBLE add_comment link (one per user, only the active user's is visible)
    var all = document.querySelectorAll('a#add_comment');
    for (var i = 0; i < all.length; i++) {
        var el = all[i];
        if (el.offsetParent === null) continue;
        var rect = el.getBoundingClientRect();
        if (rect.width > 0 && rect.height > 0) {
            return el;
        }
    }
    // Fallback: any element with text 'Add Comment' inside form
    var form = document.getElementById('assignUserForm_new');
    if (form) {
        var links = form.querySelectorAll('a, button');
        for (var i = 0; i < links.length; i++) {
            if (links[i].textContent.trim() === 'Add Comment' && links[i].offsetParent !== null) {
                var r = links[i].getBoundingClientRect();
                if (r.width > 0 && r.height > 0) return links[i];
            }
        }
    }
    return null;
""", null) as WebElement

if (addCmtEl == null) {
    throw new Exception("Add Comment button not visible")
}

WebUI.executeJavaScript('arguments[0].scrollIntoView({block:"center"});', [addCmtEl])
WebUI.delay(1)
WebUI.executeJavaScript('arguments[0].click();', [addCmtEl])
println("✅ STEP 5: Add Comment clicked")
WebUI.delay(3)

// ============================
// STEP 6: TYPE COMMENT
// ============================
def textareaResult = WebUI.executeJavaScript("""
    var commentText = 'Test Automation 234156';
    var textareas = document.querySelectorAll('#assignUserForm_new textarea');
    
    for (var i = 0; i < textareas.length; i++) {
        var ta = textareas[i];
        if (ta.offsetParent === null) continue;
        var rect = ta.getBoundingClientRect();
        if (rect.width === 0 || rect.height === 0) continue;
        
        ta.focus();
        ta.value = commentText;
        ta.dispatchEvent(new Event('input', {bubbles: true}));
        ta.dispatchEvent(new Event('change', {bubbles: true}));
        ta.dispatchEvent(new KeyboardEvent('keyup', {bubbles: true}));
        
        return 'SET: placeholder="' + ta.placeholder + '" size=' + Math.round(rect.width) + 'x' + Math.round(rect.height);
    }
    return 'NO_VISIBLE_TEXTAREA';
""", null)
println("📝 Comment: " + textareaResult)
WebUI.delay(2)

// ============================
// STEP 7: CLICK VISIBLE "SAVE AND ASSIGN" (id="assign_user" — also many hidden ones)
// ============================
WebElement saveEl = WebUI.executeJavaScript("""
    // Find the VISIBLE assign_user button (one per user, only active user's is visible)
    var all = document.querySelectorAll('button#assign_user, a#assign_user');
    for (var i = 0; i < all.length; i++) {
        var el = all[i];
        if (el.offsetParent === null) continue;
        var rect = el.getBoundingClientRect();
        if (rect.width > 0 && rect.height > 0) {
            return el;
        }
    }
    // Fallback: any element with text 'Save and Assign'
    var all2 = document.querySelectorAll('button, a');
    for (var i = 0; i < all2.length; i++) {
        var el = all2[i];
        if (el.offsetParent === null) continue;
        var rect = el.getBoundingClientRect();
        if (rect.width === 0 || rect.height === 0) continue;
        var text = el.textContent.trim();
        if (text === 'Save and Assign' || text === 'Save & Assign' || text === 'Assign') {
            return el;
        }
    }
    return null;
""", null) as WebElement

if (saveEl == null) {
    throw new Exception("Save and Assign button not visible")
}

WebUI.executeJavaScript('arguments[0].scrollIntoView({block:"center"});', [saveEl])
WebUI.delay(1)
WebUI.executeJavaScript('arguments[0].click();', [saveEl])
println("✅ STEP 7: Save and Assign clicked")
WebUI.delay(5)

// STEP 8: CLEAN UP MODAL BACKDROP
WebUI.executeJavaScript("""
    document.querySelectorAll('.modal-backdrop').forEach(function(el){ el.remove(); });
    document.body.classList.remove('modal-open');
    document.body.style.overflow = '';
""", null)
WebUI.delay(2)

println("📍 URL: " + WebUI.getUrl())

// STEP 9: REOPEN TASK
String ticketId = GlobalVariable.capturedTicketId
if (ticketId == null || ticketId.trim().isEmpty()) {
    throw new Exception('❌ No Ticket ID')
}
println('📦 Searching ticket: ' + ticketId)

TestObject search = findTestObject('Page_Work Order  Index- Workforce Manager/input_Search')
WebUI.waitForElementVisible(search, 30)
WebUI.click(search)
WebUI.clearText(search)
WebUI.setText(search, ticketId)
WebUI.sendKeys(search, Keys.chord(Keys.ENTER))
WebUI.delay(5)

TestObject titleLink = new TestObject()
titleLink.addProperty(
    'xpath',
    ConditionType.EQUALS,
    "//tr[.//*[contains(text(),'${ticketId}')]]//td[3]//a"
)
WebUI.waitForElementVisible(titleLink, 30)

try {
    WebUI.click(titleLink)
} catch (Exception e) {
    WebUI.executeJavaScript('arguments[0].click();', [WebUI.findWebElement(titleLink, 10)])
}

WebUI.waitForPageLoad(20)
println('✅ Task ' + ticketId + ' reopened')
println("===== END ASSIGN TO USER =====")