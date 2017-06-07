<%--
  Created by IntelliJ IDEA.
  User: ben
  Date: 4/06/2017
  Time: 10:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="de.hdm.wim.PubSubHome" contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">

<head>
    <meta http-equiv="refresh" content="10">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title>Material Design Lite</title>

    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">
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
                <h4>Publish something</h4>
                <form action="pubsub/publish" method="POST">
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <input class="mdl-textfield__input" id="payload" type="input" name="payload">
                        <label class="mdl-textfield__label" for="payload">Payload</label>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <input class="mdl-textfield__input" id="topic" type="input" name="topic">
                        <label class="mdl-textfield__label" for="topic">Topic</label>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <input class="mdl-textfield__input" id="attributes" type="input" name="attributes">
                        <label class="mdl-textfield__label" for="attributes">Attributes</label>
                    </div>

                    <div class="mdl-cell mdl-cell--10-col">
                        <button id="submit" type="button" class="mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--raised mdl-button--colored">
                            send
                        </button>
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
                    <%= PubSubHome.getReceivedMessages() %>
                    </tbody>
                </table>
            </div>
            <div class="mdl-layout-spacer"></div>
        </div>

    </main>
</div>

<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
</body>

</html>