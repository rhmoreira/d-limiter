package br.com.rhm.dlimiter.scan;

public final class Configuration {

	private String delimiterToken = ";";
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
}