/**
 * 
 */
package edu.arizona.sirls.ontology_lookup.search;

import java.util.ArrayList;
import java.util.Hashtable;

import edu.arizona.sirls.ontology_lookup.data.Entity;
import edu.arizona.sirls.ontology_lookup.data.EntityProposals;
import edu.arizona.sirls.ontology_lookup.data.FormalConcept;
import edu.arizona.sirls.ontology_lookup.knowledge.Dictionary;
import edu.arizona.sirls.ontology_lookup.knowledge.ELKReasoner;
import edu.arizona.sirls.ontology_lookup.knowledge.TermOutputerUtilities;

/**
 * @author Hong Cui
 * 
 */
public class SearchMain {

	// the quality ontology
	public static String pato;
	// the spatial ontology
	public static String bspo;
	// the entity ontology
	public static String eonto;
	public static TermOutputerUtilities ontoutil;
	public static ELKReasoner elk;
	// the file name of the entity ontology
	public static String entityonto = "uberon";
	public static Hashtable<String, String> ontoURLs = new Hashtable<String, String>();
	public static String dictdir;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// search: "quality"
		// C:/Users/updates/CharaParserTest/Ontologies/charaparser_eval ext bspo
		// pato "red"/
		// result: red:red:http://purl.obolibrary.org/obo/PATO_0000322
		// Search:"entity" C:/Users/updates/CharaParserTest/Ontologies ext bspo
		// pato "condyle of femur"
		// result: condyle of femur:condyle of
		// femur:http://purl.obolibrary.org/obo/UBERON_0009980
		// Search:"entity" C:/Users/updates/CharaParserTest/Ontologies ext bspo
		// pato "femur condyle"
		// result: femur condyle:condyle of
		// femur:http://purl.obolibrary.org/obo/UBERON_0009980

//		String type = args[0]; // search type: quality or entity (i.e.,
//								// structure)
//		String ontologydir = args[1]; //
//		String eonto = args[2];
//		String bspo = args[3];
//		String pato = args[4];
//		String term = args[5];
//		SearchMain.dictdir = args[6];
		String rel = "part_of";

		String type = "quality"; // search type: quality or entity (i.e., structure)
		String ontologydir = "/home/sbs0457/workspace/OTOLiteForETC/OntologyOwlFiles"; //
		String eonto = "ext";
		String bspo = "bspo";
		String pato = "pato";
		String term = "round";
		SearchMain.dictdir = "/home/sbs0457/workspace/OTOLiteForETC/DictFiles";

		// get ontology filepaths and urls

		SearchMain.entityonto = eonto;
		SearchMain.eonto = ontologydir + "/" + eonto + ".owl";
		SearchMain.bspo = ontologydir + "/" + bspo + ".owl";
		SearchMain.pato = ontologydir + "/" + pato + ".owl";

		if (eonto.compareToIgnoreCase("po") == 0) {
			SearchMain.ontoURLs.put(SearchMain.eonto,
					"http://purl.obolibrary.org/obo/po.owl");
		} else if (eonto.compareToIgnoreCase("hao") == 0) {
			SearchMain.ontoURLs.put(SearchMain.eonto,
					"http://purl.obolibrary.org/obo/hao.owl");
		} else if (eonto.compareToIgnoreCase("poro") == 0) {
			SearchMain.ontoURLs.put(SearchMain.eonto,
					"http://purl.obolibrary.org/obo/poro.owl");
		} else if (eonto.compareToIgnoreCase("ext") == 0) {
			SearchMain.ontoURLs.put(SearchMain.eonto,
					"purl.obolibrary.org/obo/uberon/ext.owl"); // this is my
																// test case
		}
		SearchMain.ontoURLs.put(SearchMain.bspo,
				"http://purl.obolibrary.org/obo/bspo.owl");
		SearchMain.ontoURLs.put(SearchMain.pato,
				"http://purl.obolibrary.org/obo/pato.owl");

		try {
			// now load ontologies
			SearchMain.ontoutil = new TermOutputerUtilities();

			// SearchMain.elk = new ELKReasoner(new File(SearchMain.eonto),
			// true); //take a long time to load.

			// search terms
			System.out.println("search " + term + " as " + type + " in "
					+ SearchMain.eonto + ";" + SearchMain.bspo + ";"
					+ SearchMain.pato);
			if (type.compareTo("quality") == 0) {
				TermSearcher ts = new TermSearcher();
				ArrayList<FormalConcept> quality = ts.searchTerm(term,
						"quality");
				if (quality != null) {
					for (FormalConcept e : quality) {
						System.out.println(term + ":" + e.getLabel()
								+ e.getClassIRI());
						System.out.println("definition:" + e.getDef());
						System.out.println("parent label:" + e.getPLabel());
					}
				}
			} else {
				Hashtable<String, String> results = new Hashtable<String, String>(); // term,
																						// id,
																						// definition,
																						// parent
																						// term,
				EntitySearcherOriginal eso = new EntitySearcherOriginal();
				ArrayList<EntityProposals> eps = eso.searchEntity(term, "",
						term + "+" + "", rel);
				if (eps != null) {
					for (EntityProposals ep : eps)
						for (Entity e : ep.getProposals()) {
							System.out.println(term + ":" + e.getLabel() + ":"
									+ e.getClassIRI());
							System.out.println("definition:" + e.getDef());
							System.out.println("parent label:" + e.getPLabel());
						}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (Dictionary.wordnetdict != null) // release resources
				Dictionary.wordnetdict.close();
		}
	}

}