Это мой API:
[Спецификация OpenAPI](./src/openapi.json)

[Swagger UI](https://api.redocly.com/registry/bundle/corp/time-tracker/v1/openapi.yaml?branch=rebuild-1&job=f92dfbb6-666a-4648-b15c-c45f5893cf92)

### CRUD операции 

##### Ответы сервера:
На все возможные запросы сервер может выдать
- 200 OK
- 404 Not Found
- 409 Conflict -

#### Пример getAllEmployees:

Отправляем GET запрос по ссылке: http://localhost:8080/api/v1/tasks </br>
##### Параметры запроса: 
- pageNumber: integer - любое целое число начиная с 0
- pageSize: integer - число от 1 до 10, в ином случае
будет выставлено значение по умолчанию - 10.
- sortingParam: одно из возможных полей для сущностей
employee и task [id, firstName, lastName] и [id, name]
соответсвенно.


#### Пример getEmployeeById:

Отправляем GET запрос по ссылке: http://localhost:8080/api/v1/tasks
Параметры запроса:
- pageNumber: integer - любое целое число начиная с 0
- pageSize: integer - число от 1 до 10, в ином случае
  будет выставлено значение по умолчанию - 10.
- sortingParam: одно из возможных полей для сущностей
  employee и task [id, firstName, lastName] и [id, name]
  соответсвенно