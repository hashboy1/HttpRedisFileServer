# HttpRedisFileServer


˵����1. ��HTTP�ļ�web���񣬷���ʼʱ���������ݶ����ڴ��У��Ժ����еķ��ʾ��������IO�������Ժ���tomcat������������Դ��Ŀ���кܴ������ռ䡣
      2. ����netty�����ܽ��б�д�������һЩ���ϵ����ӡ�

ȱ�ݣ�1��Ŀǰ��Ϊʹ��hashMap����redis,�����迼���ļ�����ʱ�ڴ�������⡣ 
     


Class com.NioHttpFileServer.HttpHashMapFileServer��      ��������ڣ�������������web���񣬲���Ҫ�κ�����������
                                            Ĭ��8888�˿ڿ��Է��ʣ�http://localIP:8888/index.html���������� 
Class com.NioHttpFileServer.HttpHashMapFileServerHandler�������߼��࣬���ڴ�����յ���Ϣ��ķ����ʹ��� 
Class com.NioHttpFileServer.hashmapUti��                  HashMap������
Class com.NioHttpFileServer.HttpFileServer�������ã�����������ڣ�������������web���񣬲���Ҫ�κ�����������
                                            Ĭ��8888�˿ڿ��Է��ʣ�http://localIP:8888/index.html���������� 
Class com.NioHttpFileServer.HttpRedisFileServerHandler�������ã��������߼��࣬���ڴ�����յ���Ϣ��ķ����ʹ��� 
Class com.NioHttpFileServer.fileRedisUtil�����ã���redis����������ϴ�����Ŀ¼�µ��ļ���redis�� 
Class com.NioHttpFileServer.configer�������ļ�������redis��ת���ַ���������
