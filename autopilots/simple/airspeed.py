from com.matigakis.controlsystems.pid import PID

class AirspeedHoldAutopilot(object):
    def __init__(self, throttle_p, throttle_i, throttle_d, dt):        
        self.target_airspeed = 65.0
        
        self.throttle_pid = PID(throttle_p, throttle_i, throttle_d, 0.0, 1.0, dt, 10.0)
                
    def set_target_airspeed(self, airspeed):
        self.target_airspeed = airspeed
        
    def get_throttle(self, current_airspeed):
        airspeed_error = self.target_airspeed - current_airspeed
        
        return self.throttle_pid.calculateOutput(airspeed_error)