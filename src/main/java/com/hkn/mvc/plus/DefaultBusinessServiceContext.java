package com.hkn.mvc.plus;

import com.hkn.mvc.plus.models.PageQuery;
import com.hkn.mvc.plus.models.PageResponse;
import com.hkn.mvc.plus.processors.ComplexServicePostProcessor;
import com.hkn.mvc.plus.processors.ServicePostProcessor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

/**
 * 默认的service上下
 *
 * @author : KangNing Hu
 */
public class DefaultBusinessServiceContext implements
    ServiceContext<Serializable, Serializable, Serializable>, InitializingBean,
    ApplicationContextAware {

  private ApplicationContext applicationContext;
  private final List<ServicePostProcessor<? extends Serializable, ? extends Serializable, ? extends Serializable>> servicePostProcessors = new ArrayList<>();

  @Override
  public Serializable get(String id) {
    return null;
  }

  @Override
  public PageResponse<Serializable> page(PageQuery query) {
    return null;
  }

  @Override
  public Long add(Serializable serializable) {
    return null;
  }

  @Override
  public Long update(Serializable serializable) {
    return null;
  }

  @Override
  public Serializable delete(Long id) {
    return null;
  }


  /**
   * 获取当前的处理器
   * <p>返回一个嵌套的处理器，用于支持多个{@link ServicePostProcessor}协助处理</p>
   *
   * @return 返回一个嵌套的处理器
   */
  private ServicePostProcessor<? extends Serializable, ? extends Serializable, ? extends Serializable> currentProcessor(
      CurrentServiceContext serviceContext) {
    List<ServicePostProcessor<?, ?, ?>> currentRuleServicePostProcessors = this.servicePostProcessors.stream()
        .filter(ruleServicePostProcessor -> ruleServicePostProcessor.supports(serviceContext))
        .collect(Collectors.toList());
    return new ComplexServicePostProcessor(currentRuleServicePostProcessors);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    String[] beanNames = applicationContext.getBeanNamesForType(ServicePostProcessor.class);
    for (String beanName : beanNames) {
      ServicePostProcessor<? extends Serializable, ? extends Serializable, ? extends Serializable> servicePostProcessor = applicationContext.getBean(
          beanName, ServicePostProcessor.class);
      this.servicePostProcessors.add(servicePostProcessor);
    }
    AnnotationAwareOrderComparator.sort(servicePostProcessors);
  }


  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}
