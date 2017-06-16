
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

      if (message.search('NewClient') > 0) {

          //wenn sich ein neuer clint meldet sendet der server die Daten des neuen clints an alle clints so dass dort die listen befüllt werden können
          console.log("neuer Clint muss an alle clints geschickt werden " + message);



      wss.clients.forEach(function each(client) {
          var timeInMs = Date.now();
          var json = JSON.stringify({NewClient: message+ timeInMs});
          client.send(json);
          console.log('Sent: ' + json);
      });
  }
  });

  socket.on('close', function() {
    console.log('Closed Connection 😱');
  });

});

var broadcast = function() {
  var timeInMs = Date.now();
    var infoJSON1 = JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument1', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
    var infoJSON2 = JSON.stringify({ "SessionID":"2","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument2", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
    var infoJSON3 = JSON.stringify({ 'SessionID':'3','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument3', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Lisa' } );
    var infoJSON4 = JSON.stringify({ "SessionID":"1","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument4", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
    var infoJSON5 = JSON.stringify({ 'SessionID':'2','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument5', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
    var infoJSON6 = JSON.stringify({ "SessionID":"3","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument6", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Wede" } );
    var infoJSON7 = JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument7', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
    var infoJSON8 = JSON.stringify({ "SessionID":"2","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument8", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
    var infoJSON9 = JSON.stringify({ 'SessionID':'3','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument9', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Lisa' } );
    var infoJSON10 = JSON.stringify({ "SessionID":"1","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument10", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
    var infoJSON11 = JSON.stringify({ 'SessionID':'2','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument11', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
    var infoJSON12 = JSON.stringify({ "SessionID":"3","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument12", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
    var infoJSON13 = JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument13', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
    var infoJSON14 = JSON.stringify({ "SessionID":"2","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument14", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
    var infoJSON15 = JSON.stringify({ 'SessionID':'3','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument15', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
    var infoJSON16 = JSON.stringify({ "SessionID":"1","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument16", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
    var infoJSON17 = JSON.stringify({ 'SessionID':'2','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument17', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
    var infoJSON18 = JSON.stringify({ "SessionID":"3","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument18", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Lisa" } );
    var infoJSON19 = JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument19', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
    var infoJSON20 = JSON.stringify({ "SessionID":"2","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument20", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
    var infoJSON21 = JSON.stringify({ "SessionID":"3","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument21", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Jan" } );



  var json = JSON.stringify({
       info1: infoJSON1, info2: infoJSON2, info3: infoJSON3, info4: infoJSON4, info5: infoJSON5, info6: infoJSON6, info7: infoJSON7, info8: infoJSON8,info9: infoJSON9, info10: infoJSON10,info12: infoJSON10, info13: infoJSON11, info14: infoJSON12, info15: infoJSON13, info16: infoJSON14, info17: infoJSON15, info18: infoJSON16, info19: infoJSON17, info20: infoJSON18,info21: infoJSON19, info22: infoJSON20, info23: infoJSON21
  });



  wss.clients.forEach(function each(client) {
    client.send(json);
 //  console.log('Sent: ' + json);
  });
}
setInterval(broadcast, 3000);

/*quelle: https://github.com/sitepoint-editors/websocket-demo
Installation Steps

npm install
npm start
open http://localhost:8080/
*/