
document.getElementById('logoutFromGoogle').onclick = function () {

    var ThisSide = window.location.href;
    window.location.href = "https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue="+ThisSide;

}


