import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import java.io.File

// ============================
// NAVIGATE TO WORK ORDER PAGE
// ============================
WebUI.navigateToUrl(GlobalVariable.workOrderUrl)
WebUI.delay(5)
WebUI.maximizeWindow()
println("✅ Navigated to: " + GlobalVariable.workOrderUrl)

WebUI.click(findTestObject('Page_Work Order  Index- Workforce Manager/a_Create Task'))

// === QUEUE DROPDOWN ===
WebUI.click(findTestObject('Page_Work Order  Create- Workforce Manager/span_Select Queue'))
WebUI.delay(1)
WebUI.setText(findTestObject('Page_Work Order  Create- Workforce Manager/input_Search'), 'aut')
WebUI.delay(1)
WebUI.click(findTestObject('Page_Work Order  Create- Workforce Manager/a_TEST AUTOMATION'))
WebUI.delay(1)

// === QUEUE TYPE DROPDOWN ===
WebUI.click(findTestObject('Page_Work Order  Create- Workforce Manager/span_Select Queue Type'))
WebUI.delay(1)
WebUI.click(findTestObject('Page_Work Order  Create- Workforce Manager/span_Automated Testing'))

// === FORM FIELDS ===
WebUI.setText(findTestObject('Page_Work Order  Create- Workforce Manager/input_Task Title _'), 'TEST AUTOMATION 10028791')
WebUI.setText(findTestObject('Page_Work Order  Create- Workforce Manager/textarea_Description'), 'Smoke Test')
WebUI.setText(findTestObject('Page_Work Order  Create- Workforce Manager/input_Input First Name'), 'Mr')
WebUI.setText(findTestObject('Page_Work Order  Create- Workforce Manager/input_Input Last Name'), 'Adekola')
WebUI.setText(findTestObject('Page_Work Order  Create- Workforce Manager/input_Input Phone Number'), 'Cole')
WebUI.setText(findTestObject('Page_Work Order  Create- Workforce Manager/input_Input Email Address'), 'testautomation@cicod.com')
WebUI.setText(findTestObject('Page_Work Order  Create- Workforce Manager/input_house_no'), '45b')
WebUI.setText(findTestObject('Page_Work Order  Create- Workforce Manager/input_street'), 'Admiralty Way')

WebUI.selectOptionByValue(findTestObject('Page_Work Order  Create- Workforce Manager/select_AngolaCameroonCongoEthopiaNigeriaSouth Af'), 
    'Nigeria', false)
WebUI.selectOptionByValue(findTestObject('Page_Work Order  Create- Workforce Manager/select_Select StateAbiaAbujaAdamawaAkwa IbomAnam'), 
    'Lagos', false)

WebUI.switchToWindowTitle('Work Order | Create- Workforce Manager')

WebUI.click(findTestObject('Page_Work Order  Create- Workforce Manager/input_optionsRadios1'))
WebUI.click(findTestObject('Page_Work Order  Create- Workforce Manager/input_optionsChecks1'))
WebUI.setText(findTestObject('Page_Work Order  Create- Workforce Manager/input_Select Date'), '2026-05-20')

// === FILE UPLOAD ===
String filePath = RunConfiguration.getProjectDir() + '/Include/test-files/data_backup.png'
assert new File(filePath).exists() : "❌ Upload file not found: " + filePath

TestObject uploadObj = findTestObject('Page_Work Order  Create- Workforce Manager/input_take-photo-input-selected_file')
WebUI.waitForElementPresent(uploadObj, 10)
WebUI.uploadFile(uploadObj, filePath)

WebUI.click(findTestObject('Page_Work Order  Create- Workforce Manager/input_should_assign_ticket'))
WebUI.selectOptionByValue(
    findTestObject('Page_Work Order  Create- Workforce Manager/select_assign-to-user'),
    GlobalVariable.assignUserValue,
    false
)

// -----------------------------
// CREATE TASK
// -----------------------------
WebUI.waitForElementClickable(findTestObject('Page_Work Order  Create- Workforce Manager/button_Create'), 20)
WebUI.click(findTestObject('Page_Work Order  Create- Workforce Manager/button_Create'))

// -----------------------------
// CAPTURE TICKET ID FROM POPUP
// -----------------------------
TestObject ticketPopup = new TestObject()
ticketPopup.addProperty('xpath', ConditionType.EQUALS, "//*[contains(text(),'Ticket ID')]")

WebUI.waitForElementVisible(ticketPopup, 15)
String popupText = WebUI.getText(ticketPopup)
println('✅ Popup text: ' + popupText)

def matcher = popupText =~ '\\d+'
if (matcher.find()) {
    String capturedId = matcher.group()
    GlobalVariable.capturedTicketId = capturedId
    println('💾 Saved Ticket ID to GlobalVariable: ' + capturedId)
} else {
    throw new Exception('No Ticket ID found in popup: ' + popupText)
}

// Close the popup
try {
    WebUI.click(findTestObject('Page_Work Order  Create- Workforce Manager/button_message-dismiss'))
    WebUI.delay(2)
} catch (Exception e) {
    println('Note: dismiss button click failed, continuing anyway')
}