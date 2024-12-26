<!doctype html>
<html lang="ko">
 <head>
  <meta charset="UTF-8">
  <meta name="Generator" content="EditPlusÂ®">
  <meta name="Author" content="JSL">
  <meta name="Keywords" content="ë°ìíííì´ì§,  JAVA, JSP, PHP, ëì ì§ìì ë¬¸íêµ, ëì êµ­ë¹ì§ì, êµ­ë¹ë¬´ë£">
  <meta name="Description" content="ìì©SWê°ë°ìë¥¼ ìí ë°ìí ííì´ì§">
  <title>JSLì¸ì¬ê°ë°ì</title>
  <link href="../css/font-awesome.min.css" rel="stylesheet">
  <link href="../css/common.css" rel="stylesheet">
  <link href="../css/layout.css" rel='stylesheet'>
  <script src="../js/jquery-3.3.1.min.js"></script>
  

 </head>
 <body>
	<div class="sr-only">
		<p><a href="#contents">ë³¸ë¬¸ ë°ë¡ê°ê¸°</a></p>
	</div>

	<div class="top_navigation">
	
		<header class="header">
			<nav class="top_left">
			  <ul>
			  	<li class="first"><a href="../index.html">HOME</a></li>
				<li><a href="">ëª¨ì§ìë´</a></li>
				<li><a href="">ìíìë´</a></li>
				<li><a href="">êµì¡ì ì²­</a></li>
			  </ul>
			</nav>
			<nav class="top_right">
				<ul>
					<li class="first"><a href="login.html">ë¡ê·¸ì¸</a></li>
					<li><a href="../member/member.html">íìê°ì</a></li>
				</ul>
			</nav>
			
			<div class="gnb_group">
				<h1 class="logo">JSL CO</h1>
				<nav class="gnb">
					<ul class="nav_1depth">
						<li><a href="gratings.html">ê¸°ììê°</a>
							<ul class="nav_2depth">
								<li><a href="../about/gratings.html">ì¸ì¬ë§</a></li>
								<li><a href="../about/history.html">ì°í ë° </a></li>
								<li><a href="../about/gratings.html">êµì§ììê°</a></li>
								<li><a href="../gallery/photo.html">ëì°ê°¤ë¬ë¦¬</a></li>
								<li><a href="../about/map.html">ì°¾ìì¤ìëê¸¸</a></li>
							</ul>
						</li>
						<li><a href="../portfolio/portfolio.html">í¬í¸í´ë¦¬ì¤</a>
							<ul class="nav_2depth">
								<li><a href="../portfolio/portfolio.html">í¬í¸í´ë¦¬ì¤</a></li>
							</ul>
						</li>
						<li><a href="../notice.html">ì»¤ë®¤ëí°</a>
							<ul class="nav_2depth">
								<li><a href="notice.html">ê³µì§ì¬í­</a></li>
								<li><a href="../qna/qa.html">ì§ë¬¸ê³¼ëµë³</a></li>
								<li><a href="../faq/faq.html">FAQ</a></li>
								<li><a href="../pds/pds.html">ìë£ì¤</a></li>
								<li><a href="../adm/admin.html">ê´ë¦¬ì</a></li>
							</ul>
						</li>
					</ul>
				</nav>
			</div>
		</header>

		<div class="line">
		</div>

	</div>


	<script>
		var code="";
		var incode="";
		var authCheck=false;
		
		$("#btn_mail").on("click", function()
		{
			var regEmail = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[a-zA-Z0-9\-]+/;
		
			if (!regEmail.test($("#email").val()))
			{
				alert("ë©ì¼ ì£¼ì ì´ìí¨");
				$("#email").focus();
				
				return false;
			}
			
			var email = $("#email").val();
			
			$.ajax
			({
				type: 'get',
				url: '/mem/mail.do?mail=' + 'email',
				success: function(data)
				{
					code = data;
					$("#authtication").attr("disabled", false);
					$("#authtication").css("background","white");
					
				}
			});
		});
		
		$("#authtication").change(function()
		{
			incode = $("#authtication").val();
			
			if (incode == code)
			{
				$("num_check").html("인증번호 일치");
				authCheck = true;
			}
			else
			{
				$("#num_check").html("인증번호 불일치");
				authCheck = false;
			}
		})		
	</script>
	
	<!-- 
	
	<!-- sub contents -->
	<div class="sub_title">
		<h2>ì ìì ë¡ê·¸ì¸</h2>
		<div class="container">
		  <div class="location">
			<ul>
				<li class="btn_home">
					<a href="index.html"><i class="fa fa-home btn_plus"></i></a>
				</li>
				<li class="dropdown">
					<a href="">ì»¤ë®¤ëí°<i class="fa fa-plus btn_plus"></i></a>
					<div class="dropdown_menu">
						<a href="gratings.html">ê³µì§ì¬í­</a>
						<a href="allclass.html">íê³¼ë°ëª¨ì§ìë´</a>
						<a href="portfolio.html">í¬í¸í´ë¦¬ì¤</a>
						<a href="online.html">ì¨ë¼ì¸ì ì</a>
						<a href="notice.html">ì»¤ë®¤ëí°</a>
					</div>
				</li>
				<li class="dropdown">
					<a href="">ê³µì§ì¬í­<i class="fa fa-plus btn_plus"></i></a>
					<div class="dropdown_menu">
						<a href="notice.html">ê³µì§ì¬í­</a>
						<a href="qa.html">ì§ë¬¸ê³¼ëµë³</a>
						<a href="faq.html">FAQ</a>
					</div>
				</li>
			</ul>
		  </div>
		</div><!-- container end -->
	</div>
    
	<div class="container">
		<form>
			<div class="member_boxL">
                <h2>ê°ì¸íì</h2>
                <div class="login_form">
                    <form id="frmLogin" method="post">
                    <div class="fl_clear"><label for="mbrId">ìì´ë</label><input name="mbrId" id="mbrId" type="text"></div>
                    <div class="fl_clear"><label for="scrtNo">ë¹ë°ë²í¸</label><input name="scrtNo" id="scrtNo" type="password"></div>
                    <a class="btn_login btn_Blue" href="javascript:fn_login();">ë¡ê·¸ì¸</a>
                    </form>
                </div>
               
            </div>
		</form>
	  
	</div>
	<!-- end contents -->
	
	
 -->
<footer class="footer">
		<div class="container clearfix">
			<address class="address">
				<p class="title">ë³¸ì¬</p>
				(ì°)12345 ëì ê´ì­ì ì¤êµ¬ ê³ë£¡ë¡ 825 (ì©ëë, í¬ìë¹ë©) 5ì¸µ,6ì¸µ/ê³ ê°ì¼í°: 042-242-4412 	ì¬ììë±ë¡ë²í¸: 305-86-06709
			</address>
			<p class="copyright">Copyright &copy JSL ì¸ì¬ê°ë°ìì£¼ìíì¬. All rights reserved.</p>
		</div>
</footer>

 </body>
</html>









