<?php
//Include GP config file && User class
include_once 'gpConfig.php';



if(isset($_GET['code'])){
	$gClient->authenticate($_GET['code']);
	$_SESSION['token'] = $gClient->getAccessToken();
	header('Location: ' . filter_var($redirectURL, FILTER_SANITIZE_URL));
}

if (isset($_SESSION['token'])) {
	$gClient->setAccessToken($_SESSION['token']);
}


if ($gClient->getAccessToken()) {
	//Get user profile data from google
	$gpUserProfile = $google_oauthV2->userinfo->get();
	
	//Initialize User class
	//$user = new User();
	
	//Insert or update user data to the database
    $gpUserData = array(
        'oauth_provider'=> 'google',
        'oauth_uid'     => $gpUserProfile['id'],
        'first_name'    => $gpUserProfile['given_name'],
        'last_name'     => $gpUserProfile['family_name'],
        'email'         => $gpUserProfile['email'],
        'gender'        => $gpUserProfile['gender'],
        'locale'        => $gpUserProfile['locale'],
        'picture'       => $gpUserProfile['picture'],
        'link'          => $gpUserProfile['link']
    );
    
    //checkuser datet db ab oder trägt User beim ersten login in die mysql Datenbank ein
   // $userData = $user->checkUser($gpUserData);
    
	
	//Storing user data into session
//	$_SESSION['userData'] = $gpUserData;

    $cookieConten = ('GoogleID_'.$gpUserData['oauth_uid'] .'_FirstName_'.$gpUserData['first_name' ].'_LastName_'.$gpUserData['last_name' ].'_mail_'.$gpUserData['email']);
   echo "schreibe in cookie" . $cookieConten;

	//Setze cookie mit google id
    setcookie("Google_ID", $cookieConten);


	
	//Render Google profile data
    if(!empty($gpUserData)){
        $output = '<h1>Google Profile Details </h1>';
        $output .= '<img src="'.$gpUserData['picture'].'" width="300" height="220">';
        $output .= '<br/>Google ID : ' . $gpUserData['oauth_uid'];
        $output .= '<br/>Name : ' . $gpUserData['first_name'].' '.$gpUserData['last_name'];
        $output .= '<br/>Email : ' . $gpUserData['email'];
        $output .= '<br/>Gender : ' . $gpUserData['gender'];
        $output .= '<br/>Locale : ' . $gpUserData['locale'];
        $output .= '<br/>Logged in with : Google';
        $output .= '<br/><a href="'.$gpUserData['link'].'" target="_blank">Click to Visit Google+ Page</a>';
        $output .= '<br/>Logout from <a href="https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue=http://localhost/cloud/GUI_FINAL/php/logout.php">Google</a>';
        $output .= ' <h2> Bestehende Meetingräume: </h2>
    <br>
   
    <br>
    <a href="chartByButton.html#id=1" target="_blank">Meetingraum 16846 </a>    ';

  
    }else{
        $output = '<h3 style="color:red">Some problem occurred, please try again.</h3>';
    }
} else {
	$authUrl = $gClient->createAuthUrl();
	$output = '<a href="'.filter_var($authUrl, FILTER_SANITIZE_URL).'"><img src="../images/glogin.png" alt=""/></a>';
}
?>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Login with Google using PHP by CodexWorld</title>
<style type="text/css">
h1{font-family:Arial, Helvetica, sans-serif;color:#999999;}

    #loginGoogle{ margin: auto;}
</style>
</head>
<body>
<div id="loginGoogle"><?php echo $output; ?></div>

<script src="https://apis.google.com/js/platform.js" async defer></script>
<div class="g-hangout" data-render="createhangout"></div>




</body>
</html>