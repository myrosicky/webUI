package org.ll.service.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.business.models.applysystem.Apply;
import org.ll.service.ApplyService;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;

public class ApplyCollapser extends
		HystrixCollapser<List<Apply>, Apply, Long> {

	private ApplyService service;
    private Long userId;

    public ApplyCollapser(ApplyService applyService, Long userId){
        super(Setter.withCollapserKey(HystrixCollapserKey .Factory.asKey("applyCollapser"))
        		.andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(100))
        		);
        this.service = applyService;
        this.userId = userId;
    }

    @Override
    public Long getRequestArgument() {
        return userId;
    }

    @Override
    public HystrixCommand<List<Apply>> createCommand(Collection<CollapsedRequest<Apply, Long>> collapsedRequests) {
        //按请求数声名ApplyId的集合
        List<Long> userIds = new ArrayList<>(collapsedRequests.size());
        //通过请求将100毫秒中的请求参数取出来装进集合中
        userIds.addAll(collapsedRequests.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList()));
        //返回ApplyBatchCommand对象，自动执行ApplyBatchCommand的run方法
        return new ApplyBatchCommand(service, userIds);
    }

    @Override
    protected void mapResponseToRequests(List<Apply> batchResponse, Collection<CollapsedRequest<Apply, Long>> collapsedRequests) {
        int count = 0 ;
        for(CollapsedRequest<Apply,Long> collapsedRequest : collapsedRequests){
            //从批响应集合中按顺序取出结果
            Apply user = batchResponse.get(count++);
            //将结果放回原Request的响应体内
            collapsedRequest.setResponse(user);
        }
    }
}
