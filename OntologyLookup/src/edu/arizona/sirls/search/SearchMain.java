/**
 * 
 */
package edu.arizona.sirls.search;

import java.util.ArrayList;
import java.util.Hashtable;

import edu.arizona.sirls.data.Entity;
import edu.arizona.sirls.data.EntityProposals;
import edu.arizona.sirls.data.FormalConcept;
import edu.arizona.sirls.knowledge.ELKReasoner;
import edu.arizona.sirls.knowledge.TermOutputerUtilities;

/**
 * @author Hong Cui
 *
 */
public class SearchMain {

	//the quality ontology
	public static String pato;
	//the spatial ontology
	public static String bspo;
	//the entity ontology
	public static String eonto;
	public static TermOutputerUtilities ontoutil;
	public static ELKReasoner elk;
	//the file name of the entity ontology
	public static String entityonto = "uberon";
	public static Hashtable<String, String> ontoURLs = new Hashtable<String, String>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//search: "quality" C:/Users/updates/CharaParserTest/Ontologies/charaparser_eval ext bspo pato "red"/ 
		//result: red:red:http://purl.obolibrary.org/obo/PATO_0000322
		//Search:"entity" C:/Users/updates/CharaParserTest/Ontologies ext bspo pato "condyle of femur"
		//result: condyle of femur:condyle of femur:http://purl.obolibrary.org/obo/UBERON_0009980
		//Search:"entity" C:/Users/updates/CharaParserTest/Ontologies ext bspo pato "femur condyle"
		//result: femur condyle:condyle of femur:http://purl.obolibrary.org/obo/UBERON_0009980
		
//		String type = args[0]; //search type: quality or entity (i.e., structure)
//		String ontologydir = args[1]; //
//		String eonto = args[2];
//		String bspo = args[3];
//		String pato = args[4];
//		String term = args[5];
		String rel = "part_of";
		
		String type = "entity";
		String ontologydir = "D:\\Work\\Code\\OTOLiteForETC\\OntologyOwlFiles";
		String eonto = "ext";
		String bspo = "bspo";
		String pato = "pato";
		String term = "femur condyle";
		
		
		//get ontology filepaths and urls

		SearchMain.entityonto = eonto;
		SearchMain.eonto = ontologydir+"/"+eonto+".owl";
		SearchMain.bspo = ontologydir+"/"+bspo+".owl";
		SearchMain.pato = ontologydir+"/"+pato+".owl";
		
		if(eonto.compareToIgnoreCase("po")==0){
			SearchMain.ontoURLs.put(SearchMain.eonto, "http://purl.obolibrary.org/obo/po.owl");
		}else if(eonto.compareToIgnoreCase("hao")==0){
			SearchMain.ontoURLs.put(SearchMain.eonto, "http://purl.obolibrary.org/obo/hao.owl");
		}else if(eonto.compareToIgnoreCase("poro")==0){
			SearchMain.ontoURLs.put(SearchMain.eonto, "http://purl.obolibrary.org/obo/poro.owl");
		}else if(eonto.compareToIgnoreCase("ext")==0){
			SearchMain.ontoURLs.put(SearchMain.eonto, "purl.obolibrary.org/obo/uberon/ext.owl"); //this is my test case
		}
		SearchMain.ontoURLs.put(SearchMain.bspo, "http://purl.obolibrary.org/obo/bspo.owl");
		SearchMain.ontoURLs.put(SearchMain.pato, "http://purl.obolibrary.org/obo/pato.owl");
		
		//now load ontologies
		SearchMain.ontoutil = new TermOutputerUtilities();
		
		try {
			//SearchMain.elk = new ELKReasoner(new File(SearchMain.eonto), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//search terms
		System.out.println("search "+term +" as "+type+" in "+SearchMain.eonto+";"+SearchMain.bspo+";"+SearchMain.pato);
		if(type.compareTo("quality")==0){
			TermSearcher ts = new TermSearcher();
			ArrayList<FormalConcept> quality = ts.searchTerm(term,
					"quality");
			if(quality!=null){
				for (FormalConcept fc : quality)
					System.out.println(term+":"+fc.getLabel()+fc.getClassIRI());
			}
			
			
		}else{
			EntitySearcherOriginal eso = new EntitySearcherOriginal();
			ArrayList<EntityProposals> eps = eso.searchEntity(term, "", term+"+"+"", rel);
			if(eps!=null){
				for(EntityProposals ep: eps )
					for(Entity e: ep.getProposals()){
						System.out.println(term+":"+e.getLabel()+":"+e.getClassIRI());
					}
			}
			
			eps = eso.searchEntity(term, "", term+"+"+"", rel);
			if(eps!=null){
				for(EntityProposals ep: eps )
					for(Entity e: ep.getProposals()){
						System.out.println(term+":"+e.getLabel()+":"+e.getClassIRI());
					}
			}
		}
	}

}
