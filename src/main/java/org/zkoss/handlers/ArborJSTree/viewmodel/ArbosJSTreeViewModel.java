package org.zkoss.handlers.ArborJSTree.viewmodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.NotifyCommand;
import org.zkoss.bind.annotation.NotifyCommands;
import org.zkoss.bind.annotation.ToClientCommand;
import org.zkoss.handlers.ArborJSTree.model.ArborJSNode;
import org.zkoss.json.JSONObject;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.TreeNode;


@NotifyCommands({
    @NotifyCommand(value = "dharborjs$doClientAddChildNode", onChange = "_vm_.toAddChildNode"),
    @NotifyCommand(value = "dharborjs$doClientRemoveNode", onChange = "_vm_.toRemoveNodes"),
})
@ToClientCommand({"dharborjs$doClientAddChildNode", "dharborjs$doClientRemoveNode"})
public class ArbosJSTreeViewModel {
	
	/** ArborJS configuration properties */
	private JSONObject arborjsProperties = new JSONObject();
	
	/** Tree model based on default ZK inplementation   */
	private DefaultTreeNode<ArborJSNode> innerTreeModelRoot = new DefaultTreeNode<ArborJSNode>(new ArborJSNode("xroot"),new ArrayList<DefaultTreeNode<ArborJSNode>>());
	private DefaultTreeModel<ArborJSNode> innerTreeModel = new DefaultTreeModel<ArborJSNode>(innerTreeModelRoot);

	/** VM properties used to communicate with the client library   */
	private Map<String, ArborJSNode> toAddChildNode;
	private List<ArborJSNode> toRemoveNodes;
	
	/** VM properties bound to client controls, define new nodes properties, allow tree manipulation, only Add and remove currently  */
	private String addNodeLabel;
	private String addNodeColor;
	private ListModelList<String> availableShapes = new ListModelList<String>(new String[]{"box", "dot"});

	private ArborJSNodeUtil nodeUtil = new ArborJSNodeUtil();
	
	public ListModelList<String> getAvalaibleShapes() {
		return availableShapes;
	}

	public DefaultTreeModel<ArborJSNode> getInnerTreeModel() {
		return innerTreeModel;
	}

	public Map<String, ArborJSNode> getToAddChildNode() {
		return toAddChildNode;
	}

	public void setToAddChildNode(Map<String, ArborJSNode> toAddChildNode) {
		this.toAddChildNode = toAddChildNode;
	}
	
	public List<ArborJSNode> getToRemoveNodes() {
		return toRemoveNodes;
	}

	public void setToRemoveNodes(List<ArborJSNode> toRemoveNodes) {
		this.toRemoveNodes = toRemoveNodes;
	}

	public JSONObject getArborjsProperties() {
		return arborjsProperties;
	}
	
	public void setAddNodeLabel(String addNodeText) {
		this.addNodeLabel = addNodeText;
	}

	public void setAddNodeColor(String addNodeColor) {
		this.addNodeColor = addNodeColor;
	}

	/** initialize VM */
	@Init
	public void init() {
		/** initialize ArborsJS system properties */
		arborjsProperties.put("repulsion", "1,000");
		arborjsProperties.put("stiffness", "600");
		arborjsProperties.put("friction", "0.5");
		arborjsProperties.put("gravity", "center");
		arborjsProperties.put("fps", "55");
		arborjsProperties.put("dt", "0.02");
		arborjsProperties.put("precision", "0.6");
		
		/** initialize the ZK tree */
		DefaultTreeNode<ArborJSNode> visibleRoot = new DefaultTreeNode<ArborJSNode>(new ArborJSNode("0","ZK Tree root"),new ArrayList<DefaultTreeNode<ArborJSNode>>());
		innerTreeModelRoot.add(visibleRoot);
		innerTreeModel.addOpenObject(visibleRoot);
		
		/** initialize the controls used to add nodes */
		availableShapes.addToSelection(availableShapes.get(0));
		innerTreeModel.addToSelection(visibleRoot);
		
		/** forbid multiple selection on tree */
		innerTreeModel.setMultiple(false);
		
	}

	/** Command used by the add a node button */
	@Command
    @NotifyChange({"toAddChildNode","innerTreeModel"})
    public void addChildNode() {
		/** reset the data transfer property */
		setToAddChildNode(null);
		/** Obtain the currently selected node, if no selected node, throw warning message, multiple=false so selection max size should be 1 */
			if(innerTreeModel.getSelection().size()!=0){
	    	TreeNode<ArborJSNode> selectedParentNode = innerTreeModel.getSelection().iterator().next();
	    	/** create node data based on the user inputs */
	    	ArborJSNode myNewArborJSNode = new ArborJSNode(nodeUtil.getNewId(), 1, false, addNodeLabel, addNodeColor, availableShapes.getSelection().iterator().next());
	    	/** add an empty array as a children to avoid the tree node to be created as leaf node */
	    	TreeNode<ArborJSNode>[] emptyarray = null;
	    	/** use the data to create the tree node */
			DefaultTreeNode<ArborJSNode> myNewTreeNode = new DefaultTreeNode<ArborJSNode>(myNewArborJSNode,emptyarray);
			/** set the selected node as parent of the new node, tree model is automatically updated  */
			selectedParentNode.add(myNewTreeNode);
			/** set the new node as opened, to see new node appear without having to open them  */
			innerTreeModel.addOpenObject(myNewTreeNode);
			/** set the content of the data transfer property  */
			Map<String, ArborJSNode> childNodeData = new HashMap<String, ArborJSNode>();
			childNodeData.put("node", myNewArborJSNode);
			childNodeData.put("parent", selectedParentNode.getData());
			/** set the transfert property with the data */
			setToAddChildNode(childNodeData);
		}else{
    		Messagebox.show("Select a parent node");
    	}
    }
    
	/** Command used by the remove node button */
    @Command
    @NotifyChange({"toRemoveNodes","innerTreeModel"})
    public void removeNode() {
    	/** reset the data transfert property */
    	setToRemoveNodes(null);
    	/** if no selected tree node, throw a warning message */
    	if(innerTreeModel.getSelection().size()!=0){
	    	TreeNode<ArborJSNode> currentNode = innerTreeModel.getSelection().iterator().next();
	    	/** if current node is root, throw a warning message */
	    	if(currentNode.getData().getName()!="0"){
	    		/** create the list to hold the data of every node to remove */
		    	List<ArborJSNode>allNodesData= new ArrayList<ArborJSNode>();
		    	/** get a list of every child (recursive) of the current node*/
		    	List<TreeNode<ArborJSNode>>allTreeNodes=getAllChildrenNodes(currentNode,innerTreeModel);
		    	/** add the current node to the list of node to remove*/
		    	allTreeNodes.add(currentNode);
		    	/** loop on nodes to remove to add their ArborJsNode*/
		    	Iterator<TreeNode<ArborJSNode>> nodeIterator = allTreeNodes.iterator();
		    	while (nodeIterator.hasNext()) {
		    		TreeNode<ArborJSNode> thisNode = nodeIterator.next();
					allNodesData.add(thisNode.getData());
				}
		    	/** set the data (the list of ArborJSNodes to remove) as the data transfer property */
				setToRemoveNodes(allNodesData);
				/** remove the nodes from the TreeModel */
		    	currentNode.getParent().remove(currentNode);
	    	}else{
	    		Messagebox.show("Cannot delete root node");
	    	}
    	}else{
    		Messagebox.show("Select a branch to delete");
    	}
    }
    
    /** Recursively get all children from a treeNode, assuming 1-n parent-child relationships */ 
    /** @param <E>*/
    private <E> List<TreeNode<E>> getAllChildrenNodes(TreeNode<E> target, TreeModel<TreeNode<E>> treeModel){
    	/** create a new list to hold all children */ 
    	List<TreeNode<E>> allChildrenNodes = new ArrayList<TreeNode<E>>();
    	/** assume non-leaf nodes with no child, consider final branch node if having no child */ 
    	if (treeModel.getChildCount(target) != 0){
    		/** if have child, get all child node recursively  */ 
	    	for (int i = 0; i < treeModel.getChildCount(target); i++) {
	    		TreeNode<E> thisChild = treeModel.getChild(target, i);
	    		allChildrenNodes.add(thisChild);
	    		allChildrenNodes.addAll(getAllChildrenNodes(thisChild,treeModel));
			}
    	}
    	/** return the full collection*/ 
    	return allChildrenNodes;
    }
}
