package hadoop.classfier.bayes.naive;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ComputeProbWordClassReduce extends Reducer<Text, DoubleWritable, Text, DoubleWritable>
{
	public void reduce(Text key, Iterable<DoubleWritable> values, Context context)
		throws IOException, InterruptedException
	{
		double probWordClass = 1;
		for(DoubleWritable value : values)
		{
			probWordClass *= value.get();
			context.write(key, new DoubleWritable(probWordClass));
		}
	}
}
