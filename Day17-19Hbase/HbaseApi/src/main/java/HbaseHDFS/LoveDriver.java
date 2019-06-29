package HbaseHDFS;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * @author CDMloong
 */
public class LoveDriver implements Tool {
  private Configuration conf;

  public static void main(String[] args) throws Exception {
    ToolRunner.run(new LoveDriver(), args);
  }  public void setConf(Configuration conf) {
    this.conf = HBaseConfiguration.create(conf);
  }

  public int run(String[] strings) throws Exception {
    //1.创建一个任务job
    Job job = Job.getInstance(conf);
    job.setJarByClass(LoveDriver.class);
    //2.配置Mapper
    job.setMapperClass(ReadLoveFromHDFSMapper.class);
    job.setMapOutputKeyClass(ImmutableBytesWritable.class);
    job.setMapOutputValueClass(Put.class);
    //3.配置Reducer
    TableMapReduceUtil.initTableReducerJob("love_hdfs", WriteLoveReducer.class, job);
    //4.配置输入inputFormt
    FileInputFormat.addInputPath(job, new Path("/love/"));
    //5.输出
    return job.waitForCompletion(true) ? 0 : 1;
  }  public Configuration getConf() {
    return this.conf;
  }




}
