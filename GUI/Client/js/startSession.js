//alert(window.location.href.toString().indexOf("id"));


if(window.location.href.toString().indexOf("id") == -1) {
    var id = Date.now();
    window.location.href = "startSession.html?id=" + id;
}

//leite bei buttonclick min session id weiter
document.getElementById('enterSession').onclick = function () {

    var meetingRaumID = window.location.toString().split('=');
    var raumID = meetingRaumID[1].toString().split("?");
    raumID = raumID[0].toString().split('#');
    raumID = raumID[0];

    window.location.href = 'chartByButton.html?id='+raumID;
}

