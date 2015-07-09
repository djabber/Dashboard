<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
		<title>System Information</title>

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
						<li><a href="/index"><img id="myStyle" class="myImage" src="/images/speedometer.svg"> Dashboard</img></a></li>
						<li class="active"><a href="/servers"><img id="myStyle" class="myImage" src="/images/server.svg"> Servers</img></a></li>
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
						<h1>System Information <small> </small></h1>
						
						<!-- INNER PAGE - SubHeading -->
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i><a href="/index"> Dashboard</li></a>
							<li class="active"><i class="fa fa-dashboard"></i><a href="/servers">Servers</li></a>
						</ol>
						<table>	
							%for list in info:
								%for tup in list: 
									%if tup[1] == "":
										<br/><div id="myStyle" class="myHeader">{{tup[0]}}</div>
									%else:
										<div id="myStyle" class="mySubHead"><b>{{tup[0]}}:</b> {{tup[1]}}</div>
									%end
								%end
							%end
						</table>		
					</div><!-- End of INNER PAGE - div 1 -->
				</div><!-- End of INNER PAGE - Heading -->
				
			</div><!-- End of INNER PAGE -->
		</div><!-- /#wrapper -->

		<!-- JavaScript -->
		<script src="/js/jquery-1.10.2.js"></script>
		<script src="/js/bootstrap.js"></script>
	</body>
</html>
