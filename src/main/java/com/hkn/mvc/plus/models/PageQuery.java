package com.hkn.mvc.plus.models;

import java.io.Serializable;
import lombok.Data;

/**
 * 分页查询对象
 * @author hukangning
 */
@Data
public class PageQuery implements Serializable {

  /**
   * 当前页的索引数，代表第几页
   */
  private Integer pageNo;

  /**
   * 当前页的页面容量，当前页展示多少条数据
   */
  private Integer pageSize;

}
