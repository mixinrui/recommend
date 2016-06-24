#!/usr/bin/env bash

p1=(
    '{"difficulty":["LEVEL_1"]}'
    '{"difficulty":["LEVEL_2"]}'
    '{"difficulty":["LEVEL_3"]}'
    '{"difficulty":["LEVEL_4"]}'
    '{"difficulty":["LEVEL_5"]}'
    )

#p2=(localhost:8002)
#p2=(online-api.inside.boxfish.cn)
p2=(121.43.181.130:8002)

for  i in  ${p1[@]};do
     for j in ${p2[@]};do
       echo `curl -X POST -H "Content-Type: application/json" -d "$i"  "http://$j/recommend/provider/lessons/candidate"`
     done
done
