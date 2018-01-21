https://travis-ci.org/vampirit/lunchVote.svg?branch=master

# LanchVote

#### Рестораны: 


##### добавить:
    curl --request POST \
      --url http://localhost:8080/restaurants \
      --header 'Authorization: Basic YWRtaW5AbHVuY2guY29tOjEyMzQ1' \
      --header 'Content-Type: application/json' \
      --data '{"name":"Sashlik","address":"Palatka"}'

		  
##### получить все:
    curl --request GET \
      --url http://localhost:8080/restaurants \
      --header 'Authorization: Basic YWRtaW5AbHVuY2guY29tOjEyMzQ1'

##### обновить:
    curl --request PUT \
      --url http://localhost:8080/restaurants/100005 \
      --header 'Authorization: Basic YWRtaW5AbHVuY2guY29tOjEyMzQ1' \
      --header 'Content-Type: application/json' \
      --data '{"id":100005,"name":"MVC Update","address":"Raduga1"}'

##### удалить:
    curl --request DELETE \
      --url http://localhost:8080/restaurants/100005 \
      --header 'Authorization: Basic YWRtaW5AbHVuY2guY29tOjEyMzQ1'

##### получить по id:
    curl --request GET \
      --url http://localhost:8080/restaurants/100004 \
      --header 'Authorization: Basic YWRtaW5AbHVuY2guY29tOjEyMzQ1'


***
  
  
#### Ланчи:

##### добавить:
    curl --request POST \
      --url http://localhost:8080/lunches \
      --header 'Authorization: Basic YWRtaW5AbHVuY2guY29tOjEyMzQ1' \
      --header 'Content-Type: application/json' \
      --data '{"description":"Gamburger","price":45.0,"date":[2018,1,21],"restaurantId":100003}'
		  
##### получить все:
    curl --request GET \
      --url http://localhost:8080/lunches \
      --header 'Authorization: Basic YWRtaW5AbHVuY2guY29tOjEyMzQ1'

##### обновить:
    curl --request PUT \
      --url http://localhost:8080/lunches/100006 \
      --header 'Authorization: Basic YWRtaW5AbHVuY2guY29tOjEyMzQ1' \
      --header 'Content-Type: application/json' \
      --data '{"id":100006,"description":"Voper2","price":120.0,"date":[2018,1,21],"restaurantId":100003}'

##### удалить:
    curl --request DELETE \
      --url http://localhost:8080/lunches/100007 \
      --header 'Authorization: Basic YWRtaW5AbHVuY2guY29tOjEyMzQ1'

##### получить по id:
    curl --request GET \
      --url http://localhost:8080/lunches/100006 \
      --header 'Authorization: Basic YWRtaW5AbHVuY2guY29tOjEyMzQ1'

***

#### Голоса:

##### получить всю статистику за день:
    curl --request GET \
      --url http://localhost:8080/vote \
      --header 'Authorization: Basic YWRtaW5AbHVuY2guY29tOjEyMzQ1'

##### проголосовать\изменить голос:
    curl --request POST \
      --url http://localhost:8080/vote/100006 \
      --header 'Authorization: Basic dXNlckBsdW5jaC5jb206cGFzcw=='
		  
***

Тоже самое но в [postman](https://documenter.getpostman.com/view/3513801/lunch-vote/7TKgY4t)