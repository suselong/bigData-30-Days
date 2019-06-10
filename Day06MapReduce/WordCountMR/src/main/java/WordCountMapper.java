import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 思路：单词计数<单词，1>相同的key不变，value加1
 * Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
 * Mapper参数含义
 * KEYIN 数据的起始偏移量，例如：第一行0-10，第二行11-20，起始偏移量就是0,11
 * VALUEIN：输入的数据
 * KEYOUT:Mapper输出到Reduce节点key类型
 * VALUEOUT：Mapper输出到Reduce节点的value类型，例如输出类型为<hello,1>,<along,1>
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
  /**
   * @param key     起始偏移量
   * @param value   输入的数据
   * @param context 输出的数据
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
    //1. 读取数据
    String line = value.toString();
    //2. 切分数据
    String[] words = line.split(" ");
    //3. 循环的写入到下个阶段
    for (String word : words) {
      //4. 输出到reduce节点,输出的类型都是<单词,1> 不会存在<单词,2>
      context.write(new Text(word), new IntWritable(1));
    }
  }
}
