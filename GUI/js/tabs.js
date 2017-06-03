/**
 * Created by Markus on 02.06.2017.
 */
function openCity(evt, cityName) {


    // alert("evt:" + evt);
    //alert("cityName: " +cityName);


    // Declare all variables
    var i, tabcontent, tablinks;

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
    document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " active";

    if (cityName == "London") {

        document.getElementById('London').innerHTML += '  <span class="closeTab" onclick="closeTab(this)">Close this Tab <i class="fa fa-times" aria-hidden="true"></i></span> ' +'<br> <iframe src="https://docs.google.com/document/d/1xzz4itoJgIoLecGOshh0d4Omxmnytlr0Ajri0FJX-kM/edit" width="80%" height="800px"> </iframe>' ;
    }
    if (cityName == "Paris") {

        document.getElementById('Paris').innerHTML += '<iframe src="https://docs.google.com/document/d/1YFh2mUzMIBy2bF5vgu1AybLKB1xjks9p4ReP4pGpMhE/edit" width="80%" height="800px"> </iframe>';
    }

}

function closeTab(tab) {
    tab.parentElement.style.display= 'none';
    tab.parentElement.innerHTML = "";
    document.getElementById("defaultOpen").click();

}

// Get the element with id="defaultOpen" and click on it at the page Load
document.getElementById("defaultOpen").click();