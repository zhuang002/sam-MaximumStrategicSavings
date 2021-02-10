import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

	static int numPlanets=0;
	static int numCities=0;
	static int numCityPaths=0;
	static int numPlanetPaths=0;
	
	
	static ArrayList<Path> allPaths=new ArrayList();
	
	static int[] cityGroups=null;
	static int numOfCityGroups=0;
	static int[] planetGroups=null;
	static int numOfPlanetGroups=0;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadInput();
		Preprocess();
		int minPathLength=Kruscal();
		System.out.println(originalPathLength()-minPathLength);
	}


	private static int originalPathLength() {
		// TODO Auto-generated method stub
		int sumOfPaths=0;
		for (Path path:allPaths) {
			if (path.type==0) { // is city path
				sumOfPaths+=numPlanets*path.length;
			} else {
				sumOfPaths+=numCities*path.length;
			}
		}
		return sumOfPaths;
	}


	private static int Kruscal() {
		// TODO Auto-generated method stub
		int sumOfPathLength=0;
		
		for (Path path:allPaths) {
			int group1=getNodeGroup(path.node1,path.type);
			int group2=getNodeGroup(path.node2,path.type);
			if (group1==group2)
				continue;
			// 2 nodes are not in the same group
			if (path.type==0) { // the path type is city to city
				sumOfPathLength=numOfPlanetGroups*path.length;
				numOfCityGroups--;
			} else { // the path type is planet to planet;
				sumOfPathLength=numOfCityGroups*path.length;
				numOfPlanetGroups--;
			}
			mergeGroups(group1,group2,path.type);
		}
		return sumOfPathLength;
	}


	private static void mergeGroups(int group1, int group2, int type) {
		// TODO Auto-generated method stub
		int[] groups=null;
		if (type==0) {
			groups=cityGroups;
		} else {
			groups=planetGroups;
		}
		
		for (int i=0;i<groups.length;i++) {
			if (groups[i]==group2) {
				groups[i]=group1;
			}
		}
	}


	private static int getNodeGroup(int node, int type) {
		// TODO Auto-generated method stub
		if (type==0) return cityGroups[node];
		else return planetGroups[node];
	}


	private static void Preprocess() {
		// TODO Auto-generated method stub
		Collections.sort(allPaths,(x,y)->x.length-y.length);
	}


	private static void ReadInput() {
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(System.in);
		numPlanets=sc.nextInt();
		numCities=sc.nextInt();
		numCityPaths=sc.nextInt();
		numPlanetPaths=sc.nextInt();
		
		for (int i=0;i<numCityPaths;i++) {
			Path path=new Path(0,sc.nextInt(),sc.nextInt(),sc.nextInt());
			allPaths.add(path);
		}
		
		for (int i=0;i<numPlanetPaths;i++) {
			Path path=new Path(1,sc.nextInt(),sc.nextInt(),sc.nextInt());
			allPaths.add(path);
		}
		
		cityGroups=new int[numCities];
		planetGroups=new int[numPlanets];
		
		for (int i=0;i<numCities;i++) {
			cityGroups[i]=i;
		}
		numOfCityGroups=numCities;
		
		for (int i=0;i<numPlanets;i++) {
			planetGroups[i]=i;
		}
		numOfPlanetGroups=numPlanets;
	}

}

class Path {
	
	public Path(int t,int n1,int n2,int l) {
		this.type=t;
		this.node1=n1;
		this.node2=n2;
		this.length=l;
	}
	
	int type=0; // 0 for city to city, 1 for planet to planet;
	int node1;
	int node2;
	int length=0;
}
