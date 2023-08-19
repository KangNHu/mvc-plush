package com.hkn.mvc.plus.utils;

import com.hkn.mvc.plus.exception.ServiceException;
import java.lang.reflect.InvocationTargetException;
import javax.sql.rowset.serial.SerialException;
import org.springframework.beans.BeanUtils;

/**
 * 对象相关工具类
 *
 * @author : KangNing Hu
 */
public class ObjectUtils {


  /**
   * 根据class对象创建一个该class的实例对象
   * <p>创建的条件必须有一个无参的构建方法</p>
   *
   * @param clazz clazz
   * @return 返回实例对象
   */
  public static Object newInstance(Class<?> clazz) {
    try {
      return BeanUtils.findPrimaryConstructor(clazz).newInstance();
    } catch (Exception e) {
      throw new ServiceException("class instance fail", e);
    }
  }
}
