# Spread

# Contributers 
* Z'heondre Angel Calcano 01141688
* Ryan Allen

# Idea
Develop a zombie game where the player or players play as the zombies and your responsibility is
infecting or convertering as many civilians as possible before law enforcement stops you or before the infected get cured. Difficulty will be increased per level. We would like to have this in 3d so we have to start doing the research now to see which libraries will be needed and how long will it take. 2d would be easier. 

# Final Project Goals

* 3d if possible if not then 2d bird's eye view, or 2d scrolling 

Graphic Libraries:

Master Class: 
* Allocates all objects 
* Manages Levels
* Monitors memory usage 

Game Gui/Layout: 
* Where the user should hit on the screen to attack, drop a bomb, run etc
* Splash screen
* Menue
* 

Weapons Items Class: 

Implement Civilian Functionality: 
* Walk randomly, react to being attack.
* Run to the closet health pack, emt, hazmat or hospital to get healed if infected
* Call for help if hurt or see a civilian near by getting hurt.
* Try to fight off the zombies, pick up a weapon if near by. 
* will try to heal infection before turning into a zombie
* Depending the level might carry a weapon

Zombie Functionality; 
* Can choose to set a bomb off at first to start an infection but this will alert people, 
* or infect in a sneaky way such as an attack from behing ect, 
* If more than one zombie is alive and your current player dies and no other player is using another zombie u can control the next zombie. 

Emt: 
* Cure the infected, emts carry no weapons 
* Emts can teleport infected to the hospital
* Can call for more emts or police 

Medics: 
* Carries a weapon 
* Faster healing 
* Transport the hurt

Police:
* Will shoot at zombies, can call for back up, eat donuts for health or throw donuts to distract zombie

Debug class: 
* Monitor statistics, memory usage, print position for the objects that is needed
* Basicly a command interface to help the developers debug the system if there is a memory leak.

Accounts: 
* Make a user name
* choose type of zombie or way to infect

Levels: 
* Need to create a level abstraction to keep adding more to each level as the person plays.
* Should each level have a timer ? the higher the level the more time u have to stay alive or should it be based on making every one a zombie ? 

# If we Have enough time

Accounts: 
* User should be able to sign in make an account, we should save them in a data base, 

* Multiplayer Mode
* People can choose to be zombies or civilians 

Hazmat:
* Has a faster firing weapon that causes more damage
* Cures infections faster, maybe freeze zombies ? 
Army class: 
* Will do more damage,
* Can set bombs, 
* Walk faster and will have more units
