# 推荐服务

## 系统结构
    系统划分为缓存层(buffer)、推荐层(recommend)。
    在学生自学推荐服务(tradition)中,缓存层负责记录App端推荐列表状态,避免已经发送给App,且未学过的课程被重复推荐。
    在在线授课推荐服务(online)中,缓存层负责记录排课系统已经分配的课程,避免已经分配,且未上过的课被重复推荐。
    
## 学生自学部分业务逻辑
    App端每次调用:
        如果上报内容不为空,则在缓存中根据上报的课程ID删除相应课程。
        
        判断课程跳过标示:
            如果是true,则判断是否连续第二次跳过:
                如果是连续第二次跳过,则返回状态。
                如果不是连续第二次跳过,继续执行后续逻辑。
            如果不是true,则设置用户响应标示位为false。
            
        判断缓存区课程数量:
            如果大于5,则什么都不干。
            如果小于5,则调用推荐层,拉取10个课程加入缓存区,并将该10课返回给App端。

## 在线授课部分业务逻辑
    排课系统每次调用:
        根据查询条件,从推荐层获取课程,加入缓存区后,返回给排课系统。
    
## 技术实现
    缓存层采用一个数据表进行记录,通过渠道字段channel(tradition、online),区分缓存目标。在过滤课程时直接根据UserId进行过滤。
    缓存层需要通过定时任务,用学过的课程清洗缓存课程,因为在线上课没有已上完课程的上报渠道。

# 配置信息
## 学生自学推荐课程
    请求方式: POST
    请求地址: http://online-api.boxfish.cn/recommend/core/tradition?access_token=XXX
    请求参数:
        access_token 
        lesson_id        POST参数
        is_skipped       POST参数
        
    请求示例:
        http://online-api.boxfish.cn/recommend/core/tradition?access_token=pziNgnmW2o

    CURL示例:
        curl -X POST -H "Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW" -F "lesson_id=L3NoYXJlL3N2bi9GdW5jdGlvbiDor63oqIDkuqTpmYXlm7Dpmr4vMzIyLuWmguS9leihqOi-vuWBmuafkOS6i-acieWbsOmavu-8ny54bHN4" -F "is_skipped=1" "http://online-api.boxfish.cn/recommend/core/tradition?access_token=pziNgnmW2o"

## 在线授课推荐课程
    请求方式: POST
    请求地址: http://online-api.boxfish.cn/recommend/core/online/user_id/{user_id}/index/{index}
    请求参数:
        user_id         路径变量
        index           路径变量
        
    请求示例:
        http://online-api.boxfish.cn/recommend/core/online/user_id/1297104/index/1
        
    CURL示例:
        curl -X POST "http://online-api.boxfish.cn/recommend/core/online/user_id/1297104/index/1"

## 应用服务部署地址
    121.43.181.130
    121.43.169.140
    121.43.175.227

## 数据请求依赖路径
    用户偏好: http://api.online.test.boxfish.cn/preference/getting/user/{user_id}
    可用课程: http://online-api.inside.boxfish.cn/recommend/provider/lessons/candidate
    学习历史: http://online-api.inside.boxfish.cn/recommend/provider/history/{user_id}
    帐号转换: http://online-api.boxfish.cn/passbook/user/me

