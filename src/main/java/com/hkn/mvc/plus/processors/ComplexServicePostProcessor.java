package com.hkn.mvc.plus.processors;

import com.hkn.mvc.plus.CurrentServiceContext;
import com.hkn.mvc.plus.ServiceContextWrapper;
import com.hkn.mvc.plus.models.ServiceModelClass;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import org.springframework.util.Assert;

/**
 * 复杂的规则service后处理器，用于可以同时支持多个{@link ServicePostProcessor}协助处理
 *
 * @author : KangNing Hu
 */
public class ComplexServicePostProcessor implements
    ServicePostProcessor<Serializable, Serializable, Serializable> {

  private final List<ServicePostProcessor<? extends Serializable, ? extends Serializable, ? extends Serializable>> ruleServicePostProcessors;

  public ComplexServicePostProcessor(
      List<ServicePostProcessor<? extends Serializable, ? extends Serializable, ? extends Serializable>> ruleServicePostProcessors) {
    Assert.notNull(ruleServicePostProcessors, "ruleServicePostProcessors  is null");
    this.ruleServicePostProcessors = ruleServicePostProcessors;
  }



  @Override
  public ModelRepertory<Serializable> getMapper() {
    for (ServicePostProcessor<? extends Serializable , ? extends Serializable, ? extends Serializable> ruleServicePostProcessor : ruleServicePostProcessors) {
      ModelRepertory<? extends Serializable> mapper = ruleServicePostProcessor.getMapper();
      if (mapper != null) {
        return (ModelRepertory<Serializable>) mapper;
      }
    }
    throw new IllegalArgumentException("No base mapper was found");
  }

  @Override
  public void validate(CurrentServiceContext serviceContext) {
    for (ServicePostProcessor<? extends Serializable , ? extends Serializable, ? extends Serializable> ruleServicePostProcessor : ruleServicePostProcessors) {
      ruleServicePostProcessor.validate(serviceContext);
    }
  }

  @Override
  public ServiceModelClass<Serializable , Serializable, Serializable> getModelClasses() {
    for (ServicePostProcessor<? extends Serializable , ? extends Serializable, ? extends Serializable> ruleServicePostProcessor : ruleServicePostProcessors) {
      ServiceModelClass<? extends Serializable, ? extends Serializable, ? extends Serializable> modelClasses = ruleServicePostProcessor.getModelClasses();
      if (modelClasses != null) {
        return (ServiceModelClass<Serializable, Serializable, Serializable>) modelClasses;
      }
    }
    throw new IllegalArgumentException("No modelClasses was found");
  }

  @Override
  public Condition<Serializable> createQueryCondition(CurrentServiceContext serviceContext) {
    Condition<Serializable> condition = null;
    for (ServicePostProcessor<? extends Serializable , ? extends Serializable, ? extends Serializable>  ruleServicePostProcessor : ruleServicePostProcessors) {
      Condition<? extends Serializable> queryCondition = ruleServicePostProcessor.createQueryCondition(
          serviceContext);
      if (queryCondition != null) {
        condition = (Condition<Serializable>) queryCondition;
        if (serviceContext instanceof ServiceContextWrapper serviceContextWrapper) {
          serviceContextWrapper.next(condition);
        } else {
          serviceContext = new ServiceContextWrapper(serviceContext).next(queryCondition);
        }
      }
    }
    if (condition == null) {
      throw new IllegalArgumentException("No queryWrapper was found");
    }
    return condition;
  }

  @Override
  public void postProcessBeforeWrite(CurrentServiceContext serviceContext) {
    this.ruleServicePostProcessors.forEach(
        ruleServicePostProcessor -> ruleServicePostProcessor.postProcessBeforeWrite(
            serviceContext));
  }

  @Override
  public void postProcessAfterWrite(CurrentServiceContext serviceContext) {
    this.ruleServicePostProcessors.forEach(
        ruleServicePostProcessor -> ruleServicePostProcessor.postProcessAfterWrite(
            serviceContext));
  }

  @Override
  public void postProcessAfterPage(CurrentServiceContext serviceContext) {
    this.ruleServicePostProcessors.forEach(
        ruleServicePostProcessor -> ruleServicePostProcessor.postProcessAfterPage(
            serviceContext));
  }

  @Override
  public void publishBusinessEvent(CurrentServiceContext serviceContext) {
    this.ruleServicePostProcessors.forEach(
        ruleServicePostProcessor -> ruleServicePostProcessor.publishBusinessEvent(
            serviceContext));
  }

  @Override
  public Serializable convertToTarget(CurrentServiceContext serviceContext) {
    Serializable target = null;
    for (ServicePostProcessor<? extends Serializable , ? extends Serializable, ? extends Serializable> ruleServicePostProcessor : ruleServicePostProcessors) {
      Serializable currentTarget = (Serializable) ruleServicePostProcessor.convertToTarget(serviceContext);
      if (currentTarget != null) {
        target = currentTarget;
        if (serviceContext instanceof ServiceContextWrapper serviceContextWrapper) {
          serviceContextWrapper.next(target);
        } else {
          serviceContext = new ServiceContextWrapper(serviceContext).next(target);
        }
      }
    }
    return target;
  }
}
