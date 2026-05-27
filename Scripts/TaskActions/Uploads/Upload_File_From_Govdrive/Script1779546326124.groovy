import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.JavascriptExecutor

//----------------------------------------
// START
//----------------------------------------
WebDriver driver = DriverFactory.getWebDriver()

WebUI.switchToDefaultContent()
WebUI.waitForPageLoad(30)

println('Starting upload flow')


//----------------------------------------
// CLICK UPLOAD BUTTON
//----------------------------------------
WebUI.waitForElementVisible(
    findTestObject(
        'Page_Work Order  View- Workforce Manager/a_dLabel'
    ),
    30
)

WebUI.waitForElementClickable(
    findTestObject(
        'Page_Work Order  View- Workforce Manager/a_dLabel'
    ),
    30
)

WebUI.click(
    findTestObject(
        'Page_Work Order  View- Workforce Manager/a_dLabel'
    )
)

println('Upload menu opened')


//----------------------------------------
// CLICK GOVDRIVE
//----------------------------------------
WebUI.waitForElementVisible(
    findTestObject(
        'Page_Work Order  View- Workforce Manager/a_cde-upload'
    ),
    30
)

WebUI.waitForElementClickable(
    findTestObject(
        'Page_Work Order  View- Workforce Manager/a_cde-upload'
    ),
    30
)

try {

    WebUI.click(
        findTestObject(
            'Page_Work Order  View- Workforce Manager/a_cde-upload'
        )
    )

}
catch(Exception e){

    WebUI.enhancedClick(
        findTestObject(
            'Page_Work Order  View- Workforce Manager/a_cde-upload'
        )
    )
}

println('GovDrive selected')


//----------------------------------------
// WAIT FOR MODAL
//----------------------------------------
WebUI.switchToDefaultContent()

WebUI.waitForElementVisible(
    findTestObject(
        'Page_Work Order  View- Workforce Manager/iframe_cde-widget'
    ),
    30
)


//----------------------------------------
// SWITCH TO IFRAME
//----------------------------------------
boolean switched = false

for(int i=0;i<3;i++){

    try{

        WebUI.switchToFrame(
            findTestObject(
                'Page_Work Order  View- Workforce Manager/iframe_cde-widget'
            ),
            10
        )

        switched = true
        break

    }
    catch(Exception e){

        WebUI.delay(2)
    }
}

if(!switched){

    throw new Exception(
        'Failed switching into GovDrive iframe'
    )
}

println('Inside iframe')


//----------------------------------------
// WAIT FOR FILES
//----------------------------------------
WebUI.delay(5)


//----------------------------------------
// CLICK FILE CHECKBOX
//----------------------------------------

println("Waiting for files to appear...")

boolean found = false
WebElement checkbox = null

for(int i=0; i<20; i++){

    List<WebElement> checkboxes =
    driver.findElements(
        By.xpath(
            "//input[@type='checkbox'] | //*[@role='checkbox']"
        )
    )

    println("Checkbox count = " + checkboxes.size())

    if(!checkboxes.isEmpty()){

        checkbox = checkboxes[0]

        ((JavascriptExecutor)driver)
        .executeScript(
            "arguments[0].scrollIntoView(true)",
            checkbox
        )

        WebUI.delay(1)

        ((JavascriptExecutor)driver)
        .executeScript(
            "arguments[0].click();",
            checkbox
        )

        found = true

        println("File selected")

        break
    }

    WebUI.delay(2)
}

if(!found){

    throw new Exception(
        "No file checkbox found after waiting"
    )
}


//----------------------------------------
// WAIT FOR INSERT TO ENABLE
//----------------------------------------
WebUI.delay(3)

WebElement insertBtn = null

for(int i=0;i<15;i++){

    try{

        insertBtn =
        driver.findElement(
            By.xpath(
                "//button[normalize-space()='Insert']"
            )
        )

        boolean visible =
            insertBtn.isDisplayed()

        boolean enabled =
            insertBtn.isEnabled()

        String disabled =
            insertBtn.getAttribute(
                "disabled"
            )

        if(
            visible &&
            enabled &&
            disabled == null
        ){

            break
        }

    }
    catch(Exception ex){

    }

    WebUI.delay(1)
}

if(insertBtn == null){

    throw new Exception(
        'Insert button not available'
    )
}


//----------------------------------------
// CLICK INSERT
//----------------------------------------
((JavascriptExecutor)driver)
.executeScript(
"""
arguments[0].scrollIntoView({
block:'center'
});
""",
insertBtn
)

WebUI.delay(1)

((JavascriptExecutor)driver)
.executeScript(
"arguments[0].click();",
insertBtn
)

println('Insert clicked')

//----------------------------------------
// HANDLE SUCCESS OK POPUP
//----------------------------------------

WebUI.switchToDefaultContent()

try {

	WebUI.delay(3)

	WebElement okButton =
	driver.findElement(
		By.xpath(
			"//button[normalize-space()='OK'] | //a[normalize-space()='OK']"
		)
	)

	((JavascriptExecutor)driver)
	.executeScript(
		"arguments[0].click();",
		okButton
	)

	println("Success popup closed")

}
catch(Exception e){

	println("No OK popup displayed")
}


//----------------------------------------
// EXIT
//----------------------------------------
WebUI.switchToDefaultContent()

println('Upload completed')

