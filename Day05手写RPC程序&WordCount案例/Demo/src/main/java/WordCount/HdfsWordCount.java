package WordCount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 需求：文件内容(hello hello along hello dalong shuai) 统计每隔单词出现的次数，原始数据存储在hdfs中，统计结果存储在hdfs中
 */
public class HdfsWordCount {
  public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, URISyntaxException, InterruptedException {
    // 读取配置文件
    Properties pro = new Properties();
    pro.load(HdfsWordCount.class.getClassLoader().getResourceAsStream("job.properties"));
    Path inPath = new Path(pro.getProperty("IN_PATH"));
    Path outPath = new Path(pro.getProperty("OUT_PATH"));
    Class<?> mapperClass = Class.forName(pro.getProperty("MAPPER_CLASS"));
    // 反射构造Mapper类
    Mapper mapper = (Mapper) mapperClass.newInstance();
    Context context = new Context();
    // 构造HDFS客户端
    Configuration conf = new Configuration();
    FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.128.244.134"), conf, "root");
    // 获取文件列表
    RemoteIterator<LocatedFileStatus> iterator = fileSystem.listFiles(inPath, false);
    while (iterator.hasNext()) {
      // 循环读取一个文件，按照行读取内容
      LocatedFileStatus file = iterator.next();
      FSDataInputStream in = fileSystem.open(file.getPath());
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "utf-8"));
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        // 调用map执行业务逻辑
        mapper.map(line, context);
      }
      // 关闭资源
      bufferedReader.close();
      in.close();
      // 如果用户输入的路径不存在，则创建一个
      if (!fileSystem.exists(outPath)) {
        fileSystem.mkdirs(outPath);
      }
      // 将数据结果放入hdfs中存储
      HashMap<Object, Integer> contextMap = context.getContextMap();
      FSDataOutputStream dataOutputStream = fileSystem.create(outPath);
      // 遍历hashmap
      Set<Map.Entry<Object, Integer>> entrySet = contextMap.entrySet();
      for (Map.Entry<Object, Integer> entry : entrySet) {
        dataOutputStream.write((entry.getKey().toString() + "\t" + entry.getValue() + "\n").getBytes());
      }
      dataOutputStream.close();
      fileSystem.close();
      System.out.println("数据计算并存储完成，存储路径：" + outPath);
    }
  }
}
