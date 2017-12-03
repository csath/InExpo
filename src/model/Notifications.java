package model;


public class Notifications {

	 private int id;
	 private String message;
	 private int isChecked;
	 private int type;
	 private long time;
	 
	 public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(int isChecked) {
		this.isChecked = isChecked;
	}

	public Notifications() {
	    }
	 
	    public Notifications(int id, String message,int isChecked) {
	        this.id = id;
	        this.message = message;
	        this.isChecked = isChecked;
	    
	    }
	 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
