package com.hkn.mvc.plus;

import com.hkn.mvc.plus.menu.ModelConvertType;
import com.hkn.mvc.plus.models.PageQuery;
import java.util.HashMap;
import java.util.Map;
import com.hkn.mvc.plus.processors.ServicePostProcessor;
import com.hkn.mvc.plus.menu.QueryType;
import lombok.Getter;
import com.hkn.mvc.plus.processors.Condition;

/**
 * 当前操作的service上下文
 * <p>该上下文会在本次操作中以一个中间媒介的作用贯穿所有的service处理周期函数</p>
 *
 * @author : KangNing Hu
 */
public class CurrentServiceContext {

  /**
   * 当前操作的动作类型
   *
   * @see Actions
   */
  @Getter
  Enum<?> action;
  /**
   * 业务类型
   */
  @Getter
  Enum<?> businessType;
  /**
   * 当前业务方法的参数列表
   */
  @Getter
  Object[] args;
  /**
   * 数据层模型对象
   */
  @Getter
  Object entity;
  /**
   * 编辑模型对象
   */
  @Getter
  Object edit;
  /**
   * 分页的查询对象
   */
  @Getter
  PageQuery  pageQuery;
  /**
   * 模型转换方式
   *
   * @see ModelConvertType
   */
  @Getter
  Enum<?> modelConvertType;
  /**
   * 转换对象的数据源
   */
  @Getter
  Object convertSource;
  /**
   * 查询标识,{@link
   * ServicePostProcessor#createQueryCondition(CurrentServiceContext)}将根据该标识进行创建不同的查询条件对象
   *
   * @see QueryType
   */
  @Getter
  Enum<?> query;
  /**
   * 属性列表
   * <p>用于将额外的参数从一个组件传递到后面的组件链中</p>
   */
  @Getter
  Map<String, Object> attributes = new HashMap<>();

  CurrentServiceContext(final Object[] args, final Enum<?> action,
      final Enum<?> businessType) {
    this.action = action;
    this.args = args;
    this.businessType = businessType;
  }

  /**
   * 构建一个service上下文
   *
   * @param action 当前操作的action
   * @param args 当前业务方法的参数列表
   * @param businessType 当前操作的业务类型
   * @return 返回service上下文对象
   */
  public static CurrentServiceContext build(Enum<?> action, Object[] args, Enum<?> businessType) {
    return new CurrentServiceContext(args, action, businessType);
  }

  /**
   * 转换模型数据
   *
   * @param convertType 转换模型的方式
   * @param source 转换模型源
   * @return 返回service上下文
   * @see ModelConvertType
   */
  public CurrentServiceContext convertToTarget(Enum<?> convertType, Object source) {
    this.convertSource = source;
    this.modelConvertType = convertType;
    return this;
  }

  /**
   * 存放entity模型数据
   *
   * @param entity 数据层模型数据
   * @return 返回service上下文
   */
  public CurrentServiceContext entity(Object entity) {
    this.entity = entity;
    return this;
  }

  /**
   * 存放edit模型数据
   *
   * @param edit 编辑模型
   * @return 返回service上下文
   */
  public CurrentServiceContext edit(Object edit) {
    this.edit = edit;
    return this;
  }

  /**
   * 获取业务方法参数
   * <p>如果index小于0或者超过参数的最大索引类型则默认情况下返回第一个参数和最后一个参数</p>
   *
   * @param index 需要获取参数的索引值
   * @param <T> 参数实际类型
   * @return 返回参数对象
   */
  public <T> T getArg(int index) {
    if (index < 0) {
      index = 0;
    }
    int length = this.args.length;
    if (index >= length) {
      index = length - 1;
    }
    if (length == 0) {
      return null;
    }
    return (T) this.args[index];
  }

  /**
   * 向当前的service上下文中添加属性值
   *
   * @param name 属性名称
   * @param value 属性值
   * @return 返回当前对象
   */
  public CurrentServiceContext addAttribute(String name, Object value) {
    this.attributes.put(name, value);
    return this;
  }

  /**
   * @param queryType 查询类型
   * <p>在{@link ServicePostProcessor#createQueryCondition(CurrentServiceContext)}时需要被调用来指定创建{@link
   * Condition}的类型</p>
   * @return 返回当前对象
   */
  public CurrentServiceContext query(Enum<?> queryType) {
    this.query = queryType;
    return this;
  }
}
