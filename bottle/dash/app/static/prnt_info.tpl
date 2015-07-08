<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
		<title>Dashboard</title>

		<!-- CSS -->
		<link rel="stylesheet" href="/css/bootstrap.css">
		<link rel="stylesheet" href="/css/sb-admin.css">
		<link rel="stylesheet" rel="/font-awesome/css/font-awesome.min.css">
		<link rel="stylesheet" href="/css/myStyle.css">
	</head>

	<body>
		<div id="wrapper">

			<!-- Sidebar -->
			<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
				
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<a class="navbar-brand" href="/index.tpl">TxSSC Admin</a>
				</div>		
				<ul class="nav navbar-right top-nav">
					<li id="myStyle" class="nav navbarClock navbarCenter top-nav">
						<iframe src="http://free.timeanddate.com/clock/i4bpvr9a/n400/fs18/fcfff/tct/pct/pa14/tt0/tb1" frameborder="0" width="448" height="49" allowTransparency="true"></iframe>
					</li>
				</ul>
				
				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse navbar-ex1-collapse">
					<ul class="nav navbar-nav side-nav">
						<li><a href="/index"><i class="fa fa-file"></i> Dashboard</a></li>
						<li><a href="/servers"><i class="fa fa-file"></i> Servers</a></li>
						<li><a href="/printers"><i class="fa fa-file"></i> Printers</a></li>
					</ul>
				</div><!-- /.navbar-collapse -->
			</nav>
	
			<div id="page-wrapper">
				<div class="row">
					<div class="col-lg-12">
						<h1>Printer Information <small> </small></h1>
						<ol class="breadcrumb">
							<li><a href="/index"><i class="icon-dashboard"></i> Dashboard</a></li>
							<li class="active"><i class="icon-file-alt"></i> Printer Information</a></li>
						</ol>
						<div id="myStyle" class="myIframe"><iframe src="http://{{host}}" style="width: 100%; height: 100%"></iframe></div>
				</div><!-- /.row -->
			</div><!-- /#page-wrapper -->
		</div><!-- /#wrapper -->

		<!-- JavaScript -->
		<script src="/js/jquery-1.10.2.js"></script>
		<script src="/js/bootstrap.js"></script>
	</body>
</html>
