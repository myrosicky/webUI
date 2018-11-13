package org.ll.controller;

import java.util.List;

import org.business.models.applysystem.Apply;
import org.ll.service.ApplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Apply> query(Apply Apply) {
        return applyService.query(Apply);
    }

    @RequestMapping(value = "/save.do", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public int save(@RequestBody Apply Apply) {
        return applyService.save(Apply);
    }

    @RequestMapping(value = "/delete.do", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public int delete(@RequestBody Apply Apply) {
        return applyService.delete(Apply);
    }

    @RequestMapping(value = "/submit.do", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public int submit(@RequestBody Apply Apply) {
        return applyService.submit(Apply);
    }

}
