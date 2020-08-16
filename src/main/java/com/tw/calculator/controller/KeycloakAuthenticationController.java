package com.tw.calculator.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class KeycloakAuthenticationController {
    @GetMapping("/getKeycloakAccessToken")
    public String getKeycloakAccessToken(@RequestParam Map<String, String> allRequestParams, ModelMap model) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(allRequestParams.get("host") + "/auth/realms/servicestarterrealm/protocol/openid-connect/token");
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grant_type", allRequestParams.get("grant_type")));
        params.add(new BasicNameValuePair("username", allRequestParams.get("username")));
        params.add(new BasicNameValuePair("password", allRequestParams.get("password")));
        params.add(new BasicNameValuePair("client_id", allRequestParams.get("client_id")));
        params.add(new BasicNameValuePair("client_secret", allRequestParams.get("client_secret")));
        params.add(new BasicNameValuePair("scope", allRequestParams.get("scope")));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        CloseableHttpResponse response = client.execute(httpPost);
        String json_string = EntityUtils.toString(response.getEntity());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json_string);
        String token = node.get("access_token").asText();
        client.close();
        return token;
    }
}
