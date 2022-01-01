# Project 3 Prep

**For tessellating hexagons, one of the hardest parts is figuring out where to place each hexagon/how to easily place hexagons on screen in an algorithmic way.
After looking at your own implementation, consider the implementation provided near the end of the lab.
How did your implementation differ from the given one? What lessons can be learned from it?**

Answer: Making a Position helper class to determine the coordinates of the top left corner of the hexagon helped a lot
with handling complexity. Instead of arithmetically calculating the positions of the hexagons through loops, having 
Positions to store them was better.

-----

**Can you think of an analogy between the process of tessellating hexagons and randomly generating a world using rooms and hallways?
What is the hexagon and what is the tesselation on the Project 3 side?**

Answer: The hexagon tiles are like the rooms and the hallways are the edges connecting them.  

-----
**If you were to start working on world generation, what kind of method would you think of writing first? 
Think back to the lab and the process used to eventually get to tessellating hexagons.**

Answer: Some helper methods would relate to:
    -Adding columns/row of the world with a given size
    -Building the tile itself
    -Grabbing the coordinates of the x and y position of a tile

-----
**What distinguishes a hallway from a room? How are they similar?**

Answer: A character can pass through it // a room has 4 walls and a hallway has 2
