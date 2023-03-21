// MADIS_TimeSeries_InputFilter_JPanel - panel to filter time series queries

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

package org.openwaterfoundation.tstool.plugin.madis.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openwaterfoundation.tstool.plugin.madis.dao.TimeSeriesCatalog;
import org.openwaterfoundation.tstool.plugin.madis.datastore.MADISDataStore;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilter_JPanel;
import RTi.Util.Message.Message;
import RTi.Util.String.StringUtil;

/**
This class is an input filter for querying MADIS web services.
*/
@SuppressWarnings("serial")
public class MADIS_TimeSeries_InputFilter_JPanel extends InputFilter_JPanel {

	/**
	Test datastore, for connection.
	*/
	private MADISDataStore datastore = null;

	/**
	Constructor for case when no datastore is configured - default panel.
	@param label label for the panel
	*/
	public MADIS_TimeSeries_InputFilter_JPanel( String label ) {
		super(label);
	}

	/**
	Constructor.
	@param dataStore the data store to use to connect to the test database.  Cannot be null.
	@param numFilterGroups the number of filter groups to display
	*/
	public MADIS_TimeSeries_InputFilter_JPanel( MADISDataStore dataStore, int numFilterGroups ) {
	    super();
	    this.datastore = dataStore;
	    if ( this.datastore != null ) {
	        setFilters ( numFilterGroups );
	    }
	}

	/**
	Set the filter data.  This method is called at setup and when refreshing the list with a new subject type.
	For all cases, use the InputFilter constructor "whereLabelPersistent" to ensure that the TSTool ReadMADIS command
	will show a nice 
	*/
	public void setFilters ( int numFilterGroups ) {
		String routine = getClass().getSimpleName() + ".setFilters";
		
		// Read the data to populate filter choices.

		List<TimeSeriesCatalog> tscatalogList = new ArrayList<>();
		try {
			// By default all time series are included in the catalog:
			// - this allows providing query filters that are found in the time series list
			// - use the saved global data rather than rereading to improve performance
			tscatalogList = datastore.getTimeSeriesCatalog (false);
		}
		catch ( Exception e ) {
			Message.printWarning(2, routine, "Exception reading the MADIS time series list");
			Message.printWarning(2, routine, e);
		}
		
		// The internal names for filters match the /tscatalog web service query parameters.
		// TODO smalers 2020-01-24 add more filters for points, point type, etc. as long as the web service API supports.

	    List<InputFilter> filters = new ArrayList<>();

	    // Always add blank to top of filter
	    filters.add(new InputFilter("", "", StringUtil.TYPE_STRING, null, null, false)); // Blank.

	    // Loop through the time series catalog records and extract unique values for filters.
	    List<String> stationIdChoices = new ArrayList<>();
	    List<String> stationNameChoices = new ArrayList<>();
	    List<String> stationNoChoices = new ArrayList<>();
	    List<String> stationParameterNameChoices = new ArrayList<>();
	    List<String> tsIdChoices = new ArrayList<>();
	    List<String> tsNameChoices = new ArrayList<>();
	    List<String> tsPathChoices = new ArrayList<>();
	    List<String> tsShortNameChoices = new ArrayList<>();
	    String stationId = null;
	    String stationName = null;
	    String stationNo = null;
	    String stationParameterName = null;
	    String tsId = null;
	    String tsName = null;
	    String tsShortName = null;
	    String tsPath = null;
	    boolean found = false;
	    for ( TimeSeriesCatalog tscatalog : tscatalogList ) {
	    	// Get the values for lists.
	    	stationId = "" + tscatalog.getStationId();
	    	stationName = tscatalog.getStationName();
	    	stationNo = tscatalog.getStationNo();

	    	stationParameterName = tscatalog.getStationParameterName();

	    	tsId = "" + tscatalog.getTsId();
	    	tsName = tscatalog.getTsName();
	    	tsPath = tscatalog.getTsPath();
	    	tsShortName = tscatalog.getTsShortName();
	    	
	    	// Only add if not already in the lists.
	    	found = false;
	    	for ( String stationId0 : stationIdChoices ) {
	    		if ( stationId.equals(stationId0) ) {
	    			found = true;
	    			break;
	    		}
	    	}
	    	if ( !found ) {
	    		stationIdChoices.add("" + stationId);
	    	}

	    	found = false;
	    	for ( String stationName0 : stationNameChoices ) {
	    		if ( stationName.equals(stationName0) ) {
	    			found = true;
	    			break;
	    		}
	    	}
	    	if ( !found ) {
	    		stationNameChoices.add(stationName);
	    	}

	    	found = false;
	    	for ( String stationNo0 : stationNoChoices ) {
	    		if ( stationNo.equals(stationNo0) ) {
	    			found = true;
	    			break;
	    		}
	    	}
	    	if ( !found ) {
	    		stationNoChoices.add(stationNo);
	    	}

	    	found = false;
	    	for ( String stationParameterName0 : stationParameterNameChoices ) {
	    		if ( stationParameterName.equals(stationParameterName0) ) {
	    			found = true;
	    			break;
	    		}
	    	}
	    	if ( !found ) {
	    		stationParameterNameChoices.add(stationParameterName);
	    	}

	    	found = false;
	    	for ( String tsId0 : tsIdChoices ) {
	    		if ( tsId.equals(tsId0) ) {
	    			found = true;
	    			break;
	    		}
	    	}
	    	if ( !found ) {
	    		tsIdChoices.add("" + tsId);
	    	}

	    	found = false;
	    	for ( String tsName0 : tsNameChoices ) {
	    		if ( tsName.equals(tsName0) ) {
	    			found = true;
	    			break;
	    		}
	    	}
	    	if ( !found ) {
	    		tsNameChoices.add(tsName);
	    	}

	    	found = false;
	    	for ( String tsPath0 : tsPathChoices ) {
	    		if ( tsPath.equals(tsPath0) ) {
	    			found = true;
	    			break;
	    		}
	    	}
	    	if ( !found ) {
	    		tsPathChoices.add(tsPath);
	    	}

	    	found = false;
	    	for ( String tsShortName0 : tsShortNameChoices ) {
	    		if ( tsShortName.equals(tsShortName0) ) {
	    			found = true;
	    			break;
	    		}
	    	}
	    	if ( !found ) {
	    		tsShortNameChoices.add(tsShortName);
	    	}
	    }

	    Collections.sort(stationIdChoices,String.CASE_INSENSITIVE_ORDER);
	    InputFilter filter = new InputFilter("Station - ID",
	        "station_id", "stationId", "station_id",
	        StringUtil.TYPE_INTEGER, stationIdChoices, stationIdChoices, false);
	    filter.removeConstraint(InputFilter.INPUT_GREATER_THAN);
	    filter.removeConstraint(InputFilter.INPUT_GREATER_THAN_OR_EQUAL_TO);
	    filter.removeConstraint(InputFilter.INPUT_LESS_THAN);
	    filter.removeConstraint(InputFilter.INPUT_LESS_THAN_OR_EQUAL_TO);
	    filters.add(filter);

	    Collections.sort(stationNameChoices,String.CASE_INSENSITIVE_ORDER);
	    filters.add(new InputFilter("Station - Name",
            "station_name", "stationName", "station_name",
            StringUtil.TYPE_STRING, stationNameChoices, stationNameChoices, true));

	    Collections.sort(stationNoChoices,String.CASE_INSENSITIVE_ORDER);
	    filters.add(new InputFilter("Station - Number",
            "station_no", "stationNo", "station_no",
            StringUtil.TYPE_STRING, stationNoChoices, stationNoChoices, true));

	    Collections.sort(stationParameterNameChoices,String.CASE_INSENSITIVE_ORDER);
	    filters.add(new InputFilter("Station Parameter - Name",
            "stationparameter_name", "stationParameterName", "stationparameter_name",
            StringUtil.TYPE_STRING, stationParameterNameChoices, stationParameterNameChoices, true));

	    Collections.sort(tsIdChoices,String.CASE_INSENSITIVE_ORDER);
	    filter = new InputFilter("Time series - ID",
	        "ts_id", "tsId", "ts_id",
	        StringUtil.TYPE_INTEGER, tsIdChoices, tsIdChoices, false);
	    filter.removeConstraint(InputFilter.INPUT_GREATER_THAN);
	    filter.removeConstraint(InputFilter.INPUT_GREATER_THAN_OR_EQUAL_TO);
	    filter.removeConstraint(InputFilter.INPUT_LESS_THAN);
	    filter.removeConstraint(InputFilter.INPUT_LESS_THAN_OR_EQUAL_TO);
	    filters.add(filter);

	    Collections.sort(tsNameChoices,String.CASE_INSENSITIVE_ORDER);
	    filters.add(new InputFilter("Time series - Name",
            "ts_name", "tsName", "ts_name",
            StringUtil.TYPE_STRING, tsNameChoices, tsNameChoices, true));

	    Collections.sort(tsPathChoices,String.CASE_INSENSITIVE_ORDER);
	    filters.add(new InputFilter("Time series - Path",
            "ts_path", "tsPath", "ts_path",
            StringUtil.TYPE_STRING, tsPathChoices, tsPathChoices, true));

	    Collections.sort(tsShortNameChoices,String.CASE_INSENSITIVE_ORDER);
	    filters.add(new InputFilter("Time series - Name (short)",
            "ts_shortname", "tsShortName", "ts_shortname",
            StringUtil.TYPE_STRING, tsShortNameChoices, tsShortNameChoices, true));

	  	setToolTipText("<html>Specify one or more input filters to limit query, will be ANDed.</html>");
	    
	    int numVisible = 14;
	    setInputFilters(filters, numFilterGroups, numVisible);
	}

	/**
	Return the data store corresponding to this input filter panel.
	@return the data store corresponding to this input filter panel.
	*/
	public MADISDataStore getDataStore ( ) {
	    return this.datastore;
	}
}
