// By Kunal Kulkarni
// Graduate Student in Computer Science at The Ohio State University 2014 - 2016
// kulkarni.120@osu.edu
// Implemented Hierarchial Clustering based on Manhattan distance using Weka

import weka.core.ContingencyTables;
import weka.core.SelectedTag;
import weka.core.ManhattanDistance;
import weka.filters.unsupervised.attribute.Remove;
import weka.clusterers.HierarchicalClusterer;
import weka.clusterers.ClusterEvaluation;
import weka.core.*;
import weka.core.converters.*;
import weka.filters.*;
import weka.filters.unsupervised.attribute.*;
import java.io.*;

public class hierarchialCluster_Manhattan{

  public static void main(String[] args) throws Exception {

    // convert the directory into a dataset
    // Set Filter Options to remove StopWords, Convert all feature words into lower case
    PrintWriter out = new PrintWriter(new FileWriter("output_hierarchial_Manhattan.txt"), true);
    double[] initialentropy = new double[125];
    double[][] finalentropy = new double[8][125];
    int classindex;
    String[] options={"-S","-L"};
    String[] options2={"-R","1"};
    String[] options3={"-L","COMPLETE","-N","8"};
    Instance inst;
    Remove          remove;
    Instances       instNew,intermediateNew;

    //TextDirectoryLoader loads all text files in a directory and uses the subdirectory names as class labels. The content of the text files will be stored in a String attribute
    TextDirectoryLoader loader = new TextDirectoryLoader();
    loader.setDirectory(new File("mydataset"));
    Instances dataRaw = loader.getDataSet();

    // StringToWordVector converts the String attributes into a set of attributes representing word occurrence (depending on the tokenizer) information from the text contained in the strings
    StringToWordVector filter = new StringToWordVector();
    filter.setOptions(options);
    filter.setInputFormat(dataRaw);
    Instances filtered_data = Filter.useFilter(dataRaw, filter);

    // This is done to calculate the initial entropy
    for(int e=0;e<filtered_data.numInstances();e++)
    {
	classindex=(int)filtered_data.instance(e).value(0);
	initialentropy[classindex]+=1;
    }
    ContingencyTables ct = new ContingencyTables();

    remove = new Remove();
    remove.setOptions(options2);
    remove.setInputFormat(filtered_data);
    intermediateNew = Filter.useFilter(filtered_data, remove);
    instNew = Filter.useFilter(filtered_data, remove);
    instNew.delete();

    // 50% of data points are considered for Clustering due to memory running out issues
    int num_instances = intermediateNew.numInstances();
    for(int i=0;i<num_instances;i++)
    {
    if(i%2 == 0)
      {
	inst=intermediateNew.instance(i);
	instNew.add(inst);
      }
    }

    num_instances = instNew.numInstances();
    out.println("Total number of data instances = "+num_instances);

    // Manhattan Distance metric is used here
    ManhattanDistance md = new ManhattanDistance(instNew);

    // Hierarchical CLustering ALgorithm
    HierarchicalClusterer hc= new HierarchicalClusterer();
    hc.setOptions(options3);
    hc.setDistanceFunction(md);
    hc.buildClusterer(instNew);

    // Outputs the exact distance between all pairs of data points
    for(int i=0;i<num_instances;i++)
    for(int j=0;j<num_instances;j++)
    out.println("Distance between Instance "+i+" and Instance "+j+" = "+md.distance(instNew.instance(i),instNew.instance(j)));


    ClusterEvaluation eval = new ClusterEvaluation();
    eval.setClusterer(hc);
    eval.evaluateClusterer(instNew);

    // Outputs the Cluster to which each data point is assigned
    out.println("Global Info Cluster "+hc.globalInfo());
    for(int i=0;i<eval.getClusterAssignments().length; i++)
    {
    out.println("instance " + i + " is put in cluster " +eval.getClusterAssignments()[i]);
    finalentropy[(int)eval.getClusterAssignments()[i]][(int)filtered_data.instance(i).value(0)]+=1;
    }

    // Cluster Evaluation results
    out.println("Total Number of Clusters generted = "+eval.getNumClusters()+"\n");
    out.println("Clusters = "+eval.clusterResultsToString());

    // Entropy results
    out.println("Entropy before clustering = "+ ct.entropy(initialentropy));
    out.println("Entropy after clustering = "+ ct.entropyConditionedOnRows(finalentropy));
    out.close();
    }   // Main ends here 
}
