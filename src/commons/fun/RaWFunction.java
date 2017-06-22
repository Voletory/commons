package commons.fun;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * @author bailey
 * @version 1.0
 * @date 2017-03-27 10:30
 */
@FunctionalInterface
public interface RaWFunction {
	void apply(InputStream is, OutputStream out);
}
