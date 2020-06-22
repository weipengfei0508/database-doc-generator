# alarm_sendmsg_record(alarm_sendmsg_record)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|id|int4||否|null|
|tel|varchar||是|手机号码|
|send_time|int4||是|发送时间|
|send_num|int4||是|短信发送次数|
|content|varchar||是|发送内容|
|created_at|int4||否|创建时间|
|is_success|int4||是|是否发送成功(0-未成功，1已成功)|
|record_id|int4||是|预警id|
|status_code|varchar||是|调用短信接口返回码|
