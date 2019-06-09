package RPC;

/**
 * 协议接口
 */
public interface ClicentNameNodeProtocol {
  //1. 定义协议的ID
  public static final long versionID = 1L;
  /**
   * 拿到元数据方法，协议通信前会访问元数据
   */
  public String getMetaData(String path);
}
