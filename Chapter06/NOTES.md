# Notes for Chapter 6: Using the Java Library

## ArrayList

Methods:
- add(E e): adds specified element to end of the list
- remove(int index): Removes the element at specified position and shifts everything left accordingly
- remove (Object o): Removes first occurance of specified element
- contains (Object o): Returns true if this list ocntains specified element
- isEmpty(): Returns true if this list has no elements
- indexOf(Object o): Returns either the first index of the element, or -1 if not found
- size(): Returns number of elements in the list
- get(int index): Returns element at specified position

- Make an ArrayList:
    ArrayList<Egg> myList = new ArrayList<Egg>();

- ArrayLists can't hold primitives, so if you want to hold ints, use Integer class