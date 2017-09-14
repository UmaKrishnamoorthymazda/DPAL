var NotificationEngine = (function(){
		var nextPollInterval;
		var viewFrameName;
		var notificationBar;
		var title;
		var titleText;
		var icon;
		var message;
		var messageText;
		var MessageType = {"info":"Information"};
		var totalDuration = 400;
		var notificationWasDismissed = true;
		var userViewCheckTimeout = 5000; 
		var barId = "notification-bar";
		
		// include the ending slash "/" at the end of the directory name
		var cssDirectory = "/parts/dpal/css/";
		var jsDirectory = "/parts/dpal/javascript/";
		
		function init(interval, viewFrameName) {
			//alert("init method called with: " + interval + ", " + viewFrameName);
			this.nextPollInterval = interval;
			this.viewFrameName = viewFrameName;
			// setup the notification div
			//this.setupNotificationPanel();
			
			//start poll timer
			setTimeout("NotificationEngine.pollServer()", this.nextPollInterval);
			setTimeout("NotificationEngine.checkViewState()", this.userViewCheckTimeout);
		}
		function showNotification(/*message, title, icon*/){
			//alert("this will show notification");
			NotificationEngine.notificationWasDismissed = false;
			if(!this.getViewFrame().getElementById(this.barId))
				this.setupNotificationPanel();
			this.setMessage(this.messageText);
			this.setTitle(this.titleText);
			this.show();
		}
		function pollServer() {
			var thetime=new Date();
			var uid = thetime.getDay() + "" + thetime.getMonth() + "" + thetime.getYear() + "" + thetime.getHours() + "" + thetime.getMinutes() + "" + thetime.getSeconds();
			//alert(uid);
			var ajax = new GLM.AJAX();
			ajax.callPage("webnotification.do?uid=" + uid, function(response){
				//alert("response = " + response);
				var notificationObj = null;
				
				try {
					notificationObj = eval('(' + response + ')');
				} catch(e) {
					// received message is not valid
					// setup timer for 5 mins later
					//alert("error: setting timeout for 5 mins later");
					setTimeout("NotificationEngine.pollServer()", 300000);
					return;
				}
				var showNotification = notificationObj.showNotification;
				var nextCheck = notificationObj.nextCheck;
				var message = notificationObj.message;
				
				showNotification = (showNotification === 'true');
				if(showNotification) {
					NotificationEngine.messageText = message;
					NotificationEngine.titleText = NotificationEngine.MessageType.info;
					NotificationEngine.showNotification();
				}
				
				if(nextCheck > 0)
					setTimeout("NotificationEngine.pollServer()", nextCheck);
			});
			
			//this.messageText = "New Back Orders have been added to your current Back Order list.";
			//this.titleText = this.MessageType.warning;
			//this.showNotification(/*message, this.MessageType.warning*/);
			
			//setTimeout("NotificationEngine.pollServer()", this.nextPollInterval);
		}
		
		function getViewFrame() {
			return parent.top.frames[this.viewFrameName].document;
		}
		
		function setTitle(title) {
			//alert(this.title.childNodes.length);
			if(this.title.childNodes && this.title.childNodes.length > 0)
				this.title.removeChild(this.title.childNodes[0]);
			var titleNode = this.getViewFrame().createTextNode(title);
			this.title.appendChild(titleNode);
		}
		function setMessage(message) {
			if(this.message.childNodes && this.message.childNodes.length > 0)
				this.message.removeChild(this.message.childNodes[0]);
			var messageNode = this.getViewFrame().createTextNode(message);
			this.message.appendChild(messageNode);	
		}
		function fadeIn(transparency) {
			if(!transparency) {
				transparency = 0.0;
				NotificationEngine.notificationBar.style.display = "block";
			}
			opacity = transparency;
			alpha = "alpha(opacity=" + (transparency*100) + ")";
			this.notificationBar.style.opacity = opacity;
			this.notificationBar.style.filter = alpha;
			if(transparency < 1)
				setTimeout("NotificationEngine.fadeIn("+ ((transparency*1) + 0.1) + ")", NotificationEngine.getTimeout());
		}
		function show() {
			this.fadeIn();	
		}
		function close() {
			NotificationEngine.notificationWasDismissed = true;
			NotificationEngine.fadeOut();
		}
		function fadeOut(transparency) {
			if(!transparency) transparency = 1.0; 
			opacity = transparency;
			alpha = "alpha(opacity=" + (transparency*100) + ")";
			this.notificationBar.style.opacity = opacity;
			this.notificationBar.style.filter = alpha;
			if(transparency > 0.0)
				setTimeout("NotificationEngine.fadeOut("+ ((transparency*1) - 0.1) + ")", NotificationEngine.getTimeout());
			else
				NotificationEngine.notificationBar.style.display = "none";
		}
		function getTimeout() {
			return this.totalDuration/10;	
		}
		function checkViewState() {
			/*alert("notificationWasDismissed = " + !notificationWasDismissed + ", get element = " + (!this.getViewFrame().getElementById(this.barId))
				+ ", result = " + (!notificationWasDismissed && !this.getViewFrame().getElementById(this.barId)));*/
			if(!NotificationEngine.notificationWasDismissed && !NotificationEngine.getViewFrame().getElementById(this.barId))
				NotificationEngine.showNotification();
			setTimeout("NotificationEngine.checkViewState()", NotificationEngine.userViewCheckTimeout);
		}
		
		function setupNotificationPanel() {
			/*alert("parent = " + parent);
			alert("parent.top = " + parent.top);
			alert("parent.top.frames = " + parent.top.frames + ", length = " + parent.top.frames.length);
			alert("viewFrameName = " + this.viewFrameName);*/
			
			this.setupStyleSheet();
			//this.setupJavascript();
			
			var viewFrameDoc = parent.top.frames[this.viewFrameName].document;
			this.notificationBar = viewFrameDoc.createElement("DIV");
			this.notificationBar.id = this.barId;
			
			var headerContainer = viewFrameDoc.createElement("DIV");
			headerContainer.id = "nb-header-container";
			
			var closeButton = viewFrameDoc.createElement("SPAN");
			closeButton.id = "nb-close-button";
			closeButton.onclick = NotificationEngine.close;
			var x = viewFrameDoc.createTextNode("X");
			closeButton.appendChild(x);
			
			var iconTextContainer = viewFrameDoc.createElement("SPAN");
			iconTextContainer.id = "nb-icon-text-container";
			//alert("iconTextContainer = " + iconTextContainer + ", type = " + iconTextContainer.type);
			
			this.icon = viewFrameDoc.createElement("SPAN");
			this.icon.id = "nb-icon";
			
			this.title = viewFrameDoc.createElement("SPAN");
			this.title.id = "nb-title";
			
			this.message = viewFrameDoc.createElement("DIV");
			this.message.id = "nb-message";			
			
			
			// assemble the icon-text-container
			iconTextContainer.appendChild(this.icon);
			iconTextContainer.appendChild(this.title);
			
			// header container
			headerContainer.appendChild(closeButton);
			headerContainer.appendChild(iconTextContainer);
			
			// notification bar
			this.notificationBar.appendChild(headerContainer);
			this.notificationBar.appendChild(this.message);
			
			viewFrameDoc.body.appendChild(this.notificationBar);
		}
		function setupStyleSheet() {
			if(!isCSSAdded())
				loadDynamicFile(NotificationEngine.cssDirectory + "notification.css", "css");
		}
		
		function setupJavascript() {
			if(!isJSAdded())
				loadDynamicFile(NotificationEngine.jsDirectory + "glm-ajax.js", "js");
		}
		
		function loadDynamicFile(filename, filetype){
			if (filetype=="js"){ //if filename is a external JavaScript file
				var fileref=NotificationEngine.getViewFrame().createElement('script');
				fileref.setAttribute("type","text/javascript");
				fileref.setAttribute("src", filename);
			}
			else if (filetype=="css"){ //if filename is an external CSS file
				var fileref=NotificationEngine.getViewFrame().createElement("link");
				fileref.setAttribute("rel", "stylesheet");
				fileref.setAttribute("type", "text/css");
				fileref.setAttribute("href", filename);
			}
			if (typeof fileref!="undefined")
				NotificationEngine.getViewFrame().getElementsByTagName("head")[0].appendChild(fileref);
		}
		
		function isCSSAdded() {
			var found = false;
			var headTag = NotificationEngine.getViewFrame().getElementsByTagName("head")[0];
			//alert(headTag.childNodes.length);
			for(var z=0; z<headTag.childNodes.length; z++) {
				if(headTag.childNodes[z].nodeType == 1) {
					//alert("name = " + headTag.childNodes[z].tagName + ", href = " + headTag.childNodes[z].href);
					if(headTag.childNodes[z].tagName.toUpperCase() == "LINK" && headTag.childNodes[z].href.indexOf("notification.css")!=-1) {
						found = true;
						break;
					}
				}
			}
			//alert("returning " + found);
			return found;
		}
		
		function isJSAdded() {
			var found = false;
			var headTag = NotificationEngine.getViewFrame().getElementsByTagName("head")[0];
			//alert(headTag.childNodes.length);
			for(var z=0; z<headTag.childNodes.length; z++) {
				if(headTag.childNodes[z].nodeType == 1) {
					//alert("name = " + headTag.childNodes[z].tagName + ", href = " + headTag.childNodes[z].href);
					if(headTag.childNodes[z].tagName.toUpperCase() == "SCRIPT" && headTag.childNodes[z].src.indexOf("glm-ajax.js")!=-1) {
						found = true;
						break;
					}
				}
			}
			//alert("returning " + found);
			return found;
		}	
		
		return {
			nextPollInterval: nextPollInterval,
			viewFrameName: viewFrameName,
			init: init,
			showNotification: showNotification,
			pollServer: pollServer,
			getViewFrame: getViewFrame,
			setupNotificationPanel: setupNotificationPanel,
			nextPollInterval: nextPollInterval,
			viewFrameName: viewFrameName,
			notificationBar: notificationBar,
			title: title,
			icon: icon,
			message: message,
			setTitle: setTitle,
			setMessage: setMessage,
			fadeIn: fadeIn,
			fadeOut: fadeOut,
			MessageType: MessageType,
			totalDuration: totalDuration,
			getTimeout: getTimeout,
			show: show,
			close: close,
			barId: barId,
			notificationWasDismissed: notificationWasDismissed,
			userViewCheckTimeout:userViewCheckTimeout,
			checkViewState: checkViewState,
			cssDirectory: cssDirectory,
			jsDirectory: jsDirectory,
			setupStyleSheet: setupStyleSheet,
			setupJavascript: setupJavascript
		};
})();