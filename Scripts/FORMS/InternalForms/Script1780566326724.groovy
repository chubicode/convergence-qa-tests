import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

import java.nio.file.Files
import java.nio.file.Paths

// ============================
// DYNAMIC VALUES
// ============================
String formTitle = "AUTO_FORM_" + new Date().format("yyyyMMdd_HHmmss")
println("📝 Form title: " + formTitle)

String uploadFile = RunConfiguration.getProjectDir() + "/Include/test-files/Enable_2_factor_authentication.jpg"

assert Files.exists(Paths.get(uploadFile)) : "❌ Upload file not found: " + uploadFile
println("📁 Upload file: " + uploadFile)

// URL-encode the title for the share URL (replaces spaces with %20, etc.)
String encodedTitle = java.net.URLEncoder.encode(formTitle, "UTF-8")
String shareUrl = "https://testgov.1gov.ng/wfm/index.php?r=webForm/internal&id=" + encodedTitle
println("🔗 Share URL will be: " + shareUrl)

// ============================
// LOGIN
// ============================
WebUI.openBrowser('')
WebUI.navigateToUrl('https://www.1gov.ng/login')

WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_1Government Login/input_Enter your MDA'), 'testgov')
WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_1Government Login/input_Enter Email'), 'apisupport@cicod.com')
WebUI.setEncryptedText(findTestObject('Pages_NEW/WorkOrder_View/Page_1Government Login/input_Enter password'), '9zJdpvfgkgtFa8MQRHGI0w==')

WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_1Government Login/img_img'))
WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_1Government Login/button_Login'))
println("✅ Logged in")

WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_1Gov/svg_svg'))
WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_1Gov/p_Automate Government MDAs critical operations,'))

// ============================
// NAVIGATE TO FORMS
// ============================
WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Work Order  Index- Workforce Manager/a_Forms'))
WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Work Order  Index- Workforce Manager/a_Create Form'))
println("✅ Create Form page opened")

// ============================
// FILL FORM TITLE (DYNAMIC)
// ============================
WebUI.setText(
    findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Create- Workforce Manager/input_Enter Name of Form'),
    formTitle
)
println("✅ Form title set: " + formTitle)

// ============================
// UPLOAD FORM IMAGE (FROM INCLUDE FOLDER)
// ============================
WebUI.uploadFile(
    findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Create- Workforce Manager/input_form-upload_input'),
    uploadFile
)
println("✅ Form image uploaded")

WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Create- Workforce Manager/p_p'))

WebUI.setText(
    findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Create- Workforce Manager/div_ql-editor ql-blank'),
    'TEST'
)

WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Create- Workforce Manager/button_Create'))
println("✅ Form created")

// ============================
// CONFIGURE QUEUE
// ============================
WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Update- Workforce Manager/button_Select Queue'))
WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Update- Workforce Manager/input_Search'), 'QA')
WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Update- Workforce Manager/a_QA'))

WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Update- Workforce Manager/button_Select Queue'))
WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Update- Workforce Manager/a_Approval Test'))

WebUI.setText(
    findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Update- Workforce Manager/div_ql-editor ql-blank'),
    'TEST'
)

WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Update- Workforce Manager/button_save_form_fields'))
println("✅ Queue configured")

// ============================
// SHARE FORM
// ============================
WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/InternalForm/img_img'))
WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/InternalForm/a_Share'))
WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/InternalForm/button_Copy'))
println("✅ Share link copied")

// ============================
// OPEN FORM IN NEW TAB (USING DYNAMIC URL)
// ============================
WebUI.newTab('')
WebUI.navigateToUrl(shareUrl)
WebUI.waitForPageLoad(20)
WebUI.delay(3)
println("✅ Opened form: " + shareUrl)

// ============================
// FILL CONTACT INFO
// ============================
WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_contact.fields.title'), 'MR')
WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_contact.fields.first_name'), 'COLE')
WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_contact.fields.last_name'), 'ADENIYI')
WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_1 (702) 123-4567'), '+234 875 748 903')
WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_contact.fields.email_address'), 'apisupport@cicod.com')
WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_address.fields.street'), 'Admiralty')
WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_address.fields.address_line_2'), 'Crown Interactive ltd.')

// Country
WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_Select - country'))
WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_Search list'), 'nigeri')
WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/button_Nigeria'))

// State
WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_Select - state'))
WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_Search list'), 'lagos')
WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/button_Nigeria'))

WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/span_Satisfied'))
WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/span_Frequent'))
WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/span_I hereby consent to the collection, use, pr'))

WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/button_Accept'))
WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/button_Submit'))

WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_address.fields.city'), 'lagos')
WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_address.fields.postal_zip code'), '123456')

WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/button_Submit'))

// ============================
// ORGANIZATION INFO
// ============================
WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_organization.fields.name_of_organaization'), 'Crown Intercative ltd')
WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_organization.fields.street'), 'Admiralty')
WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_organization.fields.address_line_2'), 'Crown Interactive')

WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_Select - country_1'))
WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_Search list'), 'Nigeria')

WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_Select - state_1'))
WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_organization.fields.city'), 'Lagos')

WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_Select - country_1'))
WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/button_Nigeria'))

WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_Search list'), 'Lagos')
WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/button_Nigeria'))

WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_organization.fields.postal_zip code'), '12345')

WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_organization.fields.first_name'), 'Cole')
WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_organization.fields.last_name'), 'Adeniyi')
WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_1 (702) 123-4567_1'), '+234 987 045 8')
WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_organization.fields.email_address'), 'apisupport@cicod.com')

WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/button_Submit'))

// ============================
// OTHER FORM FIELDS
// ============================
WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_How often is test conducted'), 'often')
WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/button_Submit'))

WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_organization.fields.title'), 'mr')
WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/button_Submit'))

WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_Date'), '2026-06-04')
WebUI.setText(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_Number of times test is conducted in a ye'), '24')

WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/input_For outsourced testing, how much does it c'))

WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/button_Submit'))

WebUI.click(findTestObject('Pages_NEW/WorkOrder_View/Page_Web Form  Internal- Workforce Manager/button_Close'))

println("✅ FORM CREATED & SUBMITTED: " + formTitle)