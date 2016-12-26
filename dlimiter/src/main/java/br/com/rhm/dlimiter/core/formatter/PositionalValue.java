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
		
		return getFormattedValue(value);
	}
	
	private String getFormattedValue(String value){
		Orientation orientation = position.orientation();
		
		OrientationPadding orientationPadding = orientation.padding();
		String charFill = orientation.fill();
		
		int length = getEnd() - (getStart() -1);
		
		switch (orientationPadding) {
		case LEFT:
			value = DUtil.padLeft(value, charFill, length);
			break;
		case RIGHT:
			value = DUtil.padRight(value, charFill, length);
			break;
		}
		
		return value;
	}
	
}