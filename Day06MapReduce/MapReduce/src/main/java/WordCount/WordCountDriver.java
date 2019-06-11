package WordCount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class WordCountDriver {
  public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

    //1. 实例化配置对象
    Configuration conf = new Configuration();

    //2. 实例化job对象
    Job job = Job.getInstance();

    //3. 设置jar包，也就是驱动类
    job.setJarByClass(WordCountDriver.class);

    //4. 设置自定义mapper类和reducer类
    job.setMapperClass(WordCountMapper.class);
    job.setReducerClass(WordCountReducer.class);

    //5. 设置map的输出数据类型
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(IntWritable.class);

    //6. 设置reducer的输出类型,也就是最终的输出类型
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);

    //7. 设置数据输入路径和处理后结果存放路径
    FileInputFormat.setInputPaths(job, "Day06MapReduce\\WordCountMR\\src\\main\\resources\\inpath");
    FileOutputFormat.setOutputPath(job, new Path("Day06MapReduce\\WordCountMR\\src\\main\\resources\\outpath"));

    //8. 提交任务
    job.waitForCompletion(true);
    System.out.println("计算并输出完成");
  }
}
