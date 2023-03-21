// TimeSeriesCatalog - list of time series

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

package org.openwaterfoundation.tstool.plugin.madis.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to store time series catalog (metadata) for MADIS time series list.
 * This is a combination of standard time series properties used in TSTool and ASOS data.
 * More data may be included and shown in the table model while evaluating the web services
 * and will be removed or disabled later.
 * The types are as one would expect, whereas the 'TimeSeries' object uses strings as per web service JSON types.
 */
public class TimeSeriesCatalog {

	// General data, provided by TSTool, extracted/duplicated from KiWIS services.
	private String locId = "";
	private String dataInterval = "";
	private String dataType = "";
	private String dataUnits = ""; // From either point_type.units_abbreviated or rating_table.units_abbreviated.
	
	// Site data, listed alphabetically.
	private Integer siteId = null;
	private String siteName = "";
	private String siteNo = "";
	
	// Station data, listed alphabetically.
	//private String stationDescription = "";
	//private Double stationElevation = null;
	private Double stationLatitude = null;
	private Double stationLongitude = null;
	private Integer stationId = null;
	private String stationName = "";
	private String stationLongName = "";
	private String stationNo = null;

	// Station parameter data.
	private String stationParameterName = "";
	private String stationParameterLongName = "";
	private String stationParameterNo = null;

	// Time series metadata, listed alphabetically.
	private Integer tsId = null;
	private String tsName = "";
	private String tsPath = "";
	private String tsShortName = "";
	private String tsSpacing = "";
	private Integer tsTypeId = null;
	private String tsTypeName = "";
	private String tsUnitName = "";
	private String tsUnitNameAbs = "";
	private String tsUnitSymbol = "";
	private String tsUnitSymbolAbs = "";

	// Parameter type data, listed alphabetically.
	private Integer parameterTypeId = null;
	private String parameterTypeName = "";
	
	// Catchment.
	private Integer catchmentId = null;
	private String catchmentName = "";
	private String catchmentNo = "";
	
	// List of problems, one string per issue.
	private List<String> problems = null; // Initialize to null to save memory ... must check elsewhere when using.

	/**
	 * Has ReadTSCatalog.checkData() resulted in problems being set?
	 * This is used when there are issues with non-unique time series identifiers.
	 * For example if two catalog are returned for a stationNumId, dataType, and dataInterval,
	 * each of the tscatalog is processed in checkData().  The will each be processed twice.
	 * This data member is set to true the first time so that the 'problems' list is only set once
	 * in TSCatalogDAO.checkData().
	 */
	private boolean haveCheckDataProblemsBeenSet = false;

	/**
	 * Constructor.
	 */
	public TimeSeriesCatalog () {
	}

	/**
	 * Copy constructor.
	 * @param timeSeriesCatalog instance to copy
	 */
	public TimeSeriesCatalog ( TimeSeriesCatalog timeSeriesCatalog ) {
		// Do a deep copy by default as per normal Java conventions.
		this(timeSeriesCatalog, true);
	}

	/**
	 * Copy constructor.
	 * @param timeSeriesCatalog instance to copy
	 * @param deepCopy indicates whether an exact deep copy should be made (true)
	 * or a shallow copy that is typically used when defining a derived catalog record.
	 * For example, use deepCopy=false when copying a scaled catalog entry for a rated time series.
	 */
	public TimeSeriesCatalog ( TimeSeriesCatalog timeSeriesCatalog, boolean deepCopy ) {
		// List in the same order as internal data member list.
		this.locId = timeSeriesCatalog.locId;
		this.dataInterval = timeSeriesCatalog.dataInterval;
		this.dataType = timeSeriesCatalog.dataType;
		this.dataUnits = timeSeriesCatalog.dataUnits;

		// Site data, listed alphabetically.
		this.siteId = timeSeriesCatalog.siteId;
		this.siteName = timeSeriesCatalog.siteName;
		this.siteNo = timeSeriesCatalog.siteNo;

		// Station data, listed alphabetically.
		//this.stationDescription = timeSeriesCatalog.stationDescription;
		//this.stationElevation = timeSeriesCatalog.stationElevation;
		this.stationLatitude = timeSeriesCatalog.stationLatitude;
		this.stationLongitude = timeSeriesCatalog.stationLongitude;
		this.stationId = timeSeriesCatalog.stationId;
		this.stationName = timeSeriesCatalog.stationName;
		this.stationNo = timeSeriesCatalog.stationNo;
		
		if ( deepCopy ) {
			// Time series catalog problems.
			if ( timeSeriesCatalog.problems == null ) {
				this.problems = null;
			}
			else {
				// Create a new list.
				this.problems = new ArrayList<>();
				for ( String s : timeSeriesCatalog.problems ) {
					this.problems.add(s);
				}
			}
		}
		else {
			// Default is null problems list.
		}
	}

	/**
	 * Add a problem to the problem list.
	 * @param problem Single problem string.
	 */
	public void addProblem ( String problem ) {
		if ( this.problems == null ) {
			this.problems = new ArrayList<>();
		}
		this.problems.add(problem);
	}
	
	/**
	 * Clear the problems.
	 * @return
	 */
	public void clearProblems() {
		if ( this.problems != null ) {
			this.problems.clear();
		}
	}

	/**
	 * Create an index list for TimeSeriesCatalog data list, using stationNumId as the index.
	 * This is a list of lists, with outermost list being the stationNumId. 
	 * It is assumed that the catalog is sorted by stationNumId, which should be the case
	 * due to logic in the 'tscatalog' service.
	 * @param tscatalogList list of TimeSeriesCatalog to create an index for.
	 * @return the indexed TimeSeriesCatalog
	 */
	/*
	public static List<IndexedDataList<Integer,TimeSeriesCatalog>> createIndex ( List<TimeSeriesCatalog> tscatalogList ) {
		List<IndexedDataList<Integer,TimeSeriesCatalog>> indexList = new ArrayList<>();
		// Loop through the TimeSeriesCatalog list.
		Integer stationNumIdPrev = null;
		boolean newStationNumId = false;
		Integer stationNumId = null;
		IndexedDataList<Integer,TimeSeriesCatalog> stationTimeSeriesCatalogList = null;
		for ( TimeSeriesCatalog tscatalog : tscatalogList ) {
			stationNumId = tscatalog.getStationNumId();
			newStationNumId = false;
			if ( stationNumIdPrev == null ) {
				// First station.
				newStationNumId = true;
			}
			else if ( ! stationNumId.equals(stationNumIdPrev) ) {
				// Station does not match previous so need to add to index.
				newStationNumId = true;
			}
			// Set the previous stationNumId for the next iteration.
			stationNumIdPrev = stationNumId;
			if ( newStationNumId ) {
				// New station:
				// - create a new list and add to the index list
				// - use the statinNumId for primary identifier and stationId for secondary identifier
				//stationTimeSeriesCatalogList = new IndexedDataList<>(stationNumId, tscatalog.getStationId());
				indexList.add(stationTimeSeriesCatalogList);
			}
			// Add the station to the current list being processed.
			stationTimeSeriesCatalogList.add(tscatalog);
		}
		return indexList;
	}
	*/

	/**
	 * Format problems into a single string.
	 * @return formatted problems.
	 */
	public String formatProblems() {
		if ( this.problems == null ) {
			return "";
		}
		StringBuilder b = new StringBuilder();
		for ( int i = 0; i < problems.size(); i++ ) {
			if ( i > 0 ) {
				b.append("; ");
			}
			b.append(problems.get(i));
		}
		return b.toString();
	}

	public Integer getCatchmentId ( ) {
		return this.catchmentId;
	}

	public String getCatchmentName ( ) {
		return this.catchmentName;
	}
	
	public String getCatchmentNo ( ) {
		return this.catchmentNo;
	}

	public String getDataInterval ( ) {
		return this.dataInterval;
	}
	
	public String getDataType ( ) {
		return this.dataType;
	}
	
	public String getDataUnits ( ) {
		return this.dataUnits;
	}

	/**
	 * Get the list of distinct data intervals from the catalog, for example "IrregSecond", "15Minute".
	 * @param tscatalogList list of TimeSeriesCatalog to process.
	 * The list may have been filtered by data type previous to calling this method.
	 * @return a list of distinct data interval strings.
	 */
	public static List<String> getDistinctDataIntervals ( List<TimeSeriesCatalog> tscatalogList ) {
	    List<String> dataIntervalsDistinct = new ArrayList<>();
	    String dataInterval;
	    boolean found;
	    for ( TimeSeriesCatalog tscatalog : tscatalogList ) {
	    	// Data interval from the catalog, something like "IrregSecond", "15Minute", "1Hour", "24Hour".
	    	dataInterval = tscatalog.getDataInterval();
	    	if ( dataInterval == null ) {
	    		continue;
	    	}
	    	found = false;
	    	for ( String dataInterval2 : dataIntervalsDistinct ) {
	    		if ( dataInterval2.equals(dataInterval) ) {
	    			found = true;
	    			break;
	    		}
	    	}
	    	if ( !found ) {
	    		// Add to the list of unique data types.
	    		dataIntervalsDistinct.add(dataInterval);
	    	}
	    }
	    return dataIntervalsDistinct;
	}

	/**
	 * Get the list of distinct data types from the catalog.
	 * @param tscatalogList list of TimeSeriesCatalog to process.
	 * @return a list of distinct data type strings.
	 */
	public static List<String> getDistinctDataTypes ( List<TimeSeriesCatalog> tscatalogList ) {
	    List<String> dataTypesDistinct = new ArrayList<>();
	    String dataType;
	    boolean found;
	    for ( TimeSeriesCatalog tscatalog : tscatalogList ) {
	    	// Data type from the catalog, something like "WaterLevelRiver".
	    	dataType = tscatalog.getDataType();
	    	if ( dataType == null ) {
	    		continue;
	    	}
	    	found = false;
	    	for ( String dataType2 : dataTypesDistinct ) {
	    		if ( dataType2.equals(dataType) ) {
	    			found = true;
	    			break;
	    		}
	    	}
	    	if ( !found ) {
	    		// Add to the list of unique data types.
	    		dataTypesDistinct.add(dataType);
	    	}
	    }
	    return dataTypesDistinct;
	}

	/**
	 * Return whether checkData() has resulted in problems being set.
	 * @return whether checkData() has resulted in problems being set.
	 */
	public boolean getHaveCheckDataProblemsBeenSet () {
		return this.haveCheckDataProblemsBeenSet;
	}

	public String getLocId ( ) {
		return this.locId;
	}

	public Integer getParameterTypeId ( ) {
		return this.parameterTypeId;
	}

	public String getParameterTypeName ( ) {
		return this.parameterTypeName;
	}

	public Integer getSiteId ( ) {
		return this.siteId;
	}

	public String getSiteName ( ) {
		return this.siteName;
	}
	
	public String getSiteNo ( ) {
		return this.siteNo;
	}
	
	//public String getStationDescription ( ) {
	//	return this.stationDescription;
	//}
	
	//public Double getStationElevation ( ) {
	//	return this.stationElevation;
	//}
	
	public Integer getStationId ( ) {
		return this.stationId;
	}
	
	public Double getStationLatitude ( ) {
		return this.stationLatitude;
	}
	
	public Double getStationLongitude ( ) {
		return this.stationLongitude;
	}

	public String getStationLongName ( ) {
		return this.stationLongName;
	}
	
	public String getStationName ( ) {
		return this.stationName;
	}
	
	public String getStationNo ( ) {
		return this.stationNo;
	}

	/**
	 * Return the station parameter long name.
	 * @return the station parameter long name. 
	 */
	public String getStationParameterLongName () {
		return this.stationParameterLongName;
	}
	
	/**
	 * Return the station parameter name.
	 * @return the station parameter name. 
	 */
	public String getStationParameterName () {
		return this.stationParameterName;
	}
	
	/**
	 * Return the station parameter number.
	 * @return the station parameter number. 
	 */
	public String getStationParameterNo () {
		return this.stationParameterNo;
	}

	public Integer getTsId ( ) {
		return this.tsId;
	}

	public String getTsName ( ) {
		return this.tsName;
	}

	public String getTsPath ( ) {
		return this.tsPath;
	}

	public String getTsShortName ( ) {
		return this.tsShortName;
	}

	public String getTsSpacing ( ) {
		return this.tsSpacing;
	}

	public Integer getTsTypeId ( ) {
		return this.tsTypeId;
	}

	public String getTsTypeName ( ) {
		return this.tsTypeName;
	}

	public String getTsUnitName ( ) {
		return this.tsUnitName;
	}

	public String getTsUnitNameAbs ( ) {
		return this.tsUnitNameAbs;
	}

	public String getTsUnitSymbol ( ) {
		return this.tsUnitSymbol;
	}

	public String getTsUnitSymbolAbs ( ) {
		return this.tsUnitSymbolAbs;
	}

	public void setCatchmentId ( Integer catchmentId ) {
		this.catchmentId = catchmentId;
	}
	
	public void setCatchmentName ( String catchmentName ) {
		this.catchmentName = catchmentName;
	}
	
	public void setCatchmentNo ( String catchmentNo ) {
		this.catchmentNo = catchmentNo;
	}

	public void setDataInterval ( String dataInterval ) {
		this.dataInterval = dataInterval;
	}
	
	public void setDataType ( String dataType ) {
		this.dataType = dataType;
	}
	
	public void setDataUnits ( String dataUnits ) {
		this.dataUnits = dataUnits;
	}

	/**
	 * Set whether checkData() has resulted in problems being set.
	 * - TODO smalers 2020-12-15 not sure this is needed with the latest code.
	 *   Take out once tested out.
	 */
	public void setHaveCheckDataProblemsBeenSet ( boolean haveCheckDataProblemsBeenSet ) {
		this.haveCheckDataProblemsBeenSet = haveCheckDataProblemsBeenSet;
	}

	public void setLocId ( String locId ) {
		this.locId = locId;
	}

	public void setParameterTypeId ( Integer parameterTypeId ) {
		this.parameterTypeId = parameterTypeId;
	}

	public void setParameterTypeName ( String parameterTypeName ) {
		this.parameterTypeName = parameterTypeName;
	}

	public void setSiteId ( Integer siteId ) {
		this.siteId = siteId;
	}
	
	public void setSiteName ( String siteName ) {
		this.siteName = siteName;
	}
	
	public void setSiteNo ( String siteNo ) {
		this.siteNo = siteNo;
	}
	
	public void setStationId ( Integer stationId ) {
		this.stationId = stationId;
	}
	
	public void setStationLatitude ( Double stationLatitude ) {
		this.stationLatitude = stationLatitude;
	}
	
	public void setStationLongitude ( Double stationLongitude ) {
		this.stationLongitude = stationLongitude;
	}

	public void setStationLongName ( String stationLongName ) {
		this.stationLongName = stationLongName;
	}
	
	public void setStationName ( String stationName ) {
		this.stationName = stationName;
	}
	
	public void setStationNo ( String stationNo ) {
		this.stationNo = stationNo;
		// Also set the location ID to the same.
		this.setLocId(stationNo);
	}
	
	public void setStationParameterLongName ( String stationParameterLongName ) {
		this.stationParameterLongName = stationParameterLongName;
	}
	
	public void setStationParameterName ( String stationParameterName ) {
		this.stationParameterName = stationParameterName;
	}
	
	public void setStationParameterNo ( String stationParameterNo ) {
		this.stationParameterNo = stationParameterNo;
	}
	
	public void setTsId ( Integer tsId ) {
		this.tsId = tsId;
	}

	public void setTsName ( String tsName ) {
		this.tsName = tsName;
	}

	public void setTsPath ( String tsPath ) {
		this.tsPath = tsPath;
	}

	public void setTsShortName ( String tsShortName ) {
		this.tsShortName = tsShortName;
	}

	public void setTsSpacing ( String tsSpacing ) {
		this.tsSpacing = tsSpacing;
	}

	public void setTsTypeId ( Integer tsTypeId ) {
		this.tsTypeId = tsTypeId;
	}

	public void setTsTypeName ( String tsTypeName ) {
		this.tsTypeName = tsTypeName;
	}

	public void setTsUnitName ( String tsUnitName ) {
		this.tsUnitName = tsUnitName;
	}

	public void setTsUnitNameAbs ( String tsUnitNameAbs ) {
		this.tsUnitNameAbs = tsUnitNameAbs;
	}

	public void setTsUnitSymbol ( String tsUnitSymbol ) {
		this.tsUnitSymbol = tsUnitSymbol;
	}

	public void setTsUnitSymbolAbs ( String tsUnitSymbolAbs ) {
		this.tsUnitSymbolAbs = tsUnitSymbolAbs;
	}
	
	/**
	 * Simple string to identify the time series catalog, for example for logging, using TSID format.
	 */
	public String toString() {
		return "" + this.locId + ".." + this.dataType + "." + this.dataInterval;
	}
}
