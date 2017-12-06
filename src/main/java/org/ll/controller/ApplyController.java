package org.ll.controller;

import java.util.List;

import org.ll.exception.AppException;
import org.ll.model.ApplyDO;
import org.ll.model.QuotaDO;
import org.ll.service.common.CallAPIService;
import org.ll.service.iface.ApplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/apply")
public class ApplyController {

    private static final Logger log = LoggerFactory.getLogger(ApplyController.class);

    @Autowired
    private ApplyService applyService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String root() {
        return "apply";
    }

    @RequestMapping(value = "/query.do", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<ApplyDO> query(ApplyDO applyDO) {
        return applyService.query(applyDO);
    }

    @RequestMapping(value = "/save.do", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public int save(@RequestBody ApplyDO applyDO) {
        return applyService.save(applyDO);
    }

    @RequestMapping(value = "/delete.do", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public int delete(@RequestBody ApplyDO applyDO) {
        return applyService.delete(applyDO);
    }

    @RequestMapping(value = "/submit.do", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public int submit(@RequestBody ApplyDO applyDO) {
        return applyService.submit(applyDO);
    }

}
