package apriori;

import java.util.ArrayList;

public class AprioriUtil {
	
	/**
	 * @param <T>
	 * 
	* @Title: isContain 
	* @Description: ���㼯��a�Ƿ��������b
	* @return boolean
	* @throws
	 */
	public static <T> boolean isContain(ArrayList<T> a, ArrayList<T> b) {
		boolean res = false;
		for(int i = 0; i < b.size(); i++) {
			if(!a.contains(b.get(i))) {
				break;
			}
			if(i == b.size() - 1) {
				res = true;
			}
		}
		return res;
	}
	
	/**
	 * 
	* @Title: getFreq 
	* @Description: ������b���a�г��ֵĴ���
	* @return int
	* @throws
	 */
	public static <T> int getFreq(ArrayList<ArrayList<T>> a, ArrayList<T> b) {
		int res = 0;
		for(int i = 0; i < a.size(); i++) {
			if(isContain(a.get(i), b)) {
				res++;
			}
		}
		return res;
	}
}
