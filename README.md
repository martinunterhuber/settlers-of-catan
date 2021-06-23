<img src="https://user-images.githubusercontent.com/61834693/120477901-949c6200-c3ac-11eb-95ff-a86659351149.jpg">

# Settlers of Catan

[![Build Status](https://travis-ci.com/martinunterhuber/settlers-of-catan.svg?branch=master)](https://travis-ci.com/martinunterhuber/settlers-of-catan)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=settlers-of-catan&metric=alert_status)](https://sonarcloud.io/dashboard?id=settlers-of-catan)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=settlers-of-catan&metric=coverage)](https://sonarcloud.io/dashboard?id=settlers-of-catan)

An Android application of the game Settlers of Catan. This app only supports multiplayer at the moment. You can play with up to 4 players in a single game. 


##### Table of Contents 
* [Introduction](#introduction)  
* [Rules](#rules)  
* [Authors](#authors)  
* [About the game](#about)  
  * [Game board](#board)     
  * [Building](#building)     
  * [Rolling dice & resource gathering](#dice_resources)     
  * [Trading](#trading)     
  * [Development cards](#development)   
  * [Robbers](#robbers)     
  * [Cheating](#cheating)   
  
  

## Introduction  <a name="introduction"/>   

[Official Settlers of Catan site](https://www.catan.com/game/catan)


## Rules  <a name="rules"/>  

[Official game rules (PDF)](https://www.catan.com/files/downloads/catan_base_rules_2020_200707.pdf)


 
## Authors <a name="authors"/> 

* [Martin Unterhuber](https://github.com/martinunterhuber)
  * roles: developer, UI
* [Tilman Alexander Niestroj](https://github.com/tilmanni)
  *  roles: architect, developer 
* [Edin Hasancevic](https://github.com/edhcvc)
  * roles: tester, documentation
* [Kerstin Maria Hinteregger](https://github.com/kerstinmarhi)
  * roles: UI, documentation



## About the game <a name="about"/>  

Since the game can only be played as online multiplayer, all players need an internet-enabled device. One of the devices serves as a server. The other players join this server by entering its IP address.

<p align="left">
  <img src="https://user-images.githubusercontent.com/61834693/123163062-98824800-d471-11eb-9540-580eca6ee876.gif" width="30%" height="30%">
  <img src="https://user-images.githubusercontent.com/61834693/123163512-16465380-d472-11eb-9ac6-86c10e0cdde0.gif" width="30%" height="30%"> 
</p>


At the beginning, each player can build two settlements and roads free of charge. Then the normal gameplay begins: At the beginning, the player has to roll the dice. Depending on the rolled number, the resources will be distributed and the current player can choose from the following options:


### Game board <a name="board"/>
As usual, the game board consists of 19 hexagonal fields that are assigned to a specific resource and have a dice value between 2-12. Settlements can be built at each node of the fields, which are connected by roads. Some nodes are harbour settlements. A black ship symbol can be seen at those nodes. The arrangement of the fields is randomly generated at the beginning of each game, just like the arrangement of the dice values. The desert is the only exception. It remains in the middle of the board every time a new game is created.


### Building <a name="building"/> 
If the player decides to build a settlement and clicks on a node on the board, the selected point is marked in blue. If the player now clicks on the settlement button, the blue point is transformed into a settlement of the player's colour. A settlement can only be built on one of the player's roads, provided the neighbouring intersections are not yet populated. Roads can either be built next to other existing roads of the player or next to one of his settlements. Settlements can be converted into cities during the game. The selected settlement that you want to upgrade to a city is marked in blue. If you now click on the city button, the settlement becomes a city.


#### Longest road
The first player to have 5 or more continuous road segments, from one point to another without doubling back, may claim this title and gets 2 additional victory points. If a different player gains a longer continuous road, they can claim the card from the current holder. They must have a greater amount of road segments than the current holder.


### Rolling dice & resource gathering <a name="dice_resources"/>  
When it is a player's turn, he must first roll the dice. This happens by clicking on the dice symbol in the lower right corner. A randomly generated number is displayed to the players. Depending on the number, resources are distributed to those players who have settlements or cities on the fields which are affected. Changes in resources are indicated by a flashing animation of the respective resources.  Settlements bring one unit of the resource, cities two. If the number 7 is rolled, the robbers are activated.


### Trading <a name="trading"/>  
#### Trading with Bank
You don't need a harbor to trade at 4:1, so when nobody wants to trade…  


<img src="https://user-images.githubusercontent.com/61834693/123163833-763cfa00-d472-11eb-8030-2a65fcf3e038.gif" width="30%" height="30%">   


There are 2 different kinds of harbor locations:
*  Generic harbor (3:1): 
   *  Here you can exchange 3 identical resource for any one other resource.
*  Special Harbor (2:1): 
   *  There is 1 special harbor for each type of resource (with the same symbol).
   *  The exchange rate of 2:1 only applies to the resource shown on the harbor location.
   *  A special harbor doesn't permit you to trade any other resource type at a more favorable rate (not even 3:1)!


#### Trading with Player
On your turn, you may trade resources with the other players. You and the other players negotiate the terms of your trades. Such as which cards will be exchanged.  
You can make an offer to an other player ...


<img src="https://user-images.githubusercontent.com/61834693/123166830-1fd1ba80-d476-11eb-8c1a-94ff039d5a28.gif" width="30%" height="30%">  


... or receive an offer from another player and make a counteroffer.  


<img src="https://user-images.githubusercontent.com/61834693/123166985-498ae180-d476-11eb-9d9f-6c6706e3e4e0.gif" width="30%" height="30%">  


  
### Development cards <a name="development"/>
There are a total of 26 development cards in the game, which can be divided into 5 categories:
1. Knight card: the player can move the robber. If settlements or cities of other players border on the new position of the robber.
2. Road building card: the player may build two roads.
3. Monopoly card: the player chooses a resource from which he receives the supplies of all other players.
4. Year of Plenty: the player chooses a resource, from which he receives two.
5. Victory point card: the player receives one victory point on purchase.


#### Largest army
The first player to play 3 Knight cards may claim the Largest Army and gets 2 additional victory points. If another player plays more Knight cards they may claim the title Largest Army from the original player and likewise are awarded 2 Victory points.


### Robbers <a name="robbers"/>  
The robber always occupies one of the resource fields on the board. At the beginning of the game, it is on the "Desert" field. If a player rolls the number 7 or plays the Knight Development Card, he must move the robber to another field. All players who own settlements or cities adjacent to the field with the robber do not get any resources when rolling the corresponding number. In addition, the player who moves the robbers gets a resource of his choice from one of the players who have neighbouring settlements or cities.


<img src="https://user-images.githubusercontent.com/61834693/123170737-29115600-d47b-11eb-9198-d0c57a078c7f.gif" width="30%" height="30%">

 
### Cheating <a name="cheating"/> 
A player can try to steal a resource from another player at any time. To do this, the player shakes the smartphone to open the cheat window. He then selects a player and a resource. For one round, each player has the opportunity to report this theft. To do so, he has to touch the theft icon of the player in question. If the player guesses correctly, he gets half of all the thief's resources. However, if one accuses the wrong person, the wrongly accused gets half of all the resources of the player who reported him.


<p align="left">
  <img src="https://user-images.githubusercontent.com/61834693/123167687-2876c080-d477-11eb-9cfd-1b150830a681.gif" width="30%" height="30%">
  <img src="https://user-images.githubusercontent.com/61834693/123167849-6a076b80-d477-11eb-93e1-e5c0d00d9046.gif" width="30%" height="30%"> 
</p>
