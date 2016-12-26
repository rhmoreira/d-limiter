package br.com.rhm.dlimiter.scan;

public final class Configuration {

	private String delimiterToken;
	private boolean blankValueWhenNull;

	public String getDelimiterToken() {
		return delimiterToken;
	}

	public void setDelimiterToken(String delimiterToken) {
		this.delimiterToken = delimiterToken;
	}

	public boolean isBlankValueWhenNull() {
		return blankValueWhenNull;
	}

	public void setBlankValueWhenNull(boolean blankValueWhenNull) {
		this.blankValueWhenNull = blankValueWhenNull;
	}
}
