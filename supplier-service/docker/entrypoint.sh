#!/bin/sh

if [[ -d /run/secrets ]]; then
  for secret in /run/secrets/*
  do
    source <(cat "${secret}" | awk '{print "export " $1$2}')
  done
fi;

sleep 10 && java -jar /app/supplier-service.jar