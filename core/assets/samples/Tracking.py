# Samples basic rotation and edge tracking
#
# The robot will move forward at 100% power until it is 125 cm away
# from the wall, then it will rotate until the distance measurement
# reads a value greater than 20, and the cycle will repeat.
# </description>

def rotate(self, power):
    self.mtr_fr.setPower( power )
    self.mtr_br.setPower( power )

    self.mtr_fl.setPower( -power )
    self.mtr_bl.setPower( -power )

def move(self, power):
    if abs( power ) <= 0.1:
        if power > 0:
            power = 0.1
        elif power < 0:
            power = -0.1

    self.mtr_fl.setPower( power )
    self.mtr_fr.setPower( power )
    self.mtr_bl.setPower( power )
    self.mtr_br.setPower( power )

def loop(self):
    distance = self.sensor_distance.getDistance()

    if distance == -1 or distance > 125:
        self.move( 1 )
    else:
        self.rotate( 1 )