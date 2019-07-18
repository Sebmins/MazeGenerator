# MazeGenerator
< Description of project Brunel University Dissertation >

## Table of Contents

* [How to use](#how-to-use)
* [Maze Generation](#Maze-Generation)
  * [Kruskal's Algorithm](#kruskal's-Algorithm)
* [Maze Solving](#Maze-solving)
  * [Depth First Search](#Depth-first-search)
  * [Breadth First Search](#Breadth-first-search)
  * [Wall Follower](#wall-follower)
* [Robot](#robot)
* [Dissertation](#dissertation)


## How to use

```java
VideoCapture video = new VideoCapture(0); // change the integer to 1 if the webcam is plugged in via usb

Number of Rows & Number of Columns: // Enter even numbers 

WaitTime: // Enter the amount of miliseconds for the application to wait between each change

Camera: // To close the application or genearte a new maze deactive the camera
```

## Maze Generation

After researching many different algoirthms for generating a random maze I chose to implement Kruskal’s Algorithm. This algorithm was developed by the mathematician and computer scientist Joseph Kruskal in 1956. The algorithm works by starting off with a set of edges and merging the edges, as long it doesn't create a loop. As shown the in picture below. 

To see further explainations of researching and implementing the algorithm look at Chapter (2.5.2) and (5.4) from my [dissertation](#dissertation).

## Maze Solving

### Depth first search

### Breadth first search

### Wall follower

## Robot

## Dissertation

[Link to disseration on github](1541110.docx)
