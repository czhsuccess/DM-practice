package apriori;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AprioriAlgorithm {
	private static final int THRESHOLD = 2;
	
	/**
	 * 
	* @Title: appriGen 
	* @Description: 产生频繁1项集
	* @return ArrayList<ArrayList<String>>
	* @throws
	 */
	public ArrayList<ArrayList<String>> freq1Gen(ArrayList<ArrayList<String>> list) {
		Map<String, Integer> candItemMap = new HashMap<String, Integer>();
		for(int i = 0; i < list.size(); i++) {
			for(int j = 0; j < list.get(i).size(); j++) {
				if(null != candItemMap.get(list.get(i).get(j))) {
					candItemMap.put(list.get(i).get(j), candItemMap.get(list.get(i).get(j)) + 1);
				} else {
					candItemMap.put(list.get(i).get(j), 1);
				}
			}
		}
		Iterator<String> iter = candItemMap.keySet().iterator();
		ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
		while(iter.hasNext()) {
			String tmp = iter.next();
			if(candItemMap.get(tmp) >= THRESHOLD) {
				ArrayList<String> tmpList =  new ArrayList<String>();
				tmpList.add(tmp);
				dataList.add(tmpList);
			}
		}
		
		return dataList;
	}
	
	/**
	 * 
	* @Title: candidateGen 
	* @Description: 由频繁k项集产生频繁k+1项集，dataLength代表item个数
	* @return ArrayList<ArrayList<String>>
	* @throws
	 */
	public ArrayList<ArrayList<String>> freqKGen(ArrayList<ArrayList<String>>candiList, int k, ArrayList<ArrayList<String>> originalData) {
		ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
		for(int i = 0; i < candiList.size() - 1; i++) {
			for(int j = i + 1; j < candiList.size(); j++) {
				ArrayList<String> tmp = new ArrayList<String>();
				if(k == 1) {
					tmp.add(candiList.get(i).get(0));
					tmp.add(candiList.get(j).get(0));
				} else {
					for(int p = 0; p < k - 1; p++) {
						if(candiList.get(i).get(p) != candiList.get(j).get(p)) {
							break;
						}
						tmp.add(candiList.get(i).get(p));
						if(p == k - 2 && candiList.get(i).get(k-1) != candiList.get(j).get(k-1)) { // 表明前k-1项相等
							tmp.add(candiList.get(i).get(k-1));
							tmp.add(candiList.get(j).get(k-1));
						}
					}
				}
				res.add(tmp);
			}
		}
		
		Iterator<ArrayList<String>> iter = res.iterator();
		while(iter.hasNext()) {
			ArrayList<String> tmp = iter.next();
			if(AprioriUtil.getFreq(originalData, tmp) < THRESHOLD) {
				iter.remove();
			}
		}
	
		return res;
	}
	
	public static void main(String[] args) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		
		ArrayList<String> tmp = new ArrayList<String>();
		tmp.add("m");
		tmp.add("s");
		tmp.add("e");
		list.add(tmp);
		
		ArrayList<String> tmp1 = new ArrayList<String>();
		tmp1.add("m");
		tmp1.add("t");
		tmp1.add("p");
		list.add(tmp1);
		
		ArrayList<String> tmp2 = new ArrayList<String>();
		tmp2.add("m");
		tmp2.add("t");
		tmp2.add("p");
		tmp2.add("s");
		list.add(tmp2);
		
		ArrayList<String> tmp3 = new ArrayList<String>();
		tmp3.add("t");
		tmp3.add("p");
		list.add(tmp3);
		
		AprioriAlgorithm dd = new AprioriAlgorithm();
		ArrayList<ArrayList<String>> res = dd.freq1Gen(list);
		ArrayList<ArrayList<String>> res1 = dd.freqKGen(res, 1, list);
		ArrayList<ArrayList<String>> res2 = dd.freqKGen(res1, 2, list);
		ArrayList<ArrayList<String>> res3 = dd.freqKGen(res2, 3, list);
		ArrayList<ArrayList<String>> res4 = dd.freqKGen(res3, 4, list);

		System.out.println(res1);
		
    }
}
