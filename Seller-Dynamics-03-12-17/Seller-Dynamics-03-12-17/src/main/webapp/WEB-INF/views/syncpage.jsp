<!--
	Copyright (C) 2017 Solenoid Augment Technologies Limited.
 	All rights reserved.
 -->
 
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Seller Dynamics </title>
<link rel="stylesheet" href="assets/bootstrap.min.css">
<link rel="stylesheet" href="assets/bootstrap-theme.min.css">
<link rel="stylesheet" href="assets/jquery-ui.css">
<link rel="stylesheet" href="assets/style.css">
<script src="assets/jquery.min.js"></script>
<script src="assets/bootstrap.min.js"></script>
<script src="assets/bootstrapvalidator.min.js"></script>
<script src="assets/bootbox.min.js"></script>
<script src="assets/jquery-ui.min.js"></script>
<script src="assets/syncpage.js"></script>

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
	<!-- <form class="well form-horizontal" action="/sd/connector/sync" method="get"
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
			<br><br>
			<div class="col-md-6 col-sm-12 col-lg-6">
				
				<div class="form-group">
					<label class="col-md-4 control-label">Select Operation</label>
						<div class="col-md-6 inputGroupContainer">
						<div class="input-group">
							<select name="fetchType" id="fetchType" class="form-control selectpicker">
							<option value="">Please Select</option>
							<option value="itemStock">Item Stock</option>
							<option value="salesOrder">Sales Order</option>
							<option value="deliveryDetails">Delivery Details</option>
							<option value="customer">Customer</option>
						</select>
						</div><span id="fetchSpan" class="sspan"></span>
					</div>
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
				
				<div class="form-group">
					<label class="col-md-4 control-label">Select Way</label>
						<div class="col-md-6 inputGroupContainer">
						<div class="input-group">
							<select name="selectWay" id="selectWay" class="form-control selectpicker">
							<option value="">Please Select</option>
							<option value="sdToExact">Seller Dynamics to EXACT</option>
							<option value="exactToSd">EXACT to Seller Dynamics</option>
						</select>
						</div><span id="waySpan" class="sspan"></span>
					</div>
				</div>
				
			<!-- Button -->
			
			<div class="form-group">
				<label class="col-md-4 control-label"></label>
				<div class="col-md-4">
					<button type="button" id="syncBtn" class="btn btn-primary"
						style="margin-right: 5px;">Sync</button>
				</div>
			</div>
			
			<!-- <div class="col-md-12">
				<div class="">
				<label class="col-md-4 control-label"></label>
				<div class="col-md-8 inputGroupContainer">
					<button type="button" id="syncBtn" style="margin-right: 5px;">Sync</button>
					<input type="submit" value="Sync" class="btn btn-primary"> 
					
				</div>
				</div>
			</div> -->
			
			
		</div>
		</fieldset>
	</form>
</div>
</html>

