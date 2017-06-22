package commons.packet;

/**
 * 
 * @author bailey_fu
 * @data 2011-11-16
 * @version 1.0
 * @Description 验证包头长度
 */
public abstract class AbstractPacket implements Packet {

	protected byte[] data;

	public int packetLength() {
		return data.length;
	}

	public boolean validate() {
		if (data != null) {
			return data.length > headLength() && headLength() == bodyOffset();
		}
		return false;
	}

}
