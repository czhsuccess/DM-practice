package adaboost;

import java.util.ArrayList;

public class Test {
	private static final int NUM = 300;
	public static void main(String[] args) {
		ArrayList<ArrayList<Double>> dataSet = new ArrayList<ArrayList<Double>>();
		for(int i = 0; i < NUM; i++) {
			double x = Math.random() * 4;
			double y = Math.random() * 4;
			if((x - 2) * (x - 2) + y * y < 4) { //说明位于环形区域内
				ArrayList<Double> ring = new ArrayList<Double>();
				ring.add(x);
				ring.add(y);
				ring.add(1d); // 列别2
				dataSet.add(ring);
			} else {
				ArrayList<Double> ring = new ArrayList<Double>();
				ring.add(-x);
				ring.add(-y);
				ring.add(-1d); // 列别2
				dataSet.add(ring);
			}
		}
		
		AdaboostAlgorithm algo = new AdaboostAlgorithm();
		AdboostResult result = algo.adaboostClassify(dataSet);
		
		// 产生测试数据
		int CorrectNum = 0;
		for(int i = 0; i < 1000; i++) {
			double value = 0;
			ArrayList<Double> testData = new ArrayList<Double>();
			
			double x1 = Math.random() * 4;
			double y1 = Math.random() * 4;
			testData.add(x1);
			testData.add(y1);
			if((x1 - 2) * (x1 - 2) + y1 * y1 < 4) {
				value = 1;
			} else {
				value = -1;
			}
			if(algo.computeResult(testData, result) * value > 0) {
				CorrectNum++;
			}
		}
		System.out.println("正确率: " + (double)CorrectNum / 1000d);
	}
}
