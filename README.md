# Ex2_OOP
#### Contributers: Bar Goldenberg, Sappit Bohbot
### About the project:
___
in this project we are assigned to implement a Directed Weighted
Graph, and run algorithms on it.
and we were asked to build a GUI were you could load graphs using a JSON file.
### Problems we encountered:
___
in this project we were asked to be able to generate large graphs
up to 1,000,000 Vertices and 10,000,000 Edges
therefore we must take Space Complexity into account.
in addition to those problems we need to be able to run algorithms
efficiently on our graph.
### Graph Design
___
Our graph is implemented using two HashMaps
the first one (V) holds Vertices (nodes)
and the second one (E) holds Edges.
each node holds two ArrayLists the first array list holds the edges
whose source is the node itself and the second one holds the edges whose destination is the
node itself.
The Edges hold the source node key and the destination node key and the edges weight.

### Building large graphs:
___
#### 10 Vertices 90 Edges: 10 ms
#### 100 Vertices 1,000 Edges: 24 ms
#### 1,000 Vertices 10,000 Edges: 50 ms
#### 10,000 Vertices 100,000 Edges: 184 ms
#### 100,000 Vertices 1,000,000 Edges: 1 sec 214 ms
#### 1,000,000 Vertices 10,000,000 Edges: 13 sec 279 ms
### Running Algorithms:
___
#### isConnected:
_______
#### 10 Vertices 90 Edges: 22 ms
#### 100 Vertices 1,000 Edges: 42 ms
#### 1,000 Vertices 10,000 Edges: 101 ms
#### 10,000 Vertices 100,000 Edges: 426 ms
#### 100,000 Vertices 2,000,000 Edges: 6 sec 101 ms
#### 1,000,000 Vertices 10,000,000 Edges: could not create connected graph, 17 sec 24 ms for false result.
___
#### Center:
____
#### 10 Vertices 90 Edges: 47 ms
#### 100 Vertices 1,000 Edges: 169 ms
#### 1,000 Vertices 10,000 Edges: 2 sec 274 ms
#### 10,000 Vertices 100,000 Edges: 4 min 56 sec
#### 100,000 Vertices 1,000,000 Edges: timeout
#### 1,000,000 Vertices 10,000,000 Edges: timeout
___
### GUI:
___
![screenshotGUI](https://user-images.githubusercontent.com/89586016/145419069-af8fdf09-f17e-4c86-ba3a-da9dd3ca721f.png)
#### The red path shows a TSP path going through (1,26,7,5,29)
#### The green path shows the shortest path from 12 to 10
#### the pink Vertex represents the graphs center
___
### Class Diagram:
___
![image](https://user-images.githubusercontent.com/89586016/145232356-d7e6e0b8-a6dd-45f1-9465-1025dd380d40.png)
### How to run:
______________
1. in the folder "out/artifacts/Ex2_Jar" there is a jar file
2. open folder in terminal and type the command java -jar Ex2.jar "Graph json file path"
####  you can find the json files inside the data folder.
____
#### please notice that this jar file was compiled with open JDK 17 therefore any older version of JDK won't work.