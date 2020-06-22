# imsi_search_log(imsi_search_log)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|id|int4||否|主键|
|imsi|varchar||是|imsi|
|msisdn|varchar||是|msisdn 手机号|
|cmdtype|bpchar||否|命令类型 0 表示手机号查IMSI, 1 表示IMSI查手机号|
|query_type|bpchar||否|查询类型 （暂时只填0）|
|mstype|bpchar||否|号码类型 0表示手机号，1表示IMSI号|
|dtagcmd|varchar||是|命令字符串|
|rid|int4||是|返回的rid|
|rtime|varchar||是|返回时间|
|create_date|timestamp||否|创建时间|
|update_date|timestamp||否|更新时间|
|created_by|int4||否|创建人|
|restr|varchar||是|返回参数|
|poststr|varchar||是|请求参数|
