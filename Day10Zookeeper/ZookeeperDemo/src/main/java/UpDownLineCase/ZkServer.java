package UpDownLineCase;
/**
 * @author :FinalLong
 * @version ：1.0
 * 类说明:服务端
 */
import java.io.IOException;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
public class ZkServer {
  public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
    // 1.连接zkServer
    ZkServer zkServer = new ZkServer();
    zkServer.getConnect();
    // 2.注册节点信息，服务器ip添加到zk中
    zkServer.regist(args[0]);
    // 3.业务逻辑处理
    zkServer.build(args[0]);
  }
  // 1.1 连接ip和端口
  private String connectString = "192.168.244.129:2181,192.168.244.130:2181,192.168.244.131:2181";
  // 1.2回话超时时间，单位ms
  private int sessionTimeout = 3000;
  // 1.3 初始化zkcli
  ZooKeeper zkCli = null;
  // 2.1 定义一个父节点
  private String parentNode = "/servers";
  // 1.连接zkServer方法
  public void getConnect() throws IOException {
    zkCli = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
      public void process(WatchedEvent event) {
        // TODO Auto-generated method stub
      }
    });
  }
  // 2.注册节点信息
  public void regist(String hostname) throws KeeperException, InterruptedException {
    // acl：应答方式,createMode：创建节点方式
    String node = zkCli.create(parentNode + "/server", hostname.getBytes(), Ids.OPEN_ACL_UNSAFE,
        CreateMode.EPHEMERAL_SEQUENTIAL);
    System.out.println(node);
  }
  // 3.构建服务端
  public void build(String hostname) throws InterruptedException {
    System.out.println(hostname + ":服务器上线了.");
    Thread.sleep(Long.MAX_VALUE);
  }
}
