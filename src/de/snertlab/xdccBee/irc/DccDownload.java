/*
 * Project: xdccBee
 * Copyright (C) 2009 snert@snert-lab.de,
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.snertlab.xdccBee.irc;

import java.io.File;

import org.jibble.pircbot.DccFileTransfer;


/**
 * @author holgi
 *
 */

public class DccDownload {

	private DccPacket dccPacket;
	private DccFileTransfer dccFileTransfer;
	private File destinationFile;
	
	public DccDownload(DccPacket dccPacket, File destination){
		this.dccPacket = dccPacket;
		this.destinationFile = destination;
	}

	public String getKey() {
		return dccPacket.toString();
	}
	
	public void setDccFileTransfer(DccFileTransfer dccFileTransfer){
		this.dccFileTransfer = dccFileTransfer;
	}
	
	public DccFileTransfer getDccFileTransfer(){
		return dccFileTransfer;
	}
	
	public File getDestinationFile(){
		return destinationFile;
	}

	public boolean matchDccFileTransfer(DccFileTransfer dccFileTransfer) {
		//FIXME: Filename kann unterschiedlich Packet Name sein
		if( dccPacket.getSender().equals(dccFileTransfer.getNick()) && dccPacket.getName().equals(dccFileTransfer.getFile().getName())){
			return true;
		}
		return false;
	}

	public DccPacket getDccPacket() {
		return dccPacket;
	}
	

}
