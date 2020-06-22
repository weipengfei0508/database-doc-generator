 SELECT n.nspname AS schemaname,
    c.relname AS tabname,
    (obj_description(c.relfilenode, 'pg_class'::name))::character varying AS comment
   FROM (pg_class c
     JOIN pg_namespace n ON ((n.oid = c.relnamespace)))
  WHERE (c.relkind = 'r'::"char")