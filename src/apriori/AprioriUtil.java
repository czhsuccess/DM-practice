package apriori;

import java.util.ArrayList;

public class AprioriUtil {
	
	/**
	 * @param <T>
	 * 
	* @Title: isContain 
	* @Description: 计算集合a是否包含集合b
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
	* @Description: 计算项b在项集a中出现的次数
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
