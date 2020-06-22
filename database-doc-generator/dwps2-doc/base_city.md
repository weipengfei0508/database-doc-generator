# 省市代码表(base_city)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|area_id|varchar||否|城市代码|
|level|int4||否|城市级别：0-省，1-省会城市，2-非省会城市|
|parent_area|varchar||是|null|
|area_name|varchar||是|城市名称|
|parent_id|varchar||是|父级id|
|is_direct_city|bool||是|是否直辖市|
|third_party_city_codes|json||是|第三方厂家自定义的城市代码|
