package automation.report;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileFilter;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.*;
import java.util.logging.Logger;

public class TestData {
    public static ObjectMapper mapper = new ObjectMapper();
    static Test test = new Test();
    public static String startTime;
    public static Suite suite = new Suite();
    public static String reportPath =  System.getProperty("user.dir") + "/report/" + suite.id + "/";
    private static List<Step> steps = new ArrayList<Step>();
    private static  StackTraceElement stp = null;
    private static StackTraceElement tst = null;
    private static JsonNode currentTest;
    public static Logger logger;
    public static String mainPackage = "";
    public static String testPackage = "";

    static
    {
        try {
            if(System.getProperty("java.util.logging.config.file") == null){
                String file = TestData.class.getResource("/logging.properties").getFile();
                System.setProperty("java.util.logging.config.file", file);
            }
            logger = Logger.getLogger("automation.report.TestData");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void emptyReportDirectory() {
        try{
            File file = new File(System.getProperty("user.dir") + "/report");
            File[] files = file.listFiles(new FileFilter() {
                public boolean accept(File f) {
                    return f.isDirectory();
                }
            });
            for(File f:files){
                FileUtils.deleteDirectory(f);
            }
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
        }


    }

    public static void addTestStep(String name,Exception ex,Object obj)
    {
        getStackTrace();
        Step step = new Step();
        if(startTime == null)
        {
            startTime = Instant.now().toString();
        }
        step.starttime = startTime;
        startTime = Instant.now().toString();
        step.endtime = startTime;
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
        if(ex == null){
            logger.info(step.name);
        } else {
            logger.severe( step.name + ":" +step.error);
        }
        if(obj != null){
            switch (obj.getClass().getName()){
                case "String": step.screenshot =(String) obj;
                    break;
                case "boolean": step.isapi =(boolean) obj;
                    break;
                default:
            }
        } else {
            step.isapi = false;
            step.screenshot = null;
        }

        steps.add(step);
        initializeVariables();
    }

    public static void addTestStep(String name,Exception ex)
    {
        addTestStep(name,ex,null);
    }

    public static void addAssertStep(String message,String expected,String actual)
    {
        if(expected.equals(actual))
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null,null );
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"),null);
        }
    }

    public static void addAssertStep(String message,int expected,int actual)
    {
        if(expected==actual)
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null,null );
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"),null);
        }
    }

    public static void addAssertStep(String message,float expected,float actual)
    {
        if(expected==actual)
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null,null );
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"),null);
        }
    }

    public static void addAssertStep(String message,boolean expected,boolean actual)
    {
        if(expected==actual)
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null,null );
        else
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,new Exception("Not Equal"),null);
    }

    public static void addAssertStep(String message,String expected,String actual,String screenshot)
    {
        if(expected.equals(actual))
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null,screenshot );
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"),screenshot);
        }
    }

    public static void addAssertStep(String message,int expected,int actual,String screenshot)
    {
        if(expected==actual)
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null,screenshot);
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"),screenshot);
        }
    }

    public static void addAssertStep(String message,float expected,float actual,String screenshot)
    {
        if(expected==actual)
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null,screenshot );
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"),screenshot);
        }
    }

    public static void addAssertStep(String message,boolean expected,boolean actual,String screenshot)
    {
        if(expected==actual)
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null,screenshot );
        else
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,new Exception("Not Equal"),screenshot);
    }


    public static void addAssertStepFailonMismatch(String message,String expected,String actual)
    {
        if(expected.equals(actual))
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null ,null);
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"),null);
            throw new AssertionError(message + " Expected:" + expected + " Actual:" + actual + " are note equal");
        }
    }

    public static void addAssertStepFailonMismatch(String message,int expected,int actual)
    {
        if(expected==actual)
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null,null );
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"),null);
            throw new AssertionError(message + " Expected:" + expected + " Actual:" + actual + " are note equal");
        }
    }

    public static void addAssertStepFailonMismatch(String message,float expected,float actual)
    {
        if(expected==actual)
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null,null );
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"),null);
            throw new AssertionError(message + " Expected:" + expected + " Actual:" + actual + " are note equal");
        }
    }

    public static void addAssertStepFailonMismatch(String message,boolean expected,boolean actual)
    {
        if(expected==actual)
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null ,null);
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"),null);
            throw new AssertionError(message + " Expected:" + expected + " Actual:" + actual + " are note equal");
        }
    }

    public static void addAssertStepFailonMismatch(String message,String expected,String actual,String screenshot)
    {
        if(expected.equals(actual))
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null,screenshot );
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"),screenshot);
            throw new AssertionError(message + " Expected:" + expected + " Actual:" + actual + " are note equal");
        }
    }

    public static void addAssertStepFailonMismatch(String message,int expected,int actual,String screenshot)
    {
        if(expected==actual)
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null,screenshot );
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"),screenshot);
            throw new AssertionError(message + " Expected:" + expected + " Actual:" + actual + " are note equal");
        }
    }

    public static void addAssertStepFailonMismatch(String message,float expected,float actual,String screenshot)
    {
        if(expected==actual)
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null,screenshot);
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"),screenshot);
            throw new AssertionError(message + " Expected:" + expected + " Actual:" + actual + " are note equal");
        }
    }

    public static void addAssertStepFailonMismatch(String message,boolean expected,boolean actual,String screenshot)
    {
        if(expected==actual)
            TestData.addTestStep(message + " Expected:"+expected + " Actual:" + actual,null,screenshot );
        else {
            TestData.addTestStep(message + " Expected:" + expected + " Actual:" + actual, new Exception("Not Equal"),screenshot);
            throw new AssertionError(message + " Expected:" + expected + " Actual:" + actual + " are note equal");
        }
    }

    public static void startTest(String name,String description, String ... labels)
    {
        logger.info("------------------------------------Test Started-------------------------------------------------------");
        logger.info("Test Name: " + name);
        test = new Test();
        startTime  = Instant.now().toString();
        steps = new ArrayList<>();
        getStackTrace();
        if(suite.tests.size() == 0)
        {
            suite.starttime = test.starttime;
        }
        test.name = name;
        test.description = description;
        if(labels.length > 0)
        {
            for(String label:labels)
                test.labels.put(label,label);
        }
        test.classname = tst != null?(tst.getClassName()):"";
        test.methodname = tst != null?(tst.getMethodName()):"";
        test.linenumber = tst != null?tst.getLineNumber():0;
    }

    public static JsonNode endTest()
    {
        try {
            if (test == null)
                return currentTest;
            test.steps = steps;
            test.endtime = Instant.now().toString();
            suite.tests.add(test);
            suite.totaltests += 1;
            suite.totalsteps += test.steps.size();
            if (test.success) {
                suite.totalpass += 1;
            } else {
                suite.totalfail += 1;
            }
            suite.endtime = Instant.now().toString();
            currentTest = mapper.convertValue(test, JsonNode.class);
            test = new Test();
            String data = mapper.writeValueAsString(suite);
            HTMLReport.reportdata = "var tests = " + data + ";";
            File htmlFile = new File(reportPath + "report.html");
            File jsonFile = new File(reportPath + "reportdata.json");
            FileUtils.write(htmlFile, HTMLReport.getTemplate(), Charset.forName("UTF-8"), false);
            FileUtils.write(jsonFile, HTMLReport.reportdata, Charset.forName("UTF-8"), false);
            logger.info("------------------------------------Test Finished-------------------------------------------------------");
            return currentTest;
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
            return null;
        }
    }

    public static JsonNode getSuite() {
        return mapper.convertValue(suite,JsonNode.class);
    }

    private static void getStackTrace()
    {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for(StackTraceElement stackTraceElement:stackTraceElements)
        {
            if(stackTraceElement.getClassName().contains(mainPackage))
            {
                stp = stackTraceElement;
            }
            else if(stackTraceElement.getClassName().contains(testPackage))
            {
                tst = stackTraceElement;
            }
        }
        if(stp == null)
        {
            stp = tst;
        }
    }

    private static void initializeVariables()
    {
        stp = null;
        tst = null;
    }
}
