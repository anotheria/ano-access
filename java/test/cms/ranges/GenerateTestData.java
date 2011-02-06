package test.cms.ranges;


public class GenerateTestData {
/*	public static void main(String a[]) throws Exception{
		generateTestRanges();
		generateCustomConstraints();
	}
	
	private static final void generateTestRanges() throws Exception{
		IConstraintManagementService cmanager = ConstraintManagementServiceFactory.createConstraintManagementService();
		generateDayRange(cmanager, DayOfWeekUtils.Monday, "Montag");
		generateDayRange(cmanager, DayOfWeekUtils.Tuesday, "Dienstag");
		generateDayRange(cmanager, DayOfWeekUtils.Wednesday, "Mittwoch");
		generateDayRange(cmanager, DayOfWeekUtils.Thursday, "Donnerstag");
		generateDayRange(cmanager, DayOfWeekUtils.Friday, "Freitag");
		generateDayRange(cmanager, DayOfWeekUtils.Saturday, "Samstag");
		generateDayRange(cmanager, DayOfWeekUtils.Sunday, "Sonntag");
		
		//////
		for (int i=10; i<=20; i++)
			generateTimeOfDayRange(cmanager, i, 0, i+1, 0);
	
		generateUnitsFromNowRange(cmanager, RangeUnitUtils.Days, 5);
		generateUnitsFromNowRange(cmanager, RangeUnitUtils.Hours, 2);
		generateUnitsFromNowRange(cmanager, RangeUnitUtils.Minutes, 10);
		generateUnitsFromNowRange(cmanager, RangeUnitUtils.Seconds, 10);
		
	}
	
	private static void generateDayRange(IConstraintManagementService service, int day, String description) throws Exception{
		DayOfWeekRangeDef d = DayOfWeekRangeDefFactory.createDayOfWeekRangeDef();
		d.setDay(day);
		d.setName(description);
		
		List<DayOfWeekRangeDef> alreadyThere = service.getDayOfWeekRangeDefsByProperty(DayOfWeekRangeDef.PROP_NAME, description);

		if (alreadyThere.size()==0){
			service.createDayOfWeekRangeDef(d);
			System.out.println("Created: "+d);
		}
	}
	
	private static void generateUnitsFromNowRange(IConstraintManagementService service, int unit, int value) throws Exception{
		UnitsFromNowRangeDef d = UnitsFromNowRangeDefFactory.createUnitsFromNowRangeDef();
		d.setUnit(unit);
		d.setValue(value);
		d.setName(value + " "+RangeUnitUtils.getName(unit));
		
		
		List<UnitsFromNowRangeDef> alreadyThere = service.getUnitsFromNowRangeDefsByProperty(UnitsFromNowRangeDef.PROP_NAME, d.getName());

		if (alreadyThere.size()==0){
			service.createUnitsFromNowRangeDef(d);
			System.out.println("Created: "+d);
		}
	}

	private static void generateTimeOfDayRange(IConstraintManagementService service, int sh, int sm, int eh, int em) throws Exception{
		TimeOfDayRangeDef d = TimeOfDayRangeDefFactory.createTimeOfDayRangeDef();
		String name = NumberUtils.itoa(sh)+":"+NumberUtils.itoa(sm)+" - "+NumberUtils.itoa(eh)+":"+NumberUtils.itoa(em);
		d.setHoursEnd(eh);
		d.setMinutesEnd(em+1);
		
		d.setHoursStart(sh);
		d.setMinutesStart(sm);
		d.setName(name);
		
		List<TimeOfDayRangeDef> alreadyThere = service.getTimeOfDayRangeDefsByProperty(TimeOfDayRangeDef.PROP_NAME, name);

		if (alreadyThere.size()==0){
			service.createTimeOfDayRangeDef(d);
			System.out.println("Created: "+d);
		}
	}
	
	private static void generateCustomConstraints() throws Exception{
		IConstraintManagementService service = ConstraintManagementServiceFactory.createConstraintManagementService();
		generateCustomConstraint(service, "FemaleObject", "de.friendscout.bouncer.constraints.custom.FemaleObjectConstraint", null);
		generateCustomConstraint(service, "FemaleSubject", "de.friendscout.bouncer.constraints.custom.FemaleSubjectConstraint", null);
		generateCustomConstraint(service, "MaleObject", "de.friendscout.bouncer.constraints.custom.MaleObjectConstraint", null);
		generateCustomConstraint(service, "MaleSubject", "de.friendscout.bouncer.constraints.custom.MaleSubjectConstraint", null);
	}
	
	private static void generateCustomConstraint(IConstraintManagementService service, String name, String clazz, String parameter) throws Exception{
		if (parameter==null)
			parameter = "";
		List<CustomConstraintDef> alreadyThere = service.getCustomConstraintDefsByProperty(CustomConstraintDef.PROP_NAME, name);
		if (alreadyThere.size()==1)
			return;
		CustomConstraintDef def = CustomConstraintDefFactory.createCustomConstraintDef();
		def.setName(name);
		def.setParameter(parameter);
		def.setClazz(clazz);
		service.createCustomConstraintDef(def);
		System.out.println("Created: "+def);
	}
*/

}
