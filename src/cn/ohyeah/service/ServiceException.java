package cn.ohyeah.service;

@SuppressWarnings("serial")
public class ServiceException extends RuntimeException {
	public ServiceException() {
	}

	public ServiceException(String s) {
		super(s);
	}
}
