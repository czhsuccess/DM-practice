package c4_5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * C4.5�㷨����ĸ�����㷽��
 * @author zhenhua.chen
 * @Description: TODO
 * @date 2013-3-1 ����10:48:47 
 *
 */
public class ComputeUtil {
	
	/**
	 * ��ȡָ�����ݼ���ָ�������еĸ������
	* @Title: getTypes 
	* @Description: TODO
	* @return ArrayList<String>
	* @throws
	 */
	public static ArrayList<String> getTypes(ArrayList<ArrayList<String>> dataSet, int columnIndex) {
		ArrayList<String> list = new ArrayList<String>();
		for(ArrayList<String> data : dataSet) {
			if(!list.contains(data.get(columnIndex))) {
				list.add(data.get(columnIndex));
			}
		}
		return list;
	}
	
	/**
	 * ��ȡָ�����ݼ���ָ�������еĸ�����������
	* @Title: getClassCounts 
	* @Description: TODO
	* @return Map<String,Integer>
	* @throws
	 */
	public static Map<String, Integer> getTypeCounts(ArrayList<ArrayList<String>> dataSet, int columnIndex) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for(ArrayList<String> data : dataSet) {
			String key = data.get(columnIndex);
			if(map.containsKey(key)) {
				map.put(key, map.get(key) + 1);
			} else {
				map.put(key, 1);
			}
		}
		return map;
	}
	
	/**
	 * ��ȡָ������ָ���������ݼ���(���Ѻ�������Ӽ�)
	* @Title: getDataSet 
	* @Description: TODO
	* @return ArrayList<ArrayList<String>>
	* @throws
	 */
	public static ArrayList<ArrayList<String>> getDataSet(ArrayList<ArrayList<String>> dataSet, int columnIndex, String attribueClass) {
		ArrayList<ArrayList<String>> splitDataSet = new ArrayList<ArrayList<String>>();
		for(ArrayList<String> data : dataSet) {
			if(data.get(columnIndex).equals(attribueClass)) {
				splitDataSet.add(data);
			}
		}
		
		return splitDataSet;
	}
	
	/**
	 * ����ָ����(����)����Ϣ��
	* @Title: computeEntropy 
	* @Description: TODO
	* @return double
	* @throws
	 */
	public static double computeEntropy(ArrayList<ArrayList<String>> dataSet, int columnIndex) {
		Map<String, Integer> map = getTypeCounts(dataSet, columnIndex);
		int dataSetSize = dataSet.size();
		Iterator<String> keyIter = map.keySet().iterator();
		double entropy = 0;
		while(keyIter.hasNext()) {
			double prob = (double)map.get((String)keyIter.next()) / (double)dataSetSize;
			entropy += (-1) * prob * Math.log(prob) / Math.log(2); 
			
		}
		return entropy;
	}
	
	/**
	 * �������ָ�������ж�Ŀ�����Ե�������Ϣ��
	 */
	public static double computeConditinalEntropy(ArrayList<ArrayList<String>> dataSet, int columnIndex) {
		Map<String, Integer> map = getTypeCounts(dataSet, columnIndex);  // ��ȡ�������е������б������
		
		double conditionalEntropy = 0; // ������
		
		// ��ȡ����ÿ�����ָ������ݼ���
		Iterator<String> iter = map.keySet().iterator(); 
		while(iter.hasNext()) {
			ArrayList<ArrayList<String>> splitDataSet = getDataSet(dataSet, columnIndex, (String)iter.next());
			// ����Ŀ�������е�������
			int desColumn = 0;
			if(splitDataSet.get(0).size() > 0) {
				desColumn = splitDataSet.get(0).size() - 1;
			}
			
			double probY = (double)splitDataSet.size() / (double)dataSet.size();
			
			Map<String, Integer> map1 = getTypeCounts(splitDataSet, desColumn); //���ݷָ����Ӽ����������
			Iterator<String> iter1 = map1.keySet().iterator();
			double proteriorEntropy = 0;
			while(iter1.hasNext()) {
				String key = (String)iter1.next(); // Ŀ���������е�һ������
				double posteriorProb = (double)map1.get(key) / (double)splitDataSet.size();
				proteriorEntropy += (-1) * posteriorProb * Math.log(posteriorProb) / Math.log(2);
			}
			
			conditionalEntropy += probY * proteriorEntropy; // ����ĳ���ָ����Լ���������
		}
		return conditionalEntropy;
	}
}
