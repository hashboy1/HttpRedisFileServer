package Common;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import Annotation.ServiceMapping;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;



/*
 * 
 * the tools to get all classes for whole project
 * 

 * 
 * 
 */

public class ClassListUtil {
	
	private String IP;
	private int port;
	
	public ClassListUtil(String IP,int port)
	{
		this.IP=IP;
		this.port=port;
		
	}
	
	
	//only support the save classes in the folder, for it didn't support jar file.
    public  List<Class<?>> getAllClassess(String Path,String PackageName) throws Exception
    {
    	
    	if (Path == null || Path.length()==0)
    	{
    		Path=getClass().getResource("/").getFile().toString();
    		PackageName="";
		
    	}
    	//System.out.println("get path:" +Path);
    	
    	ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
    	File directory = new File(Path);
    	if (!directory.exists())  
    	{
    		System.out.println("unknown path:" +directory.getName());
    		return classes;  
    	}
    		
    	File[] files = directory.listFiles();  
    	for (File file : files) {  
           if (file.isDirectory()) classes.addAll(getAllClassess(file.getPath(),PackageName+"."+file.getName()));    //for path
           else if (file.getName().endsWith(".class") && file.getName().indexOf("$") == -1) 
           {    
        	   String classname=PackageName +"."+ file.getName().substring(0,file.getName().length() - 6);
        	   if (classname.substring(0,1).equals(".")) classname=classname.substring(1);
        	   //System.out.println("Class Name:"+classname);
        	   classes.add(Class.forName(classname));
            }  
        }   
 return classes;
    
    	
    	
    }
    
    
    
    
   	public void registerAllService() throws Exception
   	{
   		
   		 JedisPool pool;   
   		 Jedis jedis;
   		 JedisPoolConfig config;
   		 config= new JedisPoolConfig();
   		 pool = new JedisPool(configer.RedisIp,configer.RedisPort);    
   		 jedis = pool.getResource();
   		 
   		 
   	     List<Class<?>> Lc=getAllClassess("","");
   	     for (Class a :  Lc)
   	     {
   	
   	    	
   	    	Annotation cc=a.getAnnotation(ServiceMapping.class);

   	    	 ServiceMapping sm = (ServiceMapping)a.getAnnotation(ServiceMapping.class); 
   	    	 	 
   	    	 if (sm!=null)
   	    	 {
   	    	 
   	    	 String url = this.IP+":"+this.port+"/"+a.getName();
   	    	 String key = sm.Value();
   	    	 jedis.setex(key,configer.RedisKeyExpiredSeconds,url); //key will be expired over 120s
   	    	 }
   	     }
   		
   	}
   	
    
    

	public static void main(String[] args) throws Exception {
		
		ClassListUtil cl = new ClassListUtil(configer.DefaultHttpIP,configer.DefaultHttpPort);
		cl.registerAllService();
		
		/*
		List<Class<?>> lc= cl.getAllClassess("", "");

		 for (Class a :  lc)
		{
        	try 
			{
        	System.out.println("Class Name:"+a.getName());	
			//JSONRPCBaseService instance1 = (JSONRPCBaseService) a.newInstance();    			
			//System.out.println(instance1.run("test message"));
			}
        	catch (Exception ex)
        	{
        		ex.printStackTrace();
        	}
			
		}
		*/
	}
	

}
