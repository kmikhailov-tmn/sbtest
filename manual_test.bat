@echo off
rem curl -H Content-type: application/json" -X POST -d  "{\"customer\" : \"John\", \"lastName\" : \"Smith\"}"  http://localhost:8080/putOrder1

type test.json |curl -H "Content-type: application/json" -X POST --data-binary @- http://localhost:8080/putOrder

