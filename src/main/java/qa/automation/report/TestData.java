package qa.automation.report;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.testng.Assert;

import java.io.File;
import java.io.FileFilter;
import java.time.Instant;
import java.util.*;

import static io.restassured.RestAssured.given;

/**
 * Created by johnson_phillips on 1/27/18.
 */
public class TestData {
    public static ObjectMapper mapper = new ObjectMapper();
    static Test test = new Test();
    public static boolean printconsole = false;
    public static boolean printerror = true;
    public static String starttime;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static Suite suite = new Suite();
    public static boolean uploadScreenshotToS3 = false;
    public static 	String	build_number;
    public static String job_name;
    public static String ProductID;
    public static String reportPath =  System.getProperty("user.dir") + "/report/" + suite.id + "/";
    static List<Step> steps = new ArrayList<Step>();
    static  StackTraceElement stp = null;
    static StackTraceElement tst = null;
    public static ObjectNode dataset;
    static JsonNode currentTest;
    public static String reportUploadUrl;

    static
    {
        try {
            build_number = System.getProperty("BUILD_NUMBER");
            if(build_number != null)
            {
                uploadScreenshotToS3 = true;
            }
            File file = new File(System.getProperty("user.dir") + "/report");
            if(file.exists()) {
                File[] files = file.listFiles(new FileFilter() {
                    public boolean accept(File f) {
                        return f.isDirectory();
                    }
                });
                Arrays.sort(files, new Comparator<File>() {
                    public int compare(File f1, File f2) {
                        return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
                    }
                });

//            FileUtils.deleteDirectory(new File(System.getProperty("user.dir") + "/report"));
                if (files.length > 5) {
                    for (int i = 0; i < files.length - 4; i++) {
                        FileUtils.deleteDirectory(files[i]);
                    }
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void addTestStep(String name,Exception ex) throws Exception
    {
        getStackTrace();
        Step step = new Step();
        if(starttime == null)
        {
            starttime = Instant.now().toString();
        }
        step.starttime = starttime;
        starttime = Instant.now().toString();
        step.endtime = starttime;
        step.name = name;
        step.classname = stp != null?(stp.getClassName()):"";
        step.methodname = stp != null?(stp.getMethodName()):"";
        step.linenumber = stp != null?stp.getLineNumber():0;
        step.description = name;
        if(ex != null)
        {
            step.error = (ex.toString().equals("java.lang.NullPointerException")) ? "java.lang.NullPointerException" : ex.getMessage();
            step.success = false;
            test.success = false;
        }
        test.classname = tst != null?(tst.getClassName()):"";
        test.methodname = tst != null?(tst.getMethodName()):"";
        test.linenumber = tst != null?tst.getLineNumber():0;
        if(printconsole)
            System.out.print("\r\n" + DateTime.now().toLocalDateTime() + ": " + ((ex==null)?step.name:(ANSI_RED + step.name + ":" +step.error))  + "\r\n" + ANSI_BLACK);
        steps.add(step);
        initializeVariables();
    }

    public static void addAssertStep(String message,String expected,String actual) throws Exception
    {
        if(expected.equalsIgnoreCase(actual))
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null );
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"));
        }
    }

    public static void addAssertStep(String message,int expected,int actual) throws Exception
    {
        if(expected==actual)
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null );
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"));
        }
    }

    public static void addAssertStep(String message,float expected,float actual) throws Exception
    {
        if(expected==actual)
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null );
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"));
        }
    }

    public static void addAssertStep(String message,boolean expected,boolean actual) throws Exception
    {
        if(expected==actual)
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null );
        else
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,new Exception("Not Equal"));
    }

    public static void addAssertStep(String message,String expected,String actual,String screenshot) throws Exception
    {
        if(expected.equalsIgnoreCase(actual))
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null,screenshot );
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"),screenshot);
        }
    }

    public static void addAssertStep(String message,int expected,int actual,String screenshot) throws Exception
    {
        if(expected==actual)
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null,screenshot);
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"),screenshot);
        }
    }

    public static void addAssertStep(String message,float expected,float actual,String screenshot) throws Exception
    {
        if(expected==actual)
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null,screenshot );
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"),screenshot);
        }
    }

    public static void addAssertStep(String message,boolean expected,boolean actual,String screenshot) throws Exception
    {
        if(expected==actual)
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null,screenshot );
        else
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,new Exception("Not Equal"),screenshot);
    }


    public static void addAssertStepFailonMismatch(String message,String expected,String actual) throws Exception
    {
        if(expected.equalsIgnoreCase(actual))
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null );
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"));
            TestData.endTest();
            Assert.fail(message + " Expected:" + expected + " Actual:" + actual + " are note equal");
        }
    }

    public static void addAssertStepFailonMismatch(String message,int expected,int actual) throws Exception
    {
        if(expected==actual)
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null );
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"));
            TestData.endTest();
            Assert.fail(message + " Expected:" + expected + " Actual:" + actual + " are note equal");
        }
    }

    public static void addAssertStepFailonMismatch(String message,float expected,float actual) throws Exception
    {
        if(expected==actual)
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null );
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"));
            TestData.endTest();
            Assert.fail(message + " Expected:" + expected + " Actual:" + actual + " are note equal");
        }
    }

    public static void addAssertStepFailonMismatch(String message,boolean expected,boolean actual) throws Exception
    {
        if(expected==actual)
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null );
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"));
            TestData.endTest();
            Assert.fail(message + " Expected:" + expected + " Actual:" + actual + " are note equal");
        }
    }

    public static void addAssertStepFailonMismatch(String message,String expected,String actual,String screenshot) throws Exception
    {
        if(expected.equalsIgnoreCase(actual))
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null,screenshot );
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"),screenshot);
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"),screenshot);
            TestData.endTest();
            Assert.fail(message + " Expected:" + expected + " Actual:" + actual + " are note equal");
        }
    }

    public static void addAssertStepFailonMismatch(String message,int expected,int actual,String screenshot) throws Exception
    {
        if(expected==actual)
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null,screenshot );
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"),screenshot);
            TestData.endTest();
            Assert.fail(message + " Expected:" + expected + " Actual:" + actual + " are note equal");
        }
    }

    public static void addAssertStepFailonMismatch(String message,float expected,float actual,String screenshot) throws Exception
    {
        if(expected==actual)
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null,screenshot );
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"),screenshot);
            TestData.endTest();
            Assert.fail(message + " Expected:" + expected + " Actual:" + actual + " are note equal");
        }
    }

    public static void addAssertStepFailonMismatch(String message,boolean expected,boolean actual,String screenshot) throws Exception
    {
        if(expected==actual)
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null,screenshot );
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"),screenshot);
            TestData.endTest();
            Assert.fail(message + " Expected:" + expected + " Actual:" + actual + " are note equal");
        }
    }



    public static void addTestStep(String name,Exception ex,String screenshot) throws Exception
    {
        getStackTrace();
        Step step = new Step();
        step.classname = stp != null?(stp.getClassName()):"";
        step.methodname = stp != null?(stp.getMethodName()):"";
        step.linenumber = stp != null?stp.getLineNumber():0;
        step.starttime = starttime;
        starttime = Instant.now().toString();
        step.endtime = starttime;
        step.name = name;
        step.description = name;
        if(ex != null)
        {
            step.error = (ex.toString().equals("java.lang.NullPointerException")) ? "java.lang.NullPointerException" : ex.toString();
            step.success = false;
            test.success = false;
        }
        step.screenshot = screenshot;
        test.classname = tst != null?(tst.getClassName()):"";
        test.methodname = tst != null?(tst.getMethodName()):"";
        test.linenumber = tst != null?tst.getLineNumber():0;
        if(printconsole)
            System.out.print("\r\n" + DateTime.now().toLocalDateTime() + ": " +  ((ex==null)?step.name:(ANSI_RED + step.name + ":" +step.error))  + "\r\n" + ANSI_BLACK);
        steps.add(step);
        initializeVariables();
    }

    public static void addTestStep(String name,Exception ex,boolean isapi) throws Exception
    {
        getStackTrace();
        Step step = new Step();
        step.classname = stp != null?(stp.getClassName()):"";
        step.methodname = stp != null?(stp.getMethodName()):"";
        step.linenumber = stp != null?stp.getLineNumber():0;
        if(starttime == null)
        {
            starttime = Instant.now().toString();
        }
        step.starttime = starttime;
        starttime = Instant.now().toString();
        step.endtime = starttime;
        step.name = name;
        step.description = name;
        step.isapi = isapi;
        if(ex != null)
        {
            step.error = (ex.toString().equals("java.lang.NullPointerException")) ? "java.lang.NullPointerException" : ex.getMessage();
            step.success = false;
            test.success = false;
        }
        test.classname = tst != null?(tst.getClassName()):"";
        test.methodname = tst != null?(tst.getMethodName()):"";
        test.linenumber = tst != null?tst.getLineNumber():0;
        if(printconsole)
            System.out.print("\r\n" + DateTime.now().toLocalDateTime() + ": " + ((ex==null)?step.name:step.error)  + "\r\n");
        steps.add(step);
        initializeVariables();
    }

    public static void startTest(String name,int Priority, String ... labels) throws Exception
    {
        System.out.print("\r\n" + "------------------------------------Test Started-------------------------------------------------------" + "\r\n");
        System.out.print("\r\n"  + "Test Name: " + name + "\r\n" + "\r\n");
        test = new Test();
        starttime  = Instant.now().toString();
        steps = new ArrayList<Step>();
        getStackTrace();
        if(suite.tests.size() == 0)
        {
            suite.starttime = test.starttime;
        }
        test.name = name;
        if(labels.length > 0)
        {
            for(String label:labels)
                test.labels.put(label,label);
        }
        test.classname = tst != null?(tst.getClassName()):"";
        test.methodname = tst != null?(tst.getMethodName()):"";
        test.linenumber = tst != null?tst.getLineNumber():0;
    }

    public static JsonNode endTest() throws Exception
    {
        if(test == null)
            return currentTest;
        test.steps = steps;
        test.endtime = Instant.now().toString();
        suite.tests.add(test);
        suite.totaltests += 1;
        suite.totalsteps += test.steps.size();
        if(test.success)
        {
            suite.totalpass += 1;
        }
        else
        {
            suite.totalfail += 1;
        }
        suite.endtime = Instant.now().toString();
        JsonNode response = mapper.readTree(mapper.writeValueAsString(test));
        currentTest = response;
        test=new Test();

//        System.out.print("\r\n" + mapper.writeValueAsString(test) + "\r\n");
        String data =  mapper.writeValueAsString(suite);
        HTMLReport.reportdata = "var tests = " + data+ ";";
        File htmlFile = new File(reportPath +"report.html");
        File jsonFile = new File(reportPath +"reportdata.js");
        FileUtils.writeStringToFile(htmlFile, HTMLReport.getTemplate());
        FileUtils.writeStringToFile(jsonFile, HTMLReport.reportdata);
        if(uploadScreenshotToS3) {
//            S3.uploadFile("executions/" + suite.id + "/report.html", htmlFile);
//            S3.uploadFile("executions/" + suite.id + "/report.json",mapper.writeValueAsString(suite) );

            try {
                given().request().body(mapper.writeValueAsBytes(suite)).when().post(reportUploadUrl + suite.id);
            }
            catch (Exception ex)
            {

            }
        }
        System.out.print("\r\n" + "------------------------------------Test Finished-------------------------------------------------------" + "\r\n");

        return currentTest;
    }

    public static JsonNode endTest(boolean add_testcase_to_jira,boolean update_testcase_to_jira,String username,String password) throws Exception
    {

        if(test == null)
            return mapper.createObjectNode();
        test.steps = steps;
        test.endtime = Instant.now().toString();
        suite.tests.add(test);
        suite.totaltests += 1;
        suite.totalsteps += test.steps.size();
        if(test.success)
        {
            suite.totalpass += 1;
        }
        else
        {
            suite.totalfail += 1;
        }
        suite.endtime = Instant.now().toString();
        JsonNode response = mapper.readTree(mapper.writeValueAsString(test));

        try
        {
            if(add_testcase_to_jira) {

                String path = System.getProperty("user.dir")+"/report/" +System.getProperty("suiteid")+ "/screenshots/";

                String classname = "class_" + response.get("classname").asText();
                String methodname = "method_" + response.get("methodname").asText();
                JiraApi jiraApi = new JiraApi(username, password);
                JsonNode searchResults = jiraApi.searchJiraUsingJQL("labels = " + methodname);
                System.out.println("Search Results " + searchResults);
                if (searchResults.get("total").asInt() == 0) {
                    JsonNode jiraitem = jiraApi.createJiraItem(20381, 3075214, response.get("name").asText(), classname, methodname, "awd-ui");
                    System.out.println("JIRA TESTCASE " + jiraitem);
                    for (JsonNode n : response.get("steps")) {
                        JsonNode step = jiraApi.addTestStep(jiraitem.get("id").asInt(), n.get("name").asText(), "", n.get("name").asText());
                        System.out.println(step);
                        if (!n.get("screenshot").asText().equalsIgnoreCase("null")) {
                            JsonNode fileupload = jiraApi.attachedFileToTestStep(step.get("id").asInt(), new File(path  + n.get("screenshot").asText()));
                            System.out.println(fileupload);
                        }
                    }
                }
                else if(update_testcase_to_jira)
                {
                    JsonNode jiraitem = searchResults.get("issues").get(0);
                    jiraApi.deleteAllTestSteps(jiraitem.get("id").asInt());
                    for (JsonNode n : response.get("steps")) {
                        JsonNode step = jiraApi.addTestStep(jiraitem.get("id").asInt(), n.get("name").asText(), "", n.get("name").asText());
                        System.out.println(step);
                        if (!n.get("screenshot").asText().equalsIgnoreCase("null")) {
                            JsonNode fileupload = jiraApi.attachedFileToTestStep(step.get("id").asInt(), new File(path + n.get("screenshot").asText()));
                            System.out.println(fileupload);
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            System.out.println("\u001B[31m" + " warning: something went wrong while creating jira ticket. Please make sure your username and password are correct " + "\u001B[31m");
            System.out.println("\u001B[0m");
        }

        test=null;



//        System.out.print("\r\n" + mapper.writeValueAsString(test) + "\r\n");
        String data =  mapper.writeValueAsString(suite);
        HTMLReport.reportdata = "var tests = " + data+ ";";
        File htmlFile = new File(reportPath +"report.html");
        File jsonFile = new File(reportPath +"reportdata.js");
        FileUtils.writeStringToFile(htmlFile, HTMLReport.getTemplate());
        FileUtils.writeStringToFile(jsonFile, HTMLReport.reportdata);
        if(uploadScreenshotToS3) {
//            S3.uploadFile("executions/" + suite.id + "/report.html", htmlFile);
//            S3.uploadFile("executions/" + suite.id + "/report.json",mapper.writeValueAsString(suite) );

            try {
                given().request().body(mapper.writeValueAsBytes(suite)).when().post(reportUploadUrl + suite.id);
            }
            catch (Exception ex)
            {

            }
        }

        return currentTest;
    }

    static void getStackTrace() throws Exception
    {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for(StackTraceElement stackTraceElement:stackTraceElements)
        {
            if(stackTraceElement.getClassName().contains("qa.automation.api") || stackTraceElement.getClassName().contains("qa.automation.pages")|| stackTraceElement.getClassName().contains("qa.automation.db"))
            {
                stp = stackTraceElement;
            }
            else if(stackTraceElement.getClassName().contains("qa.automation.tests"))
            {
                tst = stackTraceElement;
            }
        }
        if(stp == null)
        {
            stp = tst;
        }
    }

    static void initializeVariables() throws Exception
    {
        stp = null;
        tst = null;
    }
}
