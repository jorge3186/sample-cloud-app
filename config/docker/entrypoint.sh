#!/usr/bin/env bash

java -jar -DCONFIG_PROFILES=$CONFIG_PROFILES -DGIT_USER=$GIT_USER -DGIT_PASSWORD=$GIT_PASSWORD /home/configuser/config.jar