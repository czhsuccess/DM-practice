package adaboost;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * ��֪���㷨����Ϊadaboost�㷨����������
 * �ο����ϣ�http://wenku.baidu.com/view/f2aeda2458fb770bf78a55e5.html###
 * @author zhenhua.chen
 * @Description: TODO
 * @date 2013-3-7 ����9:31:01 
 *
 */
public class PerceptronApproach {
	private static final int T = 100; // ����������
	
	/**
	 * 
	 * @param dataSet�����ݼ�
	 * @param weight��ÿ�����ݵ�Ȩ��
	 * @return
	 */
	public ArrayList<Double> getWeightVector(ArrayList<ArrayList<Double>> dataSet, ArrayList<Double> dataWeight) {
		int dataLength = 0;
		if(null == dataSet) {
			return null;
		} else {
			dataLength = dataSet.get(0).size();
		}
		
		// ��ʼ����֪����Ȩ������
		ArrayList<Double> sensorWeightVector = new ArrayList<Double>(); 
		for(int i = 0; i < dataLength; i++) {
			sensorWeightVector.add(1d);
		}
		
		// ��ʼ����֪��������
//		int increment = 1;
		
		int sign = 0; // ������ֹ������: Ȩֵ�����ĵ�ֵ����dataSet.size()�δ���0
		for(int i = 0; i < T && sign < dataSet.size(); i++) { // ����������
			for(int z = 0; z < dataSet.size(); z++) {
				double result = 0;
				for(int j = 0 ; j < dataLength; j++) {
					result += dataSet.get(z).get(j) * sensorWeightVector.get(j);
				}
				if(result > 0) {
					sign++;
					if(sign >= dataSet.size()) break;
				} else {
					sign = 0;
					for(int k = 0; k < dataLength; k++) { //����Ȩֵ����
						sensorWeightVector.set(k, sensorWeightVector.get(k) + dataSet.get(z).get(k) * dataWeight.get(z));
					}
				}
			}
		}
		
		return sensorWeightVector;
	}
	
	public static void main(String[] args) {
		File f = new File("E:/PA.txt");
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(f));
			String str = null;
			try {
				ArrayList<ArrayList<Double>> dataSet = new ArrayList<ArrayList<Double>>();
				while((str = reader.readLine()) != null) {
					ArrayList<Double> tmpList = new ArrayList<Double>();
					String[] s = str.split("\t");
					for(int i = 0; i < s.length; i++) {
						tmpList.add(Double.parseDouble(s[i]));
					}
					dataSet.add(tmpList);
				}
				
				ArrayList<Double> dataWeight = new ArrayList<Double>();
				for(int i = 0; i < dataSet.size(); i++) {
					dataWeight.add(1d);
				}
				
				PerceptronApproach d = new PerceptronApproach();
				d.getWeightVector(dataSet, dataWeight);
				System.out.println(d.getWeightVector(dataSet, dataWeight));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
