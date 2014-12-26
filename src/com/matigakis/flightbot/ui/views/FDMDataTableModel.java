package com.matigakis.flightbot.ui.views;

import javax.swing.table.AbstractTableModel;

import com.matigakis.fgcontrol.fdm.FDMData;

public class FDMDataTableModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	private Object[][] fdmTableData;
	private String[] columns = {"Valiable", "value"};
	
	public FDMDataTableModel(){
		FDMData fdmData = new FDMData();
		
		fdmTableData = new Object[][]{
				{"X axis accelaration", fdmData.getAccelerations().getXAcceleration()},
				{"Y axis accelaration", fdmData.getAccelerations().getYAcceleration()},
				{"Z axis accelaration", fdmData.getAccelerations().getZAcceleration()},
				{"Airspeed", fdmData.getVelocities().getCalibratedAirspeed()},
				{"Climb rate", fdmData.getVelocities().getClimbRate()},
				{"u", fdmData.getVelocities().getU()},
				{"v", fdmData.getVelocities().getV()},
				{"w", fdmData.getVelocities().getW()},
				{"Roll rate", fdmData.getVelocities().getRollRate()},
				{"Pitch rate", fdmData.getVelocities().getPitchRate()},
				{"Yaw rate", fdmData.getVelocities().getYawRate()},
				{"North velocity", fdmData.getVelocities().getNorthVelocity()},
				{"Eastvelocity", fdmData.getVelocities().getEastVelocity()},
				{"Vertical velocity", fdmData.getVelocities().getVerticalVelocity()},
				{"Angle of attack", fdmData.getOrientation().getAngleOfAttack()},
				{"Sideslip angle", fdmData.getOrientation().getSideSlipAngle()},
				{"Roll", fdmData.getOrientation().getRoll()},
				{"Pitch", fdmData.getOrientation().getPitch()},
				{"Heading", fdmData.getOrientation().getHeading()},
				{"Altitude", fdmData.getPosition().getAltitude()},
				{"Altitude above ground", fdmData.getPosition().getAGL()},
				{"Latitude", fdmData.getPosition().getLatitude()},
				{"Longitude", fdmData.getPosition().getLongitude()},
				{"Static pressure", fdmData.getAtmosphere().getStaticPressure()},
				{"Total pressure", fdmData.getAtmosphere().getTotalPressure()},
				{"Temperature", fdmData.getAtmosphere().getTemperature()},
				{"Aileron", fdmData.getControls().getAileron()},
				{"Elevator", fdmData.getControls().getElevator()},
				{"Rudder", fdmData.getControls().getRudder()},
				{"Throttle", fdmData.getControls().getThrottle()}};
	}
	
	@Override
	public int getColumnCount() {
		return fdmTableData[0].length;
	}

	@Override
	public int getRowCount() {
		return fdmTableData.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return fdmTableData[rowIndex][columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	@Override
	public String getColumnName(int column) {
		return columns[column];
	}
	
	public void updateFromFDMData(FDMData fdmData){
		fdmTableData[0][1] = fdmData.getAccelerations().getXAcceleration();
		fdmTableData[1][1] = fdmData.getAccelerations().getYAcceleration();
		fdmTableData[2][1] = fdmData.getAccelerations().getZAcceleration();
		fdmTableData[3][1] = fdmData.getVelocities().getCalibratedAirspeed();
		fdmTableData[4][1] = fdmData.getVelocities().getClimbRate();
		fdmTableData[5][1] = fdmData.getVelocities().getU();
		fdmTableData[6][1] = fdmData.getVelocities().getV();
		fdmTableData[7][1] = fdmData.getVelocities().getW();
		fdmTableData[8][1] = fdmData.getVelocities().getRollRate();
		fdmTableData[9][1] = fdmData.getVelocities().getPitchRate();
		fdmTableData[10][1] = fdmData.getVelocities().getYawRate();
		fdmTableData[11][1] = fdmData.getVelocities().getNorthVelocity();
		fdmTableData[12][1] = fdmData.getVelocities().getEastVelocity();
		fdmTableData[13][1] = fdmData.getVelocities().getVerticalVelocity();
		fdmTableData[14][1] = fdmData.getOrientation().getAngleOfAttack();
		fdmTableData[15][1] = fdmData.getOrientation().getSideSlipAngle();
		fdmTableData[16][1] = fdmData.getOrientation().getRoll();
		fdmTableData[17][1] = fdmData.getOrientation().getPitch();
		fdmTableData[18][1] = fdmData.getOrientation().getHeading();
		fdmTableData[19][1] = fdmData.getPosition().getAltitude();
		fdmTableData[20][1] = fdmData.getPosition().getAGL();
		fdmTableData[21][1] = fdmData.getPosition().getLatitude();
		fdmTableData[22][1] = fdmData.getPosition().getLongitude();
		fdmTableData[23][1] = fdmData.getAtmosphere().getStaticPressure();
		fdmTableData[24][1] = fdmData.getAtmosphere().getTotalPressure();
		fdmTableData[25][1] = fdmData.getAtmosphere().getTemperature();
		fdmTableData[26][1] = fdmData.getControls().getAileron();
		fdmTableData[27][1] = fdmData.getControls().getElevator();
		fdmTableData[28][1] = fdmData.getControls().getRudder();
		fdmTableData[29][1] = fdmData.getControls().getThrottle();
		
		fireTableDataChanged();
	}
}
