#!/usr/bin/env bash

# wait for consul to be running
resp_code=$(wget --retry-connrefused --waitretry=5 --read-timeout=15 --tries=10 http://${CONSUL_HOST}:${CONSUL_PORT}/v1/agent/members || echo 'error')
echo "RESPONSE = $resp_code"
[[ -z "$resp_code" ]] && echo 'Consul is running' || exit 1

# generate PUT body for consul
IPADDR=$(hostname -I | awk '{print $1}')
if [[ "$#" -eq "1" ]]
then
    set PORT_NUM="$1"
    node consul-service.js $1 ${IPADDR}
else
    PORT_NUM=$(node consul-service.js ${IPADDR})
fi

# add ui service to consul
curl --request PUT --data @consul-service.json http://${CONSUL_HOST}:${CONSUL_PORT}/v1/agent/service/register

http-server dist -p ${PORT_NUM}
