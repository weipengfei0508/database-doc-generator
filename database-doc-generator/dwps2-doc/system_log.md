# 系统运行日志(system_log)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|id|int2||否|id|
|log_type|int4||是|日志类型 1系统运行日志，2目标告警日志 3其它|
|log_content|varchar||是|日志信息内容|
|created_at|int4||是|创建时间|
|updated_at|int4||是|更新时间|
