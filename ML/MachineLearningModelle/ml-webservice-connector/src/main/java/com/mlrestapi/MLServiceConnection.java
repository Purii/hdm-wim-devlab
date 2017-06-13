package com.mlrestapi;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.*;

/**
 * MLServiceConnection stellt eine Verbindung zur Machine Learning API her.
 * Die Konfiguration der Verbindung setzt voraus, dass Workspace-ID, Service-ID, API-Version, API-Key bekannt sind.
 * API-Version = 2,0 per default
 * Pflichtinput sind Workspace-ID, Service-ID, API-Key sowie der Input (JSON) welcher an die API gesendet
 * wird.
 * Falls einer der Pflichtkinputs nicht gesetzt wurde, wird eine IllegalArgumentException
 */

public class MLServiceConnection {

  private String workspaceId;
  private String serviceId;
  private String apiVersion;
  private String apiKey;
  private String details;
  private List<List<String>> values;

  public MLServiceConnection(String workspaceId, String serviceId, String apiKey) {
    this.values = new ArrayList<List<String>>();
    this.workspaceId = workspaceId;
    this.serviceId = serviceId;
    this.apiKey = apiKey;
  }

  public MLServiceConnection() {
    this.values = new ArrayList<List<String>>();
    this.apiVersion = "2.0";
    this.details = "true";
  }

  /**
   * Gibt den von der ML API zurueckgegebenen JSON Wert als String zurueck.
   * Bevor diese Methode aufgerufen wird, müssen die Methoden "addValue" / "addValues" aufgerufen werden,
   * damit Input an die ML uebermittelt werden kann.
   *
 	Output der Machine Learning API als String
   */
  public String getMLOutput() {
    checkConfiguration();
    String inputAsJson = createInputJson();
    //erstellt aus dem JSON-String ein JSONNode Objekt welches fuer die UniREST API benoetigt wird
    JsonNode jsonNode = new JsonNode(inputAsJson);
    try {
      // Unirest API Aufruf (http://unirest.io/java.html)
      // nach Builder Pattern (http://www.heikomaass.de/2008/08/23/effective-java-builder-pattern/)
      HttpResponse<JsonNode> jsonResponse = Unirest.post("https://ussouthcentral.services.azureml.net/workspaces/"
              + workspaceId + "/services/" + serviceId + "/execute")
              .header("accept", "application/json")
              .header("Content-Type", "application/json")
              .header("Authorization",
                      "Bearer " + apiKey)
              .queryString("api-version", apiVersion)
              .queryString("details", details)
              .body(jsonNode)
              .asJson();
      if (jsonResponse.getStatus() == 200) {
        System.out.println("Request erfolgreich abgesetzt");
        System.out.println(jsonResponse.getBody().toString());
        
      }
      return jsonResponse.getBody().toString();

    } catch (UnirestException e) {
      System.out.println("MLConnector::Fehler > Bitte prüfen Sie ihre Internetverbindung. Host nicht erkannt.");
      e.printStackTrace();
    }
    return "Error occured";
  }


  /**
   * Baut aus den Wertepaaren den entsprechenden JSON Array String
   */
  private String createInputJson() {
    //Baut aus dem Array von Listen das Values JSON Konstrukt zusammen
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("[");
    //Aus Listen-Paar (User, Dokument) baut daraus ein Value-Array
    for (List<String> valuePair : values) {
      stringBuilder.append("[");
      stringBuilder.append("\"" +valuePair.get(0) + "\",");
      stringBuilder.append("\"" +valuePair.get(1) + "\"");
      stringBuilder.append("],");
    }
    stringBuilder.append("]");
    // Dem letzten Werte-Paar muss das ',' entfernt werden, da es sonst JSON meckert.
    int lastIndexOfComma = stringBuilder.lastIndexOf(",");
    stringBuilder.replace(lastIndexOfComma, lastIndexOfComma + 1, "");
    String buildValues = stringBuilder.toString();

    // Der entgültige JSON String mit den oben erstellen Wertepaaren
    String inputAsJson = "{\n" +
            "  \"Inputs\": {\n" +
            "    \"input1\": {\n" +
            "      \"ColumnNames\": [\n" +
            "        \"User\",\n" +
            "        \"Dokument\"\n" +
            "      ],\n" +
            "      \"Values\":" + buildValues +
            "    }\n" +
            "  },\n" +
            "  \"GlobalParameters\": {}\n" +
            "}";
    return inputAsJson;
  }


  /**
   * Prueft ob alle Pflichtkonfigurationen vorgenommen wurde. Wenn nicht der Fall, dann wird eine
   * IllegalArgumentException geworfen.
   */
  private void checkConfiguration() {
    if (values.isEmpty()) {
      throw new IllegalArgumentException("Es wurden keine Values festgelegt. Bitte die Methode 'addValuePair' vor dem "
              + "Aufruf nutzen.");
    }

    if (workspaceId == null) {
      throw new IllegalArgumentException("Es wurde keine Workspace-ID festgelegt. Bitte die Methode 'setWorkspaceID' vor dem "
              + "Aufruf nutzen oder die Workspace-ID im Konstruktor definieren");
    }

    if (serviceId == null) {
      throw new IllegalArgumentException("Es wurde kein Service-ID festgelegt. Bitte die Methode 'setServiceID' vor dem "
              + "Aufruf nutzen oder die Service-ID im Konstruktor definieren");
    }

    if (apiKey == null) {
      throw new IllegalArgumentException("Es wurde kein API-Key festgelegt. Bitte die Methode 'setApiKey' vor dem "
              + "Aufruf nutzen oder den API-Key im Konstruktor definieren");
    }
  }

  /**
   * Die Workspace ID festlegen
   *
   */
  public void setWorkspaceId(String workspaceId) {
    this.workspaceId = workspaceId;
  }


  /**
   * Die Service ID festlegen
   *
   */
  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  /**
   * Die Api-Version fuer den Webservice Call. Per Default ist 2.0 eingestellt.
   *
   */
  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }


  public void setDetails(String details) {
    this.details = details;
  }


  /**
   * Der API-Key fuer den Webservice Calls.
   */
  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }


  /**
   * Erstellt einen Values Eintrag fuer den Webservice API Call.
   *
   * parameter user, Document
   *
   */
  public void addValuePair(String user, String document) {
    values.add(Arrays.asList(user, document));
  }

  /**
   * Erstellt einen Values Eintrag fuer den Webservice API Call.
   *
   * valuePairs = Eine Liste von Value-Paaren
   */
  public void addValuePairs(List<String> valuePairs) {
    for( String valuePair : valuePairs){
      String[] split = valuePair.split(",");
      values.add(Arrays.asList(split));
    }
  }
}
