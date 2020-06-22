# imsi_user(imsi_user)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|id|int4||否|接口用户表id|
|username|varchar||否|用户名|
|password|varchar||否|密码|
|times|int4||否|使用次数|
|expire_date|date||否|有效期|
|remark|varchar||是|备注|
|status|bpchar||否|状态 0-停用 1启用|
|update_date|timestamp||否|修改时间|
|create_date|timestamp||否|添加时间|
|user_type|bpchar||否|用户类型（0-设备，1-节点）|
|query_time|int4||否|查询次数|
