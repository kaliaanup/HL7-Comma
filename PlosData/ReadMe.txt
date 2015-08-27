Modeling Healthcare Processes using Commitments: An Empirical Evaluation
--------------------------------------------------------------------------

File Description

1. Files based on the original scenario. 
	a. Traditional_HL7 (Group A)
	b. Comma (Group B)

	
2. Files based on the modified scenario. 
	a. Traditional_HL7_Modification (Group C = 1.5 * Group A + 1.5 * Group B)
	b. Comma_Modification (Group D = (Group A + Group B) - Group C)
	

Each file contains the following columns based on different criteria used for our evaluations.

1. Time. It is represented as the sum of the following columns
	a. Time spent to review a case study
	b. Time spent to create Protos business model and Message Sequence Diagrams (MSD)
	
2. Effort or Difficulty: It is represented as the sum of the following
	a. Effort to review a case study
	b. Effort to create Protos business model and Message Sequence Diagrams (MSD)
	
We compute the weighted average of effort by considering number of responses and weight assigned for each response in the scale of 1-5 (1- lowest, 5-highest).

3. Flexibility. It is represented as the sum of the following columns.

	1. MSC#: Number of Message Sequence Diagrams (SD)
	2. alt#: Number of alternate operators used in 1
	3. opt#: Number of option operators used in 1
	4. par#: Number of parallel operators used in 1
	5. sum(MSC+ALT+OPT+PAR): Sum of number of MSCs, ALTs, OPTs, and PARs in 1

The above columns are generated from XML files using a Java Code. These XML files are generated from IBM RSA v8.0.

4. Objective Quality. It is represented as the sum of the following columns.
	1. #Missing Guards. Number of missing guards in 1.
	2. #Incorrect MSC structure. Number of incorrect structure in 1.

5. Subjective Quality. It is represented as the sum of the following columns.
	1. #Scenario Covered. 0 indicates the lowest, 100 indicates the highest
	2. #Precision. 	0 indicates the lowest, 100 indicates the highest
	3. #Comprehensibility. 0 indicates the lowest, 100 indicates the highest

