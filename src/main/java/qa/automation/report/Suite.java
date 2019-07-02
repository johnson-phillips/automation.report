package qa.automation.report;

import java.util.*;

/**
 * Created by johnson_phillips on 1/30/18.
 */
public class Suite {

    public List<Test> tests = new ArrayList<Test>();
    public int totaltests;
    public int totalpass;
    public int totalfail;
    public int totalsteps;
    public String starttime;
    public String endtime;
    public String id;
    public static HashMap<String, String> runtimeVariables = new HashMap<String, String>();

    public Suite()
    {
        String tempid = System.getProperty("suiteid");
        System.out.print("\r\n" + "Temp ID is " + tempid);
        if(tempid == null)
            id = UUID.randomUUID().toString();
        else
            id = tempid;
        System.setProperty("suiteid",id);
    }

}