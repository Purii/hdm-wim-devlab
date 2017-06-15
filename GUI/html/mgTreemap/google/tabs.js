/**
 * Created by Markus on 15.06.2017.
 */




function openTab(evt, tabName) {
    // Declare all variables
    var i, tabcontent, tablinks;

    //zeichne treemap neu


    // Get all elements with class="tabcontent" and hide them
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Get all elements with class="tablinks" and remove the class "active"
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    // Show the current tab, and add an "active" class to the button that opened the tab
 //  alert(tabName);
    console.log(tabName);
   document.getElementById(tabName+"Body").style.display = "block";
  //  document.getElementById(tabName+"Body").style.display = "block";
    evt.currentTarget.className += " active";
//    evt.currentTarget.className += "tab-background";

}


function closeTab(tab, name) {


    //alert( "close " + name);
    tab.parentElement.style.display= 'none';

    // wenn inhalte in bodys dynamisch geladen werden wieder rein machen sonst ist es zu ressourcen intzensiv
    tab.parentElement.innerHTML = "";
    document.getElementById("defaultOpen").click();

    document.getElementById(name+"TAB").remove();
  //  document.getElementById(name+"TAB").style.display= 'none';

  //  document.getElementById('tabList').innerHTML
    /*
        var codeForButton = "<button class=\"tablinks\" onclick=\"openTab(event, "+ "'"+ name + "'"+")\"> " + name + "</button>"
        document.getElementById('tabList').innerHTML =  document.getElementById('tabList').innerHTML.replace(codeForButton,'');
    */
}

function addTab(name) {

    var timeStamp = Math.floor(Date.now() / 10000);
    var nameAndTimeStampID = name+timeStamp;

    // tab body erzeugen
    document.getElementById('contentForTabs').innerHTML +=  '<div id='+ nameAndTimeStampID +"Body"+' class="tabcontent"> <span class="closeButtons" onclick="closeTab(this, \''+nameAndTimeStampID+'\')">Close tab x</span> <h3>'+name+'</h3>  <iframe class="displayedDocs" src="https://docs.google.com/document/d/1PepcbDl6-aEOFPkNZ-5sxLPVOO5fpzVU4XmaMb49P2w/edit#heading=h.gjdgxs"></iframe> </div>';

   // Buttons erzeugen und in tabList schreiben
    var codeForButton = "<button class=\"tablinks\" id="+nameAndTimeStampID+"TAB onclick=\"openTab(event, "+ "'"+ nameAndTimeStampID + "'"+")\"> " + name + "</button>"
    document.getElementById('tabList').innerHTML += codeForButton;


    document.getElementById('defaultOpen').addEventListener('click', function(event) {
        openTab(event, "KoBoRa");
        parameterFunction();
    });



openTab(event,nameAndTimeStampID );
document.getElementById(nameAndTimeStampID+"TAB").click();
}




document.getElementById("defaultOpen").onclick=  function (){openTab(event, 'KoBoRa')};

document.getElementById("defaultOpen").click();
document.getElementById("drawButton").click();