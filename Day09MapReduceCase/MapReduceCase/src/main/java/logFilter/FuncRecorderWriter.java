package logFilter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

public class FuncRecorderWriter extends RecordWriter<Text, NullWritable> {
  private FSDataOutputStream apacheLog;
  private FSDataOutputStream otherLog;

  /**
   * 因为框架自带的输出文件不需要定义，会自动创建一个类型part-r-0000文件，因此需要在这里自定义输出路径
   *
   * @param job 任务信息
   * @throws IOException
   */
  FuncRecorderWriter(TaskAttemptContext job) throws IOException {
    // 获取配置信息
    Configuration conf = job.getConfiguration();
    // 获取文件系统
    FileSystem fs = FileSystem.get(conf);
    // 定义输出路径
    apacheLog = fs.create(new Path("Day09MapReduceCase\\MapReduceCase\\src\\main\\resources\\filterOut\\apache.log"));
    otherLog = fs.create(new Path("Day09MapReduceCase\\MapReduceCase\\src\\main\\resources\\filterOut\\other.log"));

  }

  /**
   * 写入数据
   *
   * @param key   输入的单条数据的key
   * @param value 输入的单条数据的value
   * @throws IOException
   */
  @Override
  public void write(Text key, NullWritable value) throws IOException {
    if (key.toString().contains("apache")) {
      //写出到文件
      apacheLog.write(key.getBytes());
    } else {
      otherLog.write(key.getBytes());
    }
  }

  /**
   * 关闭流
   *
   * @param taskAttemptContext
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  public void close(TaskAttemptContext taskAttemptContext) throws IOException {
    // 判断如果有内容才进行关闭
    if (null != apacheLog) {
      apacheLog.close();
    }
    if (null != otherLog) {
      otherLog.close();
    }
  }
}
