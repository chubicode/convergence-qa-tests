import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import java.nio.file.Files
import java.nio.file.Paths

// ======================================
// CONFIG
// ======================================
String taskName = 'TEST AUTOMATION'

String uploadFile =
        RunConfiguration.getProjectDir() +
        "/Include/test-files/TASK CYCLE.pdf"

// ======================================
// START
// ======================================
WebUI.waitForPageLoad(20)
WebUI.switchToDefaultContent()

println("Looking for task: " + taskName)

// ======================================
// DYNAMIC TASK LOCATOR
// ======================================
TestObject task = new TestObject()

task.addProperty(
        'xpath',
        ConditionType.EQUALS,
        "//p[contains(normalize-space(.),'" + taskName + "')]"
)

// ======================================
// FIND TASK
// ======================================
boolean taskExists =
        WebUI.waitForElementPresent(
                task,
                20,
                FailureHandling.OPTIONAL
        )

println("Task exists = " + taskExists)

if (!taskExists) {

    WebUI.refresh()

    WebUI.waitForPageLoad(20)

    taskExists =
            WebUI.waitForElementPresent(
                    task,
                    20,
                    FailureHandling.OPTIONAL
            )
}

assert taskExists :
        "Task '${taskName}' not found"

// ======================================
// OPEN TASK
// ======================================
WebUI.scrollToElement(task, 10)

WebUI.enhancedClick(task)

WebUI.waitForPageLoad(20)

// ======================================
// OPEN UPLOAD MODAL
// ======================================
TestObject uploadBtn =
findTestObject(
'Page_Work Order  View- Workforce Manager/Page_Work Order  View- Workforce Manager/a_dLabel'
)

WebUI.waitForElementClickable(
        uploadBtn,
        20
)

WebUI.enhancedClick(
        uploadBtn
)

println("Upload modal opened")

// ======================================
// VERIFY FILE EXISTS
// ======================================
assert Files.exists(Paths.get(uploadFile)) :
        "File not found -> " + uploadFile

println("Uploading -> " + uploadFile)

// ======================================
// TARGET HIDDEN INPUT DIRECTLY
// DO NOT CLICK FROM DEVICE
// ======================================
TestObject fileInput = new TestObject()

fileInput.addProperty(
        "xpath",
        ConditionType.EQUALS,
        "//*[@id='file_upload']"
)

WebUI.waitForElementPresent(
        fileInput,
        20
)

// Make hidden input accessible
WebUI.executeJavaScript(
"""
arguments[0].style.display='block';
arguments[0].style.visibility='visible';
arguments[0].removeAttribute('hidden');
arguments[0].removeAttribute('disabled');
""",
[
WebUI.findWebElement(
        fileInput,
        20
)
]
)

// ======================================
// DIRECT FILE INJECTION
// NO WINDOWS PICKER
// ======================================
WebUI.uploadFile(
        fileInput,
        uploadFile
)

println("File injected successfully")

// ======================================
// WAIT FOR UPLOAD
// ======================================
WebUI.delay(8)

// ======================================
// CLICK NO MODAL
// ======================================
TestObject noButton = new TestObject()

noButton.addProperty(
        "xpath",
        ConditionType.EQUALS,
        "//button[normalize-space()='No']"
)

boolean modalPresent =
        WebUI.waitForElementPresent(
                noButton,
                10,
                FailureHandling.OPTIONAL
        )

if (modalPresent) {

    WebUI.waitForElementClickable(
            noButton,
            10
    )

    WebUI.enhancedClick(
            noButton
    )

    println("Clicked NO")
}

println("Upload completed")