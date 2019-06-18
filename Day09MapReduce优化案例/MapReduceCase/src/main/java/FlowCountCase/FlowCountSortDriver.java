package FlowCountCase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.Date;

/**
 * 驱动类，进行任务提交
 */
public class FlowCountSortDriver {
  public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
    // 初始化工作
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf);
    // 设置jar包
    job.setJarByClass(FlowCountSortDriver.class);
    // 设置Mapper和Reducer类
    job.setMapperClass(FlowCountSortMapper.class);
    job.setReducerClass(FlowCountSortReducer.class);
    // 设置Mapper输出key和value
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(FlowSortBean.class);
    //设置Reducer输出key和value
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(FlowSortBean.class);
    // 设置自定义分区类和结果文件，结果文件默认为1
    job.setPartitionerClass(FlowCountPartition.class);
    // 并自定义Reduce个数，需要比分区更大，因为还有除了自定义了还有其他的
    job.setNumReduceTasks(6);
    // 设置输入和输出路径
    FileInputFormat.setInputPaths(job, new Path("Day09MapReduce优化案例\\MapReduceCase\\src\\main\\resources\\flowIn"));
    FileOutputFormat.setOutputPath(job, new Path("Day09MapReduce优化案例\\MapReduceCase\\src\\main\\resources\\" + (new Date()).getTime() + "_flowOut"));
    // 提交任务
    boolean result = job.waitForCompletion(true);
    System.out.println(result ? "数据处理成功" : "数据处理失败");
  }
}
