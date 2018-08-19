if (!JSContext) {
    var JSContext = {};
}
config = {
    contentType: "application/json",
    stringify: true
}
$("document").ready(function() {
    JSContext.errors = undefined;
    JSContext.ajaxCall = function(api, payload, onSuccess, onError, config, nospin) {
        var cType = undefined;
        var asyncVar = true;
        var hideUI = false;
        var timeOut = undefined;
        var pData = undefined;
        var ca = undefined;
        var dType = "json";
        var ctx = document.body;
        if (config) {
            hideUI = config.hideUI;
            if (config.contentType != undefined) {
                cType = config.contentType;
            }
            if (config.async == false) {
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

            if ("setundefined" == config.context) {
                ctx = undefined;
            }
        }

        if (!hideUI) {
            if (!nospin)
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
                if (!nospin)
                    $(".loading-div").hide();

                if (!resp) {
                    JSContext.showMessage(JSContext.COMMON_ERROR_MSG, 'error');

                } else if (resp.apiResponseStatus && resp.apiResponseStatus != "SUCCESS") {
                    if (onError) {
                        onError(resp);
                    } else {
                        var errors = JSContext.retriveErrors(resp);
                        JSContext.errors = errors;
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

    $('#submitBtn').on('click', function(e) {
        $('#calForm').bootstrapValidator('validate');
        if ($('#calForm').data('bootstrapValidator').isValid()) {
            var request = {};
            request.divisionId = $("input[name='divisionId']").val();
            request.totalWidth = $("input[name='twidth']").val();
            request.totalHeight = $("input[name='theight']").val();
            request.lathSize = $("select[name='lsize']").val();
            request.orientation = $("input[name='mo']").val();
            request.color = $("select[name='lcolor']").val();
            request.canopy = $("select[name='copyRequired']").val();
            request.canopyColor = $("select[name='copyColor']").val();
            request.guide = $("select[name='guidecolor']").val();
            request.plateSize = $("select[name='endplateSize']").val();
            /*if ($("select[name='additionalItem']").val() != "") {
            	request.additionals = $("select[name='additionalItem']").val();
            }*/
            //request.invoiceRaised=$( "input[name='ir']" ).val();
            //request.depositeReceived=$( "input[name='dr']" ).val();
            request.orderBy = $("select[name='orderBy']").val();
            request.manufacturingDate = $("#datepicker").datepicker('getDate');
            request.description = $("input[name='comments']").val();
            request.dateRequired = $("#datepicker1").datepicker('getDate');
            request.mount = $("select[name='ip']").val();
            request.orientation = $("select[name='mo']").val();
            request.comment = $("input[name='comments']").val();
            request.curtain = $("select[name='curtainStr']").val();
            
//            request.raisedBy = $("input[name='raisedBy']").val();
            request.manualOverride = $("select[name='manualOverride']").val();
            JSContext.ajaxCall("/euroll/quotation/review", request, function(data) {
                $(".loading-div").show();
                if (data) {
                    //if (data.status && data.status != "SUCCESS") {
                    $('#myModal').modal('show');
                    $("#itemCode").text(data.itemDTO.itemCode);
                    $("#netCost").html('&#163;' + (data.itemDTO.netCost).toFixed(2));
                    $("#curtainNetCost").html('&#163;' + (data.bomSummaryList[0].netCost).toFixed(2));
                    $("#barrelNetCost").html('&#163;' + (data.bomSummaryList[1].netCost).toFixed(2));
                    $("#canopyNetCost").html('&#163;' + (data.bomSummaryList[2].netCost).toFixed(2));
                    $("#guidesNetCost").html('&#163;' + (data.bomSummaryList[3].netCost).toFixed(2));
                    $("#kitNetCost").html('&#163;' + (data.bomSummaryList[4].netCost).toFixed(2));

                    $("#zarQuantity").html(data.bomSummaryList[0].bomItems[0].quantity.toFixed(2))
                    $("#zarUnit").html(data.bomSummaryList[0].bomItems[0].unit)
                    $("#zarUnitCost").html('&#163;' + (data.bomSummaryList[0].bomItems[0].unitCost).toFixed(2));
                    $("#zarTotalCost").html('&#163;' + ((data.bomSummaryList[0].bomItems[0].quantity) * (data.bomSummaryList[0].bomItems[0].unitCost)).toFixed(2));

					$("#1").text(data.bomSummaryList[0].bomItems[0].itemCode);
					$("#2").text(data.bomSummaryList[0].bomItems[1].itemCode);
					$("#3").text(data.bomSummaryList[0].bomItems[2].itemCode);
					$("#4").text(data.bomSummaryList[1].bomItems[0].itemCode);
					$("#5").text(data.bomSummaryList[1].bomItems[1].itemCode);
//					$("#6").text(data.bomSummaryList[1].bomItems[2].itemCode);
					$("#7").text(data.bomSummaryList[1].bomItems[2].itemCode);
					$("#8").text(data.bomSummaryList[1].bomItems[3].itemCode);
					$("#9").text(data.bomSummaryList[2].bomItems[0].itemCode);
					$("#10").text(data.bomSummaryList[2].bomItems[1].itemCode);
					$("#11").text(data.bomSummaryList[2].bomItems[2].itemCode);
//					if ($("select[name='lsize']").val()!="_55") {
					$("#15").text(data.bomSummaryList[3].bomItems[0].itemCode);
					$("#16").text(data.bomSummaryList[3].bomItems[1].itemCode);
//					}
					$("#17").text(data.bomSummaryList[4].bomItems[0].itemCode);
					$("#18").text(data.bomSummaryList[4].bomItems[1].itemCode);
					$("#19").text(data.bomSummaryList[4].bomItems[2].itemCode);
					$("#20").text(data.bomSummaryList[4].bomItems[3].itemCode);
					$("#21").text(data.bomSummaryList[4].bomItems[4].itemCode);

                    $("#zesQuantity").html(data.bomSummaryList[0].bomItems[1].quantity.toFixed(2))
                    $("#zesUnit").html(data.bomSummaryList[0].bomItems[1].unit)
                    $("#zesUnitCost").html('&#163;' + (data.bomSummaryList[0].bomItems[1].unitCost).toFixed(2));
                    $("#zesTotalCost").html('&#163;' + ((data.bomSummaryList[0].bomItems[1].quantity) * (data.bomSummaryList[0].bomItems[1].unitCost)).toFixed(2));

                    $("#zspQuantity").html(data.bomSummaryList[0].bomItems[2].quantity.toFixed(2));
                    $("#zspUnit").html(data.bomSummaryList[0].bomItems[2].unit);
                    $("#zspUnitCost").html('&#163;' + (data.bomSummaryList[0].bomItems[2].unitCost).toFixed(2));
                    $("#zspTotalCost").html('&#163;' + ((data.bomSummaryList[0].bomItems[2].quantity) * (data.bomSummaryList[0].bomItems[2].unitCost)).toFixed(2));

                    $("#tubeQuantity").html(data.bomSummaryList[1].bomItems[0].quantity.toFixed(2));
                    $("#tubeUnit").html(data.bomSummaryList[1].bomItems[0].unit);
                    $("#tubeUnitCost").html('&#163;' + (data.bomSummaryList[1].bomItems[0].unitCost).toFixed(2));
                    $("#tubeTotalCost").html('&#163;' + ((data.bomSummaryList[1].bomItems[0].quantity) * (data.bomSummaryList[1].bomItems[0].unitCost)).toFixed(2));

                    $("#motorQuantity").html(data.bomSummaryList[1].bomItems[1].quantity.toFixed(2));
                    $("#motorUnit").html(data.bomSummaryList[1].bomItems[1].unit);
                    $("#motorUnitCost").html('&#163;' + (data.bomSummaryList[1].bomItems[1].unitCost).toFixed(2));
                    $("#motorTotalCost").html('&#163;' + ((data.bomSummaryList[1].bomItems[1].quantity) * (data.bomSummaryList[1].bomItems[1].unitCost)).toFixed(2));

//                    $("#hangerQuantity").html(data.bomSummaryList[1].bomItems[2].quantity.toFixed(2));
//                    $("#hangerUnit").html(data.bomSummaryList[1].bomItems[2].unit);
//                    $("#hangerUnitCost").html('&#163;' + (data.bomSummaryList[1].bomItems[2].unitCost).toFixed(2));
//                    $("#hangerTotalCost").html('&#163;' + ((data.bomSummaryList[1].bomItems[2].quantity) * (data.bomSummaryList[1].bomItems[2].unitCost)).toFixed(2));


                    $("#hangersQuantity").html(data.bomSummaryList[1].bomItems[2].quantity.toFixed(2));
                    $("#hangersUnit").html(data.bomSummaryList[1].bomItems[2].unit);
                    $("#hangersUnitCost").html('&#163;' + (data.bomSummaryList[1].bomItems[2].unitCost).toFixed(2));
                    $("#hangersTotalCost").html('&#163;' + ((data.bomSummaryList[1].bomItems[2].quantity) * (data.bomSummaryList[1].bomItems[3].unitCost)).toFixed(2));

                    $("#dummyQuantity").html(data.bomSummaryList[1].bomItems[3].quantity.toFixed(2));
                    $("#dummyUnit").html(data.bomSummaryList[1].bomItems[3].unit);
                    $("#dummyUnitCost").html('&#163;' + (data.bomSummaryList[1].bomItems[3].unitCost).toFixed(2));
                    $("#dummyTotalCost").html('&#163;' + ((data.bomSummaryList[1].bomItems[3].quantity) * (data.bomSummaryList[1].bomItems[3].unitCost)).toFixed(2))

                    $("#endplatesQuantity").html(data.bomSummaryList[2].bomItems[0].quantity.toFixed(2))
                    $("#endplatesUnit").html(data.bomSummaryList[2].bomItems[0].unit)
                    $("#endplatesUnitCost").html('&#163;' + (data.bomSummaryList[2].bomItems[0].unitCost).toFixed(2));
                    $("#endplatesTotalCost").html('&#163;' + ((data.bomSummaryList[2].bomItems[0].quantity) * (data.bomSummaryList[2].bomItems[0].unitCost)).toFixed(2));
					$("#weight").text(data.weight.toFixed(2)+" kg");
					if(data.weight<=25){
						$("#bearingHangerTr").show();
						$("#bearingHangerQuantity").html(data.bomSummaryList[2].bomItems[1].quantity.toFixed(2))
						$("#bearingHangerUnit").html(data.bomSummaryList[2].bomItems[1].unit)
						$("#bearingHangerUnitCost").html('&#163;' + (data.bomSummaryList[2].bomItems[1].unitCost).toFixed(2));
						$("#bearingHangerTotalCost").html('&#163;' + ((data.bomSummaryList[2].bomItems[1].quantity) * (data.bomSummaryList[2].bomItems[1].unitCost)).toFixed(2));
						$("#bearingQuantity").html(data.bomSummaryList[2].bomItems[2].quantity.toFixed(2))
						$("#bearingUnit").html(data.bomSummaryList[2].bomItems[2].unit)
						$("#bearingUnitCost").html('&#163;' + (data.bomSummaryList[2].bomItems[2].unitCost).toFixed(2));
						$("#bearingTotalCost").html('&#163;' + ((data.bomSummaryList[2].bomItems[2].quantity) * (data.bomSummaryList[2].bomItems[2].unitCost)).toFixed(2));
						
					if ($("select[name='lsize']").val()!="_77") {
						$("#guideRoller").hide();
						$("#aliminumQuantity").html(data.bomSummaryList[2].bomItems[3].quantity.toFixed(2))
						$("#aliminumUnit").html(data.bomSummaryList[2].bomItems[3].unit)
						$("#aliminumUnitCost").html('&#163;' + (data.bomSummaryList[2].bomItems[3].unitCost).toFixed(2));
						$("#aliminumTotalCost").html('&#163;' + ((data.bomSummaryList[2].bomItems[3].quantity) * (data.bomSummaryList[2].bomItems[3].unitCost)).toFixed(2));
						$("#aliminumBottomQuantity").html(data.bomSummaryList[2].bomItems[4].quantity.toFixed(2));
						$("#aliminumBottomUnit").html(data.bomSummaryList[2].bomItems[4].unit)
						$("#aliminumBottomUnitCost").html('&#163;'+(data.bomSummaryList[2].bomItems[4].unitCost).toFixed(2));
						$("#aliminumBottomTotalCost").html('&#163;'+((data.bomSummaryList[2].bomItems[4].quantity) * (data.bomSummaryList[2].bomItems[4].unitCost)).toFixed(2));
						$("#13").text(data.bomSummaryList[2].bomItems[3].itemCode);
						$("#14").text(data.bomSummaryList[2].bomItems[4].itemCode);
					} else {
						$("#guideRoller").show();
						$("#guideQuantity").html(data.bomSummaryList[2].bomItems[3].quantity.toFixed(2))
						$("#guideUnit").html(data.bomSummaryList[2].bomItems[3].unit)
						$("#guideUnitCost").html('&#163;' + (data.bomSummaryList[2].bomItems[3].unitCost).toFixed(2));
						$("#guideTotalCost").html('&#163;' + ((data.bomSummaryList[2].bomItems[3].quantity) * (data.bomSummaryList[2].bomItems[3].unitCost)).toFixed(2));
						
						$("#aliminumQuantity").html(data.bomSummaryList[2].bomItems[4].quantity.toFixed(2))
						$("#aliminumUnit").html(data.bomSummaryList[2].bomItems[4].unit)
						$("#aliminumUnitCost").html('&#163;' + (data.bomSummaryList[2].bomItems[4].unitCost).toFixed(2));
						$("#aliminumTotalCost").html('&#163;' + ((data.bomSummaryList[2].bomItems[4].quantity) * (data.bomSummaryList[2].bomItems[4].unitCost)).toFixed(2));
						
						$("#aliminumBottomQuantity").html(data.bomSummaryList[2].bomItems[5].quantity.toFixed(2))
						$("#aliminumBottomUnit").html(data.bomSummaryList[2].bomItems[5].unit)
						$("#aliminumBottomUnitCost").html('&#163;' + (data.bomSummaryList[2].bomItems[5].unitCost).toFixed(2));
						$("#aliminumBottomTotalCost").html('&#163;' + ((data.bomSummaryList[2].bomItems[5].quantity) * (data.bomSummaryList[2].bomItems[5].unitCost)).toFixed(2));
						$("#12").text(data.bomSummaryList[2].bomItems[3].itemCode);
						$("#13").text(data.bomSummaryList[2].bomItems[4].itemCode);
						$("#14").text(data.bomSummaryList[2].bomItems[5].itemCode);
					} 
					} else {
						$("#bearingHangerTr").hide();
						$("#bearingQuantity").html(data.bomSummaryList[2].bomItems[1].quantity.toFixed(2))
						$("#bearingUnit").html(data.bomSummaryList[2].bomItems[1].unit)
						$("#bearingUnitCost").html('&#163;' + (data.bomSummaryList[2].bomItems[1].unitCost).toFixed(2));
						$("#bearingTotalCost").html('&#163;' + ((data.bomSummaryList[2].bomItems[1].quantity) * (data.bomSummaryList[2].bomItems[1].unitCost)).toFixed(2));
						if ($("select[name='lsize']").val()!="_77") {
						$("#guideRoller").hide();
						$("#aliminumQuantity").html(data.bomSummaryList[2].bomItems[2].quantity.toFixed(2))
						$("#aliminumUnit").html(data.bomSummaryList[2].bomItems[2].unit)
						$("#aliminumUnitCost").html('&#163;' + (data.bomSummaryList[2].bomItems[2].unitCost).toFixed(2));
						$("#aliminumTotalCost").html('&#163;' + ((data.bomSummaryList[2].bomItems[2].quantity) * (data.bomSummaryList[2].bomItems[2].unitCost)).toFixed(2));
						$("#aliminumBottomQuantity").html(data.bomSummaryList[2].bomItems[3].quantity.toFixed(2));
						$("#aliminumBottomUnit").html(data.bomSummaryList[2].bomItems[3].unit)
						$("#aliminumBottomUnitCost").html('&#163;'+(data.bomSummaryList[2].bomItems[3].unitCost).toFixed(2));
						$("#aliminumBottomTotalCost").html('&#163;'+((data.bomSummaryList[2].bomItems[3].quantity) * (data.bomSummaryList[2].bomItems[3].unitCost)).toFixed(2));
						$("#13").text(data.bomSummaryList[2].bomItems[2].itemCode);
						$("#14").text(data.bomSummaryList[2].bomItems[3].itemCode);
					} else {
						$("#guideRoller").show();
						$("#guideQuantity").html(data.bomSummaryList[2].bomItems[2].quantity.toFixed(2))
						$("#guideUnit").html(data.bomSummaryList[2].bomItems[2].unit)
						$("#guideUnitCost").html('&#163;' + (data.bomSummaryList[2].bomItems[2].unitCost).toFixed(2));
						$("#guideTotalCost").html('&#163;' + ((data.bomSummaryList[2].bomItems[2].quantity) * (data.bomSummaryList[2].bomItems[2].unitCost)).toFixed(2));
						
						$("#aliminumQuantity").html(data.bomSummaryList[2].bomItems[3].quantity.toFixed(2))
						$("#aliminumUnit").html(data.bomSummaryList[2].bomItems[3].unit)
						$("#aliminumUnitCost").html('&#163;' + (data.bomSummaryList[2].bomItems[3].unitCost).toFixed(2));
						$("#aliminumTotalCost").html('&#163;' + ((data.bomSummaryList[2].bomItems[3].quantity) * (data.bomSummaryList[2].bomItems[3].unitCost)).toFixed(2));
						
						$("#aliminumBottomQuantity").html(data.bomSummaryList[2].bomItems[4].quantity.toFixed(2))
						$("#aliminumBottomUnit").html(data.bomSummaryList[2].bomItems[4].unit)
						$("#aliminumBottomUnitCost").html('&#163;' + (data.bomSummaryList[2].bomItems[4].unitCost).toFixed(2));
						$("#aliminumBottomTotalCost").html('&#163;' + ((data.bomSummaryList[2].bomItems[4].quantity) * (data.bomSummaryList[2].bomItems[4].unitCost)).toFixed(2));
						$("#12").text(data.bomSummaryList[2].bomItems[2].itemCode);
						$("#13").text(data.bomSummaryList[2].bomItems[3].itemCode);
						$("#14").text(data.bomSummaryList[2].bomItems[4].itemCode);
					} 
					}

					
					/*if ($("select[name='lsize']").val()!="_55") {*/
						$("#guidewithBrushTd").show();
						$("#gwbQuantity").html(data.bomSummaryList[3].bomItems[0].quantity.toFixed(2))
						$("#gwbUnit").html(data.bomSummaryList[3].bomItems[0].unit)
						$("#gwbUnitCost").html('&#163;' + (data.bomSummaryList[3].bomItems[0].unitCost).toFixed(2));
						$("#gwbTotalCost").html('&#163;' + ((data.bomSummaryList[3].bomItems[0].quantity) * (data.bomSummaryList[3].bomItems[0].unitCost)).toFixed(2));
						$("#screwQuantity").html(data.bomSummaryList[3].bomItems[1].quantity.toFixed(2))
						$("#16").text(data.bomSummaryList[3].bomItems[1].itemCode);
						$("#screwUnit").html(data.bomSummaryList[3].bomItems[1].unit)
						$("#screwUnitCost").html('&#163;' + (data.bomSummaryList[3].bomItems[1].unitCost).toFixed(2));
						$("#screwTotalCost").html('&#163;' + ((data.bomSummaryList[3].bomItems[1].quantity) * (data.bomSummaryList[3].bomItems[1].unitCost)).toFixed(2));
					/*} else {
						$("#guidewithBrushTd").hide();
						$("#screwQuantity").html(data.bomSummaryList[3].bomItems[0].quantity.toFixed(2));
						$("#screwUnit").html(data.bomSummaryList[3].bomItems[0].unit);
						$("#16").text(data.bomSummaryList[3].bomItems[0].itemCode);
						$("#screwUnitCost").html('&#163;' + (data.bomSummaryList[3].bomItems[0].unitCost).toFixed(2));
						$("#screwTotalCost").html('&#163;' + ((data.bomSummaryList[3].bomItems[0].quantity) * (data.bomSummaryList[3].bomItems[0].unitCost)).toFixed(2));
					}*/
                  

                    $("#controlQuantity").html(data.bomSummaryList[4].bomItems[0].quantity.toFixed(2))
                    $("#controlUnit").html(data.bomSummaryList[4].bomItems[0].unit)
                    $("#controlUnitCost").html('&#163;' + (data.bomSummaryList[4].bomItems[0].unitCost).toFixed(2));
                    $("#controlTotalCost").html('&#163;' + ((data.bomSummaryList[4].bomItems[0].quantity) * (data.bomSummaryList[4].bomItems[0].unitCost)).toFixed(2));

                    $("#wirelessQuantity").html(data.bomSummaryList[4].bomItems[1].quantity.toFixed(2))
                    $("#wirelessUnit").html(data.bomSummaryList[4].bomItems[1].unit)
                    $("#wirelessUnitCost").html('&#163;' + (data.bomSummaryList[4].bomItems[1].unitCost).toFixed(2));
                    $("#wirelessTotalCost").html('&#163;' + ((data.bomSummaryList[4].bomItems[1].quantity) * (data.bomSummaryList[4].bomItems[1].unitCost)).toFixed(2));

                    $("#serQuantity").html(data.bomSummaryList[4].bomItems[2].quantity.toFixed(2))
                    $("#serUnit").html(data.bomSummaryList[4].bomItems[2].unit)
                    $("#serUnitCost").html('&#163;' + (data.bomSummaryList[4].bomItems[2].unitCost).toFixed(2));
                    $("#serTotalCost").html('&#163;' + ((data.bomSummaryList[4].bomItems[2].quantity) * (data.bomSummaryList[4].bomItems[2].unitCost)).toFixed(2));

                    $("#manualQuantity").html(data.bomSummaryList[4].bomItems[3].quantity.toFixed(2))
                    $("#manualUnit").html(data.bomSummaryList[4].bomItems[3].unit)
                    $("#manualUnitCost").html('&#163;' + (data.bomSummaryList[4].bomItems[3].unitCost).toFixed(2));
                    $("#manualTotalCost").html('&#163;' + ((data.bomSummaryList[4].bomItems[3].quantity) * (data.bomSummaryList[4].bomItems[3].unitCost)).toFixed(2));
                    $("#manualOverrideQuantity").html(data.bomSummaryList[4].bomItems[4].quantity.toFixed(2))
                    $("#manualOverrideUnit").html(data.bomSummaryList[4].bomItems[4].unit)
                    $("#manualOverrideUnitCost").html('&#163;' + (data.bomSummaryList[4].bomItems[4].unitCost).toFixed(2));
                    $("#manualOverrideTotalCost").html('&#163;' + ((data.bomSummaryList[4].bomItems[4].quantity) * (data.bomSummaryList[4].bomItems[4].unitCost)).toFixed(2));
                    // bootbox.alert(data.errors[0].errorCode + " : " + data.errors[0].errorMessage);}
                    // else {
                    //  bootbox.alert("Quotation and Shop Order created successfully.");
                    //$('#calForm')[0].reset();
                    // }
                } else {
                    bootbox.alert("Something went wrong :(");
                }
                $(".loading-div").hide();
            }, undefined, config);
        } else {
            return;
        }
    });
    $('#createBtn').on('click', function(e) {
        var request = {};
        request.divisionId = $("input[name='divisionId']").val();
        request.totalWidth = $("input[name='twidth']").val();
        request.totalHeight = $("input[name='theight']").val();
        request.lathSize = $("select[name='lsize']").val();
        request.orientation = $("input[name='mo']").val();
        request.color = $("select[name='lcolor']").val();
        request.canopy = $("select[name='copyRequired']").val();
        request.canopyColor = $("select[name='copyColor']").val();
        request.guide = $("select[name='guidecolor']").val();
        request.plateSize = $("select[name='endplateSize']").val();
        /*if ($("select[name='additionalItem']").val() != "") {
        	request.additionals = $("select[name='additionalItem']").val();
        }*/
        //request.invoiceRaised=$( "input[name='ir']" ).val();
        //request.depositeReceived=$( "input[name='dr']" ).val();
        request.orderBy = $("select[name='orderBy']").val();
        request.manufacturingDate = $("#datepicker").datepicker('getDate');
        request.description = $("input[name='comments']").val();
        request.dateRequired = $("#datepicker1").datepicker('getDate');
        request.mount = $("select[name='ip']").val();
        request.orientation = $("select[name='mo']").val();
        request.comment = $("input[name='comments']").val();
//        request.raisedBy = $("input[name='raisedBy']").val();
        request.manualOverride = $("select[name='manualOverride']").val();

        request.shutterType = $("select[name='shutterType']").val();
        request.curtain = $("select[name='curtainStr']").val();
//        request.canopyStr = $("select[name='canopyStr']").val();
        request.operation = $("select[name='operation']").val();
        request.powerSupply = $("select[name='powerSupply']").val();
        request.controlMethod = $("select[name='controlMethod']").val();
        request.finish = $("select[name='finish']").val();
        request.facia = $("select[name='facia']").val();
        request.safetySystem = $("select[name='safetySystem']").val();
        request.leadTime = $("select[name='leadTime']").val();

        JSContext.ajaxCall("/euroll/quotation/calculate", request, function(data) {
            $(".loading-div").show();
			 $('#myModal').modal('hide');
            if (data) {
                if (data.status && data.status != "SUCCESS") {
                    bootbox.alert(data.errors[0].errorCode + " : " + data.errors[0].errorMessage);
                } else {
                    bootbox.alert("Quotation and Shop Order created successfully.");
                    //$('#calForm')[0].reset();
                }
            } else {
                bootbox.alert("Something went wrong :(");
            }
            $(".loading-div").hide();
        }, undefined, config);

    });


    $('#resetBtn').on('click', function(e) {
        $('#calForm').data('bootstrapValidator').resetForm();
    });

    /*$('select[name="lsize"]').change(function(e) {
        if ($(this).val() == "_55") {
            $("#curtain77-div").hide();
            $("#curtain55-div").show();
        } else if ($(this).val() == "_77") {
        	$("#curtain55-div").hide();
        	$("#curtain77-div").show();
        }
    });*/

    /*$('select[name="copyRequired"]').change(function(e) {
        if ($(this).val() == "HALF") {
            $("#canopy-div").show();
        } else if ($(this).val() == "FULL") {
        	$("#canopy-div").show();
        } else if ($(this).val() == "NONE") {
        	$("#canopy-div").hide();
        }
    });*/
    
    $('#calForm').bootstrapValidator({
            fields: {
                orderBy: {
                    validators: {
                        notEmpty: {
                            message: 'Please select order by.'
                        }
                    }
                },
                twidth: {
                    validators: {
                        notEmpty: {
                            message: 'Please provide total width.'
                        }
                    }
                },
                theight: {
                    validators: {
                        notEmpty: {
                            message: 'Please provide total height.'
                        }
                    }
                },
                lsize: {
                    validators: {
                        notEmpty: {
                            message: 'Please select size.'
                        }
                    }
                },
                lcolor: {
                    validators: {
                        notEmpty: {
                            message: 'Please select lath colour.'
                        }
                    }
                },
                copyRequired: {
                    validators: {
                        notEmpty: {
                            message: 'Please select canopy.'
                        }
                    }
                },
                mo: {
                    validators: {
                        notEmpty: {
                            message: 'Please select motor orientation.'
                        }
                    }
                },
                copyColor: {
                    validators: {
                        notEmpty: {
                            message: 'Please select canopy colour.'
                        }
                    }
                },
                guidecolor: {
                    validators: {
                        notEmpty: {
                            message: 'Please select guide colour.'
                        }
                    }
                },
                ip: {
                    validators: {
                        notEmpty: {
                            message: 'Please select internal/external mount.'
                        }
                    }
                },
                endplateSize: {
                    validators: {
                        notEmpty: {
                            message: 'Please select date.'
                        }
                    }
                },
                /*additionalItem: {
                    validators: {
                        notEmpty: {
                            message: 'Please select additional item.'
                        }
                    }
                },*/
                mdate: {
                    validators: {
                        notEmpty: {
                            message: 'Please select date.'
                        }
                    }
                },
                drequired: {
                    validators: {
                        notEmpty: {
                            message: 'Please select date.'
                        }
                    }
                },
                /*comments: {
                    validators: {
                        notEmpty: {
                            message: 'Please enter comments.'
                        }
                    }
                }*/
                shutterType: {
                    validators: {
                        notEmpty: {
                            message: 'Please select shutter type.'
                        }
                    }
                }
//                curtain77: {
//                    validators: {
//                        notEmpty: {
//                            message: 'Please select curtain.'
//                        }
//                    }
//                },
//                curtain55: {
//                    validators: {
//                        notEmpty: {
//                            message: 'Please select curtain.'
//                        }
//                    }
//                },
                /*canopyStr: {
                    validators: {
                        notEmpty: {
                            message: 'Please select canopy.'
                        }
                    }
                }*/
            }
        })
        .on('success.form.bv', function(e) {
            $('#success_message').slideDown({
                opacity: "show"
            }, "slow") // Do something ...
            $('#calForm').data('bootstrapValidator').resetForm();
            // Prevent form submission
            e.preventDefault();
            // Get the form instance
            var $form = $(e.target);
            // Get the BootstrapValidator instance
            var bv = $form.data('bootstrapValidator');

        });
});