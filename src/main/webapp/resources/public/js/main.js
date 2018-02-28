// Add String format function to String objectß
if (!String.prototype.format) {
	String.prototype.format = function() {
		var args = arguments;
		return this.replace(/{(\d+)}/g, function(match, number) {
			return typeof args[number] != 'undefined' ? args[number] : match;
		});
	};
}

(function($) {
	console.log("Came JS")
	var loadSourceFlag = false;
	var loadLocationFlag = false;
	var loadEventFlag = false;
	var dashboard_card_template = '<div class="card-box">'
			+ '<div class="card-box__container">'
			+ '<div class="accommodation_name">'
			+ '<h3 class="title">'
			+ '<img class="icon accomm-icon" src="resources/public/img/icons/svg/003-house.svg" alt=""> {0}'
			+ '</h3>' + '</div>' + '<div class="data_info">'
			+ '<div class="usage_item">' + '<span class="usage_title">'
			+ 'Electricity :' + '</span>' + '<span class="usage_total">'
			+ '{1} <i class="unit">kwh</i>' + '</span>' + '</div>'
			+ '<div class="usage_item">' + '<span class="usage_title">'
			+ 'Water : ' + '</span>' + '<span class="usage_total">'
			+ '{2} <i class="unit">m<sup>3</sup></i>' + '</span>' + '</div>'
			+ '</div>' + '</div>' + '</div>';

	var source_card_template = '<div class="card-box">'
			+ '<div class="card-box__container">'
			/*
			 * + '<div class="action-box slideTop">' + '<li class="action-item">' + '<button
			 * class="action-btn delete-btn">' + '<img
			 * src="/resources/public/img/icons/svg/001-delete-button.svg"
			 * class="action-icon"/>' + '</button>' + '</li>' + '<li class="action-item">' + '<button
			 * class="action-btn edit-btn">' + '<img
			 * src="/resources/public/img/icons/svg/002-edit.svg"
			 * class="action-icon"/>' + '</button>' + '</li>' + '</div>'
			 */
			+ '<div class="accommodation_name">'
			+ '<h3 class="title">'
			+ '<img class="icon accomm-icon" src="/resources/public/img/icons/svg/003-house.svg" alt="">'
			+ 'Room : {0}' + '</h3>' + '</div>' + '<div class="data_info">'
			+ '<div class="usage_item">' + '<span class="usage_title">'
			+ 'Electricity :' + '</span>' + '<span class="usage_total">'
			+ '{1} <i class="unit">kwh</i>' + '</span>' + '</div>'
			+ '<div class="usage_item">' + '<span class="usage_title">'
			+ 'Water : ' + '</span>' + '<span class="usage_total">'
			+ '{2} <i class="unit">m<sup>3</sup></i>' + '</span>' + '</div>'
			+ '</div>' + '</div>' + '</div>';

	var event_card_template = '<div class="card-box">'
			+ '<div class="card-box__container">'
			+ '<div class="accommodation_name">'
			+ '<h3 class="title">'
			+ '<img class="icon accomm-icon" src="/resources/public/img/icons/svg/003-house.svg" alt=""> {0}'
			+ '</h3>' + '</div>' + '<div class="data_info">'
			+ '<div class="usage_item">' + '<span class="usage_title">'
			+ 'Electricity Alert:' + '</span>' + '<span class="usage_total">'
			+ '{1} <i class="unit">kwh</i>' + '</span>' + '</div>'
			+ '<div class="usage_item">' + '<span class="usage_title">'
			+ 'Water Alert: ' + '</span>' + '<span class="usage_total">'
			+ '{2} <i class="unit">m<sup>3</sup></i>' + '</span>' + '</div>'
			+ '<div class="usage_item">' + '<span class="usage_title">'
			+ 'Alert Email: ' + '</span>' + '<span class="usage_total">'
			+ '{3} </span>' + '</div>' + '</div>' + '</div>' + '</div>';

	var region_div_template = '<div class="card-box__outter uk-flex uk-flex-wrap uk-flex-wrap-around" id="{0}"></div>';

	$(window).on(
			'load',
			function() {
				$('.loading-spinner').addClass('active');
				$.ajax({
					url : 'dash',
					type : 'POST',
					method : 'POST',
					success : function(response) {
						$('.loading-spinner').removeClass('active');
						var data = response.data;

						for (var i = 0; i < data.length; i++) {
							var rendered_card = dashboard_card_template.format(
									data[i].region_name, data[i].power,
									data[i].water);
							$('#dashboardDiv').append(rendered_card);
						}
					},
					error : function(error) {
						console.log(error);
						alert('Error loading dashboard data.');
						$('.loading-spinner').removeClass('active');
					}
				});
			});

	var loadSourceData = function() {
		if (!loadSourceFlag) {
			$('.loading-spinner').addClass('active');
			$
					.ajax({
						url : 'sourceload',
						type : 'POST',
						method : 'POST',
						success : function(response) {
							$('.loading-spinner').removeClass('active');
							$('.accommodation_list').html('');
							$('#regionDiv').html('');
							var data = response.data;
							var uniqueRegion = {};

							for (var i = 0; i < data.length; i++) {
								var region_id = data[i].region_id, region_name = data[i].region_name, room = data[i].room, power = data[i].power, water = data[i].water;
								var regionDivID = "region" + region_id;
								var regionLink = '<li><a href="#">'
										+ region_name + '</a></li>';
								if (!uniqueRegion.hasOwnProperty(region_id)) {
									uniqueRegion[region_id] = region_name;
									var regionDiv = region_div_template
											.format(regionDivID);

									$('.accommodation_list').append(regionLink);
									$('#regionDiv').append(regionDiv);
								}
								var rendered_card = source_card_template
										.format(room, power, water);
								$('#' + regionDivID).append(rendered_card);
							}

							loadSourceFlag = true;
						},
						error : function(error) {
							console.log(error);
							alert('Error loading source data.');
							$('.loading-spinner').removeClass('active');
						}
					});
		}
	}

	var getLocation = function() {
		if (!loadLocationFlag) {
			$('.loading-spinner').addClass('active');
			$.ajax({
				url : 'exportloc',
				type : 'POST',
				success : function(response) {
					data = response.data;

					var selected_val;
					$('.select_location').html('');
					var option_template = '<option value="{0}">{1}</option>';
					for (i = 0; i < data.length; i++) {
						$('.select_location').append(
								option_template
										.format(data[i].id, data[i].name));
						if (i == 0) {
							selected_val = data[i].id;
						}
					}
					$('.select_location').val(selected_val).change();
					loadLocationFlag = true;
					$('.loading-spinner').removeClass('active');
				},
				error : function(error) {
					console.log('error loading location');
					alert("error loading location");
					$('.loading-spinner').removeClass('active');
				}
			});
		}

	}

	var getEvents = function() {
		if (!loadEventFlag) {
			$('.loading-spinner').addClass('active');
			$.ajax({
				url : 'getEvents',
				type : 'GET',
				success : function(response) {
					data = response.data;

					$('#eventListDiv').html('');
					for (var i = 0; i < data.length; i++) {
						var rendered_card = event_card_template.format(
								data[i].roomName, data[i].over_usage_power,
								data[i].over_usage_water, data[i].email);

						$('#eventListDiv').append(rendered_card);
					}
					loadEventFlag = true;
					$('.loading-spinner').removeClass('active');
				},
				error : function(error) {
					console.log('error loading event');
					$('.loading-spinner').removeClass('active');
				}
			});
		}
	}
	/*var getBoardBoard = '<div class="section_content board">'
		+ '<div class="region_from_flex uk-margin">'
		+ '<div class="uk-flex uk-flex-wrap uk-flex-wrap-around uk-flex-between uk-form-stacked">'
		+ '<div class="input-item">'
		+ '<label class="uk-form-label" for="board_location">' + ' Location: ' + '</label>'
		+ '<div class="uk-form-controls">'
		+ '<select class="uk-select select_location" name="location" id="board_location">'
		+ '</select>' + '</div>' + '</div>'
		+ '<div class="input-item">'
		+ '<label class="uk-form-label" for="board_region">'
		+ 'Region:</label>'
		+ '<div class="uk-form-controls">'
		+ '<select class="uk-select select_region" name="region" id="board_region">'
		+ '</select>' + '</div>' + '</div>'
		+ '<div class="input-item">'
		+ '<label class="uk-form-label" for="board_room">' + 'Room' + '</label>'
		+ '<div class="uk-form-controls">'
		+ '<select class="uk-select select_room" name="rroom" id="board_room">'
		+ '</select>' + '</div>' + '</div>'
		+ '<div class="input-item">'
		+ '<label class="uk-form-label" for="board_room">'
		+ 'BoardMAC' + '</label>'
		+ '<div class="uk-form-controls">'
		+ '<input type="text"  name="boardmac" class="uk-input" id="board_room">'
		+ '</select>' + '</div>' + '</div>' + '</div>' + '</div>'
		+ '<div class="uk-text-center">'
		+ '<button id="register" class="uk-button uk-button-primary" type="button">'
		+ 'Register' + '</button>'
		+ '</div>' + '</div>'*/

	$('#clearCacheSource').on('click', function(e) {
		loadSourceFlag = false;

		loadSourceData();
	});

	$('#sourceMenu').on('click', function(e) {
		e.preventDefault();
		loadSourceData();
	});

	$('#exportMenu, #eventMenu').on('click', function(e) {
		e.preventDefault();
		getLocation();
	});

	$('#eventMenu').on('click', function(e) {
		e.preventDefault();
		getEvents();
	});

	$('#boardRegistration').on('click', function(e) {
		e.preventDefault();
		getLocation();
	});

	$('.select_location')
			.on(
					'change',
					function(e) {
						var location_id = $(this).val();
						$
								.ajax({
									url : 'exportreg',
									tranditional : true,
									type : 'GET',
									data : {
										"id" : location_id
									},
									success : function(response) {
										var data = response.data;
										var selected_val;

										$('.select_region').html('');
										$('.select_room').html('');
										var option_template = '<option value="{0}">{1}</option>';
										for (i = 0; i < data.length; i++) {
											$('.select_region').append(
													option_template.format(
															data[i].id,
															data[i].name));
											if (i == 0) {
												selected_val = data[i].id;
											}
										}

										$('.select_region').val(selected_val)
												.change();
									}
								});
					});

	$('.select_region')
			.on(
					'change',
					function(e) {
						var region_id = $(this).val();
						$
								.ajax({
									url : 'exportroom',
									tranditional : true,
									type : 'GET',
									data : {
										"id" : region_id
									},
									success : function(response) {
										data = response.data;

										$('.select_room').html('');
										var option_template = '<option value="{0}">{1}</option>';
										for (i = 0; i < response.data.length; i++) {
											$('.select_room').append(
													option_template.format(
															data[i].id,
															data[i].id));
										}
									}
								});
					});

	$('#export_form').on(
			'submit',
			function(e) {
				e.preventDefault();
				var room_id = $('#export_room').val();
				var export_from = $('#export_from').val();
				var export_until = $('#export_until').val();

				var row_template = '<tr>' + '<td>{0}</td>' + '<td>{1}</td>'
						+ '<td>{2}</td>' + '<td>{3}</td>' + '</tr>';

				$.ajax({
					url : 'exportoutput',
					tranditional : true,
					type : 'POST',
					data : {
						"id" : room_id,
						"export_from" : export_from,
						"export_until" : export_until
					},
					success : function(response) {

						var data = response.data;
						console.log(data);
						$('#export_table').html('');
						$('#export_table').append(
								row_template.format(data.id, data.name,
										data.power, data.water));
						$("#roomExportBtn").removeClass("uk-hidden");
					},
					error : function(error) {
						console.log('error loading location');
						alert("error creating event");
						$("#roomExportBtn").addClass("uk-hidden");
						$('.loading-spinner').removeClass('active');
					}
				});

			});

	$('.source #sidebarTrigger').on('click', function(e) {
		e.preventDefault();
		$('.source .sidebar').toggleClass('active');
	});

	$('.event #sidebarTrigger').on('click', function(e) {
		e.preventDefault();
		$('.event .sidebar').toggleClass('active');
	});

	$('#roomExportBtn').on('click', function(e) {
		e.preventDefault();
		$('#room_export_table').tableExport({
			type : 'pdf',
			jspdf : {
				orientation : 'l',
				format : 'a3',
				margins : {
					left : 10,
					right : 10,
					top : 20,
					bottom : 20
				},
				autotable : {
					styles : {
						fillColor : 'inherit',
						textColor : 'inherit'
					},
					tableWidth : 'auto'
				}
			}
		});
	});

	$("#createEventBtn").click(function() {
		var water = $("#waterr").val();
		var power = $("#electricc").val();
		var room = $("#event_room").val();
		var message = $("#messagee").val();
		var email = $("#emaill").val();
		var data = {
			'water' : water,
			'power' : power,
			'room' : room,
			'message' : message,
			'email' : email
		}
		swal('Successfully', ' ', 'success')

		console.log(data)
		$('.loading-spinner').addClass('active');
		$.ajax({
			url : 'jobEvent',
			type : 'POST',
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			data : JSON.stringify(data),
			success : function(response) {

				$('.loading-spinner').removeClass('active');
				UIkit.modal('#event_form_modal').hide();
			},
			error : function(error) {
				console.log('error loading location');

				$('.loading-spinner').removeClass('active');
			}

		});
	});

	/*Board-board*/

	$('#boardboard').on('submit', function(e) {
		e.preventDefault();

		var id = $('#board_room').val();
		var MAC = $('#board_mac').val();

		swal('Successfully', ' ', 'success')
		$.ajax({
			url : 'boardboard',
			tranditional : true,
			type : 'POST',
			data : {
				"room_ID" : id,
				"mac" : MAC
			},
			success : function(response) {

				var data = response.data;
				$('#board_mac').val("");
				console.log(data);
			},
			error : function(error) {
				console.log('error regsiter board');
				$('.loading-spinner').removeClass('active');
			}
		});

	});

	$('#boardroom').on('submit', function(e) {
		e.preventDefault();

		var regionID = $('#board_region1').val();
		var roomID = $('#board_roomid1').val();
		var roomName = $('#board_roomname1').val();
		swal('Successfully', ' ', 'success')
		$.ajax({
			url : 'boardroom',
			tranditional : true,
			type : 'POST',
			data : {
				"region_ID" : regionID,
				"room_ID" : roomID,
				"room_Name" : roomName
			},
			success : function(response) {
				console.log(response);
				var data = response.data;
				var roomID = $('#board_roomid1').val("");
				var roomName = $('#board_roomname1').val("");
				alert(response.message);

			},
			error : function(error) {
				console.log('error regsiter board');
				$('.loading-spinner').removeClass('active');
			}
		});

	});

	$('#boardregion').on('submit', function(e) {
		e.preventDefault();

		var regionID = $('#board_regionid2').val();
		var regionName = $('#board_regionname2').val();
		var locationID = $('#board_location2').val();
		swal('Successfully', ' ', 'success')
		$.ajax({
			url : 'boardregion',
			tranditional : true,
			type : 'POST',
			data : {
				"region_ID" : regionID,
				"region_Name" : regionName,
				"location_ID" : locationID
			},
			success : function(response) {

				var data = response.data;
				var regionID = $('#board_regionid2').val("");
				var regionName = $('#board_regionname2').val("");
				console.log(data);
			},
			error : function(error) {
				console.log('error regsiter board');
				$('.loading-spinner').removeClass('active');
			}
		});

	});
	$('#boardlocation').on('submit', function(e) {
		e.preventDefault();

		var locationID = $('#board_locationid3').val();
		var locationName = $('#board_locationname3').val();
		swal('Successfully', ' ', 'success')
		$.ajax({
			url : 'boardlocation',
			tranditional : true,
			type : 'POST',
			data : {
				"location_Name" : locationName,
				"location_ID" : locationID
			},
			success : function(response) {

				var data = response.data;
				var locationID = $('#board_locationid3').val("");
				var locationName = $('#board_locationname3').val("");
				console.log(data);
			},
			error : function(error) {
				console.log('error regsiter board');
				alert("error creating event")
				$('.loading-spinner').removeClass('active');
			}
		});

	});

	$('#emaill')
			.on(
					'keypress',
					function() {
						var re=/^(?=[^@]{4,}@)([\w\.-]*[a-zA-Z0-9_]@(?=.{4,}\.[^.]*$)[\w\.-]*[a-zA-Z0-9]\.[a-zA-Z][a-zA-Z\.]*[a-zA-Z])$/;
						var email = $(this).val();
						var result = re.test(email.toLowerCase());
						if (!result) {
							$(this).addClass('uk-form-danger');

						} else {
							$(this).removeClass('uk-form-danger');
						}
					});

})(jQuery);
