# PicPayRest

Solution for the Back End challenge provided by PicPay, this repository was developed only for studying purposes, aiming to have a better understanding about the Spring framework.
More info about the challenge can be found at the following link: [pic-pay-desafio-backend](https://github.com/PicPay/picpay-desafio-backend).

## Endpoints

As of now, the API provides the following endpoints:

<pre>GET /api/users - Lists all saved users</pre>

```json
[
  {
    "id": 1,
    "name": "Ellyan",
    "document": "22222222224111",
    "userType": "MERCHANT",
    "balance": 50000.00,
    "email": "ellyan@email.com",
    "createdAt": "2024-07-06T18:35:23.758619"
	},
	{
    "id": 2,
    "name": "Joao",
    "document": "11111111111",
    "userType": "COMMON",
    "balance": 50000.00,
    "email": "joao@email.com",
    "createdAt": "2024-07-06T18:35:43.845148"
	}
]
```

<pre>POST /api/users/create - Creates a new user</pre>

```json
{
	"name": "ellyan",
	"document": "11111111111",
	"balance": 50000,
	"email": "ellyan@email.com",
	"password": "123456"
}
```

<pre>POST /api/transactions/create - Creates a new transaction between users (only common users are allow to perform transactions)</pre>

```json
{
	"payerDocument": "11111111111",
	"payeeDocument": "11111111112",
	"amount": 200
}
```
