package com.alimberdi.unitconverter.enums;

import java.math.BigDecimal;

public enum WeightUnit implements Unit {
	MG(0.000_001),
	G(0.001),
	KG(1.0),
	OZ(0.0_283_495),
	LB(0.453_592);

	private final BigDecimal factor;

	WeightUnit(double factor) {
		this.factor = BigDecimal.valueOf(factor);
	}

	@Override
	public BigDecimal getBaseFactor() {
		return factor;
	}
}
