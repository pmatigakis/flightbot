from com.matigakis.controlsystems.pid import PID

class AltitudeHoldAutopilot(object):
    def __init__(self, pitch_p, pitch_i, pitch_d, elevator_p, elevator_i, elevator_d, dt):
        self.target_altitude = 1000.0
        
        self.pitch_pid = PID(pitch_p, pitch_d, pitch_d, -15.0, 15.0, dt, 0.0)
        self.elevator_pid = PID(elevator_p, elevator_i, elevator_d, -1.0, 1.0, dt, 200.0)

    def set_target_altitude(self, altitude):
        self.target_altitude = altitude
    
    def get_elevator(self, current_altitude, current_pitch):
        altitude_error = self.target_altitude - current_altitude
        target_pitch = self.pitch_pid.calculateOutput(altitude_error)

        pitch_error = target_pitch - current_pitch
        elevator = -self.elevator_pid.calculateOutput(pitch_error)
        
        return elevator