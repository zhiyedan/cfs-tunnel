package com.wisetone.cfs.tunnel;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by zhiyedan on 5/5/17.
 */
public class Function {

    public static String odlIP = "http://192.168.0.110:8181/";
    /*
    * 获取整个网络的拓扑结构--截止到node层
    * */
    public static void getTopo(){
        String url = odlIP+"restconf/operational/network-topology:network-topology/";
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
    /*
    * 添加 Ipsec-GRE tunnel port
     *
    * */
    public static void addgreTunnelPort(String nodeId,String remoteIP){
        String nodeIdInURL = nodeId.replaceAll("/","%2F");
        String url = odlIP+"restconf/config/network-topology:network-topology/topology/ovsdb:1/node/"+nodeIdInURL+"/termination-point/gre/";
        JSONObject content = new JSONObject();
        JSONArray terminalPoints = new JSONArray();
        JSONObject terminalPoint = new JSONObject();
        terminalPoint.put("ovsdb:name","gre");
        terminalPoint.put("ovsdb:interface-type","ovsdb:interface-type-gre");
        terminalPoint.put("tp-id","gre");
        JSONArray options = new JSONArray();
        JSONObject option0 = new JSONObject();
        option0.put("ovsdb:option","remote_ip");
        option0.put("ovsdb:value",remoteIP);
        JSONObject option1 = new JSONObject();
        option1.put("ovsdb:option","psk");
        option1.put("ovsdb:value","test123");
        options.put(0,option0);
        options.put(1,option1);
        terminalPoint.put("ovsdb:options",options);
        terminalPoints.put(0,terminalPoint);
        content.put("network-topology:termination-point",terminalPoints);
        System.out.println(content.toString());
        ODLRestUtil.putUtil(url,content);
    }
    public static void addipsecTunnelPort(String nodeId,String remoteIP){
        String nodeIdInURL = nodeId.replaceAll("/","%2F");
        String url = odlIP+"restconf/config/network-topology:network-topology/topology/ovsdb:1/node/"+nodeIdInURL+"/termination-point/ipsec-gre/";
        JSONObject content = new JSONObject();
        JSONArray terminalPoints = new JSONArray();
        JSONObject terminalPoint = new JSONObject();
        terminalPoint.put("ovsdb:name","ipsec-gre");
        terminalPoint.put("ovsdb:interface-type","ovsdb:interface-type-ipsec-gre");
        terminalPoint.put("tp-id","ipsec-gre");
        JSONArray options = new JSONArray();
        JSONObject option0 = new JSONObject();
        option0.put("ovsdb:option","remote_ip");
        option0.put("ovsdb:value",remoteIP);
        JSONObject option1 = new JSONObject();
        option1.put("ovsdb:option","key");
        option1.put("ovsdb:value","123");
        options.put(0,option0);
        options.put(1,option1);
        terminalPoint.put("ovsdb:options",options);
        terminalPoints.put(0,terminalPoint);
        content.put("network-topology:termination-point",terminalPoints);
        System.out.println(content.toString());
        ODLRestUtil.putUtil(url,content);
    }
}
