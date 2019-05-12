import java.util.*;
import java.io.*;
public class kruskals{
	private int totalWeight = 0;
	public kruskals() {}
	public ArrayList<Edge> kruskal(ArrayList<Edge> edges, int numVertices){
		DisjSets ds = new DisjSets(numVertices);
		PriorityQueue<Edge> pq = new PriorityQueue<>(compare);
		pq.addAll(edges);
		ArrayList<Edge> mst = new ArrayList<>();
		while(mst.size() != numVertices -1) {
			Edge e =pq.poll();	//Edge e = (u, v)
			int uset = ds.find(e.getU());
			int vset = ds.find(e.getV());
			
			if(uset != vset) {
				//Accept the edge
				mst.add(e);
				totalWeight += e.getWeight();
				ds.union(uset, vset);
			}
		}
		return mst;
	   }
	private int getTotalWeight() {
		return totalWeight;
	}
	static Comparator<Edge> compare=new Comparator<Edge>() {

		@Override
		public int compare(Edge t1,Edge t2){
			Integer x =t1.getWeight();
			Integer y = t2.getWeight();
	        return x-y;
	    }
	};
	public static void main(String[] args) throws IOException{
		File graphFile = new File("assn9_data.csv");
		Scanner in =new Scanner(graphFile);
		HashMap<String, Integer> map = new HashMap<>();
		ArrayList<Edge> edges = new ArrayList<>();
		int numVertices = 0;
		int num = 0;
		while(in.hasNextLine()) {
			int U = 0;
			int V = 0;
			int weight = 0;
			String V_name = null;
			numVertices++;
			String[] temp = in.nextLine().split(",");
			if(map.containsKey(temp[0])) {
				U = map.get(temp[0]);
			}
			else {
				map.put(temp[0], num);
				U = num;
				num++;
			}
			for(int i = 1; i < temp.length; i++) {
				if(i % 2 != 0) {
					if(map.containsKey(temp[i])) {
						V = map.get(temp[i]);
						V_name = temp[i];
					}
					else{
						map.put(temp[i], num);
						V = num;
						V_name = temp[i];
						num++;
					}
				}
				else if(i % 2 == 0){
					weight = Integer.parseInt(temp[i]);
					edges.add(new Edge(U, V, weight, temp[0], V_name));
				}
			}
		}
		in.close();
		kruskals ks = new kruskals();
		ArrayList<Edge> mst = ks.kruskal(edges, numVertices);
		for(int i = 0; i< mst.size(); i++) {
			System.out.println("From " + mst.get(i).getU_name() 
					+ "to " + mst.get(i).getV_name() + " with weight: " + mst.get(i).getWeight());
		}
		System.out.println("The total weight is: "+ks.getTotalWeight());
	}

}
