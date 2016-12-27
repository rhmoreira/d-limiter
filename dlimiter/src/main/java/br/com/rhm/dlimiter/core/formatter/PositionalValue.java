package br.com.rhm.dlimiter.core.formatter;

import br.com.rhm.dlimiter.DUtil;
import br.com.rhm.dlimiter.annotation.Orientation;
import br.com.rhm.dlimiter.annotation.OrientationPadding;
import br.com.rhm.dlimiter.annotation.Position;

class PositionalValue {

	private Position position;
	private String value;
	
	public PositionalValue(Position position, String value) {
		super();
		this.position = position;
		this.value = value;
	}

	public int getStart(){
		return position.start();
	}
	
	public int getEnd(){
		return position.end();
	}

	public String getValue() {
		if (value == null)
			value = "";
		
		value = value.trim();
		
		int maxLength = getEnd() - (getStart() -1);
		
		return getFormattedValue(value, maxLength);
	}
	
	private String getFormattedValue(String value, int maxLength){
		Orientation orientation = position.orientation();
		
		OrientationPadding orientationPadding = orientation.padding();
		String charFill = orientation.fill();
		
		switch (orientationPadding) {
		case LEFT:
			value = DUtil.padLeft(value, charFill, maxLength);
			break;
		case RIGHT:
			value = DUtil.padRight(value, charFill, maxLength);
			break;
		}
		
		return value;
	}
	
}