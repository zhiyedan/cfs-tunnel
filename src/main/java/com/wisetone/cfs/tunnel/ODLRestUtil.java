package com.wisetone.cfs.tunnel;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

/**
 * Created by zhiyedan on 5/5/17.
 */
public class ODLRestUtil {
    JSONObject jsonObject = new JSONObject();

    {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("admin", "admin");
        provider.setCredentials(AuthScope.ANY,credentials);
        HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
    }

}
