package dke.pr.cli.tests;

import java.util.Arrays;

import dke.pr.cli.CBRInterface;
import g4dke.app.SystemHelper;

/*
 * @author Viktoria J  / Burgstaller
 * */
public class testStaticModel {
	// PFAD auf das Verzeichnis der Flora installation ändern
	//Viktoria C:/Users/vikto/Flora-2/flora2
	// Philip D:\Users\Philip\Flora-2\flora2
	final static String PFAD = SystemHelper.PFAD;
	
	public static void main(String[] args) throws Exception {
		CBRInterface fl = new CBRInterface(
				PFAD + "/ctxModelAIM.flr",
				PFAD + "/bc.flr", "AIMCtx",
				"SemNOTAMCase");

		fl.setDebug(false);

		System.out.println("Contexts: " + fl.getCtxs());

		System.out.print("\nCtx Hier: ");
		for (String[] strings : fl.getCtxHierarchy()) {
			System.out.print(Arrays.toString(strings) + ", ");
		}
		System.out
				.println("\n\nCtx File: "
						+ fl.getCtxFile("allInterests_allFlightPhases_allEventScenarios"));

		System.out.println("\nParams:   " + fl.getParameters());
		System.out.println("\nParam Va: " + fl.getParameterValues());
		System.out.print("\nValue hi: ");
		for (String[] strings : fl.getParameterValuesHiearchy("Interest")) {
			System.out.print(Arrays.toString(strings) + ", ");
		}

		System.out.println("\nRel Ctxs1: " + fl.detRelevantCtxs("bc1"));
		System.out.println("\nCaseSpec1: " + fl.detCaseSpecificCtx("bc1", "m"));
		System.out.println("\nResults1:  " + fl.readTargetModule("m"));
		
		
		System.out
				.println("\nnew BC:   "
						+ fl.newBusinessCase("${bc2:SemNOTAMCase[interestSpec->iSpec2,notam->n1]@bc}"));
		System.out.println("\nRel Ctxs: " + fl.detRelevantCtxs("bc2"));
		System.out.println("\nCaseSpec: " + fl.detCaseSpecificCtx("bc2", "m"));
		System.out.println("\nResults:  " + fl.readTargetModule("m"));

		System.out.println("\nGetRules: "
				+ fl.getRules("helicopter_allFlightPhases_obstruction"));

		System.out.println("\nGetDetParam: ");
		for (String str : fl.getDetParamValue("FlightPhase")) {
			System.out.println(str + ", ");
		}

		System.out.println("\nGetDescribingProperties: ");
		for (String[] str : fl.getInterestSpecClass()) {
			System.out.println(Arrays.toString(str) + ", ");
		}

		System.out.println("\nGetNOTAMs: " + fl.getNOTAMS());

		System.out.println("\ngetNOTAMInfo: ");
		for (String[] str : fl.getNOTAMInfo("n_obstructionC")) {
			System.out.print(Arrays.toString(str) + ", ");
		}
		
		System.out.println("\ngetIspecs: " + fl.getISpecs());
		
		System.out.println("\ngetIspecInfo: ");
		for (String[] str: fl.getISpecInfo("iSpec1")){
			System.out.print(Arrays.toString(str));
		}

		fl.close();
	}
}
