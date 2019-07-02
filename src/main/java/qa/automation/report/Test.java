package qa.automation.report;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by johnson_phillips on 12/12/17.
 */
public class Test {
    public String id;
    public String name;
    public boolean success;
    public List<Step> steps;
    public String starttime;
    public String endtime;
    public String methodname;
    public String classname;
    public int linenumber;
    public String error = "";
    public String description;
    public String phase;
    public int priority;
    public HashMap<String,String> labels;

    public Test()
    {
        steps = new ArrayList<Step>();
        starttime = Instant.now().toString();
        id = UUID.randomUUID().toString();
        labels = new HashMap<String, String>();
        success = true;
    }
}
