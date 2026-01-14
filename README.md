# Hello `FunGraphics`for Scala !

FunGraphics a library implemented by Pierre-Andre Mudry, to build with predefined functions interfaces, and draw shapes easily.


# space_Invader
A game project in Scala using the FunGraphics library. This game consists of controlling a spaceship and shooting the invaders while dodging the shots coming from the invaders.

# Game demo
(video for game demo)


## Manuel

Le but du jeu est d'eliminer le plus d'invader possible sans se faire toucher par les projectiles tirée par les invaders, le joueur peut bouger de gauche a droite et de bas en haut, et a 3 vie, si le joueur se fait toucher il perd une vie, si le joueur n'a plus de vie, la partie est terminer.


### Structure du code

Le code est séparé en 2 partie principal ; d'abord nous avons les classes pour les entités (spaceship, invader, projectile) et ensuite nous avons la class qui gère les diffèrent états du jeu (état initial start Screen, état de jeu ou le joueur peu controler le spaceship et marquer le plus de point possible, état final game Over qui propose de relancer une partie et affiche le score du joueur). Dans chaque classes il y à des fonctions qui permettent la fluidité du fonctionnement du jeu. Dans la classe Spaceship je gère les événements où le joueur presse une touche, le mouvement du vaisseau, et dessiner le vaisseau. Dans la class Invader je n'ai besoin que de dessiner l'enemy parce que je gère le reste utilisant des fonction dans la class Game1. La class Projectile je dessine le projectile, je change la position du projectile et je vérifie si le projectile sort de l'interface ou non.

La class Game1 gère les états du jeu, l'aspect du mouvement du vaisseau et des enemies, la collision de projectile à une entité, le score du joueur, la reapparition de l'enemy après un certain temps découlé, l'affichage et le rendement des 3 états du jeu. Dans cette class 
