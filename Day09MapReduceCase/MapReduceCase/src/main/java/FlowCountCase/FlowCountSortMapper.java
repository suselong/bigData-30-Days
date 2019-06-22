package FlowCountCase;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 进行字符串切割，输出的数据类型是自定义的FlowSortBean
 * 对流量统计的结果进行分区并且区内排序
 *
 */
public class FlowCountSortMapper extends Mapper<LongWritable, Text, Text, FlowSortBean> {
  @Override
  protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
    String[] words = value.toString().split("\t");
    String upFlow = words[1];
    String dwFlow = words[2];
    context.write(new Text(words[0]),new FlowSortBean(Long.parseLong(upFlow),Long.parseLong(dwFlow)));
  }
}
