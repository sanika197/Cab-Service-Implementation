package Mini_Project2;
import java.util.*;
class LocNode{                                             //node in adjacency list
	int vertex;
	int weight;
	String location;
	Driver hd;
	LocNode next;

	public LocNode() {}                                    //default constructor
	public LocNode(int v, int w, String n)                 //parameterized constructor
	{
		vertex = v;
		weight = w;
		location = n;
		next = null;
	}
}

public class graph
{                                                           //adjacency list class
	LocNode head[] = new LocNode[100];
	private static final int NO_PARENT = -1;
	int distance=0;
	String arr[] = new String[10];
	ArrayList<String> storePath = new ArrayList<>();
	public graph() 
	{
		for(int i=0;i<=5;i++) 
		{
			head[i] = new LocNode();
		}

		head[0].vertex = 0;
		head[0].location = "School";
		head[1].vertex = 1;
		head[1].location = "Airport";
		head[2].vertex = 2;
		head[2].location = "Railway Station";
		head[3].vertex = 3;
		head[3].location = "Hospital";
		head[4].vertex = 4;
		head[4].location = "Super Market";
		head[5].vertex = 5;
		head[5].location = "Housing Society";

		LocNode school1 = new LocNode(0, 9, "School");  
		LocNode school5 = new LocNode(0, 12, "School");
		LocNode airp2 = new LocNode(1, 8, "Airport");
		LocNode airp3 = new LocNode(1, 17, "Airport");
		LocNode airp5 = new LocNode(1, 10, "Airport");
		LocNode rail0 = new LocNode(2, 21, "Railway Station");
		LocNode rail3 = new LocNode(2, 5, "Railway Station");
		LocNode rail4 = new LocNode(2, 13,"Railway Station");
		LocNode hosptl = new LocNode(3, 11, "Hospital");
		LocNode market2 = new LocNode(4, 13, "Super Market");
		LocNode market3 = new LocNode(4, 16, "Super Market");
		LocNode house1 = new LocNode(5, 10, "Housing Society");

		head[0].next = hosptl;
		hosptl.next = rail0;
		head[1].next = house1;
		house1.next = school1;
		head[2].next = airp2;
		airp2.next = market2;
		head[3].next = airp3;
		airp3.next = rail3;
		rail3.next = market3;
		head[4].next = rail4;
		head[5].next = school5;
		school5.next = airp5;

		Driver d1=new Driver("Sam","MH12KT456","7123456803","Micro Cab","Petrol","123456789123");
		Driver d2=new Driver("Jay","MH12BC1203","8456123017","Sedan Cab","Diesel","789456123123");
		Driver d3=new Driver("Rahul","MH14R75","94567213046","Sedan Cab","Petrol","123789456456");
		Driver d4=new Driver("Rohit","MH12XY2301","7561230789","Micro Cab","Petrol","159159159159");
		Driver d5=new Driver("Ritiesh","MH4L80","8452073165","Micro Cab","Diesel","753753753753");
		Driver d6=new Driver("Manish","MH12CX701","8942136014","Sedan Cab","Petrol","852852852852");
		Driver d7=new Driver("Raj","MH12AB6782","9988076542","Sedan Cab","Petrol","963963963963");
		Driver d8=new Driver("Kunal","MH14MN4520","7689054321","Sedan Cab","Diesel","741741741741");


		head[0].hd=d1;
		head[0].hd.next=d2;
		head[1].hd=d3;
		head[2].hd=d4;
		head[2].hd.next=d5;
		head[4].hd=d6;
		head[5].hd=d7;
		head[5].hd.next=d8;
	}

	void setHead(Driver hd1, Driver hd2,int pickup,int drop)
	{
		head[pickup].hd=hd1;
		head[drop].hd=hd2;
	}

	void setHeadD(Driver hd1,int start)
	{
		head[start].hd=hd1;
	}

	void display() 
	{
		int i;

		System.out.println("Locations: ");
		for(i=0;i<=5;i++) {
			System.out.println((i+1)+". "+head[i].location);  
		}
		System.out.println();
	}
	//*****************************************
	//Dijkstra

	private void getDistance(int src, int dest, int dist[],int parent[])
	{
		int n = dist.length;

		for (int i = 0;i < n;i++) 
		{
			if (i != src) 
			{
				if(i==dest)
				{
					distance=dist[i];
					path(i, parent);
				}
			}
		}  
	}
	//***************************************** 

	private void path(int i,int parent[])
	{  
		if (i == NO_PARENT)
		{
			return;
		}
		path(parent[i], parent);
		System.out.print("->"+head[i].location);
		storePath.add(head[i].location);
	}
	//*****************************************

	void shortestPath(int src, int dest) 
	{
		int adjacencyMatrix[][]= new int[][]{{0,0,21,11,0,0},
			                                 {9,0,0,0,0,10},
			                                 {0,8,0,0,0,0},
                                   			 {0,17,5,0,16,0},
			                                 {0,0,13,0,0,0},
			                                 {12,10,0,0,0,0}};
			
			int n = adjacencyMatrix[0].length; //number of columns i.e. number of locations
			int dist[] = new int[n];
			boolean visited[]= new boolean[n];
			int i;
			for (i = 0; i < n;i++)
			{
				dist[i] = Integer.MAX_VALUE;
				visited[i] = false;
			}
			
	 		//Using Djkstra's Algorithm
			
			dist[src] = 0;
			int parent[] = new int[n];
			parent[src] = NO_PARENT;
			
			for (i = 1; i < n; i++)
			{
				int nearest = -1;
				int shortest = Integer.MAX_VALUE;
				
				for (i = 0;i < n;i++)
				{
					if (visited[i]==false && dist[i] < shortest) 
					{
						nearest = i; //keep track of it's index 
						shortest = dist[i];  //get shortest among the dist values
						
					}
				}
				
				visited[nearest] = true; 
				
				for (i = 0;i < n;i++) 
				{
					int edgeVal = adjacencyMatrix[nearest][i]; //get the weight of the edge between each vertex and 'nearest'
					if (edgeVal > 0 && ((shortest + edgeVal) < dist[i])) 
					{
						parent[i] = nearest;  //stores the index of previous vertex everytime
						dist[i] = shortest + edgeVal;  //update the dist array value.
					}
				}
			}
			
			getDistance(src, dest, dist, parent); 
			
	}//function shortest path close
//	void showPath()
//	{
//		Iterator<String> itr = storePath.iterator();
//		while(itr.hasNext())
//		{
//			System.out.print("->"+itr.next()); //printing path for the driver.
//		}
//		System.out.println();
//	}
}
