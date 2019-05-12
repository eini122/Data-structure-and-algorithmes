
public class Edge {
	private int U;
	private int V;
	private int weight;
	private String U_name;
	private String V_name;
	public Edge(int U, int V, int weight, String U_name, String V_name) {
		this.U = U;
		this.V = V;
		this.weight = weight;
		this.U_name = U_name;
		this.V_name = V_name;
	}
	public int getU() {
		return U;
	}
	public int getV() {
		return V;
	}
	public int getWeight() {
		return weight;
	}
	public String getU_name() {
		return U_name;
	}
	public String getV_name() {
		return V_name;
	}
}
