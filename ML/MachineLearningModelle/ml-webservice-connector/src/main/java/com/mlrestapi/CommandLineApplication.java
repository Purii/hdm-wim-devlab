package com.mlrestapi;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.ini4j.Ini;
import org.ini4j.Profile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CommandLineApplication {
  public static void main(String[] args) {
    Ini ini = null;
    try {
      ini = new Ini(new File("config.ini"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (ini != null) {
      MLServiceConnection mlServiceConnector = new MLServiceConnection();
      mlServiceConnector.setWorkspaceId(ini.get("meta", "workspace-id"));
      mlServiceConnector.setServiceId(ini.get("meta", "service-id"));
      mlServiceConnector.setApiKey(ini.get("meta", "api-key"));

      if(ini.getAll("values") != null){
        Profile.Section values = ini.getAll("values").get(0);
        values.entrySet().forEach(entry -> {
          mlServiceConnector.addValuePair(entry.getKey(), entry.getValue());
        });
      }else{
        OptionParser optionParser = new OptionParser("p::");
        OptionSet optionSet = optionParser.parse(args);
        if (optionSet.has("p")) {
          List<String> valuePairs = (List<String>) optionSet.valuesOf("p");
          mlServiceConnector.addValuePairs(valuePairs);
        }
      }

      try (PrintWriter printWriter = new PrintWriter("output.json")) {
        String output = mlServiceConnector.getMLOutput();
        printWriter.print(output);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }

    }

  }
}
