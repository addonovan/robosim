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

from addonovan.robosim import Robot
from addonovan.robosim import Motor
from addonovan.robosim import Simulation

class DistanceSensor:

    def __init__(self, sensor):
        self.sensor = sensor

    def get_distance(self):
        return self.sensor.getDistance()


class PyRobot:

    def __init__(self):
        x_diff = Robot.WIDTH * 0.40
        y_diff = Robot.HEIGHT * 0.40

        self.mtr_fr = Simulation.robot.addMotor( Motor(  x_diff,  y_diff ) )
        self.mtr_br = Simulation.robot.addMotor( Motor(  x_diff, -y_diff ) )

        self.mtr_fl = Simulation.robot.addMotor( Motor( -x_diff,  y_diff ) )
        self.mtr_bl = Simulation.robot.addMotor( Motor( -x_diff, -y_diff ) )

        self.sensor_distance = DistanceSensor(Simulation.robot.getSensor(0))


    # INSERT def loop(): here