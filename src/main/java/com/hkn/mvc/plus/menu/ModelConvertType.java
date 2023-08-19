package com.hkn.mvc.plus.menu;

/**
 * 数据模型的转换方式
 *
 * @author hukangning
 */
public enum ModelConvertType {

  /**
   * 数据模型转换为编辑模型
   */
  ENTITY_2_EDIT,
  /**
   * 编辑模型转换为数据模型
   */
  EDIT_2_ENTITY,
  /**
   * 数据模型转换为传输层模型
   */
  ENTITY_2_VO,
}
