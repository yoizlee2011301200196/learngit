朴素贝叶斯分类器项目文件的说明：

程序测试环境：
java 1.6.0.31
hadoop 2.0.0-cdh4.2.1

运行步骤：
1、首先在linux上构建hadoop环境，伪分布式或者完全分布式都行，然后再HDFS上建立目录  /user/training/ 
2、然后将dataset文件夹上传到HDFS上，上传成功后构成目录 /user/training/dataset
3、在linux上进入目录 naivebayesclassfier/src ，可以看到有一个computeProbClassD.jar这个就是用来计算dataset中d1.txt被分到
     Country类和Industry类的概率
4、执行 hadoop jar computeProbClassD.jar hadoop.classfier.bayes.naive.NaiveBayesClassfier /user/training/dataset/d1.txt  /user/training/dataset/results
     命令后即可在HDFS /user/training/dataset/results中生成结果，CountryProbClassD/part-r-00000对应d1.txt分类到Country类的概率，Industry同理。
5、dataset_results是本人执行后的示例结果，仅供参考。


附：
如果用Eclipse打开项目文件时，注意这个项目文件名称naivebayesclassfier后面括号和括号中的内容要删掉，这个是我自己补充说明用的。

这个程序只是针对两个类Country和Industry来编写的，如果要改为另外两个类只需把所有代码文件中的相应位置更改就行
如果需要分类到两个以上的类，需要轻微改动几个java类文件，但需要对主类NaiveBayesClassfier大改。

另外，这个程序对训练集dataset的量没有限制，只是量越大程序花费时间越长；
但是程序对测试集d1.txt的维数（行数）有限制，维数太高，最终输入结果会是0，这是朴素贝叶斯分类算法自身决定的。
当维数为200时测试过，结果是0。目前给出的维数为6的d1.txt可以正常运行。

关于数据集dataset的说明：
以Country类为例，原始的Country类中可能包含多个文本文件，需要把每个文本文件中所有数据转换为一个合成的Country文件中的一行，
并以空格隔开每个字符串，不同文本的数据用换行符隔开。并且dataset/Country中除了Country这个文件外不能有其余的任何文件，Industry同理。

这里的dataset数据集是完整版数据集NBCorpus的精简版，即从NBCorpus中每个类挑几个文件形成的数据集。
若要完整版的，需要对NBCorpus数据集进行预处理成dataset的格式。

