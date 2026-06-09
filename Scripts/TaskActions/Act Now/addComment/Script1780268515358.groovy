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

WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Work Order  View- Workforce Manager/a_dLabel'))

WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Work Order  View- Workforce Manager/a_Add Comment'))

WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Work Order  View- Workforce Manager/textarea_To mention a user or role, type  in fr'), 
    '@api')

WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Work Order  View- Workforce Manager/a_api supportstandard user'))

WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Work Order  View- Workforce Manager/textarea_To mention a user or role, type  in fr'), 
    '[~api support] Test Automation 1267891')

WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Work Order  View- Workforce Manager/a_save_comment'))

TestObject dismissBtn = findTestObject(
    'Object Repository/Pages_NEW/WorkOrder_View/Page_Work Order  View- Workforce Manager/button_message-dismiss'
)

boolean visible = WebUI.waitForElementVisible(
    dismissBtn,
    5,
    FailureHandling.OPTIONAL
)

if (visible) {
    try {
        WebUI.scrollToElement(dismissBtn, 2)
        WebUI.enhancedClick(dismissBtn)
        println("Message dismissed")
    } catch (Exception e) {
        println("Dismiss button present but not clickable")
    }
} else {
    println("No dismiss message displayed")
}

