package com.NioHttpFileServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class fileRedisUtil {
	
	    JedisPool pool;   
		Jedis jedis;
		JedisPoolConfig config;
		
	fileRedisUtil()
	{
		
		 config= new JedisPoolConfig();
		 pool = new JedisPool(configer.RedisIp,configer.RedisPort);    
		 jedis = pool.getResource();
		
		
	}
	
	

	public String readBinaryFile(String filePath) throws Exception
	{   
		
		//String encoding = "UTF-8";     UTF-8 is not good choice
		File file = new File(filePath);  
        Long filelength = file.length();  
        byte[] filecontent = new byte[filelength.intValue()];  
        try {  
            FileInputStream in = new FileInputStream(file);  
            in.read(filecontent);  
            in.close();  
        	//System.out.println(new String(filecontent, encoding));
           
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        
        return new String(filecontent, configer.encoding);
	
	}
	
	public void setRedisKey(String key, String value)
	{
		
		jedis.set(key,value);
	}
	
	public String getRedisKey(String key)
	{
		return jedis.get(key);
		
	}
	
	public Set<String> List()
	{
		
				
		Set<String> st= jedis.keys("*");
		
		return st;
		
		
	}
	
	public void deleteAllKeys()
	{
		 Set<String>  keyList= List();   
		for (String f : keyList) {
			jedis.del(f);
	        	}
	}
	
	public void putFolder(String path,String keyroot) throws Exception
	{
		
		if (path.length()== 0)
		{
			System.out.println("empty path");
			return;
		}
		   File f=new File(path);

		   if(f!=null){
	            if(f.isDirectory()){
	                File[] fileArray=f.listFiles();
	                if(fileArray!=null){
	                    for (int i = 0; i < fileArray.length; i++) {
	                        
	                    	
	                    	if (!fileArray[i].isDirectory()) 
	                    		//putFolder(fileArray[i].getPath());
	                    	{
	                    	String filecontent=readBinaryFile(fileArray[i].getPath());
	                    	String key = fileArray[i].getPath().replace("\\", "/");
	                    	setRedisKey(keyroot+fileArray[i].getName(), filecontent);
	                    	}
	                    	}
	                }
	            }
	            else{
	                System.out.println(f);
	            }
	        }
	    
	
		
		
	}
	
	 public static void main(String[] args)throws Exception {
	 

		   
			  
		
		   /*
		  fileRedisUtil fru = new fileRedisUtil();
		  String filecontent=fru.readBinaryFile("E:\\workspace\\NioHttpFileServer\\NioHttpFileServer\\files\\shenzhen.vrkb");
		  fru.setRedisKey("/files/jixiebi.FBX", filecontent);
		   
		   String writeString=jedis.get("jixiebi.FBX");
		   byte[] writecontent=writeString.getBytes("ISO-8859-1");
			//writefile
			//byte[] writecontent=filecontent.getBytes("ISO-8859-1");
			//byte[] writecontent=filecontent.getBytes("UTF-8");
			DataOutputStream out=new DataOutputStream(new FileOutputStream("d:\\1.vrkb"));
			
			out.write(writecontent);
			out.close();
			*/
		 //http://192.168.0.160:8888/files/40coupe.mtl
		 
		//delete all key	
		 /*
		   fileRedisUtil fru = new fileRedisUtil();
		  
		   */
		 //upload file to redis
		 
		   fileRedisUtil fru = new fileRedisUtil();
		   
		   //fru.deleteAllKeys();
		   
		   fru.putFolder("E:\\mycat\\bin\\","/maycat/");
		   System.out.println(fru.List().toString());
		  
		   
		   /*  //upload and donwload file
		  String filecontent=fru.readBinaryFile("E:\\workspace\\NioHttpFileServer\\NioHttpFileServer\\files\\40coupe.mtl");
		  fru.setRedisKey("/files/40coupe.mtl", filecontent);
		  //verify the file
		  String writeString = fru.getRedisKey("/files/40coupe.mtl");
		  byte[] writecontent=writeString.getBytes("ISO-8859-1");
		  DataOutputStream out=new DataOutputStream(new FileOutputStream("d:\\1.FBX"));
		  out.write(writecontent);
		  out.close();
			*/
	 }
}
