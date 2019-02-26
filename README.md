# Sheriff-of-Nottingham
This is a version of the multiplayer board-game "Sheriff of Nottingham", based on OOP. It implements 3 different player strategies, which compete in order to gain the most points at the end of the game. The game consists of a set of rounds in which each player has the role of the sheriff two times. Each round contains four stages:
 1. Filling the bag;
 2. Declaring the goods; 
 3. The inspection;
 4. Replenishing the goods in each player's hand.

Here are some short descriptions of the strategies:

 1. Basic: He is the honest, fair-player, who always tells the truth in the declaration stage and always inspects all the bags, refusing any bribery.
 2. Greedy: He plays like the Basic player in the odd rounds, but in the even ones he may add an illegal good in the bag. He is also easy to bribe, accepting any offer in order not to check the player’s bag.
 3. Bribe: He will always try to bribe, as long as he has got enough money and put as many illegal goods in the bag as possible.  As a sheriff, he always checks the bags of his left and right side players. 
