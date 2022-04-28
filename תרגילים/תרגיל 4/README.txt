bar246802




=============================
=      File description     =
=============================
SimpleHashSet.java - an abstract class implementing SimpleSet.
OpenHashSet.java - a hash-set based on chaining. Extends SimpleHashSet.
ClosedHashSet.java - a hash-set based on closed-hashing with quadratic probing. Extends SimpleHashSet.
CollectionFacadeSet.java - implements SimpleSet. Wrap an object implementing java’s Collection<String>
interface, such as LinkedList<String>, TreeSet<String>, or HashSet<String>.
SimpleSetPerformanceAnalyzer.java - measures the run-times for Collections' methods.



=============================
=          Design           =
=============================
I implemented the OpenHashSet with a nested class - a wrapper class that has a LinkedList 
and delegates methods to it.
This inner class is only used inside the OpenHashSet and it is indeed relatively small, 
so it seemed fit to use the nested class concept.
I extended the SimpleHashSet class to have all the common methods and consts - that way in the 
OpenHashSet & ClosedHashSet classes I didn't needed to copy-past code.



=============================
=  Implementation details   =
=============================
In the SimpleSetPerformanceAnalyzer class I also used a nested class in order to create an 
array of the collections we wish to test.
I tried as much as I could to make the test of add and contain general (which was relatively 
easy thanks to CollectionFacadeSet class)
that way if one wish to turn off any test he can comment-out the relevant test in the main function 
and all the others will still work.


=============================
=    Answers to questions   =
=============================

1)Q: Open hashing-explain your choice in the implementmention of the the open-hashing set using linked lists.
A:  As I explained before I implemented the OpenHashSet with a nested class - 
a wrapper class that has a LinkedList<String> and delegates methods to it.
This inner class is only used inside the OpenHashSet and it is indeed relatively small so it seemed fit to me 
to use the nested class concept.
2)Q: Closed hashing - How you implemented the deletion mechanism in ClosedHashSet -
What will happen if when deleting a value, we simply put null in its place?
A: When deleteing a value we store a "deleted flag" in its place - meaning a const string that 
marks for us a cell who is now free,
after we deleted the old value. That way we can compare addresses with the current value and the
value we wish to insert/search
If the addresses are the same as the delete flag we now we can enter a value in it's place / keep 
on searching until we find the value or find a real null cell.
3) Q: Discuss the results of the analysis in depth:
– Account, in separate, for OpenHashSet’s and ClosedHashSet’s bad results for data1.txt
– Summarize the strengths and weaknesses of each of the data structures as reflected by
the results. Which would you use for which purposes?
– How did your two implementations compare between themselves?
– How did your implementations compare to Java’s built in HashSet?
– Did you find java’s HashSet performance on data1.txt surprising? Can you explain it?
A: After examine the time analysis tests I conclude the following conclusions:
* The LinkedList come last on almost every test, it's surprising considering how popular this
collection is with developers.
* HashSet performance were topnotch - even with data1.txt, this is thanks to the 
inner implementation with the HashMap.
* TreeSet come 2 or 3 on the tests, only with data2 OpenHashSet’s and ClosedHashSet’s
had better result's then him. It seems the 
* ClosedHashSet did bad on data1.txt as expected - because each value had
the same hash code.
OpenHashSet on the other hand did considerably better on the data1.txt related tests
which also make sense due to the fact we store the elements with the same hash in lists 
and don't need to look for a new index every time.
It still did bad in:
"check if "-13170890158" is contained in the data structures initialized with data1"
in this test its result was very much the same as the LinkList's results
which also make sense because we basically search here with the same length of a list
(the inner list inside the cell who contained "-13170890158")
* on add data ClosedHashSet did better than OpenHashSet
that’s because we insert in OpenHashSet to the same list over and over which 
takes O(n).