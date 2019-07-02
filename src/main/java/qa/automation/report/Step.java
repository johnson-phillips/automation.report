package qa.automation.report;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by johnson_phillips on 12/12/17.
 */
public class Step {
    public String id;
    public String name;
    public String description;
    public boolean success;
    public List<Action> actions;
    public String starttime;
    public String endtime;
    public String methodname;
    public String classname;
    public int linenumber;
    public String error;
    public String message;
    public String screenshot;
    public String[] parameters;
    public boolean isapi;

    public Step()
    {
        actions = new ArrayList<Action>();
        starttime = Instant.now().toString();
        success = true;
        id = UUID.randomUUID().toString();
        isapi = false;
    }

}
