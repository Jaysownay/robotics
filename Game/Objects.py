class note:

    active = True
    hit = False
    r = 0
    g = 0
    b = 0
    
    def __init__(self, color, x, y, height, width, key):
        self.key = key
        self.color = color
        self.x = x
        self.y = y
        self.height = height
        self.width = width
        

    def setcolor(self, r, g, b):
        self.color = (r, g, b)

    def update(self, kInput, delta):
        if self.active:
            self.y += 8*delta
            if ((kInput.kA and self.key == 1) or (kInput.kS and self.key == 2) or (kInput.kD and self.key == 3) or (kInput.kF and self.key == 4)) and self.y<505 and self.y>480:
                self.active = False
                self.r = 200
                self.g = 30
                self.b = 30
            if ((kInput.kA and self.key == 1) or (kInput.kS and self.key == 2) or (kInput.kD and self.key == 3) or (kInput.kF and self.key == 4)) and self.y<540 and self.y>505:
                self.active = False
                self.hit = True
                self.g = 200
                self.b = 50
            if self.y>540:
                self.active = False
                self.r = 200
                self.g = 30
                self.b = 30
        else:
            self.y += 2*delta
            if self.hit:
                if self.r > 0:
                    self.r -= .3
                if self.g > 0:
                    self.g -= .3
                if self.b > 0:
                    self.b -= .3
            else:
                if self.r > 0:
                    self.r -= .1
                if self.g > 0:
                    self.g -= .1
                if self.b > 0:
                    self.b -= .1

            self.color = (self.r, self.g, self.b)



class bar:
    r = 0
    g = 0
    b = 0
    
    def __init__(self, color, x, y, height, width):
        self.color = color
        self.x = x
        self.y = y
        self.height = height
        self.width = width
        

    def setcolor(self, r, g, b):
        self.color = (r, g, b)

    def update(self, kInput, delta):
        pass
