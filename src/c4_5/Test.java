package c4_5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Test {
	public static void main(String[] args) {
		File f = new File("D:/test.txt");
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(f));
			String str = null;
			try {
				str = reader.readLine(); 
				ArrayList<String> attributeList = new ArrayList<String>();
				String[] attributes = str.split("\t");
				
				for(int i = 0; i < attributes.length; i++) {
					attributeList.add(attributes[i]);
				}
				
				ArrayList<ArrayList<String>> dataSet = new ArrayList<ArrayList<String>>();
				while((str = reader.readLine()) != null) {
					ArrayList<String> tmpList = new ArrayList<String>();
					String[] s = str.split("\t");
					for(int i = 0; i < s.length; i++) {
						tmpList.add(s[i]);
					}
					dataSet.add(tmpList);
				}
				
				DecisionTree dt = new DecisionTree();
				TreeNode root = dt.buildTree(dataSet, attributeList);
//				dt.printTree(root);
				dt.searchTree(root);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
