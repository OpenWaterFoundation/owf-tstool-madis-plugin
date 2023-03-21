// MADISDataStoreFactory - class to create a MADISDataStore instance

/* NoticeStart

OWF TSTool MADIS Plugin
Copyright (C) 2023 Open Water Foundation

OWF TSTool MADIS Plugin is free software:  you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    OWF TSTool MADIS Plugin is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with OWF TSTool MADIS Plugin.  If not, see <https://www.gnu.org/licenses/>.

NoticeEnd */

package org.openwaterfoundation.tstool.plugin.madis.datastore;

import java.net.URI;

import RTi.Util.IO.PropList;
import RTi.Util.Message.Message;
import riverside.datastore.DataStore;
import riverside.datastore.DataStoreFactory;

public class MADISDataStoreFactory implements DataStoreFactory {

	/**
	Create a MADISDataStore instance.
	@param props datastore configuration properties, such as read from the configuration file
	*/
	public DataStore create ( PropList props ) {  
	    String name = props.getValue ( "Name" );
	    String description = props.getValue ( "Description" );
	    if ( description == null ) {
	        description = "";
	    }
	    String serviceRootURI = props.getValue ( "ServiceRootURI" );
	    if ( serviceRootURI == null ) {
	    	System.out.println("MADIS datastore ServiceRootURI is not defined.");
	    }
	    try {
	        DataStore ds = new MADISDataStore ( name, description, new URI(serviceRootURI), props );
	        return ds;
	    }
	    catch ( Exception e ) {
	        Message.printWarning(3,"",e);
	        throw new RuntimeException ( e );
	    }
	}
}