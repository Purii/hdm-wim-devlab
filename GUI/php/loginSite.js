
var timeStamp = Date.now();
document.getElementById('createSession').onclick = function(){
     window.location.replace ("http://localhost/cloud/GUI/php/chartByButton.html#id="+timeStamp);
}