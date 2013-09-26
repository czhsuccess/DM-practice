package c4_5;

import java.util.ArrayList;
import java.util.List;

/**
 * C4.5���������ݽṹ
 * @author zhenhua.chen
 * @Description: TODO
 * @date 2013-3-1 ����10:47:37 
 *
 */
public class TreeNode {
	private String nodeName; // �������ڵ�����
	private List<String> splitAttributes; // ����������
	private ArrayList<TreeNode> childrenNodes; // ���������ӽڵ�
	private ArrayList<ArrayList<String>> dataSet; // ���ֵ��ýڵ�����ݼ� 
	private ArrayList<String> arrributeSet; // ���ݼ���������
	
	public TreeNode(){
		childrenNodes = new ArrayList<TreeNode>();
	}
	
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public List<String> getSplitAttributes() {
		return splitAttributes;
	}
	public void setSplitAttributes(List<String> splitAttributes) {
		this.splitAttributes = splitAttributes;
	}
	public ArrayList<TreeNode> getChildrenNodes() {
		return childrenNodes;
	}
	public void setChildrenNodes(ArrayList<TreeNode> childrenNodes) {
		this.childrenNodes = childrenNodes;
	}
	public ArrayList<ArrayList<String>> getDataSet() {
		return dataSet;
	}
	public void setDataSet(ArrayList<ArrayList<String>> dataSet) {
		this.dataSet = dataSet;
	}
	public ArrayList<String> getArrributeSet() {
		return arrributeSet;
	}
	public void setArrributeSet(ArrayList<String> arrributeSet) {
		this.arrributeSet = arrributeSet;
	}
}
