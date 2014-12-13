from com.matigakis.controlsystems.pid import PID

class HeadingHoldAutopilot(object):
    def __init__(self, roll_p, roll_i, roll_d, aileron_p, aileron_i, aileron_d, dt):
        self.target_heading = 180.0
        
        self.course_pid = PID(roll_p, roll_i, roll_d, -15.0, 15.0, dt, 0.0)
        self.aileron_pid = PID(aileron_p, aileron_i, aileron_d, -1.0, 1.0, dt, 10.0)
        
    def set_target_heading(self, heading):
        self.target_heading = heading
    
    def get_aileron(self, current_course, current_roll):
        course_error = self.target_heading - current_course

        if course_error < -180.0:
            course_error = course_error + 360
        elif course_error > 180.0:
            course_error = course_error - 360

        target_roll = self.course_pid.calculateOutput(course_error)

        roll_error = target_roll - current_roll

        aileron = self.aileron_pid.calculateOutput(roll_error)
        
        return aileron;