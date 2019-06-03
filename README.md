# A voting system for deciding where to have lunch

## Description 
* 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

## REST API

Авторизация (доступ для всех):

`curl -i -X POST -d username=user -d password=password -c c:\curl\cookies.txt http://localhost:8080/login
`

Проголосовать (доступ для имеющих роль Пользователь):

`curl -i -X POST -d rate=5.0 http://localhost:8080/api/vote/restaurantId
`

Также с помощью Spring Data Rest сформированы следующие rest-api (доступ для имеющих роль Администратор):


    "restaurants" : {
      "href" : "http://localhost:8080/api/restaurants"
    },
    "users" : {
      "href" : "http://localhost:8080/api/users"
    },
    "menus" : {
      "href" : "http://localhost:8080/api/menus"
    },
    "menuItems" : {
      "href" : "http://localhost:8080/api/menuItems"
    }