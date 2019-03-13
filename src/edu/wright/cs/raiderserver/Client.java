/*
 * Copyright (C) 2019
 *
 *
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package edu.wright.cs.raiderserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author lukeg
 *
 */
public class Client extends Thread {

	private PrintWriter writer;
	private BufferedReader reader;
	
	private volatile Queue<String> outMessages;
	private volatile Queue<String> inMessages; 
	
	public Client(PrintWriter writer, BufferedReader reader) {
		this.writer = writer;
		this.reader = reader;
		
		outMessages = new LinkedBlockingQueue<>();
		inMessages = new LinkedBlockingQueue<>();
	}
	
	public void run() {
		while (true) {
			try {
				
				while (reader.ready()) {
					inMessages.add(reader.readLine());
				}
				
				while (!outMessages.isEmpty()) {
					String mString = outMessages.poll();
					writer.println(mString);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected String getMessage() {
		return inMessages.poll();
	}
	
	protected void addMessage(String message) {
		outMessages.add(message);
	}
}
