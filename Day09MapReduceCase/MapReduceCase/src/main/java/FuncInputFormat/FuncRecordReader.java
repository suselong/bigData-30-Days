package FuncInputFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * 自定义读数据的方式,继承RecordReader
 */
public class FuncRecordReader extends RecordReader<NullWritable, BytesWritable> {
  private BytesWritable value = new BytesWritable();
  private Configuration conf;
  private boolean isProcess = false;
  private FileSplit split;

  /**
   * 初始化
   *
   * @param inputSplit 输入切片信息
   * @param context    输入内容
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  public void initialize(InputSplit inputSplit, TaskAttemptContext context){
    // 初始化文件切片
    split = (FileSplit) inputSplit;
    // 获取配置信息
    conf = context.getConfiguration();

  }

  /**
   * 读取文件的方式,读取是一个文件一个文件的读取
   *
   * @return boolean 文件是否可读
   */
  @Override
  public boolean nextKeyValue() {
    if (!isProcess) {
      //1. 根据切片的长度来创建缓冲区
      byte[] buf = new byte[(int) split.getLength()];
      //2. 通过HDFS来读，需要定义基于HDFS的输入流
      FSDataInputStream fis = null;
      //3. 定义读取数据需要的文件系统
      FileSystem fs = null;
      //4. 获取文件的路径
      Path path = split.getPath();
      try {
        //5. 根据路径获取文件系统
        fs = path.getFileSystem(conf);
        //6. 定义输入流
        fis = fs.open(path);
        //7. 输入数据读取到buf中
        IOUtils.readFully(fis, buf, 0, buf.length);
        //8. 拷贝数据到最终的数据，输入到map阶段
        value.set(buf, 0, buf.length);
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        //9. 关流
        IOUtils.closeStream(fis);
        IOUtils.closeStream(fs);
      }
      //10.数据读完后总之循环
      isProcess = true;
      //11.表示文件是可读的
      return true;
    }
    return false;
  }

  /**
   * 获取key
   * @return
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  public NullWritable getCurrentKey() throws IOException, InterruptedException {
    return NullWritable.get();
  }

  /**
   *  获取值
   * @return
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  public BytesWritable getCurrentValue() throws IOException, InterruptedException {
    return value;
  }
  @Override
  public float getProgress() throws IOException, InterruptedException {
    return 0;
  }
  @Override
  public void close() throws IOException {
  }
}
