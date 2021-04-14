-- This is the initial script for setting up the data base for this plugin.
-- For specific data base dialects, place sql scripts in the sub directory init/{vendor}

CREATE TABLE t_plugin_ktmemo (
  pk          INTEGER NOT NULL,
  created     TIMESTAMP WITHOUT TIME ZONE,
  deleted     BOOLEAN NOT NULL,
  last_update TIMESTAMP WITHOUT TIME ZONE,
  memo        CHARACTER VARYING(4000),
  subject     CHARACTER VARYING(1000),
  owner_fk    INTEGER
);

ALTER TABLE t_plugin_ktmemo
  ADD CONSTRAINT t_plugin_ktmemo_pkey PRIMARY KEY (pk);

CREATE INDEX idx_fk_t_plugin_ktmemo_owner_fk
  ON t_plugin_ktmemo (owner_fk);

ALTER TABLE t_plugin_ktmemo
  ADD CONSTRAINT fkktub13xpknmrxc8402idlxw0d FOREIGN KEY (owner_fk) REFERENCES t_pf_user (pk);
