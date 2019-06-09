import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;

/**
 * 启动RPC服务
 */
public class PublishServer {
  public static void main(String[] args) throws IOException {
    //1. 构建RPC框架
    RPC.Builder builder = new RPC.Builder(new Configuration());
    //2. 绑定地址
    builder.setBindAddress("localhost");
    //3. 绑定端口
    builder.setPort(7777);
    //4. 绑定协议
    builder.setProtocol(ClicentNameNodeProtocol.class);
    //5. 调用协议实现类
    builder.setInstance(new MyNameNode());
    //6. 创建服务
    RPC.Server server = builder.build();
    //7. 启动服务
    server.start();
  }
}
