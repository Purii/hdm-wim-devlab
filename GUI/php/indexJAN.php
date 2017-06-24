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
        $output .= '<br/>Logout from <a href="https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue=http://localhost/cloud/GUI/php/logout.php">Google</a>';
        $output .= ' <h2> Bestehende Meetingräume: </h2>
    <br>
   
    <br>
    <a href="chartByButton.html#id=1" target="_blank">Meetingraum 16846 </a>  
    
     <br> <br>
     <button type="button" id="createSession">Create a new Session</button>
      ';

  
    }else{
        $output = '<h3 style="color:red">Some problem occurred, please try again.</h3>';
    }
} else {
	$authUrl = $gClient->createAuthUrl();
	$output = '<a href="'.filter_var($authUrl, FILTER_SANITIZE_URL).'"><img src="../images/glogin.png" alt=""/></a>';
}
?>


<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>index</title>

    <!-- Material.io -->
    <link rel="stylesheet" href="https://unpkg.com/material-components-web@latest/dist/material-components-web.min.css">

    <!-- Materials Lites -->
   <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
   <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">
   <script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>

    <!-- MDL Theme Builder-->
   <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.grey-orange.min.css" />
  


    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>


   <!-- Always shows a header, even in smaller screens. -->
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
  <header class="mdl-layout__header">
    <div class="mdl-layout__header-row">
      <!-- Title -->
      <span class="mdl-layout-title">Title</span>
      <!-- Add spacer, to align navigation to the right -->
      <div class="mdl-layout-spacer"></div>
      <!-- Navigation. We hide it in small screens. -->
      <nav class="mdl-navigation mdl-layout--large-screen-only">
        <a class="mdl-navigation__link" href="">Link</a>
        <a class="mdl-navigation__link" href="">Link</a>
        <a class="mdl-navigation__link" href="">Link</a>
        <a class="mdl-navigation__link" href="">Link</a>
      </nav>
    </div>
  </header>

  <main class="mdl-layout__content">
    <div class="page-content">
    <!-- Your content goes here -->
     <div class="panel panel-default">
      <div class="panel-body" style="width: 100%; height: 260px">
          <div class="panelHangouts" style="float: left">
            <div id="startHangouts" style="margin-left: 50%; margin-top: 50%">
                <button class="mdl-button mdl-js-button mdl-button--raised">
                    <?php echo $output = '<script src="https://apis.google.com/js/platform.js" async defer></script><div class="g-hangout" data-render="createhangout">'; ?>
                </button>
            </div>
           </div>
       <div class="panelSession" style="float: right">  
            <div id="loginGoogle" style="margin-right: 50%; margin-top: 50%">
              <a href="./startSession.html"> <button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
               Start
               </button>
              </a>
            </div> 
    </div>
  </main>
</div>


 

   


    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../css/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <!-- MDL -->
    <script src="./material.min.js"></script>
    <!-- java scrip -->
    <script language="javascript" type="text/javascript" src="loginSite.js"></script>
   </body>
</html>
