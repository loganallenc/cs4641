The test files are included in the tests folder. Compile and run with them with the custom ABAGAIL.jar in the PATH and CLASSPATH. Tests edited, but originally taken from jliu348’s Github.

Neural Network Tests
	java -cp PATH project2.NeuralNetTest ARFF_FILE ALG HN ITER
		where: PATH is the path to the compiled java code directory
		       ARFF_FILE is the path to the ARFF dataset file
		       HN is the number of hidden nodes
		       ITER is the number of iterations to train it through
		       ALG is the randomized optimization algorithm to use
			    rhc - Randomized Hill Climbing
			    sa - Simulated Annealing
				requires two more parameters in this order: ST CF
					where ST is the starting temperature
					      CF is the cooling factor
			    ga - Genetic Algorithm
				requires three more parameters in this order S MA MU
					where S is the starting population
					      MA is the number of individuals to mate per iteration
					      MU is the number of mutations to induce per iteration
	It will run the specified algorithm. Printing the sum of squared error and training accuracy every iteration.
	It concludes with final accuracy and the total time it took to run that many iterations.

Other Tests
	Four Peaks
		java -cp PATH project2.FourPeaksTest N T ITER
			where N is the input size
			      T is the "trigger' point of the function
			      ITER is the number of times the test is to be done with each algorithm
	Flip Flop
		java -cp PATH project2.FourPeaksTest N ITER
			where N is the input size
			      ITER is the number of times the test is to be done with each algorithm
	Traveling Salesman
		java -cp PATH project2.FourPeaksTest N ITER
			where N is the number of cities to use for the problem
			      ITER is the number of times the test is to be done with each algorithm
	
	All three will run ITER number of tests on each algorithm using predetermined parameters (listed in the analysis).
	It will print the final result of each test, along with the total amount of time it took to run the test in seconds.



| SPAM E-MAIL DATABASE ATTRIBUTES (in .names format)
|
| 48 continuous real [0,100] attributes of type word_freq_WORD 
| = percentage of words in the e-mail that match WORD,
| i.e. 100 * (number of times the WORD appears in the e-mail) / 
| total number of words in e-mail.  A "word" in this case is any 
| string of alphanumeric characters bounded by non-alphanumeric 
| characters or end-of-string.
|
| 6 continuous real [0,100] attributes of type char_freq_CHAR
| = percentage of characters in the e-mail that match CHAR,
| i.e. 100 * (number of CHAR occurences) / total characters in e-mail
|
| 1 continuous real [1,...] attribute of type capital_run_length_average
| = average length of uninterrupted sequences of capital letters
|
| 1 continuous integer [1,...] attribute of type capital_run_length_longest
| = length of longest uninterrupted sequence of capital letters
|
| 1 continuous integer [1,...] attribute of type capital_run_length_total
| = sum of length of uninterrupted sequences of capital letters
| = total number of capital letters in the e-mail
|
| 1 nominal {0,1} class attribute of type spam
| = denotes whether the e-mail was considered spam (1) or not (0), 
| i.e. unsolicited commercial e-mail.  
|
| For more information, see file 'spambase.DOCUMENTATION' at the
| UCI Machine Learning Repository: http://www.ics.uci.edu/~mlearn/MLRepository.html
	