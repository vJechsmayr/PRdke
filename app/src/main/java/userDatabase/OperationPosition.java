package userDatabase;

public class OperationPosition {

	// Nearly every button click -> check csv
	// if operation with current object there show status
	// else send systemmessage
	// after every step send systemmessage to next role
	// if last position and user clicks again first button -> do it!
	// test
	private String name;
	private int currentPosition;
	private String parameter;
	private String rule;
	private String context;

	public OperationPosition() {
		this.name = "";
		this.currentPosition = 0;
		this.parameter = "";
		this.rule = "";
		this.context = "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

}
