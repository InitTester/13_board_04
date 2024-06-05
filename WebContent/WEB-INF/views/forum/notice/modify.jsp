<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">

    <!-- viewport meta -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="MartPlace - Complete Online Multipurpose Marketplace HTML Template">
    <meta name="keywords" content="marketplace, easy digital download, digital product, digital, html5">
    <title>포트폴리오</title>

    <!-- inject:css -->
    <link rel="stylesheet" href="<%=ctx%>/assest/template/css/animate.css">
    <link rel="stylesheet" href="<%=ctx%>/assest/template/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=ctx%>/assest/template/css/fontello.css">
    <link rel="stylesheet" href="<%=ctx%>/assest/template/css/jquery-ui.css">
    <link rel="stylesheet" href="<%=ctx%>/assest/template/css/lnr-icon.css">
    <link rel="stylesheet" href="<%=ctx%>/assest/template/css/owl.carousel.css">
    <link rel="stylesheet" href="<%=ctx%>/assest/template/css/slick.css">
    <link rel="stylesheet" href="<%=ctx%>/assest/template/css/trumbowyg.min.css">
    <link rel="stylesheet" href="<%=ctx%>/assest/template/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="<%=ctx%>/assest/template/css/style.css">
	<link rel="stylesheet" href="<%=ctx%>/assest/template/css/trumbowyg.min.css">
    <!-- endinject -->

    <!-- Favicon -->
    <link rel="icon" type="image/png" sizes="16x16" href="<%=ctx%>/assest/template/images/favicon.png">    

</head>

<body class="preload home1 mutlti-vendor">
    <!--================================
            START DASHBOARD AREA
    =================================-->
    <section class="support_threads_area section--padding2">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div class="question-form cardify p-4">
					<form action="<%=ctx%>/forum/notice/modify.do?boardSeq=${board.boardSeq}&boardTypeSeq=${board.boardTypeSeq}" method ="post" enctype="multipart/form-data">
					    <div class="form-group">
					        <label>제목</label>
					        <input type="text" name="title" value="${board.title}" required>
					    </div>
					    <div class="form-group">
					        <label>Description</label>
					        <div id="trumbowyg-demo" >${board.content}</div>
					    </div>
					    <div class="form-group">
				            <label>Attachments</label>
                               <c:set var="listSize" value='${attFile.size() }'/>
				            
	  							<c:if test="${attFile.size() != 0}">
		                            <c:forEach items="${attFile}" var="attFile">
		                                <div class="attachments">
		                                    <label>
		                                        <span class="lnr lnr-paperclip"></span> ${attFile.orgFileNm} (size : ${attFile.fileSize})
												<button type="button" onclick="javascript:deleteFile(${attFile.attachSeq},${board.boardSeq},${board.boardTypeSeq})">  삭제  </button>
		                                        <!-- <span>or Drop Files Here</span>
		                                        <input type="file" name = "attFile" style="display:inline-block;"> -->
		                                    </label>
		                                </div>		                            
		                            	<br>
		                            </c:forEach>
	                            </c:if> 	
	                            	
                   				<!--  첨부파일은 총 3개까지 추가 가능 -->
                                <c:forEach begin='${listSize +1}' end="3" step="1">
                                  <div class="attachments">
                                    <label>
                                        <span class="lnr lnr-paperclip"></span> Add File
                                        <span>or Drop Files Here</span>
                                        <input type="file" name = "attFile" style="display:inline-block;">
                                    </label>
                                </div>
                                </c:forEach>     	                            	
					    </div>
					    
                        <input type="hidden" name= "boardSeq" value ="${board.boardSeq}" />
                        <input type="hidden" name= "boardTypeSeq" value ="${board.boardTypeSeq}" />
                        
					    <div class="form-group">
					        <button type="submit" class="btn btn--md btn-primary">Edit Request</button>
					        <a href="<c:url value='/forum/notice/readPage.do?boardSeq=${board.boardSeq}'/>" class="btn btn--md btn-light">Cancel</a>
					    </div>
					</form>
                    </div><!-- ends: .question-form -->
                </div>
                <!-- end .col-md-12 -->
            </div>
            <!-- end .row -->
        </div>
        <!-- end .container -->
    </section>
    <!--================================
            END DASHBOARD AREA
    =================================-->
   	<!--//////////////////// JS GOES HERE ////////////////-->
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA0C5etf1GVmL_ldVAichWwFFVcDfa1y_c"></script>
    <!-- inject:js -->
    <script src="<%=ctx%>/assest/template/js/vendor/jquery/jquery-1.12.3.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/jquery/popper.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/jquery/uikit.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/bootstrap.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/chart.bundle.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/grid.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/jquery-ui.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/jquery.barrating.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/jquery.countdown.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/jquery.counterup.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/jquery.easing1.3.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/owl.carousel.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/slick.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/tether.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/trumbowyg.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/waypoints.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/dashboard.js"></script>
    <script src="<%=ctx%>/assest/template/js/main.js"></script>
    <script src="<%=ctx%>/assest/template/js/map.js"></script>
    <!-- endinject -->
    
   	<script type="text/javascript">
		var ctx = '<%= request.getContextPath() %>';
	</script>	
	<script src="<%=ctx%>/assest/js/page.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/trumbowyg.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/trumbowyg/ko.js"></script>
    <script type="text/javascript">
	    $('#trumbowyg-demo').trumbowyg({
	        lang: 'kr'
	    });
	    
	    // 파일 삭제
	    function deleteFile(attachSeq,boardSeq,boardTypeSeq){
    		var result = confirm("정말 파일을 삭제 하시겠습니까?");

    		let url = '<%=ctx%>/forum/notice/deleteFile.do?';
		    	url += 'attachSeq='+attachSeq
		    	url += '&boardSeq='+boardSeq
		    	url += '&boardTypeSeq='+boardTypeSeq;
		    	
    		if(result){
    			
    			$.ajax({
    	            type: 'get',
    	            url: url,
    	            headers: {
    	                "Accept": "application/json",  // 요청에 대한 Accept 헤더를 설정
    	                "Content-Type": "application/json"
    	    		},
    	    		// 결과 성공 콜백함수 
    	    		success : function(response) {   
    	    			var page = response.page;
    	    			
    	    			/* alert(page); */
	    				location.href='<%=ctx%>'+page;
        				/* alert(response.msg);  */        	
    	    		},
    	    		// 결과 에러 콜백함수
    	    		error : function(request, status, error) {
    	    			console.log(error)
    	    		}
    	    	});
    		}else{
    			/* alert('cancel'); */
    		}
    	}    	  
	    
	</script>
</body>

</html>
	