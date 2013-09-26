package c4_5;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 构造决策树的类
 * @author zhenhua.chen
 * @Description: TODO
 * @date 2013-3-1 下午4:42:07 
 *
 */
public class DecisionTree {
	/**
	 * 建树类
	 * @param dataSet
	 * @param attributeSet
	 * @return
	 */
	public TreeNode buildTree(ArrayList<ArrayList<String>> dataSet, ArrayList<String> attributeSet) {
		TreeNode node = new TreeNode();
		node.setDataSet(dataSet);
		node.setArrributeSet(attributeSet);
		
		// 根据当前数据集计算决策树的节点
		int index = -1;
		double gain = 0;
		double maxGain = 0;
		for(int i = 0; i < attributeSet.size() - 1; i++) {
			gain = ComputeUtil.computeEntropy(dataSet, attributeSet.size() - 1) - ComputeUtil.computeConditinalEntropy(dataSet, i);
			if(gain > maxGain) {
				index = i;
				maxGain = gain;
			}
		}
		ArrayList<String> splitAttributes = ComputeUtil.getTypes(dataSet, index); // 获取该节点下的分裂属性
		node.setSplitAttributes(splitAttributes);
		node.setNodeName(attributeSet.get(index));
		
		// 判断每个属性列是否需要继续分裂
		for(int i = 0; i < splitAttributes.size(); i++) {
			ArrayList<ArrayList<String>> splitDataSet = ComputeUtil.getDataSet(dataSet, index, splitAttributes.get(i));
			
			// 判断这个分裂子数据集的目标属性是否纯净，如果纯净则结束，否则继续分裂
			int desColumn = splitDataSet.get(0).size() - 1; // 目标属性列所在的列号
			ArrayList<String> desAttributes = ComputeUtil.getTypes(splitDataSet, desColumn);
			TreeNode childNode = new TreeNode();
			if(desAttributes.size() == 1) {
				childNode.setNodeName(desAttributes.get(0));
			} else {
				ArrayList<String> newAttributeSet = new ArrayList<String>();
				for(String s : attributeSet) { // 删除新属性集合中已作为决策树节点的属性值
					if(!s.equals(attributeSet.get(index))) {
						newAttributeSet.add(s);
					}
				}
				
				ArrayList<ArrayList<String>> newDataSet = new ArrayList<ArrayList<String>>();
				for(ArrayList<String> data : splitDataSet) { // 除掉columnIndex参数指定的
					ArrayList<String> tmp = new ArrayList<String>();
					for(int j = 0; j < data.size(); j++) {
						if(j != index) {
							tmp.add(data.get(j));
						}
					}
					newDataSet.add(tmp);
				}
				
				childNode = buildTree(newDataSet, newAttributeSet); // 递归建树
			}
			node.getChildrenNodes().add(childNode);
		}
		return node;
	}
	
	/**
	 * 打印建好的树
	 * @param root
	 */
	public void printTree(TreeNode root) {
		System.out.println("----------------");
		if(null != root.getSplitAttributes()) {
			System.out.print("分裂节点：" + root.getNodeName());
			for(String attr : root.getSplitAttributes()) {
				System.out.print("(" + attr + ") ");
			}
		} else {
			System.out.print("分裂节点：" + root.getNodeName());
		}
		
		if(null != root.getChildrenNodes()) {
			for(TreeNode node : root.getChildrenNodes()) {
				printTree(node);
			}
		}
		
	}
	
	/**
	 * 
	* @Title: searchTree 
	* @Description: 层次遍历树
	* @return void
	* @throws
	 */
	public void searchTree(TreeNode root) {
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		queue.offer(root);
		
		while(queue.size() != 0) {
			TreeNode node = queue.poll();
			if(null != node.getSplitAttributes()) {
				System.out.print("分裂节点：" + node.getNodeName() + "; "); 
				for(String attr : node.getSplitAttributes()) {
					System.out.print(" (" + attr + ") ");
				}
			} else {
				System.out.print("叶子节点：" + node.getNodeName() + "; "); 
			}
			
			if(null != node.getChildrenNodes()) {
				for(TreeNode nod : node.getChildrenNodes()) {
					queue.offer(nod);
				}
			}
		}
	}
	
}
