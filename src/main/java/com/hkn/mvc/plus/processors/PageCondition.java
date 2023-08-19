package com.hkn.mvc.plus.processors;

import com.hkn.mvc.plus.processors.Condition;
import lombok.Getter;

/**
 * 分页条件
 * <p>用于数据层模型分页查询</p>
 *
 * @param delegate 塞选条件
 * @param pageNo 分页的当前页索引
 * @param pageSize 当前页的页面容量
 * @author hukangning
 */
public record PageCondition<Entity>(@Getter Condition<Entity> delegate, @Getter Integer pageNo,
                                    @Getter Integer pageSize) implements Condition<Entity> {

}
