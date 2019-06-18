package OrderMR;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.Date;

public class OrderDriver {
  public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
    //实例化配置文件和job
    Configuration conf = new Configuration();
    Job job = Job.getInstance();
    // 添加Jar包
    job.setJarByClass(OrderDriver.class);
    // 设置Mapper和Reducer
    job.setMapperClass(OrderMapper.class);
    job.setReducerClass(OrderReducer.class);
    // 设置Map输出key和value
    job.setMapOutputKeyClass(OrderBean.class);
    job.setMapOutputValueClass(NullWritable.class);
    // 设置最终的输出类型
    job.setOutputKeyClass(OrderBean.class);
    job.setOutputValueClass(NullWritable.class);
    // 设置辅助排序
    job.setGroupingComparatorClass(OrderGroupingComparator.class);
    // 设置分区
    job.setPartitionerClass(OrderPartitioner.class);
    job.setNumReduceTasks(3);
    // 设置输入输出路径
    FileInputFormat.setInputPaths(job, new Path("Day09MapReduce优化案例\\MapReduceCase\\src\\main\\resources\\orderIn"));
    FileOutputFormat.setOutputPath(job, new Path("Day09MapReduce优化案例\\MapReduceCase\\src\\main\\resources\\" + (new Date()).getTime() + "_orderOut"));
    // 启动任务
    boolean result = job.waitForCompletion(true);
    System.out.println(result ? "数据处理成功" : "数据处理失败");
  }
}
