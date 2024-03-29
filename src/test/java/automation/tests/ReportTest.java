package automation.tests;

import automation.report.TestData;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.lang.reflect.Method;


public class ReportTest {

    @BeforeSuite
    public void beforeSuite() {
        TestData.mainPackage = ("automation.report");
        TestData.testPackage = ("automation.tests");
        TestData.emptyReportDirectory();
    }

    @BeforeMethod
    public void beforeMethod(Method method){
        TestData.startTest(method.getAnnotation(Test.class).testName(),method.getAnnotation(Test.class).description());
    }

    @AfterMethod
    public void afterMethod() {
        TestData.endTest();
    }
    @Test(testName = "Test 1", description = "sample test to show report features")
    public void Test1()
    {
        TestData.addAssertStep("verify 1 and 2 are equal",1,2);
        TestData.addTestStep("this is awesome",null);
        TestData.addTestStep("test step for screenshot",null,"sample base64");
        TestData.addAssertStep("verify 1 and 2 are equal",1,2);


    }
}
