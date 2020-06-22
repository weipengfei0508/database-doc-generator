# sysuser_operator_log(sysuser_operator_log)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|id|int4||否|null|
|username|varchar||是|用户名|
|user_id|int4||是|用户id|
|role|bpchar||是|用户类型（0-设备，1-节点 ,3-所有)|
|operation_record|varchar||是|操作内容|
|update_date|timestamp||否|创建时间|
|create_date|timestamp||否|更新时间|
