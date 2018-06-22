# HttpRedisFileServer


说明：1. 供HTTP文件web服务，服务开始时将所有数据读入内存中，以后所有的访问均脱离磁盘IO，性能略好于tomcat，比起其他开源项目还有很大提升空间。
      2. 采用netty网络框架进行编写，借鉴了一些网上的例子。
      3. 提供RPC服务，请求HTTP连接返回JSON字符串。

缺陷：1、目前改为使用hashMap代替redis,但是需考虑文件过多时内存溢出问题。 
     
CLass JSONSOA.HttpSOARPCServer             RPC服务主程序，扫描工程中所有引用@ServiceMapping注解的类进行注册，并发布服务。注所有应用注解的因继承与
                                            Common.RPCBaseService，并重写run方法。
Interface Annotation.ServiceMapping         服务注解
Class ClassListUtil.ClassListUtil           类遍历与服务注册类，后期应加入对于jar包的支持。
Class HashMap.HttpHashMapFileServer：       主程序入口，可以用来启动web服务，不需要任何其他容器，
                                            默认8888端口可以访问：http://localIP:8888/index.html来访问数据 
Class HashMap.HttpHashMapFileServerHandler：核心逻辑类，用于处理接收到消息后的分析和处理 
Class HashMap.hashmapUti：                  HashMap操作类
Class Redis.HttpFileServer（已弃用）：主程序入口，可以用来启动web服务，不需要任何其他容器，
                                            默认8888端口可以访问：http://localIP:8888/index.html来访问数据 
Class Redis.HttpRedisFileServerHandler（已弃用）：核心逻辑类，用于处理接收到消息后的分析和处理 
Class Redis.fileRedisUtil已弃用）：redis操作类可以上传整个目录下的文件到redis里 
Class Common.configer：配置文件，包含redis和转码字符集的设置
