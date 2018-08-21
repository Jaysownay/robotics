import pygame

class keyInput:
    
    kW = False
    kA = False
    kS = False
    kD = False
    
    def __init__(self):
        print("active")


        
    def down(self, event):
        if event.key == pygame.K_w:
            self.kW = True

    def up(self, event):
        if event.key == pygame.K_w:
            self.kW = False
