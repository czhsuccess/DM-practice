package c4_5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * C4.5算法所需的各类计算方法
 * @author zhenhua.chen
 * @Description: TODO
 * @date 2013-3-1 上午10:48:47 
 *
 */
public class ComputeUtil {
	
	/**
	 * 获取指定数据集中指定属性列的各个类别
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
	 * 获取指定数据集中指定属性列的各个类别及其计数
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
	 * 获取指定列上指定类别的数据集合(分裂后的数据子集)
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
	 * 计算指定列(属性)的信息熵
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
	 * 计算基于指定属性列对目标属性的条件信息熵
	 */
	public static double computeConditinalEntropy(ArrayList<ArrayList<String>> dataSet, int columnIndex) {
		Map<String, Integer> map = getTypeCounts(dataSet, columnIndex);  // 获取该属性列的所有列别及其计数
		
		double conditionalEntropy = 0; // 条件熵
		
		// 获取根据每个类别分割后的数据集合
		Iterator<String> iter = map.keySet().iterator(); 
		while(iter.hasNext()) {
			ArrayList<ArrayList<String>> splitDataSet = getDataSet(dataSet, columnIndex, (String)iter.next());
			// 计算目标属性列的列索引
			int desColumn = 0;
			if(splitDataSet.get(0).size() > 0) {
				desColumn = splitDataSet.get(0).size() - 1;
			}
			
			double probY = (double)splitDataSet.size() / (double)dataSet.size();
			
			Map<String, Integer> map1 = getTypeCounts(splitDataSet, desColumn); //根据分割后的子集计算后验熵
			Iterator<String> iter1 = map1.keySet().iterator();
			double proteriorEntropy = 0;
			while(iter1.hasNext()) {
				String key = (String)iter1.next(); // 目标属性列中的一个分类
				double posteriorProb = (double)map1.get(key) / (double)splitDataSet.size();
				proteriorEntropy += (-1) * posteriorProb * Math.log(posteriorProb) / Math.log(2);
			}
			
			conditionalEntropy += probY * proteriorEntropy; // 基于某个分割属性计算条件熵
		}
		return conditionalEntropy;
	}
}
