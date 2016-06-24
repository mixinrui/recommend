package com.boxfishedu.recommend.provider.history.web;

import com.boxfishedu.recommend.provider.history.service.HistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/recommend/provider")
public class HistoryController {

    private static Logger logger = LoggerFactory.getLogger(HistoryController.class);

    @Autowired
    HistoryService historyService;


    @RequestMapping(value = "/history/{user_id}", method = RequestMethod.POST)
    public Object getHistory(@PathVariable(value = "user_id") Long userId) throws Exception {

        logger.info("[boxfish-recommend-provider] [getHistory] 请求学习历史. ID=[{}]", userId);

        String response = historyService.getHistory(userId).toJson();
        logger.info("[boxfish-recommend-provider] [getHistory] 应答学习历史. ID=[{}] DATA=[{}]", userId, response);

        return response;
    }

}
