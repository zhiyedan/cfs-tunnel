package com.wisetone.cfs.tunnel;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by zhiyedan on 5/5/17.
 */
public class Function {

    public void getTopo(){
        String url = "http://192.168.0.110:8181/restconf/operational/network-topology:network-topology/";
        String result = ODLRestUtil.getUtil(url);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject netTopo = (JSONObject) jsonObject.get("network-topology");
        JSONArray topos = netTopo.getJSONArray("topology");
        for (int i = 0; i < topos.length(); i++) {
            JSONObject topo = topos.getJSONObject(i);
            String topoID = topo.getString("topology-id");
            System.out.println("topology-id:"+topoID);
            if (topo.has("node")) {
                JSONArray nodes = topo.getJSONArray("node");
                for (int j = 0; j < nodes.length(); j++) {
                    JSONObject node = nodes.getJSONObject(j);
                    System.out.println(node.toString());
                    String nodeID = node.getString("node-id");
                    System.out.println("node-id:"+nodeID);
                }
            }
        }
    }
}
