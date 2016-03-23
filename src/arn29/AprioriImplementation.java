package arn29;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.StringTokenizer;

public class AprioriImplementation 
{
	public void init()
	{
		System.out.println("Please enter the Support Value: "); 
		sup=sc.nextDouble(); 
		System.out.println("Please enter the Confidence Value: "); 
		confidenceminimum=sc.nextDouble();
	}
	public void iterateData()throws Exception 
	{
		strongRules=new ArrayList<ArrayList<String>>(); 
		System.out.println("Please enter the name of the file to be read: ");
		fileName=sc.next();
		System.out.println("This is the file we got output of: " +fileName); 
		DataSet=new ArrayList<String>();
		int linecounter=0;
		filelinescount=0;
		do 
		{
			linecounter++;
			itemsGenerated(linecounter);
			readFrequentData(fileName);
			frequentdataCaluculate();
			ArrayList<String> tempData=new ArrayList<String>(DataSet); 
			strongRules.add(tempData);
		
		}while(DataSet.size()>1);
		CalculateConfidence(); 
		DataSet.clear();
	}
	public void CalculateConfidence() 
	{
		String[] spliter=null;
		for(int i=0;i<strongRules.size();i++) 
		{
			DataSet=strongRules.get(i); 
			System.out.println("Frequent " + (i+1) + "-itemsets"); 
			for(int items=0;items<DataSet.size();items++)
			{
				String key=DataSet.get(items); 
				Hashtable<String,Integer> confidence=supData.get(i); 
				int data=confidence.get(key);
				String rule=DataSet.get(items);
				spliter=rule.split("\\"+" ");
				double calsup=(itemcounter[i]/(double)filelinescount);
				if(spliter.length>1)
				{
					String topKey=spliter[0];
					int topValue=confidence.get(topKey);
					double confidencecalculate=((double)topValue/data); 
					if(confidencecalculate>=confidenceminimum)
					{
						System.out.println(key+"\t\t"+"Support is: "+calsup+"\t"+"\t"+"\t"+"Confidence is: "+confidencecalculate);
					}
				}
				else 
				{
					if(data>=confidenceminimum)
					{
						System.out.println(key);
					}
				}
			}
		}
	}
	public ArrayList<String> itemsGenerated(int count) 
	{
		ArrayList<String> tempDataSet=new ArrayList<String>(); 
		StringTokenizer tokenizer1=null;
		StringTokenizer tokenizer2=null;
		String stringA=null;
		String stringB=null; 
		if(count==1)
		{
			for(int i=0;i<items.length;i++) 
			{
				tempDataSet.add(items[i]);
			}
		}
		else if(count==2)
		{
			for(int i=0;i<items.length;i++)
			{
				for(int j=(i+1);j<items.length;j++)
				{
					if(i!=j)
						tempDataSet.add(items[i]+" "+items[j]);
				}
			}
		}
		else
		{
			tempDataSet=new ArrayList<String>();
			for(int items=0; items<DataSet.size(); items++)
			{
				for(int temp=items+1; temp<DataSet.size(); temp++)
				{
					stringA = "";
					stringB = "";
					tokenizer1 = new StringTokenizer(DataSet.get(items)); 
					tokenizer2 = new StringTokenizer(DataSet.get(temp));
					for(int s=0; s<count-2; s++)
					{
						stringA = stringA + " " + tokenizer1.nextToken(); 
						stringB = stringB + " " + tokenizer2.nextToken();
					}
					if(stringB.compareToIgnoreCase(stringA)==0)
						tempDataSet.add((stringA + " " + tokenizer1.nextToken() + " " + tokenizer2.nextToken()).trim());
				}
			}
		}
		DataSet.clear();
		DataSet = new ArrayList<String>(tempDataSet); 
		tempDataSet.clear();
		return DataSet;
	}
	public void readFrequentData(String fileName)throws Exception
	{
		BufferedReader br=null;
		br=new BufferedReader(new FileReader(fileName)); 
		String stline=null;
		filesplitkey=",";
		StringTokenizer fileToken=null;
		itemcounter = new int[DataSet.size()];
		Hashtable<String, String> hashdata=new Hashtable<String, String>();
		while((stline=br.readLine())!=null)
		{
			hashdata=new Hashtable<String, String>(); 
			fileToken = new StringTokenizer(stline, filesplitkey); 
			for(int j=0; j<items.length; j++)
			{	
				if(fileToken.hasMoreTokens())
				{
					String token=fileToken.nextToken(); 
					hashdata.put(token, token);
				}
			}
			for(int items=0; items<DataSet.size(); items++)
			{
				boolean match = false;
				StringTokenizer st = new StringTokenizer(DataSet.get(items)); 
				int count=0;
				int xcount=0;
				while(st.hasMoreTokens())
				{
					String token=st.nextToken(); 
					if(hashdata.get(token)!=null)
					{
						match=true;
					}
					else
						match=false;
					if(!match)
						break;
				}
				if(match)
				{
					itemcounter[items]++;
				}
			}
			filelinescount++;		
		}		
	}			
	public void frequentdataCaluculate()
	{
		ArrayList<String> frequentItems=new ArrayList<String>(); 
		for(int i=0; i<DataSet.size(); i++)
		{
			if((itemcounter[i]/(double)filelinescount)>=sup)
			{
				frequentItems.add(DataSet.get(i)); 
				confidence.put(DataSet.get(i),itemcounter[i]);
			}
		}
		supData.add(confidence);
		DataSet.clear();
		DataSet = new ArrayList<String>(frequentItems);
		frequentItems.clear();
	}
	public static ArrayList<String> DataSet=null;
	public static String[] items={"Ice-cream","Eggs","Tomatoes","Bread","Cookies","Doughnuts","Bodywash","Milk","Sauce","Shampoo"};
	public static String filesplitkey=","; 
	public static String fileName="Input1.txt";
	public static String[] fileNameArray={"Input1.txt","Input2.txt","Input3.txt","Input4.txt","Input5.txt"}; 
	private static double sup=0;
	private static int itemcounter[];
	private double confidenceminimum=1.0;
	private static int filelinescount=0;
		
	private static Scanner sc=new Scanner(System.in);
	ArrayList<Hashtable<String,Integer>> supData=new ArrayList<Hashtable<String,Integer>>(); 
	Hashtable<String,Integer> confidence = new Hashtable<String,Integer>();
	ArrayList<ArrayList<String>> strongRules=null;
	
	public static void main(String[] args)throws Exception 
	{
		AprioriImplementation ai=new AprioriImplementation();
		DataSet=new ArrayList<String>();
		ai.init();
		System.out.println("Please enter the number of files to be read: "); 
		int numofFiles=sc.nextInt();
		
		for(int i=0;i<numofFiles;i++)
		{
			ai.iterateData();
		}
	}
}
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				