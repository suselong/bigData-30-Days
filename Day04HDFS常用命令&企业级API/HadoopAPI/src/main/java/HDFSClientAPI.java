import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
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
    // 可以设置偏移量，可以可以不设置
    openFile.seek(200);
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
   * 读取数据方式2：缓冲流方式读取，效率高
   *
   * @throws IOException
   */
  @Test
  public void hdfsReadData02() throws IOException {
    //1. 拿到文件流
    FSDataInputStream openFile = fileSystem.open(new Path("/test.txt"));
    //2. 缓冲流，效率高
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openFile));
    String line;
    //3. 按行读取数据
    while ((line = bufferedReader.readLine()) != null) {
      System.out.println(line);
    }
    //4. 关闭资源
    bufferedReader.close();
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

  /**
   * 写入数据方式1：字节方式写入
   * @throws IOException
   */
  @Test
  public void hdfsWriteData01() throws IOException {
    //1. 定义输出流
    FSDataOutputStream outFile = fileSystem.create(new Path("/test.txt"), false);
    //2. 定义输入流
    FileInputStream inFile = new FileInputStream("E:\\testData.txt");
    //3. 字节数组
    byte[] bytes = new byte[1024];
    //4. 按行读取出来写入输出流
    int read;
    while ((read = inFile.read(bytes)) != -1) {
      outFile.write(bytes, 0, read);
    }
    //5. 关闭
    inFile.close();
    outFile.close();
    fileSystem.close();
  }

  /**
   * 写入数据方式2：缓冲流写入
   * @throws IOException
   */
  @Test
  public void hdfsWriteData02() throws IOException {
    FSDataOutputStream outFile = fileSystem.create(new Path("/test.txt"), false);
    FileInputStream inFile = new FileInputStream(new File("E:\\testData.txt"));
    outFile.write(inFile.toString().getBytes());
    // 关闭流，这里关闭HDFS比较特殊，需要使用IOUtils进行关闭
    IOUtils.closeStream(outFile);
    inFile.close();
    fileSystem.close();
  }
}
