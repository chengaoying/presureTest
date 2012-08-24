package cn.ohyeah.service;

import java.io.IOException;

import cn.ohyeah.model.GameRecord;
import cn.ohyeah.protocol.Constant;

/**
 * 游戏记录服务类
 * @author maqian
 * @version 1.0
 */
public final class RecordService extends AbstractHttpService{
	public RecordService(String url) {
		super(url);
		System.out.println("url:"+url);
	}
	
	/**
	 * 保存游戏记录
	 * @param accountId
	 * @param productId
	 * @param record
	 * @throws ServiceException
	 */
	public void save(int accountId, int productId, GameRecord record) {
		try {
			initHead(Constant.PROTOCOL_TAG_RECORD, Constant.RECORD_CMD_SAVE);
			openBufferDataOutputStream();
			bufferDos.writeInt(headWrapper.getHead());
			bufferDos.writeInt(accountId);
			bufferDos.writeInt(productId);
			record.writeSaveRequestData(bufferDos);
			byte[] data = bufferBaos.toByteArray();
			closeBufferDataOutputStream();
			
			writeData(data);
			checkHead();
			readResult();
		} catch (IOException e) {
			throw new ServiceException(e.getMessage());
		}
		finally {
			close();
		}
	}
	
	
	
	/**
	 * 更新游戏记录
	 * @param accountId
	 * @param productId
	 * @param record
	 * @throws ServiceException 
	 */
	public void update(int accountId, int productId, GameRecord record) {
		try {
			initHead(Constant.PROTOCOL_TAG_RECORD, Constant.RECORD_CMD_UPDATE);
			openBufferDataOutputStream();
			bufferDos.writeInt(headWrapper.getHead());
			bufferDos.writeInt(accountId);
			bufferDos.writeInt(productId);
			record.writeUpdateRequestData(bufferDos);
			byte[] data = bufferBaos.toByteArray();
			closeBufferDataOutputStream();
			
			writeData(data);
			checkHead();
			readResult();
		} catch (IOException e) {
			throw new ServiceException(e.getMessage());
		}
		finally {
			close();
		}
	}

}
