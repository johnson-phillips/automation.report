# automation.report
java library to create simple html report for test execution

usage:
```
add dependency in pom file
<dependency>
  <groupId>automation</groupId>
  <artifactId>report</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

Sample Report:
http://www.myappquality.com/

To start capturing test data call startTest method and pass test name and description as parameters
```
TestData.startTest(string,string);
example: TestData.startTest("verify login","test to verify user can login");
```
To add test step, call addTestStep method. This method has 2 - 3 parameters based on different needs.
To add a simple step, call below method.
```
TestData.addTestStep(string,null);
example: TestData.addTestStep("call login api",null);
```
For ui tests to add test step with screenshot. Pass the driver(selenium webdriver) object as third parameter
```
TestData.addTestStep(string,null,driver);
ex: TestData.addTestStep("step description",null,driver);
```
For api tests to add test step with request or response. Pass the true as third parameter.
```
TestData.addTestStep(string,null,true);
ex: TestData.addTestStep("request or response data",null,true);
```
To add test step with an error, call addTestStep and pass error/exception object. This will ensure this step is marked in red in the html report
```
TestData.addTestStep(string,object);
ex: TestData.addTestStep("step description","exception occured");
ex: TestData.addTestStep("step description",new Exception("some error"));
```
similarly for ui tests, to add test step with error or exception
```
TestData.addTestStep(string,object,driver);
ex: TestData.addTestStep("step description","pass error object here",driver);
```
To assert two values are equal, call addAssertStep method.
```
TestData.addAssertStep(string,expected,actual);
ex: TestData.addAssertStep("verify two strings are equal","abc","ABC");
```
if the values are equal, the step is marked as green. If the values are not equal the step is marked in red.

To stop the execution of test further if an assertion fails call addAssertStepFailOnMismatch method

```
TestData.addAssertStepFailOnMismatch(string,expected,actual);
ex: TestData.addAssertStepFailOnMismatch("verify string are equal","abc","ABC");
```
this will throw an error if the two values are not equal.

To end the test call endTest
```
TestData.endTest();
```

To get the object where all the tests data is saved call getSuite
```
let suite = TestData.getSuite();
```
Current date time is used to create report folder. To delete all report folders before any test execution call deleteReportFolder
```
TestData.emptyReportDirectory();
```
