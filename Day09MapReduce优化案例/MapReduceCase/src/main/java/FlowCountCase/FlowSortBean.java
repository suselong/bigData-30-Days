package FlowCountCase;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author CDMloong
 * @version 1.0
 */
public class FlowSortBean implements WritableComparable<FlowSortBean> {
  private long downFlow;
  private long sumFlow;
  // 定义属性
  private long upFlow;

  // 无参构造函数
  public FlowSortBean() {
  }

  // 有参构造函数
  public FlowSortBean(long upFlow, long downFlow) {
    this.upFlow = upFlow;
    this.downFlow = downFlow;
    this.sumFlow = upFlow + downFlow;
  }

  /**
   *  排序，首先需要明确根据什么来排序，例如流量总和sumFlow
    */
  public int compareTo(FlowSortBean flowSortBean) {
    // 数据传输是一条一条传的，当前数据比上一条大，则往上排,负数为倒序，整数为正序
    return this.sumFlow > flowSortBean.getSumFlow() ? -1 : 1;
  }
  // 序列化
  public void write(DataOutput out) throws IOException {
    out.writeLong(upFlow);
    out.writeLong(downFlow);
    out.writeLong(sumFlow);
  }

  // 反序列化
  public void readFields(DataInput in) throws IOException {
    upFlow = in.readLong();
    downFlow = in.readLong();
    sumFlow = in.readLong();
  }

  public long getSumFlow() {
    return sumFlow;
  }

  public void setSumFlow(long sumFlow) {
    this.sumFlow = sumFlow;
  }

  public long getDownFlow() {
    return downFlow;
  }

  public void setDownFlow(long downFlow) {
    this.downFlow = downFlow;
  }

  public long getUpFlow() {
    return upFlow;
  }

  public void setUpFlow(long upFlow) {
    this.upFlow = upFlow;
  }

  @Override
  public String toString() {
    return upFlow + "\t" + downFlow + "\t" + sumFlow;
  }

}
