
var http = require('http');
var express = require('express');
var WSS = require('ws').Server;
var pubsub = require('@google-cloud/pubsub');



var pubsubClient = require('@google-cloud/pubsub')({
  projectId: 'hdm-wim-devlab',
  keyFilename: 'keyfile.json'
});

// Reference a topic that has been previously created. 
var topic = pubsubClient.topic('topic-1');
 


// Publish a message to the topic. 
topic.publish('Hallo Bene von FLo und MArkus', 'wie kommt es an? ', function(err) {});
topic.publish( {data : 'wie kommt es an? ', attributes: {"test":"Bene2"} }, function(err) {});
//var message ='{"message":{"data":"_DocumentName_Dokument16_didClicked_true_Client_GoogleID_100002025373871437436_FirstName_Mar_LastName_kus_mail_markus.goetz91%40gmail.com_SessionID_1_TimeStamp_1498147808919"","attributes":	{"EventType":"hasClicked" },	"messageId":"91010751788941",	"publishTime":"2017-04-05T23:16:42.302Z"	}}';


var message = {
      data: "testBeneTest"
      ,
      attributes: {
        key: "value",
        hello: "world"
      }
    }

  topic.publish( message,function(err) {});

/*
// BETTER 
var MY_TOPIC = "topic-1";
pubsub.subscribe(MY_TOPIC, function( msg, data ){
    console.log( data + '  ' + msg )
});

*/

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
    /*
    var Dokument1 = JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument1', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
    var Dokument2 = JSON.stringify({ "SessionID":"1","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument2", "prio":1, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
    var Dokument3= JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument3', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Lisa' } );
    var Dokument4 = JSON.stringify({ "SessionID":"1","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument4", "prio":1, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
    var Dokument5 = JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument5', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
    var Dokument6 = JSON.stringify({ "SessionID":"1","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument6", "prio":1, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Wede" } );
    var Dokument7 = JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument7', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
    var Dokument8 = JSON.stringify({ "SessionID":"1","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument8", "prio":1, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
    var Dokument9 = JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument9', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Lisa' } );
    var Dokument10 = JSON.stringify({ "SessionID":"1","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument10", "prio":1, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
    var Dokument11 = JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument11', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
    var Dokument12 = JSON.stringify({ "SessionID":"1","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument12", "prio":1, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
  //Vorschläge
    var Dokumentvorschlag1 = JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument13', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
    var Dokumentvorschlag2 = JSON.stringify({ "SessionID":"1","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument14", "prio":1, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
    var Dokumentvorschlag3 = JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument15', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
 //Favouriten
    var Favorit1 = JSON.stringify({ "SessionID":"1","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument16", "prio":1, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
    var Favorit2 = JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument17', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );

    //Nicht verwendet
    var Dokument = JSON.stringify({ "SessionID":"3","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument18", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Lisa" } );
    var Dokument = JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument19', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
    var Dokument = JSON.stringify({ "SessionID":"2","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument20", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
    var Dokument = JSON.stringify({ "SessionID":"3","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument21", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Jan" } );



     */


    var Dokument1 = JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument1', 'prio':0, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
     var Dokument2 = JSON.stringify({ "SessionID":"1","TimeStamp":timeInMs, "DokumentenID": "32846","name":"GitHub Accounts", "prio":Math.round(Math.random()), "DokuTyp":"gdoc", "link": "https://docs.google.com/spreadsheets/d/1Kb2oVZSd9TVxUmUcJLDcauaBDasWxMMr4wXpKvKcDf4/edit#gid=0", "folder": "Mercedesprojekt" } );
     var Dokument3= JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument3', 'prio':Math.round(Math.random()), 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Lisa' } );
     var Dokument4 = JSON.stringify({ "SessionID":"1","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument4", "prio":Math.round(Math.random()), "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
     var Dokument5 = JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument5', 'prio':Math.round(Math.random()), 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
     var Dokument6 = JSON.stringify({ "SessionID":"1","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument6", "prio":Math.round(Math.random()), "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Wede" } );
     var Dokument7 = JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument7', 'prio':Math.round(Math.random()), 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
     var Dokument8 = JSON.stringify({ "SessionID":"1","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument8", "prio":Math.round(Math.random()), "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
     var Dokument9 = JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument9', 'prio':Math.round(Math.random()), 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Lisa' } );
     var Dokument10 = JSON.stringify({ "SessionID":"1","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument10", "prio":Math.round(Math.random()), "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
     var Dokument11 = JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument11', 'prio':Math.round(Math.random()), 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
     var Dokument12 = JSON.stringify({ "SessionID":"1","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument12", "prio":Math.round(Math.random()), "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
     //Vorschläge
     var Dokumentvorschlag1 = JSON.stringify({ 'SessionID':'1','TimeStamp':null, 'DokumentenID': 'null', 'name':'null', 'prio':0, 'DokuTyp':'null', 'link': 'null', 'folder': 'null' } );
     var Dokumentvorschlag2 = JSON.stringify({ "SessionID":"1","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument14", "prio":Math.round(Math.random()), "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
     var Dokumentvorschlag3 = JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument15', 'prio':Math.round(Math.random()), 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
     //Favouriten
     var Favorit1 = JSON.stringify({ "SessionID":"1","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument16", "prio":1, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
     var Favorit2 = JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument17', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );

     //Nicht verwendet
     var Dokument = JSON.stringify({ "SessionID":"3","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument18", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Lisa" } );
     var Dokument = JSON.stringify({ 'SessionID':'1','TimeStamp':timeInMs, 'DokumentenID': '098765', 'name':'Dokument19', 'prio':1, 'DokuTyp':'gdoc', 'link': 'https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit', 'folder': 'Mercedesprojekt' } );
     var Dokument = JSON.stringify({ "SessionID":"2","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument20", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Mercedesprojekt" } );
     var Dokument = JSON.stringify({ "SessionID":"3","TimeStamp":timeInMs, "DokumentenID": "32846","name":"Dokument21", "prio":0, "DokuTyp":"gsheet", "link": "https://docs.google.com/document/d/1bkLCvEBX56OBrECe89SFxpaqhlfQfZtOUNHNxM2Rlmc/edit", "folder": "Jan" } );





  var json = JSON.stringify({
       info1: Dokument1, info2: Dokument2, info3: Dokument3, info4: Dokument4, info5: Dokument5, info6: Dokument6, info7: Dokument7, info8: Dokument8,info9: Dokument9, info10: Dokument10,info12: Dokument12, info13: Dokumentvorschlag1, info14: Dokumentvorschlag2, info15: Dokumentvorschlag3, info16: Favorit1, info17: Favorit2
  });



  wss.clients.forEach(function each(client) {
    client.send(json);
 //  console.log('Sent: ' + json);
   counter++;
  });
}
setInterval(broadcast, 6000);
var counter = 0;


/*quelle: https://github.com/sitepoint-editors/websocket-demo
Installation Steps

npm install
npm start
open http://localhost:8080/
*/