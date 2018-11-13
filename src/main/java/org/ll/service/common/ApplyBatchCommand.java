package org.ll.service.common;

import java.util.List;

import org.business.models.applysystem.Apply;
import org.ll.service.ApplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class ApplyBatchCommand extends HystrixCommand<List<Apply>> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplyBatchCommand.class);

    private ApplyService service;
    private List<Long> ids;

    public ApplyBatchCommand(ApplyService service, List<Long> ids){
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("applyBatchCommand")));
        this.service = service;
        this.ids = ids;
    }

    @Override
    protected List<Apply> run() {
        List<Apply> users = service.queryAll(ids);
        System.out.println(users);
        return users;
    }

    @Override
    protected List<Apply> getFallback(){
        LOGGER.info("ApplyBatchCommand的run方法，调用失败");
        return null;
    }

}
