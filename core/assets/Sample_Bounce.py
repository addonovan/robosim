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

edge = False

def move(self, power):
    if abs( power ) <= 0.1:
        if power > 0:
            power = 0.1
        else:
            power = -0.1

    self.mtr_fl.set_power(power)
    self.mtr_fr.set_power(power)
    self.mtr_bl.set_power(power)
    self.mtr_br.set_power(power)

def loop(self):
    distance = self.sensor_distance.get_distance()

    if distance < 0.20 and distance != -1:
        self.edge = True

    if self.edge and distance == -1:
        self.edge = False

    power = ( distance / 2.55 )
    if power == -1 / 2.55:
        power = 1

    if self.edge:
        power *= -1


    self.move( power )