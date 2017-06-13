package br.com.rhm.dlimiter.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.rhm.dlimiter.core.formatter.Formatter;
import br.com.rhm.dlimiter.core.parser.Parser;
import br.com.rhm.dlimiter.scan.Configuration;
import br.com.rhm.dlimiter.scan.DelimitedEntity;

class DelimiterImpl<T> implements Delimiter<T>{
	
	private Configuration conf;
	private Parser<T> parser;
	private Formatter<T> formatter;
	private boolean hasHeader;
	
	public DelimiterImpl(DelimitedEntity<T> entity) {
		super();
		this.conf = entity.getConfiguration();
		this.parser = entity.getParser();
		this.formatter = entity.getFormatter();
	}
	
	@Override
	public T parse(String value) {
		return parser.parse(value);
	}

	@Override
	public String format(T t) {
		String format = formatter.format(t);
		if (!hasHeader){
			String header = formatter.formatHeader();
			format = header + format;
		}
		return format;
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
		int skipedLines = 0;
		while ( (line = br.readLine()) != null){
			if (skipedLines++ >= conf.getSkipFirstLines())
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
		BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(out, conf.getEncoding()));
		for (T t: list){
			String format = format(t);
			hasHeader = true;
			write(format, bfw);
		}
		hasHeader = false;
	}
	
	private void write(String value, BufferedWriter bfw) throws IOException{
		bfw.write(value);
		bfw.write(CR_LF);
		bfw.flush();
	}
	
	@Override
	public void format(T t, OutputStream out) throws IOException {
		BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(out, conf.getEncoding()));
		String formatted = format(t);
		write(formatted, bfw);
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
