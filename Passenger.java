package Mini_Project2;

import java.util.Scanner; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class Node{
	String query;
	Node left;
	Node right;

	public Node(){
		left = null;
		right = null;
	}
}

class Driver
{
	String name;
	String car_no;
	String vehicle_type;
	double revenue;
	String mob_no;
	int distance;
	String adhar_no;
	String fuel_type;
	Driver next;

	Driver(){}                  //default constructor
	Driver(String name,String car_no,String mob,String car_type,String ft, String ad)      //parameterized constructor
	{
		this.name=name;
		this.car_no=car_no;
		this.mob_no=mob;
		this.vehicle_type=car_type;
		revenue=0;  
		distance=0;
		this.fuel_type=ft;
		this.adhar_no=ad;
	}
}

class DecisionTree
{
	Node root = new Node();
	Node dec0=new Node(); 
	Node dec1=new Node();
	Node dec2=new Node();
	Node dec3=new Node();
	Node dec4=new Node();
	Node dec5=new Node();

	public DecisionTree()
	{                                                                                         //Hard-coded 3 level decision tree
		root.query = "Number of passengers for the ride less than 4 ?\n";

		root.left = dec0;
		dec0.query = "Checking Availability..";

		root.right = dec1;
		dec1.query =  "Checking Availability..";


		dec0.left = dec2;
		dec2.query = "Micro Cab";

		dec0.right = dec3;
		dec3.query = "Would you like to upgrade to sedan";

		dec1.left = dec4;
		dec4.query = "Sedan Cab";

		dec1.right = dec5;
		dec5.query = "Sorry! no cabs available..(press 0 to exit)";

		dec3.left = dec1;
		dec3.right = dec5;
	}
	Scanner sc = new Scanner(System.in);
}


class Passenger 
{
	String name;
	String phone;
	int pickup, drop;                      //locations	
	int rate = 16;                         //per km petrol price of petrol

	public Passenger()
	{                                      //default constructor
		name = "";
		phone = "";
		pickup = 0;
		drop = 0;
	}

	Scanner sc = new Scanner(System.in);
	Driver ad;
	
	//************************************************************************************************
	
	boolean validatePhone(String ph) 
	{
		Pattern pt = Pattern.compile("(0/+91)?[7-9][0-9]{9}");
		Matcher match = pt.matcher(ph);
		return (match.find() && match.group().equals(ph));
	}
	
	//*********************************************************************************************************
	void signUP() 
	{
		System.out.println("---------------Welcome to BookMyRide.com!----------------");

		System.out.println("Enter your details: ");
		System.out.print("NAME: ");
		name = sc.next();
		System.out.println("PHONE NO: ");
		phone = sc.next();

		while(!validatePhone(phone))
		{
			System.out.println("Invalid phone number!..please try again");
			System.out.println("PHONE NO: ");
			phone = sc.next();
			validatePhone(phone);
		}
	}

	graph g=new graph();
	
	//********************************************************************************************************
	
	public int checkAvailMicro() 
	{
		Driver dr=new Driver();
		dr=g.head[pickup-1].hd;
		int cntmicro=0;

		while(dr!=null)
		{
			if(dr.vehicle_type.equals("Micro Cab"))
			{
				cntmicro++;
			}
			dr=dr.next;
		}
		return cntmicro;
	}
	
	//********************************************************************************************************
	
	public int checkAvailSedan() 
	{
		Driver dr=new Driver();
		dr=g.head[pickup-1].hd;
		int cntsedan=0;

		while(dr!=null)
		{
			if(dr.vehicle_type.equals("Sedan Cab"))
			{
				cntsedan++;
			}
			dr=dr.next;
		}
		return cntsedan;
	}

	DecisionTree dt = new DecisionTree();
	
	//*******************************************************************************************************
	String returnCab() 
	{
		Node ptr=new Node();
		int ans = 0;
		String q;
		ptr = dt.root;

		do{
			System.out.println(ptr.query);
			q=ptr.query;

			if(ptr.query.equals("Sedan Cab") || ptr.query.equals("Micro Cab"))
			{
				System.out.println("Allocated--press any key to continue ");
			}
			if(ptr.query.equals("Number of passengers for the ride less than 4 ?\n"))
			{
				System.out.println("Press :\n\t1. Yes \n\t0. No");
				
			}
			if(ptr.query.equals("Would you like to upgrade to sedan"))
			{
				System.out.println("Press :\n\t1. To upgrade \n\t0. No");
			}
			if(ptr.equals(dt.dec0))
			{
				ans = checkAvailMicro();                                   //calls function in driver class
			}
			else if(ptr.equals(dt.dec1))
			{
				ans = checkAvailSedan();
			}
			else
			{                             
				ans = sc.nextInt();
			}
			if(ans >= 1) {                      
				ptr = ptr.left;
			}
			else {
				ptr = ptr.right;
			}
		}while(ptr!=null);
		return q;
	}
	
	//**********************************************************************************************************

	void Allocatdr(String c_type, int distance1)
	{
		Driver d=new Driver();
		int pos=0;
		d=g.head[pickup-1].hd;
		int flag=0;

		while(d!=null)
		{
			pos++;
			if(d.vehicle_type.equals(c_type))
			{
				flag=1;
				break;
			}
			d=d.next;
		}
		if (pos == 0 || flag == 0)
		{
			return;
		}
		else if (pos == 1)
		{
			Driver temp =g.head[pickup-1].hd; 

			System.out.println("--------------------------------------------------------");
			System.out.println("Your Driver is on his way! Details are: ");
			System.out.println("Name: " + temp.name);
			System.out.println("Contact No: " + temp.mob_no);
			System.out.println("Car Number: " + temp.car_no);
			System.out.println("--------------------------------------------------------");

			temp.distance=distance1;
			temp.revenue=distance1*rate;
			g.head[pickup-1].hd = g.head[pickup-1].hd.next;

			Driver t=g.head[drop-1].hd;

			if(t==null)
			{
				g.head[drop-1].hd=temp;
			}
			else
			{
				while(t.next!=null)
				{
					t=t.next;
				}
				t.next=temp;
			}

		}
		else if (pos == count())
		{
			if ( g.head[pickup-1].hd.next== null)
			{
				Driver temp=g.head[pickup-1].hd;
				g.head[pickup-1].hd=null;
				Driver t=g.head[drop-1].hd;
				if(t==null)
				{
					g.head[drop-1].hd=temp;       
				}
				else
				{
					while(t.next!=null)
					{
						t=t.next;
					}
					t.next=temp;
				}

				System.out.println("--------------------------------------------------------");
				System.out.println("Your Driver is on his way! Details are: ");
				System.out.println("Name: " + temp.name);
				System.out.println("Contact No: " + temp.mob_no);
				System.out.println("Car Number: " + temp.car_no);
				System.out.println("--------------------------------------------------------");

				temp.distance=distance1;
				temp.revenue=distance1*rate;
			}

			else
			{
				Driver temp= g.head[pickup-1].hd;

				while (temp.next.next != null)
				{
					temp = temp.next;
				}
				Driver temp2=temp.next;
				temp.next = null;
				Driver t=g.head[drop-1].hd;
				while(t.next!=null)
				{
					t=t.next;
				}
				t.next=temp2;

				System.out.println("--------------------------------------------------------");
				System.out.println("Your Driver is on his way! Details are: ");
				System.out.println("Name: " + temp2.name);
				System.out.println("Contact No: " + temp2.mob_no);
				System.out.println("Car Number: " + temp2.car_no);
				System.out.println("--------------------------------------------------------");

				temp2.distance=distance1;
				temp2.revenue=distance1*rate;
			}

		}
		else                                                         //node is in the middle
		{
			Driver temp1 = g.head[pickup-1].hd;
			Driver temp2 = null;
			while (pos - 1 > 1)
			{
				temp1 = temp1.next;
				pos--;
			}
			temp2 = temp1.next;
			temp1.next = temp2.next;
			Driver t=g.head[drop-1].hd;
			while(t.next!=null)
			{
				t=t.next;
			}
			t.next=temp2;

			System.out.println("--------------------------------------------------------");
			System.out.println("Your Driver is on his way! Details are: ");
			System.out.println("Name: " + temp2.name);
			System.out.println("Contact No: " + temp2.mob_no);
			System.out.println("Car Number: " + temp2.car_no);
			System.out.println("--------------------------------------------------------");

			temp2.distance=distance1;
			temp2.revenue=distance1*rate;
		}
		g.setHead(g.head[pickup-1].hd, g.head[drop-1].hd,pickup-1,drop-1);
	}
	
	//********************************************************************************************************
	
	public int count()                                                       //count no of members
	{
		Driver temp = g.head[pickup-1].hd;
		int icnt = 0;
		while (temp != null)
		{
			icnt++; 
			temp = temp.next;
		}
		return icnt;
	}
	
	//***************************************************************************************************

	void bookRide() 
	{
		System.out.println("REGISTRATION SUCCESSFULL!\n");
		g.display();
		String stat;

		System.out.print("PICK-UP LOCATION: ");
		pickup = sc.nextInt();
		System.out.print("DROP LOCATION: ");
		drop = sc.nextInt();
		stat = returnCab();
		if(stat.equals("Sorry! no cabs available..(press 0 to exit)"))
		{
			return;
		}
		System.out.println("--------------------------------------------------------");
		System.out.println("YOUR ROUTE IS :");

		g.shortestPath(pickup-1,drop-1);
		System.out.println();

		Allocatdr(stat,g.distance);
		System.out.println("");
	}
	
	//*********************************************************************************************************
	
	void makePayment() 
	{	
		int Dist;
		Dist = g.distance;                                                  // getDist();//call function to get distance of shortest path 

		System.out.println("--------------------------------------------------------");
		System.out.println(">>>PAYABLE AMOUNT Rs. "+ (Dist*rate));
		System.out.println("Thank You! Hope you enjoyed the ride!");
	}	

	float petrol=100f;
	float diesel=98.67f;
	int rateperKM=1;                                     //amount charged per km
	//**********************************************************************************************************************
	public int expOnDailyWage()
	{
		System.out.println("--------------------------------------------------------");
		Driver temp;
		int dist=0;
		int dailyWage=0;

		for(int i=0;i<6;i++)
		{
			temp=g.head[i].hd;
			while(temp!=null)
			{
				dist=dist+temp.distance;
				temp = temp.next;
			}
		}

		dailyWage=dist*rateperKM;
		System.out.println("TOTAL EXPENDETURE ON WAGES : "+ dailyWage); 
		System.out.println("--------------------------------------------------------");
		return dailyWage;
	}
	
	//******************************************************************************************************

	public double totalRevenue()
	{
		System.out.println("--------------------------------------------------------");
		Driver temp; 
		double revenue=0;  

		for(int i=0;i<6;i++)
		{
			temp=g.head[i].hd;
			while(temp!=null)
			{
				revenue=revenue+temp.revenue;
				temp = temp.next;
			}
		}

		System.out.println("TOTAL REVENUE : "+ revenue); 
		System.out.println("--------------------------------------------------------");
		return revenue;
	}
	
	//***************************************************************************************************
	
	public void expAtLocation()
	{
		System.out.println("--------------------------------------------------------");
		Driver temp;
		double revenue=0;
		double max=0;

		int maxloc=0,i;                                              //variable that stores location where max revenue is generated
		for( i=0;i<6;i++)
		{
			temp=g.head[i].hd;
			while(temp!=null)
			{
				revenue=revenue+temp.revenue;
				temp = temp.next;
			}
			if(max<revenue)
			{
				max=revenue;
				maxloc=i;
			}
		}

		System.out.println("MOST TRAVELLED LOCATION IS : " + g.head[maxloc].location);
		System.out.println("--------------------------------------------------------");
	}
	
	//***********************************************************************************************************
	
	public void profit()
	{
		double profit=totalRevenue()-(totalExpFuel()+expOnDailyWage());

		System.out.println("--------------------------------------------------------");
		System.out.println("TOTAL PROFIT : "+profit);
		System.out.println("--------------------------------------------------------");
	} 
	
	//**********************************************************************************************************
	
	public float totalExpFuel()
	{
		System.out.println("--------------------------------------------------------");

		Driver temp=new Driver();
		float sum1=0;
		float sum2=0;
		float total;

		for(int i=0;i<5;i++)
		{ 
			temp=g.head[i].hd;
			while(temp!=null)
			{
				if(temp.fuel_type.charAt(0)=='p' || temp.fuel_type.charAt(0)=='P')
				{
					sum1=sum1+temp.distance;
				}                                                                  
				if(temp.fuel_type.compareToIgnoreCase("diesel")==0)                     //if vehicle type diesel
				{
					sum2=sum2+temp.distance;
				}
				temp = temp.next;
			}  
		}

		sum1=sum1*petrol;
		sum2=sum2*diesel;
		total=sum1+sum2;

		System.out.println("TOTAL EXPENDITURE ON PETROL/ DIESEL : "+total); 
		System.out.println("--------------------------------------------------------");
		return total;
	}
	//*******************************************************************************************************************
	
	public void UpdateFuelPrice()
	{
		System.out.println("--------------------------------------------------------");

		@SuppressWarnings("resource")
		Scanner scan= new Scanner(System.in);
		System.out.print("ENTER NEW PETROL PRICE");
		petrol=scan.nextFloat();
		System.out.println();
		System.out.print("ENTER NEW DIESEL PRICE");
		diesel=scan.nextFloat();

		System.out.println();
		System.out.println("--------------------------------------------------------");
	}

	//**********************************************************************************************************************
	public void dailyWage()
	{    
		System.out.println("--------------------------------------------------------");

		Driver temp;
		@SuppressWarnings("resource")
		Scanner scan= new Scanner(System.in);
		int f1=1;
		String adh;

		do 
		{
			System.out.println("ENTER ADHAR NUMBER : ");
			adh =scan.next();                                              //adhar card number validation
			if(adh.length()!=12)
			{
				System.out.println("Invalid adhar number!!");
				f1=0;
			}
		}while(f1==0);

		for(int i=0;i<6;i++)
		{
			temp=g.head[i].hd;
			while(temp!=null)
			{
				if(temp.adhar_no.compareTo(adh)==0)
				{
					System.out.print("TODAYS WAGE : "+(temp.distance*rateperKM));
					System.out.println("--------------------------------------------------------");
					return;
				} 
				temp = temp.next;
			}  
		}
		scan.close();
	}
	
	//************************************************************************************************************
	
	public void changeWageRate()
	{
		System.out.println("--------------------------------------------------------");

		Scanner scan= new Scanner(System.in);
		System.out.print("Enter new wage rate per kilometer : ");
		rateperKM=scan.nextInt();

		System.out.println("--------------------------------------------------------");
	}
	
	//*************************************************************************************************************
	
	public void addDriver()
	{
		System.out.println("--------------------------------------------------------");

		Scanner sc1=new Scanner(System.in);
		int loc;

		do {
			g.display();
			System.out.print("Enter your location:");
			loc=sc1.nextInt();
			if(loc<1||loc>6)
			{
				System.out.println("Service is not available! Please Enter Valid Location.");
			}
		}while(loc<1||loc>6);

		Driver ptr=g.head[loc-1].hd;

		System.out.print("Enter your name:");
		String name=sc1.next();

		String mobile_no,c_no;
		int f1;

		do
		{
			f1=1;
			System.out.print("Enter mobile number:");
			mobile_no=sc1.next();
			if(mobile_no.length()!=10 || mobile_no.charAt(0)<'7')
			{
				System.out.println("Invalid mobile number. Please Enter Valid mobile number.");
				f1=0;
			}
		}while(f1!=1);

		String adhar;
		f1=1;

		do 
		{
			System.out.println("Enter adhar_no:");
			adhar=sc1.next();
			if(adhar.length()!=12)
			{
				System.out.println("Invalid adhar number!!");
				f1=0;
			}
		}while(f1==0);

		f1=1;
		do {
			System.out.print("Enter car number:");
			c_no=sc1.next();
			if(!Pattern.matches("^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$", c_no))
			{
				f1=0;    //checks car number
				System.out.println("Invalid Car Number. Please Enter Valid Car No.");
			}
			else {
				f1 = 1;
			}

		}while(f1 != 1);

		int ch=0;
		String car_type="";
		do
		{
			System.out.println("1.Micro Cab");
			System.out.println("2.Sedan Cab");
			System.out.print("Select your car type:");
			ch=sc1.nextInt();

			switch(ch)
			{
			case 1:car_type="Micro Cab";
			break;

			case 2:car_type="Sedan Cab";
			break;

			default:System.out.println("\n Invalid choice");
			break;
			}
		}while(ch!=1&&ch!=2);

		System.out.println("\n1.Petrol");
		System.out.println("2.Diesel");
		System.out.print("\nEnter fuel type:");
		int c=sc1.nextInt();

		String fuel="";
		do
		{
			switch(c)
			{
			case 1:fuel="petrol";
			break;

			case 2:fuel="diesel";
			break;

			default:System.out.println("\n Invalid choice!");
			break;
			}
		}while(c!=1&&c!=2);

		if(ptr==null)
		{
			g.head[loc-1].hd = new Driver(name,c_no,mobile_no,car_type,fuel,adhar);
		}
		else
		{
			Driver newnode=new Driver(name,c_no,mobile_no,car_type,fuel,adhar);
			while(ptr.next!=null)
			{
				ptr=ptr.next;
			}
			ptr.next=newnode;
		}

		System.out.println("Driver Registration completed !!");
		System.out.println("--------------------------------------------------------");
		g.setHeadD(g.head[loc-1].hd,loc-1);
		//sc1.close();
	}

}