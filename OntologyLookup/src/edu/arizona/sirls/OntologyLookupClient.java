package edu.arizona.sirls;

import java.util.ArrayList;
import java.util.Hashtable;

import edu.arizona.sirls.data.Entity;
import edu.arizona.sirls.data.EntityProposals;
import edu.arizona.sirls.data.FormalConcept;
import edu.arizona.sirls.knowledge.ELKReasoner;
import edu.arizona.sirls.knowledge.TermOutputerUtilities;
import edu.arizona.sirls.search.EntitySearcherOriginal;
import edu.arizona.sirls.search.SearchMain;
import edu.arizona.sirls.search.TermSearcher;

public class OntologyLookupClient {
	// the entity ontology
	public static String eonto;
	public static TermOutputerUtilities ontoutil;
	public static ELKReasoner elk;
	// the file name of the entity ontology
	public static String entityonto = "uberon";
	public static Hashtable<String, String> ontoURLs = new Hashtable<String, String>();

	private String rel = "part_of";

	public OntologyLookupClient(String ontologyName, String localOntologyDir) {
		String ontologydir = localOntologyDir;
		String eonto = ontologyName;
		String bspo = "bspo";
		String pato = "pato";

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

		// now load ontologies
		SearchMain.ontoutil = new TermOutputerUtilities();
	}

	public ArrayList<FormalConcept> searchCharacter(String term) {
		TermSearcher ts = new TermSearcher();
		return ts.searchTerm(term, "quality");
	}

	public ArrayList<EntityProposals> searchStrucutre(String term) {
		EntitySearcherOriginal eso = new EntitySearcherOriginal();
		return eso.searchEntity(term, "", term + "+" + "", rel);
	}

	public static void main(String[] args) {
		OntologyLookupClient client = new OntologyLookupClient("po",
				"D:\\Work\\Code\\OTOLiteForETC\\OntologyOwlFiles");

		String term = "red";
		ArrayList<FormalConcept> fcs = client.searchCharacter(term);
		if (fcs != null) {
			for (FormalConcept fc : fcs) {
				System.out.println(term + ": ");
				System.out.println("\tClassIRI: " + fc.getClassIRI());
				System.out.println("\tId: " + fc.getId());
				System.out.println("\tLabel: " + fc.getLabel());
				System.out.println("\tSearchString: " + fc.getSearchString());
				System.out.println("\tString: " + fc.getString());
			}

		}

		term = "leaf";
		ArrayList<EntityProposals> eps = client.searchStrucutre("leaf");
		if (eps != null) {
			for (EntityProposals ep : eps) {
				for (Entity e : ep.getProposals()) {
					System.out.println(term + ": ");
					System.out.println("\tClassIRI: " + e.getClassIRI());
					System.out.println("\tId: " + e.getId());
					System.out.println("\tLabel: " + e.getLabel());
					System.out
							.println("\tSearchString: " + e.getSearchString());
					System.out.println("\tString: " + e.getString());

					System.out.println("\tPrimaryEntityID: "
							+ e.getPrimaryEntityID());
					System.out.println("\tPrimaryEntityLabel: "
							+ e.getPrimaryEntityLabel());
					System.out.println("\tPrimaryEntityOWLClassIRI: "
							+ e.getPrimaryEntityOWLClassIRI());
					System.out.println("\tPrimaryString: "
							+ e.getPrimaryEntityString());
				}
			}
		}
	}
}
