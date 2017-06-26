/**
 * Created by MGoet on 26.06.2017.
 */

function onSuccess(googleUser) {
    var profile = googleUser.getBasicProfile();
    gapi.client.load('plus', 'v1', function () {
        var request = gapi.client.plus.people.get({
            'userId': 'me'
        });
        //Display the user details

        //      $cookieConten = ('GoogleID_'.$gpUserData['oauth_uid'] .'_FirstName_'.$gpUserData['first_name' ].'_LastName_'.$gpUserData['last_name' ].'_mail_'.$gpUserData['email']);
        // echo "schreibe in cookie" . $cookieConten;
        var inOneWeek = new Date();

        request.execute(function (resp) {
            inOneWeek.setDate(inOneWeek.getDate() +7);
            var cookieConten = ('GoogleID_'+resp.id+'_FirstName_'+resp.name.givenName+'_LastName_'+resp.name.surname+'_mail_'+resp.emails[0].value);
            document.cookie =  'GoogleID='+cookieConten+';expires='+inOneWeek ;
                window.location.replace ("landingpage.html");

        });
    });
}
function onFailure(error) {
    alert(error);
}
function renderButton() {
    gapi.signin2.render('gSignIn', {
        'scope': 'profile email',
        'width': 240,
        'height': 50,
        'longtitle': true,
        'theme': 'dark',
        'onsuccess': onSuccess,
        'onfailure': onFailure
    });
}
/*
function signOut() {
    var OneWeekAgo = new Date();
    OneWeekAgo.setDate(OneWeekAgo.getDate() -7);
    var cookieConten = ('GoogleID_'+null+'_FirstName_'+null+'_LastName_'+null+'_mail_'+null);

    document.cookie =  'GoogleID='+cookieConten+';expires='+OneWeekAgo ;
    window.location.replace ("https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue=http://localhost/cloud/login-with-google-account-using-javascript/login-with-google-account-using-javascript/");
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
        $('.userContent').html('');
        $('#gSignIn').slideDown('slow');
    });
}

    */