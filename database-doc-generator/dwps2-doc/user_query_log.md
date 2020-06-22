# user_query_log(user_query_log)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|id|int4||否|用户查询日志表id|
|user_id|int4||是|查询用户的id|
|parameters|varchar||是|查询内容参数|
|result|varchar||是|返回查询结果|
|query_name|varchar||是|使用系统功能名称 1区域查询，2城市查询|
|start_time|int8||是|开始查询时间|
|end_time|int8||是|返回查询结果的时间|
|task_id|varchar||是|任务id|
