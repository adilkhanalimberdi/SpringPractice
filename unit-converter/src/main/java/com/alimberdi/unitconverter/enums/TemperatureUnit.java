package com.alimberdi.unitconverter.enums;

import java.math.BigDecimal;

public enum TemperatureUnit implements Unit {
	C(1.0),
	F(-17.222_222_222),
	K(-272.15);

	private final BigDecimal factor;

	TemperatureUnit(double factor) {
		this.factor = BigDecimal.valueOf(factor);
	}

	@Override
	public BigDecimal getBaseFactor() {
		return factor;
	}
}
