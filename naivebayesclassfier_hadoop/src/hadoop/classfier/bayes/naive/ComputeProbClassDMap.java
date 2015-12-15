package hadoop.classfier.bayes.naive;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ComputeProbClassDMap extends Mapper<LongWritable, Text, Text, DoubleWritable> 
{
	public void map(LongWritable key, Text value, Context context)
		throws IOException, InterruptedException
	{
		String line = value.toString();
		if(line != null && line != "")
		{
			double probWordClass = Double.parseDouble(line.substring(line.lastIndexOf("\t") + 1));
			context.write(new Text("P(Ci|d)"), new DoubleWritable(probWordClass));
		}
	}
}
