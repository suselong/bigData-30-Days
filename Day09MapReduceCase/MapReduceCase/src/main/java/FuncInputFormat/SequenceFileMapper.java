package FuncInputFormat;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class SequenceFileMapper extends Mapper<NullWritable, BytesWritable, Text, BytesWritable> {
  private Text newKey = new Text();

  /**
   * 初始化
   * @param context 读入的内容
   */
  @Override
  protected void setup(Context context){
    // 1.拿到切片信息
    FileSplit split = (FileSplit) context.getInputSplit();
    // 2.路径
    Path path = split.getPath();
    // 3.即带路径的名称
    newKey.set(path.toString());
  }

  @Override
  protected void map(NullWritable key, BytesWritable value, Context context)
      throws IOException, InterruptedException {
    context.write(newKey, value);
  }
}
