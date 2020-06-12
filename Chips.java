package com.tomb.CardTable;

public enum Chips {

	WHITE(1.00), PINK(2.50), RED(5.00), GREEN(25.00), BLUE(50.00), BLACK(100.00);
	
	private double chip_value;
	
	private Chips(double chip_value) {
		this.chip_value = chip_value;
	}
	
	public double getChipValue() {
		return chip_value;
	}
}
