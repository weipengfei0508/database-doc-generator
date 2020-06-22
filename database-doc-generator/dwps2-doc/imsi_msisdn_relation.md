# imsi与msisdn关系表(imsi_msisdn_relation)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|id|int4||否|null|
|imsi|varchar||是|imsi|
|msisdn|varchar||是|msisdn 手机号|
|create_date|date||否|创建时间|
|update_date|date||否|更新时间|
|source|bpchar||否|数据来源(0-设备，1-三方接口)|
