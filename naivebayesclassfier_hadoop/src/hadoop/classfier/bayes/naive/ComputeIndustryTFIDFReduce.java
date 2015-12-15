package hadoop.classfier.bayes.naive;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ComputeIndustryTFIDFReduce extends Reducer<Text, DoubleWritable, Text, DoubleWritable> 
{
	public void reduce(Text key, Iterable<DoubleWritable> values, Context context)
		throws IOException, InterruptedException
	{
		int ni = 0;
		double sumTF = 0;
		for(DoubleWritable value : values)
		{
			ni++;
			sumTF += value.get();
		}
		
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		//Path Nfilepath = new Path("/user/training/dataset/CountryN/part-r-00000");
		Path Nfilepath = new Path("/user/training/dataset/IndustryN/part-r-00000");
		FSDataInputStream inStream = fs.open(Nfilepath);
		String line = inStream.readLine();
		int n = 0;
		if(line != null && line != "")
		{
			n = Integer.parseInt(line.substring(line.lastIndexOf("\t") + 1));
		}
	
		if(n != 0)
		{
			if(ni != 0)
			{
				double idf = Math.log(n * 1.0 / ni) / Math.log(2);
				double tfidf = idf * sumTF;
				context.write(key, new DoubleWritable(tfidf));
			}
			else
			{
				System.out.println("Compute ni wrong!");
			}
		}
		else
		{
			System.out.println("Something wrong with getting N from file!");
		}
	}
}
