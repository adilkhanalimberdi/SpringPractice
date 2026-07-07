package com.alimberdi.unitconverter.converter;

import com.alimberdi.unitconverter.enums.Unit;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToUnitConverter implements Converter<String, Unit> {

	@Override
	public Unit convert(String source) {
		return Unit.parse(source);
	}

}
