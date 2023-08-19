package com.hkn.mvc.plus;

import com.hkn.mvc.plus.models.PageQuery;
import com.hkn.mvc.plus.models.PageResponse;
import java.io.Serializable;
import com.hkn.mvc.plus.processors.ServicePostProcessor;

/**
 * service上下文，主要定义service业务层的基本操作
 * <p>如，新增，分页，删除，批量删除，详情</p>
 * <p>其中涉及到三类业务模型分别如下：</p>
 * <url>
 * <li>Edit 代表编辑模型，也是Vo模型中的特殊的一类模型，在某些情况下可以和Vo是同一个实体</li>
 * <li>Vo 代表传输模型，在某些情况下可以和Vo是同一个实体</li>
 * <li>entity 代表数据层模型，用于和数据库进行交换的模型</li>
 * </url>
 * <p>同时Edit和Vo模型可以是一个复杂的模型实体，比如一个业务需要涉及到多个数据表或者服务的操作</p>
 * <p>在所有的操作完成后推荐调用{@link
 * ServicePostProcessor#publishBusinessEvent(CurrentServiceContext)}发布一个事件</p>
 *
 * @author hukangning
 */
public interface ServiceContext<Edit extends Serializable, Vo extends Serializable, Entity extends Serializable> {


  /**
   * 通过id详情
   * <p>当这个业务涉及到多个业务模型，那么这个id一般为作为主模型的id（主表）</p>
   *
   * @param id 业务模型的主键id,
   * @return 返回编辑实体
   */
  Edit get(String id);

  /**
   * 分页条件查询
   *
   * @param query 分页条件
   * @return 返回分页数据
   */
  PageResponse<Vo> page(PageQuery query);

  /**
   * 新增
   * <p>新增模型数据</p>
   * <p>如果这个模型是一个复杂的模型则会涉及到多个副模型数据的新增</p>
   * <p>在新增完成后需要</p>
   *
   * @param edit 编辑模型
   * @return 返回作为本次业务的主模型id（主表id）
   */
  Long add(Edit edit);

  /**
   * 更新
   * <p>更新模型数据</p>
   * <p>该操作推荐是一个全量的更新,为了可以更好以及更容易对模型数据进行操作</p>
   * <p>如果这个模型是一个复杂的模型则会涉及到多个副模型数据的更新。复杂模型更新情况如下：</p>
   * <ul>
   *   <li>如果是主对副是一对多的场景,并且其他业务模型不会对副模型的主键进行关联或引用对则推荐全量删除然后在进行重新全量新增最终和主模型数据进行关联</li>
   *   <li>如果是主对副是一对一的场景，那么推荐的做法是对主模型数据和副模型进行更新操作而不是先删除再新增</li>
   *   <li>在一次完整的更新中需要保证的是主模型主键id不会被变更</li>
   * </ul>
   */
  Long update(Edit edit);

  /**
   * 删除
   * <p>删除主模型数据</p>
   * <p>如果其是一个复杂的模型数据，则需要删除其对应副模型的数据</p>
   *
   * @param id 主模型id
   * @return 至少需要返回主模型数据，副模型数据根据实际场景进行返回，如果返回null则任何没有删除任何数据
   */
  Edit delete(Long id);
}
