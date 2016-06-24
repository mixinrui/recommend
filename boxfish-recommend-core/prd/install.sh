#!/usr/bin/env bash


scp /Users/lupee/Desktop/install/*  root@121.196.222.156:/var/boxfish
ssh root@121.196.222.156 "source /etc/profile; service boxfish-recommend-provider restart; service boxfish-recommend-core restart"

scp /Users/lupee/Desktop/install/*  root@121.196.222.237:/var/boxfish
ssh root@121.196.222.237 "source /etc/profile; service boxfish-recommend-provider restart; service boxfish-recommend-core restart"

scp /Users/lupee/Desktop/install/*  root@121.43.184.255:/var/boxfish
ssh root@121.43.184.255 "source /etc/profile; service boxfish-recommend-provider restart; service boxfish-recommend-core restart"

#ln -s /var/boxfish/boxfish-recommend-core-1.0.jar /etc/init.d/boxfish-recommend-core
#ln -s /var/boxfish/boxfish-recommend-provider-1.0.jar /etc/init.d/boxfish-recommend-provider
