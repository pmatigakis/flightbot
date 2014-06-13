class PID(object):
    def __init__(self, kp, ki, kd, dt, max_e):
        self.kp = kp
        self.ki = ki
        self.kd = kd
        self.dt = dt
        self.max_e = max_e

        self.last_e = 0.0
        self.sum_e = 0.0

    def update(self, e):
        self.sum_e += e * self.dt

        if self.sum_e > self.max_e:
            self.sum_e = self.max_e
        elif self.sum_e < -self.max_e:
            self.sum_e = -self.max_e

        derivative = (e - self.last_e) / self.dt

        kp = self.kp * e
        ki = self.ki * self.sum_e
        kd = self.kd * derivative

        output = kp + ki + kd

        self.last_e = e

        return output
