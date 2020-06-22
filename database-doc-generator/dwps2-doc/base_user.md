# 用户表(base_user)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|id|int4||否|null|
|username|varchar||否|用户名|
|password|varchar||否|密码|
|created_at|int8||否|创建时间|
|updated_at|int8||否|更新时间|
|salt|varchar||否|加密salt|
|remark|varchar||是|备注|
|validays|int4||是|有效天数|
|is_delete|int4||是|删除标记(1--删除，0正常)|
|total_times|int4||是|总次数|
|created_by|int4||是|创建人|
|seach_times|int4||是|查询次数|
|expiration|int8||是|有效期（时间类型，根据有效天数算出来）|
|role|int4||是|角色 (1--普通用户,2--节点管理员 3--超级管理员)|
|user_status|int4||是|用户状态1正常 2锁定 3欠费 4异常|
|user_type|int4||是|账户类型：1试用账号 2正式用户|
|owner|int4||是|属于谁管理的用户|
|total_price|numeric||是|单价|
|permissions|varchar||是|权限|
