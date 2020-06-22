# sys_user(sys_user)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|id|int4||否|id号|
|username|varchar||否|用户名|
|password|varchar||否|密码|
|create_date|date||是|创建日期|
|update_date|date||是|更新日期|
|role|bpchar||是|用户类型（0-设备，1-节点 ,3-所有）|
