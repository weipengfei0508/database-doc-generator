# 开关机查询记录表(alarm_mobile_poweron)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|id|int4||否|主键|
|phone_num|varchar||是|手机号码|
|status|int4||否|状态（-1-添加默认状态  0-关机   1-开机 2-查询中）|
|return_time|int8||是|返回时间|
|created_by|int4||否|创建人|
|created_at|int8||否|创建时间|
|power_on_time|int8||是|开机时间|
|remark|varchar||是|备注|
|query_time|int8||是|查询时间|
|is_send|int4||是|是否发送开关机提醒短信(0-未发送，1已发送)|
