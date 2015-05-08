/**
Copyright (c) 2012 Longhao Luo, http://www.kindsoft.net/

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

(function($) {
	$.fn.countdown = function(options) {
		// default options
		var defaults = {
			attrName : 'data-diff',
			tmpl : '<span class="hour">%{h}</span>小时<span class="minute">%{m}</span>分钟<span class="second">%{s}</span>秒',
			end : '已结束',
			afterEnd : null
		};
		options = $.extend(defaults, options);

		// trim zero
		function trimZero(str) {
			return parseInt(str.replace(/^0/g, ''));
		}
		// convert string to time
		function getDiffTime(str) {
			var m;
			if ((m = /^(\d{4})[^\d]+(\d{1,2})[^\d]+(\d{1,2})\s+(\d{2})[^\d]+(\d{1,2})[^\d]+(\d{1,2})$/.exec(str))) {
				var year = trimZero(m[1]),
					month = trimZero(m[2]) - 1,
					day = trimZero(m[3]),
					hour = trimZero(m[4]),
					minute = trimZero(m[5]),
					second = trimZero(m[6]);
				return Math.floor((new Date(year, month, day, hour, minute, second).getTime() - new Date().getTime()) / 1000);
			}
			return parseInt(str);
		}
		// format time
		function format(diff) {
			var tmpl = options.tmpl, day, hour, minute, second;
			day = /%\{d\}/.test(tmpl) ? Math.floor(diff / 86400) : 0;
			hour = Math.floor((diff - day * 86400) / 3600);
			minute = Math.floor((diff - day * 86400 - hour * 3600) / 60);
			second = diff - day * 86400 - hour * 3600 - minute * 60;
			tmpl = tmpl.replace(/%\{d\}/ig, day)
				.replace(/%\{h\}/ig, hour)
				.replace(/%\{m\}/ig, minute)
				.replace(/%\{s\}/ig, second);
			return tmpl;
		}
		// main
		return this.each(function() {
			var el = this,
				newdiff = diff = getDiffTime($(el).attr(options.attrName));
			var myDate = new Date();
			
			console.log(myDate.getTime());
			console.log(myDate.getMinutes());
			console.log(myDate.getSeconds());
			console.log(diff);
			var lastdiff = 0;
			var tt = 0;
			var lasthour = false;
			var lasthalfhour = false;
			var lasttenmin = false;
			function update() {
				var curdate = new Date();
				var a =parseInt((curdate.getTime() - myDate.getTime())/1000);
				tt = newdiff - a;
				if(tt != lastdiff)
				{
					$(el).html(format(tt));
					
				}
				lastdiff = tt;
				//console.log((newdiff-a)+":"+diff);
				//var ss = (curdate.getHours()-myDate.getHours()
				//console.log(tt);
				if(tt <= 3600 && tt > 3590 && !lasthour)
				{
					lasthour = true;
					alert("考试还有60分钟就将结束！");
				}
				if(tt <= 1800 && tt > 1790 && !lasthalfhour)
				{
					lasthalfhour = true;
					alert("考试还有30分钟就将结束！");
				}
				if(tt <= 600 && tt > 590 && !lasttenmin)
				{
					lasttenmin = true;
					alert("考试还有10分钟就将结束！");
				}
				
				if (tt <= 0) {
					$(el).html(options.end);
					if (options.afterEnd) {
						options.afterEnd();
					}
					return;
				}
				//$(el).html(format(diff));
				setTimeout(function() {
					//diff--;
					update();
				}, 250);
			}
			update();
		});
	};
})(jQuery);
