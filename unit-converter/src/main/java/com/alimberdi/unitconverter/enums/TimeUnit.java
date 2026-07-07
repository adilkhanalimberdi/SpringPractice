package com.alimberdi.unitconverter.enums;

import java.math.BigDecimal;

public enum TimeUnit implements Unit {
	MS(0.001),
	S(1.0),
	MIN(60.0),
	H(3_600.0),
	D(86_400.0),
	W(604_800.0),
	MO(2_629_800.0),
	Y(31_557_600.0);

	private final BigDecimal factor;

	TimeUnit(double factor) {
		this.factor = BigDecimal.valueOf(factor);
	}

	@Override
	public BigDecimal getBaseFactor() {
		return factor;
	}
}
