# 位置查询(position_query)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|task_id|varchar||否|任务id|
|phonenum|varchar||是|手机号码|
|province|varchar||是|省|
|city|varchar||是|市|
|lat|varchar||是|纬度|
|lon|varchar||是|经度|
|created_by|int4||是|创建人|
|created_at|int8||是|创建时间|
|updated_at|int8||是|更新时间|
|distance|int4||是|查询结果（单位：公里）：1: [0,3],2: (3-10],3: (10,20],4: (20,50],5: 大于50|
|error_code|varchar||是|错误代码|
|error_info|varchar||是|错误信息|
|operater|varchar||是|运营商|
|query_type|int4||是|1区域查询，2城市查询|
|price|numeric||是|单价|
