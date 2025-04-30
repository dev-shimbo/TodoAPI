# How to run API on local
clone this repo and just run this command
```
docker-compose up -d or docker-compose up --build
```
# API docs
- host: http://localhost:8080
- todo list:GET /api/todo/list
- todo select(id):GET /api/todo/{id}
- todo post:POST /api/todo/create
- todo update:POST /api/todo/update
- todo delete:DELETE /api/todo/delete/{id}
## TOKEN AUTH is unimplemented now. Below urls deprecated
Implemented token generate and expire system only.
If you generate token, you cannot use it for your auth. 
- todo token list /api/tokens
- todo token generate /api/token/generate/{your name}
## RequestBody format
POST TODO
```json
{"todo_context":"eat meals","post_user_name": "Hello-san"}
```
EDIT TODO 
```json
{"id":1,"todo_context":"dont eat meals"}
```
