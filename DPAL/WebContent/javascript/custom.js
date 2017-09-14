/* Patch to add method indexOf() to arrays */
if (!Array.prototype.indexOf) {
	Array.prototype.indexOf = function(searchElement /*, fromIndex */) {
		"use strict";
		if (this === void 0 || this === null)
			throw new TypeError();

		var t = Object(this);
		var len = t.length >>> 0;
		if (len === 0)
			return -1;

		var n = 0;
		if (arguments.length > 0)
		{
			n = Number(arguments[1]);
			if (n !== n)
				n = 0;
			else if (n !== 0 && n !== (1 / 0) && n !== -(1 / 0))
				n = (n > 0 || -1) * Math.floor(Math.abs(n));
		}

		if (n >= len)
			return -1;

		var k = n >= 0
		? n
				: Math.max(len - Math.abs(n), 0);

		for (; k < len; k++)
		{
			if (k in t && t[k] === searchElement)
				return k;
		}
		return -1;
	};
}
/* Patch end */

var tableID = "";
var wslField = "";
var formVal = "";

function getMainView() {
	var doc = null
	try {
		doc = parent.top.frames["mainView"].document;
	} catch(e) {
		doc = document;
	}
	return doc;
}

function popupDealerPage(tid) {

	/*attach the custom property "creator" to win2, and have it contain the current window (main window)*/
	var is_chrome = navigator.userAgent.toLowerCase().indexOf('chrome') > -1;
	tableID = tid;
	if(tableID == "email-content1") wslField = "shippingDealerWslId";
	else if(tableID == "email-content2") wslField = "buyingDealerWslId";
	win2=window.open('',"dealer_select","width=800,height=500,scrollbars=1,menubar=yes,resizable=0,status=no,top=20,left=350");
	win2.creator=self;
	if (is_chrome) {win2.parent.blur();}
	win2.focus();
	var form = getMainView().forms[0];
	form.action = "dpaladmin.do";
	form.elements["aAction"].value = "details";
	form.target = "dealer_select";
	form.submit();
	
}

function addNewRow(wslId, emailId){	
	var doc = getMainView();
	var table = doc.getElementById(tableID);
	var rowCount = table.rows.length;
	if(rowCount == 5) {alert("You cannot add more than 5 addresses."); return;}
	
	var row = table.insertRow(rowCount);
	var cell1 = row.insertCell(0);
	var element1 = doc.createElement("input");
	element1.type = "text";
	element1.name = "emailId";
	element1.value = emailId;
	element1.readOnly = "true";
	element1.id = "emailId";
	
	var element2 = doc.createElement("input");
	element2.type = "hidden";
	element2.name = wslField;
	element2.value = wslId;
	element2.id = wslField;
	cell1.appendChild(element1);
	cell1.appendChild(element2);
	
	//cell1 = row.insertCell(1);
	var blankSpace = doc.createTextNode("  ");
	cell1.appendChild(blankSpace);
	
	var element3 = doc.createElement("img");
	element3.src = "images/minus-icon.gif";
	element3.height = 10;
	element3.width = 10;
	element3.border = 0;
	element3.style.cursor = "pointer";
	element3.onclick = (function(row){return function(){deleteRow(row);}})(row);
	cell1.appendChild(element3);
}
// Begin: Added to fix Bug id: 36
function isDuplicateEntry(wslId) {
	//alert("wslField = " + wslField);
	var wslIdList = getMainView().forms[0].elements[wslField];
	//alert("wslIdList = " + wslIdList);
	var duplicate = false;
	//alert("!wslIdList = " + (!wslIdList));
	if(!wslIdList) return false;
	
	//alert("wslIdList.length = " + wslIdList.length);
	if(wslIdList.length) {
		// there are more than one entry
		for(var z=0; z<wslIdList.length; z++) {
			//alert("wslIdList[z].value = " + wslIdList[z].value.toLowerCase() + ", wslId = " + wslId.toLowerCase() + ", compare = " + (wslIdList[z].value.toLowerCase() == wslId.toLowerCase()));
			if(wslIdList[z].value.toLowerCase() == wslId.toLowerCase()) {
				duplicate = true;
				break;
			}
		}
	} else {
		//alert("wslIdList.value = " + wslIdList.value.toLowerCase() + ", wslId = " + wslId.toLowerCase() + ", compare = " + (wslIdList.value.toLowerCase() == wslId.toLowerCase()));
		if(wslIdList.value.toLowerCase() == wslId.toLowerCase())
			duplicate = true;
	}
	
	return duplicate;
}
// End changes
function deleteRow(row) {
	var p = row.parentNode;
	p.removeChild(row);
}	
function setTarget(target){
	if(target == 0){ target = "shippingconfirmation.do"; }
	else
	{ target = ""; }
	window.location = target;
}

function showPage(pageAction) {
	var form = getMainView().forms[0];
	var url = form.action; //alert("action = " + form.action);
	form.elements["pageAction"].value = pageAction;
	form.elements["aAction"].value = "page";
	//url += "?action=page&pageAction=" + pageAction;
	//alert("url = " + url);
	form.action = url;
	form.submit();
}

function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}
function ltrim(stringToTrim) {
	return stringToTrim.replace(/^\s+/,"");
}
function rtrim(stringToTrim) {
	return stringToTrim.replace(/\s+$/,"");
}


function registerSelection(el) {
	var theForm = getMainView().forms[0];
	var idArray = null;
	//alert(el.value);
	if(el.checked) {
		if(theForm.idList.value && trim(theForm.idList.value) != "")
			theForm.idList.value += "," + el.value;
		else
			theForm.idList.value += el.value;

		el.parentNode.parentNode.className = "table-font row-selected";
	}
	else {
		// remove the id from the list
		var idArray = theForm.idList.value.split(",");
		//alert("after split: " + idArray);
		//alert("getting idx");
		var idx = idArray.indexOf(el.value);
		//alert("idx = " + idx);
		if(idx != -1){
			var ret = idArray.splice(idx, 1);
			//alert("ret = " + ret);
		}
		//alert("idArray = " + idArray);
		theForm.idList.value = idArray;
		//alert(el.parentNode.parentNode.className);
		el.parentNode.parentNode.className = "table-font row-normal";
	}

	if(getMainView().getElementById("selectCount")) {
		var l = 0;
		if(trim(theForm.idList.value) != "")
			l = theForm.idList.value.split(",").length;
		getMainView().getElementById("selectCount").innerText = l;
	}
}

function highlightRow(el, highlight) {
	if(el.className.indexOf("row-selected") != -1 || el.className.indexOf("error-row") != -1) return;
	if(highlight) {
		el.className = "table-font row-hover-highlight";
	} else {
		el.className = "table-font row-normal";
	}
}

function sortColumn(sortType) {
	//var form = document.forms[0];
	var form = getMainView().forms[0];
	//var url = form.action; //alert("action = " + form.action);
	//url += "?action=sort&sortType=" + sortType;
	//alert("url = " + url);
	//form.action = url;
	form.elements["aAction"].value = "sort";
	form.elements["sortType"].value = sortType;
	form.submit();
}

function commit(form, idList) {
	if(!validateSubmit(form, idList)) {alert("Please select at least one record"); return false;}
	//var url = form.action; //alert("action = " + form.action);
	//url += "?action=commit";
	//form.action = url;
	form.elements["aAction"].value = "commit";
	form.submit();
	//alert("Thank you, please proceed to the ‘Committed Orders Awaiting Shipment’ page to ship the parts.");
}

function remove(form, idList) {
	if(!validateSubmit(form, idList)) {alert("Please select at least one record"); return false;}
	var answer=confirm('Are you sure you wish to remove the selected shipments?');
	if(answer)
	{
		if(!validateSubmit(form, idList)) return false;
		form.elements["aAction"].value = "remove";
		form.submit();
	}
}

function ship(form, idList) {
	//alert("form = " + form + ", idList = " + idList);
	//alert("validateSubmit(form, idList) = " + validateSubmit(form, idList));
	if(!validateSubmit(form, idList)) {alert("Please select at least one record"); return false;}
	//alert("before set form.elements[\"aAction\"].value = " + form.elements["aAction"].value);
	form.elements["aAction"].value = "ship";
	//alert("after set form.elements[\"aAction\"].value = " + form.elements["aAction"].value);
	//var url = form.action; //alert("action = " + form.action);
	//url += "?action=ship";
	//form.action = url;
	form.submit();
}

function cancelShipment() {
	window.onbeforeunload = "";
	//alert('cancelShipment');
	var theForm = getMainView().forms[0];
	var answer=confirm('Are you sure you wish to remove all the shipments present in this page?');
	if(answer)
	{
		theForm.elements["aAction"].value = "cancelShipment";
		theForm.submit();
		//alert("submit pressed");
	}
}

function validateSubmit(form, idList) {
	//alert("validate");
	if(!form) return false;
	//alert("form = " + form);
	var idList = form.elements[idList];
	//alert(idList.value);
	//alert("(!idList) = " + (!idList) + ", (trim(idList.value) == \"\") = " + (trim(idList.value) == "") + "(!idList || trim(idList.value) == \"\") = " + (!idList || trim(idList.value) == ""))
	// check if idList is empty
	if(!idList || trim(idList.value) == "")
		return false;
	else
		return true;
}

function getDealerInfo(dealerCode) {
	var ajax = new GLM.AJAX();
	ajax.callPage("details.do?dealerCode=" + dealerCode, function(response){
		//alert("response = " + response);
		var dealerObj = eval('(' + response + ')');
		var output = "<b>Dealer Code:</b> " + dealerObj.dealerCode + "<br/>";
		output += "<b>Name:</b> " + dealerObj.name + "<br/>";
		output += "<b>Contact Name:</b> " + dealerObj.contactName + "<br/>";
		output += "<b>Contact Number:</b> " + dealerObj.contactNumber;
		//alert(output);
		getMainView().getElementById("dealerDetailBalloon").innerHTML = output;
	});
}

function submitShippingConfirmationForm() {
	window.onbeforeunload = "";
	//alert("submitShippingConfirmationForm()");
	var theForm = getMainView().forms[0];
	var shipQuantity = theForm.elements["shipQuantity"];
	var boQuantity = theForm.elements["boQuantity"];
	var inventory = theForm.elements["inventory"];

	var validated = true;
	var remove = true;
	var warning = false;
	var error = false;
	var numRegex = /^[0-9]+$/;
	// check if its a single element or an array of elements
	if(!shipQuantity.length) {
		var sval = trim(shipQuantity.value);
		var bval = boQuantity.value*1;
		var ival = inventory.value*1;
		
		// Modified to fix Bug id: 10
		// if(isNaN(sval) || sval == "" || sval < 0 || sval > bval || sval > ival) {
		if((!numRegex.test(shipQuantity.value)) || (isNaN(sval) || sval == "" || sval < 0 || sval > bval || sval > ival || !isInteger(sval))) {
			validated = false;
			if(sval <= bval && sval > ival) {
				highlightWarningElement(shipQuantity, true);
				warning = true;
			}
			else {
				highlightErrorElement(shipQuantity, true);
				error = true;
			}
		}
		else {
			highlightErrorElement(shipQuantity, false); 
		}
	} else {
		for(var z=0; z<shipQuantity.length; z++) {
			var sval = trim(shipQuantity[z].value);
			var bval = boQuantity[z].value*1;
			var ival = inventory[z].value*1;

			//alert(shipQuantity[z].value*1 + ", " + (boQuantity[z].value*1));
			// 1. check if the value is a number
			// 2. check if the entered quantity  is not blank
			// 3. Check if the entered quantity is less than zero
			// 4. Check if the entered quantity is less than or equal to the B/O quantity
			// 5. Check if the entered quantity is less than or equal to the "My Inventory" quantity
			//alert("isNaN(sval) = " + isNaN(sval) + ", sval == \"\" = " + (sval == "") + ", sval < 0 = " + (sval < 0) + ", sval > bval = " + (sval > bval) + ", sval > ival = " + (sval > ival));
			//alert(isNaN(sval) || sval == "" || sval < 0 || sval > bval);
			// Modified to fix Bug id: 10
			//if(isNaN(sval) || (sval == "") || (sval < 0) || (sval > bval) || (sval > ival) || (sval > ival)) {
			if((!numRegex.test(shipQuantity[z].value)) || (isNaN(sval) || (sval == "") || (sval < 0) || (sval > bval) || (sval > ival) || !isInteger(sval))) {
				validated = false;
				if(sval <= bval && sval > ival) {
					highlightWarningElement(shipQuantity, true);
					warning = true;
				}
				else {
					highlightErrorElement(shipQuantity[z], true);
					error = true;
				}
			}
			
			else {
				highlightErrorElement(shipQuantity[z], false);
			}
		}
	}
	if(!validated) {
		if(error) {
			alert("Some shipping quantities have been entered incorrectly.\nPlease make sure that the shipping quantity you enter is a number less than or equal to the given Back Order quantity. \n\nPlease enter a valid quantity for the fields highlighted in RED and try again.");
		} else if(warning) {
			if(confirm("You have entered some shipping quantities more than your available inventory. Do you wish to continue?")) {
				theForm.elements["aAction"].value = "confirm";
				theForm.submit();
			}
		}
	}
	else if(!remove){
		
		theForm.elements["aAction"].value = "cancelShipment";
		theForm.submit();
	}
	else {
		theForm.elements["aAction"].value = "confirm";
		theForm.submit();
	}
}
// Added to fix Bug id: 10
function isInteger(value){ 
	if((parseFloat(value) == parseInt(value)) && !isNaN(value)){
		return true;
	} else { 
		return false;
	} 
}

function highlightErrorElement(el, highlight) {
	if(highlight) el.className = "error-highlight";
	else el.className = "";
}

function highlightWarningElement(el, highlight) {
	if(highlight) el.className = "warning-highlight";
	else el.className = "";
}
//var win =null;
function submitFormInNewWindow() {
	upsPopupWindow = window.open('', 'theWindow', 'width=700,height=500,status=no,location=no,menubar=yes,resizable=no,scrollbars=yes');
	var form = getMainView().forms[0];
	form.action = "awaitingconfirmation.do";
	//alert("document.forms[0] = " + document.forms[0]);
	//alert("document.forms[0].elements = " + document.forms[0].elements);
	//alert("document.forms[0].elements[\"action\"] = " + document.forms[0].elements["action"]);
	form.elements["aAction"].value = "label";
	form.target = "theWindow";
	//win.creator=self;
	form.submit();
	
	upsPopupWindow.focus();
	return true;
}
/*function check() {
	if(win && !win.closed)
		win.focus();
}*/
function processMoreShipment() {
	window.onbeforeunload = "";
	var form = getMainView().forms[0];
	form.action = "awaitingconfirmation.do";
	form.elements["aAction"].value = "processMoreShipment";
	form.target = "_self";
	form.submit();
}

function image() {
	var form = getMainView().forms[0];
	form.action = "image";
	form.submit();
	
}

function disableEnterKey(e) {
     var key;      
     if(window.event)
          key = window.event.keyCode; //IE
     else
          key = e.which; //firefox      

     return (key != 13);
}

function updateTrackingNumber(trackingNum) {
	var el = getMainView().getElementById("trackingNo");
	el.innerText = trackingNum;
	el.className = "trackingNumber";
}
function submitcontact()
{
	var doc = getMainView();
	if(doc.getElementById("email-content1").rows.length < 1 || doc.getElementById("email-content2").rows.length < 1) {
		alert("You need to add at least one email id to the Buying dealer and the Selling dealer notification list"); 
		return;
	}
	
	var form = getMainView().forms[0];
	form.action = "dpaladmin.do";
	form.elements["aAction"].value = "save";
	form.target = "_self";
	form.submit();
}

function resetAdminPage() {
	var answer=confirm('All changes made to the page after the last save action will be lost. Do you wish to continue?');
	if(answer) {
		window.location = 'dpaladmin.do';
	}
}

function submitDealerSelect(dealerDropDown) {
	//alert(dealerDropDown.options[dealerDropDown.selectedIndex].value);
	var form = getMainView().forms[0];
	if(dealerDropDown.selectedIndex == 0) return;
	var selectedDealer = dealerDropDown.options[dealerDropDown.selectedIndex].value;
	form.action=""; // + "?setDealer=" + selectedDealer;
	form.setDealer.value = selectedDealer;
	form.method = "POST";
	form.submit();
}
