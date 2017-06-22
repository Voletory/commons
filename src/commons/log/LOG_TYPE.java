package commons.log;

public enum LOG_TYPE {

	CONSOLE ( 99 , "CONSOLE" , "console" ) ,
	X_COMMON ( 0 , "X_COMMON" , "x_commons" );

	public int ID;
	public String NAME;
	public String LOGGER_NAME;

	private LOG_TYPE(int ID, String NAME, String LOGGER_NAME) {
		this.ID = ID;
		this.NAME = NAME;
		this.LOGGER_NAME = LOGGER_NAME;
	}
}