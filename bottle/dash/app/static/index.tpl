<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">

		<title>Dashboard</title>

		<!-- Bootstrap core CSS -->
		<link rel="stylesheet" href="/css/bootstrap.css">

		<!-- Add custom CSS here -->
		<link rel="stylesheet" href="/css/sb-admin.css">
		<link rel="stylesheet" rel="font-awesome/css/font-awesome.min.css">
		<link rel="stylesheet" href="/css/myStyle.css">
    
		<!-- Page Specific CSS -->
		<link rel="stylesheet" href="http://cdn.oesmith.co.uk/morris-0.4.3.min.css">
	</head>

	<body>
		<div id="wrapper">

			<!-- Sidebar -->
			<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
				
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="index.tpl">TxSSC Admin</a>
				</div>		
				<ul class="nav navbar-right top-nav">
					<li id="myStyle" class="nav navbarClock navbarCenter top-nav">
						<iframe src="http://free.timeanddate.com/clock/i4bpvr9a/n400/fs18/fcfff/tct/pct/pa14/tt0/tb1" frameborder="0" width="448" height="49" allowTransparency="true"></iframe>
					</li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> DJ Deckert <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="#"><i class="fa fa-fw fa-user"></i> Profile</a></li>
							<li><a href="#"><i class="fa fa-fw fa-envelope"></i> Inbox</a></li>
							<li><a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a></li>
							<li class="divider"></li>
							<li><a href="#"><i class="fa fa-fw fa-power-off"></i> Log Out</a></li>
						</ul>
					</li>
				</ul>
				
				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse navbar-ex1-collapse">
					<ul class="nav navbar-nav side-nav">
						<li class="active"><a href="index"><i class="fa fa-dashboard"></i> Dashboard</a></li>
						<li><a href="sys_info"><i class="fa fa-file"></i> System Information</a></li>
					</ul>
				</div><!-- /.navbar-collapse -->
			</nav>
	
			<div id="page-wrapper">
				<div class="row">
					<div class="col-lg-12">
						<h1>Dashboard <small> </small></h1>
						<ol class="breadcrumb">
							<li class="active"><i class="fa fa-dashboard"></i> Dashboard</li>
						</ol>
					</div>
				</div><!-- /.row -->
				<div class="row">
					<div class="col-lg-3 col-md-6">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<div class="row">
									<div class="huge">Test VM</div>
									<div class="col-xs-3">
										<i class="fa fa-tasks fa-5x"></i>
									</div>
									<div id="myStyle" class="myFont col-xs-9 text-right">
										<div>Status:</div>
									</div>
								</div>
							</div>
							<a href="sys_info/txssc-dj-ol-vm">
								<div class="panel-footer">
									<span class="pull-right">View Details</span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
					<div class="col-lg-3 col-md-6">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<div class="row">
									<div class="huge">Test Server</div>
									<div class="col-xs-3">
										<i class="fa fa-tasks fa-5x"></i>
									</div>
									<div id="myStyle" class="myFont col-xs-9 text-right">
										<div>Status:</div>
									</div>
								</div>
							</div>
							<a href="sys_info/WIN-CEMGLVN74PK">
								<div class="panel-footer">
									<span class="pull-right">View Details</span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
					<div class="col-lg-3 col-md-6">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<div class="row">
									<div class="huge">File Server</div>
									<div class="col-xs-3">
										<i class="fa fa-tasks fa-5x"></i>
									</div>
									<div id="myStyle" class="myFont col-xs-9 text-right">
										<div>Status:</div>
									</div>
								</div>
							</div>
							<a href="sys_info/txssc-fileserv1">
								<div class="panel-footer">
									<span class="pull-right">View Details</span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
					<div class="col-lg-3 col-md-6">
						<div class="panel panel-green">
							<div class="panel-heading">
								<div class="row">
									<div class="huge">Database Server</div>
									<div class="col-xs-3">
										<i class="fa fa-tasks fa-5x"></i>
									</div>
									<div id="myStyle" class="myFont col-xs-9 text-right">
										<div>Status:</div>
									</div>									
								</div>
							</div>
							<a href="sys_info">
								<div class="panel-footer">
									<span class="pull-right">View Details</span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
					<div class="col-lg-3 col-md-6">
						<div class="panel panel-yellow">
							<div class="panel-heading">
								<div class="row">
									<div class="huge">Fax Server</div>
									<div class="col-xs-3">
										<i class="fa fa-tasks fa-5x"></i>
									</div>
									<div id="myStyle" class="myFont col-xs-9 text-right">
										<div>Status:</div>
									</div>									
								</div>
							</div>
							<a href="sys_info">
								<div class="panel-footer">
									<span class="pull-right">View Details</span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
					<div class="col-lg-3 col-md-6">
						<div class="panel panel-orange">
							<div class="panel-heading">
								<div class="row">
									<div class="huge">Print Server</div>
									<div class="col-xs-3">
										<i class="fa fa-tasks fa-5x"></i>
									</div>
									<div id="myStyle" class="myFont col-xs-9 text-right">
										<div>Status:</div>
									</div>									
								</div>
							</div>
							<a href="sys_info">
								<div class="panel-footer">
									<span class="pull-right">View Details</span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
					<div class="col-lg-3 col-md-6">
						<div class="panel panel-red">
							<div class="panel-heading">
								<div class="row">
									<div class="huge">Cloud Server 1</div>
									<div class="col-xs-3">
										<i class="fa fa-tasks fa-5x"></i>
									</div>
									<div id="myStyle" class="myFont col-xs-9 text-right">
										<div>Status:</div>
									</div>									
								</div>
							</div>
							<a href="sys_info">
								<div class="panel-footer">
									<span class="pull-right">View Details</span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
					<div class="col-lg-3 col-md-6">
						<div class="panel panel-purple">
							<div class="panel-heading">
								<div class="row">
									<div class="huge">Cloud Server 2</div>
									<div class="col-xs-3">
										<i class="fa fa-tasks fa-5x"></i>
									</div>
									<div id="myStyle" class="myFont col-xs-9 text-right">
										<div>Status:</div>
									</div>									
								</div>
							</div>
							<a href="sys_info">
								<div class="panel-footer">
									<span class="pull-right">View Details</span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
				</div><!-- /.row -->
			</div><!-- /#page-wrapper -->
		</div><!-- /#wrapper -->

		<!-- JavaScript -->
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
		
		<!-- <script src="js/jquery-1.10.2.js"></script> -->
		<script src="js/bootstrap.js"></script>

		<!-- Page Specific Plugins -->
		<script src="http://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
		<script src="http://cdn.oesmith.co.uk/morris-0.4.3.min.js"></script>
		<script src="js/morris/chart-data-morris.js"></script>
		<script src="js/tablesorter/jquery.tablesorter.js"></script>
		<script src="js/tablesorter/tables.js"></script>

	</body>
</html>
