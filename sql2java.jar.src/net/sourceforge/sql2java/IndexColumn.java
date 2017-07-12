/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/

package net.sourceforge.sql2java;

public class IndexColumn extends Column {

	private String sortSequence;
	private String filterCondition;

	public String getSortSequence() {
		return sortSequence;
	}

	public void setSortSequence(String sortSequence) {
		this.sortSequence = sortSequence;
	}

	public String getFilterCondition() {
		return filterCondition;
	}

	public void setFilterCondition(String condition) {
		filterCondition = condition;
	}
}