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
package de.snertlab.xdccBee.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import de.snertlab.xdccBee.irc.DccDownload;
import de.snertlab.xdccBee.irc.DccFileTransfer;

/**
 * @author snert
 * 
 */
public class TableItemDownload {

	private static final long KILOBYTES = 1024L;

	private Table downloadTable;
	private DccDownload dccDownload;
	private TableItem itemDownload;
	private ProgressBar bar;

	public TableItemDownload(Table downloadTable, DccDownload dccDownload) {
		dccDownload.setTableItemDownload(this);
		this.downloadTable = downloadTable;
		this.dccDownload = dccDownload;
		makeTableItem();
	}

	private void makeTableItem() {
		downloadTable.getDisplay().asyncExec(new Runnable() {
			public void run() {
				itemDownload = new TableItem(downloadTable, SWT.NONE);
				itemDownload.setText(dccDownload.getDccPacket().getSender());
				itemDownload.setText(1, dccDownload.getDccPacket().getName());
				bar = new ProgressBar(downloadTable, SWT.NONE);
				TableEditor editor = new TableEditor(downloadTable);
				editor.grabHorizontal = true;
				editor.grabVertical = true;
				editor.setEditor(bar, itemDownload, 2);
				setDisplayState(dccDownload.getState());
				itemDownload.setData(dccDownload);
			}
		});
	}

	public Display getDisplay() {
		return downloadTable.getDisplay();
	}

	public void updateFileTransferDisplay(DccFileTransfer dccFileTransfer) {
		bar.setMaximum(100);
		bar.setSelection((int) (100 * dccFileTransfer.getProgress() / dccFileTransfer
				.getSize()));
		itemDownload.setText(3, bytesToKb(dccFileTransfer.getTransferRate())
				+ " KB/s");
		itemDownload.setText(4, getEstimateTime(dccFileTransfer));
		refreshState();
	}

	private long bytesToKb(long bytes) {
		return bytes / KILOBYTES;
	}

	public String getEstimateTime(DccFileTransfer dccFileTransfer) {
		if (dccFileTransfer.getTransferRate() == 0)
			return "~";
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		long secondsTillDownloadFinished = (dccFileTransfer.getSize() - dccFileTransfer
				.getProgress()) / dccFileTransfer.getTransferRate();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, (int) secondsTillDownloadFinished);
		long diff = (cal.getTimeInMillis() - System.currentTimeMillis());
		cal.setTimeInMillis(diff);
		cal.add(Calendar.HOUR_OF_DAY, -1); // i don´t now why but duration seems
											// to be correct if 1 hour is
											// substracted
		String s1 = df.format(cal.getTime());
		return s1;
	}

	private void setDisplayState(String state_download) {
		if (DccDownload.STATE_DOWNLOAD_FINISHED.equals(state_download)
				|| DccDownload.STATE_DOWNLOAD_ABORT.equals(state_download)) {
			itemDownload.setText(3, "-");
			itemDownload.setText(4, "-");
			bar.setSelection(bar.getMaximum());
		}
		itemDownload.setText(5, state_download); // TODO: Translations of the
													// different states from
													// xdccBeeMessages
	}

	public void refreshState() {
		setDisplayState(dccDownload.getState());

	}

}
