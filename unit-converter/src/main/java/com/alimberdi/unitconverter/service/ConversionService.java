package com.alimberdi.unitconverter.service;

import com.alimberdi.unitconverter.dto.ConvertRequest;
import com.alimberdi.unitconverter.enums.Unit;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class ConversionService {

	private static final int MAX_SCALE = 8;

	public BigDecimal convert(ConvertRequest request) {
		if (request.from().getClass() != request.to().getClass()) {
			throw new IllegalArgumentException("You cannot convert "
				+ request.from().getClass().getSimpleName()
				+ " to "
				+ request.to().getClass().getSimpleName());
		}

		BigDecimal value = request.value();
		Unit from = request.from();
		Unit to = request.to();

		BigDecimal valueInBaseUnit = value.multiply(from.getBaseFactor(), MathContext.DECIMAL128);

		return valueInBaseUnit
				.divide(to.getBaseFactor(), MAX_SCALE, RoundingMode.HALF_EVEN)
				.stripTrailingZeros();
	}
}
