<!doctype html>
<!--
  Material Design Lite
  Copyright 2015 Google Inc. All rights reserved.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      https://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License
-->

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="Introducing Lollipop, a sweet new take on Android.">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Android</title>

    <!-- Page styles -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
       <link rel="stylesheet" href="../mdl/templates/android-dot-com/material.min.css">
       <link rel="stylesheet" href="../mdl/templates/android-dot-com/styles.css">

       <style>
           #view-source {
               position: fixed;
               display: block;
               right: 0;
               bottom: 0;
               margin-right: 40px;
               margin-bottom: 40px;
               z-index: 900;
           }
       </style>


       <!-- CSS Markus -->
    <link href="../css/styleTabs.css" rel="stylesheet" type="text/css">
    <link href="../css/styleBootstrap.css" rel="stylesheet" type="text/css">
  <!--  <link rel="stylesheet" href="../css/font-awesome-4.7.0/css/font-awesome.min.css"> -->

    <!-- js markus -->
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

</head>
<body>
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">

    <div class="android-header mdl-layout__header mdl-layout__header--waterfall">
        <div class="mdl-layout__header-row">

            <!-- Add spacer, to align navigation to the right in desktop -->
            <div class="android-header-spacer mdl-layout-spacer"></div>
            <div class="android-search-box mdl-textfield mdl-js-textfield mdl-textfield--expandable mdl-textfield--floating-label mdl-textfield--align-right mdl-textfield--full-width">
                <label class="mdl-button mdl-js-button mdl-button--icon" for="search-field">
                    <i class="material-icons">search</i>
                </label>
                <div class="mdl-textfield__expandable-holder">
                    <input class="mdl-textfield__input" type="text" id="search-field">
                </div>
            </div>
            <!-- Navigation -->
            <div class="android-navigation-container">
                <nav class="android-navigation mdl-navigation">
                    <div class="row">
                        <button type="button" id="startButton" class="btn"> Start </button>
                        <button type="button" id="stopButton" class="btn"> Stop </button>
                    </div>
                    <h1 class="mdl-navigation__link mdl-typography--text-uppercase" href="">Welcome to the Meetingroom <?php $meetingRaumID = $_GET['id']; echo $meetingRaumID; ?></h1>
                    <a class="mdl-navigation__link mdl-typography--text-uppercase" href=""><i class="fa fa-sign-out" aria-hidden="true"></i></a>
                    <a class="mdl-navigation__link mdl-typography--text-uppercase" href=""><i class="fa fa-user" aria-hidden="true"></i></a>
                    <a class="mdl-navigation__link mdl-typography--text-uppercase" href=""><i class="fa fa-question" aria-hidden="true"></i></a>
                </nav>
            </div>

          </span>
            <button class="android-more-button mdl-button mdl-js-button mdl-button--icon mdl-js-ripple-effect" id="more-button">
                <i class="material-icons">more_vert</i>
            </button>
            <ul class="mdl-menu mdl-js-menu mdl-menu--bottom-right mdl-js-ripple-effect" for="more-button" id="LoggedInUsers">

            </ul>
        </div>
    </div>


    <div class="android-content mdl-layout__content">
        <a name="top"></a>
        <div class="android-be-together-section mdl-typography--text-center">

        <div class="mdc-auto-init">
            <div class="tab" id="listOfTabs">
                <button class="tablinks" onclick="openCity(event, 'KoBoRa')" id="defaultOpen">KoBoRa</button>
                <button class="tablinks vorschlag" onclick="openCity(event, 'London')">London</button>
                <button class="tablinks vorschlag" onclick="openCity(event, 'Paris')">Paris</button>
                <button class="tablinks vorschlag" onclick="openCity(event, 'Tokyo')">Tokyo</button>
            </div>

            <div id="KoBoRa" class="tabcontent">
                <h3>1. Vorschlag</h3>

                <h1>Hier Visualisierung aller Vorschläge </h1>
                <p class="vorschlag" id="vorschlag123">Ich bin ein Vorschlag</p>
                <ul id="vorschlagsListe"></ul>
                <div id="chart_div" style="width: 900px; height: 500px;"></div>



                <script type="text/javascript">

                    function parameterFunction(a , b , c , d ){
                        google.charts.load('current', {'packages':['corechart']});
                        google.charts.setOnLoadCallback(drawChart);


                        function drawChart() {
                            var data = google.visualization.arrayToDataTable([
                                ['Year', 'Sales', 'Expenses'],
                                [a,  1000,      400],
                                [b,  1170,      460],
                                [c,  660,       1120],
                                [d,  1030,      540]
                            ]);

                            var options = {
                                title: 'Company Performance',
                                curveType: 'function',
                                legend: { position: 'bottom' }
                            };

                            var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));

                            chart.draw(data, options);
                        }

                    }

                </script>




                <button id="drawButton" >
                    Zeichne
                </button>





                <ul id="log"></ul>

            </div>

            <div id="London" class="tabcontent">
                <h3>1. Vorschlag</h3>

            </div>

            <div id="Paris" class="tabcontent">
                <h3>Paris</h3>

            </div>

            <div id="Tokyo" class="tabcontent">
                <h3>hier gibt's nix</h3>


            </div>
        </div>




            <footer class="android-footer mdl-mega-footer">


        </footer>
    </div>
</div>
<script src="../mdl/material.min.js"></script>

    <!-- Markus JS für Inhalt -->

    <script src="../js/xhrobjekt.js"></script>
    <script src="../js/ajaxjson.js"></script>
    <script src="../js/tabs.js"></script>

    <script src="../js/client.js"></script>





    <!--fonts:  https://material.io/icons/ -->

</body>
</html>
