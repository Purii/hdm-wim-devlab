
var http = require('http');
var express = require('express');
var WSS = require('ws').Server;

var app = express().use(express.static('public'));
var server = http.createServer(app);
server.listen(8080, '127.0.0.1');

var wss = new WSS({ port: 8081 });
wss.on('connection', function(socket) {
  console.log('Opened Connection 🎉');

  var json = JSON.stringify({ message: 'Gotcha' });
  socket.send(json);
  console.log('Sent: ' + json);

  socket.on('message', function(message) {
    console.log('Received: ' + message);

    if(message.search('NewClient')> 0){

        //infos an Semrep schicken 
        //hier neue initial Vorschläge schicken

        // Mitteilung zerlegen:


     //   var json = JSON.stringify({ Update_UserList: + 'Für welchen Raum ist diese Info bestimmt? '+ 'User Daten für den neuen User' });
      //  client.send(json);
    }

    wss.clients.forEach(function each(client) {
        var timeInMs = Date.now();
      var json = JSON.stringify({ message: ' oder hier neue initial Vorschläge schicken ??? Something changed at Time:' + timeInMs });
      client.send(json);
      console.log('Sent: ' + json);
    });
  });

  socket.on('close', function() {
    console.log('Closed Connection 😱');
  });

});

var broadcast = function() {
  var timeInMs = Date.now();
    var infoJSON1 = JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs,'SuggestionID':'12345', 'DokumentenID': '098765', 'name':'Dokument1', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
    var infoJSON2 = JSON.stringify({ "SessionID":"2","TimeStamp":timeInMs,"SuggestionID":"56843", "DokumentenID": "32846","name":"Dokument2", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
    var infoJSON3 = JSON.stringify({ 'SessionID':'3','TimeStamp':timeInMs,'SuggestionID':'12345', 'DokumentenID': '098765', 'name':'Dokument3', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
    var infoJSON4 = JSON.stringify({ "SessionID":"1","TimeStamp":timeInMs,"SuggestionID":"56843", "DokumentenID": "32846","name":"Dokument4", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
    var infoJSON5 = JSON.stringify({ 'SessionID':'2','TimeStamp':timeInMs,'SuggestionID':'12345', 'DokumentenID': '098765', 'name':'Dokument5', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
    var infoJSON6 = JSON.stringify({ "SessionID":"3","TimeStamp":timeInMs,"SuggestionID":"56843", "DokumentenID": "32846","name":"Dokument6", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
    var infoJSON7 = JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs,'SuggestionID':'12345', 'DokumentenID': '098765', 'name':'Dokument7', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
    var infoJSON8 = JSON.stringify({ "SessionID":"2","TimeStamp":timeInMs,"SuggestionID":"56843", "DokumentenID": "32846","name":"Dokument8", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
    var infoJSON9 = JSON.stringify({ 'SessionID':'3','TimeStamp':timeInMs,'SuggestionID':'12345', 'DokumentenID': '098765', 'name':'Dokument9', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
    var infoJSON10 = JSON.stringify({ "SessionID":"1","TimeStamp":timeInMs,"SuggestionID":"56843", "DokumentenID": "32846","name":"Dokument10", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
    var infoJSON11 = JSON.stringify({ 'SessionID':'2','TimeStamp':timeInMs,'SuggestionID':'12345', 'DokumentenID': '098765', 'name':'Dokument11', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
    var infoJSON12 = JSON.stringify({ "SessionID":"3","TimeStamp":timeInMs,"SuggestionID":"56843", "DokumentenID": "32846","name":"Dokument12", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
    var infoJSON13 = JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs,'SuggestionID':'12345', 'DokumentenID': '098765', 'name':'Dokument13', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
    var infoJSON14 = JSON.stringify({ "SessionID":"2","TimeStamp":timeInMs,"SuggestionID":"56843", "DokumentenID": "32846","name":"Dokument14", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
    var infoJSON15 = JSON.stringify({ 'SessionID':'3','TimeStamp':timeInMs,'SuggestionID':'12345', 'DokumentenID': '098765', 'name':'Dokument15', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
    var infoJSON16 = JSON.stringify({ "SessionID":"1","TimeStamp":timeInMs,"SuggestionID":"56843", "DokumentenID": "32846","name":"Dokument16", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
    var infoJSON17 = JSON.stringify({ 'SessionID':'2','TimeStamp':timeInMs,'SuggestionID':'12345', 'DokumentenID': '098765', 'name':'Dokument17', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
    var infoJSON18 = JSON.stringify({ "SessionID":"3","TimeStamp":timeInMs,"SuggestionID":"56843", "DokumentenID": "32846","name":"Dokument18", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
    var infoJSON19 = JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs,'SuggestionID':'12345', 'DokumentenID': '098765', 'name':'Dokument19', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
    var infoJSON20 = JSON.stringify({ "SessionID":"2","TimeStamp":timeInMs,"SuggestionID":"56843", "DokumentenID": "32846","name":"Dokument20", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );



  var json = JSON.stringify({
       forSession1: infoJSON1, forSession2: infoJSON2, forSession3: infoJSON3, forSession1: infoJSON4, forSession2: infoJSON5, forSession3: infoJSON6, forSession1: infoJSON7, forSession2: infoJSON8,forSession3: infoJSON9, forSession1: infoJSON10,forSession2: infoJSON10, forSession3: infoJSON11, forSession1: infoJSON12, forSession2: infoJSON13, forSession3: infoJSON14, forSession1: infoJSON15, forSession2: infoJSON16, forSession3: infoJSON17, forSession1: infoJSON18,forSession2: infoJSON19, forSession3: infoJSON20
  });

  wss.clients.forEach(function each(client) {
    client.send(json);
    console.log('Sent: ' + json);
  });
}
setInterval(broadcast, 3000);

/*quelle: https://github.com/sitepoint-editors/websocket-demo
Installation Steps

npm install
npm start
open http://localhost:8080/
*/