# Algorithms for Fun (yay) 

Just fun algorithms in JAVA. Applying classical algorithms to real-world problems and developing analytical solutions. 

## Percolation Problem

[Read more here](http://fafnir.phyast.pitt.edu/myjava/perc/percTest.html)

Find the transition point empty-space probablility for a cell in a nxn system to ensure that the system is *probably* able to be percolated; i.e. the 'top' and 'bottom' are part of the same connected sets. 

To use, simply run PercolationStats with 2 args (n and trials) to see the mean, standard deviation, and 95% confidence interval.

Trials run in O(nlogn) time and O(n^2) space. Solved with union-find using path compression. 
