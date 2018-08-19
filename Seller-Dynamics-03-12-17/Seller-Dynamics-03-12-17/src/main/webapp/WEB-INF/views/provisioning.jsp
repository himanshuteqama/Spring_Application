<!--
	Copyright (C) 2017 Solenoid Augment Technologies Limited.
 	All rights reserved.
 -->
 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Seller Dynamics - Provisioning </title>
<link rel="stylesheet" href="assets/bootstrap.min.css">
<link rel="stylesheet" href="assets/bootstrap-theme.min.css">
<link rel="stylesheet" href="assets/jquery-ui.css">
<link rel="stylesheet" href="assets/style.css">
<script src="assets/jquery.min.js"></script>
<script src="assets/bootstrap.min.js"></script>
<script src="assets/bootstrapvalidator.min.js"></script>
<script src="assets/bootbox.min.js"></script>
<script src="assets/jquery-ui.min.js"></script>
<script src="assets/provisioning.js"></script>
<script>
	$(function() {
		$("#datepicker").datepicker();
	});
</script>
<style>
.form-control {padding: 0px !important}
.modal-footer {padding: 4px 19px 10px !important;}
.modal-body {padding-bottom: 0px !important;}
legend{border-bottom: 1px solid black !important;}
.control-label{color:#333 !important;}
.loading-div {background: url("assets/images/loading.gif") no-repeat 50% 50% #000;display: table;width: 85%;height: 100%;position: fixed;z-index: 100000;opacity: .5;}
.marginBottom5px{margin-bottom: 7px !important;}
.bold{font-weight:bold;}
.fsize{font-size: 0.9em !important;}
#myModal .btn {padding: 5px 12px;font-size: 13px !important;}
.modal-header{padding-bottom: 0px;padding-top: 5px;}
.paddingLeft30px{padding-left: 30px !important;}
.rg-container {font-family: 'Lato', Helvetica, Arial, sans-serif;font-size: 16px;line-height: 1.4;margin: 0;padding: 1em 0.5em;color: #222;}
.rg-header {margin-bottom: 1em;text-align: left;}
.rg-header > * {display: block;}
.rg-hed {font-weight: bold;font-size: 1.4em;}
.rg-dek {font-size: 1em;}
.rg-source {margin: 0;font-size: 0.75em;text-align: right;}
.rg-source .pre-colon {text-transform: uppercase;}
.rg-source .post-colon {font-weight: bold;}
/* table */
table.rg-table {width: 100%;margin-bottom: 0.5em;font-size: 1em;border-collapse: collapse;border-spacing: 0;}
table.rg-table tr {-moz-box-sizing: border-box;box-sizing: border-box;margin: 0;padding: 0;border: 0;font-size: 100%;font: inherit;vertical-align: baseline;text-align: left;color: #333;}
table.rg-table thead {border-bottom: 3px solid #ddd;}
table.rg-table tr {color: #222;}
table.rg-table tr.highlight {background-color: #dcf1f0 !important;}
table.rg-table.zebra tr:nth-child(even) {background-color: #f6f6f6;}
table.rg-table th {font-weight: bold;padding: 0.35em;font-size: 0.9em;}
table.rg-table td {padding: 0.35em;font-size: 0.7em;}
table.rg-table .highlight td {font-weight: bold;}
table.rg-table th.number, td.number {text-align: right;}
.modal-body {max-height: calc(125vh - 300px);overflow-y: auto;}
/* media queries */
@media screen and (max-width: 600px) {
.rg-container {max-width: 600px;margin: 0 auto;}
table.rg-table {width: 100%;}
table.rg-table tr.hide-mobile, table.rg-table th.hide-mobile, table.rg-table td.hide-mobile {display: none;}
table.rg-table thead {display: none;}
table.rg-table tbody {width: 100%;}
table.rg-table tr, table.rg-table th, table.rg-table td {display: block;padding: 0;}
table.rg-table tr {border-bottom: none;margin: 0 0 1em 0;padding: 0.5em;}
table.rg-table tr.highlight {background-color: inherit !important;}
table.rg-table.zebra tr:nth-child(even) {background-color: none;}
table.rg-table.zebra td:nth-child(even) {background-color: #f6f6f6;}
table.rg-table tr:nth-child(even) {background-color: none;}
table.rg-table td {padding: 0.5em 0 0.25em 0;border-bottom: 1px dotted #ccc;text-align: right;}
table.rg-table td[data-title]:before {content: attr(data-title);font-weight: bold;display: inline-block;content: attr(data-title);float: left;margin-right: 0.5em;font-size: 0.95em;}
table.rg-table td:last-child {padding-right: 0;border-bottom: 2px solid #ccc;}
table.rg-table td:empty {display: none;}
table.rg-table .highlight td {background-color: inherit;font-weight: normal;}
}

</style>

</head>
<div class="container">
<div class="loading-div" style="display:none;"></div>
	<!-- <form class="well form-horizontal" action="/sd/connector/save" method="get"
		id="calForm"> -->
		
		<form class="well form-horizontal" action=" " method="post"
		id="calForm">
		<fieldset>
			<!-- Form Name -->
			<legend><span>Seller Dynamics</span><span style="float:right;">${division} | ${fullName}</span></legend>
			
			<input type="hidden" name="divisionId" value="${division}" id="did"> 
			<input type="hidden" name="raisedBy" value="${fullName}">
			<!-- Text input-->
			<%-- <input type="hidden" name="divisionId" value="${division}">
			<input type="hidden" name="raisedBy" value="${fullName}"> --%>
			
			<h4 style="text-align:center; margin-right:135px;">Provisioning</h4><br>
			
			<div class="col-md-6 col-sm-12 col-lg-6">
				
				<div class="form-group">
					<label class="col-md-4 control-label">Encrypted Login</label>
					<div class="col-md-6 inputGroupContainer">
						<div class="input-group">
							<input type="text" placeholder="Please enter encrypted login" name="encryptedLogin" 
								class="form-control" style="width:220px" >
						</div><span id="elSpan" class="sspan"></span>
					</div>
				</div>
				<div class="form-group" style="margin-bottom: 5px;">
					<label class="col-md-4 control-label">RetailerID</label>
					<div class="col-md-6 inputGroupContainer">
						<div class="input-group">
							<input type="text" placeholder="Please enter retailer ID" name="retailerID" 
								class="form-control" style="width:220px" >
						</div><span id="rtSpan" class="sspan"></span>
					</div>
				</div>
				
				<!-- <div class="form-group" style="display:none">
					<label class="col-md-4 control-label">VAT Code</label>
					<div class="col-md-6 inputGroupContainer">
						<div class="input-group">
							<input type="text" placeholder="Please enter VAT code" name="vat" 
								class="form-control" style="width:220px" >
						</div><span id="vatSpan" class="sspan"></span>
					</div>
				</div> -->
				
				<div class="form-group" style="margin-bottom:10px;margin-top:10px;">
				<label class="col-md-4 control-label">Sync Products</label>
					<input type="checkbox" style="margin-left:16px;margin-top:11px;" name="syncProducts" id="syncProducts" ><br>
					
				<label class="col-md-4 control-label">Sync Customers</label>	
					<input type="checkbox" style="margin-left:16px;margin-top:11px;" name="syncCustomers" id="syncCustomers"><br>
					
				<label class="col-md-4 control-label">Sync SalesOrder</label>	
					<input type="checkbox" style="margin-left:16px;margin-top:11px;" name="syncSalesOrder" id="syncSalesOrder"><br>
					
				<label class="col-md-4 control-label">Sync Marketplaces</label>	
					<input type="checkbox" style="margin-left:16px;margin-top:11px;" name="syncMarketplaces" id="syncMarketplaces">
				</div>
				
				<div class="form-group" id="warehouseDiv" style="display:none">
					<label class="col-md-4 control-label">Warehouse</label>
						<div class="col-md-6 inputGroupContainer">
						<div class="input-group">
							<c:if test="${not empty ware}">
								<select name="warehouse" id="warehouse" class="form-control selectpicker">
									<option value="">Select Warehouse</option>
									<c:forEach var="acc" items="${ware}">
										<option value="${acc.id}">${acc.description}</option>
									</c:forEach>
								</select>
							</c:if>
						</div>
					</div>
				</div>
				
				
				
				
				
				
				<div class="form-group" id="marketplaces">
<%-- 					<c:if test="${not empty marketPlaces}"> --%>
<%-- 						<c:forEach var="market" items="${marketPlaces}"> --%>
							<label class="col-md-4 control-label" for=""></label>
							<div class="col-md-6 inputGroupContainer" style="padding-top:3px;">
								<div class="input-group" id="selectBoxes">
									<%-- <select name="users" id="users" class="form-control selectpicker">
									<option value="">Select User</option>
									<c:forEach var="user" items="${users}">
<!-- 										<option value="">Select User</option> -->
									</c:forEach>
									</select> --%>
								</div>
							</div>
<%-- 						</c:forEach> --%>
<%-- 					</c:if> --%>
				</div>
				
				
				
				
				
				
				
				<div class="form-group">
					<label class="col-md-4 control-label">Sync Frequency</label>
					<div class="col-md-4 inputGroupContainer">
						<div class="input-group" style="width:850px">
							<input type="number" min="0" placeholder="Please enter frequency" name="frequency" class="form-control" style="width:220px" >
							&nbsp;&nbsp;&nbsp;
							<select name="frequencyDur" id="frequencyDur" class="form-control selectpicker" style="width:180px;">
							<option value="">Please Select</option>
							<option value="hour" selected>Hour</option>
							<option value="day">Day</option>
						</select>
						</div>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-md-4 control-label" style="width:165px;">Date from you want to sync :</label>
					<div class="col-md-6 inputGroupContainer">
						<div class="input-group" style="margin-left:18px;margin-top: 10px;">
							<input type="text" id="datepicker" name="mdate"
								placeholder="Select Date">
						</div>
					</div>
				</div>
				
			<!-- Button -->
			<div class="form-group">
				<label class="col-md-4 control-label"></label>
				<div class="col-md-4" style="width:300px;">
					<button type="button" id="saveBtn" class="btn btn-primary"
						style="margin-right: 5px;">Save</button>
					<button type="button" id="getMarket" class="btn btn-primary"
						>Get Market</button>
				</div>
			</div>
			
			
		</div>
		</fieldset>
	</form>
</div>
</html>

