package FlowCountCase;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 数据封装类，用于数据传输
 */
public class FlowBean implements Writable {
  private long dfFlow;
  private long flowSum;
  /**
   * 定义数据类的属性
   */
  private long upFlow;

  public FlowBean() {
  }

  public FlowBean(long upFlow, long dfFlow) {
    this.upFlow = upFlow;
    this.dfFlow = dfFlow;
    this.flowSum = upFlow + dfFlow;
  }

  public long getDfFlow() {
    return dfFlow;
  }

  public void setDfFlow(long dfFlow) {
    this.dfFlow = dfFlow;
  }

  public long getFlowSum() {
    return flowSum;
  }

  public void setFlowSum(long flowSum) {
    this.flowSum = flowSum;
  }

  public long getUpFlow() {
    return upFlow;
  }

  public void setUpFlow(long upFlow) {
    this.upFlow = upFlow;
  }

  @Override
  public String toString() {
    return "upFlow:" + upFlow + ",dfFlow:" + dfFlow + ",flowSum:" + flowSum;
  }

  /**
   * 序列化
   *
   * @param dataOutput 输出数据
   * @throws IOException
   */
  public void write(DataOutput dataOutput) throws IOException {
    dataOutput.writeLong(upFlow);
    dataOutput.writeLong(dfFlow);
    dataOutput.writeLong(flowSum);
  }

  /**
   * 反序列化,输入到内存需要按照序列化的顺序
   *
   * @param dataInput 输入数据
   * @throws IOException
   */
  public void readFields(DataInput dataInput) throws IOException {
    upFlow = dataInput.readLong();
    dfFlow = dataInput.readLong();
    flowSum = dataInput.readLong();
  }
}
