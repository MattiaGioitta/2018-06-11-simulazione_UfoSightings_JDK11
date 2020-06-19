package it.polito.tdp.ufo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {
	
	private SightingsDAO dao;
	private Graph<String,DefaultEdge> graph;
	private List<String> bestPath;
	private int bestLenght;
	
	public Model() {
		this.dao = new SightingsDAO();
	}

	public List<Anno> getAnni() {
		List<Anno> anni = this.dao.getAnni();
		Collections.sort(anni);
		return anni;
	}

	public void createGraph(Anno a) {
		this.graph = new SimpleDirectedGraph<>(DefaultEdge.class);
		List<Adiacenza> adiacenze = this.dao.getAdiacenze(a.getAnno());
		for(Adiacenza ad : adiacenze) {
			if(!this.graph.containsVertex(ad.getS1())) {
				this.graph.addVertex(ad.getS1());
			}
			if(!this.graph.containsVertex(ad.getS2())) {
				this.graph.addVertex(ad.getS2());
			}
			if(this.graph.getEdge(ad.getS1(), ad.getS2()) == null) {
				Graphs.addEdgeWithVertices(this.graph, ad.getS1(), ad.getS2());
			}
		}
	}

	public Integer nVertici() {
		return this.graph.vertexSet().size();
	}

	public Integer nArchi() {
		return this.graph.edgeSet().size();
	}

	public List<String> getVertici() {
		List<String> lista = new ArrayList<>();
		for(String s : this.graph.vertexSet()) {
			lista.add(s);
		}
		Collections.sort(lista);
		return lista;
	}

	public List<String> getPrecedenti(String scelto) {
		List<String> lista = new ArrayList<>();
		for(String s : Graphs.predecessorListOf(this.graph, scelto)) {
			lista.add(s);
		}
		return lista;
	}

	public List<String> getSuccessivi(String scelto) {
		List<String> lista = new ArrayList<>();
		for(String s : Graphs.successorListOf(this.graph, scelto)) {
			lista.add(s);
		}
		return lista;
	}

	public List<String> getRaggiungibili(String scelto) {
		List<String> lista = new ArrayList<>();
		GraphIterator<String,DefaultEdge> bfv = new BreadthFirstIterator<String,DefaultEdge>(this.graph,scelto);
		if(bfv.hasNext()) {
			lista.add(bfv.next());
		}
		return lista;
	}

	public void cammino(String scelto) {
		this.bestPath = new ArrayList<>();
		this.bestLenght = 0;
		List<String> parziale = new ArrayList<>();
		parziale.add(scelto);
		cerca(parziale);
	}

	private void cerca(List<String> parziale) {
		if(parziale.size()>this.bestLenght) {
			this.bestLenght = parziale.size();
			this.bestPath = new ArrayList<>(parziale);
			
		}
		
		
		for(String s : Graphs.successorListOf(this.graph, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(s)) {
				parziale.add(s);
				cerca(parziale);
				parziale.remove(parziale.size()-1);
			}
		}
		return;
	}

	public List<String> getCammino() {
		// TODO Auto-generated method stub
		return this.bestPath;
	}

}
