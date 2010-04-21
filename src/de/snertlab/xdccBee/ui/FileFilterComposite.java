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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import de.snertlab.xdccBee.messages.XdccBeeMessages;
import de.snertlab.xdccBee.tools.swt.filtertext.FilterTextComposite;

/**
 * @author holgi
 *
 */
public class FileFilterComposite extends Composite {
	
	private Text txtFilter;

	public FileFilterComposite(Composite parent, FormData formData, final FileViewer packetViewer) {
		super(parent, SWT.NONE);
		setLayoutData(formData);
		Layout layout = new GridLayout(2, true);
		setLayout(layout);
		GridData gridDataCompControls = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridDataCompControls.verticalIndent = -5;
		
		if( Application.isMac() ){
			txtFilter = new Text(this, SWT.SEARCH | SWT.ICON_CANCEL | SWT.ICON_SEARCH);
			txtFilter.setLayoutData( gridDataCompControls );		
			Listener listener = new Listener() {
				public void handleEvent(Event event) {
					if (event.detail == SWT.ICON_CANCEL) {
						packetViewer.setFileNameFilterText("");
					}
				}
			};
			txtFilter.addListener(SWT.DefaultSelection, listener);
		}else{
			FilterTextComposite filterTextComposite = new FilterTextComposite(this);
			filterTextComposite.setLayoutData(gridDataCompControls);
			txtFilter = filterTextComposite.getFilterControl();
		}
		txtFilter.setMessage(XdccBeeMessages.getString("FileFilterComposite_FILTER_DUMMY_TEXT")); //$NON-NLS-1$
		txtFilter.addKeyListener(new KeyListener() {		
			@Override
			public void keyReleased(KeyEvent e) {
				packetViewer.setFileNameFilterText(txtFilter.getText());
			}
			@Override
			public void keyPressed(KeyEvent e) {
				// Do Nothing
			}
		});
		
		new Label(this, SWT.NONE); //Spacer
	}
	
}
