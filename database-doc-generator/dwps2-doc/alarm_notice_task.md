# alarm_notice_task(alarm_notice_task)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|task_id|varchar||否|任务id|
|alarm_id|int4||否|预警设置表id|
|person_id|int4||否|用户id|
|tel|varchar||否|手机号|
|person_name|varchar||是|用户名称|
|area_id|varchar||是|查询所在城市编码|
|area_name|varchar||是|查询所在城市名称|
|created_at|int8||否|创建时间|
|updated_at|int8||是|更新时间|
|batchno|varchar||是|查询批次号|
|req_parm|varchar||是|请求参数|
|return_parm|varchar||是|返回参数|
|in_area|int4||是|是否在设置目标区域0为不一致，1为一致，2为省份一致，城市不一致|
|alarm_type|int4||是|预警设置类型(1-进入城市，2离开城市)|
|created_by|int4||否|创建人|
|case_id|int4||否|案件id号|
|parent_areaid|varchar||是|父级区域id|
|parent_areaname|varchar||是|父级名称|
|price|numeric||是|单价|
|fence_type|int4||否|围栏类型(1-城市围栏 2-位置围栏)|
|range_code|int4||是|目标地址距离编码，距离单位为km，例如：1表示[0,3]，2表示(3,10]，3表示(10,20]，4表示(20,50]，5表示大于50|
|errno|varchar||是|错误码|
