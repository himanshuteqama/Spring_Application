if (!JSContext) {
    var JSContext = {};
}
config= {
    contentType: "application/json",
    stringify: true
}
$("document").ready(function() {
	JSContext.errors=undefined;
    JSContext.ajaxCall = function(api, payload, onSuccess, onError, config, nospin) {
        var cType = undefined;
        var asyncVar = true;
        var hideUI = false;
        var timeOut = undefined;
        var pData = undefined;
        var ca = undefined;
        var dType = "json";
        var ctx = document.body;
        var request = {};
        if (config) {
            hideUI = config.hideUI;
            if (config.contentType!=undefined) {
                cType = config.contentType;
            }
            if (config.async==false) {
            	asyncVar = config.async;
            }
            if (config.stringify && config.stringify == true) {
                payload = JSON.stringify(payload);
            }
            if (false == config.timeOut) {
                timeOut = false;
            }
            if (false == config.processData) {
            	pData = false;
            }
            if (false == config.cache) {
            	ca = false;
            }
            if ("setundefined" == config.dataType) {
            	dType = undefined;
            }
            if ("setundefined" == config.conext) {
            	ctx = undefined;
            }
        }
        if (!hideUI) {
        	if(!nospin)
        		$(".loading-div").show();
        }
        var xhr = $.ajax({
            url: api,
            type: "POST",
            context: ctx,
            data: payload,
            contentType: cType,
            processData: pData,
            cache: ca,
            dataType: dType,
            timeout: timeOut,
            async: asyncVar,
            success: function(resp) {
            	if(!nospin)
                if (!resp) {
                    JSContext.showMessage(JSContext.COMMON_ERROR_MSG, 'error');

                } else if (resp.apiResponseStatus && resp.apiResponseStatus != "SUCCESS") {
                    if (onError) {
                        onError(resp);
                    } else {
                    	var errors = JSContext.retriveErrors(resp);
                    	JSContext.errors=errors;
                        JSContext.displayErrors(errors);
                    }
                } else {
                    if (onSuccess) {
                        onSuccess(resp);
                    }
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
            	$(".loading-div").hide();
            	bootbox.alert("Something went wrong, try after refreshing page.");
            }
        });
        return xhr;
    };
    
    
    $('#saveBtn').on('click', function(e) {
    	var salesOrderCheckValue = $("#syncSalesOrder").is(':checked')?1:0;
    	var productsCheckValue = $("#syncProducts").is(':checked')?1:0;
    	var customerCheckValue = $("#syncCustomers").is(':checked')?1:0;
    	var marketplaceCheckValue = $("#syncMarketplaces").is(':checked')?1:0;
    	
    	$("#elSpan").hide();
    	$("#rtSpan").hide();
    	if ($("input[name='encryptedLogin']" ).val()=="") {
    		$("#elSpan").show().text("Please enter ecnrypted login").css("color","red");
    		return false;
        } 
    	if ($("input[name='retailerID']").val()=="") {
    		$("#rtSpan").show().text("Please enter retailerID").css("color","red");
    		return false;
        } 
    	
    		var request={};
		 	request.divisionId=$("input[name='divisionId']" ).val();
	        request.encryptedLogin=$("input[name='encryptedLogin']").val();
	        request.retailerID=$("input[name='retailerID']").val();
	        request.frequency=$("input[name='frequency']").val();
	        request.frequencyDur=$('select[name=frequencyDur]').val();
	        request.warehouse=$('select[name=warehouse]').val();
	        
	        request.salesOrderCheckValue = salesOrderCheckValue;
	        request.productsCheckValue = productsCheckValue;
	        request.customerCheckValue = customerCheckValue;
	        request.marketplaceCheckValue = marketplaceCheckValue;
	        
	        //request.syncStartDate = $("#datepicker").datepicker('getDate');
	        request.startSyncDate = $("#datepicker").datepicker({dateFormat:"yy-mm-dd"}).val();
	        
	    	JSContext.ajaxCall("/sd/connector/save", request, function(data) {
	             if (data) {
	                 if ( data.status && data.status != "SUCCESS" ) {
	                	 bootbox.alert(data.errors[0].errorCode + " : " + data.errors[0].errorMessage);
	                 } else {
	                		//bootbox.alert("Credentials saved successfully.");
	                	 $(".loading-div").hide();
	                	 window.location = "/sd/connector";
	                 }
	             } 
	         }, undefined, config);
    });
    
    
    
    $('#getMarket').on('click', function(e) {
    	
    	$("#elSpan").hide();
    	$("#rtSpan").hide();
    	if ($("input[name='encryptedLogin']" ).val()=="") {
    		$("#elSpan").show().text("Please enter ecnrypted login").css("color","red");
    		return false;
        } 
    	if ($("input[name='retailerID']").val()=="") {
    		$("#rtSpan").show().text("Please enter retailerID").css("color","red");
    		return false;
        } 
		var request={};
	 	request.divisionId=$("input[name='divisionId']" ).val();
        request.encryptedLogin=$("input[name='encryptedLogin']").val();
        request.retailerID=$("input[name='retailerID']").val();
    	JSContext.ajaxCall("/sd/connector/getMarketplaces", request, function(data) {
             if (data) {
                 if ( data.status && data.status != "SUCCESS" ) {
                	 bootbox.alert(data.errors[0].errorCode + " : " + data.errors[0].errorMessage);
                 } else {
                	 $(".loading-div").hide();
                	 alert(JSON.stringify(data));
                	 
                	 
                	 
                	$.each(data.marketPlaces, function(i, market) { 
                	var myDiv = document.getElementById("marketplaces");
                	var selectDiv = document.getElementById("selectBoxes");

                    /*var label = $("<label>").attr('for', "users");
                    label.html("UserDrop");
                    label.appendTo('selectBoxes');*/
                	
                	var h4=document.createElement("h4");
                	h4.innerHTML=market.marketplaceName;
                	myDiv.appendChild(h4);
                	
                	var selectList = document.createElement("select");
                	selectList.id = market.retailerMarketplaceId;
//                	document.getElementById(selectList.id).className = "form-control selectpicker";
                	myDiv.appendChild(selectList);
                	
                	$(myDiv).css("display","inline")
	                	$.each(data.users, function(i, user) { 
	                		var option = document.createElement("option");
	                	    option.value = user.UserID;
	                	    option.text = user.FullName;
	                	    selectList.appendChild(option);
	                	});
                	 });

                	/*for (var i = 0; i < array.length; i++) {
                	    var option = document.createElement("option");
                	    option.value = array[i];
                	    option.text = array[i];
                	    selectList.appendChild(option);
                	}
                	
                	 /*for (var i = 0; i < data.users.length; i++) {
	            		  jQuery('<option/>', {
	            		    value: data.users[i].userId,
	            		    html: data.users[i].userFullName
	            		  }).appendTo('#users');
            		 }*/
                	 
                	 
//                	 window.location = "/sd/connector";
                 }
             } 
         }, undefined, config);
    });
    
    
    
    $('#datepicker').change(function(){
        $('#datepicker').datepicker("option","dateFormat","yy-mm-dd 00:00");
    });
    $('#syncSalesOrder').click(function() {
    	if ($(this).is(':checked')) {
  		  $("#warehouseDiv").show();
  	    }else {
  	      $("#warehouse").val('');
  		  $("#warehouseDiv").hide();
  	  }
    });
    	
});