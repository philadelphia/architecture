package com.delta.smt.api;

public class API {
    //public static String BASE_URL = "http://CNBJDRCNB022:8081/";
    //String BASE_URL = "http://172.17.52.43:8081/";
    //更新配置文件update.json url
    public static final String bundleJsonUrl = "http://172.22.34.45:8809/mobile/DG3_Release/update.json";
   // public static String WebSocketURl = "ws://172.22.35.241:9090";
    //public static final String WebSocketURl = "ws://172.22.35.176:9090/websocket";
    //public static  String BASE_URL = "http://dg3smt-server.delta.corp:8081/";
    // public static  String BASE_URL = "http://172.22.34.37:8081/";
    public static String IP = "dg3smt-server.delta.corp";
    //public static String IP = "172.22.40.36";
    public static String PORT = "8081";
    public static String SOCKET_PORT="9090";
    public static String BASE_URL = "http://" + IP + ":" + PORT + "/";
    public static String WebSocketURl = "ws://"+IP+":"+SOCKET_PORT;

}
