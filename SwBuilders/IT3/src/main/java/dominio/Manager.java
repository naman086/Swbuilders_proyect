package dominio;

import persistencia.GeneralDao;
import persistencia.OwnerDao;
import persistencia.VehicleDao;
 
/**
 * This class is the access point to the business logic.
 * @author macario.polo
 *  
 */
public class Manager {
	
	private Manager() {	}
	
	public static class ManagerHolder {
		public static Manager manager=new Manager();	
	
		/**
		 * 
		 * @return The only existing instance of the Manager
		 */
		public static Manager get() {
			return ManagerHolder.manager;
		}
	}
	
	/**
	 * Executed by {@link Manager} when a vehicle passes through the radar exceeding the speed limit
	 * @param license: license plate of the vehicle
	 * @param speed: speed of the vehicle
	 * @param location: location of the radar
	 * @param maxSpeed: max speed in the controlled point
	 */
	public int openInquiry(String license, double speed, String location, double maxSpeed) {
		Inquiry inquiry=new Inquiry(license, speed, location, maxSpeed);
		GeneralDao<Inquiry> dao=new GeneralDao<>();
		dao.insert(inquiry);
		return inquiry.getId();
	}
	
	/**
	 * Executed from the user interface when the vehicle's owner identifies the driver
	 * @param idInquiry id of the {@link Inquiry}
	 * @param dni dni of the {@link Driver}
	 * @return 
	 */
	public Sanction identifyDriver(int idInquiry, String dni) {
		GeneralDao<Inquiry> dao=new GeneralDao<>();
		Inquiry inquiry=dao.findById(Inquiry.class, idInquiry);
		Sanction sanction=inquiry.createSanctionFor(dni);
		return sanction;
	}
	
	/***
	 * Executed from the user interface when a sanction is paid
	 * @param idSanction The id of the sanction to be paid
	 */
	public void pay(int idSanction) {
		GeneralDao<Sanction> dao=new GeneralDao<>();
		Sanction sanction=dao.findById(Sanction.class, idSanction);
		sanction.pay();
		dao.update(sanction);
	}
	

}
