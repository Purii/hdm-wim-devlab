var socket = new WebSocket('ws://localhost:8081/');
var meetingRaumID = window.location.toString().split('=');
var raumID = meetingRaumID[1].toString().split("#");

var sendetAllreadyGoogleInfos =[];

function readCookie(name) {
    name += '=';
    for (var ca = document.cookie.split(/;\s*/), i = ca.length - 1; i >= 0; i--)
        if (!ca[i].indexOf(name))
            return ca[i].replace(name, '');
}

var googleTokenId = readCookie("Google_ID");


socket.onopen = function (event) {

    log('Opened connection 🎉');

    var meetingRaumID = window.location.toString().split('=');




    var json = JSON.stringify({message: 'NewClient_' + googleTokenId + '_SessionID_' + raumID});

    socket.send(json);
    log('Sent: ' + json);
}


socket.onerror = function (event) {
    log('Error: ' + JSON.stringify(event));
}


function contains(testArray, isInside) {
    var i = testArray.length;
    while (i--) {
        if (testArray[i] === isInside) {
            return true;
        }
    }
    return false;
}

socket.onmessage = function (event) {


    try {


        var obj = JSON.parse(event.data); // this is how you parse a string into JSON

    //    alert("in obj" +obj['0'] );

     //   log(event.data);

        for (var key in obj) {

   //     log(key.toString());

        if(key.includes('NewClient') ) {

          var newClientData = obj[key];

          var newClient = newClientData.split('_');

          if(document.getElementById('LoggedInUsers').innerHTML.indexOf(' <li class="mdl-menu__item">googleID: ' + newClient[2] + ' Vorname: ' + newClient[4] + ' Nachname: ' + newClient[6] + ' email: ' + newClient[8] + '</li>') == -1 ) {

              document.getElementById('LoggedInUsers').innerHTML += ' <li class="mdl-menu__item">googleID: ' + newClient[2] + ' Vorname: ' + newClient[4] + ' Nachname: ' + newClient[6] + ' email: ' + newClient[8] + '</li>';

          }

            //checken ob es sich um meine session handelt

            //liste im html anlegen die mit google daten befüllt wird
            //      checken ob die empfangenen daten die eigenen sind, ansonsten nochmal eigenen daten schicken


            //wenn es sich nicht um die selbst gesendete nachricht handelt (dass ein enuer client da ist) dann sende das du da bist. Damit alle clients ihre liste mit allen teilnehmern füllen können
            var googleTokenId = readCookie("Google_ID");

            var googleTokenData = googleTokenId.split('_');


          if( googleTokenData[1] !=  newClient[2]){

            //  alert($.inArray(newClient[2], sendetAllreadyGoogleInfos));
              if( !contains(sendetAllreadyGoogleInfos,newClient[2]) ){


                /*   alert(googleTokenId.search(newClient[2]));
                   alert(googleTokenId);
                   alert(newClient[2]);
        */

                  //
                  var json = JSON.stringify({message: 'NewClient_' + googleTokenId + '_SessionID_' + raumID});

                  socket.send(json);
                  log('Sent:' + json);
                  sendetAllreadyGoogleInfos.push(newClient[2]);
              }

          }


       //  alert("googleID: "+ newClient[2] + " Vorname: " +newClient[4] + " Nachname: " + newClient[6] + " email: " + newClient[8]);


        }


            var suggestionINSIDE = JSON.parse(obj[key]);

            //   alert("in suginside" +obj[key] );
            //   log(obj[key]);

            //       var newClint = suggestionINSIDE['NewClient'];

            //     log(newClint);
            var name = suggestionINSIDE['name'];
            var link = suggestionINSIDE['link'];
            var SessionID = suggestionINSIDE['SessionID'];
            var TimeStamp = suggestionINSIDE['TimeStamp'];
          //  var SuggestionID = suggestionINSIDE['SuggestionID'];
            var DokumentenID = suggestionINSIDE['DokumentenID'];
            var prio = suggestionINSIDE['prio'];
            var DokuTyp = suggestionINSIDE['DokuTyp'];
            var folder = suggestionINSIDE['folder'];

            //checkt anhand der sessionID ob dieser vorschlag für diese Session ist
            //  log(SessionID + "  rennt gegen if");

            if (SessionID == raumID.toString()) {
               log("name " + name + " Link " + link);

               log("*****************************************************************");



            }


            else {


            }


        }


    }
    catch
        (ex) {
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
/*document.querySelector('#close').addEventListener('click', function (event) {
 socket.close();
 log('Closed connection 😱');
 });


 //setzt funktion auf send button (gibt es im meetingraum im moment nicht)
 document.querySelector('#send').addEventListener('click', function (event) {
 var json = JSON.stringify({message: 'Hey there'});
 socket.send(json);
 log('Sent: ' + json);
 });
 */
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
