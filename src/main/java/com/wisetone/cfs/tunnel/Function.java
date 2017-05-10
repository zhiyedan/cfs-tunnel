package com.wisetone.cfs.tunnel;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by zhiyedan on 5/5/17.
 */
public class Function {

    public static String ODLIP = "http://192.168.0.110:8181/";
    public static String PREURL = "restconf/config/network-topology:network-topology/topology/ovsdb:1/node/";
    /*
    * 获取整个网络的拓扑结构--截止到node层
    * */
    public static void getTopo(){
        String url = ODLIP+"restconf/operational/network-topology:network-topology/";
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
    * active connect to OVS host
    * @param name:ovs name
    * @param remoteIp : the ovs host ip
    * */
    public static void setOVSNodeId(String name,String remoteIp){
        //TODO name查重
        String nameINURL = name.replaceAll("/","%2F");
        String url = ODLIP+PREURL+nameINURL;
        String body = "{\"network-topology:node\":[{\"node-id\":\""+name+"\",\"connection-info\":{\"ovsdb:remote-port\":\"6640\",\"ovsdb:remote-ip\":\""+remoteIp+"\"}}]}";
        System.out.println(body);
        ODLRestUtil.putUtil(url,body);
    }

    /*
    * add a bridge on OVS
    * @param name : bridge name
    * @param nodeID
    * */
    public static void addBridge(String ovsNodeId,String bridgeName){
        String nodeId = ovsNodeId+"/bridge/"+bridgeName;
        String nodeIdURL = nodeId.replaceAll("/","%2F");
        String url = ODLIP+PREURL+nodeIdURL;
        String JsonBody = "{\"network-topology:node\":[{\"node-id\":\""+nodeId+"\",\"ovsdb:bridge-name\":\""+bridgeName
                +"\",\"ovsdb:protocol-entry\":[{\"protocol\": \"ovsdb:ovsdb-bridge-protocol-openflow-13\"}]," +
                "\"ovsdb:managed-by\": \"/network-topology:network-topology/network-topology:topology[network-topology:topology-id='ovsdb:1']" +
                "/network-topology:node[network-topology:node-id='"+ovsNodeId+"']\"}]}";
        ODLRestUtil.putUtil(url,JsonBody);
    }

    /*
    * delete bridge
    * @param bridgeId: the bridge node-id
    * */
    public static void deleteBridge(String bridgeId){
        String bridgeIdURL = bridgeId.replaceAll("/","%2F");
        String url = ODLIP+PREURL+bridgeIdURL;
        ODLRestUtil.deleteUtil(url);
    }

    /*
    * 添加 GRE tunnel port
     *
    * */
    public static void addGRETunnelPort(String bridgeNodeId,String remoteIP,String portName){
        String nodeIdInURL = bridgeNodeId.replaceAll("/","%2F");
        String url = ODLIP+PREURL+nodeIdInURL+"/termination-point/"+portName;
        String jsonBody = "{\"network-topology:termination-point\":[{\"ovsdb:name\":\""+portName+"\",\"ovsdb:interface-type\":\"ovsdb:interface-type-gre\"," +
                "\"tp-id\":\""+portName+"\",\"ovsdb:options\":[{\"ovsdb:option\":\"remote_ip\",\"ovsdb:value\":\""+remoteIP+"\"}]}]}";
        ODLRestUtil.putUtil(url,jsonBody);
    }
    /*
    * 添加 Ipsec-GRE tunnel port
     *
    * */
    public static void addIpsecTunnelPort(String bridgeNodeId,String remoteIP,String portName){
        String nodeIdInURL = bridgeNodeId.replaceAll("/","%2F");
        String url = ODLIP+PREURL+nodeIdInURL+"/termination-point/"+portName;
        String jsonBody = "{\"network-topology:termination-point\":[{\"ovsdb:name\":\""+portName+"\",\"ovsdb:interface-type\":\"ovsdb:interface-type-ipsec-gre\"," +
                "\"tp-id\":\""+portName+"\",\"ovsdb:options\":[{\"ovsdb:option\":\"remote_ip\",\"ovsdb:value\":\""+remoteIP+"\"}," +
                "{\"ovsdb:option\":\"psk\",\"ovsdb:value\":\"test123\"}]}]}";
        ODLRestUtil.putUtil(url,jsonBody);
    }
    /*
    * delete tunnel
    * */
    public static void deleteTunnel(String bridgeNodeId,String portID){
        String nodeIDURL = bridgeNodeId.replaceAll("/","%2F");
        String url = ODLIP+PREURL+nodeIDURL+"/termination-point/"+portID;
        ODLRestUtil.deleteUtil(url);
    }
}
