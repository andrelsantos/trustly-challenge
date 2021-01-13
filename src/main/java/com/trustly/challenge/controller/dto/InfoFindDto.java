package com.trustly.challenge.controller.dto;

public class InfoFindDto {
	
	int lines;
	
	double bytes;

	public InfoFindDto(int lines, double bytes) {
		super();
		this.lines = lines;
		this.bytes = bytes;
	}

	public int getLines() {
		return lines;
	}

	public void setLines(int lines) {
		this.lines = lines;
	}

	public double getBytes() {
		return bytes;
	}

	public void setBytes(double bytes) {
		this.bytes = bytes;
	}

	
}
