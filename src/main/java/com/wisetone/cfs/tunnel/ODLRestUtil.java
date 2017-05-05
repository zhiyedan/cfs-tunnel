package com.wisetone.cfs.tunnel;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by zhiyedan on 5/5/17.
 */
public class ODLRestUtil {
    //    JSONObject jsonObject = new JSONObject();
    public static HttpClient httpClient;

    static {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("admin", "admin");
        provider.setCredentials(AuthScope.ANY, credentials);
        httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
    }

    public static String getUtil(String url) {
        String content=null;
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if(response.getStatusLine().getStatusCode()==200){
                content = EntityUtils.toString(entity );
                System.out.println("haha");
                System.out.println(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static String putUtil(String url,JSONObject content){
        String result=null;
        HttpPut httpPut = new HttpPut(url);

        return result;
    }

}
