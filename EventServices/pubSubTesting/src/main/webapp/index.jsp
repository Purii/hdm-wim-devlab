<%--
  Created by IntelliJ IDEA.
  User: ben
  Date: 4/06/2017
  Time: 10:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="de.hdm.wim.PubSubHome" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="refresh" content="10">
</head>
<title>An example of using PubSub on App Engine Flex</title>
<body>

<h3> Publish a message </h3>
<form action="pubsub/publish" method="POST">
    <label for="payload">Payload:</label>
    <input id="payload" type="input" name="payload" />

    <label for="topic">Topic:</label>
    <input id="topic" type="input" name="topic" />

    <input id="submit"  type="submit" value="Send" />
</form>

<h3> Last received messages </h3>
<table border="1" cellpadding="10">
    <tr>
        <th>Id</th>
        <th>Data</th>
        <th>Topic</th>
        <th>PublishTime</th>
    </tr>
    <%= PubSubHome.getReceivedMessages() %>
</table>
</body>
</html>


