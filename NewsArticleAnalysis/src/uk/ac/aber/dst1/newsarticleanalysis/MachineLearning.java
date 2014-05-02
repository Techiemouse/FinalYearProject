package uk.ac.aber.dst1.newsarticleanalysis;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.experiment.InstanceQuery;


public class MachineLearning {

	public void getMLResults() {

		try {
			InstanceQuery query = new InstanceQuery();
			query.setUsername("Diana");

			query.setPassword("ppiytirK");

			query.setQuery("select * from mlverbtraining");

			Instances traindata = query.retrieveInstances();
			Instances testdata = query.retrieveInstances();
			// Make the last attribute be the class
			traindata.setClassIndex(traindata.numAttributes() - 1);

			// Print header and instances.
			System.out.println("\nDataset:\n");
			System.out.println(traindata);

			Classifier cModel = (Classifier) new NaiveBayes();
			cModel.buildClassifier(traindata);
			Evaluation eTest = new Evaluation(testdata);
			eTest.evaluateModel(cModel, testdata);
			String strSummary = eTest.toSummaryString();
			System.out.println(strSummary);

		} catch (Exception e) {
			System.err.println("Error!" + e.getMessage());
		}

	}

}