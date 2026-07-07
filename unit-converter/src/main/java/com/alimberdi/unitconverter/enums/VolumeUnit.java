package com.alimberdi.unitconverter.enums;

import java.math.BigDecimal;

public enum VolumeUnit implements Unit {
	L(0.001),
	ML(0.000_001),
	MM3(0.000_000_001),
	CM3(0.000_001),
	M3(1.0),
	KM3(1_000_000_000),
	GAL(0.00_378_541),
	QT(0.0_009_463_525),
	PT(0.0_004_731_763),
	FLOZ(0.0_000_295_735),
	TBSP(0.0_000_147_868),
	TSP(0.0_000_049_289);

	private final BigDecimal factor;

	VolumeUnit(double factor) {
		this.factor = BigDecimal.valueOf(factor);
	}

	@Override
	public BigDecimal getBaseFactor() {
		return factor;
	}
}
