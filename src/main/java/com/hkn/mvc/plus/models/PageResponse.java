package com.hkn.mvc.plus.models;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 分页响应体
 *
 * @author : KangNing Hu
 */
@Data
public class PageResponse<Data> implements Serializable {

  /**
   * 当前页索引，代表第几页
   */
  private Integer pageNo;
  /**
   * 页面容量，代表一页有多少数据
   */
  private Integer pageSize;
  /**
   * 总数据，代表这次查询一共有多少条数据
   */
  private Long total;
  /**
   * 当前页的数据列表
   */
  private List<Data> rows;
}
