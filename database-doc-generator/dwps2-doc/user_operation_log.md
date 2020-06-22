# 用户操作日志(user_operation_log)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|user_id|int4||是|用户id|
|user_type|int4||是|账户类型  1试用账号 2正式用户 3节点账户|
|operation_record|varchar||是|操作内容|
|created_at|int4||是|创建时间|
|updated_at|int4||是|更新时间|
|id|int4||否|null|
