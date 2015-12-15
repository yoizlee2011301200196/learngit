package hadoop.classfier.bayes.naive;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ComputeIndustryProbWordClassMap extends Mapper<LongWritable, Text, Text, DoubleWritable>
{
	public void map(LongWritable key, Text value, Context context)
		throws IOException,InterruptedException
	{
		String dline = value.toString();
		double probWordClass = 0;
		
		int v = 0;
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		//Path vpath = new Path("/user/training/dataset/CountryV/part-r-00000");
		Path vpath = new Path("/user/training/dataset/IndustryV/part-r-00000");
		FSDataInputStream inStream = fs.open(vpath);
		String vline = inStream.readLine();
		if(vline != null && vline != "")
		{
			v = Integer.parseInt(vline.substring(vline.lastIndexOf("\t") + 1));
		}
		inStream.close();
		
		//Path tfidfpath = new Path("/user/training/dataset/CountryTFIDF/part-r-00000");
		Path tfidfpath = new Path("/user/training/dataset/IndustryTFIDF/part-r-00000");
		inStream = fs.open(tfidfpath);
		double sumTFIDF = 0;
		double tfidfWordC =0;
		String tfidfline = "";
		double wordtfidf = 0;
		while((tfidfline = inStream.readLine()) != null)
		{
			wordtfidf = Double.parseDouble(tfidfline.substring(tfidfline.lastIndexOf("\t") + 1));
			sumTFIDF += wordtfidf;
		}
		inStream.close();
		inStream = fs.open(tfidfpath);
		String word = "";
		wordtfidf = 0;
		boolean find = false;
		while((tfidfline = inStream.readLine()) != null)
		{
			word = tfidfline.substring(0,tfidfline.indexOf("\t"));
			wordtfidf = Double.parseDouble(tfidfline.substring(tfidfline.lastIndexOf("\t") + 1));
			if(word == dline)
			{
				find = true;
				tfidfWordC = wordtfidf;
				probWordClass = (1 + tfidfWordC) * 1.0 / (v + sumTFIDF);
				context.write(new Text(dline), new DoubleWritable(probWordClass));
			}
		}
		if(!find)
		{
			probWordClass = 1.0 / (v + sumTFIDF);
			context.write(new Text(dline), new DoubleWritable(probWordClass));
		}
		inStream.close();
		
	}
}
