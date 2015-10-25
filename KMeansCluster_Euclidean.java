// By Kunal Kulkarni
// Graduate Student in Computer Science at The Ohio State University 2014 - 2016
// kulkarni.120@osu.edu
// Implemented KMeansClustering based on Euclidean distance using Weka

import weka.core.ContingencyTables;
import weka.core.SelectedTag;
import weka.core.EuclideanDistance;
import weka.filters.unsupervised.attribute.Remove;
import weka.clusterers.SimpleKMeans;
import weka.clusterers.ClusterEvaluation;
import weka.core.*;
import weka.core.converters.*;
import weka.filters.*;
import weka.filters.unsupervised.attribute.*;
import java.io.*;

public class kmeansCluster_Euclidean{

  public static void main(String[] args) throws Exception {

   // convert the directory into a dataset
   // Set Filter Options to remove StopWords, Convert all feature words into lower case
   PrintWriter out = new PrintWriter(new FileWriter("output_kmeans_Euclidean.txt"), true); 
    double[] initialentropy = new double[125];
    double[][] finalentropy = new double[12][125];
    int classindex; 
    String[] options={"-S","-L"};
    String[] options2={"-R","1"};
    String[] options3={"-init","1","-M","-O","-N","12"};
    Instance inst;
    Remove          remove;
    Instances       instNew;
    Instances       centroids;

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
    instNew = Filter.useFilter(filtered_data, remove); 
    int num_instances = instNew.numInstances();
    out.println("Total number of data instances = "+num_instances);

    // Euclidean Distance metric is used here
    EuclideanDistance ed = new EuclideanDistance(instNew);
 
    // K-Means CLustering ALgorithm
    SimpleKMeans km = new SimpleKMeans();
    km.setOptions(options3);
    km.buildClusterer(instNew);

    // Outputs the Centroids generated
    centroids = km.getClusterCentroids();
    out.println("Number of Centroids = "+centroids.numInstances());
    for(int i=0;i<centroids.numInstances(); i++)
    out.println("Centroid "+i+" : "+ centroids.instance(i));

    // Outputs the exact distance between each data point to each Centroid
    for(int i=0;i<num_instances;i++)
    for(int j=0;j<centroids.numInstances();j++)
    out.println("Distance between Instance "+i+" and Centroid "+j+" = "+ed.distance(instNew.instance(i),centroids.instance(j)));
    ClusterEvaluation eval = new ClusterEvaluation();
    eval.setClusterer(km);
    eval.evaluateClusterer(instNew);

    // Outputs the Cluster to which each data point is assigned
    for(int i=0;i<eval.getClusterAssignments().length; i++)
    {
    out.println("instance " + i + " is put in cluster " +eval.getClusterAssignments()[i]);
    finalentropy[(int)eval.getClusterAssignments()[i]][(int)filtered_data.instance(i).value(0)]+=1;
    }

    // Cluster Evaluation results
    out.println("Total Number of Clusters generted = "+eval.getNumClusters());
    for(int i=0;i<km.getClusterSizes().length; i++)
    out.println("Number of Instances in Cluster "+i+" = "+km.getClusterSizes()[i]);
    out.println("Sum of Squared Error for all the Clusters = "+km.getSquaredError());

    // Entropy results
    out.println("Entropy before clustering = "+ ct.entropy(initialentropy));
    out.println("Entropy after clustering = "+ ct.entropyConditionedOnRows(finalentropy));
    out.close();

    }  // Main ends here 
}  // Class ends here
