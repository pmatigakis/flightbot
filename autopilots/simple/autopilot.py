from java.lang import Math

from com.matigakis.flightbot.aircraft.controllers import AircraftController

from pid import PID

def sigmoid(x):
    return x / Math.sqrt(1.0 + Math.pow(x, 2))

def logistic(x):
    return 1.0 / (1.0 + Math.exp(-x)) 

class Autopilot(AircraftController):
    def __init__(self):
        self.throttle_pid = PID(0.1, 0.07, 0.05, 0.05, 10.0)
        self.course_pid = PID(1.0, 0.0, 0.0, 0.05, 0.0)
        self.aileron_pid = PID(0.1, 0.005, 0.001, 0.05, 10.0)
        self.pitch_pid = PID(0.5, 0.0, 0.0, 0.05, 0.0)
        self.elevator_pid = PID(0.055, 0.0005, 0.0001, 0.05, 200.0)

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

        course_e = target_course - current_course

        target_roll = self.course_pid.update(course_e)

        #print course_e, target_roll

        if target_roll > 15.0:
            target_roll = 15.0
        elif target_roll < -15.0:
            target_roll = -15.0

        current_roll = orientation.getRoll()

        roll_e = target_roll - current_roll

        target_aileron = self.aileron_pid.update(roll_e)

        if target_aileron > 1.0:
            target_aileron = 1.0
        elif target_aileron < -1.0:
            target_aileron = -1.0

        controls.setAileron(target_aileron)

    def updateElevator(self, instrumentation, orientation, controls):
        current_altitude = instrumentation.getAltitude()
        target_altitude = 1000.0

        altitude_e = target_altitude - current_altitude
        target_pitch = self.pitch_pid.update(altitude_e)

        if target_pitch > 15.0:
            target_pitch = 15.0
        elif target_pitch < -15.0:
            target_pitch = -15.0

        current_pitch = orientation.getPitch()

        pitch_e = target_pitch - current_pitch
        target_elevator = -self.elevator_pid.update(pitch_e)

        if target_elevator > 1.0:
            target_elevator = 1.0
        elif target_elevator < -1.0:
            target_elevator = -1.0

        controls.setElevator(target_elevator)

    def updateAircraftControls(self, aircraft):
        controls = aircraft.getControls()
        instrumentation = aircraft.getInstrumentation()
        orientation = aircraft.getOrientation()

        self.updateThrottle(instrumentation, controls)
        self.updateAileron(instrumentation, orientation, controls)
        self.updateElevator(instrumentation, orientation, controls)

