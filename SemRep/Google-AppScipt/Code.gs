//View wird angelegt durch HTML Service und dem User Interface übergeben

function metadataUserView() {
  html= HtmlService
      .createTemplateFromFile('dynamicMetadata')
      .evaluate()
      .setSandboxMode(HtmlService.SandboxMode.IFRAME)
  .setTitle("Manuelle Attributierung");
    DocumentApp.getUi().showSidebar(html);
}
 

function getCurrentFolderName() {
  var activeDoc = DocumentApp.getActiveDocument(); //current spreadsheet
  var directParents = DriveApp.getFileById(activeDoc.getId()).getParents();
  var currentFolderName = directParents.next().getName();
  Logger.log("aaa ->     " +currentFolderName);
  return currentFolderName; 
}


function metadataStaticView() {
  html= HtmlService
      .createTemplateFromFile('staticMetadata')
       .evaluate()
      .setSandboxMode(HtmlService.SandboxMode.IFRAME);
    DocumentApp.getUi().showSidebar(html);
}


function helpView() {
  html= HtmlService
      .createTemplateFromFile('help')
       .evaluate()
      .setSandboxMode(HtmlService.SandboxMode.IFRAME);
    DocumentApp.getUi().showSidebar(html);
}




// Getter für statische Metadaten
 
function getOwner() {
 
  var fileNameID = DocumentApp.getActiveDocument().getId();
  
  try
  {
    
    var ownerMail = DriveApp.getFolderById(fileNameID).getOwner().getEmail();
    var ownerName = DriveApp.getFolderById(fileNameID).getOwner().getName();
    var ownerdata = ownerName + ' , <br />' + ownerMail;
    
  }
  catch(err)
  {
    
    Logger.log(err)
    
  }
   return ownerdata;
}


function getURL() {
  var documentPath = DocumentApp.getActiveDocument().getUrl();    
  PropertiesService.getScriptProperties().setProperty('documentPath', documentPath);
  return documentPath;
}


function getFileName() { 
  var fileName = DocumentApp.getActiveDocument().getName();
  PropertiesService.getScriptProperties().setProperty('fileName', fileName);
  return fileName;
}


function getID() {
  var driveDocumentID = DocumentApp.getActiveDocument().getId();
  PropertiesService.getScriptProperties().setProperty('driveDocumentID', driveDocumentID);
  return driveDocumentID;
}


function getDateCreated() {
  var files = DriveApp.getFilesByName(PropertiesService.getScriptProperties().getProperty('fileName'));
  while (files.hasNext()) {
   var file = files.next();
   var formattedDate = Utilities.formatDate(file.getDateCreated(), "GMT", "yyyy-MM-dd'T'HH:mm:ss");

  // PropertiesService.getScriptProperties().setProperty('creationDate', creationDate);
   return ""+formattedDate;
 }
}

/*
function getDateCreated() {
 var files = DriveApp.getFilesByName(PropertiesService.getScriptProperties().getProperty('fileName'));
 while (files.hasNext()) {
   var file = files.next();
   //var creationDate = 'test'; 
      return ""+file.getDateCreated();
 }
}*/


function getMimeType() {
var mimetype = DocumentApp.getActiveDocument().getBlob().getContentType();
return  mimetype 
} 


function getLastUpdated() {
 var files = DriveApp.getFilesByName(PropertiesService.getScriptProperties().getProperty('fileName'));
 while (files.hasNext()) {
   var file = files.next();
   var formattedDate = Utilities.formatDate(file.getLastUpdated(), "GMT", "yyyy-MM-dd'T'HH:mm:ss");
   return ""+formattedDate;
 }
  var folders = DriveApp.getFolders();
   while (folders.hasNext()) {
   var folder = folders.next();
 }
}

 
//Dokumentproperties abfragen und setzen

function processMetadataForm(theForm) {
  var props=PropertiesService.getDocumentProperties()
  
  for (var item in theForm) {
    props.setProperty(item,theForm[item])
    Logger.log(item+':::'+theForm[item]);
  }
}



//Diese funktion wird beim Start des Addons aufgerufen
//Das Menü wird initalisiiert, eine View wird hinterlegt, abschließend zur UI hinzugefügt

function onOpen(e) {
  DocumentApp.getUi()
  .createMenu('timing')
  .addItem('Manuelle Attributierung', 'metadataUserView')
  .addSeparator()
  .addItem('Statische Attributierung', 'metadataStaticView')
  .addSeparator()
  .addItem('Hilfe', 'helpView')
  .addToUi();
  
  
    
  
}




