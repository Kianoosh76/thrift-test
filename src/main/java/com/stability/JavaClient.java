package com.stability;/*
		       * Licensed to the Apache Software Foundation (ASF) under one
		       * or more contributor license agreements. See the NOTICE file
		       * distributed with this work for additional information
		       * regarding copyright ownership. The ASF licenses this file
		       * to you under the Apache License, Version 2.0 (the
		       * "License"); you may not use this file except in compliance
		       * with the License. You may obtain a copy of the License at
		       *
		       *   http://www.apache.org/licenses/LICENSE-2.0
		       *
		       * Unless required by applicable law or agreed to in writing,
		       * software distributed under the License is distributed on an
		       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
		       * KIND, either express or implied. See the License for the
		       * specific language governing permissions and limitations
		       * under the License.
		       */

// Generated code
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.server.TSaslNonblockingServer;
import org.apache.thrift.transport.*;
import tutorial.*;
import shared.*;



import org.apache.thrift .TException;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import java.util.*;

import java.io.IOException;
import java.util.Random;

public class JavaClient {
	public static void main(String [] args) throws Exception {
		try {
			TNonblockingTransport transport;
			if (args[0].contains("simple")) {
				transport = new TNonblockingSocket("localhost", 9090);
				transport.open();
			}
			else {
				/*
				 * Similar to the server, you can use the parameters to setup client parameters or
				 * use the default settings. On the client side, you will need a TrustStore which
				 * contains the trusted certificate along with the public key.
				 * For this example it's a self-signed cert.
				 */
				TSSLTransportParameters params = new TSSLTransportParameters();
				params.setTrustStore("../../lib/java/test/.truststore", "thrift", "SunX509", "JKS");
				/*
				 * Get a client transport instead of a server transport. The connection is opened on
				 * invocation of the factory method, no need to specifically call open()
				 */
				throw new Exception();
	//                transport = TSSLTransportFactory.getClientSocket("localhost", 9091, 0, params);
		}

//            TProtocol protocol = new  TBinaryProtocol(transport);
		Calculator.AsyncClient client = new Calculator.AsyncClient(new TBinaryProtocol.Factory(),
				new TAsyncClientManager(),
				transport);

		perform(client);

		transport.close();
	} catch (TException | IOException x) {
		x.printStackTrace();
	}
}
	public static String getRandomString(int len) {
		String SALTCHARS = "abcdef1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < len -2) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		return "0x" + salt.toString();

	}

	private static double avg(ArrayList<Double> arr){
		return arr.stream()
			.mapToDouble(a -> a)
			.average().getAsDouble();
	}

	private static double min(ArrayList<Double> arr){
		return arr.stream()
			.mapToDouble(a -> a)
			.min().getAsDouble();
	}

	private static double max(ArrayList<Double> arr){
		return arr.stream()
			.mapToDouble(a -> a)
			.max().getAsDouble();
	}

	private static void perform(Calculator.AsyncClient client) throws TException
	{
		for (int j=0; j<10; j++){
			ArrayList<Double> ar = new ArrayList();
			for (int i=0; i<1000; i++) {
				Long tim1 = System.nanoTime();
				client.ping(getRandomString(128 * 1024), new AsyncMethodCallback<String>() {
					@Override
					public void onComplete(String s) {
						System.out.println("received " + s);
					}

					@Override
					public void onError(Exception e) {

					}
				});
				Long tim2 = System.nanoTime();
//				System.out.println((tim2-tim1)/1_000_000.0);
				if (i > 50)
					ar.add((tim2-tim1)/1_000_000.0);
				//System.out.println("ping() " + s);
//				try{
//					Thread.sleep(50);
//				} catch (Exception e){
//
//				}
			}
			System.out.printf("%f\t%f\t%f\n", min(ar), avg(ar), max(ar));
		}

		//        int sum = client.add(1,1);
		//        System.out.println("1+1=" + sum);
		//
		//        Work work = new Work();
		//
		//        work.op = Operation.DIVIDE;
		//        work.num1 = 1;
		//        work.num2 = 0;
		//        try {
		//            int quotient = client.calculate(1, work);
		//            System.out.println("Whoa we can divide by 0");
		//        } catch (InvalidOperation io) {
		//            System.out.println("Invalid operation: " + io.why);
		//        }
		//
		//        work.op = Operation.SUBTRACT;
		//        work.num1 = 15;
		//        work.num2 = 10;
		//        try {
		//            int diff = client.calculate(1, work);
		//            System.out.println("15-10=" + diff);
		//        } catch (InvalidOperation io) {
		//            System.out.println("Invalid operation: " + io.why);
		//        }
		//
		//        SharedStruct log = client.getStruct(1);
		//        System.out.println("Check log: " + log.value);
	}
}
