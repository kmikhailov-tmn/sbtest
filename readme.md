# Task

1.Требуется разработать REST микросервис используя Spring Framework, который будет принимать 1 документ вида:

{

"seller":"123534251",

"customer":"648563524",

"products":[{"name":"milk","code":"2364758363546"},{"name":"water","code":"3656352437590"}]

}

В данном документе все поля обязательны. Идентификатор seller/customer - строка 9 символов,

идентификатор продукта - строка 13 символов.

В случае, если документ не корректен, возвращать сообщения об ошибках в документе

