#!/bin/bash
./mosquitto_clean.sh
xterm -hold -e "mosquitto_sub -h localhost -t miage1/menez/sensors/light" &
xterm -hold -e "mosquitto_sub -h localhost -t miage1/menez/sensors/temp" &
xterm -hold -e "mosquitto_sub -h localhost -t miage1/menez/sensors/led" &
xterm -hold -e "mosquitto_sub -h localhost -v -t \$SYS/#" &
