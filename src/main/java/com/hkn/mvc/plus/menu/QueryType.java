package com.hkn.mvc.plus.menu;

/**
 * @author hukangning
 * 查询类型
 */
public enum QueryType {
  /**
   * 分页时的查询条件
   */
  PAGE,

  /**
   * 新增判重时的查询条件
   */
  ADD_DISTINCT,

  /**
   * 更新判重时的查询条件
   */
  UPDATE_DISTINCT;
}
