#!/usr/bin/env bash

# wait for consul
echo "Connecting to Consul at http://$DISCOVERY_HOST:$DISCOVERY_PORT"
resp_code=$(wget --retry-connrefused --waitretry=5 --read-timeout=15 --tries=10 http://$DISCOVERY_HOST:$DISCOVERY_PORT/v1/agent/members || echo 'error')
[[ -z "$resp_code" ]] && echo 'Consul is running' || exit 1

# start nginx
nginx -g "daemon off;" &

# watch for consul service changes and reload nginx
/usr/local/bin/consul-template -config=/usr/local/app-config/consul-template-config.hcl -log-level=debug