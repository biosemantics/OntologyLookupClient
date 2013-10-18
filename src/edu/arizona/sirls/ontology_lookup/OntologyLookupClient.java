package edu.arizona.sirls.ontology_lookup;

import java.util.ArrayList;

import edu.arizona.sirls.ontology_lookup.data.Entity;
import edu.arizona.sirls.ontology_lookup.data.EntityProposals;
import edu.arizona.sirls.ontology_lookup.data.FormalConcept;
import edu.arizona.sirls.ontology_lookup.knowledge.TermOutputerUtilities;
import edu.arizona.sirls.ontology_lookup.search.EntitySearcherOriginal;
import edu.arizona.sirls.ontology_lookup.search.SearchMain;
import edu.arizona.sirls.ontology_lookup.search.TermSearcher;

public class OntologyLookupClient {
	private String rel = "part_of";

	public OntologyLookupClient(String ontologyName, String ontologyDir,
			String dictDir) {
		SearchMain.entityonto = ontologyName;
		SearchMain.eonto = ontologyDir + "/" + ontologyName + ".owl";
		SearchMain.bspo = ontologyDir + "/bspo.owl";
		SearchMain.pato = ontologyDir + "/pato.owl";
		SearchMain.dictdir = dictDir;

		if (ontologyName.compareToIgnoreCase("po") == 0) {
			SearchMain.ontoURLs.put(SearchMain.eonto,
					"http://purl.obolibrary.org/obo/po.owl");
		} else if (ontologyName.compareToIgnoreCase("hao") == 0) {
			SearchMain.ontoURLs.put(SearchMain.eonto,
					"http://purl.obolibrary.org/obo/hao.owl");
		} else if (ontologyName.compareToIgnoreCase("poro") == 0) {
			SearchMain.ontoURLs.put(SearchMain.eonto,
					"http://purl.obolibrary.org/obo/poro.owl");
		} else if (ontologyName.compareToIgnoreCase("ext") == 0) {
			SearchMain.ontoURLs.put(SearchMain.eonto,
					"purl.obolibrary.org/obo/uberon/ext.owl");
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
		OntologyLookupClient client = new OntologyLookupClient("ext",
				"/home/sbs0457/workspace/OTOLiteForETC/OntologyOwlFiles",
				"/home/sbs0457/workspace/OTOLiteForETC/DictFiles");

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
				System.out.println("\tDef: " + fc.getDef());
				System.out.println("\tParent label: " + fc.getPLabel());
			}

		}

		term = "condyle of femur";
		ArrayList<EntityProposals> eps = client.searchStrucutre(term);
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
					System.out.println("\tDef: " + e.getDef());
					System.out.println("\tParent label: " + e.getPLabel());
				}
			}
		}
	}
}
