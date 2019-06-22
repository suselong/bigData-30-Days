package OrderMapJoin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.Date;

public class OrderJoinDriver {
  public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
    // 1.获取job信息
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf);
    // 2.获取jar包
    job.setJarByClass(OrderJoinDriver.class);
    // 3.获取自定义的mapper,不需要Reducer
    job.setMapperClass(OrderJoinMapper.class);
    // 4.设置map输出的数据类型
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(NullWritable.class);
    // 5.不需要reduce，因此设置reduceTask为0
    job.setNumReduceTasks(0);
    // 6.设置输入存在的路径与处理后的结果路径
    FileInputFormat.setInputPaths(job, new Path("Day09MapReduceCase\\MapReduceCase\\src\\main\\resources\\orderJoinIn\\order"));
    FileOutputFormat.setOutputPath(job, new Path("Day09MapReduceCase\\MapReduceCase\\src\\main\\resources\\" + (new Date()).getTime() + "_orderJoinOut"));
    // 7.提交任务
    boolean rs = job.waitForCompletion(true);
    System.out.println(rs ? 0 : 1);
  }
}
