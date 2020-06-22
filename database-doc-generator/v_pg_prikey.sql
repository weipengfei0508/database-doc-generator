 SELECT pg_class.relname AS tab_name,
    pg_constraint.conname AS pk_name,
    pg_attribute.attname AS colname,
    pg_type.typname AS typename
   FROM (((pg_constraint
     JOIN pg_class ON ((pg_constraint.conrelid = pg_class.oid)))
     JOIN pg_attribute ON (((pg_attribute.attrelid = pg_class.oid) AND (pg_attribute.attnum = pg_constraint.conkey[1]))))
     JOIN pg_type ON ((pg_type.oid = pg_attribute.atttypid)))
  WHERE (pg_constraint.contype = 'p'::"char")