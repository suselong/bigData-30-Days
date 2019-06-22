package OrderMapJoin;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * 思路：
 * 1. 商品列表较小，可以加到缓存中
 * 2.Mapper阶段是读入数据，然后数据在map端输出前进行替换成商品名称
 * 3.需要重写steup方法把商品表通过输入缓冲流BufferedReader来读入到hashMap中；
 * 4.重写map方法，通过获取到商品id，然后通过id获取到商品名称，拼接到后面，进行输出
 */
public class OrderJoinMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
  /**
   * 存放商品编号和商品名称对应的Map
   */
  private HashMap<String, String> prMap = new HashMap<String, String>();

  /**
   * 初始化
   *
   * @param context 输入的内容
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  protected void setup(Context context) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("Day09MapReduceCase\\MapReduceCase\\src\\main\\resources\\orderJoinIn\\pr_table.txt"));
    // 读取一行数据
    String line;
    while ((line = br.readLine()) != null) {
      // 切割并放到Map中
      String[] splits = line.split(" ");
      prMap.put(splits[0], splits[1]);
    }
    br.close();
  }

  /**
   * Map业务逻辑代码
   *
   * @param key     起始偏移量
   * @param value   单行数据
   * @param context 输出类
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
    // 转换为String并切割字段
    String[] orderStrs = value.toString().split(" ");
    // 拼接商品名称并输出
    context.write(new Text(value.toString() + " " + prMap.get(orderStrs[1])), NullWritable.get());
  }
}
