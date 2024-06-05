<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
                    <div class="forum_detail_area ">
                    
                        <div class="cardify forum--issue">
                            <div class="title_vote clearfix">
                              <h3>${board.title}</h3>

								<!-- 게시글에 대한 좋아요/ 싫어요 이벤트 css 위치 
									 클릭 시 함수를 호출 하도록 onClick 이벤트를 연결
									 필수 값인 게시글번호와 게시글 타입을 매개변수로 전달 -->								
								 <div class="vote">
								    <a href="#" id='cThumbUpAnchor' data-isLike ='Y' data-thumb ='Up' onClick="javascript:thumbVote(${board.boardSeq}, ${board.boardTypeSeq}, this);" class="${isLike eq 'Y'? 'active':'' }" >
								        <span class="lnr lnr-thumbs-up"></span>
								    </a>
								    <a href="#" id='!' data-isLike ='N' data-thumb ='Down' onClick="javascript:thumbVote(${board.boardSeq}, ${board.boardTypeSeq}, this);" class="${isLike eq 'N'? 'active':'' }">
								        <span class="lnr lnr-thumbs-down"></span>
								    </a>
								</div>
                                <!-- end .vote -->
                                
                            </div>
                            <!-- end .title_vote -->
                            
                            <div class="suppot_query_tag">
                                <img class="poster_avatar" src="<%=ctx%>/assest/template/images/support_avat1.png" alt="Support Avatar"> 
                                ${board.regMemberId}
                                <span>${board.regDtm}</span>
                                
                                <c:if test='${memberSeq eq board.regMemberSeq }'>
                                 <%-- <c:if test='${sessionScope.memberSeq eq board.regMemberSeq }'> --%>
	                        		<!-- 수정버튼 -->	                               
	                                <a href="<c:url value='/forum/notice/modifyPage.do?boardSeq=${board.boardSeq}&boardTypeSeq=${board.boardTypeSeq}'/>" >수정</a>
	                        		<!-- 삭제버튼 -->
	                        		<a href="#" onClick="javascript:deleteClick(${board.boardSeq}, ${board.boardTypeSeq});">삭제</a>	  
                        		 </c:if>                             
                                
                            </div>
                            
                            <p style="margin-bottom: 0; margin-top: 19px;">
                            	${board.content}</p>
                            <br>
                            <div class = "downLoad_area"> 	
	                            <!-- 전체 다운로드 -->
	                            <c:if test="${attFile.size() > 1}">
	                            	<a href="<%=ctx%>/forum/downloadAll.do?boardSeq=${board.boardSeq}&boardTypeSeq=${board.boardTypeSeq}">파일 전체 다운로드</a>
	                            	<br>
	                            </c:if>
	                                                        	
	  							<c:if test="${attFile.size() != 0}">
		                            <c:forEach items="${attFile}" var="attFile">
		                            	<a href="<%=ctx%>/forum/download.do?attachSeq=${attFile.attachSeq}"> ${attFile.orgFileNm} (size : ${attFile.fileSize})</a>
		                            	<br>
		                            </c:forEach>
	                            </c:if>         
	                                        
                            </div>	
                        </div>
                        <!-- end .forum_issue -->

                        <div class="forum--replays cardify">
                            <div class="area_title">
                                <h4>${fn:length(comments)} Replies</h4>
                            </div>
                            <!-- end .area_title -->

							<!-- 댓글 -->
                        	<c:forEach items="${comments}" var="comment" varStatus="status">
	                            <!-- end .area_title -->
                            	<div class="forum_single_reply" data-commentSeq="${comment.commentSeq }">
                            	<!-- commentDto의 lvl을 활용해 패딩값을 조절해서 댓글 계층구조를 시각적으로 드러내기 -->
	                                <div class="reply_content" style="padding-left: ${18 + 30*comment.lvl}px">
	                                    <div class="name_vote">
	                                        <div class="pull-left">	                                        
	                                            <h4>${comment.memberNm}
	                                                <!-- <span>staff</span> -->
	                                            </h4>
	                                            
	                                            <!-- 값 확인용 -->
	                                    		<h5>[self : ${comment.commentSeq}] [parent : ${comment.parentCommentSeq}]</h5>
	                                    	
	                                            <div style="display: flex; padding-right: 20px " >
	                                            	<p>${comment.regDtm}</p>  
	                                            	
	                                            	
                                					<c:if test='${memberSeq eq board.regMemberSeq }'>
                                 					<%-- <c:if test='${sessionScope.memberSeq eq board.regMemberSeq }'> --%>
						                        		<!-- 수정버튼 -->
						                        		<a style="padding-left: 6px" href="#" onClick="javascript:modifyCommentBox(this);">수정</a>	                               
						                        		<%-- <a style="padding-left: 6px" href="#" onClick="javascript:modifyCommentClick(${comment.commentSeq}, ${board.boardSeq}, ${board.boardTypeSeq});">수정</a> --%>	                               
						                                <%-- <a href="<c:url value='/forum/notice/modifyPage.do?boardSeq=${board.boardSeq}&boardTypeSeq=${board.boardTypeSeq}'/>" >수정</a> --%>
						                        		<!-- 삭제버튼 -->
						                        		<a style="padding-left: 6px" href="#" onClick="javascript:deleteCommentClick(${comment.commentSeq}, ${board.boardSeq}, ${board.boardTypeSeq});">삭제</a>
					                        		</c:if>
					                        		
					                        		<!-- 댓글 -->      
					                        		<%-- <h1> seq : ${comment.commentSeq} , lvl : ${comment.lvl}</h1> --%>
				                        			<a style="padding-left: 6px" href="#" data-commentSeq="${comment.commentSeq}" data-commentLvl="${comment.lvl}" onclick="javascript:openReplyWindow(this)">답글</a>
  	
				                        		</div>	  	                                            
	                                        </div>
	                                        <!-- end .pull-left -->
	                                        
<!-- 											댓글 좋아요/싫어요
	                                        <div class="vote">
	                                            <a href="#" class="active">
	                                                <span class="lnr lnr-thumbs-up"></span>
	                                            </a>
	                                            <a href="#" class="">
	                                                <span class="lnr lnr-thumbs-down"></span>
	                                            </a>
	                                        </div> -->
	                                        
	                                    </div>
	                                    <!-- end .vote -->
                         		       <div class="commentContent"> ${comment.content}</div>
	                                </div>
	                                <!-- end .reply_content -->
	                            </div>
	                            <!-- end .forum_single_reply -->
                            </c:forEach>    

 							<!-- 댓글 추가 창 -->
                            <div class="comment-form-area reply">
                                <h4>Leave a comment</h4>
                                <!-- comment reply -->
                                <div class="media comment-form support__comment">
                                
                                    <div class="media-left">
                                        <a href="#">
                                            <img class="media-object" src="<%=ctx%>/assest/template/images/m7.png" alt="Commentator Avatar">
                                        </a>
                                    </div>
                                    
                                    <div class="media-body">
									    <div id="trumbowyg-demo"></div>					
									    <button class="btn btn--sm btn--round submit" onClick='javascript:addComment(${board.boardSeq}, ${board.boardTypeSeq}, this);'>Post Comment</button>
                                        <button type="button" onclick="location.href='<%=ctx %>/forum/notice/readPage.do?boardSeq=${board.boardSeq}&boardTypeSeq=${board.boardTypeSeq}'" class="btn btn--sm btn--round">Cancel</button>
									</div>
                                </div>
                                <!-- comment reply -->
                                
	 							<!-- 댓글 수정 창 -->
	                            <div class="comment-form-area edit" style="display:none">
	                                <h4>Edit your comment</h4>
	                                <!-- comment reply -->
	                                <div class="media comment-form support__comment">
	                                    <div class="media-left">
	                                        <a href="#">
	                                            <img class="media-object" src="<%=ctx%>/resources/template/images/m7.png" alt="Commentator Avatar">
	                                        </a>
	                                    </div>
	                                    
	                                   <div class="media-body">
	                                       <div class="comment-reply-form">
	                                           <div id="comment-edit"></div>
	                                           <button type="button" onclick="javascript:modifyComment(${board.boardSeq },${board.boardTypeSeq}, this)" class="btn btn--sm btn--round edit">Edit Comment</button>
	                                           <button type="button" 
	                                           		onclick="location.href='<%=ctx %>/forum/notice/readPage.do?boardSeq=${board.boardSeq }&boardTypeSeq=${board.boardTypeSeq }'" 
	                                           		class="btn btn--sm btn--round cancel">Cancel</button>
	                                       </div>
	                                   </div>
	                                </div>
	                                <!-- comment reply -->
	                            </div>                                
                            </div>
                        </div>
                        <!-- end .forum_replays -->
                    </div>
                    <!-- end .forum_detail_area -->
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
	    
     	$('#comment-edit').trumbowyg({
     		lang: 'kr'
     	})	    

	    /* 상태 msg */
    	window.onload=function(){
    		var code = '${code}';
    		var msg = '${msg}';
    		var page = '${page}';
    		
   			if(msg != null && msg != "" ) {
   				
   				console.log(ctx);
				<%-- location.href='<%=ctx%>'+page; --%>
   				alert(msg);
   			}
    	}
    	
	    /* 좋아요/싫어요 */
	    function thumbVote(boardSeq, boardTypeSeq, elem) {
	    	
	    	let url = '<%=ctx%>/forum/notice/vote.do?';
	    	url += 'boardSeq='+boardSeq
	    	url += '&boardTypeSeq='+boardTypeSeq
	    	url += '&isLike=' + elem.getAttribute("data-isLike")
	    	url += '&thumb=' + elem.getAttribute("data-thumb"); 
	    	
	    	$.ajax({
	    		// 타입 (get, post, put 등등)    
	    		type : 'get',           
	    		// 요청할 서버url
	    		url : url,
	    		// Http header
	    		headers : {
	    			"Content-Type" : "application/json"
	    			/* "accept" : "application/jsoin" */
	    		},
	    		/* dataType : 'text', */
	    		// 결과 성공 콜백함수 
	    		success : function(result) {
    				
    				console.log("result : " + result);
    				
				 /* result : 0, 삭제
    				result : 1, 추가
    				result : 2, 수정 */

	      			// jQuery 이용해 이벤트 후처리	      			
	    			if (result == 0) {
		      			$('a#cThumb'+elem.getAttribute("data-thumb")+'Anchor').removeClass('active');	    				
	    			}else if(result == 1){
	    				$('a#cThumb'+elem.getAttribute("data-thumb")+'Anchor').addClass('active');
	    			} else if(result ==2){
	    				$('a#cThumb'+!elem.getAttribute("data-thumb")+'Anchor').removeClass('active');
	    				$('a#cThumb'+elem.getAttribute("data-thumb")+'Anchor').addClass('active');
	    			}
	    		},
	    		// 결과 에러 콜백함수
	    		error : function(request, status, error) {
	    			console.log(error)
	    		}
	    	});
	    }
	 
    	/* 게시글 삭제 */
    	function deleteClick(boardSeq, boardTypeSeq) {
    		var result = confirm("정말 현재 게시글을 삭제 하시겠습니까?");

    		let url = '<%=ctx%>/forum/notice/delete.do?';
		    	url += 'boardSeq='+boardSeq
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
    	
    	/* 게시글 댓글 */
    	function addComment(boardSeq, boardTypeSeq, elem){
    		
    		var url = '<%=ctx%>/forum/notice/comment.do';
    		
    		$.ajax({
    			type : 'post',
    			url : url,    			
    			headers : {
    				'Content-Type' : 'application/json',
	    			"accept" : "application/json"
    			},
    			dataType : 'JSON',
    			data : JSON.stringify ({
    				boardSeq : boardSeq,
    				boardTypeSeq : boardTypeSeq,
	    			content: $('#trumbowyg-demo').trumbowyg('html'),
	    			parentCommentSeq: elem.getAttribute("data-parentCommentSeq"),
	    			lvl: elem.getAttribute("data-commentLvl")
    			}),
    			success : function(result){
    				if(result){
    					window.location.reload();
    				} else{
    					alert('실패!');
    				}
    			},
    			error : function(request, status, error){
    				console.log(error);
    			}
    		});
    	}
    	
    	function modifyCommentBox(elem){
    		//1. 현재 댓글 내용을 댓글 수정 창에 뿌리기.
	    	//let contentBox = elem.closest('div.contentBtn');
	    	let commentContent = document.querySelector('.commentContent').innerText;
	    	$('#comment-edit').trumbowyg('html', commentContent);
	    	
	    	//2.댓글 수정 창을 현재 댓글 위치로 가져오고(&display를 block) 현재 댓글 내용 요소는 삭제.
	    	let commentArea = elem.closest('div.forum_single_reply');
	    	let editForm = document.querySelector('div.comment-form-area.edit');
	    	commentArea.append(editForm);
	    	editForm.style.display = "block";
	    	
	    	//2-2. 이전의 다른 댓글의 수정버튼을 누른 상태라면, 그 댓글의 수정창은 닫혀야 한다.
	    /* 	let hiddenElem = document.querySelector('div.contentBtn.hiddenComment');
	    	console.log(hiddenElem);
	    	
	    	if(hiddenElem != null) {
	    		hiddenElem.classList.remove("hiddenComment");
	    	}
	    	contentBox.classList.add("hiddenComment"); */
	    	
	    	//3.댓글 수정 창의 수정 버튼에 현재 댓글의 commentSeq를 심어둔다.
	    	let editBtn = editForm.querySelector('button.edit');
	    	editBtn.setAttribute('data-commentSeq', commentArea.getAttribute('data-commentSeq'));    		
    	}
    	
	    // 대댓글 등록 창을 띄우는 메서드
	    function openReplyWindow(elem){
	    	
	    	//이전에 등록하지 않고 작성한 내용이 있다면 초기화시키기
	    	$('#trumbowyg-demo').trumbowyg('html', '');
	    	
	    	//1.댓글 등록 창을 현재 댓글 위치로 가져오기
	    	let ReplyArea = elem.closest('div.forum_single_reply');
	    	let commentForm = document.querySelector('div.comment-form-area.reply');
	    	
	    	ReplyArea.append(commentForm);
	    	
	    	//3.대댓글 등록 창의 등록 버튼에 현재 댓글의 commentSeq를 심어둔다.
	    	let submitBtn = commentForm.querySelector('button.submit');
	    	//현재 댓글의 commentSeq를 작성중인 대댓글의 parentCommentSeq로 넣어주고
	    	//대댓글이므로 lvl+1 해주기
	    	
	    	console.dir(elem);
	    	
	    	console.log("data :" + elem.getAttribute('data-commentSeq'));
	    	console.log("commentLvl :" + elem.getAttribute('data-commentLvl'));
	    	
	    	submitBtn.setAttribute('data-parentCommentSeq', elem.getAttribute('data-commentSeq'));
	    	submitBtn.setAttribute('data-commentLvl', parseInt(elem.getAttribute('data-commentLvl'))+1);	    	
	    }    	
    	
    	/* 게시글 댓글 수정 */
    	function modifyComment(boardSeq, boardTypeSeq, elem) {

    		let url = '<%=ctx%>/forum/notice/modifyComment.do?';
		    	url += 'commentSeq='+ elem.getAttribute("data-commentSeq")
		    	url += '&boardSeq='+boardSeq
		    	url += '&boardTypeSeq='+boardTypeSeq
		    	url += '&content=' + $('#comment-edit').trumbowyg('html');
		    	
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
    	}        	
    	
    	/* 게시글 댓글 삭제 */
    	function deleteCommentClick(commentSeq,boardSeq, boardTypeSeq) {
    		var result = confirm("정말 현재 댓글을 삭제 하시겠습니까?");

    		let url = '<%=ctx%>/forum/notice/deleteComment.do?';
		    	url += 'commentSeq='+commentSeq
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
	