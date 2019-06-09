package WordCount;

import java.util.HashMap;

/**
 * 数据传输类
 * 封装数据
 * 集合
 */
public class Context {
  /**
   * 数据封装
   */
  private HashMap<Object,Integer> contextMap=new HashMap<Object, Integer>();

  /**
   * 写入数据
   * @param key
   * @param value
   */
  public void write(Object key,Integer value){
    // 放数据到Map中
    contextMap.put(key,value);
  }
  /**
   * 定义根据key获取value方法
   */
  public Integer get(Object key){
    return contextMap.get(key);
  }

  /**
   * 拿到MAP中所有数据内容
   * @return
   */
  public HashMap<Object,Integer> getContextMap(){
    return contextMap;
  }
}
