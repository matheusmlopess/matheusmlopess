package testeGUI;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.algorithm.TarjanStronglyConnectedComponents;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

public class LVTEST{
	
	private static int kk =0;
	
	 
	 protected String styleSheetedge =
	            "edge {" +
	            "	size: 2px;" +
	            "	stroke-color: black;" +
	            "	stroke-width: 1px;" +
	            "   stroke-mode: plain;" +
				"}" ;
	 
//	 edge.tollway { size: 2px; stroke-color: red; stroke-width: 1px; stroke-mode: plain; }
	
	
	
	public static String readLine(int line, String File){
		FileReader tempFileReader = null;
		BufferedReader tempBufferedReader = null;

		try { 
			tempFileReader = new FileReader(File); 
			tempBufferedReader = new BufferedReader(tempFileReader);
		} catch (Exception e) { }

		String returnStr = "ERROR";
		for(int i = 0; i < line - 1; i++){
			try { tempBufferedReader.readLine(); } catch (Exception e) { }
		}
		try { returnStr = tempBufferedReader.readLine(); }  catch (Exception e) { }

		return returnStr;
	}

	/**
	 * @return
	 */
	public static Graph loadGraph() {

//		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		Graph graph = new MultiGraph("Test");
		SpriteManager sman = new SpriteManager(graph);
		
//		graph.setStrict(true);
//		graph.setAutoCreate( true );

//		graph.addAttribute("layout.gravity", 2);
//		layout.weight
//		A.addAttribute("layout.frozen");
		graph.addAttribute("ui.antialias", true);
//		graph.addAttribute("ui.stylesheet", "node { size-mode: dyn-size; text-background-mode: plain; text-visibility-mode: at-zoom; text-alignment: under;} edge {fill-color:grey;}");
		graph.addAttribute("ui.stylesheet", "node { "
				+ "size-mode: dyn-size; "
				+ "text-background-mode: rounded-box;"
				+ "text-background-color: black; "
				+ "text-alignment: center; "
				+ "text-color: white; "
//				+ "text-offset: 8, 7;"
				+ "text-size:10 "
				+ ";} "
				+ "edge {fill-color:black;"
				+ "size: 2;"
				+ "text-size:8;}"
				+ "graph {padding :9px; }"
				);
		
		String auxRead ="empty";
		while (auxRead != null ){ 
			Iterable<String> pieces= Splitter.onPattern("[,]").trimResults().omitEmptyStrings().split(auxRead);

			if(auxRead!="empty") {
				String nome = Iterables.get(pieces, 0);
				String corX = Iterables.get(pieces, 1);
				String corY = Iterables.get(pieces, 2);

				graph.addNode(nome).addAttribute("xy",Double.parseDouble(corX),Double.parseDouble(corY));
			}
			kk++;	
			auxRead = readLine(kk,"C:/Users/matheus/Desktop/TESE/workspace/testeGUI/src/testeGUI/coordXY_LVTEST.txt");
		}
		kk=0;
		
		String auxRead2="empty";
		while (auxRead2 != null ){ 
			Iterable<String> pieces= Splitter.onPattern("[,]").trimResults().omitEmptyStrings().split(auxRead2);

			if(auxRead2!="empty") {
				String eleName = Iterables.get(pieces, 0);
				String FromBus = Iterables.get(pieces, 1);
				String to_Buss = Iterables.get(pieces, 2);
				String lenghLn = Iterables.get(pieces, 3);
				
				graph.addEdge(eleName,FromBus,to_Buss).addAttribute("length",Double.parseDouble(lenghLn));
			}
			kk++;	
			auxRead2 = readLine(kk,"C:/Users/matheus/Desktop/TESE/workspace/testeGUI/src/testeGUI/coordFromTo_LVTEST.txt");
		}
					

		for (Node n : graph) {

			n.addAttribute("ui.label", n.getId());
			n.addAttribute("ui.size", 13);
			Iterable<?> b =n.getEachEdge();
			
			String ss = Iterables.get(b, 0).toString();
			
	
//			("ui.style", "shape: triangle; size: 12, 12; fill-color: black; sprite-orientation: from ;");
//			("ui.style", "shape: circle; size: 12, 12; fill-color: #CCC; sprite-orientation: from ;");
//			("ui.style", "shape: triangle;size: 20, 20; fill-color: red; sprite-orientation: to;");
			
			if(n.getId().contains("SO") ) {

				Iterable<?> a =n.getEachEdge();
				String test = a.iterator().next().toString();

				if(test.contains("SO")) {
					n.addAttribute( "ui.hide" );
					sman.addSprite(b.iterator().next().toString().replace(".", "")).attachToNode(n.getId());
					sman.getSprite(b.iterator().next().toString().replace(".", "")).setAttribute("ui.style", "shape: triangle;size: 20, 20; fill-color: red; sprite-orientation: to;");
				}
				if(!test.contains("TR")) {
					sman.addSprite(test.replace(".", "")).attachToNode(n.getId());
					sman.getSprite(test.replace(".", "")).setAttribute("ui.style"," shape: circle; size: 13, 13; fill-color: #CCC; sprite-orientation: to; stroke-mode: plain; shadow-color: black; shadow-width: 3px; stroke-color: #999;");
				}
			}
			
			if(b.iterator().next().toString().contains("SO")) {
				n.addAttribute( "ui.hide" );
				sman.addSprite(b.iterator().next().toString().replace(".", "")).attachToNode(n.getId());
				sman.getSprite(b.iterator().next().toString().replace(".", "")).setAttribute("ui.style", "shape: triangle; size: 12, 12; fill-color: black; sprite-orientation: from ;");
			}

			if(b.iterator().next().toString().contains("SW")) {
				//					n.addAttribute( "ui.hide" );
				//					 sman.addSprite("S1c").attachToNode(n.getId());
			}

		}

		for (Edge e : graph.getEachEdge()) {
//			e.addAttribute("ui.label", "" + e.getNumber("length"));

						System.out.println("From--->"+e.getNode0().toString()+"  To---->"+e.getNode1().toString());
						System.out.println(e.getId().toString());

			if(e.getId().toString().contains("SW")) {
//								e.addAttribute( "ui.hide" );
						        System.out.println(e.getNode0().toString());
								System.out.println(e.getNode1().toString());
								System.out.println(e.getId().toString());
				if(e.getNode1().toString().contains("_OPEN")) {
										sman.addSprite(e.getId().toString().replace(".", "")).attachToEdge(e.getId());
										sman.getSprite(e.getId().toString().replace(".", "")).setPosition(1);
										sman.getSprite(e.getId().toString().replace(".", "")).setAttribute("ui.style"," shape: circle; size: 8, 8; fill-color: red; sprite-orientation: to; stroke-mode: plain; shadow-color: black; shadow-width: 3px; stroke-color: #999;");
										
										String swAux =e.getNode1().toString();
										String[] splitSw=swAux.split("[ _ ]");
										
//										graph.addEdge(swAux+"_aux",e.getNode1().toString(),splitSw[0].toString()).addAttribute("length",0.008);
				}
				if(!e.getNode1().toString().contains("_OPEN")) {
					sman.addSprite(e.getId().toString().replace(".", "")).attachToEdge(e.getId());
					sman.getSprite(e.getId().toString().replace(".", "")).setAttribute("ui.style"," shape: box; size: 9,9; fill-color: red; sprite-orientation: projection;");
					sman.getSprite(e.getId().toString().replace(".", "")).setPosition(0.5);
				}
			}
		}

				
		return graph;
	}


	 
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Graph g = loadGraph();
		g.display(false);
		
		Thread.sleep(10000);
		// Edge lengths are stored in an attribute called "length"
		// The length of a path is the sum of the lengths of its edges
		Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, null, "length");

		// Compute the shortest paths in g from A to all nodes
		dijkstra.init(g);
		dijkstra.setSource(g.getNode("1"));
		dijkstra.compute();

		// Print the lengths of all the shortest paths
		for (Node node : g)
			System.out.printf("%s->%s:%10.2f%n", dijkstra.getSource(), node,dijkstra.getPathLength(node));

		// Color in blue all the nodes on the shortest path form A to B
		for (Node node : dijkstra.getPathNodes(g.getNode("744"))) {
			node.addAttribute("ui.style", "fill-color: blue;");
			
			node.addAttribute("ui.label", node.getId());
			sleep();
		}
		// Color in red all the edges in the shortest path tree
		for (Edge edge : dijkstra.getTreeEdges()) {
			edge.addAttribute("ui.style", "fill-color: red;");
			edge.setAttribute("ui.class", "marked");
			sleep();
		}


		// Print the shortest path from A to B
		System.out.println(dijkstra.getPath(g.getNode("1")));

		// Build a list containing the nodes in the shortest path from A to B
		// Note that nodes are added at the beginning of the list
		// because the iterator traverses them in reverse order, from B to A
		List<Node> list1 = new ArrayList<Node>();
		for (Node node : dijkstra.getPathNodes(g.getNode("340")))
			list1.add(0, node);

		// A shorter but less efficient way to do the same thing
		List<Node> list2 = dijkstra.getPath(g.getNode("340")).getNodePath();
		System.out.println(list2);
		
		// cleanup to save memory if solutions are no longer needed
		dijkstra.clear();

		// Now compute the shortest path from A to all the other nodes
		// but taking the number of nodes in the path as its length
		dijkstra = new Dijkstra(Dijkstra.Element.NODE, null, null);
		dijkstra.init(g);
		
		dijkstra.setSource(g.getNode("1"));
		dijkstra.compute();

		// Print the lengths of the new shortest paths
		for (Node node : g)
			System.out.printf("%s->%s:%10.2f%n", dijkstra.getSource(), node,
					dijkstra.getPathLength(node));

		// Print all the shortest paths between A and F
		Iterator<Path> pathIterator = dijkstra.getAllPathsIterator(g.getNode("340"));
		while (pathIterator.hasNext())
			System.out.println(pathIterator.next());

    	}

	protected static void sleep() {
		try { Thread.sleep(20); } catch (Exception e) {}
	}

	protected String styleSheet =
			"node {" +
					"	fill-color: black;" +
					"}" +
					"node.marked {" +
					"	fill-color: red;" +
					"}";
}
