package logFilter;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FuncFilterReducer extends Reducer<Text, NullWritable, Text, NullWritable> {
  @Override
  protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
    // 加上换行
    context.write(new Text(key + "\n"), NullWritable.get());
  }
}
