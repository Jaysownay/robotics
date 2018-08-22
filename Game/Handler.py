import pygame
from Jason.Games.Game.Objects import note, bar

class handler:
    def __init__(self, screen, kInput):
        self.kInput = kInput
        self.screen = screen
        self.objects = []

    def addObject(self, name, color, x, y, height, width, key):
        if name == "note":
            self.objects.append(note(color, x, y, height, width, key))
        if name == "bar":
            self.objects.append(bar(color, x, y, height, width))
            

    def update(self, delta):
        for x in self.objects:
            x.update(self.kInput, delta)

    def render(self):
        self.screen.fill((0, 0, 0))
        for x in self.objects:
            
            pygame.draw.rect(self.screen, x.color, pygame.Rect(x.x, x.y, x.width, x.height))
