package hadoop.classfier.bayes.naive;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class NaiveBayesClassfier 
{

	public static void main(String[] args) throws Exception
	{
		if(args.length != 2)
		{
			System.out.println("Usage: NaiveBayesClassfier <input Dir> <output Dir>\n");
			System.exit(-1);
		}
		
		//compute the P(Ci|d) of Country and Industry of the new sample d.txt, computeProbClassD.jar,
		//hadoop jar computeProbClassD.jar haoop.classfier.bayes.naive.NaiveBayesClassfier dataset/d.txt dataset/results
		
		//first stage, compute N, computeN.jar, hadoop jar computeN.jar hadoop.classfier.bayes.naive.NaiveBayesClassfier dataset/Country dataset/CountryN
		Job computeCountryNjob = new Job();
		computeCountryNjob.setJarByClass(NaiveBayesClassfier.class);
		computeCountryNjob.setJobName("compute N of Country");
		FileInputFormat.setInputPaths(computeCountryNjob, new Path("/user/training/dataset/Country"));
		FileOutputFormat.setOutputPath(computeCountryNjob, new Path("/user/training/dataset/CountryN"));
		computeCountryNjob.setMapperClass(ComputeNMap.class);
		computeCountryNjob.setReducerClass(ComputeNReduce.class);
		computeCountryNjob.setMapOutputKeyClass(Text.class);
		computeCountryNjob.setMapOutputValueClass(IntWritable.class);
		computeCountryNjob.setOutputKeyClass(Text.class);
		computeCountryNjob.setOutputValueClass(IntWritable.class);
		boolean successCountryN = computeCountryNjob.waitForCompletion(true);
		
		Job computeIndustryNjob = new Job();
		computeIndustryNjob.setJarByClass(NaiveBayesClassfier.class);
		computeIndustryNjob.setJobName("compute N of Industry");
		FileInputFormat.setInputPaths(computeIndustryNjob, new Path("/user/training/dataset/Industry"));
		FileOutputFormat.setOutputPath(computeIndustryNjob, new Path("/user/training/dataset/IndustryN"));
		computeIndustryNjob.setMapperClass(ComputeNMap.class);
		computeIndustryNjob.setReducerClass(ComputeNReduce.class);
		computeIndustryNjob.setMapOutputKeyClass(Text.class);
		computeIndustryNjob.setMapOutputValueClass(IntWritable.class);
		computeIndustryNjob.setOutputKeyClass(Text.class);
		computeIndustryNjob.setOutputValueClass(IntWritable.class);
		boolean successIndustryN = computeIndustryNjob.waitForCompletion(true);
		
		if(successCountryN && successIndustryN)
		{
			//second stage, compute TFIDF(Wj|Ci), computeCountryTFIDF.jar/computeIndustryTFIDF.jar,
			//hadoop jar computeCountryTFIDF.jar hadoop.classfier.bayes.naive.NaiveBayesClassfier dataset/Country dataset/CountryTFIDF
			Job computeCountryTFIDFjob = new Job();
			computeCountryTFIDFjob.setJarByClass(NaiveBayesClassfier.class);
			computeCountryTFIDFjob.setJobName("compute TFIDF(Wj|Ci) of Country");
			FileInputFormat.setInputPaths(computeCountryTFIDFjob, new Path("/user/training/dataset/Country"));
			FileOutputFormat.setOutputPath(computeCountryTFIDFjob, new Path("/user/training/dataset/CountryTFIDF"));
			computeCountryTFIDFjob.setMapperClass(ComputeTFIDFMap.class);
			computeCountryTFIDFjob.setReducerClass(ComputeCountryTFIDFReduce.class);
			computeCountryTFIDFjob.setMapOutputKeyClass(Text.class);
			computeCountryTFIDFjob.setMapOutputValueClass(DoubleWritable.class);
			computeCountryTFIDFjob.setOutputKeyClass(Text.class);
			computeCountryTFIDFjob.setOutputValueClass(DoubleWritable.class);
			boolean successCountryTFIDF = computeCountryTFIDFjob.waitForCompletion(true);
			
			Job computeIndustryTFIDFjob = new Job();
			computeIndustryTFIDFjob.setJarByClass(NaiveBayesClassfier.class);
			computeIndustryTFIDFjob.setJobName("compute TFIDF(Wj|Ci) of Industry");
			FileInputFormat.setInputPaths(computeIndustryTFIDFjob, new Path("/user/training/dataset/Industry"));
			FileOutputFormat.setOutputPath(computeIndustryTFIDFjob, new Path("/user/training/dataset/IndustryTFIDF"));
			computeIndustryTFIDFjob.setMapperClass(ComputeTFIDFMap.class);
			computeIndustryTFIDFjob.setReducerClass(ComputeIndustryTFIDFReduce.class);
			computeIndustryTFIDFjob.setMapOutputKeyClass(Text.class);
			computeIndustryTFIDFjob.setMapOutputValueClass(DoubleWritable.class);
			computeIndustryTFIDFjob.setOutputKeyClass(Text.class);
			computeIndustryTFIDFjob.setOutputValueClass(DoubleWritable.class);
			boolean successIndustryTFIDF = computeIndustryTFIDFjob.waitForCompletion(true);
			
			if(successCountryTFIDF && successIndustryTFIDF)
			{
				//third stage, compute V, computeV.jar, hadoop jar  computeV.jar hadoop.classfier.bayes.naive.NaiveBayesClassfier dataset/CountryTFIDF/part-r-00000 dataset/CountryV
				Job computeCountryVjob = new Job();
				computeCountryVjob.setJarByClass(NaiveBayesClassfier.class);
				computeCountryVjob.setJobName("compute V of Country");
				FileInputFormat.setInputPaths(computeCountryVjob, new Path("/user/training/dataset/CountryTFIDF/part-r-00000"));
				FileOutputFormat.setOutputPath(computeCountryVjob, new Path("/user/training/dataset/CountryV"));
				computeCountryVjob.setMapperClass(ComputeVMap.class);
				computeCountryVjob.setReducerClass(ComputeVReduce.class);
				computeCountryVjob.setMapOutputKeyClass(Text.class);
				computeCountryVjob.setMapOutputValueClass(IntWritable.class);
				computeCountryVjob.setOutputKeyClass(Text.class);
				computeCountryVjob.setOutputValueClass(IntWritable.class);
				boolean successCountryV = computeCountryVjob.waitForCompletion(true);
				
				Job computeIndustryVjob = new Job();
				computeIndustryVjob.setJarByClass(NaiveBayesClassfier.class);
				computeIndustryVjob.setJobName("compute V of Industry");
				FileInputFormat.setInputPaths(computeIndustryVjob, new Path("/user/training/dataset/IndustryTFIDF/part-r-00000"));
				FileOutputFormat.setOutputPath(computeIndustryVjob, new Path("/user/training/dataset/IndustryV"));
				computeIndustryVjob.setMapperClass(ComputeVMap.class);
				computeIndustryVjob.setReducerClass(ComputeVReduce.class);
				computeIndustryVjob.setMapOutputKeyClass(Text.class);
				computeIndustryVjob.setMapOutputValueClass(IntWritable.class);
				computeIndustryVjob.setOutputKeyClass(Text.class);
				computeIndustryVjob.setOutputValueClass(IntWritable.class);
				boolean successIndustryV = computeIndustryVjob.waitForCompletion(true);
				
				if(successCountryV && successIndustryV)
				{
					//forth stage, compute P(Wj|Ci) for every word in d.txt, computeCountryProbWordClass.jar/computeIndustryProbWordClass.jar
					// hadoop jar computeCountryProbWordClass.jar hadoop.classfier.bayes.naive.NaiveBayesClassfier dataset/d.txt dataset/CountryPorbWordClass
					Job computeCountryProbWordClassjob = new Job();
					computeCountryProbWordClassjob.setJarByClass(NaiveBayesClassfier.class);
					computeCountryProbWordClassjob.setJobName("compute P(Wj|Ci) of Country");
					FileInputFormat.setInputPaths(computeCountryProbWordClassjob, new Path(args[0]));
					FileOutputFormat.setOutputPath(computeCountryProbWordClassjob, new Path("/user/training/dataset/CountryProbWordClass"));
					computeCountryProbWordClassjob.setMapperClass(ComputeCountryProbWordClassMap.class);
					computeCountryProbWordClassjob.setReducerClass(ComputeProbWordClassReduce.class);
					computeCountryProbWordClassjob.setMapOutputKeyClass(Text.class);
					computeCountryProbWordClassjob.setMapOutputValueClass(DoubleWritable.class);
					computeCountryProbWordClassjob.setOutputKeyClass(Text.class);
					computeCountryProbWordClassjob.setOutputValueClass(DoubleWritable.class);
					boolean successCountryProbWordClass = computeCountryProbWordClassjob.waitForCompletion(true);
					
					Job computeIndustryProbWordClassjob = new Job();
					computeIndustryProbWordClassjob.setJarByClass(NaiveBayesClassfier.class);
					computeIndustryProbWordClassjob.setJobName("compute P(Wj|Ci) of Industry");
					FileInputFormat.setInputPaths(computeIndustryProbWordClassjob, new Path(args[0]));
					FileOutputFormat.setOutputPath(computeIndustryProbWordClassjob, new Path("/user/training/dataset/IndustryProbWordClass"));
					computeIndustryProbWordClassjob.setMapperClass(ComputeIndustryProbWordClassMap.class);
					computeIndustryProbWordClassjob.setReducerClass(ComputeProbWordClassReduce.class);
					computeIndustryProbWordClassjob.setMapOutputKeyClass(Text.class);
					computeIndustryProbWordClassjob.setMapOutputValueClass(DoubleWritable.class);
					computeIndustryProbWordClassjob.setOutputKeyClass(Text.class);
					computeIndustryProbWordClassjob.setOutputValueClass(DoubleWritable.class);
					boolean successIndustryProbWordClass = computeIndustryProbWordClassjob.waitForCompletion(true);
					
					if(successCountryProbWordClass && successIndustryProbWordClass)
					{
						//fifth stage, compute P(Ci|d), one class for one time, computeCountryProbClassD.jar/computeIndustryPorbClassD.jar
						// hadoop jar computeCountryProbClassD.jar hadoop.classfier.bayes.naive.NaiveBayesClassfier dataset/CountryProbWordClass/part-r-00000 dataset/CountryProbClassD
						Job computeCountryProbClassDjob = new Job();
						computeCountryProbClassDjob.setJarByClass(NaiveBayesClassfier.class);
						computeCountryProbClassDjob.setJobName("compute P(Ci|d) of Country");
						FileInputFormat.setInputPaths(computeCountryProbClassDjob, new Path("/user/training/dataset/CountryProbWordClass/part-r-00000"));
						FileOutputFormat.setOutputPath(computeCountryProbClassDjob, new Path(args[1] + "/CountryProbClassD"));
						computeCountryProbClassDjob.setMapperClass(ComputeProbClassDMap.class);
						computeCountryProbClassDjob.setReducerClass(ComputeCountryProbClassDReduce.class);
						computeCountryProbClassDjob.setMapOutputKeyClass(Text.class);
						computeCountryProbClassDjob.setMapOutputValueClass(DoubleWritable.class);
						computeCountryProbClassDjob.setOutputKeyClass(Text.class);
						computeCountryProbClassDjob.setOutputValueClass(DoubleWritable.class);
						boolean successCountryProbClassD = computeCountryProbClassDjob.waitForCompletion(true);
						
						Job computeIndustryProbClassDjob = new Job();
						computeIndustryProbClassDjob.setJarByClass(NaiveBayesClassfier.class);
						computeIndustryProbClassDjob.setJobName("compute P(Ci|d) of Industry");
						FileInputFormat.setInputPaths(computeIndustryProbClassDjob, new Path("/user/training/dataset/IndustryProbWordClass/part-r-00000"));
						FileOutputFormat.setOutputPath(computeIndustryProbClassDjob, new Path(args[1] + "/IndustryProbClassD"));
						computeIndustryProbClassDjob.setMapperClass(ComputeProbClassDMap.class);
						computeIndustryProbClassDjob.setReducerClass(ComputeIndustryProbClassDReduce.class);
						computeIndustryProbClassDjob.setMapOutputKeyClass(Text.class);
						computeIndustryProbClassDjob.setMapOutputValueClass(DoubleWritable.class);
						computeIndustryProbClassDjob.setOutputKeyClass(Text.class);
						computeIndustryProbClassDjob.setOutputValueClass(DoubleWritable.class);
						boolean successIndustryProbClassD = computeIndustryProbClassDjob.waitForCompletion(true);
						
						if(successCountryProbClassD && successIndustryProbClassD)
						{
							System.out.println("success!");
						}
					}
				}
				
			}
		}
	}
}
