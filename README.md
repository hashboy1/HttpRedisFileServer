# HttpRedisFileServer


说明：1. 供HTTP文件web服务，服务开始时将所有数据读入内存中，以后所有的访问均脱离磁盘IO，性能略好于tomcat，比起其他开源项目还有很大提升空间。
      2. 采用netty网络框架进行编写，借鉴了一些网上的例子。

缺陷：1、目前改为使用hashMap代替redis,但是需考虑文件过多时内存溢出问题。 
     


Class com.NioHttpFileServer.HttpHashMapFileServer：      主程序入口，可以用来启动web服务，不需要任何其他容器，
                                            默认8888端口可以访问：http://localIP:8888/index.html来访问数据 
Class com.NioHttpFileServer.HttpHashMapFileServerHandler：核心逻辑类，用于处理接收到消息后的分析和处理 
Class com.NioHttpFileServer.hashmapUti：                  HashMap操作类
Class com.NioHttpFileServer.HttpFileServer（已弃用）：主程序入口，可以用来启动web服务，不需要任何其他容器，
                                            默认8888端口可以访问：http://localIP:8888/index.html来访问数据 
Class com.NioHttpFileServer.HttpRedisFileServerHandler（已弃用）：核心逻辑类，用于处理接收到消息后的分析和处理 
Class com.NioHttpFileServer.fileRedisUtil已弃用）：redis操作类可以上传整个目录下的文件到redis里 
Class com.NioHttpFileServer.configer：配置文件，包含redis和转码字符集的设置
