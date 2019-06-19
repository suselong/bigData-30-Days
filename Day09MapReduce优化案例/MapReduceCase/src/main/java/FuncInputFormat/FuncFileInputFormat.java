package FuncInputFormat;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;

/**
 * 自定义inputFormat内容，key无内容，value是BytesWritable
 */
public class FuncFileInputFormat extends FileInputFormat<NullWritable, BytesWritable> {
  /**
   * 进行数据的读取，因为返回的是RecordReader，方法中初始化一个类，需要自定义，如果不自定义就和默认的没有区别
   *
   * @param inputSplit
   * @param taskAttemptContext
   * @return
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  public RecordReader<NullWritable, BytesWritable> createRecordReader(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
    return new FuncRecordReader();
  }

  /**
   * 参数需要根据曾哥数据的传输来，JobContext为数据的传入，加上路径，自定义方法
   *
   * @param context  传入内容
   * @param filename 文件
   * @return
   */
  @Override
  protected boolean isSplitable(JobContext context, Path filename) {
    // 返回false不进行切分，表示合并为一个文件
    return false;
  }
}
