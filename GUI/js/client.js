var socket = new WebSocket('ws://localhost:8081/');
var meetingRaumID = window.location.toString().split('=');
var raumID = meetingRaumID[1].toString().split("#");

var sendetAllreadyGoogleInfos =[];

//arrays für google treechart
var nameArray = [];
var parentArray = [];
var sizeArray = [];
var colorArray = [];

//wird für unterscheidliche farbabstufungen genutzt
var folderArray = [];


//grundelemente für treechart
nameArray.push('StarCars');
parentArray.push(null);
sizeArray.push(1);
colorArray.push(1);


function in_array(arrayName, needle) {
    var isInside = false;

    for(var g = 0; g < arrayName.length; g++) {

    //   log( typeof (arrayName[g])  +"  " +arrayName[g]  +" typ var " + typeof (needle) + " " + needle);
        if (arrayName[g] == needle) {

            isInside = true;
            return true;
        } else {
            isInside = false;
        }
    }
    return isInside;
}




function getFolderArrayIndex(arrayName, needle) {
    var arrayIndex = 0;

    for(var g = 0; g < arrayName.length; g++) {

        if( arrayName[g] == needle){
            arrayIndex = g;
        }

    }

        return arrayIndex;

}










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





        }

          //    log(obj[key]);
            var suggestionINSIDE = JSON.parse(obj[key]);
            var SessionID = suggestionINSIDE['SessionID'];

//            log("vor if mit: " + SessionID +"  und raumid:  " + raumID.toString());

                 //checkt anhand der sessionID ob dieser vorschlag für diese Session ist
                  if (SessionID == raumID.toString()) {

                   //   log("nach if");


                var name = suggestionINSIDE['name'];                          // name der angezeigt wird
                var link = suggestionINSIDE['link'];                         // Link der im neuen tab gezeigt wird

                var TimeStamp = suggestionINSIDE['TimeStamp'];
                //  var SuggestionID = suggestionINSIDE['SuggestionID'];
                var DokumentenID = suggestionINSIDE['DokumentenID'];
                var prio = suggestionINSIDE['prio'];                         // 0 oder 1 also errechnet sich daraus die farbe (hohe zahlen 1 = 100)
                var DokuTyp = suggestionINSIDE['DokuTyp'];
                var folder = suggestionINSIDE['folder'];                    // eregibt farbe der unterscheidlichen zahlen (niedrige zahlen je nach farben 1 - 50)

                  //    alert("pushe vor arrays");

                      log(in_array(nameArray, name )+ " name "+name+"  " +nameArray );

                      //prüft ob dokument schon vorhanden ist !!! keine doppelanmen zulässig !!!
                      if(in_array(nameArray, name ) == false) {

                         // log("pushe in arrays " + name);
                          folderArray.push(folder);

                          nameArray.push(name);
                          parentArray.push('StarCars');
                          sizeArray.push(prio* 5 + 1);   // Aufpassen!!! prio muss min 1 haben, sonst wird es nicht angezeigt!!!
                          colorArray.push( prio* 5 + 1 + getFolderArrayIndex(folderArray, folder)  );
                      }


                      log("name " + name + " Link " + link);

               log("*****************************************************************");


            }




        }

        //zeichen google chart

        function selectHandler(e) {
            addTab(1, 'name auf Namensarray');
           // alert('A table row was selected' +'______hier später neuen tab öffenen');
        }

        function parameterFunction( ){
            google.charts.load('current', {'packages':['treemap']});
            google.charts.setOnLoadCallback(drawChart);
            function drawChart() {

                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Location');
                data.addColumn('string', 'Parent');
                data.addColumn('number', 'size');
                data.addColumn('number', 'color');


                var rowArray = [];

                for(var y= 0; y < nameArray.length; y++) {

                    rowArray.push([nameArray[y], parentArray[y], sizeArray[y], colorArray[y]]);
                //    log("pushe: " +[[nameArray[y], parentArray[y], sizeArray[y], colorArray[y]]] );
                }


                data.addRows( rowArray);



                tree = new google.visualization.TreeMap(document.getElementById('chart_div'));

                google.visualization.events.addListener(tree, 'select', selectHandler);


          /*      function showStaticTooltip(row, size, value) {
                    return '<div style="background:#fd9; padding:10px; border-style:solid; ">' +
                        'Read more about the <a href="http://en.wikipedia.org/wiki/Kingdom_(biology)">kingdoms of life</a>.</div>';
                }

                */
                function showFullTooltip(row, size, value) {
                    return '<div style="background:#fd9; padding:10px; border-style:solid">' +
                        '<span style="font-family:Courier"><b>' + data.getValue(row, 0) +
                        '</b>, ' + data.getValue(row, 1) + ', ' + data.getValue(row, 2) +
                        ', ' + data.getValue(row, 3) + '</span><br>' +
                        'Datatable row: ' + row + '<br>' +
                        data.getColumnLabel(2) +
                        ' (total value of this cell and its children): ' + size + '<br>' +
                        data.getColumnLabel(3) + ': ' + value + ' </div>';
                }

                var options = {
                    minColor: '#FBF6EA',
                    midColor: '#FCDC86',
                    maxColor: '#FCB303',
                    headerHeight: 15,
                    fontColor: 'black',
                    showScale: true,
                    generateTooltip: showFullTooltip

                }

                tree.draw(data, options);
            }

        }

        document.getElementById("drawButton").onclick = parameterFunction;
       //document.getElementById("drawButton").onclick = alert(rowArray);



    }
    catch
        (ex) {
    //    console.error(ex);
    }

    try {

        //alert(event.data);
        var messageDaten = JSON.parse(event.data);
        var daten = JSON.parse(messageDaten);


    } catch (e) {
    //    alert("problem");
     //   alert(resOb.responseText);
      //  stoppeAnwendung();
    }



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
