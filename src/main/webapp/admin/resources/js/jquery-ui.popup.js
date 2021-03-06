// miendatwebPopup v1.0 by Miền Đất Web - http://www.miendatweb.com - 2012/07/22
(function (a) {
    a.fn.extend({
        showPopup: function (b) {
            function f(c) {
                if (b.onClose instanceof Function)
                    b.onClose();
                a("html").css({overflow: "auto"});
                a("body").css({overflow: "auto"});
                a("#lean_overlay").fadeOut(200);
                a(c).html('');
                a(c).css({display: "none"});
            }
            var e = a("<div id='lean_overlay'></div>");
            a("body").append(e);
            b = a.extend({
                top: 100,
                width: 604,
                height: 254,
                overlay: 0.5,
                scroll: !0,
                closeButton: null,
                closeOnEscape: null
            }, b);
            return this.each(function () {
                var c = b;
                a(this).click(function (b) {
                    var d = a(this).attr("href");
                    ajaxLoad(a(this).attr("url-data"), d.substr(1));
                    a("#lean_overlay").click(function () {
                        f(d);
                    });
                    a(c.closeButton).click(function () {
                        f(d);
                    });
                    $('body').keypress(function (e) {
                        if (c.closeOnEscape) {
                            if (e.keyCode == 27) {
                                f(d);
                            }
                        }
                    });
                    a(d).css({width: c.width + "px", height: c.height == 'auto' ? c.height : c.height + "px"});
                    a(d).outerHeight();
                    var e = a(d).outerWidth();
                    a("#lean_overlay").css({
                        display: "block",
                        opacity: 0
                    });
                    a("#lean_overlay").fadeTo(200, c.overlay);
                    a(d).css({
                        display: "block",
                        position: "fixed",
                        opacity: 0,
                        "z-index": 110,
                        left: "50%",
                        "margin-left": -(e / 2) + "px",
                        top: c.top + "px"
                    });
                    c.scroll && (a("html").css({
                        overflow: "hidden"
                    }), a("body").css({
                        overflow: "hidden"
                    }));
                    a(d).fadeTo(200, 1);
                    b.preventDefault();
                });
            });
        }
    });
})(jQuery);