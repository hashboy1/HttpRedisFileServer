package Common;

public class configer {
      //redis configration  
	  public static String RedisIp = "192.168.0.160";
      public static int RedisPort = 6379;
      public static int RedisKeyExpiredSeconds = 120;
      //zookeeper configuration (option)
      
      
      //http service port
      public static String DefaultHttpIP="192.168.0.160";
      public static int DefaultHttpPort=8888;
       
      //encode configuration
      public static String encoding = "ISO-8859-1";
      
      //path for HashMapFileServer
      public static String sourcePath="C:\\Users\\Administrator\\Desktop\\cache\\";
      
      //service package configuration
      public static String ServicePackage="JSONRPC.Service";
}
