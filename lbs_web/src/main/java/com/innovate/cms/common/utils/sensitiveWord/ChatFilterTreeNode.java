package com.innovate.cms.common.utils.sensitiveWord;

import java.util.HashMap;

public class ChatFilterTreeNode {
	private boolean isEnd = true;
	private HashMap<String, ChatFilterTreeNode> nextNodeMap = null;
	private boolean isOverLapEnd = false;

	/**
	 * Lazy Getter and Setter
	 * 
	 * @return
	 */

	public HashMap<String, ChatFilterTreeNode> getNextNodeMap() {
		if (nextNodeMap == null) {
			nextNodeMap = new HashMap<String, ChatFilterTreeNode>();
		}
		return nextNodeMap;
	}

	public void setNextNodeMap(HashMap<String, ChatFilterTreeNode> nextNodeMap) {
		this.nextNodeMap = nextNodeMap;
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public boolean isOverLapEnd() {
		return isOverLapEnd;
	}

	public void setOverLapEnd(boolean isOverLapEnd) {
		this.isOverLapEnd = isOverLapEnd;
	}
}
