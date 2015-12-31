(function () {
	return function (wgt, option) {
		var el = wgt.aframe = document.createElement('a-' + option);
		var d = jq(wgt.$n()).data();
		for (var i in d) {
			if (i.length > 6 && i.startsWith('aframe')) {
				el.setAttribute(i.substring(6), d[i]);
			}
		}
		if (wgt.nChildren) {
			for (var w = wgt.firstChild; w; w = w.nextSibling)
				if (w.aframe)
					el.appendChild(w.aframe);
		}
		if (option == 'scene') {
			var n = wgt.$n(),
				pos = jq(n).position();
			el.setAttribute("width", jq.px0(n.offsetWidth));
			el.setAttribute("height", jq.px0(n.offsetHeight));
			jq(wgt.$n()).replaceWith(el);
			if (el.canvas) {
				el.canvas.style.top = pos.top + 'px';
				el.canvas.style.left = pos.left + 'px';
			} else {
				setTimeout(function () { // fix firefox issue
					if (el.canvas) {
            			el.canvas.style.top = pos.top + 'px';
            			el.canvas.style.left = pos.left + 'px';
            		}
				});
			}
		} else if (wgt.parent.aframe) {
			wgt.parent.aframe.appendChild(el);
		}
	};
})()