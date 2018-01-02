package dke.pr.cli.tests;

import java.util.Arrays;

import dke.pr.cli.CBRInterface;
import g4dke.app.SystemHelper;

/*
 * @author Viktoria J  / Burgstaller
 * */
public class testModOps {
	// PFAD auf das Verzeichnis der Flora installation ändern
	//Viktoria C:/Users/vikto/Flora-2/flora2
	// Philip D:\Users\Philip\Flora-2\flora2
	final static String PFAD = SystemHelper.PFAD;

	public static void main(String[] args) throws Exception {
		CBRInterface fl = new CBRInterface(
				PFAD + "/ctxModelAIM.flr",
				PFAD + "/bc.flr", "AIMCtx",
				"SemNOTAMCase");

		System.out.println("addRule: "
				+ fl.addRule("helicopter_allFlightPhases_obstruction", "@!{RX}"
						+ System.lineSeparator() + "test:-true."));
		System.out.println("delRule: "
				+ fl.delRule("aircraft_allFlightPhases_obstruction", "R2"));
		fl.restart();
		System.out.println("Test: "
				+ fl.getRules("helicopter_allFlightPhases_obstruction"));
		System.out.println("Test: "
				+ fl.getRules("aircraft_allFlightPhases_obstruction"));

		System.out
				.println("\n\naddCtx:     "
						+ fl.addCtx(
								"aircraft_arrival_closure:AIMCtx[Interest->aircraft,FlightPhase->arrival,EventScenario->closure,file->"+ PFAD +"'/Contexts/aircraft_arrival_closure.flr'].",
								PFAD + "/Contexts/aircraft_arrival_closure.flr"));
		Thread.sleep(1000);
		fl.restart();
		System.out.println("Contexts:   " + fl.getCtxs() + "\nTest: "
				+ fl.getRules("aircraft_arrival_closure"));

		System.out.println("\ndelCtx:     "
				+ fl.delCtx("aircraft_onground_closure", true));
		Thread.sleep(1000);
		fl.restart();
		System.out.println("Contexts:   " + fl.getCtxs());

		System.out
				.println("\n\naddParam:   "
						+ fl.addParameter(
								"MeteorologicalCondition",
								"allMeteorologicalConditions",
								"MeteorologicalCondition[detParamValue(?bc)->?v]:-?v=allMeteorologicalConditions."));
		Thread.sleep(1000);
		fl.restart();
		System.out.println("Params:     " + fl.getParameters());
		for (String[] strings : fl
				.getCtxInfo("aircraft_allFlightPhases_obstruction")) {
			System.out.print(Arrays.toString(strings) + ", ");
		}

		System.out.println("\n\ndelParam:   " + fl.delParameter("FlightPhase"));
		Thread.sleep(1000);
		fl.restart();
		System.out.println("Params:     " + fl.getParameters());
		System.out.println("Values:     " + fl.getParameterValues());
		System.out.print("CTx Infor:  ");
		for (String[] strings : fl
				.getCtxInfo("aircraft_allFlightPhases_obstruction")) {
			System.out.print(Arrays.toString(strings) + ", ");
		}

		System.out.println("\n\naddvalLeaf: "
				+ fl.addParameterValue("Interest", "aerodrome",
						new String[] { "area" }, null));
		Thread.sleep(1000);
		fl.restart();
		System.out.print("Val Hier:   ");
		for (String[] strings : fl.getParameterValuesHiearchy("Interest")) {
			System.out.print(Arrays.toString(strings) + ", ");
		}

		System.out.println("\n\naddValNode: "
				+ fl.addParameterValue("Interest", "specifiedAircraft",
						new String[] { "aircraft" }, new String[] {
								"landplane", "seaplane", "helicopter" }));
		Thread.sleep(1000);
		fl.restart();
		System.out.print("Val Hier:   ");
		for (String[] strings : fl.getParameterValuesHiearchy("Interest")) {
			System.out.print(Arrays.toString(strings) + ", ");
		}

		System.out.println("\n\naddValRoot: "
				+ fl.addParameterValue("EventScenario", "events", null,
						new String[] { "allEventScenarios" }));
		Thread.sleep(1000);
		fl.restart();
		System.out.print("Val Hier:   ");
		for (String[] strings : fl.getParameterValuesHiearchy("EventScenario")) {
			System.out.print(Arrays.toString(strings) + ", ");
		}

		System.out.println("\n\ndelVal:     "
				+ fl.delParameterValue("aircraft"));
		Thread.sleep(1000);
		fl.restart();
		System.out.print("Val Hier:   ");
		for (String[] strings : fl.getParameterValuesHiearchy("Interest")) {
			System.out.print(Arrays.toString(strings) + ", ");
		}

		System.out
				.println("\n\nupDescProp:     "
						+ fl.updateInterestSpecClass("interest=>\\\\object,flightPhase=>FlightPhase,test=>\\\\object"));
		Thread.sleep(1000);
		fl.restart();
		System.out.print("GetISpecClass:   ");
		for (String[] strings : fl.getInterestSpecClass()) {
			System.out.print(Arrays.toString(strings) + ", ");
		}

		System.out
				.println("\n\nupDescProp:     "
						+ fl.updateInterestSpecClass("interest=>\\\\object,flightPhase=>FlightPhase"));
		Thread.sleep(1000);
		fl.restart();
		System.out.print("GetISpecClass:   ");
		for (String[] strings : fl.getInterestSpecClass()) {
			System.out.print(Arrays.toString(strings) + ", ");
		}

		System.out.println("\n\nDelInterestSpec:" + fl.delInterestSpec("iSpec1"));
		Thread.sleep(1000);
		fl.restart();
		System.out.println("getIspecs: " + fl.getISpecs());

		System.out
				.println("\n\naddInterestSpec:"
						+ fl.addInterestSpec("iSpec1:InterestSpec[interest->Boeing_737:Aircraft,flightPhase->onground]."));
		Thread.sleep(1000);
		fl.restart();
		System.out.println("getIspecs: " + fl.getISpecs());
		System.out.println("\nRel Ctxs1: " + fl.detRelevantCtxs("bc1"));
		System.out.println("CaseSpec1: " + fl.detCaseSpecificCtx("bc1", "m"));
		System.out.println("Results1:  " + fl.readTargetModule("m"));
		
		System.out.println("\n\nupDetParamValue:     "
				+ fl.updateDetParamValue("EventScenario", ""));
		Thread.sleep(1000);
		fl.restart();
		System.out.print("GetDetParamValue:   ");
		for (String str : fl.getDetParamValue("EventScenario")) {
			System.out.print(str + ", ");
		}
		// //empty model
		// System.out
		// .println("\naddParam:   "
		// + fl.addParameter(
		// "MeteorologicalCondition",
		// "allMeteorologicalConditions",
		// "MeteorologicalCondition[detParamValue(?bc)->?v]:-?v=allMeteorologicalConditions."));
		// Thread.sleep(1000);
		// fl.restart();
		// System.out.println("Params:     " + fl.getParameters());
		//
		// System.out.println("\naddParam:   "
		// + fl.addParameter("Test", "allTests",
		// "Test[detParamValue(?bc)->?v]:-?v=allTests."));
		// Thread.sleep(1000);
		// fl.restart();
		// System.out.println("Params:     " + fl.getParameters());
		//
		// System.out
		// .println("\naddCtx:     "
		// + fl.addCtx(
		// "test:AIMCtx[MeteorologicalCondition->allMeteorologicalConditions,Test->allTests,file->'/home/fburgstaller/flora2/CBRM/Contexts/aircraft_arrival_closure.flr'].",
		// "/home/fburgstaller/flora2/CBRM/Contexts/aircraft_arrival_closure.flr"));
		// Thread.sleep(1000);
		// fl.restart();
		// System.out.println("Contexts:   " + fl.getCtxs() + "\nTest: "
		// + fl.getRules("test"));
		// for (String[] strings : fl
		// .getCtx("test")) {
		// System.out.print(Arrays.toString(strings) + ", ");
		// }
	}
}
