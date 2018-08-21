import pygame


from Jason.Games.Game.Handler import handler
from Jason.Games.Game.KeyInput import keyInput

pygame.init()
screen = pygame.display.set_mode((1600, 900))
done = False

kInput = keyInput()
handle = handler(screen)
handle.addObject("player", (0, 255, 155), 300, 400, 100, 100)


while not done:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            done = True

        if event.type == pygame.KEYDOWN:
            kInput.down(event)
        if event.type == pygame.KEYUP:
            kInput.up(event)
        print(kInput.kW)
        
    handle.render()
        
        
    pygame.display.flip()

pygame.quit()
