package FlowCountCase;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 进行字符串切割，输出的数据类型是自定义的FlowBean
 *
 */
public class FlowCountMapper extends Mapper<LongWritable, Text, Text, FlowSortBean> {
  @Override
  protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
    String[] words = value.toString().split("\t");
    String phoneNum = words[1];
    String upFlow = words[words.length - 3];
    String dwFlow = words[words.length - 2];
    context.write(new Text(phoneNum),new FlowSortBean(Long.parseLong(upFlow),Long.parseLong(dwFlow)));
  }
}
