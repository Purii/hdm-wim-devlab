
// erzeuge XMLHttpRequest - Objekt für ajax
var resOb = erzXHRObject();


// holt json vom deliverer und ruft handleResponse auf

function sndReq() {
 
    var meetingRaumID = window.location.toString().split('=');
    var raumID = meetingRaumID[1].toString().split("#");

    alert("Rsum ID in ajaxjson: " + raumID);
    //   alert("meetingraumid: "+raumID[0]);
    resOb.open('get', '../php/jsonDeliverer.php?&raumID='+raumID[0], true);
    resOb.onreadystatechange = function () {
        handleResponse();
    }
    resOb.send(null);
}

//überprüft ob der server im richtigen stadium ist (mit == 4) wenn ja wird draw() aufgerufen
function handleResponse() {
    var daten = null;
   
    if (resOb.readyState == 4) {
        drawSuggestion(daten);
    }
}

var id= 1;

//drawSuggestion parst die json daten und zerschneidet den String in die passenden Variablen. Danach erzeugt es die Bubbles,
//verpackt diese mit einem bubbleroom und fügt alles dem Anzeigebereich hinzu.
//Danach scrollt es die Anzeige zur neuesten bubble
function drawSuggestion(daten) {
    try {
        daten = JSON.parse(resOb.responseText);
    } catch (e) {
        //alert("problem");
        //alert(resOb.responseText);
        //stoppeAnwendung();
    }

    var datenString = daten.substring(1, daten.length - 1);
    var datenBubble = datenString.split(",");
    var name = datenBubble[0].substring(9, datenBubble[0].length - 1);
    var prio = datenBubble[1].substring(8);
    var color = datenBubble[2].substring(10);
    var link = datenBubble[3].substring(9, datenBubble[3].length - 1);



    //alert(name.toString());
    document.getElementById("vorschlagsListe").innerHTML += "<li id=suggestion"+id +">"+name.toString()+' id: ' + id +" </li>"

    var  idArray = [];
    //idArray.add('suggestion'+id );

    addTab(id, name);

    id++;

}

function addTab(id, name) {



    document.getElementById('suggestion'+id).onclick = function () {

        document.getElementById("listOfTabs").innerHTML +=    '<button class="tablinks vorschlag" onclick="openCity(event, \'London\')">'+name.toString() +' id: ' + id +'</button>';
    }

}
var myInterval;

function starteAnwendung() {
    myInterval = setInterval(sndReq, 2500);
}

function stoppeAnwendung() {
    clearInterval(myInterval);
}

//initalisiert beim Laden der html seite alle Komponenten
function init() {

    window.document.getElementById("stopButton").onclick = function () {
    	stoppeAnwendung();
    }
    window.document.getElementById("startButton").onclick = function () {
        starteAnwendung();
        doScroll = 1;
     }

}

//initalisiert beim laden der html seite alle Komponenten
window.onload = init;