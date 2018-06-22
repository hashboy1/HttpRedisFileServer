package Common;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import Annotation.ServiceMapping;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import test.Person;

/*
 * old tools for service register,now it has changed to classListUtil
 * 
 * 
 * 
 * 
 */

public class ClassUtil {
	
 
	
	
	
	
	    private static String packageName = "";
	    
	    public ClassUtil(String varPakageName)
	    {
	    	this.packageName=varPakageName;
	    
	    }
	    
	    
	   
	  
	  
	    public static List<Class<?>> getClasses()  
	            throws ClassNotFoundException, IOException {  
	  
	        ClassLoader classLoader = Thread.currentThread()  
	                .getContextClassLoader();  
	        String path = packageName.replace(".", "/");  
	        System.out.println("path:" + path);  
	        Enumeration<URL> resources = classLoader.getResources(path);  
	        System.out.println("获取资源路径" + resources);  
	        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();  
	        while (resources.hasMoreElements()) {    
	            URL resource = resources.nextElement();  
	            System.out.println("Portocal:"+resource.getProtocol());  
	            System.out.println("Path:"+resource.getPath());  
	  
	            String protocol = resource.getProtocol();  
	  
	            if ("file".equals(protocol)) {  
	                File file = new File(resource.getFile());  
	                classes.addAll(findClasses(file, packageName));  
	            } else if ("jar".equals(protocol)) {  
	                System.out.println("jar类型的扫描");  
	                String jarpath = resource.getPath();  
	                jarpath = jarpath.replace("file:/", "");  
	                jarpath = jarpath.substring(0, jarpath.indexOf("!"));  
	                return getClasssFromJarFile(jarpath, path);  
	            }  
	  
	        }  
	        return classes;  
	    }  
	  
	    private static List<Class<?>> findClasses(File directory, String packageName)  
	            throws ClassNotFoundException {  
	    	System.out.println("directory.exists()=" + directory.exists());  
	    	System.out.println("directory.getName()=" + directory.getName());  
	        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();  
	  
	        if (!directory.exists()) {  
	            return classes;  
	        }  
	  
	        File[] files = directory.listFiles();  
	        for (File file : files) {  
	            if (file.isDirectory()) {  
	                assert !file.getName().contains(".");  
	                classes.addAll(findClasses(file,  
	                        packageName + "." + file.getName()));  
	            } else if (file.getName().endsWith(".class") && file.getName().indexOf("$") == -1) {  
	               
	            	if (packageName ==null && packageName.length()<=0)
	            	{
	            		classes.add(Class.forName(file.getName().substring(0,  
                                file.getName().length() - 6)));  
	            	}
	            	else
	            	{
	            	
	            		
	            		classes.add(Class.forName(packageName  
		                        + "."  
		                        + file.getName().substring(0,  
		                                file.getName().length() - 6)));  
	            	}
	            	}  
	        }  
	        return classes;  
	  
	    }  
	  
	    /** 
	     * 从jar文件中读取指定目录下面的所有的class文件 
	     *  
	     * @param jarPaht 
	     *            jar文件存放的位置 
	     * @param filePaht 
	     *            指定的文件目录 
	     * @return 所有的的class的对象 
	     */  
	    public static List<Class<?>> getClasssFromJarFile(String jarPaht,  
	            String filePaht) {  
	        List<Class<?>> clazzs = new ArrayList<Class<?>>();  
	  
	        JarFile jarFile = null;  
	        try {  
	            jarFile = new JarFile(jarPaht);  
	  
	            List<JarEntry> jarEntryList = new ArrayList<JarEntry>();  
	  
	            Enumeration<JarEntry> ee = jarFile.entries();  
	            while (ee.hasMoreElements()) {  
	                JarEntry entry = (JarEntry) ee.nextElement();  
	                // 过滤我们出满足我们需求的东西  
	                if (entry.getName().startsWith(filePaht)  
	                        && entry.getName().endsWith(".class")) {  
	                    jarEntryList.add(entry);  
	                }  
	            }  
	            for (JarEntry entry : jarEntryList) {  
	                String className = entry.getName().replace('/', '.');  
	                className = className.substring(0, className.length() - 6);  
	  
	                try {  
	                    clazzs.add(Thread.currentThread().getContextClassLoader()  
	                            .loadClass(className));  
	                } catch (ClassNotFoundException e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	        } catch (IOException e1) {  
	        	System.out.println("解析jar包文件异常");  
	        } finally {  
	            if (null != jarFile) {  
	                try {  
	                    jarFile.close();  
	                } catch (Exception e) {  
	                }  
	  
	            }  
	        }  
	        return clazzs;  
	    }  
   
	    
	public void registerAllService() throws Exception
	{
		
		 JedisPool pool;   
		 Jedis jedis;
		 JedisPoolConfig config;
		 config= new JedisPoolConfig();
		 pool = new JedisPool(configer.RedisIp,configer.RedisPort);    
		 jedis = pool.getResource();
		 
		 
	     List<Class<?>> Lc=getClasses();
	     for (Class a :  Lc)
	     {
	    	 ServiceMapping sm = (ServiceMapping)a.getAnnotation(ServiceMapping.class);
	    	 
	    	 if (sm!=null)
	    	 {
	    	 
	    	 String url = configer.DefaultHttpIP+":"+configer.DefaultHttpPort+"/"+a.getName();
	    	 String key = sm.Value();
	    	 jedis.setex(key,configer.RedisKeyExpiredSeconds,url); //key will be expired over 120s
	    	 }
	     }
		
	}
	
/*
 * 
 * 
 * one function to create class by java reflected
 * only support the class which the ancestor is the JSONBaseService
 * 
 * 	
 */
	

	public static String callJSONBaseService(String className,String parameter1,String parameter2) throws Exception
	{
		
		
		Class a = Class.forName(className);
        RPCBaseService instance1 = (RPCBaseService) a.newInstance(); 
        String writecontent= instance1.run(parameter1,parameter2);
        return writecontent;
	}
	
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		
		ClassUtil cu = new ClassUtil("");
		//cu.registerAllService();
		
		
		
		
		
		
		/*
		
		//Added one timer trigger into program running environment
	    Runnable runnable = new Runnable() {  
	        public void run() {  
	            System.out.println("方法三：ScheduledExecutorService定时调用 !!  时间=" + new Date() );  
	            for (Class a :  Lc)
	    		{
	            	try 
	    			{
	            	System.out.println("Class Name:"+a.getName());	
	    			JSONRPCBaseService instance1 = (JSONRPCBaseService) a.newInstance();    			
	    			//System.out.println(instance1.run("test message"));
	    			}
	            	catch (Exception ex)
	            	{
	            		ex.printStackTrace();
	            	}
	    			
	    		}
	        }  
	    };  
	    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();  
	    // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
	    service.scheduleAtFixedRate(runnable, 0, 2, TimeUnit.MINUTES);
		*/
		
		
		
		
	}

}
