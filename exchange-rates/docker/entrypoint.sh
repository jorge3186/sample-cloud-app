#!/usr/bin/env bash

# wait for consul to be running
resp_code=$(wget --retry-connrefused --waitretry=5 --read-timeout=15 --tries=10 http://$CONSUL_HOST:$CONSUL_PORT/v1/agent/members || echo 'error')
echo "RESPONSE = $resp_code"
[[ -z "$resp_code" ]] && echo 'Consul is running' || exit 1

# wait for config to be running
config_code=$(wget --retry-connrefused --waitretry=5 --read-timeout=15 --tries=10 http://$CONFIG_HOST:$CONFIG_PORT/actuator/health || echo 'error')
[[ -z "$config_code" ]] && echo 'Config service is running' || exit 1

java -jar -DCOSUL_HOST=$CONSUL_HOST -DCONSUL_PORT=$CONSUL_PORT /home/exrtuser/exchange-rates.jar