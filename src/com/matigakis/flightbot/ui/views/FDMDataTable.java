package com.matigakis.flightbot.ui.views;

import javax.swing.JTable;

public class FDMDataTable extends JTable{
	private static final long serialVersionUID = 1L;
	
	private FDMDataTableModel fdmDataTableModel;
	
	public FDMDataTable(FDMDataTableModel fdmDataTableModel){
		super(fdmDataTableModel);
		
		this.fdmDataTableModel = fdmDataTableModel;
	}
	
	public FDMDataTableModel getDataTable(){
		return fdmDataTableModel;
	}
}
