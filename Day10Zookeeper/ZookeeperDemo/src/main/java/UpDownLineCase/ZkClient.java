package UpDownLineCase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
/**
 * @author :FinalLong
 * @date : 2018年11月7日 下午8:43:03
 * @version ：1.0 类说明：服务端
 */
public class ZkClient {
  public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
    // 1.获取连接
    ZkClient zkClient = new ZkClient();
    zkClient.getConnect();
    // 2.监听服务器的节点信息
    zkClient.getServers();
    // 3.业务逻辑，需要一直监听
    zkClient.getWatch();
  }
  // 1.1 连接ip和端口
  private String connectString = "192.168.244.129:2181,192.168.244.130:2181,192.168.244.131:2181";
  // 1.2回话超时时间，单位ms
  private int sessionTimeout = 3000;
  // 1.3 初始化zkcli
  ZooKeeper zkCli = null;
  // 1.连接集群
  public void getConnect() throws IOException {
    zkCli = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
      List<String> children;
      public void process(WatchedEvent event) {
        try {
          // 1.4监听父节点servers
          children = zkCli.getChildren("/servers", true);
          // 1.5创建集合存储服务器列表
          ArrayList<String> serverList = new ArrayList<String>();
          // 1.6获取每个节点的数据
          for (String chil : children) {
            byte[] data = zkCli.getData("/servers/" + chil, true, null);
            serverList.add(new String(data));
          }
          // 1.7打印服务器列表
          System.out.println(serverList);
        } catch (KeeperException | InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
  }
  // 2.监听服务器方法
  public void getServers() throws KeeperException, InterruptedException {
    // 2.1监听服务器的父节点
    List<String> children = zkCli.getChildren("/servers", true);
    ArrayList<String> serverList = new ArrayList<String>();
    // 2.2获取每个节点的数据
    for (String chil : children) {
      byte[] data = zkCli.getData("/servers/" + chil, true, null);
      serverList.add(new String(data));
    }
    // 2.3打印服务器列表
    System.out.println(serverList);
  }
  // 3.加上业务逻辑，获得监听
  public void getWatch() throws InterruptedException {
    Thread.sleep(Long.MAX_VALUE);
  }
}
