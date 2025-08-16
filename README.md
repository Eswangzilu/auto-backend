# Automic clock machine

```
Author: ZILU WANG
```

## User Management REST API Docs

### Basic Information
- **Base URL**: &nbsp;`/management/reg`
- **Cross Origin**: &nbsp;`http://localhost:3000`
- **Controller Name**: &nbsp;`Management.java`

### API END-POINT list

#### 1. Get all users

- **Endpoint**: `GET /management/reg/users`
- **Full Path**: `http://localhost:8080/management/reg/users`

##### Description
Acquire all users.

##### Request
- Method: &nbsp;`GET`
- Headers:
  - `Origin`: `http://localhost:3000`

##### Response
- Status: `200 OK`
- Response: User[&nbsp;]
  ```json
  [
    {
      "userId": "number",
      "accountName": "string",
      "accountPwd": "string" 
    }
  ]
  ```

#### 2. Add users

- **Endpoint**: `POST /management/reg/add`
- **Full Path**: `http://localhost:8080/management/reg/add`

##### Description
Add users.

##### Request
- Method: &nbsp;`POST`
- Headers:
  - `Content-Type`: `application/json`
  - `Origin`: `http://localhost:3000`
- Payloads: User[&nbsp;]
  ```json
  [
    {
      "accountName": "string",
      "accountPwd": "string" 
    }
  ]
  ```

##### Response
- Status: `200 OK`
- Response: User[&nbsp;]
  ```json
  [
    {
      "userId": "number",
      "accountName": "string",
      "accountPwd": "string" 
    }
  ]
  ```

#### 3. Update users

- **Endpoint**: `POST /management/reg/exchange`
- **Full Path**: `http://localhost:8080/management/reg/exchange`

##### Description
Update users.

##### Request
- Method: &nbsp;`POST`
- Headers:
  - `Content-Type`: `application/json`
  - `Origin`: `http://localhost:3000`
- Payloads: User[&nbsp;]
  ```json
  [
    {
      "accountName": "string",
      "accountPwd": "string" 
    }
  ]
  ```

##### Response
- Status: `200 OK`
- Response: number
  ```json
  2
  ```

#### 4. Delete users

- **Endpoint**: `POST /management/reg/delete`
- **Full Path**: `http://localhost:8080/management/reg/delete`

##### Description
Delete users and this function will return users who are NOT deleted.

##### Request
- Method: &nbsp;`POST`
- Headers:
  - `Content-Type`: `application/json`
  - `Origin`: `http://localhost:3000`
- Payloads: User[&nbsp;]
  ```json
  [
    {
      "accountName": "string" 
    }
  ]
  ```

##### Response
- Status: `200 OK`
- Response: User[&nbsp;]
  ```json
  [
    {
      "userId": "number",
      "accountName": "string",
      "accountPwd": "string"
    }
  ]
  ```

  #### 5. Status check

- **Endpoint**: `GET /check`
- **Full Path**: `http://localhost:8080/check`

##### Description
Get the status of server, if it successes, it will return a string code "200" and a server running time in ms.

##### Request
- Method: &nbsp;`GET`
- Headers:
  - `Origin`: `http://localhost:3000`

##### Response
- Status: `200 OK`
- Response: HealthResponse[&nbsp;]
  ```json
  [
    {
      "status": "string",
      "time": "number"
    }
  ]
  ```
