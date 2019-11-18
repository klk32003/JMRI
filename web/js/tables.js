/*
 * TablesServlet specific JavaScript
 * 
 * TODO: update other language NavBar.html, Tables.html
 * TODO: update json help with correct program references
 * TODO: add filter to tables
 * TODO: add button or dropdown to change state for selected items
 * TODO: add enum descriptions to schema and use them for converting states, and 
 *         for calc'ing the "next" state
 * TODO: additional columns and changes for block, light, route
 * TODO: why does no configProfile show isAutoStart?
 */

var jmri = null;

//handle an error message returned via the websocket from the server
//  parms: html error code, message is the message text
function showError(code, message) {
	$("#activity-alert").removeClass("show").addClass("hidden");
	$("table#jmri-data").removeClass("show").addClass("hidden");
	$("#warning-no-data").removeClass("show").addClass("hidden");
	$("#error-message").html("Error " + code + ":" + message);
	$("#error-message").removeClass("hidden").addClass("show");
}

//parm is the array of items from which to build table
function rebuildTable(data) {
	$("#activity-alert").removeClass("hidden").addClass("show");
	$("table#jmri-data").removeClass("show").addClass("hidden");
	$("#warning-no-data").removeClass("show").addClass("hidden");
	$("#error-message").removeClass("show").addClass("hidden");
	if (data.length) {
		//build header row from first row of data
		var thead = '<tr>';
		$.each(data[0].data, function (index, value) {
			//			jmri.log("head " + index+"="+value);
			thead += '<th>' + index + '</th>';
		});
		thead += '</tr>';
		$("table#jmri-data thead").html(thead);
		//build all data rows for table body, store item name in rows for later lookup
		var tbody = '';
		data.forEach(function (item) {
			jmri.socket.send(item.type, { name: item.data.name });
			tbody += '<tr data-name="' + item.data.name + '">';
			$.each(item.data, function (index, value) {
				tbody += '<td>' + displayCellValue($("html").data("table-type"), index, value) + '</td>'; //replace some values with descriptions
			});
			tbody += '</tr>';
		});
		$("table#jmri-data tbody").html(tbody);
		$("table#jmri-data").removeClass("hidden").addClass("show");
		var newTableObject = document.getElementById("jmri-data");
		sorttable.makeSortable(newTableObject);
	} else {
		$("#warning-no-data").removeClass("hidden").addClass("show");
	}
	$("#activity-alert").removeClass("show").addClass("hidden");
	hideEmptyColumns("table#jmri-data tr th");
}

function replaceRow(key, data) {
	jmri.log("in replaceRow: name='" + key + "'");
	var row = $("table#jmri-data tr[data-name='" + key + "']");
	if ($(row).length) {
		var r = "";
		$.each(data, function (index, value) {
			r += '<td>' + displayCellValue($("html").data("table-type"), index, value) + '</td>'; //replace some values with descriptions
		});
		row.html(r);
		hideEmptyColumns("table#jmri-data tr th");
	} else {
		jmri.log("row not found for name='" + key + "'");
	}
}

/* convert each cell into more human-readable form */
function displayCellValue(type, colName, value) {
	if (value == null) {
		return ""; //return empty string for any null value
	}
	if ($.isArray(value)) { 
		if (value.length == 0) {
			return "";
		}
		if (typeof value[0] === "object") { //check first item to see if associative array
			ret = "";
			comma = "";
			value.forEach(function(item) { //return list
				if (item.name)
					ret += comma + item.name;
				else { 
					Object.keys(item).forEach(function(key) {
						ret += comma + key + ":" + item[key];
					});
				}
				comma = ", ";
			});
			return ret;
		}
		return value; //return simple array
	}
	if (typeof value === "object") {
		if (value.name) {
			return value.name;  // return name of object if it has one
		} else {
			return "[obj]"; //placeholder				
		}
	}
	//convert known states to human-readable strings, if not known show as is
	if ((colName == "state") || (colName == "occupiedSense")) {
		switch (type) {
			case "turnout":
				switch (value) {
					case jmri.UNKNOWN: return "unknown";
					case jmri.CLOSED: return "closed";
					case jmri.THROWN: return "thrown";
					case jmri.INCONSISTENT: return "inconsistent";
					default: return value;
				}
			case "route":
			case "sensor":
			case "layoutBlock":
				switch (value) {
					case jmri.UNKNOWN: return "unknown";
					case jmri.ACTIVE: return "active";
					case jmri.INACTIVE: return "inactive";
					case jmri.INCONSISTENT: return "inconsistent";
					default: return value;
				}
			case "block":
				switch (value) {
					case jmri.UNKNOWN: return "unknown";
					case 2: return "occupied";
					case 4: return "unoccupied";
					default: return value;
				}
			case "light":
				switch (value) {
					case jmri.UNKNOWN: return "unknown";
					case 2: return "on";
					case 4: return "off";
					default: return value;
				}
			default:
				return value; //not special, just return the passed in value
		}
	}
	return value; //otherwise return unchanged value
}

//replace some special chars with html equivalents
function htmlEncode(html) {
	return document.createElement('a').appendChild(
		document.createTextNode(html)).parentNode.innerHTML;
};

function hideEmptyColumns(selector) {
	$(selector).each(function (index) {
		//select all tds in this column
		var tds = $(this).parents('table').find('tr td:nth-child(' + (index + 1) + ')');
		//check if all the cells in this column are empty
		if (tds.length === tds.filter(':empty').length) {
			$(this).addClass("hidden"); //hide header			
			tds.addClass("hidden"); //hide cells
		} else {
			$(this).removeClass("hidden"); //show header			
			tds.removeClass("hidden"); //show cells			
		}
	});
}

//-----------------------------------------javascript processing starts here (main) ---------------------------------------------
$(document).ready(function () {
	jmri = $.JMRI({});

	//replace title with the table type
	document.title = "JMRI Tables: " + $("html").data("table-type") + "s";
	$("h1.title").text($("html").data("table-type") + "s");

	jmri = $.JMRI({
		//when we get the hello message, send a websocket list request which 
		//  returns the list and sets up change listeners
		hello: function (data) {
			jmri.getList($("html").data("table-type")); // request list and updates for the table-type 
		},
		//everything calls console()
		console: function (originalData) {
			var data = JSON.parse(originalData);
			jmri.log("in console: data=" + JSON.stringify(data).substr(0, 180) + "...");
			if ($.isArray(data)) {  //if its an array, 
				rebuildTable(data); //  replace the table with the array list			
			} else if ((data.type) && (data.type === "error")) {
				showError(data.data.code, data.data.message); //display any errors returned
			} else if ((data.type) && (data.type !== "hello") && (data.type !== "pong")) {
				replaceRow(data.data.name, data.data); //if single item, update the row
			}
		},
	});
});

