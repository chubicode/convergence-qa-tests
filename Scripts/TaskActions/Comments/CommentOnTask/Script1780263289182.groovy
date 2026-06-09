import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.WebElement

println("===== START COMMENT ON TASK =====")

// ============================
// CLICK ADD COMMENT
// ============================
WebUI.click(findTestObject('Pages_NEW/WorkOrder_Index/Page_Work Order  View- Workforce Manager/a_Add Comment'))
WebUI.delay(2)
println("✅ Add Comment clicked")

// ============================
// TYPE @USER KEYWORD
// ============================
WebUI.setText(
    findTestObject('Pages_NEW/WorkOrder_Index/Page_Work Order  View- Workforce Manager/textarea_To mention a user or role, type  in fr'),
    '@' + GlobalVariable.mentionUserKeyword
)
WebUI.delay(2)
println("✅ Typed: @" + GlobalVariable.mentionUserKeyword)

// ============================
// CLICK USER MENTION SUGGESTION (inline XPath)
// ============================
TestObject mentionUser = new TestObject('user mention')
mentionUser.addProperty(
    'xpath',
    ConditionType.EQUALS,
    "//a[contains(.,'${GlobalVariable.mentionUserFullName}')] | //li[contains(.,'${GlobalVariable.mentionUserFullName}')]"
)

WebUI.waitForElementPresent(mentionUser, 15)

try {
    WebUI.click(mentionUser)
} catch (Exception e) {
    WebElement mentionEl = WebUI.findWebElement(mentionUser, 10)
    WebUI.executeJavaScript('arguments[0].click();', [mentionEl])
}
WebUI.delay(2)
println("✅ User mention selected: " + GlobalVariable.mentionUserFullName)

// ============================
// TYPE COMMENT WITH USER REFERENCE
// ============================
WebUI.setText(
    findTestObject('Pages_NEW/WorkOrder_Index/Page_Work Order  View- Workforce Manager/textarea_To mention a user or role, type  in fr'),
    '[~' + GlobalVariable.mentionUserFullName + '] test automation'
)
WebUI.delay(1)
println("✅ Comment text entered")

// ============================
// SAVE COMMENT
// ============================
WebUI.click(findTestObject('Pages_NEW/WorkOrder_Index/Page_Work Order  View- Workforce Manager/a_save_comment'))
WebUI.delay(3)
println("✅ Save Comment clicked")

// ============================
// DISMISS SUCCESS MODAL
// ============================
TestObject okBtn = new TestObject('OK on success modal')
okBtn.addProperty(
    'xpath',
    ConditionType.EQUALS,
    "//*[@id='notificationModal']//button[normalize-space()='OK'] | //*[@id='notificationModal']//button[contains(@class,'btn-primary')]"
)

try {
    WebUI.waitForElementVisible(okBtn, 10, FailureHandling.OPTIONAL)
    WebUI.click(okBtn)
    println("✅ OK clicked")
} catch (Exception e) {
    // Fallback: dispatch close via JS
    WebUI.executeJavaScript("\$('#notificationModal').modal('hide');", null)
    println("⚠️ Force-closed modal via JS")
}

WebUI.delay(2)

// ============================
// CLICK MESSAGE DISMISS (with JS fallback)
// ============================
try {
    WebUI.click(findTestObject('Pages_NEW/WorkOrder_Index/Page_Work Order  View- Workforce Manager/button_message-dismiss'))
    println("✅ Message dismissed")
} catch (Exception e) {
    try {
        WebElement dismissEl = WebUI.findWebElement(
            findTestObject('Pages_NEW/WorkOrder_Index/Page_Work Order  View- Workforce Manager/button_message-dismiss'),
            10
        )
        WebUI.executeJavaScript('arguments[0].click();', [dismissEl])
        println("✅ Dismissed via JS")
    } catch (Exception e2) {
        WebUI.executeJavaScript("\$('.modal').modal('hide'); \$('.modal-backdrop').remove();", null)
        println("⚠️ Force-closed all modals")
    }
}

println("===== END COMMENT ON TASK =====")