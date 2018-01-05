package dke.pr.cli.tests;

import dke.pr.cli.CBRInterface;
import g4dke.app.SystemHelper;

import java.util.Arrays;

public class testDelParameterValue {

	public static void main(String[] args) throws Exception {
		CBRInterface fl = new CBRInterface(
				SystemHelper.PFAD + "/ctxModelAIM.flr",
				SystemHelper.PFAD + "/bc.flr", "AIMCtx",
				"SemNOTAMCase");

		System.out.println("\n\nUnusedParameterValues:     " + fl.detUnusedParameterValues());
		System.out.println("\n\ndelVal seaplane:     " + fl.delParameterValue("seaplane"));
		Thread.sleep(1000);
		fl.restart();
		System.out.print("Val Hier:   ");
		for (String[] strings : fl.getParameterValuesHiearchy("Interest")) {
			System.out.print(Arrays.toString(strings) + ", ");
		}
		System.out.println("\n");

		System.out.println("\n\nUnusedParameterValues:     " + fl.detUnusedParameterValues());
		System.out.println("\n\ndelVal unspecifiedAircraft:     " + fl.delParameterValue("unspecifiedAircraft"));
		Thread.sleep(1000);
		fl.restart();
		System.out.print("Val Hier:   ");
		for (String[] strings : fl.getParameterValuesHiearchy("Interest")) {
			System.out.print(Arrays.toString(strings) + ", ");
		}
		System.out.println("\n");
	}
}
