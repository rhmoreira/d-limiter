package br.com.rhm.dlimiter.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import br.com.rhm.dlimiter.DelimiterException;

public interface Delimiter<T> {

	T parse(String value) throws DelimiterException;
	List<T> parse(List<String> value) throws DelimiterException;
	List<T> parse(File file) throws IOException;
	List<T> parse(InputStream in) throws IOException;
		
	String format(T t) throws DelimiterException;
	void format(T t, OutputStream out) throws IOException;
	void format(T t, File dest) throws IOException;
	void format(T t, File dest, boolean append) throws IOException;
	void format(List<T> list, File dest) throws IOException;
	void format(List<T> list, File dest, boolean append) throws IOException;
	void format(List<T> list, OutputStream out) throws IOException;
	
}
