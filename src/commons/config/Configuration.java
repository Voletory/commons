package commons.config;

/**
 * 配置接口
 * 
 * @author bailey.fu
 * @date Oct 19, 2009
 * @version 1.0
 * @description 装载并解析配置文件获得参数
 */
public interface Configuration {

	/** 根据文件配置得到相应参数 */
	public void configure() throws ConfigurationException;

}
