var socket = new WebSocket('ws://localhost:8081/');
var meetingRaumID = window.location.toString().split('=');
var raumID = meetingRaumID[1].toString().split("#");
socket.onopen = function (event) {

    log('Opened connection 🎉');

    var meetingRaumID = window.location.toString().split('=');


    function readCookie(name) {
        name += '=';
        for (var ca = document.cookie.split(/;\s*/), i = ca.length - 1; i >= 0; i--)
            if (!ca[i].indexOf(name))
                return ca[i].replace(name, '');
    }

    var googleTokenId = readCookie("Google_ID");


    var json = JSON.stringify({message: 'NewClient_' + googleTokenId + '_SessionID_' + raumID});

    socket.send(json);
    log('Sent: ' + json);
}


socket.onerror = function (event) {
    log('Error: ' + JSON.stringify(event));
}


socket.onmessage = function (event) {


    try {
        var obj = JSON.parse(event.data); // this is how you parse a string into JSON
        //     document.body.innerHTML += obj.message;

        for (var key in obj) {

            var forSession = 'forSession';

            if (key.indexOf(forSession) !== -1) {

                if ((key.replace('forSession', '') == raumID.toString())) {


                    log(key.toString());


                    var suggestionINSIDE = JSON.parse(obj[key]);

                    var name = suggestionINSIDE['name'];
                    var link = suggestionINSIDE['link'];
                    var SessionID = suggestionINSIDE['SessionID'];
                    var TimeStamp = suggestionINSIDE['TimeStamp'];
                    var SuggestionID = suggestionINSIDE['SuggestionID'];
                    var DokumentenID = suggestionINSIDE['DokumentenID'];
                    var prio = suggestionINSIDE['prio'];
                    var DokuTyp = suggestionINSIDE['DokuTyp'];
                    var folder = suggestionINSIDE['folder'];

                    log("name " + name + " Link " +link);
                }
            }

        }

}
catch
(ex)
{
    console.error(ex);
}

try {

    //alert(event.data);
    var messageDaten = JSON.parse(event.data);
    var daten = JSON.parse(messageDaten);

    // alert(daten.valueOf(0));
    /*
     var datenString = daten.substring(1, daten.length - 1);
     var datenBubble = datenString.split(",");
     var name = datenBubble[0].substring(9, datenBubble[0].length - 1);
     var prio = datenBubble[1].substring(8);
     var color = datenBubble[2].substring(10);
     var link = datenBubble[3].substring(9, datenBubble[3].length - 1);

     */

    //   log('Received MG: ' + daten);
    //   alert("name: " + name);
} catch (e) {
    // alert("problem");
    //alert(resOb.responseText);
    //stoppeAnwendung();
}

//   log('<br>*****************************************************</br>');

//    log('Received: ' + event.data);

}

socket.onclose = function (event) {
    log('Closed connection 😱');
}

//setzt funktion auf close button (gibt es im meetingraum im moment nicht)
document.querySelector('#close').addEventListener('click', function (event) {
    socket.close();
    log('Closed connection 😱');
});

//setzt funktion auf send button (gibt es im meetingraum im moment nicht)
document.querySelector('#send').addEventListener('click', function (event) {
    var json = JSON.stringify({message: 'Hey there'});
    socket.send(json);
    log('Sent: ' + json);
});

function log(text) {
    var li = document.createElement('li');
    li.innerHTML = text;
    document.getElementById('log').appendChild(li);
}

/*
 var log = function(text) {
 var li = document.createElement('li');
 li.innerHTML = text;
 document.getElementById('log').appendChild(li);
 }

 */
window.addEventListener('beforeunload', function () {
    socket.close();
});
