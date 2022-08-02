# Zinworks ATM

Creates a Simple Spring Boot project to simulate an ATM through REST API's.

# Requirements

Java > 1.8 is mandatory to run the project. If you want also to check and debug the code, you can use an IDE like IntelliJ
(recommended) or Eclipse.

# Installation 🛠️

You can clone the project from this link:

```sh
git clone https://github.com/valerio65xz/zinworks-atm.git
```

# Usage ℹ️

If you want to just execute the project, open a terminal in your installation folder and type:

```sh
java -jar ATM.jar
```

then if you use Postman, you may import `Zinworks.postman_collection.json` to import the collection.

if you don't have or don't want to use postman:
* The URL to call to get the balance: `http://localhost:8080/atm/balance`
* The URL to fullfil a withdrawal request: `http://localhost:8080/atm/withdraw/{amount}`
* API's type: `POST`

The body is **mandatory** and you have to define the *accountNumber* and the *pin*

```sh
{
    "accountNumber": "123456789",
    "pin": "1234"
}
```

# Docs 📚

You can find the OpenAPI 3.0 Docs at `http://localhost:8080/swagger-ui/index.html`.
