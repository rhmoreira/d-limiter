package br.com.rhm.dlimiter.scan;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.rhm.dlimiter.annotation.Index;
import br.com.rhm.dlimiter.core.formatter.Formatter;
import br.com.rhm.dlimiter.core.formatter.TokenFormatter;
import br.com.rhm.dlimiter.core.parser.Parser;
import br.com.rhm.dlimiter.core.parser.TokenParser;
import br.com.rhm.dlimiter.reflection.ClassHandler;

public class TokenDelimitedEntity<T> extends AbstractDelimitedEntity<T> implements DelimitedEntityHandler<T>, DelimitedEntity<T> {

	private Map<Integer, Field> indexedFieldMap;
	private Parser<T> tokenParser;
	private Formatter<T> tokenFormatter;
	
	public TokenDelimitedEntity(Class<T> clazz, ClassHandler classHandler, Configuration conf) {
		super(clazz, classHandler, conf);
		this.tokenParser = new TokenParser<T>(this);
		this.tokenFormatter = new TokenFormatter<T>(this);
		indexFields();
	}

	private void indexFields() {
		Collection<Field> scannedFields = classHandler.getScannedFields();
		indexedFieldMap = scannedFields
			.stream()
			.collect(Collectors.toMap(f -> f.getAnnotation(Index.class).value(), f ->  f));
	}

	public Field getFieldForIndex(int index){
		return indexedFieldMap.get(index);
	}
	
	public Set<Integer> getSortedIndexSet(){
		return indexedFieldMap.keySet().stream().sorted().collect(Collectors.toSet());
	}

	@Override
	public Parser<T> getParser() {
		return tokenParser;
	}

	@Override
	public Formatter<T> getFormatter() {
		return tokenFormatter;
	}
	
}
