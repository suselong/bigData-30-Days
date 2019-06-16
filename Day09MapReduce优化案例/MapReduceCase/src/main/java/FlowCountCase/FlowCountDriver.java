package FlowCountCase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
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
    job.setMapOutputValueClass(FlowSortBean.class);
    //设置Reducer输出key和value
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(FlowSortBean.class);
    // 设置自定义分区类和结果文件，结果文件默认为1
    job.setPartitionerClass(FlowCountPartition.class);
    // 并自定义Reduce个数，需要比分区更大，因为还有除了自定义了还有其他的
    job.setNumReduceTasks(6);

    // 设置文件输入处理方法，默认是TextInputFormat，需要设置小文件合并为大文件最大最小值
    job.setInputFormatClass(CombineTextInputFormat.class);
    CombineTextInputFormat.setMaxInputSplitSize(job, 4194304);
    CombineTextInputFormat.setMinInputSplitSize(job, 3145728);

    // 设置输入和输出路径
    FileInputFormat.setInputPaths(job, new Path("wc/flowIn"));
    FileOutputFormat.setOutputPath(job, new Path("wc/flowOut"));
    // 提交任务
    boolean result = job.waitForCompletion(true);
    System.out.println(result ? "数据处理成功" : "数据处理失败");
  }
}
