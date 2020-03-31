-- This is the initial script for setting up the data base for this plugin.
-- For specific data base dialects, place sql scripts in the sub directory init/{vendor}

CREATE TABLE t_plugin_jmemo (
  pk          INTEGER NOT NULL,
  created     TIMESTAMP WITHOUT TIME ZONE,
  deleted     BOOLEAN NOT NULL,
  last_update TIMESTAMP WITHOUT TIME ZONE,
  memo        CHARACTER VARYING(4000),
  subject     CHARACTER VARYING(1000),
  tenant_id   INTEGER,
  owner_fk    INTEGER
);

ALTER TABLE t_plugin_jmemo
  ADD CONSTRAINT t_plugin_jmemo_pkey PRIMARY KEY (pk);

CREATE INDEX idx_fk_t_plugin_jmemo_owner_fk
  ON t_plugin_jmemo (owner_fk);

CREATE INDEX idx_fk_t_plugin_jmemo_tenant_id
  ON t_plugin_jmemo (tenant_id);

ALTER TABLE t_plugin_jmemo
  ADD CONSTRAINT fkkt72ignhv3m4spo048i722roy FOREIGN KEY (tenant_id) REFERENCES t_tenant (pk);

ALTER TABLE t_plugin_jmemo
  ADD CONSTRAINT fkktub13xpknmrxc8402idlxw0d FOREIGN KEY (owner_fk) REFERENCES t_pf_user (pk);
