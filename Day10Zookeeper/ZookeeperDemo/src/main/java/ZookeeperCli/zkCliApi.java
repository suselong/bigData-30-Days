package ZookeeperCli;
/**
 * @author :FinalLong
 * @version ：1.0
 * 类说明
 */
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;
public class zkCliApi {
  /**
   * 客户端地址:使用请换成自己的集群ip
   */
  private String connectString = "192.168.244.129:2181,192.168.244.130:2181,192.168.244.131:2181";
  /**
   * 连接超时时间,ms
   */
  private int sessionTimeout = 3000;
  /**
   * 初始化watcher，进行监控回调
   */
  private Watcher wh =new Watcher(){
    //监控回调
    public void process(WatchedEvent event){
      System.out.println(event.getPath()+"\t"+event.getState()+"\t"+event.getType());
    }
  };
  /**
   * 初始化客户端
   */
  private ZooKeeper zkCli=null;
  @Before
  public void init() throws IOException, KeeperException, InterruptedException {
    zkCli = new ZooKeeper(connectString, sessionTimeout, wh);
    List<String> children = zkCli.getChildren("/", true);
    for(String chil:children) {
      System.out.println(chil);
    }
  }
  /**
   * 创建子节点
   * @throws KeeperException
   * @throws InterruptedException
   */
  @Test
  public void createZnode() throws KeeperException, InterruptedException {
    String path = zkCli.create("/along005", "chihuoguo".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    String path01 = zkCli.create("/along006", "chihuoguole".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

    System.out.println(path);
    System.out.println(path01);
  }

  /**
   * 获取子节点
   * @throws KeeperException
   * @throws InterruptedException
   */
  @Test
  public void getChild() throws KeeperException, InterruptedException {
    List<String> children = zkCli.getChildren("/", true);

    for(String chil:children) {
      System.out.println(chil);
    }
    Thread.sleep(Long.MAX_VALUE);
  }

  /**
   * 删除节点
   * @throws KeeperException
   * @throws InterruptedException
   */
  @Test
  public void rmChildDate() throws KeeperException, InterruptedException {
    zkCli.delete("/along006", -1);
  }
  /**
   * 修改数据
   * @throws KeeperException
   * @throws InterruptedException
   */
  @Test
  public void setData() throws KeeperException, InterruptedException {
    zkCli.setData("/along005", "quchegnduchihuoguo".getBytes(), -1);
    byte[] data = zkCli.getData("/along005", true, null);
    System.out.println(Arrays.toString(data));
  }
  /**
   * 判断节点是否存在
   * @throws KeeperException
   * @throws InterruptedException
   */
  @Test
  public void testExist() throws KeeperException, InterruptedException {
    Stat exists = zkCli.exists("/along005", false);
    System.out.println(exists == null ? "not exists":"exist");
  }
}
