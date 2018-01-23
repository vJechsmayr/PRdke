package g4dke.app;

import java.io.IOException;

import dke.pr.cli.CBRInterface;

public class FloraInterface {
	private static CBRInterface fl = null;

	public static CBRInterface getInstance() {
		if (fl == null) {
			initInterface();
		}

		return fl;
	}

	private static void initInterface() {
		try {
			fl = new CBRInterface(SystemHelper.PFAD + "/ctxModelAIM.flr", SystemHelper.PFAD + "/bc.flr", "AIMCtx",
					"SemNOTAMCase");
			fl.setDebug(false);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
