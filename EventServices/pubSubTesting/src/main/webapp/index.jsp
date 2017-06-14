<%--
  Created by IntelliJ IDEA.
  User: ben
  Date: 4/06/2017
  Time: 10:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="de.hdm.wim.pubSubTesting.PubSubHome" contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="de.hdm.wim.sharedLib.Constants.RequestParameters" %>
<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title>Event Gruppe</title>

    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://cdn.rawgit.com/kybarg/mdl-selectfield/mdl-menu-implementation/mdl-selectfield.min.css">
    <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">
    <link rel="stylesheet" href="style.css">

</head>

<body>
<div class="mdl-layout mdl-layout--fixed-header mdl-js-layout mdl-color--grey-100">
    <header class="mdl-layout__header mdl-layout__header--scroll mdl-color--grey-100 mdl-color-text--grey-800">
        <div class="mdl-layout__header-row">
            <span class="mdl-layout-title">PubSub</span>
        </div>
    </header>
    <main class="mdl-layout__content">

        <div class="centeritems mdl-grid">
            <div class="mdl-layout-spacer"></div>
            <div class="mdl-cell--6-col">
                <h4>Publish a message</h4>

                <%--
                                <button id="show-toast" class="mdl-button mdl-js-button mdl-button--raised" type="button">Show Toast</button>
                --%>

                <form name="sendMessage" action="/pubsub/publish" method="post" id="send-pubsub-message-form">

                    <div class="form-wrapper">
                        <div class="mdl-grid">
                            <div class="mdl-selectfield mdl-js-selectfield mdl-selectfield--floating-label mdl-cell mdl-cell--12-col">
                                <select class="mdl-selectfield__select" id="topic" name="<%= RequestParameters.TOPIC%>" required>
                                    <%= PubSubHome.getListOfTopics() %>
                                </select>
                                <label class="mdl-selectfield__label" for="topic">Topic</label>
                            </div>
                        </div>

                        <div class="mdl-grid">
                            <div class="mdl-textfield my-textfield-wrapper mdl-cell mdl-cell--12-col">
                                <input class="my-input-field" id="payload" type="input" name="<%= RequestParameters.PAYLOAD%>" required placeholder="Payload">
                            </div>
                        </div>
                        <div class="wrapper_all_inputs">
                            <div class="mdl-grid remove_me">
                                <div class="mdl-textfield my-textfield-wrapper mdl-cell mdl-cell--6-col">
                                    <input class="my-input-field" id="attribute_key" type="input" name="<%= RequestParameters.ATTRIBUTE_KEY%>" placeholder="Key" required>
                                </div>
                                <div class="mdl-textfield my-textfield-wrapper mdl-cell mdl-cell--5-col">
                                    <input class="my-input-field" id="attribute_value" type="input" name="<%= RequestParameters.ATTRIBUTE_VALUE%>" placeholder="Value">
                                </div>
                                <div class="mdl-textfield my-textfield-wrapper mdl-cell mdl-cell--1-col">
                                    <input class="btn_remove_this" type="button" name="delete" value="X">
                                </div>
                            </div>

                            <div class="wrapper_input_spawner">
                                <!-- Dynamic Fields go here -->
                            </div>
                        </div>

                        <div class="mdl-grid">
                            <div class="mdl-cell mdl-cell--12-col">
                                <button class="btn_add_kv mdl-cell--12-col mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--raised mdl-button--colored">Add item</button>
                            </div>
                        </div>

                    </div>
                    <div class="mdl-grid">
                        <button type="submit" name="button" value="send" id="submit" class="mdl-cell--2-col mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--raised mdl-button--colored">Send</button>
                    </div>
                </form>
            </div>
            <div class="mdl-layout-spacer"></div>
        </div>

        <div class="centeritems mdl-grid">
            <div class="mdl-layout-spacer"></div>
            <div class="mdl-cell--6-col">
                <h4>Last received messages</h4>
                <table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp">
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th class="mdl-data-table__cell--non-numeric">Topic</th>
                        <th class="mdl-data-table__cell--non-numeric">Data</th>
                        <th class="mdl-data-table__cell--non-numeric">Attributes</th>
                        <th>PublishTime</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%= PubSubHome.getReceivedEvents() %>
                    </tbody>
                </table>
            </div>
            <div class="mdl-layout-spacer"></div>
        </div>

    </main>
</div>

<div id="toast-container" class="mdl-js-snackbar mdl-snackbar">
    <div class="mdl-snackbar__text"></div>
    <button class="mdl-snackbar__action" type="button"></button>
</div>

<script
    src="https://code.jquery.com/jquery-3.2.1.js"
    integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="
    crossorigin="anonymous"></script>

<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
<script defer src="https://cdn.rawgit.com/kybarg/mdl-selectfield/mdl-menu-implementation/mdl-selectfield.min.js"></script>

<script src="script.js"></script>

</body>

</html>