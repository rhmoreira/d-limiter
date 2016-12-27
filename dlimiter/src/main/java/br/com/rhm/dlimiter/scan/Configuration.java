package br.com.rhm.dlimiter.scan;

public final class Configuration {

	private String delimiterToken = ";";
	private String encoding = "UTF-8";
	private int skipFirstLines;

	public String getDelimiterToken() {
		return delimiterToken;
	}

	public void setDelimiterToken(String delimiterToken) {
		this.delimiterToken = delimiterToken;
	}

	public int getSkipFirstLines() {
		return skipFirstLines;
	}

	public void setSkipFirstLines(int skipFirstLines) {
		this.skipFirstLines = skipFirstLines;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
}