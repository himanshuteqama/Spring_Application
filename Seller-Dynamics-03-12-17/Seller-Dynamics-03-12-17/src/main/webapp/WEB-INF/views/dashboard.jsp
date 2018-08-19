<!--
	Copyright (C) 2017 Solenoid Augment Technologies Limited.
 	All rights reserved.
 -->
 
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Seller Dynamics - Dashboard </title>
<link rel="stylesheet" href="assets/bootstrap.min.css">
<link rel="stylesheet" href="assets/bootstrap-theme.min.css">
<link rel="stylesheet" href="assets/jquery-ui.css">
<link rel="stylesheet" href="assets/style.css">
<script src="assets/jquery.min.js"></script>
<script src="assets/bootstrap.min.js"></script>
<script src="assets/bootstrapvalidator.min.js"></script>
<script src="assets/bootbox.min.js"></script>
<script src="assets/jquery-ui.min.js"></script>
<script src="assets/dashboard.js"></script>

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
<script type="text/javascript">
	function refreshPage(){
		window.location = "/sd/connector";
	}
</script>
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
			
			<h4 style="margin-left:480px;">DashBoard
			
			<img src="assets/images/setting.png" style="float:right;margin-right:30px;cursor: pointer;" title="Edit Preferences" id="editBtn" width="30" height="30">
			<img src="assets/images/refresh.png" style="float:right;margin-right:20px;" title="Refresh Details" onclick="refreshPage()" width="30" height="30">
			</h4>
			<div class="col-md-6 col-sm-12 col-lg-6">
				<span id="exceptionSpan" class="sspan"></span>
				<div class="form-group">
					<label class="col-md-4 control-label" style="width:215px;">Sync Entity </label>
					<label class="col-md-4 control-label" style="width:150px;">Last Sync Date</label>
				</div>
				
				<div class="form-group">
					<label class="col-md-4 control-label" style="width:230px;">EXACT To SD Item : </label>
					<div class="col-md-4 inputGroupContainer">
						<div class="input-group" style="margin-top:5px">
							${dateItemExactToSD}
						</div>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-md-4 control-label" style="width:230px;">SD To EXACT SalesOrder : </label>
					<div class="col-md-4 inputGroupContainer">
						<div class="input-group" style="margin-top:5px">
							${dateSalesSDToExact}
						</div>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-md-4 control-label" style="width:230px;">SD To EXACT Customer : </label>
					<div class="col-md-4 inputGroupContainer">
						<div class="input-group" style="margin-top:5px">
							${dateCustomerSDToExact}
						</div>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-md-4 control-label" style="width:230px;">SD To EXACT Delivery : </label>
					<div class="col-md-4 inputGroupContainer">
						<div class="input-group" style="margin-top:5px">
							${dateDeliverySDToExact}
						</div>
					</div>
				</div>
				
				
				<div class="form-group">
					<label class="col-md-4 control-label"></label>
					<div class="col-md-4" style="width:300px;">
						<c:if test="${checkSchedularkStatus==true}">
							<button type="button" id="stopSyncBtn" class="btn btn-danger"
							style="margin-right: 5px">Stop Sync</button>&nbsp;
						</c:if>
						<%-- <c:if test="${checkSchedularkStatus==false}"> --%>
							&nbsp;<button type="button" id="syncBtn" class="btn btn-primary"
							style="margin-right: 5px;">Start Sync</button>&nbsp;
						<%-- </c:if>	 --%>
						
					</div>
				</div>
				
		</div>
		</fieldset>
	</form>
</div>
</html>



<div class="modal fade" tabindex="-1" role="dialog" id="myModal" style="font-size: 15px;overflow:hidden;">
  <div class="modal-dialog modal-lg" role="document" style="width:800px;">
    <div class="modal-content" style="height:350px;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" style="font-size:21px;">Edit Provisioning</h4>
      </div>
		
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
				
				<div class="form-group" id="warehouseDiv">
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
				
				<div class="form-group" id="frequencyDiv" style="margin-top:57px;">
					<label class="col-md-4 control-label">Sync Frequency</label>
					<div class="col-md-4 inputGroupContainer">
						<div class="input-group" style="width:850px;">
							<input type="number" min="0" placeholder="Please enter frequency" value="${frequency}" name="frequency" class="form-control" style="width:220px" >
							&nbsp;&nbsp;&nbsp;
							<select name="frequencyDur" id="frequencyDur" class="form-control selectpicker" style="width:180px;">
							<option value="">Please Select</option>
							<option value="minute">Minute</option>
							<option value="hour" selected>Hour</option>
							<option value="day">Day</option>
						</select>
						</div><span id="frequencySpan" class="sspan"></span>
					</div>
				</div>
				<br><br>
				<div id="startSyncDiv" style="margin-top:5px;">
					<label class="col-md-4 control-label" >Start Sync Date </label>
					<label class="col-md-4 control-label" id="startSyncDateLabel"></label>
				</div>
			
	  
      <div class="modal-footer" id="footerButtonsDiv" style="padding-top:0px;margin-top:42px;">
	    <button type="button" class="btn btn-primary" id="updateBtn">Update</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
      </div>
      
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div>

