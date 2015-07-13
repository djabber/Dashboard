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
			<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
				
				<!---------- TOPBAR ---------->
				<div class="navbar-header">
					<a class="navbar-brand" href="index.tpl">TxSSC Admin</a>
				</div>	
				
					<!----- TOPBAR - Clock ----->	
					<ul class="nav navbar-right top-nav">
						<li id="myStyle" class="nav navbarClock navbarCenter top-nav">
							<iframe src="http://free.timeanddate.com/clock/i4bpvr9a/n400/fs18/fcfff/tct/pct/pa14/tt0/tb1" frameborder="0" width="448" height="49" allowTransparency="true"></iframe>
						</li>
					</ul>
				
				<!---------- SIDEBAR ---------->
				<div class="collapse navbar-collapse navbar-ex1-collapse">
					<!----- SIDEBAR - Links ----->
					<ul class="nav navbar-nav side-nav">
						<li class="active"><a href="/index"><img id="myStyle" class="myImage" src="/images/speedometer.svg"> Dashboard</img></a></li>
						<li><a href="/servers"><img id="myStyle" class="myImage" src="/images/server.svg"> Servers</img></a></li>
						<li><a href="/printers"><img id="myStyle" class="myImage" src="/images/printer.svg"> Printers</img></a></li>
					</ul>
				</div><!-- End of SIDEBAR -->
			</nav>
	
			<!---------- INNER PAGE ---------->
			<div id="page-wrapper">
				
				<!----- INNER PAGE - Heading ----->
				<div class="row">
					
					<!-- INNER PAGE - div 1 -->
					<div class="col-lg-12">
						<h1>Dashboard <small> </small></h1>
						
						<!-- INNER PAGE - SubHeading -->
						<ol class="breadcrumb">
							<li class="active"><i class="fa fa-dashboard"></i> Dashboard</li>
						</ol>
					</div><!-- End of INNER PAGE - div 1 -->
				</div><!-- End of INNER PAGE - Heading -->
				
				<!----- INNER PAGE CONTENT ROW 1----->
				<div class="row">
					
					<!-- INNER PAGE CONTENT ROW 1 - Server SubHeading -->
					<div id="myStyle" class="mySubHead2">Servers:</div>
						%for item in servers: 
						
							<!-- INNER PAGE CONTENT ROW 1 - div 1 -->
							<div class="col-lg-3 col-md-6">
							
								<!-- INNER PAGE CONTENT ROW 1 - div 2 -->
								<div class="panel panel-color">
									<!-- INNER PAGE CONTENT ROW 1 - div 3 -->
									<div class="panel-heading">
										<!-- INNER PAGE CONTENT ROW 1 - div 4 -->
										<div class="row">
										
											<!-- Server Names -->
											<div class="huge">{{item[0]}}</div>
											<div class="col-xs-3">
												<i class="fa fa-tasks fa-5x"></i>
											</div>
									
											<!-- Server Status Image Conditions -->
											<div id="myStyle" class="myFont col-xs-9 text-right">
												%if item[2] == 1:
													<div>Status: <img id="status_image" src="/images/green.svg" height="14" width="14"/> </div>
												%else:
													<div>Status: <img id="status_image" src="/images/red.svg" height="14" width="14"/> </div>
												%end
											</div><!-- End of Server Status Image Conditions -->
										</div><!-- End of INNER PAGE CONTENT ROW 1 - div 4 -->
									</div><!-- INNER PAGE CONTENT ROW 1 - div 3 -->
							
									<!-- Server Info Path and Link-->
									<a href=sys_info/{{item[1]}}>
										<div class="panel-footer">
											<span class="pull-right">View Details</span>
											<div class="clearfix"></div>
										</div>
									</a>
								</div><!-- End of INNER PAGE CONTENT ROW 1 - div 2 -->
							</div><!-- End of INNER PAGE CONTENT ROW 1 - div 1 -->
						%end
					</div><!-- End of INNER PAGE CONTENT ROW 1 - Server SubHeading -->
				</div><!-- End of INNER PAGE CONTENT ROW 1-->
				
				<!----- INNER PAGE CONTENT ROW 2----->
				<div class="row">
					
					<!-- INNER PAGE CONTENT ROW 2 - Printer SubHeading -->
					<div id="myStyle" class="mySubHead2">Printers:</div>
						%for item in printers: 
					
							<!-- INNER PAGE CONTENT ROW 2 - div 1 -->
							<div class="col-lg-3 col-md-6">
								<div class="panel panel-color">
							
									<!-- INNER PAGE CONTENT ROW 2 - div 2 -->
									<div class="panel-heading">
										<div class="row">
									
											<!-- Printer Names -->
											<div class="huge">{{item[0]}}</div>
											<div class="col-xs-3">
												<i class="fa fa-tasks fa-5x"></i>
											</div>
									
											<!-- Printer Status Image Conditions -->
											<div id="myStyle" class="myFont col-xs-9 text-right">
												%if item[2] == 1:
													<div>Status: <img id="status_image" src="/images/green.svg" height="14" width="14"/> </div>
												%else:
													<div>Status: <img id="status_image" src="/images/red.svg" height="14" width="14"/> </div>
												%end
											</div><!-- End of Printer Status Image Conditions -->
										</div>
									</div><!-- INNER PAGE CONTENT ROW 2 - div 2 -->
								
									<!-- Printer Info Path and Link -->
									<a href=prnt_info/{{item[1]}}>
										<div class="panel-footer">
											<span class="pull-right">View Details</span>
											<div class="clearfix"></div>
										</div>
									</a>
								</div>
							</div><!-- End of INNER PAGE CONTENT ROW 2 - div 1 -->
						%end
					</div><!-- End of INNER PAGE CONTENT ROW 2 - Printer SubHeading -->
				</div><!-- End of INNER PAGE CONTENT ROW 2 -->
				
			</div><!-- End of INNER PAGE -->
		</div><!-- /#wrapper -->
	
		<!-- JavaScript -->
		<script src="/js/jquery-1.10.2.js"></script>
		<script src="/js/bootstrap.js"></script>
	</body>
</html>
