'use strict';

const fs = require('fs');
const uuid = require('uuid');
const getPort = require('get-port');

async function generate() {
    var port = await getPort();

    let data = {
        ID: `sample-ui-${uuid.v4()}`,
        Name: 'sample-ui',
        Tags: [
            "app=sample-ui",
            "secure=false"
        ],
        Address: process.env.HOSTNAME,
        Port: port,
        Meta: {},
        EnableTagOverride: false,
        Check: {
            HTTP: `http://${process.env.HOSTNAME}:${port}`,
            Interval: '30s'
        },
        Weights: {
            Passing: 10,
            Warning: 1
        }
    };

    fs.writeFileSync('consul-service.json', JSON.stringify(data));

    console.log(port);
}

generate();

