# Journal de bord

## Semaine du 29 au 5 juin 2022

### Répartition des tâches
* Ana travaille sur les bases de l'architecture du projet.
* William travaille sur la génération des grottes (javascript).
* Jérémy travaille sur les graphismes des joueurs et mob.
* Tout le monde travaille sur la conception du projet .

### Ana : architecture de base du projet

- Création d'une classe Entity
    - position, avatar, fonction tick
- Création d'une classe Avatar
    - gère le chargement d'une image et son animation
- Création d'une classe Vec2
    - un vecteur de 2 flottants pour représenter une position, une échelle, etc

### Jérémy:graphisme du jeu

* appararences des joueurs et des mobs
* apparence du fond du jeu

### Tout le monde (conception)

* conceptualisation du jeu
    * manière dont il sera joué
    * mécaniques récurrentes
    * setting de l'univers, scénario

## Mardi 7 juin 2022

### Répartition des tâches

* Ana travaille sur la mise en place du réseau
* Lucas travaille sur l'implémentation des tests et l'intégration continue avec gitlab
* Benjamin, Léa et Amaury travaillent sur les automates des divers objets
* Jérémy et William travaillent sur la génération des grottes (de javascript à java)
* Ali travaille sur la physique

### Ana : mise en place du réseau

- Abstraction pour pouvoir avoir un "serveur" interne si n'on a qu'un seul client : séparation entre vue et controlleur, avec une implémentation locale et une implémentation contrôlée via le réseau dans les deux cas.
- Deux points d'entrées : un pour le client, un pour le serveur

### Lucas : implémentation des tests et intégration continue

- Mise en place de JUnit afin de réaliser et exécuter des tests unitaires sur les différentes classes du projet.
- Mise en place du build system Maven ce qui a demandé une restructuration du projet. Cela a permis de mettre en place l'intégration continue sur Gitlab permettant de vérifier que le projet build pour chaque commit.

### Benjamin, Léa, Amaury : conception des automates

- Conception des automates des blocs, consumables et de l'inventaire.

### William et Jérémy : génération des grottes

* convertir le code de cette génération qui est en javascript en java.

### Idées

- classe dégats propre à la pioche et à l'épée pour que les blocks et mob sachent si ils prennent des dégats et qu'ils puissent détecter l'action de leur mettre des coups.

## Mercredi 8 juin 2022

### Répartition des tâches
* Ana a fini la mise en place du réseau
* Benjamin et Léa travaillent sur les automates des objets restants

### Benjamin et Léa : conception des automates
* Réflexions autour de diverses problématiques d'un point de vue automate : 
    - Quel(s) automate(s) attaque(nt) ? Et que représente la notion d'attaquer ?
    - Est-il est nécessaire de différencier une attaque dans le vide et une attaque qui fait des dégats ?
    - Faut-il créer une "classe intermédiaires" Dégats pour différentes entités afin de détecter les attaques monstres/personnages ?
    - Comment/où choisir quel bloc est frappé en priorité si on en est entouré de plusieurs côtés ?
    - Comment/où  selectionner les objets de l'inventaire que l'on souhaite utiliser ? (on assigne chaque objet à une touche, on utilise les flêches dans un menu inventaire...)
    - Est-ce que le fait de tourner le personnage à 180 degrés est explicite ou implicite dans l'automate ?
    
    - #### Les automates peuvent-ils communiquer entre eux ? i.e : lors de l'activation d'une touche, chaque automate réagit-il, ou bien un premier automate peut-il communiquer l'information à un second et déclencher une de ses transitions ?


* Reconception et amélioration des automates (Bloc, Inventaire, Épée, Pioche) avec les informations reçues de M. Périn

* Suppression des automates Épée et Pioche, maintenant c'est le joueur qui gère l'entièreté des attaques

### Ana : fin de la mise en place du réseau

- Bug dû à une mauvaise utilisation des ObjectOutputStream réglé

### William et Ali : 
- Conception de la partie physique et des colliders . 
- Utilisation de la méthode AABB de détéction des collisions . 

## Jeudi 9 Juin 2022

Après l'entretien avec M. Gruber, nous avons décidé de faire une réunion afin de définir techniquement les tâches à faire (ainsi qu'évaluer leur difficulté et temps), réaliser des diagrammes de classes et d'objets, et répondre aux éventuelles questions des membres de l'équipe.

### Répartition des tâches
* Benjamin, Léa, Lucas et Amaury travaillent sur les automates
* Ana optimise le code du réseau et règle quelques bugs
### Lucas,  Benjamin, Léa, Amaury: conception des automates

* Amélioratons des automates (Bloc, Inventaire, Consumables)
* Conception des automates Statue, Champignon (monstre basique)
* Réflexion autour de l'automate du joueur et début de conception
* Les automates des consumables (eau, nourriture) deviennent des automates vides, maintenant c'est l'inventaire qui se charge de tout gérer (ramasser, jeter, comsommer)

### Ana : optimisation du réseau
- Bugs d'accès concurrents réglés
- Optimisations
    - Message "MultiMessage" qui permet d'envoyer plusieurs messages en un seul paquet
    - Un seul paquet au plus est envoyé par tick du serveur, pour éviter de saturer le réseau et s'assurer que les clients ont bien le temps de traiter les paquets reçus

### Ali et William: 
* Création du package physique 
* Mise en place des colliders : 
   -Une classe absraite Collider
   -Classe Boxcollider qui hérite de Collider et gère les collisions par des rectangles . 
   -Classe Circle Collider(pas encore implémenter pour le moment ), on pourra introduire de cette classe pour la gestion des collisions de type  cercle-cerlce ou rectangle-cercle .
   
## Vendredi 10 Juin 2022

### Répartition des tâches

- Ana : review de code et aide
- Benjamin, Léa, et Amaury travaillent sur les automates

### Lucas: ajout d'une caméra

* Ajout d'un système de caméra basique contrôlable avec les flêches du clavier, pour l'instant les différents clients sur le réseau partagent la même caméra

### Benjamin, Amaury et Léa : conception des automates
* Communication importante avec les chargés de la partie Physique après avoir pensé à d'autres détails (dialogue autour des collisions, actions possibles dans les airs pour les monstres et héros...)
* mise en place des pop et Wizz et généralisation des conditions
* finalisation des automates en GAL

### William et Ali 
* Création de la classe ColliderSide qui permet de déterminer le coté du rectangle où il a eu une collision . 
* Création de la classe RigidBody pour la mise en place des forces physiques. 


## Lundi 13 juin 2022

### Répartition des tâches

* Ana : merge de trois branches
* Lucas, Benjamin et Léa : rédaction du contrat
* Lucas : rédaction du planning

### Ana : merge de trois branches

- Merge du code permettant de différencier les joueurs côté serveur.
    - chaque joueur a maintenant ses propres contrôles indépendants des autres
    - chaque joueur a sa caméra qui suit son avatar
- Merge du code de génération de la carte
    - crée des blocs dans le modèle là où le code de génération indique qu'il en faut
- Merge du code de physique


## Mardi 14 juin 2022

- Ana : règle quelques bugs liés au réseau. Objectif à la fin de la journée : pouvoir se promener à deux (ou plus) sur la carte qu'on génère maintenant, en réseau
- Lucas : implémentation des classes pour les Automates + commencement de BotBuilder
- Benjamin et Amaury : implémentation des classes pour les conditions des automates et fonctions de la classe playerBehaviour.

### Lucas : implémentation des classes pour les Automates + commencement de BotBuilder

* Ajout des classes Automata, AutomataState et AutomataTransition permettant de représenter les automates dans le code
* Ajout de la classe BotBuilder implémentant l'interface fournie IVisitor, cette classe permet de créer les automates à partir d'un AST.

### Benjamin, Amaury: implémentation des classes pour les conditions et actions des automates et fonctions de la classe playerBehaviour.

* Ajout des classes IConditions, IActions et de toutes les classes des diveress conditions et action avec leurs fonctions eval.
* Implémentation des fonctions True,Cell,GotPower,wizz(jump) et move de la classe playerBehaviour.


## Mercredi 15 juin 2022

* Benjamin : implémentation des points de vie, des divers dégats, des classes de toutes les entités nécessaires ainsi que leurs behaviours

### Benjamin : implémentation des points de vie, des divers dégats, des classes des entités et de leurs behaviours.

* Ajout des classes Mushroom, Block, Statue, Socle et Stalactite.
* Ajout des classes Behaviours pour toutes les entités.
* implémentation des fonctions  hit et coup_reçu.

## Jeudi 16 juin 2022

* Benjamin, Amaury : implémentation des fonctions des Behaviours de chaque entité.

### Benjamin et Amaury : implémentation des fonctions des Behaviours de chaque entitié.

* implémentation des fonctions de déplacements (Move,Jump...)

## Vendredi 17 juin 2022

* Benjamin, Amaury : implémentation des fonctions des Behaviours de chaque entité.
* Lucas: BotBuilder terminé

### Benjamin et Amaury : implémentation des fonctions des Behaviours de chaque entitié.

* implémentation des fonctions de hit et coup reçu.
* implémentation des fonctions de cell et closest.

## Dimanche 19 juin 2022

* Lucas: Exécution des automates dans le code
* Benjamin, Amaury : tests des fonctions faites et des automates.

### Lucas: Execution des automates dans le code

* Ajout des automates enregistrés par le BotBuilder au modèle
* Automates liés aux entités
* Automates exécutés dans les ticks 

### Benjamin et Amaury : tests des fonctions faites et des automates

* tests et débug des fonctions cell, closest et des déplacements
* modification des fonctions cell et closest.
* fonction egg implémentée (à tester sur les mobs)
* ajustement dans la hiérarchie des behaviours

### Jérémy, Ana, Amaury : génération des filons
