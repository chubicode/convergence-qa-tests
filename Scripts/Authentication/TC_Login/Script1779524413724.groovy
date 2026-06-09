import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

import internal.GlobalVariable as GlobalVariable

// ============================
// LOGIN (uses active profile: staging or prod)
// ============================
WebUI.openBrowser('')

WebUI.navigateToUrl(GlobalVariable.baseUrl + 'login')

WebUI.setText(
    findTestObject('Page_1Government Login/input_Enter your MDA'),
    GlobalVariable.username
)

WebUI.setText(
    findTestObject('Page_1Government Login/input_Enter Email'),
    GlobalVariable.email
)

// setEncryptedText decrypts the value before typing it
WebUI.setEncryptedText(
    findTestObject('Page_1Government Login/input_Enter password'),
    GlobalVariable.password
)

WebUI.click(findTestObject('Page_1Government Login/button_Login'))

println("✅ Logged in as " + GlobalVariable.email + " on " + GlobalVariable.baseUrl)