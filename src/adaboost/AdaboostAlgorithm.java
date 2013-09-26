package adaboost;

import java.util.ArrayList;

/**
 * http://wenku.baidu.com/view/49478920aaea998fcc220e98.html
 * @author zhenhua.chen
 * @Description: TODO
 * @date 2013-3-8 ����3:09:36 
 *
 */
public class AdaboostAlgorithm {
	private static final int T = 30; // ��������
	PerceptronApproach pa = new PerceptronApproach(); // ��������
	
	/**
	 * 
	* @Title: adaboostClassify 
	* @Description: ͨ��ѵ�����������Ϸ�����
	* @return AdboostResult
	* @throws
	 */
	public AdboostResult adaboostClassify(ArrayList<ArrayList<Double>> dataSet) {
		AdboostResult res = new AdboostResult();
		
		int dataDimension;
		if(null != dataSet && dataSet.size() > 0) {
			dataDimension = dataSet.get(0).size();
		} else {
			return null;
		}
		
		// Ϊÿ�����ݵ�Ȩ�ظ���ֵ
		ArrayList<Double> dataWeightSet = new ArrayList<Double>();
		for(int i = 0; i < dataSet.size(); i ++) {
			dataWeightSet.add(1.0 / (double)dataSet.size());
		}
		
		// �洢ÿ������������Ȩ��
		ArrayList<Double> classifierWeightSet = new ArrayList<Double>();
		
		// �洢ÿ����������
		ArrayList<ArrayList<Double>> weakClassifierSet = new ArrayList<ArrayList<Double>>();
		
		for(int i = 0; i < T; i++) {
			// ������������
			ArrayList<Double> sensorWeightVector = pa.getWeightVector(dataSet, dataWeightSet);
			weakClassifierSet.add(sensorWeightVector);
			
			// ���������������
			double error = 0; //������
			int rightClassifyNum = 0;
			ArrayList<Double> cllassifyResult = new ArrayList<Double>();
			for(int j = 0; j < dataSet.size(); j++) { 
				double result = 0;
				for(int k = 0; k < dataDimension - 1; k++) {
					result += dataSet.get(j).get(k) * sensorWeightVector.get(k);
					
				}
				result += sensorWeightVector.get(dataDimension - 1);
				if(result < 0) { // ˵��Ԥ�����
					error += dataWeightSet.get(j);
					cllassifyResult.add(-1d);
				} else{ 
					cllassifyResult.add(1d);
					rightClassifyNum++;
				}
			}
			System.out.println("������" + dataSet.size() + "��ȷԤ����" + rightClassifyNum);
			if(dataSet.size() == rightClassifyNum) {
				classifierWeightSet.clear();
				weakClassifierSet.clear();
				classifierWeightSet.add(1.0);
				weakClassifierSet.add(sensorWeightVector);
				break;
			}
			
			// �������ݼ���ÿ�����ݵ�Ȩ�ز���һ��
			double dataWeightSum = 0;
			for(int j = 0; j < dataSet.size(); j++) {
				dataWeightSet.set(j, dataWeightSet.get(j) * Math.pow(Math.E, (-1) * 0.5 * Math.log((1 - error) / error) * cllassifyResult.get(j))); // ����http://wenku.baidu.com/view/49478920aaea998fcc220e98.html�����µ�Ȩ���ٳ�һ������
				dataWeightSum += dataWeightSet.get(j);
			}
			for(int j = 0; j < dataSet.size(); j++) {
				dataWeightSet.set(j, dataWeightSet.get(j) / dataWeightSum);
			}
			
			
			// ���������������Ȩ��
			double currentWeight = (0.5 * Math.log((1 - error) / error));
			classifierWeightSet.add(currentWeight);
			System.out.println("classifier weight: " + currentWeight);
		}
		
		res.setClassifierWeightSet(classifierWeightSet);
		res.setWeakClassifierSet(weakClassifierSet);
		return res;
	}
	
	/**
	 * 
	* @Title: computeResult 
	* @Description: �����������ݵ����
	* @return double
	* @throws
	 */
	public int computeResult(ArrayList<Double> data, AdboostResult classifier) {
		double result = 0;
		int dataSize = data.size();
		ArrayList<ArrayList<Double>> weakClassifierSet = classifier.getWeakClassifierSet();
		ArrayList<Double> classifierWeightSet = classifier.getClassifierWeightSet();
		for(int i = 0; i < weakClassifierSet.size(); i++) {
			for(int j = 0; j < dataSize; j++) {
				result += weakClassifierSet.get(i).get(j) * data.get(j) * classifierWeightSet.get(i);
			}
			result += weakClassifierSet.get(i).get(dataSize);
		}
		if(result > 0) {
			return 1;
		} else {
			return -1;
		}
		
	}
	
	public static void main(String[] args) {
		/**
		 * �������ݣ����������������һ��λ��Բ�ڣ���һ��λ�ڰ���СԲ�Ĵ�Բ�ڣ��ɻ�״
		 * СԲ�뾶Ϊ1����Բ�뾶Ϊ2������Բ��λ��(2, 2)��
		 */
		final int SMALL_CIRCLE_NUM = 24;
		final int RING_NUM = 34;
		
		ArrayList<ArrayList<Double>> dataSet = new ArrayList<ArrayList<Double>>();
		
		// ����СԲ����
		for(int i = 0; i < SMALL_CIRCLE_NUM; i++) {
			double x = 1 + Math.random() * 2; // 1��3�������
			double y = 1 + Math.random() * 2; // 1��3�������
			if((x - 2) * (x - 2) + (y - 2) * (y - 2) - 1 <= 0) { //˵��λ��Բ��
				ArrayList<Double> smallCircle = new ArrayList<Double>();
				smallCircle.add(x);
				smallCircle.add(y);
				smallCircle.add(1d); // �б�1
				dataSet.add(smallCircle);
			}
		}
		
		// ������Χ��������
		for(int i = 0; i < RING_NUM; i++) {
			double x1 = Math.random() * 4;
			double y1 = Math.random() * 4;
			if((x1 - 2) * (x1 - 2) + (y1 - 2) * (y1 - 2) - 4 < 0 && (x1 - 2) * (x1 - 2) + (y1 - 2) * (y1 - 2) - 1 > 0) { //˵��λ�ڻ���������
				ArrayList<Double> ring = new ArrayList<Double>();
				ring.add(-x1);
				ring.add(-y1);
				ring.add(-1d); // �б�2
				dataSet.add(ring);
			}
		}
		
		AdaboostAlgorithm algo = new AdaboostAlgorithm();
		AdboostResult result = algo.adaboostClassify(dataSet);
		
		// ������������
		for(int i = 0; i < 10; i++) {
		
		ArrayList<Double> testData = new ArrayList<Double>();
		
		double x1 = Math.random() * 4;
		double y1 = Math.random() * 4;
		if((x1 - 2) * (x1 - 2) + (y1 - 2) * (y1 - 2) - 4 < 0 && (x1 - 2) * (x1 - 2) + (y1 - 2) * (y1 - 2) - 1 > 0) {
			testData.add(x1);
			testData.add(y1);
		}
		
//		double x = 1 + Math.random() * 2; // 1��3�������
//		double y = 1 + Math.random() * 2; // 1��3�������
//		if((x - 2) * (x - 2) + (y - 2) * (y - 2) - 1 <= 0) { //˵��λ��Բ��
//			testData.add(x);
//			testData.add(y);
//		}
		
		algo.computeResult(testData, result);
		System.out.println(algo.computeResult(testData, result));
		}
		
	}
}
