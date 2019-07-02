package qa.automation.report;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.io.File;
import java.util.Base64;

import static io.restassured.RestAssured.given;

public class JiraApi {

    public ObjectMapper mapper = new ObjectMapper();
    public static String jiraurl = "";
    public String username = "";
    public String password = "";
    String basicauth = "";
    Header header;

    public enum TestStatus
    {
        TODO,
        INPROGESS,
        AUTOMATED,

    }

    public JiraApi(String username,String password) throws Exception
    {
        this.username = username;
        this.password = password;
        basicauth = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        header = new Header("Authorization","Basic "+basicauth);

    }

    public JsonNode getJiraItem(String key) throws Exception
    {
        String url = jiraurl + "/rest/api/latest/issue/" + key;
        Response response = given().log().method().log().uri().log().parameters().log().body().header(header).get(url);
        return  mapper.readTree(response.getBody().asInputStream());
    }

    public JsonNode getProjects(String key) throws Exception
    {
        String url = jiraurl + "/rest/api/latest/issue/" + key;
        Response response = given().log().method().log().uri().log().parameters().log().body().header(header).get(url);
        return  mapper.readTree(response.getBody().asInputStream());
    }

    public JsonNode searchJiraUsingJQL(String jql) throws Exception
    {
        String url = jiraurl + "/rest/api/latest/search?jql=" + jql;
        Response response = given().log().method().log().uri().log().parameters().log().body().header(header).get(url);
        return  mapper.readTree(response.getBody().asInputStream());
    }

    public JsonNode createJiraItem(int project_id,int agileteam, String summary,String ... labels) throws Exception
    {
        ObjectNode node = mapper.createObjectNode();

        ObjectNode fields = mapper.createObjectNode();

        ObjectNode project = mapper.createObjectNode();
        project.put("id",String.valueOf(project_id));

        ArrayNode lbls = mapper.valueToTree(labels);

        ObjectNode issuetype = mapper.createObjectNode();
        issuetype.put("id",String.valueOf(10500));

        ObjectNode customfield_16465 = mapper.createObjectNode();
        customfield_16465.put("id",String.valueOf(agileteam));
        fields.put("project",project);
        fields.put("summary",summary);
        fields.put("issuetype",issuetype);
        fields.put("customfield_16465",customfield_16465);
        fields.put("labels",lbls);

        node.put("fields",fields);
        String url = jiraurl + "/rest/api/latest/issue";
        Response resp = given().log().method().log().uri().log().parameters().log().body().header(header).contentType(ContentType.JSON).body(node.toString()).post(url);
        return  mapper.readTree(resp.getBody().asInputStream());
    }

    public JsonNode addTestStep(int testid,String step,String data,String result) throws Exception
    {
        ObjectNode node = mapper.createObjectNode();
        node.put("step",step);
        node.put("data",data);
        node.put("result",result);
        String url = jiraurl + "/rest/zapi/latest/teststep/" + testid;
        Response resp = given().log().method().log().uri().log().parameters().log().body().header(header).contentType(ContentType.JSON).body(node.toString()).post(url);
        return  mapper.readTree(resp.getBody().asInputStream());
    }

    public JsonNode getTestStep(int testid,int stepid) throws Exception
    {

        String url = jiraurl + "/rest/zapi/latest/teststep/" + testid + "/" + stepid;
        Response resp = given().log().method().log().uri().log().parameters().log().body().header(header).contentType(ContentType.JSON).get(url);
        return  mapper.readTree(resp.getBody().asInputStream());
    }

    public JsonNode deleteTestStep(int testid,int stepid) throws Exception
    {

        String url = jiraurl + "/rest/zapi/latest/teststep/" + testid + "/" + stepid;
        Response resp = given().log().method().log().uri().log().parameters().log().body().header(header).contentType(ContentType.JSON).delete(url);
        return  mapper.readTree(resp.getBody().asInputStream());
    }

    public JsonNode getAllTestSteps(int testid) throws Exception
    {
        String url = jiraurl + "/rest/zapi/latest/teststep/" + testid;
        Response resp = given().log().method().log().uri().log().parameters().log().body().header(header).contentType(ContentType.JSON).get(url);
        return  mapper.readTree(resp.getBody().asInputStream());
    }

    public JsonNode deleteAllTestSteps(int testid) throws Exception {

        ObjectNode res = mapper.createObjectNode();
        try {
            JsonNode steps = getAllTestSteps(testid);
            for(JsonNode step:steps.get("stepBeanCollection"))
            {
                deleteTestStep(testid,step.get("id").asInt());
            }

            steps = getAllTestSteps(testid);
            if(steps.get("stepBeanCollection").size() != 0)
            {
                System.out.println("\u001B[31m" + " warning: couldn't delete all steps please try again " + "\u001B[31m");
                System.out.println("\u001B[0m");
            }
            res.put("success",true);
        }
        catch (Exception ex)
        {
            res.put("success",false);
            res.put("error",ex.getMessage());

        }

        return res;

    }


    public JsonNode attachedFileToTestStep(int stepid,File file) throws Exception
    {
        String url = jiraurl + "/rest/zapi/latest/attachment?entityType=TESTSTEP&entityId=" + stepid;
        Response resp = given().log().method().log().uri().log().parameters().log().body().header(header).multiPart("file",file).post(url);
        return  mapper.readTree(resp.getBody().asInputStream());

    }

    public boolean updateJiraTestStatus(TestStatus testStatus,String jiraid) {
        //TODO 91
        //AUTOMATED 101
        //INPGORESS 31

        try {
            ObjectNode node = mapper.createObjectNode();
            ObjectNode transition = mapper.createObjectNode();

            switch (testStatus) {
                case TODO:
                    transition.put("id", 91);
                    break;
                case INPROGESS:
                    transition.put("id", 31);
                    break;
                case AUTOMATED:
                    transition.put("id", 101);
                    break;

            }

            node.put("transition", transition);

            String url = jiraurl + "/rest/api/latest/issue/" + jiraid + "/transitions";
            Response resp = given().log().method().log().uri().log().parameters().log().body().header(header).contentType(ContentType.JSON).body(node.toString()).post(url);
            if (resp.getStatusCode() != 204) {
                System.out.println("error updating status");
                return false;
            }
        }
        catch (Exception ex)
        {
            return false;
        }

        return true;




    }
}
