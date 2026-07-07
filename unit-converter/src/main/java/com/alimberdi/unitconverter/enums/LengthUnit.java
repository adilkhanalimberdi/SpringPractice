package com.alimberdi.unitconverter.enums;

import java.math.BigDecimal;

public enum LengthUnit implements Unit {
	MM(0.001),
	CM(0.01),
	M(1.0),
	KM(1_000.0),
	IN(0.0_254),
	FT(0.3_048),
	YD(0.9_144),
	MI(1_609.344);

	private final BigDecimal factor;

	LengthUnit(double factor) {
		this.factor = BigDecimal.valueOf(factor);
	}

	@Override
	public BigDecimal getBaseFactor() {
		return factor;
	}
}
