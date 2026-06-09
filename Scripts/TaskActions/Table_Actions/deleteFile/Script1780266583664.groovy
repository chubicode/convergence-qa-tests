import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import org.openqa.selenium.WebDriver
import org.openqa.selenium.Alert
import org.openqa.selenium.WebElement
import org.openqa.selenium.NoAlertPresentException

println("===== START DELETE FILE =====")

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
    // No alert
}

WebUI.waitForPageLoad(20)
WebUI.delay(2)

// ============================
// CLICK DELETE ICON
// ============================
TestObject deleteBtn = new TestObject('Delete file icon')
deleteBtn.addProperty('xpath', ConditionType.EQUALS,
    "(//a[@title='Delete'] | //*[@title='Delete'] | //*[contains(@class,'fa-trash')])[1]")

WebUI.waitForElementPresent(deleteBtn, 15)

WebElement deleteEl = WebUI.findWebElement(deleteBtn, 10)
WebUI.executeJavaScript('arguments[0].scrollIntoView({block:"center"});', [deleteEl])
WebUI.delay(1)
WebUI.executeJavaScript('arguments[0].click();', [deleteEl])
println("✅ Delete clicked")

WebUI.delay(3)

// ============================
// CONFIRMATION DIALOG (native alert OR modal)
// ============================
try {
    WebDriver driver = DriverFactory.getWebDriver()
    Alert confirmAlert = driver.switchTo().alert()
    println("⚠️ Confirmation alert: " + confirmAlert.getText())
    confirmAlert.accept()
    println("✅ Alert accepted")
} catch (Exception e) {
    println("ℹ️ No native alert, looking for modal OK button")
    
    TestObject okBtn = new TestObject('OK button')
    okBtn.addProperty('xpath', ConditionType.EQUALS,
        "//button[normalize-space()='OK'] | //button[normalize-space()='Yes'] | //button[normalize-space()='Delete']")
    
    try {
        WebUI.waitForElementVisible(okBtn, 10, FailureHandling.OPTIONAL)
        WebElement okEl = WebUI.findWebElement(okBtn, 10)
        WebUI.executeJavaScript('arguments[0].click();', [okEl])
        println("✅ Modal OK clicked")
    } catch (Exception e2) {
        println("⚠️ No confirmation needed")
    }
}

WebUI.delay(5)

// ============================
// DISMISS SUCCESS MODAL ("File deleted successfully")
// Multiple strategies — try each one
// ============================

boolean modalClosed = false

// Strategy 1: Find ALL visible OK buttons and click the right one
try {
    def okClicked = WebUI.executeJavaScript("""
        // Find all buttons that say OK and are visible
        var buttons = document.querySelectorAll('button, a');
        var clickedAny = false;
        for (var i = 0; i < buttons.length; i++) {
            var btn = buttons[i];
            var text = btn.textContent.trim();
            // Only visible OK buttons
            if ((text === 'OK' || text === 'Ok' || text === 'ok') && btn.offsetParent !== null) {
                btn.click();
                clickedAny = true;
            }
        }
        return clickedAny ? 'clicked OK button' : 'no visible OK button';
    """, null)
    println("📊 Strategy 1: " + okClicked)
    if (okClicked.toString().contains("clicked")) {
        modalClosed = true
    }
} catch (Exception e) {
    println("⚠️ Strategy 1 failed: " + e.message)
}

WebUI.delay(2)

// Strategy 2: Force-close any open Bootstrap modal
if (!modalClosed) {
    try {
        WebUI.executeJavaScript("""
            // Hide all visible modals
            if (typeof jQuery !== 'undefined') {
                jQuery('.modal').modal('hide');
            }
            // Remove backdrops
            document.querySelectorAll('.modal-backdrop').forEach(function(el) {
                el.remove();
            });
            // Remove modal-open class from body
            document.body.classList.remove('modal-open');
            document.body.style.overflow = '';
            document.body.style.paddingRight = '';
            
            // Force-hide any modal that's still showing
            document.querySelectorAll('.modal.show, .modal.in, .modal[style*="display: block"]').forEach(function(modal) {
                modal.classList.remove('show', 'in');
                modal.style.display = 'none';
                modal.setAttribute('aria-hidden', 'true');
            });
            
            return 'force-closed all modals';
        """, null)
        println("✅ Strategy 2: Force-closed all modals via JS")
        modalClosed = true
    } catch (Exception e) {
        println("⚠️ Strategy 2 failed: " + e.message)
    }
}

WebUI.delay(2)

// Strategy 3: Press Escape key
if (!modalClosed) {
    try {
        WebUI.executeJavaScript("""
            document.dispatchEvent(new KeyboardEvent('keydown', {
                key: 'Escape',
                code: 'Escape',
                keyCode: 27,
                which: 27,
                bubbles: true
            }));
        """, null)
        println("✅ Strategy 3: Pressed Escape")
    } catch (Exception e) {
        println("⚠️ Strategy 3 failed: " + e.message)
    }
}

WebUI.delay(2)

// ============================
// FINAL VERIFICATION
// ============================
def finalState = WebUI.executeJavaScript("""
    var visibleModals = document.querySelectorAll('.modal.show, .modal.in, .modal[style*="display: block"]');
    var backdrops = document.querySelectorAll('.modal-backdrop');
    return 'visible modals: ' + visibleModals.length + ', backdrops: ' + backdrops.length;
""", null)
println("📊 Final modal state: " + finalState)

// If anything still visible, force-clear it
if (!finalState.toString().contains("visible modals: 0") || !finalState.toString().contains("backdrops: 0")) {
    WebUI.executeJavaScript("""
        document.querySelectorAll('.modal').forEach(function(m) {
            m.classList.remove('show', 'in');
            m.style.display = 'none';
        });
        document.querySelectorAll('.modal-backdrop').forEach(function(el) {
            el.remove();
        });
        document.body.classList.remove('modal-open');
        document.body.style.overflow = '';
        document.body.style.paddingRight = '';
    """, null)
    println("🧹 Final cleanup performed")
}

WebUI.delay(2)

println("===== END DELETE FILE =====")