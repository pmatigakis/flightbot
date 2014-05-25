from com.matigakis.flightbot.aircraft.controllers import AircraftController

class Autopilot(AircraftController):
    def updateAircraftControls(self, aircraft):
        controls = aircraft.getControls()

        controls.setThrottle(1.0)            

