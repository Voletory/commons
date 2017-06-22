package commons.packet;

/**
 * 请求包
 * 
 * @author bailey_fu
 * @data 2011-11-16
 * @version 1.0
 * @Description
 */
public abstract class RequestPacket extends AbstractPacket {

	/**
	 * 实体解析成数据包
	 * 
	 * @return
	 */
	abstract public byte[] entity2Bytes() throws Exception;

}
