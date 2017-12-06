package org.ll.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import org.ll.service.iface.ApproveService;
import org.ll.model.ApplyDO;
import org.ll.model.ApproveDO;

@Controller
@RequestMapping("/approve")
public class ApproveController {

    private static final Logger log = LoggerFactory.getLogger(ApproveController.class);

    @Autowired
    private ApproveService approveService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String root() {
        return "approve";
    }

    @RequestMapping(value = "/query.do", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<ApproveDO> query(ApproveDO approveDO) {
        return approveService.query(approveDO);
    }

    @RequestMapping(value = "/queryPendingApply.do", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<ApplyDO> queryPendingApply(ApproveDO approveDO) {
        return approveService.queryPendingApply(approveDO);
    }

    @RequestMapping(value = "/approve.do", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public int approve(@RequestBody ApplyDO applyDO) {
        return approveService.approve(applyDO);
    }

    @RequestMapping(value = "/reject.do", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public int reject(@RequestBody ApplyDO applyDO) {
        return approveService.reject(applyDO);
    }

    @RequestMapping(value = "/viewApproveHistory.do", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<ApproveDO> viewApproveHistory(ApproveDO approveDO) {
        return approveService.viewApproveHistory(approveDO);
    }

}
