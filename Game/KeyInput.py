import pygame

class keyInput:
    kA = False
    kS = False
    kD = False
    kF = False
    def update(self):
        kA = False
        kS = False
        kD = False
        kF = False
    
    def down(self, event):
        if event.key == pygame.K_a:
            self.kA = True
        if event.key == pygame.K_s:
            self.kS = True
        if event.key == pygame.K_d:
            self.kD = True
        if event.key == pygame.K_f:
            self.kF = True


    def up(self, event):
        if event.key == pygame.K_a:
            self.kA = False
        if event.key == pygame.K_s:
            self.kS = False
        if event.key == pygame.K_d:
            self.kD= False
        if event.key == pygame.K_f:
            self.kF = False
