<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Apriori</title>
	</head>
	<script type="text/javascript">
		function start(){
			var x=document.getElementById("file_page");
			x.style.display="none";
			document.getElementById("db").checked=true;
			document.getElementById("file").checked=false;
			document.getElementById("db-filepg").checked=true;
			document.getElementById("file-filepg").checked=false;
		}
		
		function refresh_page(request_type){
			if(request_type=='db'){
				var db_div=document.getElementById("database_page");
				db_div.style.display="block";
				var file_div=document.getElementById("file_page");
				file_div.style.display="none";
				document.getElementById("db").checked=true;
				document.getElementById("file").checked=false;
				document.getElementById("db-filepg").checked=true;
				document.getElementById("file-filepg").checked=false;
			}else if(request_type=='file'){
				var db_div=document.getElementById("database_page");
				db_div.style.display="none";
				var file_div=document.getElementById("file_page");
				file_div.style.display="block";
				document.getElementById("db").checked=false;
				document.getElementById("file").checked=true;
				document.getElementById("db-filepg").checked=false;
				document.getElementById("file-filepg").checked=true;
			}
		}		
	</script>
	<body onload="start();" bgcolor="#808080" text="#000000" style="font-family: Arial, Arial, sans-serif;">
		<div id="database_page">
			<form action="/AprioriServlet/AprioriServlet" method="POST">
				<table style="width:95%; margin-bot:10px;" align="center" border="0">
					<tr>
						<td bgcolor="#B2B2B2" style="border: 1px solid black;"><b>Apriori</b><br>
							<table style="width:90%;" align="center">
								<tr>
									<td bgcolor="#EFEFEF" style="border: 1px solid black;">
										<b>Selecting Data Source</b><br><center>
											<input type="radio" name="data-source" id="db" value="db" onclick="refresh_page('db');"><label for="db">Learning rules from db</label>
											<input type="radio" name="data-source" id="file" value="file" onclick="refresh_page('file');"><label for="file">Reading rules from file</label>
										</center>
									</td>
								</tr>
								<tr>
									<td bgcolor="#EFEFEF" style="border: 1px solid black;"><b>Input Parameters</b><br><center>
										<label for="data">Data</label><input type="text" name="data" id="data">
										<label style="padding-left: 5px" for="file-name">File name(leave empty to not save)</label><input type="text" name="file-name" id="file-name">
										<label style="padding-left: 5px" for="min-sup">Min Sup</label><input type="text" name="min-sup" id="min-sup">
										<label style="padding-left: 5px" for="min-conf">Min Conf</label><input type="text" name="min-conf" id="min-conf">
									</center></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td bgcolor="#B2B2B2" style="border: 1px solid black;">
							<b>Mining Command</b><br><center>
								<input type="submit" value="Apriori">
							</center>
						</td>
					</tr>
					<tr>
						<td bgcolor="#B2B2B2" style="border: 1px solid black;"><b>Patterns and Rules</b><br>
							<center>
								<textarea name="rules" readonly style="resize:none; width: 90%; height:100px">
									<%	String result=null;
										result=(String)request.getAttribute("result");
										if(result!=null){
											out.print(result);
										}
									%>
								</textarea><br>
								<textarea name="msg" readonly style="color: red; resize:none; width: 90%; height:100px">
									<% 	String error=null;
										error=(String)request.getAttribute("error");
										if(error!=null){
											out.print(error);
										}
									%>
								</textarea>
							</center>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="file_page">
			<form action="/AprioriServlet/AprioriServlet" method="POST">
				<table style="width:95%; margin-bot:10px;" align="center" border="0">
					<tr>
						<td bgcolor="#B2B2B2" style="border: 1px solid black;"><b>Apriori</b><br>
							<table style="width:90%;" align="center">
								<tr>
									<td bgcolor="#EFEFEF" style="border: 1px solid black;">
										<b>Selecting Data Source</b><br><center>
											<input type="radio" name="data-source" id="db-filepg" value="db" onclick="refresh_page('db');"><label for="db-filepg">Learning rules from db</label>
											<input type="radio" name="data-source" id="file-filepg" value="file" onclick="refresh_page('file');"><label for="file-filepg">Reading rules from file</label>
										</center>
									</td>
								</tr>
								<tr>
									<td bgcolor="#EFEFEF" style="border: 1px solid black;"><b>Input Parameters</b><br><center>
										<label style="padding-left: 5px" for="file-name-filepg">File name</label><input type="text" name="file-name" id="file-name-filepg">
									</center></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td bgcolor="#B2B2B2" style="border: 1px solid black;">
							<b>Mining Command</b><br><center>
								<input type="submit" value="Apriori">
							</center>
						</td>
					</tr>
					<tr>
						<td bgcolor="#B2B2B2" style="border: 1px solid black;"><b>Patterns and Rules</b><br>
							<center>
								<textarea name="rules" readonly style="resize:none; width: 90%; height:100px">
									<%	result=null;
										result=(String)request.getAttribute("result");
										if(result!=null){
											out.print(result);
										}
									%>
								</textarea><br>
								<textarea name="msg" readonly style="color: red; resize:none; width: 90%; height:100px">
									<% 	error=null;
										error=(String)request.getAttribute("error");
										if(error!=null){
											out.print(error);
										}
									%>
								</textarea>
							</center>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>