# PrivateInvestigator

Read Me!!
 
Overview of the solution:

    Here is the solution for the following task:
        - The idea here was to create new sentences from each line and each time remove one word (and 0  words) 
        - Each line is inserterd into a list of Maps that each one holds the new String and a Result Object instance.
        - Each Result Object instance is composed of the original line and the word that was excluded from it.
        - Once We have the Map we iterate on each argument to find duplicates and write them to the "output.txt" file 
        
How to run?

    - there is an "input.txt" file waiting with the sentence to test the algorithm
    - on the class "PrivateInvestigator" you will find the "main" function.
    - once the program is done a new file named "output.txt" will be created and will hold the results.
    
1. What can you say about the complexity of your code?

    - The complexity here is linear and is dependent on 2 variables: the number of lines (n) and the length of the sentence (k).
    - O(n*k)
    
2. How will your algorithm scale?

    If We have a large input and the reader will not be able to load the file into memory, we can use threads to create a better load
    and improve the time of the script. 
    
3. If you had two weeks to do this task, what would you have done differently? What would be better?

    - I would have asked for a code review to improve readability and try to reduce the number of lines in the code. 
