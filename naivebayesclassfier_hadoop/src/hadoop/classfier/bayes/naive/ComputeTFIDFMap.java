package hadoop.classfier.bayes.naive;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ComputeTFIDFMap extends Mapper<LongWritable, Text,Text, DoubleWritable> 
{
	public void map(LongWritable key, Text value, Context context)
		throws IOException, InterruptedException
	{
		String line = value.toString();
		String[] words = line.split(" ");
		Map<String, Integer> wordfrequency = new HashMap<String, Integer>();
		int maxfrequency = 0;
		for(int i = 0; i < words.length; i++)
		{
			if(words[i] == null || words[i] == "" || words[i] == " ")
			{
				continue;
			}
			if(wordfrequency.containsKey(words[i]))
			{
				wordfrequency.put(words[i], wordfrequency.get(words[i]) + 1);
				if(wordfrequency.get(words[i]) > maxfrequency)
				{
					maxfrequency = wordfrequency.get(words[i]);
				}
			}
			else
			{
				wordfrequency.put(words[i], new Integer(1));
				if(wordfrequency.get(words[i]) > maxfrequency)
				{
					maxfrequency = wordfrequency.get(words[i]);
				}
			}
		}
		try
		{
			for(String word : wordfrequency.keySet())
			{
				if(maxfrequency != 0)
				{
					double tf = wordfrequency.get(word) * 1.0 / maxfrequency;
					context.write(new Text(word), new DoubleWritable(tf));
				}
				else
				{
					System.out.println("compute maxfrequency wrong!");
				}
			}
		}catch(NullPointerException e)
		{
			System.out.println("Construct Map<String, Integer> wrong!");
		}
	}
}
