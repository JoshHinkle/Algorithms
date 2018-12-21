# Algorithms for Fun (yay) 

Just fun algorithms in Java 1.8. Applying classical algorithms to real-world problems and developing analytical solutions.

## Dependencies 

Add the following to the build path (mostly used for generating inputs and drawing graphical solutions): 
https://algs4.cs.princeton.edu/code/algs4.jar

## Percolation Problem

[Read more here](http://fafnir.phyast.pitt.edu/myjava/perc/percTest.html)

Find the transition point empty-space probablility for a cell in a nxn system to ensure that the system is *probably* able to be percolated; i.e. the 'top' and 'bottom' are part of the same connected sets. 

To use, simply run PercolationStats with 2 args (n and trials) to see the mean, standard deviation, and 95% confidence interval.

Trials run in O(nlogn) time and O(n^2) space. Solved with union-find using path compression. 

## Collinear Points 

Two approaches to solving the problem of identifying collinear points (in this case, 4 or more points along the same straight line in a scatter plot). First approach is brute force, taking n^4 time, but second approach sorts by slopes and takes n^2*logn. 
