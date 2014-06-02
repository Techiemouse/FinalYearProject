package uk.ac.aber.dst1.newsarticleanalysis;



import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.Debug.Random;
import weka.core.Instances;
import weka.experiment.InstanceQuery;


public class MachineLearning {
	/**
	 * The method applies the Naive Bayes classifier to the data from the database and outputs the model and it's evaluation 
	 * @param option is used to select weather we use the verb attributes or the noun attributes
	 */
	public void getMLResultsForBayes(String option) {

		try {
			InstanceQuery query = new InstanceQuery();
			query.setUsername("Diana");

			query.setPassword("ppiytirK");
			//select all data to be used as training data
			query.setQuery("select * from ml"+option+"training");
			Instances traindata = query.retrieveInstances();
			// Make the last attribute be the class that contains the label 
			traindata.setClassIndex(traindata.numAttributes() - 1);

			//select all data to be used as testing data
			query.setQuery("select * from ml"+option+"test");
			Instances testdata = query.retrieveInstances();
			// Make the last attribute be the class that contains the label 
			testdata.setClassIndex(testdata.numAttributes() - 1);


			// Print header and instances.
			System.out.println("\nDataset:\n");
			System.out.println(traindata);


			//get the model required
			Classifier cModel = (Classifier) new NaiveBayes();

			//apply classifier to the training data and build the model
			cModel.buildClassifier(traindata);
			System.out.println("\n---the model ----:\n");
			System.out.println(cModel);

			//build an evaluation object for the training data
			Evaluation eTest = new Evaluation(testdata);
			eTest.evaluateModel(cModel, testdata);

			String strSummary = eTest.toSummaryString("=== Summary ===\n", true);
			System.out.println(strSummary);

			Random rand = new Random(1);
			int folds = 10;
			//Perform a cross-validation for a classifier on the test data.
			eTest.crossValidateModel(cModel, testdata, folds, rand);

			System.out.println(eTest.toClassDetailsString());
			System.out.println(eTest.toMatrixString());

		} catch (Exception e) {
			System.err.println("Error! " + e.getMessage());
		}

	}

	/**
	 * The method applies the Decision Tree classifier to the data from the database and outputs the model and it's evaluation 
	 * @param option is used to select weather we use the verb attributes or the noun attributes
	 */
	public void getMLResultsForDecisionTrees(String option) {

		try {
			InstanceQuery query = new InstanceQuery();
			query.setUsername("Diana");

			query.setPassword("ppiytirK");


			//select all data to be used as training data
			query.setQuery("select * from ml"+option+"training");
			Instances traindata = query.retrieveInstances();
			// Make the last attribute be the class that contains the label 
			traindata.setClassIndex(traindata.numAttributes() - 1);

			//select all data to be used as testing data
			query.setQuery("select * from ml"+option+"test");
			Instances testdata = query.retrieveInstances();
			// Make the last attribute be the class that contains the label 
			testdata.setClassIndex(testdata.numAttributes() - 1);

			// Print header and instances.
			System.out.println("\nDataset:\n");
			System.out.println(traindata);


			//get the model required
			Classifier cModel = (Classifier) new J48();

			//apply classifier to the training data and build the model
			cModel.buildClassifier(traindata);
			System.out.println("\n---the model ----:\n");
			System.out.println(cModel);

			//build an evaluation object for the training data
			Evaluation eTest = new Evaluation(testdata);
			eTest.evaluateModel(cModel, testdata);

			String strSummary = eTest.toSummaryString("=== Summary ===\n", true);
			System.out.println(strSummary);

			Random rand = new Random(1);
			int folds = 10;
			//Perform a cross-validation for a classifier on the test data.
			eTest.crossValidateModel(cModel, testdata, folds, rand);

			System.out.println(eTest.toClassDetailsString());
			System.out.println(eTest.toMatrixString());

		} catch (Exception e) {
			System.err.println("Error! " + e.getMessage());
		}

	}

}
