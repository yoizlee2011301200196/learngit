���ر�Ҷ˹��������Ŀ�ļ���˵����

������Ի�����
java 1.6.0.31
hadoop 2.0.0-cdh4.2.1

���в��裺
1��������linux�Ϲ���hadoop������α�ֲ�ʽ������ȫ�ֲ�ʽ���У�Ȼ����HDFS�Ͻ���Ŀ¼  /user/training/ 
2��Ȼ��dataset�ļ����ϴ���HDFS�ϣ��ϴ��ɹ��󹹳�Ŀ¼ /user/training/dataset
3����linux�Ͻ���Ŀ¼ naivebayesclassfier/src �����Կ�����һ��computeProbClassD.jar���������������dataset��d1.txt���ֵ�
     Country���Industry��ĸ���
4��ִ�� hadoop jar computeProbClassD.jar hadoop.classfier.bayes.naive.NaiveBayesClassfier /user/training/dataset/d1.txt  /user/training/dataset/results
     ����󼴿���HDFS /user/training/dataset/results�����ɽ����CountryProbClassD/part-r-00000��Ӧd1.txt���ൽCountry��ĸ��ʣ�Industryͬ��
5��dataset_results�Ǳ���ִ�к��ʾ������������ο���


����
�����Eclipse����Ŀ�ļ�ʱ��ע�������Ŀ�ļ�����naivebayesclassfier�������ź������е�����Ҫɾ������������Լ�����˵���õġ�

�������ֻ�����������Country��Industry����д�ģ����Ҫ��Ϊ����������ֻ������д����ļ��е���Ӧλ�ø��ľ���
�����Ҫ���ൽ�������ϵ��࣬��Ҫ��΢�Ķ�����java���ļ�������Ҫ������NaiveBayesClassfier��ġ�

���⣬��������ѵ����dataset����û�����ƣ�ֻ����Խ����򻨷�ʱ��Խ����
���ǳ���Բ��Լ�d1.txt��ά���������������ƣ�ά��̫�ߣ���������������0���������ر�Ҷ˹�����㷨��������ġ�
��ά��Ϊ200ʱ���Թ��������0��Ŀǰ������ά��Ϊ6��d1.txt�����������С�

�������ݼ�dataset��˵����
��Country��Ϊ����ԭʼ��Country���п��ܰ�������ı��ļ�����Ҫ��ÿ���ı��ļ�����������ת��Ϊһ���ϳɵ�Country�ļ��е�һ�У�
���Կո����ÿ���ַ�������ͬ�ı��������û��з�����������dataset/Country�г���Country����ļ��ⲻ����������κ��ļ���Industryͬ��

�����dataset���ݼ������������ݼ�NBCorpus�ľ���棬����NBCorpus��ÿ�����������ļ��γɵ����ݼ���
��Ҫ������ģ���Ҫ��NBCorpus���ݼ�����Ԥ�����dataset�ĸ�ʽ��

