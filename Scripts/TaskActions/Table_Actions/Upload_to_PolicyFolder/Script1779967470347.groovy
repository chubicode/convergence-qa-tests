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

WebUI.waitForPageLoad(20)

WebUI.waitForElementPresent(findTestObject('Page_Work Order  Index- Workforce Manager/input_Search'), 20)

WebUI.switchToWindowTitle('Work Order | View- Workforce Manager')

WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/img_fileUploadIcon'))

WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/iframe_cdeWidget'))

WebUI.click(findTestObject('Page_Work Order  View- Workforce Manager/div_QAJul 19, 2024 7_34_07 AM'))

WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/btn_savePath'))

