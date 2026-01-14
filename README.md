# Hello `FunGraphics`for Scala !

FunGraphics a library implemented by Pierre-Andre Mudry, to build with predefined functions interfaces, and draw shapes easily. La source pour la librairi FunGraphics sont [ici](https://github.com/ISC-HEI/FunGraphics/releases/tag/1.5.20)


# space_Invader
Un projet de construire un jeu utilisant la librairy Fungraphics. Ce jeu consiste de survivre l'invasion extraterrestre en évitant les tirs des envahisseur et en les tirant dessus pour marquer le plus de point.


# Game demo
(video for game demo)


## Manuel

Le but du jeu est d'eliminer le plus d'invader possible sans se faire toucher par les projectiles tirée par les invaders, le joueur peut bouger de gauche a droite et de bas en haut, et a 3 vie, si le joueur se fait toucher il perd une vie, si le joueur n'a plus de vie, la partie est terminer.


## Image du jeu
![Screenshot 1](https://github.com/user-attachments/assets/22e5fa92-7fe5-45a3-8a8b-e0ad066d201c)
![Screenshot 2](https://github.com/user-attachments/assets/2baf4323-c054-4024-a4f0-65746792eca6)
![Screenshot 3](https://github.com/user-attachments/assets/1789940b-981c-44da-841a-10563de6e660)
![Screenshot 4](https://github.com/user-attachments/assets/eb16fd73-cb77-4bd6-8112-3121a102489d)


### Structure du code

Global : Objet singleton contenant l'interface graphique pour permettre le dessin depuis toutes les classes.

Spaceship : Classe du vaisseau, gère la position, les déplacements, les tirs et les entrées du clavier.

Invader : Classe des envahisseurs avec position, taille, couleur et méthode de dessin

Projectile: Classe des projectiles, avec mise à jour de trajectoire, dessin et vérification des limites

Game1: Classe principale contenant la logique du jeu :
- Déplacement et tir du vaisseau
- Mouvement et tir automatique des envahisseurs
- Gestion des collisions et des respawns
- Rendu graphique (vaisseau, envahisseurs, projectiles, étoiles, score, vies)
- Écrans de démarrage et de fin
- Boucle principale avec Timer pour mise à jour toutes les ~16 ms (environ 60 fps)

Contrôles:
- Déplacement : W / A / S / D ou flèches
- Tir : E
- Quitter le jeu : ESC
- Souris : cliquer pour démarrer ou relancer le jeu
