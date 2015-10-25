// By Kunal Kulkarni
// Graduate Student in Computer Science at The Ohio State University 2014 - 2016
// kulkarni.120@osu.edu
// Implemented Apriori Associations using Weka

import weka.classifiers.rules.car.JCBA;
import weka.associations.CARuleMiner;
import weka.associations.ItemSet;
import weka.associations.AprioriItemSet;
import weka.filters.supervised.attribute.Discretize;
import weka.associations.Apriori;
import java.util.Random;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.*;
import weka.core.converters.*;
import weka.classifiers.trees.*;
import weka.filters.*;
import weka.filters.unsupervised.attribute.*;
import weka.classifiers.bayes.NaiveBayes;
import java.io.*;

public class apriori{

  public static void main(String[] args) throws Exception {
    // convert the directory into a dataset
    // Set Filter Options to remove StopWords, Convert all feature words into lower case
    PrintWriter out = new PrintWriter(new FileWriter("class_rules.txt"), true);
    File file = new File("classification_results.txt");
    file.createNewFile();
    FileWriter fw = new FileWriter(file.getAbsoluteFile());
    BufferedWriter bw = new BufferedWriter(fw);


    String[] options={"-S","-L"};
    Instance inst;
    Discretize    filter1;
    filter1 = new Discretize();
    filter1.setUseBetterEncoding(true);


    //TextDirectoryLoader loads all text files in a directory and uses the subdirectory names as class labels. The content of the text files will be stored in a String attribute
    TextDirectoryLoader loader = new TextDirectoryLoader();
    loader.setDirectory(new File("reuters_dataset"));
    Instances dataRaw = loader.getDataSet();

    // StringToWordVector converts the String attributes into a set of attributes representing word occurrence (depending on the tokenizer) information from the text contained in the strings
    StringToWordVector filter = new StringToWordVector();
    filter.setOptions(options);
    filter.setInputFormat(dataRaw);
    Instances filtered_data = Filter.useFilter(dataRaw, filter);
    filter1.setInputFormat(filtered_data);
    filtered_data=Filter.useFilter(filtered_data,filter1); 

    Instances data = Filter.useFilter(filtered_data,filter1); 
    Instances test_data = Filter.useFilter(filtered_data,filter1);
   
    int num_instances = filtered_data.numInstances();
    test_data.delete();
    data.delete();

    // Divide the Total Number of Instances into 70% Training Set and 30% Testing Set
    // Have a 70-30 Split between Training and Testing DataSets to get best accuracy of the Classifier 
    for(int i=0;i<num_instances;i++)
    {
	if (i%4 == 0)
	{
		inst = filtered_data.instance(i);
		test_data.add(inst);
	}
    	else
	{
		inst = filtered_data.instance(i);
		data.add(inst);
	}
    }
num_instances = data.numInstances();
 for(int i=0;i<num_instances;i++)
    {
	if (i%2 == 0)
	{
		inst = data.instance(i);
		test_data.add(inst);
	}
}

   // Create the Apriori object

    Apriori aprioriObj = new Apriori();
    aprioriObj.setClassIndex(1);

//Set mine Class Association Rules to True
    //aprioriObj.setCar(true);
//Set the value of minConfidence level
    aprioriObj.setMinMetric(0.85);
    aprioriObj.setNumRules(12);
// build associations
    aprioriObj.buildAssociations(data);
    out.println("---"+aprioriObj.toString());

CARuleMiner m_assoc = new weka.associations.Apriori();

// Classify the test instances based on the class association rules
JCBA jcbaa = new JCBA ();
jcbaa.setCBA(true);
jcbaa.setCarMiner(m_assoc);
jcbaa.buildClassifier(data);
Evaluation eTest1 = new Evaluation(data);
eTest1.evaluateModel(jcbaa,test_data);

// Calculate the classfier evaluation results such as precision, recall, fmeasure, total number of mis-classified instances
String strSummary1 = eTest1.toSummaryString();
bw.write("Total Number of incorrectly classified instances is "+eTest1.incorrect());
bw.write("\n");
    bw.write(strSummary1);
bw.write("\n");
bw.write("Num_False_Positives, Num_False_Positives, Precision, Recall, FMeasure of class index are "+eTest1.falsePositiveRate(0)+", "+eTest1.falseNegativeRate(0)+", "+eTest1.precision(0)+", "+eTest1.recall(0)+", "+eTest1.fMeasure(0));
bw.write("\n");

out.close();
bw.close();

    }   // Main ends here 
}
