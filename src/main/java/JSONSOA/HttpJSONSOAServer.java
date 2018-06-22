package JSONSOA;


import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;


import Common.ClassListUtil;
import Common.configer;

/*
 * 
 * all services will be registered in redis
 * 
 * 
 */

class HttpJSONSOAServer {
    private static final String DEFAULT_URL = "/src/";
    
    public void run(final String IP,final int port, final String url)throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
        	
        	//call the service registers
        	
    		
        	//added one timer to register service  
    		Runnable runnable = new Runnable() {  
    	        public void run() {  
    	            System.out.println("services registed,current time stamp:" + new Date() );  
    	           
    	            		ClassListUtil cu = new ClassListUtil(IP,port);
    	            		try {
								cu.registerAllService();
							} catch (Exception e) {
								e.printStackTrace();
							}
    	        }  
    	    };  
    	    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();  
    	    // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
    	    service.scheduleAtFixedRate(runnable, 1, 120, TimeUnit.SECONDS);
    		
    		
    	    
    	    //web service startup
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                    ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                    ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                    ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                    ch.pipeline().addLast("fileServerHandler", new HttpJSONSOAServerHandler(url));
                }
            });              
            ChannelFuture f = b.bind(IP, port).sync();
            System.out.println("HTTP 文件服务器启动, 地址是： " + "http://"+ IP +":" + port + url);
            f.channel().closeFuture().sync();
            
        }finally{
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    
    public static void main(String[] args) throws Exception {
        String IP = configer.DefaultHttpIP;
    	int port = configer.DefaultHttpPort;
        if(args.length > 0)
        {
            try{
            	IP = args[0];
                port = Integer.parseInt(args[1]);
            }catch(NumberFormatException e){
            	IP = configer.DefaultHttpIP;
                port = configer.DefaultHttpPort;
            }
        }
        
        //String url = DEFAULT_URL;
        String url = "/index.html";
     
        new HttpJSONSOAServer().run(IP,port, url);
    }
}

