<?php

if(!isset($_GET['id'])){

    header('Location: login_with_google_using_php.php');
}

$meetingRaumID = $_GET['id'];

echo '<h1> Wilkommen im Meetingraum ' .$meetingRaumID .'</h1>';

?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        /* Style the tab */
        div.tab {
            overflow: hidden;
            border: 1px solid #ccc;
            background-color: #f1f1f1;
        }

        /* Style the buttons inside the tab */
        div.tab button {
            background-color: inherit;
            float: left;
            border: none;
            outline: none;
            cursor: pointer;
            padding: 14px 16px;
            transition: 0.3s;
        }

        /* Change background color of buttons on hover */
        div.tab button:hover {
            background-color: #ddd;
        }

        /* Create an active/current tablink class */
        div.tab button.active {
            background-color: #ccc;
        }

        /* Style the tab content */
        .tabcontent {
            display: none;
            padding: 6px 12px;
            border: 1px solid #ccc;
            border-top: none;
        }

        .tabcontent {
            -webkit-animation: fadeEffect 1s;
            animation: fadeEffect 1s; /* Fading effect takes 1 second */
        }

        @-webkit-keyframes fadeEffect {
            from {opacity: 0;}
            to {opacity: 1;}
        }

        @keyframes fadeEffect {
            from {opacity: 0;}
            to {opacity: 1;}
        }



    </style>
    <script>
        function openCity(evt, cityName) {
            // Declare all variables
            var i, tabcontent, tablinks;

            // Get all elements with class="tabcontent" and hide them
            tabcontent = document.getElementsByClassName("tabcontent");
            for (i = 0; i < tabcontent.length; i++) {
                tabcontent[i].style.display = "none";
            }

            // Get all elements with class="tablinks" and remove the class "active"
            tablinks = document.getElementsByClassName("tablinks");
            for (i = 0; i < tablinks.length; i++) {
                tablinks[i].className = tablinks[i].className.replace(" active", "");
            }

            // Show the current tab, and add an "active" class to the button that opened the tab
            document.getElementById(cityName).style.display = "block";
            evt.currentTarget.className += " active";
        }

        function openCity(evt, cityName) {
            // Declare all variables
            var i, tabcontent, tablinks;

            // Get all elements with class="tabcontent" and hide them
            tabcontent = document.getElementsByClassName("tabcontent");
            for (i = 0; i < tabcontent.length; i++) {
                tabcontent[i].style.display = "none";
            }

            // Get all elements with class="tablinks" and remove the class "active"
            tablinks = document.getElementsByClassName("tablinks");
            for (i = 0; i < tablinks.length; i++) {
                tablinks[i].className = tablinks[i].className.replace(" active", "");
            }

            // Show the current tab, and add an "active" class to the button that opened the tab
            document.getElementById(cityName).style.display = "block";
            evt.currentTarget.className += " active";
        }
    </script>
</head>
<body>


<div class="tab">
    <button class="tablinks" onclick="openCity(event, 'KoBoRa')">KoBoRa</button>
    <button class="tablinks vorschlag" onclick="openCity(event, 'London')">London</button>
    <button class="tablinks vorschlag" onclick="openCity(event, 'Paris')">Paris</button>
    <button class="tablinks vorschlag" onclick="openCity(event, 'Tokyo')">Tokyo</button>
</div>

<div id="KoBoRa" class="tabcontent">
    <h3>1. Vorschlag</h3>
    <h1>Hier Visualisierung aller Vorschläge </h1>
</div>

<div id="London" class="tabcontent">
    <h3>1. Vorschlag</h3>
    <iframe src="https://docs.google.com/document/d/1xzz4itoJgIoLecGOshh0d4Omxmnytlr0Ajri0FJX-kM/edit" width="90%" height="400px"> </iframe>
</div>

<div id="Paris" class="tabcontent">
    <h3>Paris</h3>
    <iframe src="https://docs.google.com/document/d/1YFh2mUzMIBy2bF5vgu1AybLKB1xjks9p4ReP4pGpMhE/edit" width="90%" height="400px"> </iframe>
</div>

<div id="Tokyo" class="tabcontent">
    <h3>nur lesen</h3>
    <iframe src="https://docs.google.com/document/d/1jQFZmcS__-CtScjqd3g5KKM8xepPnMSqaepy2ag2jNc/edit" width="90%" height="400px"> </iframe>

</div>
</body>
</html>
