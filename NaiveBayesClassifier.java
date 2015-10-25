// By Kunal Kulkarni
// Graduate Student in Computer Science at The Ohio State University 2014 - 2016
// kulkarni.120@osu.edu
// Implemented NaiveBayesClassifier using Weka

import java.util.Random;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.*;
import weka.core.converters.*;
import weka.classifiers.trees.*;
import weka.filters.*;
import weka.filters.unsupervised.attribute.*;
import weka.classifiers.lazy.IBk;
import weka.classifiers.bayes.NaiveBayes;
import java.io.*;

public class NaiveBayesClassifier{

  public static void main(String[] args) throws Exception {
    // convert the directory into a dataset
    // Set Filter Options to remove StopWords, Convert all feature words into lower case
    String[] options={"-C","-S","-L"};
    Instance inst;

    //TextDirectoryLoader loads all text files in a directory and uses the subdirectory names as class labels. The content of the text files will be stored in a String attribute
    TextDirectoryLoader loader = new TextDirectoryLoader();
    loader.setDirectory(new File("mydataset"));
    Instances dataRaw = loader.getDataSet();

    // StringToWordVector converts the String attributes into a set of attributes representing word occurrence (depending on the tokenizer) information from the text contained in the strings
    StringToWordVector filter = new StringToWordVector();
    filter.setOptions(options);
    filter.setInputFormat(dataRaw);
    Instances filtered_data = Filter.useFilter(dataRaw, filter);
    Instances train_data = Filter.useFilter(dataRaw, filter);
    Instances test_data = Filter.useFilter(dataRaw, filter);
   
    int num_instances = filtered_data.numInstances();
    System.out.println("\n Total number of data instances\n:"+num_instances);
    test_data.delete();
    train_data.delete();

    // Divide the Total Number of Instances into 70% Training Set and 30% Testing Set
    // Have a 70-30 Split between Training and Testing DataSets to get best accuracy of the Classifier 
    for(int i=0;i<num_instances;i++)
    {
	if (i%5 == 0)
	{
		inst = filtered_data.instance(i);
		test_data.add(inst);
	}
    	else
	{
		inst = filtered_data.instance(i);
		train_data.add(inst);
	}
    }

    System.out.println("\n Total number of Training Data instances\n:"+train_data.numInstances());
    System.out.println("\n Total number of Testing Data instances\n:"+test_data.numInstances());
    System.out.println("\n\nTraining data instances are:\n\n" + train_data);
    System.out.println("\n\nTesting data instances are:\n\n" + test_data);

// Naive Bayes Classifier
    NaiveBayes nb = new NaiveBayes();
    nb.buildClassifier(train_data);
    Evaluation eTest1 = new Evaluation(train_data);
    eTest1.evaluateModel(nb,test_data);
    String strSummary1 = eTest1.toSummaryString();
    System.out.println(strSummary1);

    }   // Main ends here 
}
