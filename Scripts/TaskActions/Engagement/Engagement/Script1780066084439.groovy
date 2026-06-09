import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import java.io.File as File

WebUI.waitForPageLoad(20)

// Type the message
WebUI.waitForElementPresent(findTestObject('Pages_NEW/WorkOrder_Index/input_messageText'), 20)
WebUI.setText(findTestObject('Pages_NEW/WorkOrder_Index/input_messageText'), 
    'TEST AUTOMATION ON ENGAGEMENT')

// Click attach to reveal the menu
WebUI.click(findTestObject('Pages_NEW/WorkOrder_Index/btn_attach'))
WebUI.delay(1)

// Upload file DIRECTLY to hidden input (no OS picker)
String filePath = RunConfiguration.getProjectDir() + '/Include/test-files/TASK CYCLE.pdf'
assert new File(filePath).exists() : '❌ Test file not found at: ' + filePath

TestObject uploadInput = findTestObject('Pages_NEW/WorkOrder_Index/btn_attachFromDevice')
WebUI.uploadFile(uploadInput, filePath)

WebUI.delay(3)  // wait for file to attach visually

// Send the message
WebUI.click(findTestObject('Pages_NEW/WorkOrder_Index/btn_sendMessage'))

WebUI.waitForElementPresent(findTestObject('Pages_NEW/WorkOrder_Index/div_sendConfirmMessage'), 10)
WebUI.click(findTestObject('Pages_NEW/WorkOrder_Index/lnk_sendConversation'))

// === KEEP BROWSER OPEN TO VERIFY ===
println('⏸️ Test done — browser open for 10 seconds for inspection')
WebUI.delay(30)

println('✅ Message sent with file attachment')