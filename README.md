# Remote Monitoring and Management

A Remote Monitoring and Management (RMM) platform helps IT professionals manage a
fleet of Devices with Services associated with them.

## Security

In order to perform any request for this API, a basic authentication mechanism is required.
 
- User: test
- Password: test

## Swagger

In order to test and try it out the API, a swagger could be find in [http://localhost:8080/swagger-ui.html]([http://localhost:8080/swagger-ui.html])

## Device Management

See further below the operations to create, update, list, delete a device and also to retrive the cost of the device according the services related with it.

__POST /devices__  - Creates a new device

*Request Body*

```
{
	systemName: "Windows",
	type: "Desktop"
}
```
- systemName: String
- type: String

*Response Header* - Location (e.g. /devices/2)

*Response Body*  -  __No__

201 - Device was created successfully

400 - It is missing one of than parameters

401 - Unauthorized

409 - There is another device already added with the same system Name and name

500 - Internal Server Error

---

__GET /devices__  - Retrieves a list of all devices with pagination

*Request Params*

- page: page number - defult: 0 - required: false - type: Integer
	
- size: page size - default: 10 - required: false - type: Integer

*Request Body*  -  __No__

*Response Body*

```
{
    "content": [
        {
            "id": 1,
            "systemName": "string",
            "type": "string",
            "services": [
                {
                    "id": 1,
                    "name": "back",
                    "price": 2.00
                }
            ]
        }
    ],
    "pageable": {
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 10,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalElements": 1,
    "totalPages": 1,
    "size": 10,
    "number": 0,
    "first": true,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "numberOfElements": 1,
    "empty": false
}

```

200 - Devices and pagination are retrieved

401 - Unauthorized

500 - Internal Server Error

---

__GET /devices/{deviceId}__  - Retrieves a device

*PathParam* 
- deviceId - The device identification ( ID ) - required: true

*Request Body*  -  __No__

*Response Body*

```
{
    "id": 1,
    "systemName": "string",
    "type": "string",
    "services": [
        {
            "id": 1,
            "name": "back",
            "price": 2.00
        }
    ]
}

```

200 - Service is retrieved

401 - Unauthorized

404 - Device was not found

500 - Internal Server Error

---

__PUT /devices/{deviceId}__  - Updates a device

*PathParam* 
- deviceId - The device identification ( ID ) - required: true

*Request Body*

```
{
	systemName: "Windows",
	type: "Desktop"
}
```
- systemName: String
- type: String

*Response Body*  -  __No__


204 - Device was updated successfully

401 - Unauthorized

404 - Device was not found

409 - There is another device already added with the same system Name and name

500 - Internal Server Error

---

__DELETE /devices/{deviceId}__  - Deletes a device

*PathParam* 
- deviceId - The device identification ( ID ) - required: true

*Request Body*  -  __No__

*Response Body*  -  __No__

204 - Device was deleted successfully

401 - Unauthorized

404 - Device was not found

500 - Internal Server Error

---

__PUT /devices/{deviceId}/services/{serviceId}__  - Adds/Removes a service from the device's services list

*PathParam* 
- deviceId - The device identification ( ID ) - required: true
- serviceId - The service identification ( ID ) - required: true

*Request Body*  -  __No__

*Response Body*  -  __No__

*If the service*  __is not__  *in the device's service list, it would be added.*

*If the service*  __is__  *in the device's service list, it would be removed.*

204 - Service was added/removed successfully

401 - Unauthorized

404 - Device/Service was not found

500 - Internal Server Error

---

__GET /devices/{deviceId}/cost__  - Retrieves the cost of all services that were added for a device 

*PathParam* 
- deviceId - The device identification ( ID ) - required: true

*Request Body*  -  __No__

*Response Body*

```
8.00

```

200 - The cost was calculated successfully

401 - Unauthorized

404 - Device was not found

500 - Internal Server Error

## Service Management

See further below the operations to create, update, list, delete a service.

__POST /services__  - Creates a new service

*Request Body*

```
{
	name: "Backup",
	price: 5.00
}
```
- name: String
- price: double

*Response Header* - Location (e.g. /services/2)

*Response Body*  -  __No__


201 - Service was created successfully

400 - It is missing one of than parameters

401 - Unauthorized

409 - There is another service already added with the same name

500 - Internal Server Error

---

__GET /services__  - Retrieves a list of all services with pagination

*Request Params*

- page: page number - defult: 0 - required: false - type: Integer
	
- size: page size - default: 10 - required: false - type: Integer

*Request Body*  -  __No__

*Response Body*

```
{
    "content": [
        {
            "id": 1,
            "name": "back",
            "price": 2.00
        },
        {
            "id": 2,
            "name": "tes",
            "price": 2.00
        },
        {
            "id": 3,
            "name": "tesw",
            "price": 2.00
        }
    ],
    "pageable": {
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 10,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalElements": 3,
    "totalPages": 1,
    "size": 10,
    "number": 0,
    "first": true,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "numberOfElements": 3,
    "empty": false
}

```

200 - Services and pagination are retrieved

401 - Unauthorized

500 - Internal Server Error

---

__GET /services/{serviceId}__  - Retrieves a service

*PathParam* 
- serviceId - The service identification ( ID ) - required: true

*Request Body*  -  __No__

*Response Body*

```
{
    "id": 1,
    "name": "back",
    "price": 2.00
}

```

200 - Service is retrieved

401 - Unauthorized

404 - Service was not found

500 - Internal Server Error

---

__PUT /services/{serviceId}__  - Updates a service

*PathParam* 
- serviceId - The service identification ( ID ) - required: true

*Request Body*

```
{
	name: "Backup",
	price: 10.00
}
```

- name: String
- price: double

*Response Body*  -  __No__


204 - Service was updated successfully

401 - Unauthorized

404 - Service was not found

409 - There is another service already added with the same name

500 - Internal Server Error

---

__DELETE /services/{serviceId}__  - Deletes a service

*PathParam* 
- serviceId - The device identification ( ID ) - required: true

*Request Body*  -  __No__

*Response Body*  -  __No__

204 - Service was deleted successfully

401 - Unauthorized

404 - Service was not found

500 - Internal Server Error







