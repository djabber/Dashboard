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
					<a class="navbar-brand" href="index.tpl">TxSSC Admin</a>
				</div>		
				<ul class="nav navbar-right top-nav">
					<li id="myStyle" class="nav navbarClock navbarCenter top-nav">
						<iframe src="http://free.timeanddate.com/clock/i4bpvr9a/n400/fs18/fcfff/tct/pct/pa14/tt0/tb1" frameborder="0" width="448" height="49" allowTransparency="true"></iframe>
					</li>
				</ul>
				
				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse navbar-ex1-collapse">
					<ul class="nav navbar-nav side-nav">
						<li class="active"><a href="/index"><i class="fa fa-dashboard"></i> Dashboard</a></li>
						<li><a href="/sys_info"><i class="fa fa-file"></i> System Information</a></li>
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
				</div>
				
				<!-- /.row -->
				<div class="row">
					<div class="col-lg-3 col-md-6">
						<div class="panel panel-color">
							<div class="panel-heading">
								<div class="row">
									%for tup in servers: 
										<div class="huge">{{tup[1]}}</div>
									%end
									<div class="col-xs-3">
										<i class="fa fa-tasks fa-5x"></i>
									</div>
									<div id="myStyle" class="myFont col-xs-9 text-right">
										<div>Status:</div>
									</div>
								</div>
							</div>
							<a href="/sys_info/txssc-fileserv1">
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
		<script src="/js/jquery-1.10.2.js"></script>
		<script src="/js/bootstrap.js"></script>
	</body>
</html>
