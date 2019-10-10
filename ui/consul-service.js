'use strict';

const fs = require('fs');
const uuid = require('uuid');
const getPort = require('get-port');

var generatePort = true;
if (process.argv.length > 3) {
    generatePort = false;
}

async function generate() {
    var port = await getPort();
    port = generatePort ? port : process.argv[2]

    let data = {
        ID: `sample-ui-${uuid.v4()}`,
        Name: 'sample-ui',
        Tags: [
            "app=sample-ui",
            "secure=false"
        ],
        Address: process.argv[process.argv.length-1],
        Port: port,
        Meta: {},
        EnableTagOverride: false,
        Check: {
            HTTP: `http://${process.argv[process.argv.length-1]}:${port}`,
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

