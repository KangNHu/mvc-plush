package com.hkn.mvc.plus.processors;

import com.hkn.mvc.plus.CurrentServiceContext;
import com.hkn.mvc.plus.menu.ModelConvertType;
import com.hkn.mvc.plus.models.ServiceModelClass;
import com.hkn.mvc.plus.utils.ObjectUtils;
import java.io.Serializable;
import com.hkn.mvc.plus.Actions;
import com.hkn.mvc.plus.exception.ServiceException;
import org.springframework.beans.BeanUtils;

/**
 * service业务的后处理器
 * <p>1.用于对模型对象的填充</p>
 * <p>2.用于获取主模型数据层仓库</p>
 * <p>3.用于对模型的校验</p>
 * <p>4.用于不同模型之间的转换</p>
 * <p>5.用于创建不同的查询对象</p>
 * <p>5.用于对新增，删除，更新，分页不同阶段的生命周期处理</p>
 */
public interface ServicePostProcessor<Edit extends Serializable, Vo extends Serializable, Entity extends Serializable> {

  /**
   * 校验当前处理器是否支持当前的业务处理
   *
   * @param serviceContext service上下文
   * @return 如果支持返回true否则返回false
   */
  default boolean supports(CurrentServiceContext serviceContext) {
    return false;
  }

  /**
   * 获取该entity对应的数据层仓库实例
   *
   * @return 返回数据层仓库实例
   */
  ModelRepertory<Entity> getMapper();

  /**
   * 校验实体
   * <p>只有在{@link CurrentServiceContext#getAction()}}为{@link Actions#UPDATE}或{@link
   * Actions#INSERT}被调用</p>
   *
   * @param serviceContext service上下文
   * @throws ServiceException,IllegalArgumentException 当实体参数不符合时抛出这两个异常
   */
  void validate(CurrentServiceContext serviceContext);

  /**
   * 获取当前处理器设计到的模型class对象列表
   * <p>包含 vo ,entity , edit</p>
   * <p>在默认的{@link
   * ServicePostProcessor#convertToTarget(CurrentServiceContext)}}中，将使用该返回值进行类型转换处理</p>
   *
   * @return 返回class对象列表
   */
  ServiceModelClass<Entity, Vo, Edit> getModelClasses();

  /**
   * 创建不同场景的查询条件
   *
   * @return 返回数据层条件对象，不能返回空
   */
  Condition<Entity> createQueryCondition(CurrentServiceContext serviceContext);


  /**
   * 在写主模型数据之前被调用
   * <p>默认情况下在如下操作之前会被调用</p>
   * <ul>
   *   <li>{@link Actions#INSERT}</li>
   *   <li>{@link Actions#UPDATE}</li>
   *   <li>{@link Actions#DELETE}</li>
   * </ul>
   *
   * @param serviceContext service上下文
   */
  default void postProcessBeforeWrite(CurrentServiceContext serviceContext) {
  }

  /**
   * 在写主模型之后被调用
   * <p>默认情况下在如下操作之后会被调用</p>
   * <ul>
   *   <li>{@link Actions#INSERT}</li>
   *   <li>{@link Actions#UPDATE}</li>
   *   <li>{@link Actions#DELETE}</li>
   * </ul>
   *
   * @param serviceContext service上下文
   */
  default void postProcessAfterWrite(CurrentServiceContext serviceContext) {
  }

  /**
   * 主表分页之后，类型转换之前的处理
   * <p>在{@link Actions#PAGE}时被调用</p>
   *
   * @param serviceContext service上下文
   */
  default void postProcessAfterPage(CurrentServiceContext serviceContext) {

  }

  /**
   * 发布业务事件
   *
   * @param serviceContext serviceContext
   */
  default void publishBusinessEvent(CurrentServiceContext serviceContext) {

  }

  /**
   * 实体转换
   * <p>默认情况下对以下类型进行处理</p>
   * <ul>
   *   <li>{@link ModelConvertType#EDIT_2_ENTITY}</li>
   *   <li>{@link ModelConvertType#ENTITY_2_EDIT}</li>
   *   <li>{@link ModelConvertType#ENTITY_2_VO}</li>
   * </ul>
   * <p>默认情况下的转换方式为属性拷贝(浅拷贝)</p>
   * <p>目标模型实体必须有一个无参构建方法来供其进行实例化</p>
   *
   * @param serviceContext service上下文
   * @return 返回目标实体
   * @see ModelConvertType
   */
  default Object convertToTarget(CurrentServiceContext serviceContext) {
    Enum<?> modelConvertType = serviceContext.getModelConvertType();
    if (modelConvertType instanceof ModelConvertType convertType) {
      Object target;
      switch (convertType) {
        case ENTITY_2_VO -> target = ObjectUtils.newInstance(this.getModelClasses().getVoClass());
        case ENTITY_2_EDIT ->
            target = ObjectUtils.newInstance(this.getModelClasses().getEditClass());
        case EDIT_2_ENTITY ->
            target = ObjectUtils.newInstance(this.getModelClasses().getEntityClass());
        default ->
            throw new ServiceException(String.format("Unknown convertType:%s", modelConvertType));
      }
      BeanUtils.copyProperties(serviceContext.getConvertSource(), target);
      return target;
    }
    throw new ServiceException(String.format("Unknown convertType:%s", modelConvertType));
  }

}
