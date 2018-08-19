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
            	
            	alert(JSON.stringify(jqXHR.exception));
             	$("#exceptionSpan").show().text(jqXHR.responseText.exception).css("color","red");
            	//bootbox.alert("Something went wrong, try after refreshing page.");
            }
        });
        return xhr;
    };
    
    
    $('#syncBtn').on('click', function(e) {
		var request={};
		request.divisionId=$("input[name='divisionId']" ).val();
    	JSContext.ajaxCall("/sd/schedular/start", request, function(data) {
    		
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
    
    
    $('#editBtn').on('click', function(e) {
    	$("#frequencySpan").hide();
    	var request={};
		request.divisionId=$("input[name='divisionId']" ).val();
		
    	JSContext.ajaxCall("/sd/connector/getPreferences", request, function(data) {
            if (data) {
                if (data.status && data.status != "SUCCESS") {
                    bootbox.alert(data.errors[0].errorCode + " : " + data.errors[0].errorMessage);
                } else {
                	$(".loading-div").hide();
                	if(data.salesOrderCheckValue==1){
                		$('#syncSalesOrder').prop('checked', true);
                		$("#warehouseDiv").show();
                	}else{
                		$("#warehouseDiv").hide();
                	}
                	if(data.customerCheckValue==1){
                		$('#syncCustomers').prop('checked', true);
                	}
                	if(data.productsCheckValue==1){
                		$('#syncProducts').prop('checked', true);
                	}
                	if(data.marketplaceCheckValue==1){
                		$('#syncMarketplaces').prop('checked', true);
                	}
                	$('#warehouse').val(data.warehouse).prop('selected', true);
                	$('input[name="frequency"]').val(data.frequency);
                	$('#frequencyDur').val(data.frequencyDur).prop('selected', true);
                	$('#startSyncDateLabel').html(data.startSyncDate);
                	$('#myModal').modal('show');
                }
            } else {
//                bootbox.alert("Something went wrong :(");
            }
        }, undefined, config);
    	
    });
    
    $('#syncSalesOrder').click(function() {
    	if ($(this).is(':checked')) {
  		  $("#warehouseDiv").show();
  		  $('#startSyncDiv').css('margin-top',5);
  		  $('#footerButtonsDiv').css('margin-top',40);
  		  $('#frequencyDiv').css('margin-top',57);
  		  if ($("input[name='frequency']" ).val()=="") {
  			$('#startSyncDiv').css('margin-top',17);
  		  }
  	    }else {
  	      $('#startSyncDiv').css('margin-top',0);
  	      $('#footerButtonsDiv').css('margin-top',82);
  	      $('#frequencyDiv').css('margin-top',10);
  	      if ($("input[name='frequency']" ).val()=="") {
  			$('#startSyncDiv').css('margin-top',17);
  		  }
  	      $("#warehouse").val('');
  		  $("#warehouseDiv").hide();
  	  }
    });
    
    $('#updateBtn').on('click', function(e) {
    	if ($("input[name='frequency']" ).val()=="") {
    		$("#frequencySpan").show().text("Please enter Frequency.").css("color","red");
    		$('#startSyncDiv').css('margin-left',1);
    		$('#startSyncDiv').css('margin-top',17);
    		return false;
        }else{
        	$("#frequencySpan").hide();
        	$('#startSyncDiv').css('margin-left',0);
        }
    	
    	var salesOrderCheckValue = $("#syncSalesOrder").is(':checked')?1:0;
    	var productsCheckValue = $("#syncProducts").is(':checked')?1:0;
    	var customerCheckValue = $("#syncCustomers").is(':checked')?1:0;
    	var marketplaceCheckValue = $("#syncMarketplaces").is(':checked')?1:0;
    	
    	var request = {};
        request.divisionId = $("input[name='divisionId']").val();
        request.frequency=$("input[name='frequency']").val();
        request.frequencyDur=$('select[name=frequencyDur]').val();
        request.warehouse=$('select[name=warehouse]').val();
        
        request.salesOrderCheckValue = salesOrderCheckValue;
        request.productsCheckValue = productsCheckValue;
        request.customerCheckValue = customerCheckValue;
        request.marketplaceCheckValue = marketplaceCheckValue;
        
        JSContext.ajaxCall("/sd/connector/edit", request, function(data) {
            $(".loading-div").show();
			 $('#myModal').modal('hide');
            if (data) {
                if (data.status && data.status != "SUCCESS") {
                    bootbox.alert(data.errors[0].errorCode + " : " + data.errors[0].errorMessage);
                } else {
                    bootbox.alert("Data saved successfully.");
                }
            } else {
//                bootbox.alert("Something went wrong :(");
            }
            $(".loading-div").hide();
        }, undefined, config);

    });
    
    
    
    $('#stopSyncBtn').on('click', function(e) {
    	var request={};
		request.divisionId=$("input[name='divisionId']" ).val();
		
    	JSContext.ajaxCall("/sd/connector/schedular/stop", request, function(data) {
            if (data) {
            	$(".loading-div").hide();
                if (data.status && data.status != "SUCCESS") {
                    bootbox.alert(data.errors[0].errorCode + " : " + data.errors[0].errorMessage);
                } else {
                	
                	bootbox.alert("Sync stop successfully.");
                }
            } else {
//                bootbox.alert("Something went wrong :(");
            }
        }, undefined, config);
    	
    });
    
    
    
$('#frequency').on("input",function () {$("#frequencySpan").hide();});
    
});

