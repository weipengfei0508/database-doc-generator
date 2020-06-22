# 城市预警设置表(alarm_rule)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|id|int4||否|id|
|alarm_type|varchar||否|预警类型(1-进入城市，2离开城市，3.进入或离开)|
|frequency|int4||是|监控频率(0-半小，1-1小时，2-2小时,3-6小时)|
|created_by|int4||否|创建人|
|created_at|int8||否|创建时间|
|updated_at|int8||否|更新时间|
|start_time|int8||是|监控起始时间|
|end_time|int8||是|监控结束时间|
|alarm_times|int4||是|预警总数|
|alarm_name|varchar||是|预警名称|
|case_id|int4||否|案件表id|
|expiration|int4||是|监控有效期|
|is_alarm|int4||是|是否预警(0-否,1-是)|
|fence_type|int4||是|围栏类型(1-城市围栏 2-位置围栏)|
|notice_tel|varchar||是|通知手机号码|
|time_point|varchar||是|监控时间段|
