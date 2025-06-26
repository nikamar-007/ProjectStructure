package model;
import java.time.LocalDate;

public class EmployeeWork {
	private int idEmployee;
	private int idWork;
	private String urgency;
	private LocalDate startDate;
	private LocalDate endDate;
	private double additionalPayment;
	
	public EmployeeWork(int idEmployee, int idWork, String urgency, LocalDate startDate, LocalDate endDate, double additionalPayment) {
		this.idEmployee = idEmployee;
		this.idWork = idWork;
		this.urgency = urgency;
		this.startDate = startDate;
		this.endDate = endDate;
		this.additionalPayment = additionalPayment;
	}
	
	public int getIdEmployee() {return idEmployee;}
	public int getIdWork() {return idWork;}
	public String getUrgency() {return urgency;}
	public void setUrgency(String urgency) {this.urgency = urgency;}
	public LocalDate getStartDate() {return startDate;}
	public LocalDate getEndDate() {return endDate;}
	public void setEndDate(LocalDate endDate) {this.endDate = endDate;}
	public double getAdditionalPayment() {return additionalPayment;}
	public void setAdditionalPayment(double additionalPayment) {this.additionalPayment = additionalPayment;}
}