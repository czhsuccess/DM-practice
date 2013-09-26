package c4_5;

import java.util.ArrayList;
import java.util.List;

/**
 * C4.5决策树数据结构
 * @author zhenhua.chen
 * @Description: TODO
 * @date 2013-3-1 上午10:47:37 
 *
 */
public class TreeNode {
	private String nodeName; // 决策树节点名称
	private List<String> splitAttributes; // 分裂属性名
	private ArrayList<TreeNode> childrenNodes; // 决策树的子节点
	private ArrayList<ArrayList<String>> dataSet; // 划分到该节点的数据集 
	private ArrayList<String> arrributeSet; // 数据集所有属性
	
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
