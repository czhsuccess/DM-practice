package c4_5;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * �������������
 * @author zhenhua.chen
 * @Description: TODO
 * @date 2013-3-1 ����4:42:07 
 *
 */
public class DecisionTree {
	/**
	 * ������
	 * @param dataSet
	 * @param attributeSet
	 * @return
	 */
	public TreeNode buildTree(ArrayList<ArrayList<String>> dataSet, ArrayList<String> attributeSet) {
		TreeNode node = new TreeNode();
		node.setDataSet(dataSet);
		node.setArrributeSet(attributeSet);
		
		// ���ݵ�ǰ���ݼ�����������Ľڵ�
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
		ArrayList<String> splitAttributes = ComputeUtil.getTypes(dataSet, index); // ��ȡ�ýڵ��µķ�������
		node.setSplitAttributes(splitAttributes);
		node.setNodeName(attributeSet.get(index));
		
		// �ж�ÿ���������Ƿ���Ҫ��������
		for(int i = 0; i < splitAttributes.size(); i++) {
			ArrayList<ArrayList<String>> splitDataSet = ComputeUtil.getDataSet(dataSet, index, splitAttributes.get(i));
			
			// �ж�������������ݼ���Ŀ�������Ƿ񴿾����������������������������
			int desColumn = splitDataSet.get(0).size() - 1; // Ŀ�����������ڵ��к�
			ArrayList<String> desAttributes = ComputeUtil.getTypes(splitDataSet, desColumn);
			TreeNode childNode = new TreeNode();
			if(desAttributes.size() == 1) {
				childNode.setNodeName(desAttributes.get(0));
			} else {
				ArrayList<String> newAttributeSet = new ArrayList<String>();
				for(String s : attributeSet) { // ɾ�������Լ���������Ϊ�������ڵ������ֵ
					if(!s.equals(attributeSet.get(index))) {
						newAttributeSet.add(s);
					}
				}
				
				ArrayList<ArrayList<String>> newDataSet = new ArrayList<ArrayList<String>>();
				for(ArrayList<String> data : splitDataSet) { // ����columnIndex����ָ����
					ArrayList<String> tmp = new ArrayList<String>();
					for(int j = 0; j < data.size(); j++) {
						if(j != index) {
							tmp.add(data.get(j));
						}
					}
					newDataSet.add(tmp);
				}
				
				childNode = buildTree(newDataSet, newAttributeSet); // �ݹ齨��
			}
			node.getChildrenNodes().add(childNode);
		}
		return node;
	}
	
	/**
	 * ��ӡ���õ���
	 * @param root
	 */
	public void printTree(TreeNode root) {
		System.out.println("----------------");
		if(null != root.getSplitAttributes()) {
			System.out.print("���ѽڵ㣺" + root.getNodeName());
			for(String attr : root.getSplitAttributes()) {
				System.out.print("(" + attr + ") ");
			}
		} else {
			System.out.print("���ѽڵ㣺" + root.getNodeName());
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
	* @Description: ��α�����
	* @return void
	* @throws
	 */
	public void searchTree(TreeNode root) {
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		queue.offer(root);
		
		while(queue.size() != 0) {
			TreeNode node = queue.poll();
			if(null != node.getSplitAttributes()) {
				System.out.print("���ѽڵ㣺" + node.getNodeName() + "; "); 
				for(String attr : node.getSplitAttributes()) {
					System.out.print(" (" + attr + ") ");
				}
			} else {
				System.out.print("Ҷ�ӽڵ㣺" + node.getNodeName() + "; "); 
			}
			
			if(null != node.getChildrenNodes()) {
				for(TreeNode nod : node.getChildrenNodes()) {
					queue.offer(nod);
				}
			}
		}
	}
	
}
