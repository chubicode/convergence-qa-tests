import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement

import java.util.Arrays

// -----------------------------------
// CREATE TASK
// -----------------------------------

WebUI.callTestCase(
    findTestCase('Tasks/Task Creation'),
    [:],
    FailureHandling.STOP_ON_FAILURE
)

// -----------------------------------
// WAIT SUCCESS MESSAGE
// -----------------------------------

TestObject modal = new TestObject()

modal.addProperty(
    'xpath',
    ConditionType.EQUALS,
    "//*[contains(text(),'Ticket ID')]"
)

WebUI.waitForElementVisible(
    modal,
    30
)

// -----------------------------------
// GET MESSAGE
// -----------------------------------

String successMessage =
WebUI.getText(modal)

println(
"SUCCESS → " +
successMessage
)

// -----------------------------------
// EXTRACT TICKET ID
// -----------------------------------

def matcher =
(successMessage =~ /\d+/)

if (!matcher.find()) {

    throw new Exception(
        "Ticket ID not found"
    )
}

String ticketId =
matcher.group()

println(
"Captured Ticket → " +
ticketId
)

// -----------------------------------
// CLOSE MODAL
// -----------------------------------

WebUI.click(
findTestObject(
'Page_Work Order  Create- Workforce Manager/button_message-dismiss'
)
)

WebUI.waitForPageLoad(20)

WebUI.delay(2)

// -----------------------------------
// OPEN CREATED BY ME
// -----------------------------------

WebUI.click(
findTestObject(
'Page_Work Order  Index- Workforce Manager/a_Created By Me'
)
)

WebUI.waitForPageLoad(10)

WebUI.delay(3)

// -----------------------------------
// SEARCH
// -----------------------------------

TestObject search =
findTestObject(
'Page_Work Order  Index- Workforce Manager/input_Search'
)

WebUI.waitForElementVisible(
search,
30
)

WebUI.click(search)

WebUI.clearText(search)

WebUI.setText(
search,
ticketId
)

WebUI.sendKeys(
search,
Keys.chord(Keys.ENTER)
)

println(
"Searching → " +
ticketId
)

WebUI.delay(8)

// -----------------------------------
// WAIT FOR RESULT ROW
// -----------------------------------

TestObject row =
new TestObject()

row.addProperty(
'xpath',
ConditionType.EQUALS,

"//tr[.//*[contains(text(),'${ticketId}')]]"
)

boolean found =
WebUI.waitForElementPresent(
row,
30
)

if (!found) {

WebUI.takeScreenshot()

throw new Exception(
"Ticket row not found → " +
ticketId
)

}

println(
"Ticket row found"
)

// -----------------------------------
// FIND TITLE INSIDE SAME ROW
// -----------------------------------

TestObject titleLink =
new TestObject()

titleLink.addProperty(
'xpath',
ConditionType.EQUALS,

"""
(
 //tr[
   .//*[contains(text(),'${ticketId}')]
 ]
//td[3]//a
)
|
(
 //tr[
   .//*[contains(text(),'${ticketId}')]
 ]
//td[3]//*[self::span or self::div]
)
"""
)

WebUI.waitForElementVisible(
titleLink,
30
)

WebUI.scrollToElement(
titleLink,
10
)

WebElement link =
WebUI.findWebElement(
titleLink,
20
)

// force visible

WebUI.executeJavaScript(
'''
arguments[0].scrollIntoView({
block:'center'
});
''',
Arrays.asList(link)
)

WebUI.delay(2)

// -----------------------------------
// CLICK TITLE
// -----------------------------------

println(
"Opening ticket"
)

try {

WebUI.click(
titleLink
)

}

catch(Exception e){

println(
"Normal click failed → using JS"
)

WebUI.executeJavaScript(
'''
arguments[0].click();
''',
Arrays.asList(link)
)

}

// -----------------------------------
// WAIT FOR TASK DETAILS
// -----------------------------------

boolean opened =
false

for(int i=0;i<30;i++){

WebUI.delay(1)

TestObject details =
new TestObject()

details.addProperty(
'xpath',
ConditionType.EQUALS,

"""
//*[contains(text(),'Priority')]
|
//*[contains(text(),'Task Details')]
|
//*[contains(text(),'Queue')]
"""
)

if(

WebUI.verifyElementPresent(
details,
1,
FailureHandling.OPTIONAL

)

){

opened=true
break

}

}

if(!opened){

WebUI.takeScreenshot()

throw new Exception(
"Ticket clicked but details never opened"
)

}

println(
"TASK DETAILS OPENED → " +
ticketId
)