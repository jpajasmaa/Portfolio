""" SnakeGame, class and game example&Practice
    lisäksi tämän voisi pistää tietokoneen itse pelaamaan

    Tehty oliopohjaisesti
"""
import pygame
import sys
import random

WINDOW_WIDTH = 800
WINDOW_HEIGHT = 800



"""
snakeluokka hoitaa snaken liikuttelun ja törmäykset, kasvamisen.
"""
class Snake:
    def __init__(self):
        self.position = [100, 50]
        self.body = [[100, 50], [90, 50], [80, 50]]
        self.direction = "RIGHT"

    """"tee paremmin ei iffeillä"""
    def change_dir_to(self, direction):
        if direction == "RIGHT" and not self.direction == "LEFT":
            self.direction = "RIGHT"
        if direction == "LEFT" and not self.direction == "RIGHT":
            self.direction = "LEFT"
        if direction == "UP" and not self.direction == "DOWN":
            self.direction = "UP"
        if direction == "DOWN" and not self.direction == "UP":
            self.direction = "DOWN"

    def move(self, foodpos):
        if self.direction == "RIGHT":
            self.position[0] += 10

        if self.direction == "LEFT":
            self.position[0] -= 10

        if self.direction == "UP":
            self.position[1] -= 10

        if self.direction == "DOWN":
            self.position[1] += 10

        self.body.insert(0, list(self.position))
        if self.position == foodpos:
            return 1
        else:
            self.body.pop()
            return 0

    def check_collision(self):
        if self.position[0] > WINDOW_WIDTH - 10 or self.position[0] < 0:
            return 1
        elif self.position[1] > WINDOW_HEIGHT - 10 or self.position[1] < 0:
            return 1
        for bodypart in self.body[1:]:
            if self.position == bodypart:
                return 1
        return 0

    def get_head(self):
        return self.position

    def get_body(self):
        return self.body



""" 
luokka joka spawnaa ruuat randomilla.
"""
class FoodSpawner:
    def __init__(self):
        self.position = [random.randrange(1, 50)*10, random.randrange(1, 50)*10]
        self.Fonscreen = True

    def spawn_food(self):
        if self.Fonscreen is False:
            self.position = [random.randrange(1, 50)*10, random.randrange(1, 50)*10]
            self.Fonscreen = True
        return self.position

    def setFoodOnScreen(self, b):
        self.Fonscreen = b


def game_over():
    print("Score: " + str(score))
    pygame.quit()
    sys.exit()


snake = Snake()
FoodSpawner = FoodSpawner() 


def main():
    game_window = pygame.display.set_mode((WINDOW_WIDTH, WINDOW_HEIGHT))
    pygame.display.set_caption("Snake test")
    fps = pygame.time.Clock()

    score = 0


    game_running = True
    while game_running:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                game_over()
            elif event.type == pygame.KEYDOWN:
                if event.key == pygame.K_RIGHT:
                    snake.change_dir_to("RIGHT")
                if event.key == pygame.K_UP:
                    snake.change_dir_to("UP")
                if event.key == pygame.K_DOWN:
                    snake.change_dir_to("DOWN")
                if event.key == pygame.K_LEFT:
                    snake.change_dir_to("LEFT")
           
        foodPos = FoodSpawner.spawn_food()

        game_window.fill((255, 255, 255))

        if snake.move(foodPos) == 1:
            score += 1
            FoodSpawner.setFoodOnScreen(False)

        for pos in snake.get_body():
            pygame.draw.rect(game_window, (255, 0, 0), pygame.Rect(pos[0], pos[1], 10, 10))

        pygame.draw.rect(game_window, (0, 255, 0), pygame.Rect(foodPos[0], foodPos[1], 10, 10))

        if snake.check_collision() == 1:
            game_over()

        pygame.display.flip()
        fps.tick(20)





if __name__ == "__main__":
    main()
