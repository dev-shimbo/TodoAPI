-- DB切り替え
\c tododb

-- テーブル作成
CREATE TABLE  api.todo (
  id serial NOT NULL,
  todo_context varchar(500),
  post_user_name varchar(100),
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  PRIMARY KEY(id)
);

-- 権限追加
GRANT ALL PRIVILEGES ON api.todo TO apiUseDev;

-- サンプルレコード作成
INSERT INTO api.todo VALUES(1, '御飯食べる', 'あすからんぐれー', now());
INSERT INTO api.todo VALUES(2, '眠いので寝ること', 'HappyCat', now());
INSERT INTO api.todo VALUES(3, '仕事を探す', '限界PJから脱出したい人', now());

-- DBコンテナ初期化の際にIdシーケンスが狂うので
-- シーケンスを新たなに作成して修正する
create sequence todo_seq;
select setval('todo_seq', (select max(id) from api.todo));
-- シーケンスをテーブルに適用する
ALTER TABLE api.todo ALTER COLUMN id SET DEFAULT nextval('todo_seq');

-- TOKENテーブル作成
CREATE TABLE  api.token (
  token_id serial NOT NULL,
  access_token varchar(100),
  created_at TIMESTAMP,
  PRIMARY KEY(token_id)
);