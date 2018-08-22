import pygame
from time import time


from Jason.Games.Game.Handler import handler
from Jason.Games.Game.KeyInput import keyInput
import Jason.Games.Game.Song

pygame.init()
screen = pygame.display.set_mode((1600, 900))
clock = pygame.time.Clock()
done = False

kInput = keyInput()
handle = handler(screen, kInput)
song = Jason.Games.Game.Song.gradientWalk(handle)

tim = time()
timee = 0
delta = 0
while not done:
    kInput.update()
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            done = True
        if event.type == pygame.KEYDOWN:
            kInput.down(event)
        if event.type == pygame.KEYUP:
            kInput.up(event)
    handle.update(delta)
    handle.render()
        
        
    pygame.display.flip()
    timee = time()
    delta = (timee-tim)*60
    tim = time()
    clock.tick()
pygame.quit()
