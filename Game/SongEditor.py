import pygame
from time import time

from Jason.Games.Game.KeyInput import keyInput

g = open('Shine Get.txt', 'w')
pygame.init()
screen = pygame.display.set_mode((1600, 900))
clock = pygame.time.Clock()
done = False

key = 0

tim = time()
timee = 0
delta = 0

quantization = 0
when = 0
q = 0
qBool = False

pygame.mixer.music.load('Shine Get.mp3')
pygame.mixer.music.play()

while not done:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            done = True
        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_a:
                key = 1
            if event.key == pygame.K_s:
                key = 2
            if event.key == pygame.K_d:
                key = 3
            if event.key == pygame.K_f:
                key = 4
            g.write(str(when+q-.5) + " " + str(key)+ '\n')
            print("yep")
        
    
    pygame.display.flip()
    timee = time()
    delta = (timee-tim)*60
    
    
    
    
    
    tim = time()
    quantization = (int(round(time()*100000000000, 0))%46153846153)/100000000000
    if quantization > .4038 or quantization < .0577:
        if qBool:
            when += 1
            qBool = False
        q = 0
    if quantization > .0577 and quantization < .1727:
        q = .25
    if quantization > .1727 and quantization < .2877:
        qBool = True
        q = .5
    if quantization > .2877 and quantization < .4038:
        q = .75
    print(when+q)
    clock.tick()
pygame.quit()
g.close()
