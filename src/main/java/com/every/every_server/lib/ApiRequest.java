package com.every.every_server.lib;

import com.every.every_server.domain.vo.school.SchoolVO;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApiRequest {
    public static JsonObject getRequest(Url apiURL) throws Exception {
        URL url = new URL(apiURL.getBase());
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");

        StringBuilder sb = new StringBuilder();
        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();

            // Json Parsing
            JsonParser jsonParser = new JsonParser();

            JsonObject object = (JsonObject) jsonParser.parse(sb.toString());

            return object;
        } else {
            throw new Exception();
        }
    }
}
