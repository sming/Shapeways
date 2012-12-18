Shapeways
=========

To run the program, just pass in the path to the file containing the list of artists as follows:

java psk.shapeways.ArtistPairFinder ../Artist_lists_small.txt


Coding Challenge
---
The attached text file "Artist_lists_small.txt" contains the favorite musical artists of 1000 users from LastFM. Each line is a list of up to 50 artists, formatted as follows:

Radiohead,Pulp,Morrissey,Delays,Stereophonics,Blur,Suede,Sleeper,The La's,Super Furry Animals

Band of Horses,Iggy Pop,The Velvet Underground,Radiohead,The Decemberists,Morrissey,Television etc.

Write a program that, using this file as input, produces an output file containing a list of pairs of artists which appear TOGETHER in at least fifty different lists. For example, in the above sample, Radiohead and Morrissey appear together twice, but every other pair appears only once.

Your solution cannot store a list of all possible pairs of bands (don't use a 'brute force' approach). Your solution MAY return a best guess, i.e. lists which appear at least 50 times with high probability, as long as you explain why this tradeoff improves the performance of the algorithm. Please include, either in comments or in a separate file, a brief one-paragraph description of any optimizations you made and how they impact the run-time of the algorithm.

Your solution should preferably be implemented Java or PHP. Other languages may be considered on a case-by-case basis.


Solution
---
U = number of user lists
A = number of unique artists across all lists
N = number of artists per user list 

The algorithm adopted uses 2 steps:
1/ build a map of Artist -> [ User List 1, User List 2, ... ]
2/ iterate over each pair of Artists in the map (num artists ^ 2), AND-ing the user list ID's. Each artist pair that has > 50 lists is written to the export file.

Efficiency
The approach adopted in 2/ uses an inner loop over all unique artists which is of O(A^2). step 1/ runs in O(U * N). We can ignore the individual cost of inserting an artist into the map or looking it up since its implemented as a hash map of O(1).

I believe the content of the data determines which of O(U*N) or O(A^2) dominates as numbers get large.  Empirically O(A^2) dominated but with a less diverse set of artists in a larger list (N * U), O(U*N) would dominate.

Scaling 
---
This algorithm would not scale well since it's O(N^2) - the number of operations performed increases dramatically as n increases.  

One alternative could be to use Bloom Filters. Bloom filters can give false positives e.g. incorrectly say "yes, this list contains this band" when it does not. This is typically insignificant for a well-tuned collection ~ 1%. Bloom filters are of O(k) where k is the optimal number of hash funtions used. I.e. it's performance is completely independent of A (total number unique artists) 
