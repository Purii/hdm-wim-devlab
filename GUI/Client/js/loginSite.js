



if (document.cookie.indexOf("GoogleID") >= 0) {
    // They've been here before.
    //alert("hello again");
   // alert("hello again");
}else {

    window.location.href = 'index.html';
    //alert("leite zu login weiter");



}


document.getElementById('logoutFromGoogle').onclick = function () {

     var OneWeekAgo = new Date();
    OneWeekAgo.setDate(OneWeekAgo.getDate() -7);


    var cookieConten = ('GoogleID_'+null+'_FirstName_'+null+'_LastName_'+null+'_mail_'+null);
    document.cookie =  'GoogleID='+cookieConten+';expires='+OneWeekAgo ;


    var cookieConten2 = ('G_AUTHUSER_H'+null+'_FirstName_'+null+'_LastName_'+null+'_mail_'+null);
    document.cookie =  'G_AUTHUSER_H='+cookieConten2+';expires='+OneWeekAgo ;

    var ThisSide = 'http://localhost/cloud/test/hdm-wim-devlab/GUI/Client/html/index.html';
    window.location.href = "https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue="+ThisSide;

}



