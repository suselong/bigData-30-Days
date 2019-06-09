import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * 访问RPC服务
 */
public class GetServer {
  public static void main(String[] args) throws IOException {
    //1. 拿到RPC协议
    ClicentNameNodeProtocol proxy = RPC.getProxy(ClicentNameNodeProtocol.class, 1L,
        new InetSocketAddress("localhost", 7777), new Configuration());
    //2. 发送请求
    String metaData = proxy.getMetaData("/along");
    //3. 打印元数据
    System.out.println(metaData);
  }
}
