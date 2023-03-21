// MADIS_TimeSeries_TableModel - table model for the time series catalog

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

import java.util.List;

import RTi.Util.GUI.JWorksheet_AbstractRowTableModel;

import org.openwaterfoundation.tstool.plugin.madis.datastore.MADISDataStore;
import org.openwaterfoundation.tstool.plugin.madis.dao.TimeSeriesCatalog;

/**
This class is a table model for time series header information for MADIS web resource time series.
By default the sheet will contain row and column numbers.
*/
@SuppressWarnings({ "serial", "rawtypes" })
public class MADIS_TimeSeries_TableModel extends JWorksheet_AbstractRowTableModel {
	
	/**
	Number of columns in the table model.
	*/
	private final int COLUMNS = 33;

	//public final int COL_LOCATION_ID = 0;
	public final int COL_STATION_NO = 0;
	public final int COL_STATION_NAME = 1;
	public final int COL_DATA_TYPE = 2;
	public final int COL_DATA_INTERVAL = 3;
	//public final int COL_STATISTIC = 4;
	public final int COL_DATA_UNITS = 4;
	public final int COL_SITE_ID = 5;
	public final int COL_SITE_NO = 6;
	public final int COL_SITE_NAME = 7;
	public final int COL_STATION_LONGNAME = 8;
	public final int COL_STATION_LONGITUDE = 9;
	public final int COL_STATION_LATITUDE = 10;
	//public final int COL_STATION_ELEVATION = 16;
	//public final int COL_STATION_DESCRIPTION = 17;
	public final int COL_STATION_ID = 11; // Want this to be somewhat hidden so put on the far right.
	public final int COL_STATION_PARAMETER_NAME = 12;
	public final int COL_STATION_PARAMETER_NO = 13;
	public final int COL_STATION_PARAMETER_LONGNAME = 14;
	public final int COL_TS_ID = 15;
	public final int COL_TS_NAME = 16;
	public final int COL_TS_SHORTNAME = 17;
	public final int COL_TS_SPACING = 18;
	public final int COL_TS_PATH = 19;
	public final int COL_TS_TYPE_ID = 20;
	public final int COL_TS_TYPE_NAME = 21;
	public final int COL_TS_UNIT_NAME = 22;
	public final int COL_TS_UNIT_NAME_ABS = 23;
	public final int COL_TS_UNIT_SYMBOL = 24;
	public final int COL_TS_UNIT_SYMBOL_ABS = 25;
	public final int COL_PARAMETER_TYPE_ID = 26;
	public final int COL_PARAMETER_TYPE_NAME = 27;
	public final int COL_CATCHMENT_ID = 28;
	public final int COL_CATCHMENT_NAME = 29;
	public final int COL_CATCHMENT_NO = 30;
	public final int COL_PROBLEMS = 31;
	public final int COL_DATASTORE = 32;
	
	/**
	Datastore corresponding to datastore used to retrieve the data.
	*/
	MADISDataStore datastore = null;

	/**
	Data are a list of TimeSeriesCatalog.
	*/
	private List<TimeSeriesCatalog> timeSeriesCatalogList = null;

	/**
	Constructor.  This builds the model for displaying the given KiWIS time series data.
	@param dataStore the data store for the data
	@param data the list of KiWIS TimeSeriesCatalog that will be displayed in the table.
	@throws Exception if an invalid results passed in.
	*/
	@SuppressWarnings("unchecked")
	public MADIS_TimeSeries_TableModel ( MADISDataStore dataStore, List<? extends Object> data ) {
		if ( data == null ) {
			_rows = 0;
		}
		else {
		    _rows = data.size();
		}
	    this.datastore = dataStore;
		_data = data; // Generic
		// TODO SAM 2016-04-17 Need to use instanceof here to check.
		this.timeSeriesCatalogList = (List<TimeSeriesCatalog>)data;
	}

	/**
	From AbstractTableModel.  Returns the class of the data stored in a given column.
	@param columnIndex the column for which to return the data class.
	*/
	@SuppressWarnings({ "unchecked" })
	public Class getColumnClass (int columnIndex) {
		switch (columnIndex) {
			// List in the same order as top of the class.
			case COL_CATCHMENT_ID: return Integer.class;
			case COL_PARAMETER_TYPE_ID: return Integer.class;
			case COL_SITE_ID: return Integer.class;
			case COL_STATION_ID: return Integer.class;
			case COL_STATION_LATITUDE: return Double.class;
			case COL_STATION_LONGITUDE: return Double.class;
			case COL_TS_ID: return Integer.class;
			case COL_TS_TYPE_ID: return Integer.class;
			//case COL_STATION_ELEVATION: return Double.class;
			default: return String.class; // All others.
		}
	}

	/**
	From AbstractTableMode.  Returns the number of columns of data.
	@return the number of columns of data.
	*/
	public int getColumnCount() {
		return this.COLUMNS;
	}

	/**
	From AbstractTableMode.  Returns the name of the column at the given position.
	@return the name of the column at the given position.
	*/
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
			//case COL_LOCATION_ID: return "Location ID";
			case COL_STATION_NO: return "Station Number";
			case COL_STATION_NAME: return "Station Name";
			case COL_DATA_TYPE: return "Data Type";
			case COL_DATA_INTERVAL: return "Interval";
			//case COL_STATISTIC: return "Statistic";
			case COL_DATA_UNITS: return "Units";
			case COL_SITE_ID: return "Site ID";
			case COL_SITE_NO: return "Site Number";
			case COL_SITE_NAME: return "Site Name";
			case COL_STATION_LONGNAME: return "Station Name (long)";
			case COL_STATION_LONGITUDE: return "Longitude";
			case COL_STATION_LATITUDE: return "Latitude";
			//case COL_STATION_ELEVATION: return "Elevation";
			//case COL_STATION_DESCRIPTION: return "Station Description";
			case COL_STATION_ID: return "Station ID";
			case COL_STATION_PARAMETER_NAME: return "Station Parameter Name";
			case COL_STATION_PARAMETER_NO: return "Station Parameter Number";
			case COL_STATION_PARAMETER_LONGNAME: return "Station Parameter Long Name";
			case COL_TS_ID: return "Time Series ID";
			case COL_TS_NAME: return "Time Series Name";
			case COL_TS_SHORTNAME: return "Time Series Name (short)";
			case COL_TS_SPACING: return "Time Series Spacing";
			case COL_TS_PATH: return "Time Series Path";
			case COL_TS_TYPE_ID: return "Time Series Type ID";
			case COL_TS_TYPE_NAME: return "Time Series Type Name";
			case COL_TS_UNIT_NAME: return "Time Series Unit Name";
			case COL_TS_UNIT_NAME_ABS: return "Time Series Unit Name Abs";
			case COL_TS_UNIT_SYMBOL: return "Time Series Unit Symbol";
			case COL_TS_UNIT_SYMBOL_ABS: return "Time Series Unit Symbol Abs";
			case COL_PARAMETER_TYPE_ID: return "Parameter Type ID";
			case COL_PARAMETER_TYPE_NAME: return "Parameter Type Name";
			case COL_CATCHMENT_ID: return "Catchment ID";
			case COL_CATCHMENT_NAME: return "Catchment Name";
			case COL_CATCHMENT_NO: return "Catchment Number";
			case COL_PROBLEMS: return "Problems";
			case COL_DATASTORE: return "Datastore";

			default: return "";
		}
	}

	/**
	Returns an array containing the column widths (in number of characters).
	@return an integer array containing the widths for each field.
	*/
	public String[] getColumnToolTips() {
	    String[] toolTips = new String[this.COLUMNS];
	    //toolTips[COL_LOCATION_ID] = "Location identifier, Station Number for uniqueness";
	    toolTips[COL_STATION_NO] = "Station number - unique station number within KiWIS (station_no)";
	    toolTips[COL_STATION_NAME] = "Station name (station_name)";
	    toolTips[COL_DATA_TYPE] = "Time series data type (stationparameter_no-ts_shortname)";
	    toolTips[COL_DATA_INTERVAL] = "Time series data interval (ts_spacing converted to TSTool interval)";
	    //toolTips[COL_STATISTIC] = "NOT USED - statistic for regular interval data (included in main data type)";
	    toolTips[COL_DATA_UNITS] = "Time series data value units abbreviation (ts_unit_symbol)";
	    toolTips[COL_SITE_ID] = "Site identifier - unique site identifier within KiWIS (site_id)";
	    toolTips[COL_SITE_NO] = "Site number - unique site number within KiWIS (site_no)";
	    toolTips[COL_SITE_NAME] = "Site name (site_name)";
	    toolTips[COL_STATION_LONGNAME] = "Station name, long (station_longname)";
	    toolTips[COL_STATION_LONGITUDE] = "Station longitude, decimal degrees (station_longitude)";
	    toolTips[COL_STATION_LATITUDE] = "Station latitude, decimal degrees (station_latitude)";
	    //toolTips[COL_STATION_ELEVATION] = "Station elevation";
		//toolTips[COL_STATION_DESCRIPTION] = "Station description";
	    toolTips[COL_STATION_ID] = "Station internal identifier, useful for troubleshooting (station_id)";
	    toolTips[COL_STATION_PARAMETER_NAME] = "Station parameter (stationparameter_id)";
	    toolTips[COL_STATION_PARAMETER_NO] = "Station parameter number (stationparameter_no)";
	    toolTips[COL_STATION_PARAMETER_LONGNAME] = "Station parameter long name (stationparameter_longname)";
		toolTips[COL_TS_ID] = "Time series identifier (ts_id)";
		toolTips[COL_TS_NAME] = "Time series name (ts_name)";
		toolTips[COL_TS_SHORTNAME] = "Time series name, short (ts_shortname)";
		toolTips[COL_TS_SPACING] = "Time series data value spacing (ts_spacing)";
		toolTips[COL_TS_PATH] = "Time series path as site_no/station_no/stationparameter_no/ts_shortname (ts_path)";
		toolTips[COL_TS_TYPE_ID] = "Time series type ID (type_id)";
		toolTips[COL_TS_TYPE_NAME] = "Time series type name (ts_type_name)";
		toolTips[COL_TS_UNIT_NAME] = "Time series unit name (ts_unit_name)";
		toolTips[COL_TS_UNIT_NAME_ABS] = "Time series unit name, abs (ts_unit_name_abs)";
		toolTips[COL_TS_UNIT_SYMBOL] = "Time series unit symbol (ts_unit_symbol)";
		toolTips[COL_TS_UNIT_SYMBOL_ABS] = "Time series unit symbol, abs (ts_unit_symbol_abs)";
		toolTips[COL_PARAMETER_TYPE_ID] = "Parameter type identifier (parametertype_id)";
		toolTips[COL_PARAMETER_TYPE_NAME] = "Parameter type name (parametertype_name)";
		toolTips[COL_CATCHMENT_ID] = "Catchment ID (catchment_id)";
		toolTips[COL_CATCHMENT_NAME] = "Catchment name (catchment_name)";
		toolTips[COL_CATCHMENT_NO] = "Catchment number (catchment_no)";
		toolTips[COL_PROBLEMS] = "Problems";
		toolTips[COL_DATASTORE] = "Datastore name";
	    return toolTips;
	}

	/**
	Returns an array containing the column widths (in number of characters).
	@return an integer array containing the widths for each field.
	*/
	public int[] getColumnWidths() {
		int[] widths = new int[this.COLUMNS];
	    //widths[COL_LOCATION_ID] = 11;
	    widths[COL_STATION_NO] = 11;
	    widths[COL_STATION_NAME] = 20;
	    widths[COL_DATA_TYPE] = 20;
	    widths[COL_DATA_INTERVAL] = 8;
	    //widths[COL_STATISTIC] = 8;
	    widths[COL_DATA_UNITS] = 6;
	    widths[COL_SITE_ID] = 7;
	    widths[COL_SITE_NO] = 9;
	    widths[COL_SITE_NAME] = 20;
	    widths[COL_STATION_LONGNAME] = 13;
	    widths[COL_STATION_LONGITUDE] = 9;
	    widths[COL_STATION_LATITUDE] = 9;
	    //widths[COL_STATION_ELEVATION] = 6;
		//widths[COL_STATION_DESCRIPTION] = 15;
	    widths[COL_STATION_ID] = 6;
	    widths[COL_STATION_PARAMETER_NAME] = 25;
	    widths[COL_STATION_PARAMETER_NO] = 18;
	    widths[COL_STATION_PARAMETER_LONGNAME] = 20;
	    widths[COL_TS_ID] = 10;
	    widths[COL_TS_NAME] = 20;
	    widths[COL_TS_SHORTNAME] = 20;
	    widths[COL_TS_SPACING] = 15;
	    widths[COL_TS_PATH] = 25;
	    widths[COL_TS_TYPE_ID] = 13;
	    widths[COL_TS_TYPE_NAME] = 17;
	    widths[COL_TS_UNIT_NAME] = 15;
	    widths[COL_TS_UNIT_NAME_ABS] = 18;
	    widths[COL_TS_UNIT_SYMBOL] = 17;
	    widths[COL_TS_UNIT_SYMBOL_ABS] = 20;
	    widths[COL_PARAMETER_TYPE_ID] = 12;
	    widths[COL_PARAMETER_TYPE_NAME] = 20;
	    widths[COL_CATCHMENT_ID] = 10;
	    widths[COL_CATCHMENT_NAME] = 15;
	    widths[COL_CATCHMENT_NO] = 15;
		widths[COL_PROBLEMS] = 30;
		widths[COL_DATASTORE] = 10;
		return widths;
	}

	/**
	Returns the format to display the specified column.
	@param column column for which to return the format.
	@return the format (as used by StringUtil.formatString()).
	*/
	public String getFormat ( int column ) {
		switch (column) {
			//case COL_STATION_LONGITUDE: return "%.6f";
			//case COL_STATION_LATITUDE: return "%.6f";
			//case COL_STATION_ELEVATION: return "%.2f";
			default: return "%s"; // All else are strings.
		}
	}

	/**
	From AbstractTableMode.  Returns the number of rows of data in the table.
	*/
	public int getRowCount() {
		return _rows;
	}

	/**
	From AbstractTableMode.  Returns the data that should be placed in the JTable at the given row and column.
	@param row the row for which to return data.
	@param col the column for which to return data.
	@return the data that should be placed in the JTable at the given row and column.
	*/
	public Object getValueAt(int row, int col) {
		// Make sure the row numbers are never sorted.
		if (_sortOrder != null) {
			row = _sortOrder[row];
		}

		TimeSeriesCatalog timeSeriesCatalog = this.timeSeriesCatalogList.get(row);
		switch (col) {
			// OK to allow null because will be displayed as blank.
			//case COL_LOCATION_ID: return timeSeriesCatalog.getLocId();
			case COL_STATION_NO: return timeSeriesCatalog.getStationNo();
			case COL_STATION_NAME: return timeSeriesCatalog.getStationName();
			case COL_DATA_TYPE: return timeSeriesCatalog.getDataType();
			case COL_DATA_INTERVAL: return timeSeriesCatalog.getDataInterval();
			//case COL_STATISTIC: return timeSeriesCatalog.getStatistic();
			// Data units come from the general value, which will be from point_type or rating.
			case COL_DATA_UNITS: return timeSeriesCatalog.getDataUnits();
			case COL_SITE_ID: return timeSeriesCatalog.getSiteId();
			case COL_SITE_NO: return timeSeriesCatalog.getSiteNo();
			case COL_SITE_NAME: return timeSeriesCatalog.getSiteName();
			case COL_STATION_LONGNAME: return timeSeriesCatalog.getStationLongName();
			case COL_STATION_LONGITUDE: return timeSeriesCatalog.getStationLongitude();
			case COL_STATION_LATITUDE: return timeSeriesCatalog.getStationLatitude();
			//case COL_STATION_ELEVATION: return timeSeriesCatalog.getStationElevation();
			//case COL_STATION_DESCRIPTION: return timeSeriesCatalog.getStationDescription();
			case COL_STATION_ID: return timeSeriesCatalog.getStationId();
			case COL_STATION_PARAMETER_NAME: return timeSeriesCatalog.getStationParameterName();
			case COL_STATION_PARAMETER_NO: return timeSeriesCatalog.getStationParameterNo();
			case COL_STATION_PARAMETER_LONGNAME: return timeSeriesCatalog.getStationParameterLongName();
			case COL_TS_ID: return timeSeriesCatalog.getTsId();
			case COL_TS_NAME: return timeSeriesCatalog.getTsName();
			case COL_TS_SHORTNAME: return timeSeriesCatalog.getTsShortName();
			case COL_TS_SPACING: return timeSeriesCatalog.getTsSpacing();
			case COL_TS_PATH: return timeSeriesCatalog.getTsPath();
			case COL_TS_TYPE_ID: return timeSeriesCatalog.getTsTypeId();
			case COL_TS_TYPE_NAME: return timeSeriesCatalog.getTsTypeName();
			case COL_TS_UNIT_NAME: return timeSeriesCatalog.getTsUnitName();
			case COL_TS_UNIT_NAME_ABS: return timeSeriesCatalog.getTsUnitNameAbs();
			case COL_TS_UNIT_SYMBOL: return timeSeriesCatalog.getTsUnitSymbol();
			case COL_TS_UNIT_SYMBOL_ABS: return timeSeriesCatalog.getTsUnitSymbolAbs();
			case COL_PARAMETER_TYPE_ID: return timeSeriesCatalog.getParameterTypeId();
			case COL_PARAMETER_TYPE_NAME: return timeSeriesCatalog.getParameterTypeName();
			case COL_CATCHMENT_ID: return timeSeriesCatalog.getCatchmentId();
			case COL_CATCHMENT_NAME: return timeSeriesCatalog.getCatchmentName();
			case COL_CATCHMENT_NO: return timeSeriesCatalog.getCatchmentNo();
			case COL_PROBLEMS: return timeSeriesCatalog.formatProblems();			
			case COL_DATASTORE: return this.datastore.getName();			
			default: return "";
		}
	}

}
