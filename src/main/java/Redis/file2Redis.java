package Redis;

import java.io.*;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class file2Redis {
	   
		JedisPool pool;   
		Jedis jedis;   
		
		
		file2Redis()
		{
			
			
			JedisPoolConfig config = new JedisPoolConfig();
			 /*
		        //配置最大jedis实例数
		       config.setMaxTotal(1000);
		        //配置资源池最大闲置数
		       config.setMaxIdle(200);
		        //等待可用连接的最大时间
		       config.setMaxWaitMillis(10000);
		        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
		       config.setTestOnBorrow(true);
			*/
			pool = new JedisPool("192.168.0.160",6379);    
			jedis = pool.getResource();   
		}

        /*
        * 文件转数组
        * */
        public byte[] toByteArray(String path) throws IOException
        {
            if (path == null)
            {
                return null;
            }
            File f = new File(path);
            if (!f.exists())
            {
                return null;
            }
            BufferedInputStream in = null;//创建一个缓冲处理流
            try
            {
                in = new BufferedInputStream(new FileInputStream(f));
                byte[] buffer = new byte[10240];//数组大小应适当大于文件
                int len = 0;
                int i = 0;
                while (-1 != (len = in.read()))//逐个字节读取
                {
                    buffer[i] = (byte) len;//读取到的字节放进数组
                    i++;
                }
                byte[] buffer2 = new byte[i];//新建一个与文件大小相同的数组
                System.arraycopy(buffer, 0, buffer2, 0, i);//复制大数组中有效内容
                return buffer2;
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return null;
            }
            finally
            {
                try
                {
                    if (in != null)
                    {
                        in.close();
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        /*
        * 将文件转成数组存到redis
        * */
        public Boolean setFile(String key, String path)
        {
            if (key == null || key.equals("") || path == null || path.equals(""))
            {
                return false;
            }

            if (jedis != null)
            {
                try
                {   
                	System.out.println("open file:"+path);
                	byte[] b = toByteArray(path);
                    if (b != null)
                    {
                        String json = jedis.set(key.getBytes(), b);
                        if (json != null && json.equals("OK"))
                        {
                            return true;
                        }
                        else
                        {
                            return false;
                        }
                    }
                    else
                    {
                        return false;
                    }

                }
                catch (Exception ex)
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }

        /*
        * 将redis中的数组读取出来转成输入流
        * */
        public InputStream getInputStream(String key)
        {
            if (key == null || key.equals(""))
            {
                return null;
            }
            if (jedis != null)
            {
                try
                {
                    byte[] json = jedis.get(key.getBytes());//取出数组
                    if (json != null && json.length > 0)
                    {
                        try
                        {
                            InputStream inputStream = new ByteArrayInputStream(json);//转流
                            return inputStream;
                        }
                        catch (Exception e)
                        {
                            return null;
                        }
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    return null;
                }
            }
            else
            {
                return null;
            }
            return null;
        }
        
        
        public static void main(String[] args)throws Exception {
       	 
        	file2Redis fru = new file2Redis();
        	fru.setFile("jixiebi.FBX","E:\\workspace\\NioHttpFileServer\\NioHttpFileServer\\files\\jixiebi.FBX");
   		    
   	 }


}