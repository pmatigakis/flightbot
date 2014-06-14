from java.lang import Math

from com.matigakis.flightbot.aircraft.controllers import AircraftController
from com.matigakis.flightbot.util import PID, Navigator

#from pid import PID

#def distance(lat1, lon1, lat2, lon2):
#    phi1 = Math.toRadians(lat1)
#    phi2 = Math.toRadians(lat2)
#    lam1 = Math.toRadians(lon1)
#    lam2 = Math.toRadians(lon2)

#    return 6371.01 * Math.acos( Math.sin(phi1) * Math.sin(phi2) + Math.cos(phi1) * Math.cos(phi2) * Math.cos(lam2 - lam1) );

#def bearing(lat1, lon1, lat2, lon2):
#    brng = Math.atan2(Math.sin(lon2-lon1)*Math.cos(lat2),
#                      Math.cos(lat1)*Math.sin(lat2)-Math.sin(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))

#    return Math.toDegrees(brng % (2.0 * 3.1415))

class Autopilot(AircraftController):
    def __init__(self):
        self.throttle_pid = PID(0.1, 0.07, 0.05, 0.05, 10.0)
        self.course_pid = PID(1.0, 0.0, 0.0, 0.05, 0.0)
        self.aileron_pid = PID(0.1, 0.005, 0.001, 0.05, 10.0)
        self.pitch_pid = PID(0.5, 0.0, 0.0, 0.05, 0.0)
        self.elevator_pid = PID(0.055, 0.0005, 0.0001, 0.05, 200.0)

        self.navigator = Navigator()

        self.updateCnt = 0;
        self.target_course = 100;
        self.target_airspeed = 60.0;
        self.target_altitude = 1000.0

        self.waypoints = []

        f = open("./autopilots/simple/waypoints.csv", "r")

        for line in f:
            lat, lon = line.split(",")
            lat = float(lat)
            lon = float(lon)
            self.waypoints.append([lat, lon])
        
        f.close()

        self.waypoint_index = 0

    def updateThrottle(self, instrumentation, controls):
        current_airspeed = instrumentation.getAirspeed()

        e = self.target_airspeed - current_airspeed

        target_throttle = self.throttle_pid.update(e)
        
        if target_throttle > 1.0:
            target_throttle = 1.0
        elif target_throttle < 0.0:
            target_throttle = 0.0

        controls.setThrottle(target_throttle)            

    def updateAileron(self, gps, instrumentation, orientation, controls):
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

        target_roll = self.course_pid.update(course_e)

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

        altitude_e = self.target_altitude - current_altitude
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
        gps = aircraft.getGPS()

        self.updateThrottle(instrumentation, controls)
        self.updateAileron(gps, instrumentation, orientation, controls)
        self.updateElevator(instrumentation, orientation, controls)
