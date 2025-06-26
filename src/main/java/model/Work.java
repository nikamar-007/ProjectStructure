package model;

public class Work {
	private int idWork;
	private int idResponsible;
	private String title;
	private int laborIntensity;
	private Double fixedPayment;
	private int recommendedEmployees;
	private String description;
	
	public Work(int idResponsible, String title, int laborIntensity, Double fixedPayment, int recommendedEmployees, String description) {
		this.idResponsible = idResponsible;
		this.title = title;
		this.laborIntensity = laborIntensity;
		this.fixedPayment = fixedPayment;
		this.recommendedEmployees = recommendedEmployees;
		this.description = description;
	}
	
	public int getIdWork() {return idWork;}
	public void setIdWork(int idWork) {this.idWork = idWork;}
	public int getIdResponsible() {return idResponsible;}
	public void setIdResponsible(int idResponsible) {this.idResponsible = idResponsible;}
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}
	public int getLaborIntensity() {return laborIntensity;}
	public void setLaborIntensity(int laborIntensity) {this.laborIntensity = laborIntensity;}
	public Double getFixedPayment() {return fixedPayment;}
	public void setFixedPayment(Double fixedPayment) {this.fixedPayment = fixedPayment;}
	public int getRecommendedEmployees() {return recommendedEmployees;}
	public void setRecommendedEmployees(int recommendedEmployees) {this.recommendedEmployees = recommendedEmployees;}
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}
	
	@Override
	public String toString() {
		return title;
	}
}