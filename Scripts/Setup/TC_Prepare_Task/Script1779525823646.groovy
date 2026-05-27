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

WebUI.callTestCase(findTestCase('Authentication/TC_Login'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('Navigation/TC_Open_Task_Module'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('Setup/TC_Create_Task'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('Setup/TC_Open_Task_Details'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('TaskActions/Uploads/Upload_File_From_Govdrive'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('TaskActions/Uploads/Upload_file_Without_Classification'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('TaskActions/Table_Actions/Esign'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('TaskActions/Table_Actions/Download_File'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('TaskActions/Table_Actions/View_File'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('TaskActions/Change_Priority/Priority'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('TaskActions/Comments/TC_Add_Comment'), [:], FailureHandling.STOP_ON_FAILURE)

