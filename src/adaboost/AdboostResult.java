package adaboost;

import java.util.ArrayList;

/**
 * 
 * @author zhenhua.chen
 * @Description: adboost�㷨�Ľ���࣬�������������ļ��Ϻ�ÿ������������Ȩ��
 * @date 2013-3-8 ����3:14:58 
 *
 */
public class AdboostResult {
	private ArrayList<ArrayList<Double>> weakClassifierSet;
	private ArrayList<Double> classifierWeightSet;
	
	public ArrayList<ArrayList<Double>> getWeakClassifierSet() {
		return weakClassifierSet;
	}
	public void setWeakClassifierSet(ArrayList<ArrayList<Double>> weakClassifierSet) {
		this.weakClassifierSet = weakClassifierSet;
	}
	public ArrayList<Double> getClassifierWeightSet() {
		return classifierWeightSet;
	}
	public void setClassifierWeightSet(ArrayList<Double> classifierWeightSet) {
		this.classifierWeightSet = classifierWeightSet;
	}
}
