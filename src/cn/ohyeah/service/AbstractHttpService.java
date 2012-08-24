package cn.ohyeah.service;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.ohyeah.protocol.Constant;
import cn.ohyeah.protocol.HeadWrapper;


/**
 * ����Http������
 * @author maqian
 * @version 1.0
 */
public abstract class AbstractHttpService {
	protected String serviceLocation;
	protected ByteArrayOutputStream bufferBaos;
	protected DataOutputStream bufferDos;
	protected DataOutputStream connectionDos;
	protected DataInputStream connectionDis;
	protected HttpURLConnection httpConnection;
	protected HeadWrapper headWrapper;
	protected int result;
	protected String message;
	
	protected AbstractHttpService(String url) {
		serviceLocation = url;
	}
	
	/**
	 * ���ط�����
	 * @return ����ֵ<0��ʧ�ܣ�����ֵ==0���ɹ�
	 */
	public int getResult() {
		return result;
	}
	
	/**
	 * �жϷ����Ƿ�ɹ�
	 * @return
	 */
	public boolean isSuccess() {
		return result==0;
	}
	
	/**
	 * ���ش�����Ϣ
	 * @return
	 */
	public String getMessage() {
		return message;
	}
	
	protected void closeConnectionDataOutputStream() {
		if (connectionDos != null) {
			try {
				connectionDos.flush();
				connectionDos.close();
				connectionDos = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void closeConnectionDataInputStream() {
		if (connectionDis != null) {
			try {
				connectionDis.close();
				connectionDis = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void closeHttpConnection() {
		if (httpConnection != null) {
			httpConnection.disconnect();
			httpConnection = null;
		}
	}
	
	protected void close() {
		closeBufferDataOutputStream();
		closeConnectionDataOutputStream();
		closeConnectionDataInputStream();
		closeHttpConnection();
	}
	
	protected void initHead(int tag, int cmd) {
		this.headWrapper = new HeadWrapper.Builder()
			.version(Constant.PROTOCOL_VERSION).tag(tag).command(cmd).build();;
	}
	
	protected int readResult() throws IOException {
		result = connectionDis.readInt();
		if (result < 0) {
			message = connectionDis.readUTF();
		}
		return result;
	}
	
	protected void checkHead() throws IOException {
		int rc = httpConnection.getResponseCode();
		if (rc != HttpURLConnection.HTTP_OK) {
	        throw new ServiceException("Http Response Code: " + rc);
	    }
	    openConnectionDataInputStream();
	    HeadWrapper rspHeadWrapper = new HeadWrapper();
	    rspHeadWrapper.setHead(connectionDis.readInt());
	    if (rspHeadWrapper.getVersion() != headWrapper.getVersion()) {
	    	throw new ServiceException("Э��汾��һ��");
	    }
	    if (rspHeadWrapper.getTag() != headWrapper.getTag()) {
	    	throw new ServiceException("Э���ʶ��һ��");
	    }
	    if (rspHeadWrapper.getCommand() != headWrapper.getCommand()) {
	    	throw new ServiceException("Э�����һ��");
	    }
	}
	
	protected DataOutputStream openConnectionDataOutputStream() {
		if (connectionDos != null) {
			return connectionDos;
		}
		try {
			System.out.println(httpConnection.getOutputStream());
			connectionDos = new DataOutputStream(httpConnection.getOutputStream());
			return connectionDos;
		} catch (IOException e) {
			throw new ServiceException("���ӷ�����ʧ��");
		}
	}
	
	protected DataInputStream openConnectionDataInputStream() {
		if (connectionDis != null) {
			return connectionDis;
		}
		try {
			connectionDis = new DataInputStream(httpConnection.getInputStream());
			return connectionDis;
		} catch (IOException e) {
			throw new ServiceException("���ӷ�����ʧ��");
		}
	}
	
	protected void openConnection() {
		try {
			httpConnection = null;
			connectionDis = null;
			connectionDos = null;
			result = -1;
			message = null;
			
			httpConnection = (HttpURLConnection)new URL(serviceLocation).openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/octet-stream");
			httpConnection.setRequestProperty("Connection", "close");
			
		} catch (IOException e) {
			throw new ServiceException("���ӷ�����ʧ��");
		}
	}
	
	protected void writeData(byte[] data) throws IOException {
		openConnection();
		setContentLength(data.length);
		openConnectionDataOutputStream();
		connectionDos.write(data, 0, data.length);
		closeConnectionDataOutputStream();
	}
	
	protected void setContentLength(int len) {
		try {
			httpConnection.setRequestProperty("Content-Length", Integer.toString(len));
		} catch (Exception e) {
			throw new ServiceException("���ӷ�����ʧ��");
		}
	}
	
	protected void openBufferDataOutputStream() {
		bufferBaos = new ByteArrayOutputStream();
		bufferDos = new DataOutputStream(bufferBaos);
	}
	
	protected void closeBufferDataOutputStream() {
		if (bufferDos != null) {
			try {
				bufferDos.close();
				bufferDos = null;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (bufferBaos != null) {
			try {
				bufferBaos.close();
				bufferBaos = null;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
