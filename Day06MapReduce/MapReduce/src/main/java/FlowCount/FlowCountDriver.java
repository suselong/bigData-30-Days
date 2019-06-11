package FlowCount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 驱动类，进行任务提交
 */
public class FlowCountDriver {
  public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
    // 初始化工作
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf);
    // 设置jar包
    job.setJarByClass(FlowCountDriver.class);
    // 设置Mapper和Reducer类
    job.setMapperClass(FlowCountMapper.class);
    job.setReducerClass(FlowCountReducer.class);
    // 设置Mapper输出key和value
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(FlowBean.class);
    //设置Reducer输出key和value
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(FlowBean.class);
    // 设置输入和输出路径
    FileInputFormat.setInputPaths(job, new Path("wc/flowIn"));
    FileOutputFormat.setOutputPath(job, new Path("wc/flowOut"));
    // 提交任务
    boolean result = job.waitForCompletion(true);
    System.out.println(result ? "数据处理成功" : "数据处理失败");
  }
}
