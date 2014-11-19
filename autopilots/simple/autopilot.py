from java.lang import Math

from com.matigakis.flightbot.aircraft.controllers import Autopilot
from com.matigakis.flightbot.navigation import Navigator
from com.matigakis.controlsystems.pid import PID


class JythonAutopilot(Autopilot):
    def __init__(self):
        p = PID
        self.throttle_pid = PID(0.1, 0.07, 0.05, 0.0, 1.0, 0.1, 10.0)
        self.course_pid = PID(1.0, 0.0, 0.0, -15.0, 15.0, 0.1, 0.0)
        self.aileron_pid = PID(0.1, 0.005, 0.001, -1.0, 1.0, 0.1, 10.0)
        self.pitch_pid = PID(0.5, 0.0, 0.0, -15.0, 15.0, 0.1, 0.0)
        self.elevator_pid = PID(0.055, 0.0005, 0.0001, -1.0, 1.0, 0.1, 200.0)

        self.navigator = Navigator()

        self.updateCnt = 0;
        self.target_course = 100;
        self.target_airspeed = 60.0;
        self.target_altitude = 1000.0

        self.waypoints = [(37.614299,-122.357153),
                          (37.602468,-122.398867),
                          (37.630683,-122.415518),
                          (37.638364,-122.385477)]

        #f = open("waypoints.csv", "r")

        #for line in f:
        #    lat, lon = line.split(",")
        #    lat = float(lat)
        #    lon = float(lon)
        #    self.waypoints.append([lat, lon])
        
        #f.close()

        self.waypoint_index = 0

    def updateThrottle(self, instrumentation, controls):
        current_airspeed = instrumentation.getAirspeed()

        e = self.target_airspeed - current_airspeed

        target_throttle = self.throttle_pid.calculateOutput(e)

        controls.setThrottle(target_throttle)            

    def updateAileron(self, gps, instrumentation, controls):
        current_course = instrumentation.getHeading()
        
        waypoint = self.waypoints[self.waypoint_index]
        
        if self.updateCnt == 0:
            distance_to_point = self.navigator.distance(gps.getLatitude(), gps.getLongitude(),
                                                   waypoint[0], waypoint[1])

            self.target_course = self.navigator.bearing(gps.getLatitude(), gps.getLongitude(),
                                                        waypoint[0], waypoint[1])

            self.updateCnt = 20
            
            if distance_to_point < 0.2:
                self.waypoint_index += 1
                self.waypoint_index %= len(self.waypoints)

        self.updateCnt -= 1

        course_e = self.target_course - current_course

        if course_e < -180.0:
            course_e = course_e + 360
        elif course_e > 180.0:
            course_e =  course_e - 360

        target_roll = self.course_pid.calculateOutput(course_e)

        current_roll = instrumentation.getRoll()

        roll_e = target_roll - current_roll

        target_aileron = self.aileron_pid.calculateOutput(roll_e)

        controls.setAileron(target_aileron)

    def updateElevator(self, instrumentation, controls):
        current_altitude = instrumentation.getAltitude()

        altitude_e = self.target_altitude - current_altitude
        target_pitch = self.pitch_pid.calculateOutput(altitude_e)

        current_pitch = instrumentation.getPitch()

        pitch_e = target_pitch - current_pitch
        target_elevator = -self.elevator_pid.calculateOutput(pitch_e)

        controls.setElevator(target_elevator)

    def updateControls(self, aircraft):
        controls = aircraft.getControls()
        instrumentation = aircraft.getInstrumentation()
        gps = aircraft.getGPS()

        self.updateThrottle(instrumentation, controls)
        self.updateAileron(gps, instrumentation, controls)
        self.updateElevator(instrumentation, controls)
        
def create_autopilot():
    return JythonAutopilot()
