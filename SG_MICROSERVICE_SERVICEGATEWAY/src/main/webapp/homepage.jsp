
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta name="generator" content="jqueryform.com">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

<title>Data Ingestor</title>


<!-- Bootstrap -->
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.0/css/bootstrap-datepicker3.min.css"
	rel="stylesheet">


<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

<link href="vendor.css" rel="stylesheet">

<style type="text/css">
body {
	background-color: transparent;
}

.jf-form {
	margin-top: 28px;
}

.jf-option-box {
	display: none;
	margin-left: 8px;
}

.jf-hide {
	display: none;
}

.jf-disabled {
	background-color: #eeeeee;
	opacity: 0.6;
	pointer-events: none;
}

/* 
overwrite bootstrap default margin-left, because the <label> doesn't contain the <input> element.
*/
.checkbox input[type=checkbox], .checkbox-inline input[type=checkbox],
	.radio input[type=radio], .radio-inline input[type=radio] {
	position: absolute;
	margin-top: 4px \9;
	margin-left: 0px;
}

div.form-group {
	padding: 8px 8px 4px 8px;
}

.mainDescription {
	margin-bottom: 10px;
}

.responsive img {
	width: 100%;
}

p.error, p.validation-error {
	padding: 5px;
}

p.error {
	margin-top: 10px;
	color: #a94442;
}

p.server-error {
	font-weight: bold;
}

div.thumbnail {
	position: relative;
	text-align: center;
}

div.thumbnail.selected p {
	color: #ffffff;
}

div.thumbnail .glyphicon-ok-circle {
	position: absolute;
	top: 16px;
	left: 16px;
	color: #ffffff;
	font-size: 32px;
}

.jf-copyright {
	color: #888888;
	display: inline-block;
	margin: 16px;
	display: none;
}

.form-group.required .control-label:after {
	color: #dd0000;
	content: "*";
	margin-left: 6px;
}

.submit .btn.disabled, .submit .btn[disabled] {
	background: transparent;
	opacity: 0.75;
}

/* for image option with span text */
.checkbox label>span, .radio label>span {
	display: block;
}

.form-group.inline .control-label, .form-group.col-1 .control-label,
	.form-group.col-2 .control-label, .form-group.col-3 .control-label {
	display: block;
}

.form-group.inline div.radio, .form-group.inline div.checkbox {
	display: inline-block;
}

.form-group.col-1 div.radio, .form-group.col-1 div.checkbox {
	display: block;
}

.form-group.col-2 div.radio, .form-group.col-2 div.checkbox {
	display: inline-block;
	width: 48%;
}

.form-group.col-3 div.radio, .form-group.col-3 div.checkbox {
	display: inline-block;
	width: 30%;
}
</style>

</head>

<body>


	<!-- ----------------------------------------------- -->
	<div class="container jf-form">
		<form data-licenseKey="" name="jqueryform-96767c"
			id="jqueryform-96767c"
			action='/SG_MICROSERVICE_DATAINGESTOR/webapi/DataIngesterManager/delegate'
			method='get' enctype='multipart/form-data' novalidate
			autocomplete="on">
			<input type="hidden" name="method" value="validateForm"> <input
				type="hidden" id="serverValidationFields"
				name="serverValidationFields" value="">



			<div class="form-group c26 required" data-cid="c26">
				<label class="control-label" for="c26">NEXRAD STATION</label> <select
					class="form-control" id="c26" name="station"
					data-rule-required="true" data-msg-required="SELECT A STATION">
					<option value="">- Select -</option>
					<option value="KABX-Albuquerque, NM">KABX-Albuquerque,NM</option>
					<option value="KCYS-Cheyenne, WY">KCYS-Cheyenne,WY</option>
					<option value="KDAX-Sacramento, CA">KDAX-Sacramento,CA</option>
					<option value="KAMA-Amarillo, TX">KAMA-Amarillo,TX</option>
					<option value="KEAX-Kansas City, MO">KEAX-KansasCity,MO</option>
					<option value="KVBX-Kansas City, MO">KVBX-KansasCity,MO</option>
				</select>


			</div>

			<div class="form-group c32 required" data-cid="c32">
				<label class="control-label" for="c32">DATE</label>

				<div class="input-group date">
					<input type="text" class="form-control datepicker" id="c32"
						name="date" value="" data-rule-required="true"
						data-msg-required="SELECT A DATE"
						data-datepicker-format="mm/dd/yyyy"
						data-datepicker-startDate="10/14/1994"
						data-datepicker-endDate="09/20/2016" /> <span
						class="input-group-addon right"><i
						class="glyphicon glyphicon-th"></i> </span>
				</div>

			</div>

			<div class="form-group c37 required" data-cid="c37">
				<label class="control-label" for="c37">TIME (HOURS)</label> <select
					class="form-control" id="c37" name="hours"
					data-rule-required="true">
					<option value="">- Select -</option>
					<option value="00">00</option>
					<option value="01">01</option>
					<option value="02">02</option>
					<option value="03">03</option>
					<option value="04">04</option>
					<option value="05">05</option>
					<option value="06">06</option>
					<option value="07">07</option>
					<option value="08">08</option>
					<option value="09">09</option>
					<option value="10">10</option>
					<option value="11">11</option>
					<option value="12">12</option>
					<option value="13">13</option>
					<option value="14">14</option>
					<option value="15">15</option>
					<option value="16">16</option>
					<option value="17">17</option>
					<option value="18">18</option>
					<option value="19">19</option>
					<option value="20">20</option>
					<option value="21">21</option>
					<option value="22">22</option>
					<option value="23">23</option>
				</select>

			</div>

			<div class="form-group c42 required" data-cid="c42">
				<label class="control-label" for="c42">TIME (MINUTES)</label> <input
					type="text" placeholder="00" class="form-control touchspin"
					id="c42" name="minutes" value="" data-rule-number="true"
					data-rule-required="true" data-rule-min="00" data-rule-max="59"
					data-spin-step="1" />

			</div>
			<div class="form-group c47 required" data-cid="c47">
				<label class="control-label" for="c47">TIME (SECONDS)</label> <input
					type="text" class="form-control touchspin" id="c47" name="seconds"
					value="" data-rule-number="true" data-rule-required="true"
					data-rule-min="00" data-rule-max="59" data-spin-step="1" />
			</div>

			<div class="form-group submit c100 " data-cid="c100"
				style="position: relative;">
				<label class="control-label sr-only" for="c100"
					style="display: block;">Submit Button</label>

				<div class="progress"
					style="display: none; z-index: -1; position: absolute;">
					<div class="progress-bar progress-bar-striped active"
						role="progressbar" style="width: 100%"></div>
				</div>

				<button type="submit" class="btn btn-primary btn-lg"
					style="z-index: 1;">Submit</button>

				<button type="button" onclick="signOut()" class="btn btn-primary btn-lg"
					style="z-index: 1;">Sign out</button>

				<p class="error bg-warning" style="display: none;">Please check
					the required fields.</p>

			</div>

		</form>

		<a href="http://www.jqueryform.com" target="_blank"
			class="jf-copyright">Powered by jqueryform.com</a>
	</div>

	<div class="container jf-thankyou" style="display: none;"
		data-redirect="" data-seconds="10">
		<h3>Your form has been submitted. Thank You!</h3>
	</div>
	<!-- ----------------------------------------------- -->


	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.0/js/bootstrap-datepicker.min.js"></script>
	<script
		src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.1/jquery.validate.min.js"></script>
	<script
		src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.1/additional-methods.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery-scrollTo/2.1.0/jquery.scrollTo.min.js"></script>
	<script src="vendor.js"></script>

	<script src="jqueryform.com.min.js?ver=v1.0-rc24&id=jqueryform-96767c"></script>
	
	
	


	<!-- [ Start: Select2 support ] ---------------------------------- -->
	<link rel="stylesheet" type="text/css"
		href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/css/select2.min.css">
	<script type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/js/select2.full.min.js"></script>
	<style type="text/css">
.select2-hidden-accessible {
	opacity: 0;
	width: 1% !important;
}

.select2-container .select2-selection--single {
	height: 34px;
	padding-top: 2px;
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
	border: 1px solid #ccc;
}

.select2-container--default .select2-selection--single .select2-selection__arrow
	{
	top: 4px;
}
</style>
	<script type="text/javascript">
		;
		(function() {

			function templateResult(obj) {
				if (!obj.id) {
					return obj.text;
				}

				var img = $(obj.element).data('imgSrc');
				if (img) {
					return $('<span><img src="' + img + '" class="img-flag" /> '
							+ obj.text + '</span>');
				}
				;

				return obj.text;
			}
			;

			$(".jf-form select").css('width', '100%'); // make it responsive
			$(".jf-form select").select2({
				templateResult : templateResult
			}).change(function(e) {
				$(e.target).valid();
			});

		})();
	</script>
	<!-- [ End: Select2 support ] ---------------------------------- -->

	<script src="//fast.wistia.net/labs/fresh-url/v1.js" async></script>

	<script type="text/javascript">
		// setup number fields with touchSpin
		;
		(function() {

			$(".touchspin")
					.each(
							function() {

								var $t = $(this), settings = {}, attrs = "step,initval,min,max,decimals,prefix,postfix,forcestepdivisibility"
										.split(","), i = 0, n = attrs.length, attr, val;

								for (; i < n; i++) {
									attr = attrs[i];
									val = $t.data('spin-' + attr);
									if (val) {
										settings[attr] = val;
									}
								}
								;
								//console.log( settings );
								$t.TouchSpin(settings);

							});

		})();
	</script>
	
	<script type="text/javascript">
	function signOut(){
		document.location.href = "https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue=http://ec2-35-160-137-157.us-west-2.compute.amazonaws.com:8085/SG_MICROSERVICE_DATAINGESTOR";
	}
	
	</script>
	
	
	<script type="text/javascript">
		// start jqueryform initialization
		// --------------------------------
		JF.init('#jqueryform-96767c');
		// <!-- action='/SG_MICROSERVICE_DATAINGESTOR/webapi/dataingester/get' -->
	</script>

</body>
</html>