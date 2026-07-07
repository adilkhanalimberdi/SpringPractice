package com.alimberdi.unitconverter.enums;

import java.math.BigDecimal;

public enum AreaUnit implements Unit {
	MM2(0.000_001),
	CM2(0.000_1),
	M2(1.0),
	KM2(1_000_000.0),
	IN2(0.00_064_516),
	YD2(0.83_612_736),
	FT2(0.09_290_304),
	AC(4_046.8_564_224);

	private final BigDecimal factor;

	AreaUnit(double factor) {
		this.factor = BigDecimal.valueOf(factor);
	}

	@Override
	public BigDecimal getBaseFactor() {
		return factor;
	}
}
