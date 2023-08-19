package com.hkn.mvc.plus;

import com.hkn.mvc.plus.processors.Condition;
import com.hkn.mvc.plus.processors.ServicePostProcessor;
import java.io.Serializable;
import lombok.Data;
import lombok.Getter;

/**
 * service 上下文的包装器
 *
 * @author hukangning
 */
public class ServiceContextWrapper extends CurrentServiceContext {

  /**
   * 上一个{@link ServicePostProcessor#createQueryCondition(CurrentServiceContext)}执行的结果
   */
  @Getter
  private Condition<?> preQueryCondition;

  /**
   * 上一个{@link ServicePostProcessor#convertToTarget(CurrentServiceContext)}执行结果
   */
  @Getter
  private Serializable preConvertTargetObject;


  public ServiceContextWrapper(CurrentServiceContext serviceContext) {
    super(serviceContext.args , serviceContext.action , serviceContext.businessType);
    this.modelConvertType = serviceContext.modelConvertType;
    this.entity = serviceContext.entity;
    this.query = serviceContext.query;
    this.pageQuery = serviceContext.pageQuery;
    this.edit = serviceContext.edit;
    this.attributes = serviceContext.attributes;
    this.convertSource = serviceContext.convertSource;
  }

  /**
   * 构建下一个{@link ServicePostProcessor}执行的{@link ServiceContext}
   *
   * @param queryCondition
   * 上一个{@link ServicePostProcessor#createQueryCondition(CurrentServiceContext)}的执行结构
   * @return 返回当前对象
   */
  public ServiceContextWrapper next(Condition<?> queryCondition) {
    this.preQueryCondition = queryCondition;
    return this;
  }

  /**
   * 构建下一个{@link ServicePostProcessor}执行的{@link ServiceContext}
   *
   * @param target 上一个{@link ServicePostProcessor#convertToTarget(CurrentServiceContext)}执行的结果
   * @return 返回当前对象
   */
  public ServiceContextWrapper next(Serializable target) {
    this.preConvertTargetObject = target;
    return this;
  }
}