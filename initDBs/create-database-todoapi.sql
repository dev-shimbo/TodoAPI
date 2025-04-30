-- DB作成
CREATE DATABASE tododb; 

-- 作成したDBへ切り替え
\c tododb

-- スキーマ作成
CREATE SCHEMA api;

-- ロールの作成
CREATE ROLE apiUseDev WITH LOGIN PASSWORD 'postgres';

-- 権限追加
GRANT ALL PRIVILEGES ON SCHEMA api TO apiUseDev;
