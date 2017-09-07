package com.xlibao.saas.market.controller.openapi;

import com.alibaba.fastjson.JSONObject;
import com.xlibao.saas.market.service.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chinahuangxc on 2017/9/7.
 */
@Controller
@RequestMapping(value = "/market/question/openapi")
public class QuestionOpenApiController {

    @Autowired
    private QuestionService questionService;

    /**
     * <pre>
     *     <b>加载问题类目</b>
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "loaderProblemTypes")
    public JSONObject loaderProblemTypes() {
        return questionService.loaderProblemTypes();
    }

    /**
     * <pre>
     *     <b>找到某个类目下的所有问题</b>
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "findProblems")
    public JSONObject findProblems() {
        return questionService.findProblems();
    }

    /**
     * <pre>
     *     <b>加载退款标签</b>
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "loaderRefundLabel")
    public JSONObject loaderRefundLabel() {
        return questionService.loaderRefundLabel();
    }
}