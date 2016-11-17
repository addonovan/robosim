#
# The MIT License (MIT)
#
# Copyright (c) 2016 Austin Donovan (addonovan)
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in all
# copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
# SOFTWARE.
#
from addonovan.robosim import Simulation

def set_run_speed(speed):
    if speed < 0.01:
        speed = 0.01
    elif speed > 5:
        speed = 5

    Simulation.runSpeed.setValue( speed )

class Motor:

    def __init__(self, id):
        self.id = id

    def set_power(self, power):
        Simulation.robot.powerMotor( power, self.id )

class DistanceSensor:

    def __init__(self, sensor):
        self.sensor = sensor

    def get_distance(self):
        return self.sensor.getDistance()


class PyRobot:

    def __init__(self):
        # create the motors
        self.mtr_fl = Motor("front_left")
        self.mtr_fr = Motor("front_right")
        self.mtr_bl = Motor("back_left")
        self.mtr_br = Motor("back_right")

        self.sensor_distance = DistanceSensor(Simulation.robot.getSensor(0))


    # INSERT def loop(): here