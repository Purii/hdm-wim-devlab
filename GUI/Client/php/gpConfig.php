<?php
session_start();

//Include Google client library 
include_once '../src/Google_Client.php';
include_once '../src/contrib/Google_Oauth2Service.php';

/*
 * Configuration and setup Google API
 * https://console.developers.google.com/apis/credentials?project=tesat-149819
 */
$clientId = '133336320793-hh58jrp5e95vs1moso69eeoohaf4j78s.apps.googleusercontent.com'; //Google client ID
$clientSecret = '4cPBdkmcTL41KVcHmJeDp4bO'; //Google client secret
$redirectURL = 'http://localhost/cloud/GUI/php/'; //Callback URL

//Call Google API
$gClient = new Google_Client();
$gClient->setApplicationName('Login to CodexWorld.com');
$gClient->setClientId($clientId);
$gClient->setClientSecret($clientSecret);
$gClient->setRedirectUri($redirectURL);

$google_oauthV2 = new Google_Oauth2Service($gClient);
?>