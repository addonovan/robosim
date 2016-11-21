# Samples basic motor movement and distance sensor usage.
#
# The robot will move forward at a decreasing power, as a function
# of how far away the robot is from the wall. Once it is 20 centimeters
# away from the wall, it will start to move backwards, until the
# distance sensor is out of range, and the process will start over
# again.
# </description>

edge = False

def move(self, power):
    if abs( power ) <= 0.1:
        if power > 0:
            power = 0.1
        elif power < 0:
            power = -0.1
    elif power > 1:
        power = 1
    elif power < -1:
        power -1

    self.mtr_fl.setPower( power )
    self.mtr_fr.setPower( power )
    self.mtr_bl.setPower( power )
    self.mtr_br.setPower( power )

def loop(self):
    distance = self.sensor_distance.getDistance()

    if distance < 20 and distance != -1:
        self.edge = True

    if self.edge and distance == -1:
        self.edge = False

    power = ( distance / 255.0 )
    if power < 0:
        power = 1

    if self.edge:
        power *= -1

    self.move( power )