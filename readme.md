<h2>Настройка</h2>

1. Первоначально необходимо склонировать репозиторий сервиса с помощью команды:

```bash
git clone https://github.com/KlimovMaksim/Otp-Service
```

2. Для корректной работы интеграции приложения с SMTP-сервисом необходимо на сайте https://ethereal.email/ создать почтовый ящик.
    <br>Далее в файле ***application-dev.yml*** установить конфигурационные свойства для его использования:

```properties
  mail:
    host: smtp.ethereal.email
    port: 587
    username: monique.reynolds@ethereal.email
    password: rgxJuRerQ74yBM6uRD
```

3. Установить в Intellij Idea параметры запуска
![img.png](screens/img.png)
![img_1.png](screens/img_1.png)

4. Сервис запускается на порту 8080. Пример вызова api: localhost:8080/auth/register
5. Для авторизации надо в каждом запросе, кроме создания пользователя и авторизации, добавлять header 
```json
{
  Authorization: "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QG1haWwucnUiLCJpYXQiOjE3MzY1NTIzMjAsImV4cCI6MTczNjU1NDEyMH0.LnvM2b-2SedS3kO4F8JA_hxJGby1ejCVrfSxs-GXTXc_FDxgGvLZbhvfapi1dtV6BNO_e4p3dCeRpQ5Rj3zRmQ"
}
```


<h2 >API Endpoints</h2>
<h3>1. Создание пользователя с ролью ADMIN или USER</h3>
в поле login надо указывать валидную электронную почту, иначе интеграция с SMTP-сервисом не будет работать
</br>**URL**: localhost:8080/auth/register POST
</br>**REQUEST**
```json
{
  "fullName": "test1",
  "login": "test1@mail.ru",
  "password": "test1",
  "role": "USER"
}
```
</br>**RESPONSE**
```json
{
    "id": "0cbe332c-dbc1-44f6-a53e-f21a3df04733",
    "login": "test1@mail.ru",
    "role": "USER"
}
```

<h3>2. Аутентификация пользователя в системе</h3>
**URL**:localhost:8080/auth/login POST
</br>**REQUEST**
```json
{
  "login": "test1@mail.ru",
  "password": "test1"
}
```
</br>**RESPONSE**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MUBtYWlsLnJ1IiwiaWF0IjoxNzQ3MTAxNDkyLCJleHAiOjE3NDcxMDUwOTJ9.0ZiXCJuRWg-JM-eYuT3ox3nBm9BvzHvVKSUCKcrkmQb5N27jHf2EalZE43ZgDxOfelrijBmlPFtjc03Jwi7HgA"
}
```

<h3>3. Изменение конфигурации otp кода</h3>
**URL**:localhost:8080/otpConfiguration PUT
</br>**REQUEST**
```json
{
  "otpLength": 7,
  "otpExpirationMinutes": 7
}
```
</br>**RESPONSE**
```json
{
  "otpLength": 7,
  "otpExpirationMinutes": 7
}
```

<h3>4. Получить всех пользователей</h3>
**URL**:localhost:8080/users GET
</br>**RESPONSE**
```json
[
  {
    "id": "81f825d5-e8c5-44ad-9cfb-bb211c7b622b",
    "login": "test1",
    "role": "USER"
  },
  {
    "id": "0cbe332c-dbc1-44f6-a53e-f21a3df04733",
    "login": "test1@mail.ru",
    "role": "USER"
  }
]
```

<h3>5. Удаление пользователя</h3>
**URL**:localhost:8080/users/{USER_ID} DELETE

<h3>6. Получить otp код</h3>
**URL**:localhost:8080/otpCode GET
</br>**RESPONSE**
```json
{
  "code": "i9h5w",
  "expiryDate": "2025-05-13T05:03:22.4690153"
}
```

<h3>7. Проверить otp код</h3>
**URL**:localhost:8080/otpCode/{OTP_CODE}/verify POST
</br>**RESPONSE**
```json
false
```
