package dke.pr.cli;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Specific wrapper for CBR
 *
 * @author fburgstaller
 *
 */
public class CBRInterface extends Flora2CLI {

	protected static final String MODEL_MODULE = "ctxModel";
	protected static final String BC_MODULE = "bc";
	private final String CONTEXT_CLASS;
	private final String BC_CLASS;
	private final String F_CTX_MODEL;
	private final String F_BC;

	/**
	 * Initializes a Flora 2 shell with the CBR Model and the business cases,
	 * the contextClass specifies the name of the context class used (AIMCtx)
	 *
	 * @param fCtxModel
	 * @param fBc
	 * @param contextClass
	 * @throws IOException
	 */
	public CBRInterface(String fCtxModel, String fBc, String contextClass,
			String bcClass) throws IOException {
		super();
		this.CONTEXT_CLASS = contextClass;
		this.BC_CLASS = bcClass;
		this.F_BC = fBc;
		this.F_CTX_MODEL = fCtxModel;
		if (!loadFile(fCtxModel, MODEL_MODULE))
			throw new IOException("Loading module failed");
		if (!loadFile(fBc, BC_MODULE))
			throw new IOException("Loading module failed");
	}

	public boolean restart() throws IOException {
		close();
		start();
		if (!loadFile(F_CTX_MODEL, MODEL_MODULE))
			throw new IOException("Loading module model failed");
		if (!loadFile(F_BC, BC_MODULE))
			throw new IOException("Loading module bc failed");
		return true;
	}

	// -----------------------------------------------------------------------------
	// ------------------ Query model information
	// -----------------------------------------------------------------------------
	/**
	 * Retrieve the contexts in the model
	 *
	 * @return a list of contexts
	 * @throws IOException
	 */
	public List<String> getCtxs() throws IOException {
		String cmd = String.format("?ctx:%s@%s.", CONTEXT_CLASS, MODEL_MODULE);
		String ret = issueCommand(cmd);
		return parseSingleVar(ret);
	}

	/**
	 * Retrieves all super-sub context tuples of the model
	 * 
	 * ATTENTION: Requires -
	 * transCtx(?X,?Y):-?X:AIMCtx[specialises->?Z],?Z[specialises->?Y], \+ (?X =
	 * ?Z), \+ (?Z = ?Y). - in ctxModelAIM.flr or ctxModel.flr
	 *
	 * @return List of context-pairs [subCtx,superCtx]
	 * @throws IOException
	 */
	public List<String[]> getCtxHierarchy() throws IOException {
		String cmd = String
				.format("?subCtx:%s[specialises->?superCtx]@%s,\\+ (?superCtx =?subCtx),\\naf transCtx(?subCtx,?superCtx)@%s.",
						CONTEXT_CLASS, MODEL_MODULE, MODEL_MODULE);
		String ret = issueCommand(cmd);
		return parseMultipleVars(ret, 2);
	}

	/**
	 * Get the file location of the rule file belonging to context ctx
	 *
	 * @param ctx
	 * @return file path of rule file
	 * @throws IOException
	 */
	public String getCtxFile(String ctx) throws Exception {
		String cmd = String.format("%s:%s[file->?ctxf]@%s.", ctx,
				CONTEXT_CLASS, MODEL_MODULE);
		String str = issueCommand(cmd);
		List<String> ret = parseSingleVar(str);
		if (ret.size() == 0)
			throw new Exception(
					"No file for context defined or context does not exist");
		return ret.get(0);
	}

	/**
	 * @param ctx
	 * @return
	 * @throws IOException
	 */
	public List<String[]> getCtxInfo(String ctx) throws IOException {
		String cmd = String.format("(%s:%s[?val:Parameter->?ctx])@%s.", ctx,
				CONTEXT_CLASS, MODEL_MODULE);
		String ret = issueCommand(cmd);
		return parseMultipleVars(ret, 2);
	}

	/**
	 * Get the parameters of the CBR model
	 *
	 * @return list of parameters
	 * @throws IOException
	 */
	public List<String> getParameters() throws IOException {
		String cmd = String.format("?param:Parameter@%s.", MODEL_MODULE);
		String ret = issueCommand(cmd);
		return parseSingleVar(ret);
	}

	/**
	 * Get all parameter values of the CBR model
	 *
	 * @return
	 * @throws IOException
	 */
	public List<String> getParameterValues() throws IOException {
		String cmd = String.format("(?val:?_param,?_param:Parameter)@%s.",
				MODEL_MODULE);
		String ret = issueCommand(cmd);
		return parseSingleVar(ret);
	}

	/**
	 * Get all parameter values of the CBR model
	 *
	 * @return
	 * @throws IOException
	 */
	public List<String> getParameterParameterValues(String param)
			throws IOException {
		String cmd = String.format("(?val:%s,%s:Parameter)@%s.", param, param,
				MODEL_MODULE);
		String ret = issueCommand(cmd);
		return parseSingleVar(ret);
	}

	/**
	 * Retrieves all super-sub parameter value tuples of the model
	 *
	 * @parameter parameter name
	 * @return List of parameter-value-pairs [superValue,subValue]
	 * @throws IOException
	 */
	public List<String[]> getParameterValuesHiearchy(String parameter)
			throws IOException {
		String cmd = String
				.format("isbasefact{?superVal[covers->?subVal]@%s}, (?superVal:%s,?subVal:%s)@%s,\\+ (?superVal = ?subVal).",
						MODEL_MODULE, parameter, parameter, MODEL_MODULE);
		String ret = issueCommand(cmd);
		return parseMultipleVars(ret, 2);
	}

	/**
	 * Get DetParamValue of a Parameter
	 * 
	 * @param param
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<String> getDetParamValue(String param)
			throws FileNotFoundException, IOException {
		List<String> detParams = new LinkedList<String>();
		Pattern p = Pattern.compile(param + "\\[detParamValue.*\\]\\s*:-.*\\.");

		try (BufferedReader reader = new BufferedReader(new FileReader(
				F_CTX_MODEL))) {

			StringBuffer line = new StringBuffer("");
			String l = null;
			while ((l = reader.readLine()) != null) {
				if (l.endsWith(".") || l.endsWith("*/") || l.startsWith("//")
						|| l.startsWith("#") || l.matches("^\\s*$")) {
					line.append(l);
					Matcher match = p.matcher(line);
					if (match.find())
						detParams.add(line.toString());

					line = new StringBuffer("");
				} else {
					line.append(l);
					line.append(System.lineSeparator());
				}
			}
			reader.close();
		}

		return detParams;
	}

	/**
	 * Returns the describing properties of the INterestSpec class
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<String[]> getInterestSpecClass() throws IOException {
		String cmd = String.format("InterestSpec[|?describing=>?type|]@%s.",
				BC_MODULE);
		String ret = issueCommand(cmd);
		return parseMultipleVars(ret, 2);
	}

	/**
	 * Returns the current NOTAMs stored in Flora-2
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<String> getNOTAMS() throws IOException {
		String cmd = String.format("?n:NOTAM@%s.", BC_MODULE);
		String ret = issueCommand(cmd);
		return parseSingleVar(ret);
	}

	/**
	 * Returns the attributes and values of the given NOTAM
	 * 
	 * @param notam
	 * @return
	 * @throws IOException
	 */
	public List<String[]> getNOTAMInfo(String notam) throws IOException {
		String cmd = String.format("(%s:NOTAM[?param->?val])@%s.", notam,
				BC_MODULE);
		String ret = issueCommand(cmd);
		return parseMultipleVars(ret, 2);
	}

	/**
	 * Get interestspecs in system
	 * 
	 * @param notam
	 * @return
	 * @throws IOException
	 */
	public List<String> getISpecs() throws IOException {
		String cmd = String.format("?s:InterestSpec@%s.", BC_MODULE);
		String ret = issueCommand(cmd);
		return parseSingleVar(ret);
	}

	/**
	 * Get info of given interest spec
	 * 
	 * @param notam
	 * @return
	 * @throws IOException
	 */
	public List<String[]> getISpecInfo(String iSpec) throws IOException {
		String cmd = String.format("%s:InterestSpec[?p->?v]@%s.", iSpec,
				BC_MODULE);
		String ret = issueCommand(cmd);
		return parseMultipleVars(ret, 2);
	}

	/**
	 * Get the rules of a certain context
	 * 
	 * @param ctx
	 * @return hashmap with rule id as key and rule code as value
	 */
	public HashMap<String, String> getRules(String ctx) throws Exception {
		String file = this.getCtxFile(ctx);
		file = file.replace("'", "");
		HashMap<String, String> rules = new HashMap<String, String>();
		Pattern p = Pattern.compile("(?<=@!\\{)\\w+(?=\\})");

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

			StringBuffer line = new StringBuffer("");
			String l = null;
			while ((l = reader.readLine()) != null) {
				if (l.endsWith(".") || l.endsWith("*/") || l.startsWith("//")
						|| l.startsWith("#") || l.matches("^\\s*$")) {
					line.append(l);
					Matcher match = p.matcher(line);
					if (match.find()) {
						String key = match.group(0);
						rules.put(key, line.toString());
					}
					line = new StringBuffer("");
				} else {
					line.append(l);
					line.append(System.lineSeparator());
				}
			}
			reader.close();
		}

		return rules;
	}

	private List<String> getParameterValueChildren(String value)
			throws IOException {
		String cmd = String.format(
				"isbasefact{%s[covers->?subVal]@%s},?subVal != %s.", value,
				MODEL_MODULE, value);
		String ret = issueCommand(cmd);
		return parseSingleVar(ret);
	}

	private List<String> getParameterValueParents(String value)
			throws IOException {
		String cmd = String.format(
				"isbasefact{?superVal[covers->%s]@%s},?superVal != %s.", value,
				MODEL_MODULE, value);
		String ret = issueCommand(cmd);
		return parseSingleVar(ret);
	}

	// -----------------------------------------------------------------------------
	// ------------------ Evaluate business cases
	// -----------------------------------------------------------------------------
	/**
	 * Retrieve relevant contexts for given business case.
	 *
	 * @param bc
	 * @return
	 * @throws IOException
	 */
	public List<String> detRelevantCtxs(String bc) throws IOException {
		String cmd = String.format("%s[detRelevantCtxs(%s)->?ctx]@%s.",
				CONTEXT_CLASS, bc, MODEL_MODULE);
		String ret = issueCommand(cmd);
		return parseSingleVar(ret);
	}

	/**
	 * Retrieve relevant contexts for given business case.
	 *
	 * @param bc
	 * @return
	 * @throws IOException
	 */
	public List<String> readTargetModule(String targetModule)
			throws IOException {
		String cmd = String.format("clause{?h@" + targetModule + ",?_b}.");
		String ret = issueCommand(cmd);
		List<String> lst = parseSingleVar(ret);
		List<String> retLst = new LinkedList<String>();
		for (int i = 0; i < lst.size(); i++) {
			retLst.add(lst.get(i).replaceAll("(\\$\\{|@\\w+\\})", ""));
		}
		return retLst;
	}

	/**
	 * Merges rules/terms of all contexts relevant to bc into the specified
	 * targetModule.
	 *
	 * @param bc
	 * @param targetModule
	 * @return
	 * @throws IOException
	 */
	public List<String> detCaseSpecificCtx(String bc, String targetModule)
			throws IOException {
		checkTargetModule(targetModule);
		String cmd = String.format(
				"%s[%%detCleanCaseSpecificCtx(%s,%s,?ctxf)]@%s.",
				CONTEXT_CLASS, bc, targetModule, MODEL_MODULE);
		String ret = issueCommand(cmd);
		return parseSingleVar(ret);

	}

	/**
	 * creates a new business case
	 *
	 * @param bcDef
	 * @return
	 * @throws IOException
	 */
	public boolean newBusinessCase(String bcDef) throws IOException {
		String cmd = String.format("%s[%%newBC(%s)]@%s.", BC_CLASS, bcDef,
				BC_MODULE);
		String ret = issueCommand(cmd);
		return ret.endsWith("Yes\n");

	}

	/**
	 * creates/empties the target module
	 *
	 * @param targetModule
	 * @return
	 * @throws IOException
	 */
	private boolean checkTargetModule(String targetModule) throws IOException {
		String cmd = String
				.format("\\if isloaded{%s} \\then erasemodule{%s} \\else newmodule{%s}.",
						targetModule, targetModule, targetModule);
		String ret = issueCommand(cmd);
		return ret.equals("Yes\n");
	}

	/**
	 * Determine unused parameter values
	 *
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public List<String> detUnusedParameterValues() throws IOException {
		String cmd = String.format("%s[detUnusedValues->?v]@%s.",
				CONTEXT_CLASS, MODEL_MODULE);
		String ret = issueCommand(cmd);
		return parseSingleVar(ret);

	}

	// -----------------------------------------------------------------------------
	// -------------------- Modification operations
	// -----------------------------------------------------------------------------

	// ---------------rule
	/**
	 * Adds a rule to the given context
	 *
	 * @param ctx
	 * @param rule
	 * @return whether it works or not
	 * @throws Exception
	 */
	public boolean addRule(String ctx, String rule) throws Exception {
		Path rules = Paths.get(this.getCtxFile(ctx).replace("'", ""));

		Files.write(rules, (System.lineSeparator() + rule + System
				.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
		return true;
	}

	/**
	 * Deletes a rule from a given context
	 *
	 * @param ctx
	 * @param rule
	 * @return whether it works or not
	 * @throws Exception
	 */
	public boolean delRule(String ctx, String ruleID) throws Exception {
		String rules = this.getCtxFile(ctx).replace("'", "");
		return replaceRegExPatternFromFile(new File(rules), "(?s)\\@!\\{"
				+ ruleID + ".*\\.", "");
	}

	// ---------------context
	/**
	 * Adds the flora2 context spec to the model
	 *
	 * @param ctxDef
	 * @param fCtx
	 * @return whether it works or not
	 * @throws IOException
	 */
	public boolean addCtx(String ctxDef, String fCtx) throws IOException {
		Path model = Paths.get(F_CTX_MODEL);
		Files.write(model, (System.lineSeparator() + ctxDef + System
				.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
		if (!Files.exists(Paths.get(fCtx), LinkOption.NOFOLLOW_LINKS))
			Files.createFile(Paths.get(fCtx));
		return true;
	}

	/**
	 * Deletes the context with the name ctx from the model
	 *
	 * @param ctx
	 * @return
	 * @throws Exception
	 */
	public boolean delCtx(String ctx, boolean fileAlso) throws Exception {
		Path temp = Paths.get(this.getCtxFile(ctx).replace("'", ""));

		if (fileAlso)
			Files.delete(temp);
		return removeRegExPatternFromFile(new File(F_CTX_MODEL), "(?s)^" + ctx
				+ ":" + CONTEXT_CLASS + "\\[.*\\]\\.");
	}

	// ---------------parameter
	/**
	 * add a parameter with pName and parameter root value rootValue and the
	 * detParam method specified in Flora2 in parameter detParamDef
	 *
	 * @param name
	 * @param rootValue
	 * @param detParamDef
	 * @return false if no context has been adapted
	 * @throws IOException
	 */
	public boolean addParameter(String pName, String rootValue,
			String detParamDef) throws IOException {
		Path model = Paths.get(F_CTX_MODEL);
		Files.write(model,
				(System.lineSeparator() + pName + ":Parameter." + System
						.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
		Files.write(model, (System.lineSeparator() + detParamDef + System
				.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
		Files.write(model, (System.lineSeparator() + "{" + rootValue + "}:"
				+ pName + "." + System.lineSeparator()).getBytes(),
				StandardOpenOption.APPEND);

		boolean ret = replaceRegExPatternFromFile(new File(F_CTX_MODEL),
				"(?s)(?<=:" + CONTEXT_CLASS + "\\[)", pName + "->" + rootValue
						+ ",");

		return ret
				&& replaceRegExPatternFromFile(new File(F_CTX_MODEL),
						"(?s)(?<=" + CONTEXT_CLASS
								+ ":ContextClass\\[defBy->\\{)", pName + ",");
	}

	/**
	 * Deletes the parameter pName, does not delete parameter values!
	 *
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public boolean delParameter(String pName) throws IOException {
		File model = new File(F_CTX_MODEL);
		removeRegExPatternFromFile(model, "(?s)" + pName + ":Parameter\\.");
		removeRegExPatternFromFile(model, "(?s)" + pName + "\\[.*\\.");
		removeRegExPatternFromFile(model, "(?s)\\{[\\w, ]*\\}:" + pName + "\\.");

		boolean ret = removeValueFromFile(new File(F_CTX_MODEL), "(?s)\\w+:"
				+ CONTEXT_CLASS + "\\[.*\\].", ",\\s*" + pName
				+ "\\s*->\\s*\\w+\\s*,", ",");

		ret = removeValueFromFile(new File(F_CTX_MODEL), "(?s)\\w+:"
				+ CONTEXT_CLASS + "\\[.*\\].", pName + "\\s*->\\s*\\w+\\s*,",
				"")
				|| ret;

		ret = removeValueFromFile(new File(F_CTX_MODEL), "(?s)\\w+:"
				+ CONTEXT_CLASS + "\\[.*\\].", ",\\s*" + pName
				+ "\\s*->\\s*\\w+\\s*", "")
				|| ret;
		boolean ret2 = replaceRegExPatternFromFile(model, ",\\s*" + pName
				+ "\\s*,", ",");
		ret2 = replaceRegExPatternFromFile(model, ",\\s*" + pName, "") || ret2;
		ret2 = replaceRegExPatternFromFile(model, pName + "\\s*,", "") || ret2;

		return ret && ret2;
	}

	// ---------------parameter value
	/**
	 * Adds a parameter value into to the parameter value hierarchy
	 *
	 * @param name
	 * @param parent
	 * @return
	 * @throws IOException
	 */
	public boolean addParameterValue(String pName, String vName,
			String[] parents, String[] children) throws Exception {
		Path model = Paths.get(F_CTX_MODEL);
		File m = new File(F_CTX_MODEL);

		if (!this.getParameters().contains(pName))
			throw new Exception("Unknown Parameter");
		boolean ret = replaceRegExPatternFromFile(new File(F_CTX_MODEL),
				"(?s)(?=\\}:" + pName + ")", "," + vName);

		if (parents == null && children == null)
			throw new Exception("Unconnected Value");

		if (parents != null)
			for (String parent : parents)
				if (!replaceRegExPatternFromFile(new File(F_CTX_MODEL),
						"(?s)(?<=" + parent + "\\[covers->\\{)", vName + ","))
					Files.write(model, (System.lineSeparator() + parent
							+ "[covers->{" + vName + "}]." + System
							.lineSeparator()).getBytes(),
							StandardOpenOption.APPEND);

		if (children != null)
			Files.write(model, (System.lineSeparator() + vName + "[covers->{"
					+ Arrays.toString(children).replaceAll("[\\[\\]]", "")
					+ "}]." + System.lineSeparator()).getBytes(),
					StandardOpenOption.APPEND);

		if (parents != null && children != null)
			for (String parent : parents)
				for (String child : children) {
					ret = removeValueFromFile(m, "(?s)" + parent
							+ "\\[covers->\\{.*\\}\\]\\.", ",\\s*" + child
							+ "\\s*,", ",")
							|| ret;
					ret = removeValueFromFile(m, "(?s)" + parent
							+ "\\[covers->\\{.*\\}\\]\\.", child + "\\s*,", "")
							|| ret;

					ret = removeValueFromFile(m, "(?s)" + parent
							+ "\\[covers->\\{.*\\}\\]\\.", ",\\s*" + child, "")
							|| ret;
				}
		return ret;
	}

	/**
	 * Delete a single parameter value
	 *
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public boolean delParameterValue(String vName) throws IOException {
		List<String> children = getParameterValueChildren(vName);
		List<String> parents = getParameterValueParents(vName);
		File model = new File(F_CTX_MODEL);

		boolean ret = removeRegExPatternFromFile(model, "(?s)" + vName
				+ "\\[covers->\\{.*\\}\\]\\.");

		boolean ret2 = removeValueFromFile(model,
				"(?s)\\w+\\[covers->\\{.*\\}\\]\\.", ",\\s*" + vName + "\\s*,",
				",");
		ret2 = removeValueFromFile(model, "(?s)\\w+\\[covers->\\{.*\\}\\]\\.",
				",\\s*" + vName, "") || ret2;
		ret2 = removeValueFromFile(model, "(?s)\\w+\\[covers->\\{.*\\}\\]\\.",
				vName + "\\s*,", "") || ret2;
		ret2 = removeValueFromFile(model,
				"(?s)\\{.*" + vName + ".*\\}:\\w+\\.", ",\\s*" + vName
						+ "\\s*,", ",")
				|| ret2;
		ret2 = removeValueFromFile(model,
				"(?s)\\{.*" + vName + ".*\\}:\\w+\\.", ",\\s*" + vName, "")
				|| ret2;
		ret2 = removeValueFromFile(model,
				"(?s)\\{.*" + vName + ".*\\}:\\w+\\.", vName + "\\s*,", "")
				|| ret2;

		ret = ret && ret2;
		for (String parent : parents)
			for (String child : children)
				ret = ret
						&& replaceRegExPatternFromFile(new File(F_CTX_MODEL),
								"(?s)(?<=" + parent + "\\[covers->\\{)", child
										+ ",");
		return ret;
	}

	// --------------- detParamValue
	/**
	 * Given the new definition to determine parameter values from business case
	 * classes the old definition is replaced
	 * 
	 * @param param
	 * @param def
	 * @return
	 * @throws IOException
	 */
	public boolean updateDetParamValue(String param, String def)
			throws IOException {
		File model = new File(F_CTX_MODEL);
		boolean ret = removeRegExPatternFromFile(model, "(?s)" + param
				+ "\\[detParamValue.*\\]\\s*:-.*\\.");

		Files.write(Paths.get(F_CTX_MODEL),
				(System.lineSeparator() + def + System.lineSeparator())
						.getBytes(), StandardOpenOption.APPEND);
		return ret;
	}

	// --------------- interestSpec(class)
	/**
	 * Given the definition, e.g. "interest=>\object, flightPhase=>FlightPhase",
	 * the old InterestSpec is replaced
	 * 
	 * @param iSpec
	 * @return
	 * @throws IOException
	 */
	public boolean updateInterestSpecClass(String iSpec) throws IOException {
		File model = new File(F_BC);
		return replaceRegExPatternFromFile(model,
				"InterestSpec\\[\\|.*\\|\\].", "InterestSpec[|" + iSpec + "|].");
	}

	/**
	 * Add new InterestSpec to bc file
	 * 
	 * @param def
	 * @return
	 * @throws IOException
	 */
	public boolean addInterestSpec(String def) throws IOException {
		Path rules = Paths.get(F_BC);

		Files.write(rules, (System.lineSeparator() + def + System
				.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
		return true;
	}

	/**
	 * Delete InterestSpec from bc file
	 * 
	 * @param iSpec
	 * @return
	 * @throws IOException
	 */
	public boolean delInterestSpec(String iSpec) throws IOException {
		File model = new File(F_BC);
		return replaceRegExPatternFromFile(model, "(?s)" + iSpec
				+ ":InterestSpec\\[.*\\]\\.", "");
	}

	// --------------- helpers
	/**
	 * Assumes that "." ending FLora-2 command is followed by a newline
	 * 
	 * Deletes any command matching the regex expression.
	 * 
	 * @param input
	 *            the file from which to delete
	 * @param regex
	 *            the regex whose matches are to be deleted
	 * @return true if deleted or not found
	 */
	private boolean removeRegExPatternFromFile(File input, String regex) {
		String temp = "temp.flr";
		boolean found = false;

		try {
			File outputFile = new File(temp);
			try (BufferedReader reader = new BufferedReader(new FileReader(
					input));
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							outputFile))) {

				StringBuffer line = new StringBuffer("");
				String l = null;
				while ((l = reader.readLine()) != null) {
					if (l.endsWith(".") || l.endsWith("*/")
							|| l.startsWith("//") || l.startsWith("#")
							|| l.matches("^\\s*$")) {
						line.append(l);
						if (!line.toString().matches(regex)) {
							writer.write(line.toString());
							writer.newLine();
							found = true;
						}
						line = new StringBuffer("");
					} else {
						line.append(l);
						line.append(System.lineSeparator());
					}
				}
				reader.close();
				writer.flush();
				writer.close();
			}

			if (input.delete()) {
				// Rename the output file to the input file
				if (!outputFile.renameTo(input)) {
					throw new IOException("Could not rename " + outputFile
							+ " to " + input.getName());
				}
			} else {
				throw new IOException("Could not delete original input file "
						+ input.getName());
			}
		} catch (IOException ex) {
			// Handle any exceptions
			ex.printStackTrace();
		}
		return found;
	}

	/**
	 * Assumes that "." ending FLora-2 command is followed by a newline
	 * 
	 * Replaces the regex expression by the given string
	 * 
	 * @param input
	 *            the file from which to delete
	 * @param regex
	 *            the regex whose matches are to be deleted
	 * @param replace
	 *            the value to be used instead
	 * @return true if deleted or not found
	 */
	private boolean replaceRegExPatternFromFile(File input, String regex,
			String replace) {
		String temp = "temp.flr";
		boolean found = false;

		try {
			File outputFile = new File(temp);
			try (BufferedReader reader = new BufferedReader(new FileReader(
					input));
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							outputFile))) {

				StringBuffer line = new StringBuffer("");
				String l = null;
				while ((l = reader.readLine()) != null) {
					if (l.endsWith(".") || l.endsWith("*/")
							|| l.startsWith("//") || l.startsWith("#")
							|| l.matches("^\\s*$")) {
						line.append(l);
						String str = line.toString().replaceAll(regex, replace);
						if (!str.equals(line.toString()))
							found = true;
						writer.write(str);
						writer.newLine();
						line = new StringBuffer("");
					} else {
						line.append(l);
						line.append(System.lineSeparator());
					}
				}
				reader.close();
				writer.flush();
				writer.close();
			}

			if (input.delete()) {
				// Rename the output file to the input file
				if (!outputFile.renameTo(input)) {
					throw new IOException("Could not rename " + outputFile
							+ " to " + input.getName());
				}
			} else {
				throw new IOException("Could not delete original input file "
						+ input.getName());
			}
		} catch (IOException ex) {
			// Handle any exceptions
			ex.printStackTrace();
		}
		return found;
	}

	/**
	 * Assumes that "." ending FLora-2 command is followed by a newline
	 * 
	 * Replace a value in a list ({...}).
	 * 
	 * @param input
	 *            the file from which to delete
	 * @param regex
	 *            the regex matching the command
	 * @param the
	 *            name of the value to be deleted
	 * @return true if deleted or not found
	 */
	private boolean removeValueFromFile(File input, String regex, String vText,
			String vRep) {
		String temp = "temp.flr";
		boolean found = false;
		try {
			File outputFile = new File(temp);
			try (BufferedReader reader = new BufferedReader(new FileReader(
					input));
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							outputFile))) {

				StringBuffer line = new StringBuffer("");
				String l = null;
				while ((l = reader.readLine()) != null) {
					if (l.endsWith(".") || l.endsWith("*/")
							|| l.startsWith("//") || l.startsWith("#")
							|| l.matches("^\\s*$")) {
						line.append(l);
						String str = line.toString();
						if (line.toString().matches(regex)) {
							str = str.replaceAll(vText, vRep);
							str = str.replaceAll(",\\s*,", ",");
							found = true;
						}
						writer.write(str);
						writer.newLine();
						line = new StringBuffer("");
					} else {
						line.append(l);
						line.append(System.lineSeparator());
					}
				}
				reader.close();
				writer.flush();
				writer.close();
			}

			if (input.delete()) {
				// Rename the output file to the input file
				if (!outputFile.renameTo(input)) {
					throw new IOException("Could not rename " + outputFile
							+ " to " + input.getName());
				}
			} else {
				throw new IOException("Could not delete original input file "
						+ input.getName());
			}
		} catch (IOException ex) {
			// Handle any exceptions
			ex.printStackTrace();
		}
		return found;
	}
}
