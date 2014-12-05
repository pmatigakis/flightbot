# Introduction
FlightBot is an autopilot for the open source flight simulator Flightgear.
This is still in development.

# Installation
## Requirements
1. Ant
2. Flightgear version 3.0.0 or later

## Compiling FlightBot
1. Download the source code from Github.

    git clone https://github.com/pmatigakis/flightbot.git

2. Build FlightBot by giving the following command to the console.

    ant dist

3. Copy fgcontrol.xml from the flightgear/protocol directory to Flightgear's Protocol directory

4. Create a variable named FLIGHTBOT_HOME that is pointing to FlightBot's installation path.
5. Go to the scripts directory and start Flightgear with the following command.

    ./start_fg.sh

6. Run the fdm data viewer using the following command

    ./run_fdm_viewer.sh

7. Run the sample autopilot using the following command

    ./run_jython_autopilot.sh ../autopilots/simple


# Documentation
There isn't any documentation yet. However you can check how the sample autopilot at
$FLIGHTBOT_HOME/autopilots/simple works and use it as a base for your autopilot.
