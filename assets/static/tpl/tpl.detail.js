window.template = '\
					<div class="detail">\
						<div class="header">\
							<h2 class="title-detail">{{=it.header.title}}</h2>\
							<span class="date">{{=it.header.date}}</span>\
							<span class="time ml5">{{=it.header.time}}</span>\
							<span class="channel ml5">{{=it.header.channel}}</span>\
						</div>\
						<div class="article">\
							<p class="summary">\
								{{=it.article.summary}}\
							</p>\
							<p class="specific">\
								{{=it.article.specific}}\
							</p>\
						</div>\
						<div class="share">\
							<span class="share-font">分享至</span>\
							<span class="ellipse">\
								<span class="iconfont friend"><img src="./static/img/friend.png"></span>\
								<span>朋友圈</span>\
							</span>\
							<span class="ellipse">\
								<span class="iconfont weixin">&#xe600;</span>\
								<span>微信</span>\
							</span>\
							<span class="ellipse">\
								<span class="iconfont weibo"><img src="./static/img/xinlang.png"></span>\
								<span>微博</span>\
							</span>\
							<span class="ellipse">\
								<span class="iconfont more-btn">&#xe603;</span>\
								<span>更多</span>\
							</span>\
						</div>\
						<div class="more">\
							<div class="img-box box">\
								<img class="img" src="{{=it.more.src}}">\
							</div>\
							<div class="info-box box">\
								<span class="from">来自：</span>\
								<span class="channel">{{=it.more.channel}}</span></br>\
								<span class="info-title">{{=it.more.title}}</span>\
							</div>\
							<div class="go-box box">\
								<span>查看</span>\
							</div>\
							<span class="clear"></span>\
						</div>\
						<div class="gap">\
							<!-- 我是完美分割线 -->\
						</div>\
						<div class="related">\
							<h2>相关阅读</h2>\
							{{for(var i in it.related){var d =it.related[i]; }}\
								<span class="article-title">{{=d.title}}</span>\
							{{ } }}\
						</div>\
						<div class="gap">\
							<!-- 我是完美分割线 -->\
						</div>\
						<div class="comments">\
							<h2>评论</h2>\
							{{for(var j in it.comments){ var c=it.comments[j]; }}\
								<div class="comments-box">\
									<div class="top-box">\
										<img class="img" src="{{=c.src}}">\
										<div class="info">\
											<span class="name">{{=c.name}}</span>\
						    				</br>\
						    				<span class="time">{{=c.time}}</span>\
										</div>\
										<div class="handle">\
											<span class="num iconfont">{{=c.num}}</span>\
											<span class="hand iconfont">&#xe604;</span>\
											<span class="discuss iconfont">&#xe605;</span>\
										</div>\
									</div>\
									<span class="clear"></span>\
									<div class="content-box">\
										{{=c.content}}\
									</div>\
									<div class="reaply">\
										{{for(var m in c.disscuss){var k=c.disscuss[m]; }}\
											<span class="triangle"></span>\
											<p class="reaplyers">\
												<span class="reaplyer">{{=k.name}}:</span>\
												<span class="huifu">回复</span>\
												<span class="reaplyer">{{=k.to}}:</span>\
												<span class="reaply-content">{{=k.content}}</span>\
						    				</p>\
										{{ } }}\
					    			</div>\
								</div>\
							{{ } }}\
							<div class="end-line">\
								<span class="left-line"></span>\
								<span class="end">END</span>\
								<span class="right-line"></span>\
							</div>\
						</div>\
				</div>';