/**
 * 
 */
package edu.arizona.sirls.ontology_lookup.search;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;


import org.semanticweb.owlapi.model.OWLClass;


import edu.arizona.sirls.ontology_lookup.data.SimpleEntity;
import edu.arizona.sirls.ontology_lookup.owlaccessor.OWLAccessorImpl;
import edu.arizona.sirls.ontology_lookup.utilities.Utilities;

/**
 * @author Hong Cui
 *
 */
public class SearchPartOfChain {
	private ArrayList<SimpleEntity> chain = new ArrayList<SimpleEntity>();
	private static Hashtable<OWLClass, OWLClass> partofcache = new Hashtable<OWLClass, OWLClass>();
	private OWLAccessorImpl api;
	
	/**
	 * 
	 */
	public SearchPartOfChain(String ontologyIRI, String ontoFilePath) {		
		if(Utilities.ping(ontologyIRI, 200)){
			api = new OWLAccessorImpl(ontologyIRI, new ArrayList<String>());
		}else{
			api = new OWLAccessorImpl(new File(ontoFilePath), new ArrayList<String>());
		}
	}

	public void search(String partIRI){
		if(api!=null){
			Set<OWLClass> parents = api.getClassesWithPart(partIRI);
			//take the first class at this time
			if(parents.size()>0){
				OWLClass p = parents.iterator().next();
				SimpleEntity e = new SimpleEntity();
				e.setClassIRI(p.getIRI().toString());
				e.setLabel(api.getLabel(p));
				e.setDef(api.getDefinition(p));
				chain.add(e);
				search(p.getIRI().toString());
			}else{
				return;
			}
			
		}
	}
	
	public ArrayList<SimpleEntity> getParentChain(){
		return chain;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String ontologyiri = "http://purl.obolibrary.org/obo/po.owl";
		String ontofilepath = "C:/Users/updates/CharaParserTest/Ontologies/po.owl";
		SearchPartOfChain spoc = new SearchPartOfChain(ontologyiri, ontofilepath);
		String partIRI = "http://purl.obolibrary.org/obo/PO_0009032"; //petal
		spoc.search(partIRI);
		ArrayList<SimpleEntity> chain = spoc.getParentChain();
		System.out.println("parent organ in order: ");
		for(SimpleEntity e: chain){
			System.out.println(e.getLabel());
			System.out.println(e.getClassIRI());
			System.out.println(e.getDef());
		}
		
	}

}
