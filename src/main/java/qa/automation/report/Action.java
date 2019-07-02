package qa.automation.report;

import java.time.Instant;
import java.util.*;

/**
 * Created by johnson_phillips on 12/12/17.
 */
@Deprecated
public class Action {
    public String id;
    public String name;
    public String description;
    public boolean success;
    public String error;
    public String starttime;
    public String endtime;
    public String screenshot;
    public String methodname;
    public String classname;
    public int linenumber;
    public String response;
    public boolean isapi = false;
    public int responsecode;
    public List<String> parameters;

    public Action()
    {
        starttime = Instant.now().toString();
        parameters = new ArrayList<String>();
        success = true;
        id = UUID.randomUUID().toString();
    }
}
