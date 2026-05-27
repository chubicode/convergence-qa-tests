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

WebUI.openBrowser('')

WebUI.navigateToUrl('https://convergenceondemand.com/login')

WebUI.setText(findTestObject('Page_1Government Login/input_Enter your MDA'), 'govtest')

WebUI.setText(findTestObject('Page_1Government Login/input_Enter Email'), 'apisupport@cicod.com')

WebUI.click(findTestObject('Page_1Government Login/section_Welcome BackEnter your credentials to a'))

WebUI.setEncryptedText(findTestObject('Page_1Government Login/input_Enter password'), '9zJdpvfgkgtFa8MQRHGI0w==')

WebUI.click(findTestObject('Page_1Government Login/button_Login'))

WebUI.click(findTestObject('Page_1Government Login/div_An OTP has been sent to your email'))

WebUI.click(findTestObject('Page_1Government Login/div_flex flex-nowrap overflow-x-auto justify-cen'))

WebUI.setText(findTestObject('Page_1Government Login/input_focus_outline-none focus_ring-2 focus_ring'), '4')

WebUI.setText(findTestObject('Page_1Government Login/input_focus_outline-none focus_ring-2 focus_ring_1'), '6')

WebUI.setText(findTestObject('Page_1Government Login/input_focus_outline-none focus_ring-2 focus_ring_2'), '7')

WebUI.setText(findTestObject('Page_1Government Login/input_focus_outline-none focus_ring-2 focus_ring_3'), '5')

WebUI.setText(findTestObject('Page_1Government Login/input_focus_outline-none focus_ring-2 focus_ring_4'), '8')

WebUI.setEncryptedText(findTestObject('Page_1Government Login/input_Enter password'), '4zGPQDZ5VqE=')

WebUI.click(findTestObject('Page_1Government Login/button_Verify Code'))

WebUI.click(findTestObject('Page_1Government Login/div_FAILED_VALIDATION'))

