import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

// WAIT FOR PAGE TO LOAD FULLY
WebUI.waitForPageLoad(20)

WebUI.waitForElementPresent(findTestObject('Page_Work Order  Index- Workforce Manager/input_Search'), 20)

// WAIT FOR PAGE TO LOAD FULLY
WebUI.waitForPageLoad(20)

WebUI.waitForElementPresent(findTestObject('Page_Work Order  Index- Workforce Manager/input_Search'), 20)

WebUI.click(findTestObject('Page_Work Order  View- Workforce Manager/div_Hi there, For line manager escalation or app'))

WebUI.click(findTestObject('Page_Work Order  View- Workforce Manager/button_assign-modal-close-btn_1'))

WebUI.click(findTestObject('Page_Work Order  View- Workforce Manager/a_Add Comment'))

WebUI.setText(findTestObject('Page_Work Order  View- Workforce Manager/textarea_To mention a user or role, type  in fr'), 
    '@chubby')

WebUI.click(findTestObject('Page_Work Order  View- Workforce Manager/a_Chubby Chubbystandard user'))

WebUI.setText(findTestObject('Page_Work Order  View- Workforce Manager/textarea_To mention a user or role, type  in fr'), 
    '[~Chubby Chubby] ')

WebUI.setText(findTestObject('Page_Work Order  View- Workforce Manager/textarea_To mention a user or role, type  in fr'), 
    '[~Chubby Chubby] Smoke test comment Automation ')

WebUI.click(findTestObject('Page_Work Order  View- Workforce Manager/a_save_comment'))

WebUI.waitForElementVisible(findTestObject('Page_Work Order  View- Workforce Manager/div_Comment saved successfully'), 20)

WebUI.delay(2)

//WebUI.waitForElementVisible(findTestObject('Page_Work Order  View- Workforce Manager/div_Comment saved successfully'), 20)
WebUI.waitForElementClickable(findTestObject('Page_Work Order  View- Workforce Manager/button_SaveComment'), 20)

WebUI.click(findTestObject('Page_Work Order  View- Workforce Manager/button_SaveComment'))

