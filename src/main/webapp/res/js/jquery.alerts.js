(function(e){e.alerts = {verticalOffset: - 75, horizontalOffset:0, repositionOnResize:true, overlayOpacity:.3, overlayColor:"#000", draggable:true, okButton:" OK ", cancelButton:" Cancel ", dialogClass:null, alert:function(t, n, r){if (n == null)n = "Thông báo"; e.alerts._show(n, t, null, "alert", function(e){if (r)r(e)})}, confirm:function(t, n, r){if (n == null)n = "Confirm"; e.alerts._show(n, t, null, "confirm", function(e){if (r)r(e)})}, prompt:function(t, n, r, i){if (r == null)r = "Prompt"; e.alerts._show(r, t, n, "prompt", function(e){if (i)i(e)})}, form:function(t, n, r){if (n == null)n = "Form"; e.alerts._show(n, null, t, "form", function(e){if (r)r(e)})}, _show:function(t, n, r, i, s){e.alerts._hide(); e.alerts._overlay("show"); e("BODY").append('<div id="popup_container">' + '<div class="popup_border">' + '<h1 id="popup_title"></h1>' + '<div id="popup_content">' + '<div id="popup_message"></div>' + "</div>" + "</div>" + "</div>"); if (e.alerts.dialogClass)e("#popup_container").addClass(e.alerts.dialogClass); var o = e.browser.msie && parseInt(e.browser.version) <= 6?"absolute":"fixed"; e("#popup_container").css({position:o, zIndex:99999, padding:0, margin:0}); e("#popup_title").text(t); e("#popup_content").addClass(i); e("#popup_message").text(n); e("#popup_message").html(e("#popup_message").text().replace(/\n/g, "<br />")); e("#popup_container").css({minWidth:e("#popup_container").outerWidth(), maxWidth:e("#popup_container").outerWidth()}); e.alerts._reposition(); e.alerts._maintainPosition(true); switch (i){case"alert":e("#popup_content").after('<div id="popup_panel"><input type="button" value="' + e.alerts.okButton + '" id="popup_ok" /></div>'); e("#popup_ok").click(function(){e.alerts._hide(); s(true)}); e("#popup_ok").keypress(function(t){if (t.keyCode == 13 || t.keyCode == 27)e("#popup_ok").trigger("click")}); break; case"confirm":e("#popup_content").after('<div id="popup_panel"><input type="button" value="' + e.alerts.cancelButton + '" id="popup_cancel" /> <input type="button" value="' + e.alerts.okButton + '" id="popup_ok" /></div>'); e("#popup_ok").click(function(){e.alerts._hide(); if (s)s(true)}); e("#popup_cancel").click(function(){e.alerts._hide(); if (s)s(false)}); e("#popup_ok, #popup_cancel").keypress(function(t){if (t.keyCode == 13)e("#popup_ok").trigger("click"); if (t.keyCode == 27)e("#popup_cancel").trigger("click")}); break; case"prompt":e("#popup_message").append('<br /><input type="text" size="30" id="popup_prompt" />'); e("#popup_content").after('<div id="popup_panel"><input type="button" value="' + e.alerts.cancelButton + '" id="popup_cancel" /> <input type="button" value="' + e.alerts.okButton + '" id="popup_ok" /></div>'); e("#popup_prompt").width(e("#popup_message").width()); e("#popup_ok").click(function(){var t = e("#popup_prompt").val(); e.alerts._hide(); if (s)s(t)}); e("#popup_cancel").click(function(){e.alerts._hide(); if (s)s(null)}); e("#popup_prompt, #popup_ok, #popup_cancel").keypress(function(t){if (t.keyCode == 13)e("#popup_ok").trigger("click"); if (t.keyCode == 27)e("#popup_cancel").trigger("click")}); if (r)e("#popup_prompt").val(r); e("#popup_prompt").select(); break; case"form":var u = e("#" + r).attr("action"); e("#popup_message").append('<form method="post" action="' + u + '">' + e("#" + r).html() + "<form>"); e("#popup_content").after('<div id="popup_panel"><input type="button" value="' + e.alerts.cancelButton + '" id="popup_cancel" /> <input type="button" value="' + e.alerts.okButton + '" id="popup_ok" /> <img src="/tai-nguyen/images/loading.gif" class="loading"/></div>'); e("#popup_prompt").width(e("#popup_message").width()); e('#popup_message form input[type="text"]').eq(0).focus(); e("#popup_ok").click(function(){e('#popup_message input[type="text"]').attr("disabled", "disabled"); e("#popup_panel .loading").show(); e('#popup_panel input[type="button"]').attr("disabled", "disabled"); var t = {}; e("#popup_message form").find("input, textarea, button").each(function(){t[e(this).attr("name")] = e(this).val()}); e.ajax({url:u, data:t, type:"post", success:function(e){if (s)s(e)}, complete:function(){}, error:function(){setTimeout(function(){e.alerts._hide(); jAlert("Hệ thống đang bận, bạn vui lòng thử lại sau giây lát.")}, 3e3)}})}); e("#popup_cancel").click(function(){e.alerts._hide(); if (s)s(null)}); e("#popup_prompt, #popup_ok, #popup_cancel").keypress(function(t){if (t.keyCode == 13)e("#popup_ok").trigger("click"); if (t.keyCode == 27)e("#popup_cancel").trigger("click")}); if (r)e("#popup_prompt").val(r); e("#popup_prompt").select(); break}if (e.alerts.draggable){try{e("#popup_container").draggable({handle:e("#popup_title")}); e("#popup_title").css({cursor:"move"})} catch (a){}}}, _hide:function(){e("#popup_container").remove(); e.alerts._overlay("hide"); e.alerts._maintainPosition(false)}, _overlay:function(t){switch (t){case"show":e.alerts._overlay("hide"); e("BODY").append('<div id="popup_overlay"></div>'); e("#popup_overlay").css({position:"absolute", zIndex:99998, top:"0px", left:"0px", width:"100%", height:e(document).height(), background:e.alerts.overlayColor, opacity:e.alerts.overlayOpacity}); break; case"hide":e("#popup_overlay").remove(); break}}, _reposition:function(){var t = e(window).height() / 2 - e("#popup_container").outerHeight() / 2 + e.alerts.verticalOffset; var n = e(window).width() / 2 - e("#popup_container").outerWidth() / 2 + e.alerts.horizontalOffset; if (t < 0)t = 0; if (n < 0)n = 0; if (e.browser.msie && parseInt(e.browser.version) <= 6)t = t + e(window).scrollTop(); e("#popup_container").css({top:t + "px", left:n + "px"}); e("#popup_overlay").height(e(document).height())}, _maintainPosition:function(t){if (e.alerts.repositionOnResize){switch (t){case true:e(window).bind("resize", e.alerts._reposition); break; case false:e(window).unbind("resize", e.alerts._reposition); break}}}}; jAlert = function(t, n, r){e.alerts.alert(t, n, r)}; jConfirm = function(t, n, r){e.alerts.confirm(t, n, r)}; jPrompt = function(t, n, r, i){e.alerts.prompt(t, n, r, i)}; jForm = function(t, n, r){e.alerts.form(t, n, r)}})(jQuery)