import pygame

class gradientWalk():
    bpm = 130
    noteList = [0, 1.5, 2, 3, 3.5, 5.5, 6, 7, 8, 9.5, 10, 10.75, 11.5, 13.5, 14, 15, 16, 17, 17.5, 18, 19,  19, 19.5, 19.5, 19.5, 20.5, 21]
    keyList  = [1, 1,   2, 2, 3,   3,   2, 2, 1, 1,   2,  2,     4,    4,    3,  2,  1,  4,  1,    2,  4,   2,  3,    1,    3,    1,    4]

    def __init__(self, handle):
        g = open('Gradient Walk.txt', 'r')
        self.test = []
        for x in g:
            line = x.split()
            for s in range(0, len(line)):
                line[s] = float(line[s])
            self.test.append(line)
            
        handle.addObject("bar", (255, 255, 255), 0, 530, 5, 800, 0)
        for x in range(0, len(self.test)):
            handle.addObject("note", (255, 255, 255), (self.test[x][1]*150)-50, 420-((self.test[x][0]-.25)*(3600*8/self.bpm)), 5, 100, self.test[x][1])
        pygame.mixer.music.load('Gradient Walk.mp3')
        pygame.mixer.music.play()
            
