package OrderReducerJoin;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class OrderJoinMapper extends Mapper<LongWritable, Text, Text, OrderJoinBean> {
  @Override
  protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
    OrderJoinBean v = new OrderJoinBean();
    Text k = new Text();
    // 1.区分是两张表格是什么表格
    FileSplit inputSplit = (FileSplit) context.getInputSplit();// hadoop自带的方式获取到文件
    String fileName = inputSplit.getPath().getName(); // 获取文件名称
    // 2.获取数据
    String line = value.toString();
    // 3.区分表格进行不同处理，此时是商品表格
    if (fileName.contains("order.txt")) {
      // 3.1切分字段
      String[] fields = line.split(" ");
      // 3.2封装对象
      v.setOrderId(fields[0]);
      v.setPid(fields[1]);
      v.setAmount(Integer.parseInt(fields[2]));
      v.setpName("");
      v.setFlag("0");
      // 3.3设置k的值 商品id作为k
      k.set(fields[1]);
    } else {// 否则为商品表
      // 3.4切分字段
      String[] fields = line.split(" ");
      // 3.5封装对象
      v.setOrderId("");
      v.setPid(fields[0]);
      v.setAmount(0);
      v.setpName(fields[1]);
      v.setFlag("1");
      // 3.6设置k的值 商品id作为k
      k.set(fields[0]);
    }
    context.write(k, v);
  }
}
