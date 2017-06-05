/**
 * Hier wird der gefilterte Outputtext tokenisiert bzw. einzelne Tokens erstellt
 * @author Tolga Zülfikaroglu
 */

	/**
	 * Erstellen von TextInformation Objekt
	 */
	 
var TextInformation = new Object();


function JSONUmwandlung(){
	
	/**
	 * In diesem Bereich wird der Timestamp erstellt bzw. die JavaSript Datums-Objekte mit den Datumswerten: Sekunden, Minuten, Stunden, Tage, Monate und Jahre.
	 */
	var dt = new Date();
	var second = dt.getSeconds();
	var minute = dt.getMinutes();
	var hour = dt.getHours();
	var day = dt.getDate();
	var month = dt.getMonth()+1;
	var year = dt.getFullYear();
	var dtmixed = second + " " + minute + " " + hour + " " + day + " " + month + " " + year;
	

		
	/**
	 * Erstellen des Textinformationen Objektes mit Atrributen = (Textresultat aus Google WebSpeechAPI (final_transcript) + UserID)
	 */
		
		TextInformation = {
				final_transcript: final_transcript,
				timestamp: dtmixed,
				createdByUserId: getUniqueId(),
								
		};
		

		
		
/**
 * Diese Funktion erzeugt aus dem @param TextInformation einen JSON mit Hilfe der Methode
 * <code>JSON.stringify(TextInformation)</code>.
 */

function Stringify (TextInformation){
	var jsonString = JSON.stringify(TextInformation);
	console.log(jsonString);
}







/**
 *  Ajax Aufruf zur EventGruppe. Das stringify macht aus dem Objekt ein JSON.
 *  Über die URL wird die Schnittstelle aufgerufen.
 *  contentType und dataType signalisieren JSON.
 */


/**
 * Ajax Aufruf zur SPARK EVENT Komponente. Das stringify macht aus dem Objekt ein JSON.
  <code>JSON.stringify(TextInformation)</code>.
 * Diese Funktion sendet einen JSON File per Asychroner Post Request auf die SPARK Events Komponente
 * Über die URL wird die Schnittstelle aufgerufen.
 */

function SendTokenToEvent(TextInformation){
//Asynchroner Post Request auf SPARKEA

	$.ajax({ type:'POST', 
	url: 'https://URL/SPARKEA/rest/events/Text',  
	data: JSON.stringify(TextInformation),
	contentType: 'application/json',
	datatyp: 'json',

	//Fehler loggen 
    error: function( ){
        console.log("Error from event");
    },

	//Bei erfolgreichem Request Objekt in der Console ausgeben
    success: function(responsedata){
    	console.log("success from event")    		
    	}
});

}

		

};