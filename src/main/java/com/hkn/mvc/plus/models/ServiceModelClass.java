package com.hkn.mvc.plus.models;


import lombok.Data;

/**
 * service主表的数据层实体class（entity）,视图层实体class(vo),编辑实体class(edit)
 *
 * @author : KangNing Hu
 */
@Data
public class ServiceModelClass<Entity, Vo, Edit> {

  /**
   * 数据层实体class
   */
  private Class<Entity> entityClass;

  /**
   * vo层实体class
   */
  private Class<Vo> voClass;

  /**
   * 编辑实体class
   */
  private Class<Edit> editClass;

  /**
   * 构建service模型的class表
   *
   * @param entityClass entityClass
   * @param voClass voClass
   * @param editClass editClass
   * @param <Entity> entity的实际类型
   * @param <Vo> vo的实际类型
   * @param <Edit> edit的实际类型
   * @return 返回service模型的class表
   */
  public static <Entity, Vo, Edit> ServiceModelClass<Entity, Vo, Edit> of(Class entityClass,
      Class voClass, Class editClass) {
    ServiceModelClass<Entity, Vo, Edit> serviceModelClass = new ServiceModelClass<>();
    serviceModelClass.setVoClass(voClass);
    serviceModelClass.setEditClass(editClass);
    serviceModelClass.setEntityClass(entityClass);
    return serviceModelClass;
  }
}
