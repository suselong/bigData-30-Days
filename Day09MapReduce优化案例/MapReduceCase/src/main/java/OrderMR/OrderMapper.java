package OrderMR;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Mapper类
 */
public class OrderMapper extends Mapper<LongWritable, Text, OrderBean, NullWritable> {
  @Override
  protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
    // 拿到第一行数据
    String line = value.toString();
    // 切分
    String[] split = line.split(" ");
    // 获取关键数据
    int orderId = Integer.parseInt(split[0]);
    double price = Double.parseDouble(split[2]);
    // 写出数据
    context.write(new OrderBean(orderId, price), NullWritable.get());
  }
}
