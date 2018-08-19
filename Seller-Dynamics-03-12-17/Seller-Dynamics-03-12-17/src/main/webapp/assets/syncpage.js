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
            url: "/sd/connector/sync",
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
    
    
    $('#syncBtn').on('click', function(e) {
    	$("#fetchSpan").hide();
    	$("#waySpan").hide();
    	if ($('select[name=fetchType]').val()=="") {
    		$("#fetchSpan").show().text("Please select fetch type.").css("color","red");
    		return false;
        } 
    	if ($('select[name=selectWay]').val()=="") {
    		$("#waySpan").show().text("Please select sync way.").css("color","red");
    		return false;
        } 
    	
    		var request={};
    		request.divisionId=$("input[name='divisionId']" ).val();
		 	/*request.selectWay=$('select[name=selectWay]').val();
	        request.fetchType=$('select[name=fetchType]').val();	        
	        request.warehouse=$('select[name=warehouse]').val();*/
	        
	    	JSContext.ajaxCall("", request, function(data) {
	    		
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
        	
});