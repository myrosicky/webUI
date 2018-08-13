package org.ll.controller;

import java.util.List;

import org.business.models.applysystem.Apply;
import org.business.models.applysystem.AuditDO;
import org.ll.service.iface.AuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/audit")
public class AuditController {

    private static final Logger log = LoggerFactory.getLogger(AuditController.class);

    @Autowired
    private AuditService auditService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String root() {
        return "audit";
    }

    @RequestMapping(value = "/query.do", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<AuditDO> query(AuditDO auditDO) {
        return auditService.query(auditDO);
    }

    @RequestMapping(value = "/queryPendingApply.do", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Apply> queryPendingApply(AuditDO auditDO) {
        return auditService.queryPendingApply(auditDO);
    }

    @RequestMapping(value = "/pass.do", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public int pass(@RequestBody Apply apply) {
        return auditService.pass(apply);
    }

    @RequestMapping(value = "/reject.do", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public int reject(@RequestBody Apply apply) {
        return auditService.reject(apply);
    }

    @RequestMapping(value = "/viewAuditHistory.do", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Apply> viewAuditHistory(AuditDO auditDO) {
        return auditService.viewAuditHistory(auditDO);
    }

}
