# Quadtrees
Quadtrees implementation
Implements a Quadtree with insert, delete, and search functions. Flexible and easy to change to contain whatever data or tuple of interest.

A Quadtree is type of tree where all nodes have four children or none (in this implementation, leaf nodes without data are kept null to save space). Only leaf nodes contain data. The root represents a quadrant of space where all the data lies. However, its four children each represent one of the sub-quadrants of the root (if you drew a line horizontally and a line veritically through the middle of the root's quadrant). When data is inserted into the tree, it will go down the correct branch which is based on what sub-quadrant it would belong to until it finds a leaf node. If the leaf is full, four new children are created below and all of its data and the inserted data are put in these new leaves. Otherwise, the data is simply inserted in the leaf node. When deleting data, the same traversal process is followed and the data deleted if found. If this leaves an empty branch, meaning there is no data in any of the nodes starting from whatever node in the tree, the nodes in this branch are also removed. The search function follows the same traversal and returns the data found in the leaf node if the location matches, returns an empty string otherwise.

Assuming the Quadtree is roughly balanced, all three functions mentioned above will have a complexity of O(logn). This is because the traversal algorithm for all three is the same, simply travel all the way down a branch which would have a height of ~logn. However, if the tree is very unbalanced the height of a branch can be ~n and the method would have complexity O(n). The delete function can be a bit more complicated, sometimes needing to traverse up and delete nodes in an empty branch. However, since this is never more than the length of a branch, if the height of a branch is logn then runtime is approximately 2logn and complexity is still O(logn).
note: these complexities are also Theta, not just Big O

The most vital purpose of a Quadtree is for spatial searching. Examples include Snapchat keeping track of where all your friends are, Uber telling you how close your ride is, etc. These types of services might also want to add a range query function. This function would return all data near a given location.

Further reading on Quadtrees:
https://medium.com/@waleoyediran/spatial-indexing-with-quadtrees-b998ae49336
