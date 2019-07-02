package qa.automation.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JiraTestCase {

    static ObjectMapper mapper = new ObjectMapper();


    public static JsonNode getTestCaseRequestData(int project_id,int agileteam, String summary) throws Exception
    {
        ObjectNode node = mapper.createObjectNode();

        ObjectNode fields = mapper.createObjectNode();

        ObjectNode project = mapper.createObjectNode();
        project.put("id",String.valueOf(project_id));

        ObjectNode issuetype = mapper.createObjectNode();
        issuetype.put("id",String.valueOf(0000));

        ObjectNode customfield_16465 = mapper.createObjectNode();
        customfield_16465.put("id",String.valueOf(agileteam));
        fields.put("project",project);
        fields.put("summary",summary);
        fields.put("issuetype",issuetype);
        fields.put("customfield_16465",customfield_16465);

        node.put("fields",fields);
        return  node;
    }

}
