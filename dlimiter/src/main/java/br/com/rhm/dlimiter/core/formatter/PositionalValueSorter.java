package br.com.rhm.dlimiter.core.formatter;

import java.util.ArrayList;
import java.util.List;

class PositionalValueSorter {

	private List<PositionalValue> values;
	
	public void addValue(PositionalValue value){
		if (values == null)
			values = new ArrayList<>();
		
		values.add(value);
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		values.forEach(v -> sb.append(v.getValue()));
		return sb.toString();
	}

	public void sort() {
		values.sort( (v1, v2) -> {
			if (v1.getStart() > v2.getStart())
				return 1;
			else if (v1.getStart() == v2.getStart())
				return 0;
			else
				return -1;
		});
	}
}
