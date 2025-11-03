package model;

public class Leave {
    private int id;
    private String empName;
    private String month;
    private String fromDate;
    private String toDate;
    private String reason;
    private String leaveType;
    private String status;
    private String note;

    public Leave() {}

    public Leave(int id, String empName, String month, String fromDate, String toDate,
                 String reason, String leaveType, String status, String note) {

        this.id = id;
        this.empName = empName;
        this.month = month;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reason = reason;
        this.leaveType = leaveType;
        this.status = status;
        this.note = note;
    }

    // Getters & Setters
    public int getId() { return id; }
    public String getEmpName() { return empName; }
    public String getMonth() { return month; }
    public String getFromDate() { return fromDate; }
    public String getToDate() { return toDate; }
    public String getReason() { return reason; }
    public String getLeaveType() { return leaveType; }
    public String getStatus() { return status; }
    public String getNote() { return note; }

    public void setId(int id) { this.id = id; }
    public void setEmpName(String empName) { this.empName = empName; }
    public void setMonth(String month) { this.month = month; }
    public void setFromDate(String fromDate) { this.fromDate = fromDate; }
    public void setToDate(String toDate) { this.toDate = toDate; }
    public void setReason(String reason) { this.reason = reason; }
    public void setLeaveType(String leaveType) { this.leaveType = leaveType; }
    public void setStatus(String status) { this.status = status; }
    public void setNote(String note) { this.note = note; }

	public Object getEmpEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getTaken() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getRemaining() {
		// TODO Auto-generated method stub
		return null;
	}
}
