package com.trustly.challenge.controller.dto;

public class InfoDto {
	
	String fileExtension;
	
	int lines;
	
	double bytes;



	public InfoDto(String fileExtension, int lines, double bytes) {
		this.fileExtension = fileExtension;
		this.lines = lines;
		this.bytes = bytes;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
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

	public void setBytes(int bytes) {
		this.bytes = bytes;
	}
	
	

}
