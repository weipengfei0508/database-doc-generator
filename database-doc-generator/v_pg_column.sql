 SELECT (n.nspname)::character varying(32) AS schemaname,
    (tab.relname)::character varying(60) AS tablename,
    (col.attname)::character varying(32) AS colname,
    (col.attnum)::integer AS col_position,
    col.attnotnull AS not_null,
    (format_type(col.atttypid, col.atttypmod))::character varying(64) AS type,
    (col_description(col.attrelid, (col.attnum)::integer))::character varying(256) AS comment
   FROM ((pg_attribute col
     JOIN pg_class tab ON ((col.attrelid = tab.oid)))
     LEFT JOIN pg_namespace n ON ((n.oid = tab.relnamespace)))
  WHERE ((tab.relkind = 'r'::"char") AND (col.attnum > 0) AND (NOT col.attisdropped))