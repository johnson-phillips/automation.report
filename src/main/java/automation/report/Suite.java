package automation.report;

import java.time.Instant;
import java.util.*;

public class Suite {

    public List<Test> tests = new ArrayList<Test>();
    public int totaltests;
    public int totalpass;
    public int totalfail;
    public int totalsteps;
    public String starttime;
    public String endtime;
    public String id;

    public Suite()
    {
        id = Instant.now().toString();
    }

}
