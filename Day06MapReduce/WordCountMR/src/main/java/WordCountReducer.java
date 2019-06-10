import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * 进行数据汇总，相同的key的值相加
 * 前两个参数是Mapper输出的类型，后面两个参数是Reducer输出的类型
 * 输出的情况为<key,3>,<key,5>
 */
public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
  /**
   * @param key     Mapper输出的key
   * @param values  相同key的value迭代器
   * @param context 输出类型
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
    //1. 统计单词出现的次数
    int sum = 0;
    System.out.println("values:" + values.toString());
    //2. 累加求和
    for (IntWritable value : values) {
      sum += value.get();
    }
    //3. 输出
    context.write(new Text(key), new IntWritable(sum));
  }
}
