package com.hughes.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
//import java.sql.*;
import java.util.Scanner;

import com.mysql.cj.jdbc.JdbcPreparedStatement;

public class MyjdbcConnection {
	
	private static void BankTrans() {
		Scanner scn = new Scanner(System.in);
		try {
			System.out.println("Enter the details of the transaction as asked: \n NOTE: Make sure you enter correct details");
			
			//Enter TransID;
			System.out.print("Enter valid Transaction Number:");
			String TransID=scn.nextLine();
			System.out.println();
			
			
			//Enter AccNo;
			System.out.print("Enter valid Account Number:");
			int AccNo=scn.nextInt();
			System.out.println();
			
			//Enter OldBal
			System.out.print("Enter valid Old Balance:");
			int OldBal=scn.nextInt();
			System.out.println();
			scn.nextLine();
			
			//Type of transaction(Transaction Type);
			System.out.print("Enter valid Transaction type:");
			String TransType=scn.nextLine();
			System.out.println();
			
			// TransAmt;
			System.out.print("Enter valid Transaction Amount:");
			int TransAmt=scn.nextInt();
			System.out.println();
			
			//NEwBal;
			int NewBal = (TransType.equals("W"))?(OldBal - TransAmt):(OldBal + TransAmt);
//			int NewBal=0;
//			if(TransType == "D") {
//				System.out.println("transtype == " + TransType);
//				NewBal = OldBal+TransAmt;
//				System.out.println("newbal  == " + NewBal + " add");
//			}
//			else{
//				System.out.println("transtype == " + TransType);
//				NewBal = OldBal - TransAmt;
//				System.out.println("newbal  == " + NewBal + " sub");
//			}
			
			//TransStat;
			String TransStat = (NewBal<0)?"Invalid":"Valid";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","Tharun@123");
			JdbcPreparedStatement stmt = (JdbcPreparedStatement) con.prepareStatement("INSERT INTO BankTrans (TransID,AccNo,OldBal,TransType,TransAmt,NewBal,TransStat) VALUES (?,?,?,?,?,?,?)");
			stmt.setString(1, TransID);
			stmt.setInt(2, AccNo);
			stmt.setInt(3, OldBal);
			stmt.setString(4,TransType);
			stmt.setInt(5,TransAmt);
			stmt.setInt(6, NewBal);
			stmt.setString(7,TransStat);
			
			int i = stmt.executeUpdate();
			System.out.println("Updated values are: " + i + "\n" + "Sucessfully updates all the details updated!!");
			
			//updating valid and invalid transactions;
//			(TransStat=="Invalid")?InvalidTrans():ValidTrans();
			if(TransStat.equals("Valid")) {
				ValidTrans(TransID,TransType,TransAmt,TransStat);
			}
			else {
				InvalidTrans(TransID,TransType,TransAmt,TransStat);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private static void print_BankTrans() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","Tharun@123");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT TransID,AccNo,OldBal,TransType,TransAmt,NewBal,TransStat from  BankTrans");
			while(rs.next()) {
				String transid = rs.getString(1);
				int accno = rs.getInt(2);
				int oldbal = rs.getInt(3);
				String transtype = rs.getString(4);
				int transamt = rs.getInt(5);
				int newbal = rs.getInt(6);
				String transstat = rs.getString(7);
				System.out.println(transid + " " + accno + " " + oldbal + " " + transtype + " " + transamt + " " + newbal + " " + transstat );
//				con.commit();
//				con.close();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// ValidTrans Table update
	private static void ValidTrans(String TransID,String TransType,int TransAmt,String Validity) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","Tharun@123");
			JdbcPreparedStatement stmt = (JdbcPreparedStatement) con.prepareStatement("INSERT INTO ValidTrans (TransID,TransType,TransAmt,Validity) VALUES (?,?,?,?)");
			stmt.setString(1, TransID);
			stmt.setString(2,TransType);
			stmt.setInt(3,TransAmt);
			stmt.setString(4,Validity);
			int i = stmt.executeUpdate();
//			System.out.println("Updated ValidTrans Table also with :" +i);
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	
	
	//InvalidTrans Table update
	private static void InvalidTrans(String TransID,String TransType,int TransAmt,String Validity) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","Tharun@123");
			JdbcPreparedStatement stmt = (JdbcPreparedStatement) con.prepareStatement("INSERT INTO InValidTrans (TransID,TransType,TransAmt,Validity) VALUES (?,?,?,?)");
			stmt.setString(1, TransID);
			stmt.setString(2,TransType);
			stmt.setInt(3,TransAmt);
			stmt.setString(4,Validity);
			int i = stmt.executeUpdate();
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}

	
	//printing validtransactions table;
	private static void print_ValidTrans() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","Tharun@123");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT TransID,TransType,TransAmt,Validity from ValidTrans");
			while(rs.next()) {
				String transid = rs.getString(1);
				String transtype = rs.getString(2);
				int transamt = rs.getInt(3);
				String transstat = rs.getString(4);
				System.out.println(transid + " " + transtype + " " + transamt + " " + transstat );
//				con.commit();
//				con.close();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	//printing InValidTrans table;
	private static void print_InValidTrans() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","Tharun@123");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT TransID,TransType,TransAmt,Validity from InValidTrans");
			while(rs.next()) {
				String transid = rs.getString(1);
				String transtype = rs.getString(2);
				int transamt = rs.getInt(3);
				String transstat = rs.getString(4);
				System.out.println(transid + " " + transtype + " " + transamt + " " + transstat );
//				con.commit();
//				con.close();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","Tharun@123");
//			Statement stmt = con.createStatement();
//			ResultSet rs = stmt.executeQuery("SELECT TransID,AccNo,OldBal,TransType,TransAmt,NewBal,TransStat from  BankTrans");
//			while(rs.next()) {
//				String transid = rs.getString(1);
//				int accno = rs.getInt(2);
//				int oldbal = rs.getInt(3);
//				String transtype = rs.getString(4);
//				int transamt = rs.getInt(5);
//				int newbal = rs.getInt(6);
//				String transstat = rs.getString(7);
//				System.out.println(transid + " " + accno + " " + oldbal + " " + transtype + " " + transamt + " " + newbal + " " + transstat );
//				con.commit();
//				con.close();
//			}
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
		Scanner sc = new Scanner(System.in);
		System.out.println("Check the following details and enter the option stating with the number: ");
		System.out.println("1: To enter the details in the database.");
		System.out.println("2: View the BankTrans Database.");
		System.out.println("3: View the Invalid Transaction Details");
		System.out.println("4: View the Valid Transaction Details");
		System.out.println("-1: To Exit from the Transaction");
		int i = sc.nextInt();
		while(i!=-1) {
			if(i==1) {
				// function call to insert the details;
				BankTrans();
			}
			else if(i==2) {
				//function call to view the bank trans table;
				print_BankTrans();
			}
			else if(i==3) {
				//function call to view the invalid trans table;
				print_ValidTrans();
			}
			else if(i==4) {
				//function call to view the valid trans table;
				print_InValidTrans();
			}
			else {
				System.out.println("Enter valid details");
			}
			i = sc.nextInt();
		}
		System.out.println("Thanks for choosing us!!!");
	}

}
