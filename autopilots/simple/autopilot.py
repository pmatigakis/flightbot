from java.lang import Math

from com.matigakis.flightbot.aircraft.controllers import AircraftController

from pid import PID

def sigmoid(x):
    return x / Math.sqrt(1.0 + Math.pow(x, 2))

def logistic(x):
    return 1.0 / (1.0 + Math.exp(-x)) 

class Autopilot(AircraftController):
    def __init__(self):
        self.throttle_pid = PID(0.095, 0.0007, 5000.0, 0.05, 1000.0)

    def calculateRoll(self, target_course, current_course):
        e = (target_course - current_course) / 90.0

        return 20.0 * sigmoid(e)

    def calculatePitch(self, target_altitude, current_altitude):
        e = (target_altitude - current_altitude) / 100.0

        return 20.0 * sigmoid(e)

    def updateThrottle(self, instrumentation, controls):
        current_airspeed = instrumentation.getAirspeed()
        target_airspeed = 60

        e = target_airspeed - current_airspeed

        target_throttle = self.throttle_pid.update(e)
        
        if target_throttle > 1.0:
            target_throttle = 1.0
        elif target_throttle < 0.0:
            target_throttle = 0.0

        controls.setThrottle(target_throttle)            

    def updateAileron(self, instrumentation, orientation, controls):
        current_course = instrumentation.getHeading()
        target_course = 50.0

        current_roll = orientation.getRoll()
        target_roll = self.calculateRoll(target_course, current_course)

        e = (target_roll - current_roll) / 10.0

        target_aileron = sigmoid(e)

        controls.setAileron(target_aileron)

    def updateElevator(self, instrumentation, orientation, controls):
        current_altitude = instrumentation.getAltitude()
        target_altitude = 1000.0
        
        current_pitch = orientation.getPitch()
        target_pitch = self.calculatePitch(target_altitude, current_altitude)

        e = (target_pitch - current_pitch) / 10.0

        target_elevator = -sigmoid(e)

        controls.setElevator(target_elevator)

    def updateAircraftControls(self, aircraft):
        controls = aircraft.getControls()
        instrumentation = aircraft.getInstrumentation()
        orientation = aircraft.getOrientation()

        self.updateThrottle(instrumentation, controls)
        self.updateAileron(instrumentation, orientation, controls)
        self.updateElevator(instrumentation, orientation, controls)

