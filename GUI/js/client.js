var socket = new WebSocket('ws://localhost:8081/');
var meetingRaumID = window.location.toString().split('=');
var raumID = meetingRaumID[1].toString().split("#");

var sendetAllreadyGoogleInfos = [];

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

    for (var g = 0; g < arrayName.length; g++) {

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


function upgradeArrays( name,  folderName,  parentName,  sizeName,  colorName) {

    //alert("upgrade arrays: " + name);

    if(in_array(nameArray, name)== true){

        // wenn name schon vorhanden, dann upgrade alle arrays mit neuen daten an entsprechender stelle ansonsten einfügen
    for (var g = 0; g < nameArray.length; g++) {


        if (nameArray[g] == name && sizeArray[g] != sizeName && colorArray[g] != colorName ) {

            nameArray[g] = name;
            folderArray[g] = folderName;
            parentArray[g] = parentName;
            sizeArray[g] = sizeName;
            colorArray[g] = colorName;

           // document.getElementById("drawButton").click();
        }
    }
    }else {
     //   alert("pushe in arrays: " + name);
        nameArray.push(name);
        folderArray.push(folderName);
        parentArray.push(parentName);
        sizeArray.push(sizeName);
        colorArray.push(colorName);
     //   document.getElementById("drawButton").click();
    }

    try {

parameterFunction();
     //   document.getElementById("defaultOpen").click();
     //   document.getElementById("drawButton").click();

    }catch (e){
        console.log("fehler beim chart aktualisieren in upgradeArrays methode im client.js")
    }

}


function getFolderArrayIndex(arrayName, needle) {
    var arrayIndex = 0;

    for (var g = 0; g < arrayName.length; g++) {

        if (arrayName[g] == needle) {
            arrayIndex = g;
        }

    }

    return arrayIndex;

}


function readCookie(name) {
   // alert(document.cookie)
    var value = "; " + document.cookie;
  var parts = value.split("; " + name + "=");
  if (parts.length == 2) return parts.pop().split(";").shift();
}


var googleTokenId = readCookie("Google_ID");
//alert(readCookie("Google_ID"));

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

      
        for (var key in obj) {



            if (key.includes('NewClient')) {

                var newClientData = obj[key];

                var newClient = newClientData.split('_');

                if (document.getElementById('LoggedInUsers').innerHTML.indexOf(' <li class="mdl-menu__item">googleID: ' + newClient[2] + ' Vorname: ' + newClient[4] + ' Nachname: ' + newClient[6] + ' email: ' + newClient[8] + '</li>') == -1) {

                    document.getElementById('LoggedInUsers').innerHTML += ' <li class="mdl-menu__item">googleID: ' + newClient[2] + ' Vorname: ' + newClient[4] + ' Nachname: ' + newClient[6] + ' email: ' + newClient[8] + '</li>';

                }

                //checken ob es sich um meine session handelt

                //liste im html anlegen die mit google daten befüllt wird
                //      checken ob die empfangenen daten die eigenen sind, ansonsten nochmal eigenen daten schicken


                //wenn es sich nicht um die selbst gesendete nachricht handelt (dass ein enuer client da ist) dann sende das du da bist. Damit alle clients ihre liste mit allen teilnehmern füllen können
                var googleTokenId = readCookie("Google_ID");

                var googleTokenData = googleTokenId.split('_');


                if (googleTokenData[1] != newClient[2]) {

                    //  alert($.inArray(newClient[2], sendetAllreadyGoogleInfos));
                    if (!contains(sendetAllreadyGoogleInfos, newClient[2])) {

                        var json = JSON.stringify({message: 'NewClient_' + googleTokenId + '_SessionID_' + raumID});

                        socket.send(json);
                        log('Sent:' + json);
                        sendetAllreadyGoogleInfos.push(newClient[2]);
                    }
                }
            }
        //    log("<br><br><br><br> jetzt key: ");
         //   log(key.toString());

            var suggestionINSIDE = JSON.parse(obj[key]);

            var SessionID = suggestionINSIDE['SessionID'];
            if (SessionID == raumID.toString()) {
                var name = suggestionINSIDE['name'];                          // name der angezeigt wird
                var link = suggestionINSIDE['link'];                         // Link der im neuen tab gezeigt wird

                var TimeStamp = suggestionINSIDE['TimeStamp'];
                //  var SuggestionID = suggestionINSIDE['SuggestionID'];
                var DokumentenID = suggestionINSIDE['DokumentenID'];
                var prio = suggestionINSIDE['prio'];                         // 0 oder 1 also errechnet sich daraus die farbe (hohe zahlen 1 = 100)
                var DokuTyp = suggestionINSIDE['DokuTyp'];
                var folder = suggestionINSIDE['folder'];

             //   upgradeArrays(nameArray, name, folderArray, folder, parentArray, 'StarCars', sizeArray, prio + 1, colorArray, prio * 5 + 1 + getFolderArrayIndex(folderArray, folder)) ;
    //            log(name);
  //              log(link);
//                log(folder);

                upgradeArrays( name, folder ,'StarCars', prio + 1, prio + 1 ) ;
            }

           //    log("name " + name + " Link " + link);
            //    log("*****************************************************************");



        }

        //zeichen google chart


        function parameterFunction() {
            google.charts.load('current', {'packages': ['treemap']});
            google.charts.setOnLoadCallback(drawChart);
            function drawChart() {

                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Location');
                data.addColumn('string', 'Parent');
                data.addColumn('number', 'size');
                data.addColumn('number', 'color');


                var rowArray = [];

                for (var y = 0; y < nameArray.length; y++) {

                    rowArray.push([nameArray[y], parentArray[y], sizeArray[y], colorArray[y]]);
                    //    log("pushe: " +[[nameArray[y], parentArray[y], sizeArray[y], colorArray[y]]] );
                }


                data.addRows(rowArray);


                tree = new google.visualization.TreeMap(document.getElementById('chart_div'));

                google.visualization.events.addListener(tree, 'select', selectHandler);


                /*      function showStaticTooltip(row, size, value) {
                 return '<div style="background:#fd9; padding:10px; border-style:solid; ">' +
                 'Read more about the <a href="http://en.wikipedia.org/wiki/Kingdom_(biology)">kingdoms of life</a>.</div>';
                 }

                 */
                function showFullTooltip(row, size, value) {
                    return '<div style="background:#fd9; padding:10px; z-index: +10; border-style:solid">' +
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
