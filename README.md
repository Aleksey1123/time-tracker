��� ��� API:
[������������ OpenAPI](./src/openapi.json)

[Swagger UI](https://api.redocly.com/registry/bundle/corp/time-tracker/v1/openapi.yaml?branch=rebuild-1&job=f92dfbb6-666a-4648-b15c-c45f5893cf92)

### CRUD �������� 

##### ������ �������:
�� ��� ��������� ������� ������ ����� ������
- 200 OK
- 404 Not Found
- 409 Conflict -

#### ������ getAllEmployees:

���������� GET ������ �� ������: http://localhost:8080/api/v1/tasks </br>
##### ��������� �������: 
- pageNumber: integer - ����� ����� ����� ������� � 0
- pageSize: integer - ����� �� 1 �� 10, � ���� ������
����� ���������� �������� �� ��������� - 10.
- sortingParam: ���� �� ��������� ����� ��� ���������
employee � task [id, firstName, lastName] � [id, name]
�������������.


#### ������ getEmployeeById:

���������� GET ������ �� ������: http://localhost:8080/api/v1/tasks
��������� �������:
- pageNumber: integer - ����� ����� ����� ������� � 0
- pageSize: integer - ����� �� 1 �� 10, � ���� ������
  ����� ���������� �������� �� ��������� - 10.
- sortingParam: ���� �� ��������� ����� ��� ���������
  employee � task [id, firstName, lastName] � [id, name]
  �������������