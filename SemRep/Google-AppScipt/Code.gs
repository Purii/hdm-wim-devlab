



// ############################## URL-FETCH ##############################

/*
* Diese Methode fügt alle im Forumlar vorhadenen Metadaten in die Abox ein. Dazu werden neben manuellen Attribute,
* die der Nutzer in die Formularfelder eingetragen hat, auch statische Attribute übergeben. Die statischen Attribute
* werden aus den Documentproperties gezogen. Am Ende der Metode wird die UrlFetchApp.fetch();
*/


function insertMetadata(doc_version, doc_have_context, doc_category, doc_stage, doc_projectlink, doc_keywords) {

       var docKeywords = [];

       var doc_version = "2";
       var doc_have_context = "GoogleDriveAddonMetadata";
       var doc_category = "GoogleDriveAddonMetadata";
       var doc_stage = "GoogleDriveAddonMetadata";
       var doc_projectlink = "https://docs.google.com/document/d/1IzjhOotzvk7OVh7MgBrZchrKspBEpbvk9WFIz5ZTzi0/edit";

       var doc_id = getID();
       var doc_updateTime =  getLastUpdated();
       var doc_url = getURL();
       var doc_creationTime = getDateCreated();
       var doc_rootFolder =  getCurrentFolderName() ;
       var doc_name =  getFileName();
       var doc_typ = getMimeType();

       var doc_favorite = getFileName();
       var doc_autor = getOwner();


       this.docKeywords = doc_keywords;



       var payload =
       {
       "docVersion" : doc_version,
       "docContext" : doc_have_context,
       "docCategpry" : doc_category,
       "docKeywords" : "'nummer1'",
       "docStage" : doc_stage,
       "docProjectlink" : doc_projectlink,

       "docID" : doc_id,
       "docUpdateTime" : doc_updateTime,
       "docUrl" : doc_url,
       "docCreationTime" : doc_creationTime,
       "docRootFolder" : doc_rootFolder,
       "docName" : doc_name,
       "docTyp" : doc_typ,
       "docFavoriten" : doc_favorite,
       "docAutor" : doc_autor,
       };

       var options =
       {
       "method" : "post",
       "payload" : payload,
       "followRedirects" : true,
       "muteHttpExceptions": true
       }

       var ausgabe = UrlFetchApp.fetch("http://35.187.45.171/rest/internal/insertMetadata", options);

       //Logger.log(doc_id );
       Logger.log(doc_id + "  " + doc_updateTime  + "  " + doc_url + "  " + doc_creationTime + " e1 " + doc_rootFolder + " e2 " + doc_name );
       Logger.log(doc_typ  + " " +doc_favorite+ " " +doc_autor ) ;

  }



/*
* Diese Methode ruft alle Projekte aus der A-Box von dem Jena Fuseki Server ab.
* Eine REST-methode nimmt auf der Appengine die Anfrage entgegen und nimmt mit dem Fusekiserver
* kontakt auf. Das Ergebnis der Abfrage wir dann letztendlich an die Methode getProjectsFromEndpoint() zurückgegeben.
*/

          function getProjectsFromEndpoint() {

            var options =
                {
                  "method"  : "GET",
                  "followRedirects" : true,
                  "muteHttpExceptions": true
                };

            var response = UrlFetchApp.fetch("https://clouddocs-152111.appspot.com/rest/internal/getprojects",options);

            var message = response.getContentText();
            //  var payload = JSON.stringify(response);
            //var payload =  JSON.parse(message);

            Logger.log(message);

            // Logger.log("REsponse ->      " + response.getContentText()  +  " b   "+ payload);
            return message;

          }



/*
* Diese Methode ruft alle Projektphasen aus der A-Box von dem Jena Fuseki Server ab.
* Eine REST-methode nimmt auf der Appengine die Anfrage entgegen und nimmt mit dem Fusekiserver
* kontakt auf. Das Ergebnis der Abfrage wir dann letztendlich an die Methode getProjectStagesFromEndpoint() zurückgegeben.
*/

          function getProjectStagesFromEndpoint() {

            var options =
                {
                  "method"  : "GET",
                  "followRedirects" : true,
                  "muteHttpExceptions": true
                };

            var response = UrlFetchApp.fetch("https://clouddocs-152111.appspot.com/rest/internal/getprojectstage",options);
            var message = response.getContentText();
            return message;
          }




/*
* Diese Methode ruft alle Dokumentenkategorien aus der A-Box von dem Jena Fuseki Server ab.
* Eine REST-methode nimmt auf der Appengine die Anfrage entgegen und nimmt mit dem Fusekiserver
* kontakt auf. Das Ergebnis der Abfrage wir dann letztendlich an die Methode getDocCategoryFromEndpoint() zurückgegeben.
*/
          function getDocCategoryFromEndpoint() {

            //var a =    getProjectStagesFromEndpoint();

            var options =
                {
                  "method"  : "GET",
                  "followRedirects" : true,
                  "muteHttpExceptions": true
                };

            var response = UrlFetchApp.fetch("https://clouddocs-152111.appspot.com/rest/internal/getdoccategory",options);

            var message = response.getContentText();
            Logger.log(message);
            return message;
          }



// ############################## VIEW  ##############################

/*
* Diese Methoden weisen die HTML-Dateien: staticMetadata.html, help.html, dynamicMetadata.html dem
* Sidebarwidget zu.
*/


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

             function metadataUserView() {
                html= HtmlService
                .createTemplateFromFile('dynamicMetadata')
                .evaluate()
                .setSandboxMode(HtmlService.SandboxMode.IFRAME)
                .setTitle("Manuelle Attributierung");
                DocumentApp.getUi().showSidebar(html);
              }



// ############################## Entry  ##############################

/*
* Diese Funktion wird beim Start des Addons aufgerufen
* Das Menü wird initalisiiert, eine View wird hinterlegt und abschließend zur UI hinzugefügt.
*/

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



// ############################## GETTER ##############################

/*
*  -- Diese Methode ruft alle Dokumentenkategorien aus der A-Box von dem Jena Fuseki Server ab.
* Eine REST-methode nimmt auf der Appengine die Anfrage entgegen und nimmt mit dem Fusekiserver
* kontakt auf. Das Ergebnis der Abfrage wir dann letztendlich an die Methode getDocCategoryFromEndpoint() zurückgegeben.
*/


function getCurrentFolderName() {
  var activeDoc = DocumentApp.getActiveDocument();
  var directParents = DriveApp.getFileById(activeDoc.getId()).getParents();
  var currentFolderName = directParents.next().getName();
  return currentFolderName;
}


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
  PropertiesService.getScriptProperties().setProperty(driveDocumentID, driveDocumentID);
  Logger.log(driveDocumentID);

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


function getMimeType() {
var mimetype = DocumentApp.getActiveDocument().getBlob().getContentType();
Logger.log(mimetype);
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








