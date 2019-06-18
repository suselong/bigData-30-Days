package OrderMR;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class OrderBean implements WritableComparable<OrderBean> {
  private int orderId;
  private double price;

  public OrderBean() {
  }

  public OrderBean(int orderId, double price) {
    this.orderId = orderId;
    this.price = price;
  }

  /**
   * 排序，订单号升序，相同订单号，价格倒序
   *
   * @param o 下一个数据类
   * @return
   */
  public int compareTo(OrderBean o) {
    int rs;
    if (this.orderId > o.orderId) {
      rs = 1;
    } else if (this.orderId < o.orderId) {
      rs = -1;
    } else {
      rs = this.price > o.price ? -1 : 1;
    }
    return rs;
  }

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return orderId + "\t" + price;
  }

  /**
   * 序列化
   *
   * @param dataOutput 输出的数据
   * @throws IOException
   */
  public void write(DataOutput dataOutput) throws IOException {
    dataOutput.writeInt(orderId);
    dataOutput.writeDouble(price);
  }

  /**
   * 反序列化
   *
   * @param dataInput 输入的数据
   * @throws IOException
   */
  public void readFields(DataInput dataInput) throws IOException {
    orderId = dataInput.readInt();
    price = dataInput.readDouble();
  }
}
