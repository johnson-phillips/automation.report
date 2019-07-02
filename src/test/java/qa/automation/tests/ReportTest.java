package qa.automation.tests;

import qa.automation.report.TestData;
import org.testng.annotations.Test;


/**
 * Created by johnson_phillips on 2/1/18.
 */
public class ReportTest {
    @Test
    public void Test1() throws Exception
    {
        TestData.printconsole = true;
        TestData.startTest("Step 1",1);

        TestData.addAssertStep("sdvd",1,2);
        TestData.endTest();

        TestData.startTest("Step 2",1);

        TestData.addTestStep("sdvd",null);
        TestData.endTest();




    }
}
