package br.com.rhm.dlimiter.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.rhm.dlimiter.core.formatter.Formatter;
import br.com.rhm.dlimiter.core.parser.Parser;
import br.com.rhm.dlimiter.scan.DelimitedEntity;

abstract class AbstractDelimiter<T> implements Delimiter<T>{
	
	private static final byte[] LINE_BREAK = "\n".getBytes();
	private static final byte[] CARRIAGE_RETURN = "\r".getBytes();
	
	protected DelimitedEntity<T> entity;
	protected Parser<T> parser;
	protected Formatter<T> formatter;
	
	protected AbstractDelimiter(DelimitedEntity<T> entity) {
		super();
		this.parser = entity.getParser();
		this.formatter = entity.getFormatter();
	}
	
	@Override
	public List<T> parse(List<String> values) {
		return values.stream()
					 .map(s -> parse(s))
					 .collect(Collectors.toList());
	}

	@Override
	public List<T> parse(File file) throws IOException{
		try (InputStream in = new FileInputStream(file)){
			return parse(in);
		}
	}

	@Override
	public List<T> parse(InputStream in) throws IOException{
		List<T> entities = new ArrayList<>();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line = null;
		while ( (line = br.readLine()) != null){
			entities.add(parse(line));
		}
		
		return entities;
	}
	
	@Override
	public void format(List<T> list, File dest) throws IOException {
		format(list, dest, false);
	}
	
	@Override
	public void format(List<T> list, File dest, boolean append) throws IOException {
		try (OutputStream out = new FileOutputStream(dest, append)){
			format(list, out);
		}
	}
	
	@Override
	public void format(List<T> list, OutputStream out) throws IOException {
		for (T t: list){
			String format = format(t);
			out.write(format.getBytes());
			out.write(LINE_BREAK);
			out.write(CARRIAGE_RETURN);
			out.flush();
		}
	}
	
	@Override
	public void format(T t, OutputStream out) throws IOException {
		String formatted = format(t);
		out.write(formatted.getBytes());
	}

	@Override
	public void format(T t, File dest, boolean append) throws IOException {
		try (OutputStream out = new FileOutputStream(dest, append)){
			format(t, out);
		}
	}
	
	@Override
	public void format(T t, File dest) throws IOException {
		format(t, dest, false);
	}
}
