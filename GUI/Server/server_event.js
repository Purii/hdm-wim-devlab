//PubsubClient
var pubsubClient = require('@google-cloud/pubsub')({
  projectId: 'hdm-wim-devlab',
  keyFilename: 'keyfile.json'
});

// Reference a topic that has been previously created.
var topic = pubsubClient.topic('topic-1');
//Hier kommen die Daten an

var userID 
var sessionID
var documentID
var documentName
var eventSource
var eventType

//Message Konstruktor
switch(eventType) {

//FeedbackEvent Dokument wurde dem User angezeigt
    case feedback:
        var FeedbackEvent = {
            data: "FeedbackEvent",
            attributes: {
                userId: userID,
                documentName: documentName,
                EventSource: "user-interface",
                EventType: "feedback"
                        }
        }
        var message = FeedbackEvent
                topic.publish( message,function(err) {
                console.log("Sended")
                });
    break;

//SessionStartEvent User starter eine Session

    case session-start:
        var SessionStartEvent = {
            data: "SessionStartEvent",
            attributes: {
                sessionId: sessionID,
                userId: userID,
                EventSource: "user-interface",
                EventType: "session-start"
                        }
        }
             var message = SessionStartEvent
                topic.publish( message,function(err) {
                console.log("Sended")
                });
    break;

//StayAliveEvent Aktivitätsprüfung 

case stayalive:
        var StayAliveEvent = {
            data: "StayAliveEvent",
            attributes: {
                userId: userID,
                EventSource: "user-interface",
                EventType: "stayalive"
                        }
        }
             var message = StayAliveEvent
                topic.publish( message,function(err) {
                console.log("Sended")
                });
    break;

//SuccessfulFeedbackEvent ein Nutzer hat ein Dokument angeklickt

case feedback: //nochmal prüfen renundater Eventtype
        var SuccessfulFeedbackEvent = {
            data: "SuccessfulFeedbackEvent",
            attributes: {
                userId: userID,
                documentId: documentID,
                documentAffiliation:"true",
                EventSource:"user-interface",
                EventType:"feedback"
                        }
        }
             var message = SuccessfulFeedbackEvent
                topic.publish( message,function(err) {
                console.log("Sended")
                });
    break;

//UserLoginEvent User hat sich eingeloggt

case user-login: 
        var UserLoginEvent = {
            data: "UserLoginEvent",
            attributes: {
                userId: userID,
                EventSource:"user-interface",
                EventType:"user-login"
                        }
        }
             var message = UserLoginEvent
                topic.publish( message,function(err) {
                console.log("Sended")
                });
    break;

//UserJoinedSessionEvent ein User hat sich an einer Session angemeldet

    case user-joined-session: 
        var UserJoinedSessionEvent = {
            data: "UserJoinedSessionEvent",
            attributes: {
                userId: userID,
                sessionId: sessionID,
                EventSource: "user-interface",
                EventType: "user-joined-session"
                        }
        }
             var message = UserLoginEvent
                topic.publish( message,function(err) {
                console.log("Sended")
                });
    break;

//UserLeftSessionEvent ein User hat sich von einer Session abgemeldet 

    case user-left-session: 
        var UserJoinedSessionEvent = {
            data: "UserLeftSessionEvent",
            attributes: {
                userId: userID,
                sessionId: sessionID,
                EventSource:"user-interface",
                EventType: "user-left-session"
                        }
        }
             var message = UserLoginEvent
                topic.publish( message,function(err) {
                console.log("Sended")
                });
    break;

//UserStartEvent ein User startet die Recommender-Funktion

    case user-start: 
        var UserStartEvent = {
            data: "UserStartEvent",
            attributes: {
                userId: userID,
                sessionId: sessionID,
                EventSource:"user-interface",
                EventType:"user-start"
                        }
        }
             var message = UserLoginEvent
                topic.publish( message,function(err) {
                console.log("Sended")
                });
    break;
}
