package br.com.rhm.dlimiter.core.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.rhm.dlimiter.DelimiterException;

class DateConverter implements Converter<Date> {

	private static final DateFormat sdf = new SimpleDateFormat("ddMMyyyy");
	
	@Override
	public Date from(String value) {
		try {
			return sdf.parse(value);
		} catch (ParseException e) {
			throw new DelimiterException("Wrong string date format [" + value + "]", e);
		}
	}

	@Override
	public String to(Date date) {
		return sdf.format(date);
	}

}
