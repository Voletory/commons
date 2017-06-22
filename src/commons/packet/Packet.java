package commons.packet;

/**
 * 数据包
 * 
 * @author bailey.fu
 * @date Nov 26, 2009
 * @version 1.0
 * @description 包头和包体
 */
public interface Packet {

	/** 包长 */
	public int packetLength();

	/** 包头长 */
	public int headLength();

	/** 包体起始位置 */
	public int bodyOffset();

	/** 验证包数据 */
	public boolean validate();
}
