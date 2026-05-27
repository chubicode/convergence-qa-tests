import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.waitForPageLoad(20)

// Open priority editor
WebUI.waitForElementClickable(
    findTestObject(
        'Page_Work Order  View- Workforce Manager/i_fa fa-pencil text-interact-blue'
    ),
    20
)

WebUI.click(
    findTestObject(
        'Page_Work Order  View- Workforce Manager/i_fa fa-pencil text-interact-blue'
    )
)

// Select High
WebUI.waitForElementClickable(
    findTestObject(
        'Page_Work Order  View- Workforce Manager/small_High'
    ),
    20
)

WebUI.click(
    findTestObject(
        'Page_Work Order  View- Workforce Manager/small_High'
    )
)

// Save
WebUI.click(
    findTestObject(
        'Page_Work Order  View- Workforce Manager/div_Successful'
    )
)

// Confirm
WebUI.click(
    findTestObject(
        'Page_Work Order  View- Workforce Manager/button_OK'
    )
)