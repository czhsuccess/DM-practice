package kmeans;
import java.util.ArrayList;

public class KmeansAlgorithm {
	private static final int T = 10; // 最大迭代次数
	private static final double THRESHOLD = 0.1; // 中心节点位置变化大小的阈值
	
	public ArrayList<ArrayList<Double>> getClusters(ArrayList<ArrayList<Double>> dataSet, int k) {
		int dataDimension = 0;
		if(null != dataSet && dataSet.size() < k) {
			System.out.println("data size is smaller than the number to be clustered");
		} else {
			dataDimension = dataSet.get(0).size();
		}
		
		// 为每条数据赋初始类别0
		for(int i = 0; i < dataSet.size(); i++) {
			dataSet.get(i).add(0d);
		}
		
		// 随机从数据集中选注k个点作为初始的k个中心节点
		ArrayList<ArrayList<Double>> centerData = new ArrayList<ArrayList<Double>>();
		for(int i = 0; i < k; i++) {
			centerData.add(dataSet.get(i));
		}
		
		for(int i = 0; i < T; i++) {
			for(int j = 0; j < dataSet.size(); j++) {
				double classify = 0; // classify取值为0到k-1代表k个类别
				double minDistance = computeDistance(dataSet.get(j), centerData.get(0));
				for(int l = 1; l < centerData.size(); l++) {
					if(computeDistance(dataSet.get(j), centerData.get(l)) < minDistance) {
						minDistance = computeDistance(dataSet.get(j), centerData.get(l));
						classify = l;
					}
					
				}
				dataSet.get(j).set(dataDimension, classify);
			}
			
			// 每次分类后计算中心节点的位置变化情况
			double variance = computeChange(dataSet, centerData, k, dataDimension);
			if(variance < THRESHOLD) {
				break;
			}
			
			// 每次分类后重新计算中心节点
			centerData = computeCenterData(dataSet, k, dataDimension);
		}
		return dataSet;
	}
	
	/**
	 * 
	* @Title: computeDistance 
	* @Description: 计算任意两个节点间的距离
	* @return double
	* @throws
	 */
	public double computeDistance(ArrayList<Double> d1, ArrayList<Double> d2) {
		double squareSum = 0;
		for(int i = 0; i < d1.size() - 1; i++) {
			squareSum += (d1.get(i) - d2.get(i)) * (d1.get(i) - d2.get(i));
		}
		return Math.sqrt(squareSum);
	}
	
	/**
	 * 
	* @Title: computeCenterData 
	* @Description: 计算中心节点
	* @return ArrayList<Double>
	* @throws
	 */
	public ArrayList<ArrayList<Double>> computeCenterData(ArrayList<ArrayList<Double>> dataSet, int k, int dataDimension) {
		ArrayList<ArrayList<Double>> res = new ArrayList<ArrayList<Double>>();
		for(int i = 0; i < k; i++) {
			int ClassNum = 0;
			ArrayList<Double> tmp = new ArrayList<Double>();
			for(int l = 0; l < dataDimension; l++) {
				tmp.add(0d);
			}
			for(int j = 0; j < dataSet.size(); j++) {
				if(dataSet.get(j).get(dataDimension) == i) {
					ClassNum++;
					for(int m = 0; m < dataDimension; m++) {
						tmp.set(m, tmp.get(m) + dataSet.get(j).get(m));
					}
				}
			}
			for(int l = 0; l < dataDimension; l++) {
				tmp.set(l, tmp.get(l) / (double)ClassNum);
			}
			res.add(tmp);
		}
		return res;
	}
	/**
	 * 
	* @Title: computeChange 
	* @Description: 计算两轮迭代中心节点位置的变化量
	* @return double
	* @throws
	 */
	public double computeChange(ArrayList<ArrayList<Double>> dataSet, ArrayList<ArrayList<Double>> centerData, int k, int dataDimension) {
		double variance = 0;
		ArrayList<ArrayList<Double>> originalCenterData = computeCenterData(dataSet, k, dataDimension);
		for(int i = 0; i < centerData.size(); i++) {
			variance += computeDistance(originalCenterData.get(i), centerData.get(i));
		}
		return variance;
	}
	
	public static void main(String[] args) {
		final int CLUSTER1_NUM = 4;
		final int CLUSTER2_NUM = 4;
		final int CLUSTER3_NUM = 4;
		
		ArrayList<ArrayList<Double>> dataSet = new ArrayList<ArrayList<Double>>();
		
		// 产生簇1
		for(int i = 0; i < CLUSTER1_NUM; i++) {
			ArrayList<Double> cluster1 = new ArrayList<Double>();
			cluster1.add(1 + Math.random() * 2);
			cluster1.add(1 + Math.random() * 2);
			dataSet.add(cluster1);
		}
		
		// 产生簇2
		for(int i = 0; i < CLUSTER2_NUM; i++) {
			ArrayList<Double> cluster2 = new ArrayList<Double>();
			cluster2.add(Math.random());
			cluster2.add(Math.random());
			dataSet.add(cluster2);
		}
		
		// 产生簇3
		for(int i = 0; i < CLUSTER3_NUM; i++) {
			ArrayList<Double> cluster3 = new ArrayList<Double>();
			cluster3.add(3 + Math.random());
			cluster3.add(3 + Math.random());
			dataSet.add(cluster3);
		}
		
		KmeansAlgorithm d = new KmeansAlgorithm();
		ArrayList<ArrayList<Double>> dd  = d.getClusters(dataSet, 3);
		System.out.println(dd);
	}
	
}
