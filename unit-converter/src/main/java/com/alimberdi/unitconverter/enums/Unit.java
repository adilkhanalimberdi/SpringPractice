package com.alimberdi.unitconverter.enums;

import com.alimberdi.unitconverter.exception.UnitParseFailedException;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public interface Unit {

	BigDecimal getBaseFactor();

	class Registry {
		private static final Map<String, Unit> ALL_UNITS = new HashMap<>();

		static {
			register(LengthUnit.values());
			register(WeightUnit.values());
			register(TemperatureUnit.values());
			register(AreaUnit.values());
			register(VolumeUnit.values());
			register(TimeUnit.values());
		}

		private static void register(Unit[] units) {
			for (Unit unit : units) {
				if (unit instanceof Enum<?> anEnum) {
					ALL_UNITS.put(anEnum.name().toUpperCase(), unit);
				}
			}
		}
	}

	@JsonCreator
	static Unit parse(String unitStr) {
		if (unitStr == null) return null;

		Unit unit = Registry.ALL_UNITS.get(unitStr.toUpperCase().trim());
		if (unit == null) {
			throw new UnitParseFailedException("Unit " + unitStr + " not found");
		}

		return unit;
	}

}
