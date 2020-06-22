# user_login_device(user_login_device)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|id|int4||否|表主键|
|user_id|int4||否|用户id|
|device_sn|varchar||否|设备序列号|
|created_at|int8||否|创建时间|
|status|int4||否|状态（0-解绑,1-绑定）|
|type|int4||否|设备类型(0-手机或平板，1.电脑)|
