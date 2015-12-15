package hadoop.classfier.bayes.naive;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ComputeNMap extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	public void map(LongWritable key, Text value, Context context)
		throws IOException, InterruptedException
	{
		String line = value.toString();
		if(line != null)
		{
			context.write(new Text("1"), new IntWritable(1));
		}
	}

}
