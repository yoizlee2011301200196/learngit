package hadoop.classfier.bayes.naive;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ComputeCountryProbClassDReduce extends Reducer<Text, DoubleWritable, Text, DoubleWritable>
{
	public void reduce(Text key, Iterable<DoubleWritable> values, Context context)
		throws IOException, InterruptedException
	{
		double probClass = 1;
		int countryN = 0;
		int industryN = 0;
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		Path countryNpath = new Path("/user/training/dataset/CountryN/part-r-00000");
		FSDataInputStream inStream = fs.open(countryNpath);
		String line = "";
		while((line = inStream.readLine()) != null)
		{
			if(line != "")
			{
				countryN = Integer.parseInt(line.substring(line.lastIndexOf("\t") + 1));
			}
		}
		
		inStream.close();
		Path industryPath = new Path("/user/training/dataset/IndustryN/part-r-00000");
		inStream = fs.open(industryPath);
		line = "";
		while((line = inStream.readLine()) != null)
		{
			if(line != "")
			{
				industryN = Integer.parseInt(line.substring(line.lastIndexOf("\t") + 1));
			}
		}
		inStream.close();
		//when compute P(d|Ci) of Country
		probClass = countryN * 1.0 / (1.0 * (countryN + industryN));     //attension!!!
		//when compute P(d|Ci) of Industry
		//probClass = industryN * 1.0 / (countryN + industryN);
		
		double probClassD = 0;
		for(DoubleWritable value : values)
		{
			probClass *= value.get();
		}
		
		try
		{
			probClassD = probClass;
			if(probClassD == 0)
			{
				System.out.println("probClassD = " + probClassD);
				throw new InterruptedException();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		context.write(key, new DoubleWritable(probClassD));
	}
}
