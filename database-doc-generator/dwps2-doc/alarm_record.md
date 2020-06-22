# alarm_record(alarm_record)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|id|int4||否|id|
|person_id|int4||是|预警目标表id|
|alarm_type|int4||是|预警类型(1-进入城市，2离开城市)|
|rail|varchar||是|围栏（如湖北省武汉市）|
|alarm_time|int8||是|预警时间|
|area_id|varchar||否|区域编码|
|alarm_id|int4||否|城市预警规则表id|
|task_id|varchar||否|任务唯一id|
|created_at|int8||否|创建时间|
|created_by|int4||否|创建用户|
|in_area|int4||否|是否在设置目标区域|
|batchno|varchar||否|查询批次号|
|case_id|int4||是|案件号id|
|fence_type|int4||否|围栏类型(1-城市围栏 2-位置围栏)|
|is_send|int4||是|是否发送短信(0-未发送，1已发送)|
|send_time|int4||是|发送时间|
|is_show|int4||是|是否显示(1显示,2不显示)|
|first_record|int4||是|是否第一次查询(1-是 2-否)|
