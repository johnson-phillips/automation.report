package qa.automation.report;

/**
 * Created by johnson_phillips on 1/30/18.
 */
public class HTMLReport {

    public static String reportdata;

    public static String getTemplate() throws Exception {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <title>Execution Results</title>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"https://www.w3schools.com/w3css/4/w3.css\">\n" +
                "    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n" +
                "    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>\n" +
                "    <script src=\"https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js\"></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<style>\n" +
                "    div{\n" +
                "        overflow-wrap: break-word;\n" +
                "    }\n" +
                "</style>\n" +
                "<div class=\"container-fluid\">\n" +
                "<h3 style=\"text-align: center;\">Execution Results</h3>\n" +
                "    <div class=\"row\" style=\"text-align: center;\">\n" +
                "        <div class=\"col-md-1\"></div>\n" +
                "        <div class=\"col-md-2\">\n" +
                "            <button class=\"thumbnail shadow w3-animate-zoom w3-blue btn-block\" onclick=\"hide('none')\">\n" +
                "                <h3 id=\"totaltests\"></h3>\n" +
                "                <h4>Total Tests</h4>\n" +
                "            </button>\n" +
                "        </div>\n" +
                "        <div class=\"col-md-2\">\n" +
                "            <button class=\"thumbnail shadow w3-animate-zoom w3-green btn-block\" onclick=\"hide('fail')\">\n" +
                "                <h3 id=\"totalpass\"></h3>\n" +
                "                <h4>Total Pass</h4>\n" +
                "            </button>\n" +
                "        </div>\n" +
                "        <div class=\"col-md-2\">\n" +
                "            <button class=\"thumbnail shadow w3-animate-zoom w3-red btn-block\" onclick=\"hide('pass')\">\n" +
                "                <h3 id=\"totalfail\"></h3>\n" +
                "                <h4>Total Fail</h4>\n" +
                "            </button>\n" +
                "        </div>\n" +
                "        <div class=\"col-md-2\">\n" +
                "            <div class=\"thumbnail shadow w3-animate-zoom w3-teal\">\n" +
                "                <h3 id=\"totalduration\"></h3>\n" +
                "                <h4>Total Duration</h4>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"col-md-2\">\n" +
                "            <button class=\"thumbnail shadow w3-animate-zoom w3-orange btn-block\" onclick=\"expandall()\">\n" +
                "                <h3 id=\"totalsteps\"></h3>\n" +
                "                <h4>Total Steps</h4>\n" +
                "            </button>\n" +
                "        </div>\n" +
                "        <div class=\"col-md-1\"></div>\n" +
                "    </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<div class=\"row w3-left-align\">\n" +
                "        <div class=\"col-sm-8\"><label>&nbsp Scenario</label></div>\n" +
                "        <div class=\"col-sm-1\"><label>Start Time</label></div>\n" +
                "        <div class=\"col-sm-1\"><label>End Time</label></div>\n" +
                "        <div class=\"col-sm-1\"><label>Duration</label></div>\n" +
                "        <div class=\"col-sm-1\"><label>Steps</label></div>\n" +
                "    </div>\n" +
                "    <div class=\"panel-group\" id=\"tests\" style=\"overflow: auto;\"></div>\n" +
                "\n" +
                "    <div class=\"modal fade\" id=\"modal\">\n" +
                "        <div class=\"modal-dialog modal-lg\" style=\"width:80%;height:90%;\">\n" +
                "            <div class=\"modal-content\">\n" +
                "                <div class=\"modal-header\">\n" +
                "                    <button type=\"button\" class=\"close\" data-dismiss=\"modal\">&times;</button>\n" +
                "                    <h4 class=\"modal-title\"></h4>\n" +
                "                </div>\n" +
                "                <div class=\"modal-body w3-center\">\n" +
                "                </div>\n" +
                "                <div class=\"modal-footer\">\n" +
                "                    <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Close</button>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "<script>\n" +
                "    $(document).ready(function() {\n" +
                "        //$(window).bind('resize', $(\"#tests\").css('height', '' + Math.round(.80 * $(window).height())));\n" +
                "    });" +
                "\n" + reportdata + "\n" +
                "console.log(tests);\n" +
                "\n" +
                "    $('#totaltests').html(tests.totaltests);\n" +
                "    var pass = tests.totalpass;\n" +
                "    var fail = tests.totalfail;\n" +
                "    var steps = tests.totalsteps;\n" +
                "    var actions = 0;\n" +
                "    var i=0;\n" +
                "    var duration = new Date(new Date(tests.endtime) - new Date(tests.starttime)).toISOString().slice(11, -1).substring(0,8);\n" +
                "\n" +
                "    $.each(tests.tests,function(index,element){\n" +
                "        var startdate = new Date(element.starttime);\n" +
                "        var enddate = new Date(element.endtime);\n" +
                "        var liclass = \"\";\n" +
                "        switch(element.success)\n" +
                "        {\n" +
                "            case true:\n" +
                "                liclass = ' class =\"panel panel-default pass btn-block\" style=\"text-decoration: none; border: 2px solid green;\"';\n" +
                "                break;\n" +
                "            case false:\n" +
                "\n" +
                "                liclass = ' class =\"panel panel-default fail btn-block\" style=\"text-decoration: none; border: 2px solid red;\"';\n" +
                "                break;\n" +
                "\n" +
                "        }\n" +
                "temp = '<div'+liclass+'>';\n" +
                "        temp += '<button class=\"panel-heading w3-left-align btn-block\" onclick=\"showDetails(this)\" data-toggle=\"collapse\" href=\"#'+index+'\">';\n" +
                "            temp += '<h4 class=\"panel-title\">';\n" +
                "            temp += ' <div class=\"row\">';\n" +
                "            temp += '<div class=\"col-sm-8\">';\n" +
                "            temp += 'Test Name: ' + element.name + '' + '<br/>' + 'Test Class: ' +element.classname + '<br/>' + 'Method: ' +element.methodname;\n" +
                "            temp += '</div>';\n" +
                "            temp += '<div class=\"col-sm-1\">';\n" +
                "            temp += '<label>'+new Date(element.starttime).toLocaleTimeString()+'</label>';\n" +
                "            temp += '</div>';\n" +
                "            temp += '<div class=\"col-sm-1\">';\n" +
                "            temp += '<label>'+new Date(element.endtime).toLocaleTimeString()+'</label>';\n" +
                "            temp += '</div>';\n" +
                "            temp += '<div class=\"col-sm-1\">';\n" +
                "            temp += '<label>'+new Date(new Date(element.endtime) - new Date(element.starttime)).toISOString().slice(11, -1).substring(0,12)+'</label>';\n" +
                "            temp += '</div>';\n" +
                "            temp += '<div class=\"col-sm-1\">';\n" +
                "            temp += '<label>'+(element.hasOwnProperty('steps')?element.steps.length:0)+'</label>';\n" +
                "            temp += '</div>';\n" +
                "            temp += ' </div>';\n" +
                "            temp += '</h4>';\n" +
                "        temp += '</button>';\n" +
                "        temp += '<div id=\"'+index+'\" class=\"panel-collapse collapse\">';\n" +
                "        temp += ' <div class=\"panel-body\">';\n" +
                "        temp += '</div>'+\n" +
                "                ' <div class=\"panel-footer\"></div>' +\n" +
                "                ' </div>';\n" +
                "        $(temp).appendTo('#tests');\n" +
                "\n" +
                "    });\n" +
                "\n" +
                "    $('#totalduration').html(duration);\n" +
                "    $('#totalpass').html(pass);\n" +
                "    $('#totalfail').html(fail);\n" +
                "    $('#totalsteps').html(steps);\n" +
                "\n" +
                "    function showDetails(div)\n" +
                "    {\n" +
                "        var testid = (div).getAttribute('href').replace('#','');\n" +
                "\n" +
                "       if(div.getAttribute('aria-expanded') === null || div.getAttribute('aria-expanded') === 'false')\n" +
                "       {\n" +
                "           if(div.parentNode.getAttribute('class').indexOf('pass') > 0)\n" +
                "           {\n" +
                "               div.setAttribute('style','background-color:#4CAF50!important;');\n" +
                "           }\n" +
                "           else\n" +
                "           {\n" +
                "               div.setAttribute('style','background-color:#f44336!important;');\n" +
                "           }\n" +
                "       }\n" +
                "        else\n" +
                "       {\n" +
                "           (div).removeAttribute('style');\n" +
                "       }\n" +
                "\n" +
                "        //div.setAttribute('style','background-color:#f44336!important;')\n" +
                "        var id = '#' + testid + ' .panel-body';\n" +
                "        $(id).html('');\n" +
                "        var test = tests.tests[testid];\n" +
                "        $('<h3>Steps</h3><input id=\"showcode\" type=\"checkbox\" class=\"btn-primary\" onclick=\"showCode(this)\">Show Code Lines</input>').appendTo(id);\n" +
                "        var j = 1;\n" +
                "        $.each(test.steps,function(index,step) {\n" +
                "            var color = '';\n" +
                "\n" +
                "            switch (step.success) {\n" +
                "                case true:\n" +
                "                    color = 'style=\"border: 1px solid green;\"';\n" +
                "                    break;\n" +
                "                case false:\n" +
                "                    color = 'style=\"border: 1px solid red;\"';\n" +
                "                    break;\n" +
                "            }\n" +
                "\n" +
                "        var temptable = '<div class=\"row w3-left-align\" '+color+' >';\n" +
                "        var details = '';\n" +
                "            if(step.classname && step.methodname)\n" +
                "                details = ' Class: '+step.classname+ ' Method: ' + step.methodname + ' Line: '+step.linenumber + ' ' ;\n" +
                "        if(step.isapi)\n" +
                "        {\n" +
                "            temptable += '<div class=\"col-md-6\"><textarea rows=3 style=\"width:100%;\">' + step.name + '</textarea></div>';\n" +
                "\n" +
                "        }\n" +
                "        else\n" +
                "        {\n" +
                "            temptable += '<div class=\"col-md-6\">Step:'+j+'<text class=\"debug\" hidden=\"hidden\">' +details +'</text>'+  '<br/>'  + step.name + '</div>';\n" +
                "        }\n" +
                "        j+=1;\n" +
                "        if (!step.success)\n" +
                "        {\n" +
                "            temptable += '<div class=\"col-md-2 w3-center\">'+(step.error === null?\"\":\"Error:<textarea rows=1 style='width:100%;'>\"+ step.error +\"</textarea>\" )+'</div>';\n" +
                "        }\n" +
                "        else\n" +
                "        {\n" +
                "            temptable += '<div class=\"col-md-2\"></div>';\n" +
                "        }\n" +
                "        temptable += '<div class=\"col-md-1\">' + new Date(step.starttime).toLocaleTimeString()+  '</div>';\n" +
                "        temptable += '<div class=\"col-md-1\">' + new Date(step.endtime).toLocaleTimeString()+  '</div>';\n" +
                "        temptable += '<div class=\"col-md-1\">' + new Date(new Date(step.endtime) - new Date(step.starttime)).toISOString().slice(11, -1).substring(0,12)+  '</div>';\n" +
                "        temptable += '<div class=\"col-md-1\">'+(step.screenshot == null?'':'<a href=\"#\" data-toggle=\"modal\" data-target=\"#modal\"><img onclick=\"expand(this.src)\" style=\"width:100%;height:50px;\" src=\"screenshots/'+step.screenshot+'\"/></a>' )+'</div>';\n" +
                "\n" +
                "        temptable+='</div>';\n" +
                "        temptable += '<legend class=\"'+color+'\"></legend>';\n" +
                "        $(temptable).appendTo(id);\n" +
                "    });\n" +
                "    }\n" +
                "\n" +
                "    function expand(src)\n" +
                "    {\n" +
                "        $('.modal-body').html('<img style=\"width:80%;height:80%;\" src=\"'+src+'\" />');\n" +
                "        $('#modal').show();\n" +
                "    }\n" +
                "\n" +
                "    function hide(css)\n" +
                "    {\n" +
                "        console.log(css);\n" +
                "        if(css === 'pass')\n" +
                "        {\n" +
                "            $('.pass').css('display','none');\n" +
                "            $('.fail').css('display','block');\n" +
                "        }\n" +
                "        else if(css === 'fail')\n" +
                "        {\n" +
                "            $('.pass').css('display','block');\n" +
                "            $('.fail').css('display','none');\n" +
                "        }\n" +
                "        else\n" +
                "        {\n" +
                "            $('.pass').css('display','block');\n" +
                "            $('.fail').css('display','block');\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    function expandall()\n" +
                "    {\n" +
                "        $(\".panel-heading\").each(function(index,element){\n" +
                "            console.log(element);\n" +
                "\n" +
                "            element.click();\n" +
                "        });\n" +
                "    }\n" +
                "function showCode(element) {\n" +
                "\n" +
                "        if(element.checked)\n" +
                "        {\n" +
                "            $('.debug').show();\n" +
                "        }\n" +
                "        else\n" +
                "        {\n" +
                "            $('.debug').hide();\n" +
                "        }\n" +
                "\n" +
                "    }" +
                "</script>\n" +
                "</body></html>";
    }
}
