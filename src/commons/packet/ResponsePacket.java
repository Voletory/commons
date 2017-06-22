package commons.packet;

/**
 * 回执包
 * 
 * @author bailey_fu
 * @data 2011-11-16
 * @version 1.0
 * @Description
 */
public abstract class ResponsePacket extends AbstractPacket {

	/**
	 * 数据包解析到实体
	 * 
	 * @param data
	 * @throws Exception
	 */
	abstract void bytes2Entity(byte[] data) throws Exception;
}
