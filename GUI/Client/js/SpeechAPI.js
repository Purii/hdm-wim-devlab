/**
 * @autor Emre Kesiciler, Nermin Hasani, Inci Kökpinar
 * Bei diesem File wird die Spracherkennung bzw. der Service von der Google Webspeech API mittels JavaScript angesprochen. 
 * Die Spracherkennung funktioniert nur in Chrome und Opera
 */

var final_transcript = '';
var recognizing = false;
var ignore_onend;
var start_timestamp;



/* Überprüft ob die Web Speech API von dem Brower untersützt wird. 
* Hierbei wird geprüft ob webkitSPeechRecognition Objekt existert, falls das nicht der Fall ist: Aufforderung den Browser upzudaten.
*/
if (!('webkitSpeechRecognition' in window)) {
  upgrade();
} else {
  
  
/* Falls webkitSPeechRecognition Objekt existiert, wird der Zugriff auf das Mikrofon erlaubt und die Spracherkennungs-Schnitstelle (SpeechRecognition Interface) bereitgestellt.
* Der Wert true für @continuous bestimmt, das wenn der Benutzer während des Sprechers pausiert, die Spracherkennung forgesetzt wird.
*/

  var recognition = new webkitSpeechRecognition();
  recognition.continuous = true;

  
  
/* Der Standardwert für interimResults ist false, was beudetet, das die Ergebnisse die vom Spracherkenner zurückgeliefert werden, 
* undgültig sind und sich daher nicht ändern werden. Beim Setzen des true Wertes, werden zunächst Zwischenergebnisse angezeigt und anschließend Endergebnisse als schwarz markiert.
*/
  recognition.interimResults = false;

/* Nach Beginn der Sprachaufnahme, wird der @onstart Event-Handler aufgerufen. Bei jedem neuen erzeugtem Ergebniss, wird der @onresult Event-Handler aufgerufen. 
* @recognizing = true: Hier wird der Zustand des Mikrofons überprüft, falls es nicht aktiv ist, wird ein Erorr Symbol anzeigt.
*/
  recognition.onstart = function() {
    recognizing = true;
    showInfo('info_speak_now');
    start_img.src = 'css/mic-animate.gif';
  };

  recognition.onerror = function(event) {
    if (event.error == 'no-speech') {
      start_img.src = 'css/mic.gif';
      showInfo('info_no_speech');
      ignore_onend = true;
    }
    if (event.error == 'audio-capture') {
      start_img.src = 'css/mic.gif';
      showInfo('info_no_microphone');
      ignore_onend = true;
    }
    if (event.error == 'not-allowed') {
      if (event.timeStamp - start_timestamp < 100) {
        showInfo('info_blocked');
      } else {
        showInfo('info_denied');
      }
      ignore_onend = true;
    }
  };

  
/* Hier wird die Funktion recognition.onend aufrufen, falls die Sprachaufnahme beendet wurde. Der default Wert ist für recognizing false.
*/
  recognition.onend = function() {
    recognizing = false;
    if (ignore_onend) {
      return;
    }
    start_img.src = 'css/mic.gif';
    if (!final_transcript) {
      showInfo('info_start');
      return;
    }
    showInfo('');
    if (window.getSelection) {
      window.getSelection().removeAllRanges();
      var range = document.createRange();
      range.selectNode(document.getElementById('final_span'));
      window.getSelection().addRange(range);
    }
  };

/*
* Das Resultat des erzeugtes Audios wird mit der Methode onresult durch eine For-Schleife durchlaufen.
* Parameter final_transcript wird in einen Text umgewandelt und anschließend in HTML konvertiert.
* 
*/
  
  recognition.onresult = function(event) {
    var interim_transcript = '';
    for (var i = event.resultIndex; i < event.results.length; ++i) {
      if (event.results[i].isFinal) {
        final_transcript += event.results[i][0].transcript;
      } else {
        interim_transcript += event.results[i][0].transcript;
      }
	  
	  /* Hier womöglich Aufruf einer Funktion in einem JavaScript File z.B (JSON.js), um das Textresultat bzw. Textstring mit den User Daten in Objekt zu packen und dieses Objekt anschließend in ein 
	  ein JSON Objekt umzukonvertieren und auf die SparkEAKomponente zuzuschicken.
	  
	  Funktionsname z.B. JSONUmwandlung();
      */
	  }
	  
	 
    final_transcript = capitalize(final_transcript);
    final_span.innerHTML = linebreak(final_transcript);
    editIntakeTime(final_transcript);

    interim_span.innerHTML = linebreak(interim_transcript);
    if (final_transcript || interim_transcript) {
      //showButtons('inline-block');
    }
  };
}

function editIntakeTime(transcript) {
	var objJson = new Object();
	objJson.timestamp = 1234556;
	objJson.sessionID = "1234Session";
	objJson.userID = "9832498729879";
	objJson.textresultat = transcript;

    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: "http://104.199.13.32/speechtokenizer/rest/token",
        dataType: "json",
        data: JSON.stringify(objJson),
        success: function(data, textStatus, jqXHR){
        	console.log(jqXHR);
        },
        error: function(jqXHR, textStatus, errorThrown){
        	console.log(jqXHR);
        }
    });
}

function upgrade() {
  start_button.style.visibility = 'hidden';
  showInfo('info_upgrade');
}

var two_line = /\n\n/g;
var one_line = /\n/g;
function linebreak(s) {
  return s.replace(two_line, '<p></p>').replace(one_line, '<br>');
}

var first_char = /\S/;
function capitalize(s) {
  return s.replace(first_char, function(m) { return m.toUpperCase(); });
}



/* Die Funktion startButton beginnt mit dem Drücken auf den Start-Button. 
** Falls beim Drücken des Start-Buttons die Sprachaufnahme bereits läuft, wird diese angehalten. 
** Bei einem Neustart wird das final_transcript bzw. das Resultat geleert.
*/

/*
window.onload = function (){
    startButton(event);
}

*/
function startButton(event) {
  if (recognizing) {
    recognition.stop();
    return;
  }
  final_transcript = '';
  recognition.lang = 'en-US';
  
/* Die Funktion recognition.start aktiviert die Spracherkennung anschließend ruft es den onstart Event-Handler auf.
*/
  recognition.start();
  ignore_onend = false;
  final_span.innerHTML = '';
  interim_span.innerHTML = '';


  start_img.src = 'css/mic-slash.gif';
  showInfo('info_allow');
  //showButtons('none');
  start_timestamp = event.timeStamp;
}

function showInfo(s) {
  if (s) {
    for (var child = info.firstChild; child; child = child.nextSibling) {
      if (child.style) {
        child.style.display = child.id == s ? 'inline' : 'none';
      }
    }
    info.style.visibility = 'visible';
  } else {
    info.style.visibility = 'hidden';
  }
}