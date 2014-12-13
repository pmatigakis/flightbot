from java.lang import Math

from com.matigakis.flightbot.navigation import Navigator
from com.matigakis.controlsystems.pid import PID

from altitude import AltitudeHoldAutopilot
from airspeed import AirspeedHoldAutopilot
from heading import HeadingHoldAutopilot

import settings

__VERSION__ = "0.0.2"

class Autopilot(object):
    def __init__(self):
        
        self.waypoints = [(37.614299,-122.357153),
                          (37.602468,-122.398867),
                          (37.630683,-122.415518),
                          (37.638364,-122.385477)]
         
        self.navigator = Navigator()
    
    def setup(self, configuration):    
        self.updateCnt = 0
        
        self.target_heading = 100;
        self.target_airspeed = 60.0;
        self.target_altitude = 1000.0
        
        self.current_waypoint = 0
        
        self.altitude_hold_autopilot = AltitudeHoldAutopilot(settings.PITCH_P,
                                                            settings.PITCH_I,
                                                            settings.PITCH_D,
                                                            settings.ELEVATOR_P,
                                                            settings.ELEVATOR_I,
                                                            settings.ELEVATOR_D,
                                                            settings.dt)
        
        self.airspeed_hold_autopilot = AirspeedHoldAutopilot(settings.THROTTLE_P,
                                                            settings.THROTTLE_I,
                                                            settings.THROTTLE_D,
                                                            settings.dt)
        
        self.heading_hold_autopilot = HeadingHoldAutopilot(settings.ROLL_P,
                                                           settings.ROLL_I,
                                                           settings.ROLL_D,
                                                           settings.AILERON_P,
                                                           settings.AILERON_I,
                                                           settings.AILERON_D,
                                                           settings.dt)
        
        self.altitude_hold_autopilot.set_target_altitude(self.target_altitude)
        self.heading_hold_autopilot.set_target_heading(self.target_heading)
        self.airspeed_hold_autopilot.set_target_airspeed(self.target_airspeed)
        
    def reset(self, configuration):
        #just call setup again for the moment
        self.setup(configuration)
    
    def run(self, aircraft):
        instrumentation = aircraft.getInstrumentation()
        
        current_airspeed = instrumentation.getAirspeed()
        throttle = self.airspeed_hold_autopilot.get_throttle(current_airspeed)
        
        current_altitude = instrumentation.getAltitude()
        current_pitch = instrumentation.getPitch()
        elevator = self.altitude_hold_autopilot.get_elevator(current_altitude, current_pitch)
            
        if self.updateCnt == 0:
            gps = aircraft.getGPS()
            
            waypoint = self.waypoints[self.current_waypoint]
            
            distance_to_point = self.navigator.distance(gps.getLatitude(), gps.getLongitude(),
                                                        waypoint[0], waypoint[1])
    
            target_heading = self.navigator.bearing(gps.getLatitude(), gps.getLongitude(),
                                                    waypoint[0], waypoint[1])
    
            self.heading_hold_autopilot.set_target_heading(target_heading)
    
            self.updateCnt = 20
            
            if distance_to_point < 0.2:
                self.current_waypoint += 1
                self.current_waypoint %= len(self.waypoints)
                print("Going to %f, %f" % (self.waypoints[self.current_waypoint][0], self.waypoints[self.current_waypoint][1]))
    
        current_heading = instrumentation.getHeading()
        current_roll = instrumentation.getRoll()
        aileron = self.heading_hold_autopilot.get_aileron(current_heading, current_roll)
        
        controls = aircraft.getControls()
        
        controls.setElevator(elevator)
        controls.setAileron(aileron)
        controls.setThrottle(throttle)
        controls.setRudder(0.0)
        
        self.updateCnt -= 1

def create_autopilot():
    return Autopilot()
