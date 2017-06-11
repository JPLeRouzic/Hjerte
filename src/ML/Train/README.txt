
There are three fundamental problems for HMMs:

    Given the model parameters and observed data, estimate the optimal sequence of hidden states.
    Given the model parameters and observed data, calculate the likelihood of the data.
    Given just the observed data, estimate the model parameters.

The first and the second problem can be solved by the dynamic programming algorithms known as the Viterbi algorithm and the Forward-Backward algorithm, respectively. The last one can be solved by an iterative Expectation-Maximization (EM) algorithm, known as the Baum-Welch algorithm.

A HMM lets find hidden states corresponding to observations (couple: hidden_states/observations), i.e. estimate the model parameters.
Data must be placed in the data folder and be called train.pos/test.pos).
We have beforehand "trained" this HMM and so for a new test set, it can provide the hidden states of this test set, these are a subset of those found in the training set hidden states.

Both the training and test files are composed of lines of couple: hidden_states/observations.
The delimiter character is programmable.
The test file use a dummy hidden_states symbol.

This Hidden Markov Chain program can train and provide good classification of hidden states, even from a noisy test file. 
	* For example if a name is misspelled between the training and test files, its hidden state is still correctly found.
	* It even often works well if the observation name is changed between the training and test files.

It is also a kind of extraordinary classifier. Instead of having as output modalities like (yes/no) one can classify observations in hundred or thousands modalities. 

Eventually it could work continuously as a pipe or server.
Observation could be a link to the real observation, removing the constraint that "observation" is only a text value at this time. For example it could link to pictures. Indeed the comparizon fonction must be different from text function.





