import pygame
from Jason.Games.Game.Character import character

class handler:
    def __init__(self, screen):
        self.screen = screen
        self.objects = []

    def addObject(self, name, color, x, y, height, width):
        self.objects.append(character(name, color, x, y, height, width))

    def render(self):
        for x in self.objects:
            pygame.draw.rect(self.screen, x.color, pygame.Rect(x.x, x.y, x.height, x.width))
