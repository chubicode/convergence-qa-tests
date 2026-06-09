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
String uploadFile = RunConfiguration.getProjectDir() + "/Include/test-files/TASK_CYCLE.pdf"

assert Files.exists(Paths.get(uploadFile)) :
        "File not found -> " + uploadFile

println("📁 File to upload: " + uploadFile)

// ======================================
// SAVE CURRENT URL + COUNT FILES BEFORE
// ======================================
String originalUrl = WebUI.getUrl()
println("📍 Starting URL: " + originalUrl)

WebUI.waitForPageLoad(30)
WebUI.switchToDefaultContent()

def filesBeforeCount = WebUI.executeJavaScript("""
    var match = document.body.textContent.match(/Attached Files\\s*(\\d+)/);
    return match ? match[1] : 'unknown';
""", null)
println("📊 Files BEFORE: " + filesBeforeCount)

// ======================================
// EXTRACT DOMAIN + TASK ID + NAVIGATE TO UPLOAD PAGE
// ======================================
String domain = ""
if (originalUrl.contains("://")) {
    int afterProtocol = originalUrl.indexOf("://") + 3
    int domainEnd = originalUrl.indexOf("/", afterProtocol)
    domain = domainEnd == -1 ? originalUrl : originalUrl.substring(0, domainEnd)
}

String taskId = ""
if (originalUrl.contains("id=")) {
    taskId = originalUrl.substring(originalUrl.indexOf("id=") + 3)
    if (taskId.contains("&")) {
        taskId = taskId.substring(0, taskId.indexOf("&"))
    }
}

if (taskId.isEmpty() || domain.isEmpty()) {
    throw new Exception("❌ Could not extract domain/task ID from: " + originalUrl)
}

String uploadUrl = domain + "/wfm/index.php?r=workOrder/addfile&id=" + taskId
WebUI.navigateToUrl(uploadUrl)
WebUI.waitForPageLoad(30)
WebUI.delay(3)
println("✅ At upload page")

// ======================================
// SET FILE
// ======================================
TestObject fileInput = new TestObject("fileInput")
fileInput.addProperty("id", ConditionType.EQUALS, "WorkOrderFile_filename")

WebUI.waitForElementPresent(fileInput, 15)
WebUI.uploadFile(fileInput, uploadFile)
WebUI.delay(2)

String selectedFile = WebUI.getAttribute(fileInput, "value")
println("📁 File set: " + selectedFile)

// ======================================
// SET DESCRIPTION
// ======================================
TestObject description = new TestObject("description")
description.addProperty("xpath", ConditionType.EQUALS, "//textarea[@name='WorkOrderAttachments[description]']")

if (WebUI.waitForElementPresent(description, 5, FailureHandling.OPTIONAL)) {
    WebUI.setText(description, "Automated upload")
    println("✅ Description set")
}

// ======================================
// SUBMIT THE FORM PROGRAMMATICALLY (not just click)
// ======================================
def submitResult = WebUI.executeJavaScript("""
    var fileInput = document.getElementById('WorkOrderFile_filename');
    if (!fileInput) return 'NO FILE INPUT';
    
    var form = fileInput.closest('form');
    if (!form) return 'NO FORM FOUND';
    
    var formAction = form.action;
    var formMethod = form.method;
    
    // Trigger the form submit — this properly handles the file upload
    form.submit();
    
    return 'submitted to ' + formAction + ' via ' + formMethod;
""", null)
println("📤 Submit result: " + submitResult)

WebUI.waitForPageLoad(60)
WebUI.delay(8)

println("📍 URL after submit: " + WebUI.getUrl())

// ======================================
// CHECK FOR ERRORS
// ======================================
def errorCheck = WebUI.executeJavaScript("""
    var allErrors = [];
    var selectors = ['.alert-danger', '.alert-warning', '.error', '.has-error', '.text-danger'];
    for (var s = 0; s < selectors.length; s++) {
        var els = document.querySelectorAll(selectors[s]);
        for (var i = 0; i < els.length; i++) {
            var text = els[i].textContent.trim();
            if (text && text.length > 0 && text.length < 300) {
                allErrors.push(text);
            }
        }
    }
    return allErrors.length > 0 ? allErrors.join(' | ') : 'NO ERRORS';
""", null)
println("⚠️ Errors: " + errorCheck)

// Handle "No" button
TestObject noButton = new TestObject("noButton")
noButton.addProperty("xpath", ConditionType.EQUALS, "//button[normalize-space()='No']")

if (WebUI.waitForElementPresent(noButton, 5, FailureHandling.OPTIONAL)) {
    try {
        WebUI.click(noButton)
    } catch (Exception e) {
        WebUI.enhancedClick(noButton)
    }
    println("✅ Clicked NO")
}

// ======================================
// RETURN TO TASK + VERIFY
// ======================================
WebUI.navigateToUrl(originalUrl)
WebUI.waitForPageLoad(30)
WebUI.delay(5)

def filesAfterCount = WebUI.executeJavaScript("""
    var match = document.body.textContent.match(/Attached Files\\s*(\\d+)/);
    return match ? match[1] : 'unknown';
""", null)
println("📊 Files AFTER: " + filesAfterCount)

if (filesBeforeCount != 'unknown' && filesAfterCount != 'unknown') {
    int before = filesBeforeCount.toInteger()
    int after = filesAfterCount.toInteger()
    if (after > before) {
        println("✅ UPLOAD CONFIRMED: " + before + " → " + after + " attachments")
    } else {
        throw new Exception("❌ SILENT UPLOAD FAILURE: count didn't increase (" + before + " → " + after + ")")
    }
}