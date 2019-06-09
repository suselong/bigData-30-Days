import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

/**
 * HDDFS客户端API DEMO
 *
 * @author along
 * @date 2019-06-09
 */
public class HDFSClientAPI {
  private FileSystem fileSystem = null;

  /**
   * 构造HDFS客户端
   */
  @Before
  public void hdfsClientInit() throws URISyntaxException, IOException, InterruptedException {
    //1. 客户端加载配置文件
    Configuration conf = new Configuration();
    //2. 设置副本数，默认是3
    conf.set("dfs.replication", "2");
    //3. 指定块大小，默认是128M
    conf.set("dfs.blocksize", "64m");
    //4. 构造客户端
    fileSystem = FileSystem.get(new URI("hdfs://192.168.244.134:9000"), conf, "root");
  }

  /**
   * 判断是否文件还是文件夹
   *
   * @throws IOException
   */
  @Test
  public void hdfsFind() throws IOException {
    //1. 获取到状态信息列表
    FileStatus[] listStatus = fileSystem.listStatus(new Path("/"));
    //2. 遍历列表进行判断
    for (FileStatus fileStatus : listStatus) {
      if (fileStatus.isFile()) {
        System.out.println("文件" + fileStatus.getPath().getName());
      } else if (fileStatus.isDirectory()) {
        System.out.println("文件夹" + fileStatus.getPath().getName());
      }
    }
  }

  /**
   * 查询HDFS指定目录信息
   *
   * @throws IOException
   */
  @Test
  public void hdfsLs() throws IOException {
    //1. 返回的是一个远程迭代器，第二个参数表示是否迭代查询文件
    RemoteIterator<LocatedFileStatus> iterator = fileSystem.listFiles(new Path("/"), false);
    while (iterator.hasNext()) {
      LocatedFileStatus status = iterator.next();
      System.out.println("文件路径为：" + status.getPath());
      System.out.println("块大小：" + status.getBlockSize());
      System.out.println("文件长度：" + status.getLen());
      System.out.println("副本数量：" + status.getReplication());
      System.out.println("块信息：" + Arrays.toString(status.getBlockLocations()));
    }
    fileSystem.close();
  }

  /**
   * HDFS中创建文件夹，对应命令 hdfs dfs -mkdir /文件名
   */
  @Test
  public void hdfsMkdir() throws IOException {
    fileSystem.mkdirs(new Path("/mkdirAPI"));
    fileSystem.close();
  }

  /**
   * 读取方式1：直接读取指定字节数据
   *
   * @throws IOException
   */
  @Test
  public void hdfsReadData01() throws IOException {
    //1. 拿到文件流
    FSDataInputStream openFile = fileSystem.open(new Path("/test.txt"));
    //2. 定义一个字节数组用于接收流
    byte[] buf = new byte[1024];
    //3. 读取文件到字节数组
    openFile.read(buf);
    //4. 打印，因返回的是字节数组，需要转换为String
    System.out.println(new String(buf));
    //5. 关闭文件和HDFS
    openFile.close();
    fileSystem.close();

  }

  /**
   * HDFS移动/修改文件
   *
   * @throws IOException
   */
  @Test
  public void hdfsRename() throws IOException {
    fileSystem.rename(new Path("/oldReFile.txt"), new Path("/mkdirAPI/newReFile.txt"));
    fileSystem.close();
  }
  //三种常用的读取HDFS中的数据

  /**
   * HDFS删除文件或文件夹
   *
   * @throws IOException
   */
  @Test
  public void hdfsRm() throws IOException {
    // 参数2，表示是否递归删除
    fileSystem.delete(new Path("/mkdirAPI/newReFile.txt"), true);
    fileSystem.close();
  }

}
