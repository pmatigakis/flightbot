if [ $# -eq 1 ]; then
  java -cp $FLIGHTBOT_HOME:$FLIGHTBOT_HOME/lib/*:$FLIGHTBOT_HOME/dist/FlightBot.jar: com.matigakis.flightbot.FlightBot --autopilot=$1
else
  echo "Usage: start_flightbot.sh AUTOPILOT"
fi
