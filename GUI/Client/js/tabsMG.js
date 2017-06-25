/**
 * Created by Markus on 15.06.2017.
 */

function hasClickedEvent(documentName){

    var time = Date.now();

    var json = JSON.stringify({hasClicked:'_DocumentName_'+documentName+'_didClicked_'+true+'_Client_' + googleTokenId + '_SessionID_' + raumID+'_TimeStamp_'+time });

    socket.send(json);
    log('Sent: ' + json);
}



function openTab(evt, tabName) {

    // doDraw = false;
    //  alert("doDraw: " + doDraw);
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
    // console.log(tabName);
    document.getElementById(tabName+"Body").style.display = "block";
    //  document.getElementById(tabName+"Body").style.display = "block";
    evt.currentTarget.className += " active";
//    evt.currentTarget.className += "tab-background";
    var documentName = tabName.split("_");
    hasClickedEvent(documentName[0]);
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
    doDraw == true;
}


function addTab(name) {



    var timeStamp = Math.floor(Date.now() / 10000);
    var nameAndTimeStampID = name+'_'+timeStamp;


    /* chartData.push(nameArray[i]);
     chartData.push(folderArray[i]);
     chartData.push(parentArray[i]);
     chartData.push(sizeArray[i]);
     chartData.push(colorArray[i]);
     chartData.push(dokuTypArray[i]);
     chartData.push(linkArray[i]);
     chartData.push(DokumentenIDArray[i]);

     */

    var chartData =    getChartDataByName(name);
    var link = chartData[6];
//alert(link);
    // tab body erzeugen
    document.getElementById('contentForTabs').innerHTML +=  '<div id='+ nameAndTimeStampID +"Body"+' class="tabcontent"> <span class="closeButtons" onclick="closeTab(this, \''+nameAndTimeStampID+'\')">Close tab x</span> <h3>'+name+'</h3>  <a href="'+link+'" class="openinBrowserTab" target="_blank">Open Document in a new Browser Tab</a> <br><br><br><br> <iframe class="displayedDocs" src="'+link+'"></iframe> </div>';

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
//document.getElementById("drawButton").click();