import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import java.io.File as File
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import org.openqa.selenium.Keys as Keys
import internal.GlobalVariable as GlobalVariable

// Open Browser
WebUI.callTestCase(findTestCase('Authentication/TC_Login'), [:], FailureHandling.STOP_ON_FAILURE)

// Wait a little for page load
WebUI.delay(5)

// Handle Popup if it appears
if (WebUI.verifyElementPresent(findTestObject('Popup/button_ClosePopup'), 5, FailureHandling.OPTIONAL)) {
    WebUI.waitForElementClickable(findTestObject('Popup/button_ClosePopup'), 10)

    WebUI.click(findTestObject('Popup/button_ClosePopup'))
}

// Navigate directly to Work Order Page
WebUI.navigateToUrl('https://govtest.convergenceondemand.com/wfm/index.php?r=workOrder#active-tickets')

// Optional wait
WebUI.delay(5)

// Maximize Browser
'// Maximize Browser'
WebUI.maximizeWindow()

WebUI.click(findTestObject('Page_Work Order  Index- Workforce Manager/a_Create Task'))

WebUI.click(findTestObject('Page_Work Order  Create- Workforce Manager/span_Select Queue'))

WebUI.setText(findTestObject('Page_Work Order  Create- Workforce Manager/input_Search'), 'aut')

WebUI.click(findTestObject('Page_Work Order  Create- Workforce Manager/a_TEST AUTOMATION'))

WebUI.click(findTestObject('Page_Work Order  Create- Workforce Manager/span_Select Queue Type'))

WebUI.click(findTestObject('Page_Work Order  Create- Workforce Manager/span_Automated Testing'))

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

String filePath = RunConfiguration.getProjectDir() + '/Include/test-files/data_backup.png'

// safety check (prevents silent failure)
assert new File(filePath).exists()

TestObject uploadObj = findTestObject('Page_Work Order  Create- Workforce Manager/input_take-photo-input-selected_file')

WebUI.waitForElementPresent(uploadObj, 10)

WebUI.uploadFile(uploadObj, filePath)

WebUI.click(findTestObject('Page_Work Order  Create- Workforce Manager/input_should_assign_ticket'))

WebUI.selectOptionByValue(findTestObject('Page_Work Order  Create- Workforce Manager/select_assign-to-user'), '82', false)

// -----------------------------
// CREATE TASK (ONLY ONCE - FIXED)
// -----------------------------
WebUI.waitForElementClickable(findTestObject('Page_Work Order  Create- Workforce Manager/button_Create'), 20)

WebUI.click(findTestObject('Page_Work Order  Create- Workforce Manager/button_Create'))

