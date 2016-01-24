/**
 * Chat Tracker JQuery UI Widget
 *
 * This is a JQuery UI widget useful to show you how svg.js 3rd party library works.
 * Svg.js is a lightweight library for manipulating and animating SVG.
 *
 * This is a simple widget to display messages among people as a
 * flow chart where you can do these things:
 * - drag and drop users icons to change users order (in this demo I cannot manage the drop action)
 * - drag and drop messages to change the messages order
 * - use HOME, END, UP, DOWN keys and mouse click to move the focus between all messages
 * - use DEL key to delete a message
 * - drag and drop message over trash icon to delete a message (trash will be visible only when you start drag a message)
 * - catch events about user selected, message selected, message deleted and message moved
 *
 * This example could be useful to create an application to teach English,
 * where the students have to reorder the users' messages until they are in the right order.
 *
 * First draft version May 2012
 * Version 2.0
 *
 * @author Enrico Tedeschini (etedeschini@gmail.com)
 * @license MIT
 *
 * About svg.js
 * - Web site link: http://svgjs.com
 * - Documentation link: http://documentup.com/wout/SVG.js
 *
 */
$(function ($) {

    'use strict';

    $.widget('enrico.ctracker', {

        // Default settings
        defaults: {

            width: 300,
            height: 300,
            tabindex: 0,
            iconsDistance: 210,
            maxCharsPerLine: 20,

            /* Callback events */

            /* Event dispatched when this action is performed:
             * - user icon or user label selected with mouse click
             */
            onClickUser: null,

            /* Event dispatched when these actions are performed:
             * - message selected with mouse click
             * - message selected with UP, DOWN, HOME, END keys
             */
            onSelectedMessage: null,

            /* Event dispatched when these actions are performed:
             * - message deleted with drag and drop over the trash icon
             * - message deleted with DELETE key
             */
            onDeleteMessage: null,

            /* Event dispatched when this action is performed:
             * - message moved with drag and drop
             */
            onMoveMessage: null
        },

        _init: function () {

            // Initialization private members
            this._hHeader = 58;
            this._active = false;
            this._messageSelected = null;
            this._users = [];
            this._messages = [];

            // Merge settings ...
            this.settings = $.extend({}, this.defaults, this.options);

            // Store original DOM
            this.original = this.element;

            // create div container with same id already assigned
            var $container = $('<div id="' + this.element.attr('id') + '"></div>')
                .width(this.settings.width)
                .height(this.settings.height)
                .addClass('ctracker');
            this.element.replaceWith($container);
            this.element = $container;

           // create focus handler
            var $focus = $('<a tabindex="' + this.settings.tabindex + '" style="top:0px;width:0px;height:0px;position:absolute;"></a>')
                .addClass('ctracker-focus');
            this.element.append($focus);
            this._focusable($focus);

            // create viewport
            this.$view = $('<div style="width:100%;height:100%;overflow:auto;"></div>')
                .addClass('ctracker-view');
            this.element.append(this.$view);

            // create SVG area
            this._svg = SVG(this.$view.get(0));
            this._svg.addClass('ctracker-svg');

            // add listeners
            this._on(true, this.element, {'focus .ctracker-focus': '_evFocus'});
            this._on(true, this.element, {'blur .ctracker-focus': '_evBlur'});
            this._on(true, this.element, {'keyup': '_evKeyUp'});
            this._on(true, this.element, {'click': '_evClick'});
            this._on(true, this.$view, {'scroll': '_evScroll'});

           // calculate platform/browser width scrollbar
           this._wScrollbar = (function() {
                var $tmp = $('<div style="overflow:scroll;position:absolute;width:100px;height:100px;top:-200px" ></div>');
                $('body').append($tmp);
                var w = $tmp[0].offsetWidth - $tmp[0].clientWidth;
                $tmp.remove();
                return w;
            })();

            // resize content to the new container size
            var self = this;
            $(window).resize(function () {
                setTimeout (function () {
                    self._updateLayout();
                }, 1000);
            });

            // set initial values if available
            if (this.settings.data)
                this.setValue(this.settings.data);
            else
                this._draw();

            // fix possible initialization size defect
            this._delay( this._updateLayout, 500 );
        },

        _destroy: function () {

            // clean up class
            this.element.removeClass('ctracker');

            // remove listeners
            this._off(this.element, 'focus .ctracker-focus');
            this._off(this.element, 'blur .ctracker-focus');
            this._off(this.element, 'keyup');
            this._off(this.element, 'click');
            this._off(this.$view, 'scroll');

            // clean SVG document
            this._svg.clear();
            this._svg = undefined;

            // replace div with the original html content
            this.element.replaceWith(this.original);
        },

        /**
         * Set and get distance among Users/Icons
         * @param value - pixel among Icons
         * @public
         */
        iconsDistance: function (value) {
            // No value passed, act as a getter.
            if (value === undefined) {
                return this.settings.iconsDistance;
            }

            // Value passed, act as a setter.
            this.settings.iconsDistance = value;
            this._draw();
        },

        /**
         * Load messages in the chat
         * @param values - messages to display.
         *                See this JSON /examples/messageList1.json
         *                file content to know the format required
         * @public
         */
        setValue: function (values) {

            // clean up last selection
            this._messageSelected = null;

            // convert input values to the internal users and messages objects
            // and add these objects in two lists (this._users and this._messages)
            // list of objects used to create SVG elements
            var messages = values.messages || [];
            var users = values.users || [];
            this._users = [];
            this._messages = [];
            var self = this;

            var mapUsers = {};
            var x = (this.settings.iconsDistance / 2);
            $.each(users, function (index, obj) {
                mapUsers[obj.id] = index;
                var user = obj;
                    user.idx = index;
                    user.x = x;
                self._users.push(user);
                x += self.settings.iconsDistance;
             });

             $.each(messages || [], function (index, obj) {
                var message = obj;
                    message.idx = index;
                    message.idxUser = mapUsers[obj.from];
                self._messages.push(message);

            });

            this._draw();

            // highlight  message if required and available
            var selectedIndex = values.selectedIndex !== undefined ? values.selectedIndex : -1;

            var msg = this._messagesLayer.get(selectedIndex);
            this._selectMessage(msg, false);
        },

        /**
         * @public
         * Resize the widget
         */
        resize: function (width, height) {

            this.element.width(width);
            this.element.height(height);
            this._updateLayout();
        },

        _draw: function () {

            // reset scrolling position
            this.$view.scrollLeft(0);
            this.$view.scrollTop(0);

            // clean up svg document to rebuild all
            this._svg.clear();

            // create gradients
            this._gradientHeader = this._svg.gradient('linear', function (stop) {
                stop.at({offset: 0, color: '#FEFEFF', opacity: 1});
                stop.at({offset: 1, color: '#EEF', opacity: 1});
            }).from(0, 0).to(0, 1);

            this._gradientMsg = this._svg.gradient('linear', function (stop) {
                stop.at({offset: 0, color: '#EDF6E6', opacity: 1});
                stop.at({offset: 1, color: '#D3E8C6', opacity: 1});
            });

            // create SVG layers
            this._bodyLayer = this._svg.group();
            this._headLayer = this._svg.group();
            this._usersLayer = this._headLayer.group();
            this._lineeLayer = this._bodyLayer.group();
            this._messagesLayer = this._bodyLayer.group();

            this._drawUsers();
            this._drawMessages();
            this._updateLayout();
        },

        _drawUsers: function () {

            // draw background user icons and labels
            this._usersLayer.rect(0, this._hHeader)
                .attr('fill', this._gradientHeader);
            // draw bottom horizontal line header
            this._usersLayer.line(0, this._hHeader, 0, this._hHeader)
                .addClass('ctracker-hline');

            var self = this;
            $.each(this._users, function (index, user) {
                self._drawUser(user);
            })
        },

        _drawUser: function (user) {
            var usr = this._usersLayer.group()
                .style('cursor:pointer')
                .remember('obj', user);

            // add image
            usr.image(user.image, 32, 32)
                .move(user.x - 16, 5);
            // add label
            usr.text(user.id)
                .attr({'y': 30, x: user.x, 'text-anchor': 'middle'});
            // add vertical lines body area
            this._lineeLayer.line(user.x, 0, user.x, 0)
                .addClass('ctracker-vline');

            // make the user draggable
            usr.draggable({minY: 0, maxY: 50});

            usr.on('beforedrag', $.proxy(this._evUserDragStart, this));
            usr.on('dragend', this._evUserDragEnd);
        },

        _drawMessages: function () {

            var y = this._hHeader + 8; // 8 == free space between header and first message
            var self = this;
            $.each(this._messages, function (index, message) {
                y = self._drawMessage(y, message);
            })
        },

        _drawMessage: function (y, message) {

            function pathRoundRect(x, y, w, h) {

                function p(x, y) {
                    return x + ' ' + y + ' ';
                }

                var r1 = 4;
                var strPath = 'M' + p(x + r1, y) + 'H' + (x + w / 2 - 4) + ' L' + p(x + w / 2, y - 5);
                strPath += 'L' + p(x + w / 2 + 4, y) + 'H' + (x + w - r1) + ' Q' + p(x + w, y) + p(x + w, y + r1);
                strPath += 'L' + p(x + w, y + h - r1) + 'Q' + p(x + w, y + h) + p(x + w - r1, y + h);
                strPath += 'L' + p(x + r1, y + h) + 'Q' + p(x, y + h) + p(x, y + h - r1);
                strPath += 'L' + p(x, y + r1) + 'Q' + p(x, y) + p(x + r1, y);
                strPath += 'Z';
                return strPath;
            }

            function splitInMultipleLines(text, maxChars) {
                text = text || '';
                if (text.length > maxChars) {
                    var p = maxChars;
                    for (; p > 0 && text[p] != ' '; p--);
                    if (p > 0) {
                        var left = text.substring(0, p);
                        var right = text.substring(p + 1);
                        return left + '\n' + splitInMultipleLines(right, maxChars);
                    }
                }
                return text;
            }

            var lines = splitInMultipleLines(message.text, this.settings.maxCharsPerLine);

            var msg = this._messagesLayer.group()
                .style('cursor:pointer')
                .remember('obj', message);
            // add text box
            var box = msg.path()
                .addClass('ctracker-box')
                .attr('fill', this._gradientMsg);
            // add text message
            var text = msg.text(lines)
                .attr({'text-anchor': 'middle'})
                .addClass('ctracker-text');

            // set text message position
            text.attr({y: message.y || y, x: this._users[message.idxUser].x});

            // set border text message box
            var bText = text.bbox();
            var path = pathRoundRect(bText.x - 2, bText.y - 2, bText.width + 4, bText.height + 4);
            box.plot(path);

            // make the message draggable
            msg.draggable();

            // set up drag events to re-position a message
            // to the closest vertical user line at the end
            // of drag and drop.
            msg.on('beforedrag', $.proxy(this._evMessageDragStart, this));
            msg.on('dragmove', $.proxy(this._evMessageDragMove, this));
            msg.on('dragend', $.proxy(this._evMessageDragEnd, this));

            return y + msg.bbox().height;
        },

        _updateLayout: function () {

        	var extraSpace = 10;
            var bbox = this._messagesLayer.bbox();

            // set width required
            var wRequired = 0;
            if (this._users && this._users.length > 0)
            	this._users[this._users.length-1].x + (this.settings.iconsDistance / 2);
            if (wRequired > 0)
                wRequired += extraSpace;
            if (bbox.x2 > wRequired)     // special case where we have message too long
                wRequired = bbox.x2 + 10;

            // set height required
            var hRequired = bbox.y2;
            if (hRequired > 0)
                hRequired += extraSpace;

            var $div = this.$view; //$(this.element);
            if ($div.innerWidth() > wRequired)
                wRequired = $div.innerWidth() - this._wScrollbar;
            if ($div.innerHeight() > hRequired)
                hRequired = $div.innerHeight();

            // update SVG doc size
            this._svg.size(wRequired, hRequired);

            // adjust background header rectangle
            this._usersLayer.get(0).width(wRequired);
            //adjust header bottom line
            this._usersLayer.get(1).plot(0, this._hHeader, wRequired, this._hHeader);
            // adjust body vertical lines
            var self = this;
            this._lineeLayer.each(function (i, children) {
                this.plot(this.x(), self._hHeader, this.x(), hRequired)
            });
        },

        _evScroll: function (event) {

            this._headLayer.animate().y(Math.floor(this.$view.scrollTop()));
        },

        _evFocus: function (event) {
            this._active = true;
            if (!this._messageSelected) {
                var msg = this._messagesLayer.first();
                this._selectMessage(msg, false);
            }
            //else {
            //    this._scrollToView(this._messageSelected);
            //}

            if (this._messageSelected) {
                this._messageSelected.get(0)
                    .addClass('ctracker-active')
                    .filter(function(add) {
                        var blur = add.gaussianBlur(5);
                        add.blend(add.source, blur)
                    })
            }
        },

        _evBlur: function (event) {
            this._active = false;
            if (this._messageSelected) {
                this._messageSelected.get(0)
                    .removeClass('ctracker-active')
                    .unfilter();
            }
        },

        _evClick: function (event) {
            this._gotFocus();
        },

        _evUserClick: function (event) {

            var usr = event.currentTarget.instance;
            // notify listeners
            var data = usr.remember('obj');
            this._trigger('onClickUser', event, data);
            this._gotFocus();
            return false;
        },

        _evUserDragStart: function (event) {

            event.currentTarget.instance.front();
            this._evUserClick(event);
         },

        _evUserDragEnd: function (event) {

            // prevent not real drag and drop
           if (event.detail.handler.startPoints.point.x == event.detail.p.x)
               return;

           alert('Drag and drop user only for test purpose');

            // reposition user to the same original position
           this.animate(300).x(0);
        },

        _evMessageClick: function (event) {

            var msg = event.currentTarget.instance;
            this._selectMessage(msg, true);
            this._gotFocus();
            return false;
        },

        _evMessageDragStart: function (event) {

            event.currentTarget.instance.front();
            this._evMessageClick(event);
        },

        _evMessageDragMove: function (event) {

            this._showTrash();

            // highlight off all vertical lines
            this._lineeLayer.each(function (i, children) {
                this.removeClass('ctracker-active')
            });

            var p = this._mousePoint(event.detail.event);
            if (!this._isMouseOverTrash(p)) {

                // highlight on vertical line where should be attached the message
                var user = this._closestUser(p);
                this._lineeLayer.get(user.idx).addClass('ctracker-active')
            }
        },

        _evMessageDragEnd: function (event) {

            // highlight off all vertical lines
            this._lineeLayer.each(function (i, children) {
                this.removeClass('ctracker-active')
            });

            // prevent not real drag and drop
            if (event.detail.handler.startPoints.point.x == event.detail.p.x &&
                event.detail.handler.startPoints.point.y == event.detail.p.y) {
                this._hideTrash();
                return;
            }

            // get mouse position ....
            var p = this._mousePoint(event.detail.event);

            // delete message
            if (this._isMouseOverTrash(p)) {
            	// and synch the data model on server side
                this._deleteMessage(event.detail.event);
            }
            // position message to the closest vertical user line
            else {
                var msg    = this._messageSelected; // should be == event.currentTarget.instance;
                var data   = msg.remember('obj');
                var usr    = this._closestUser(p);
                var pxOrig = this._users[data.idxUser].x;
                var pxDest = usr.x;
                msg.animate(300).x(pxDest - pxOrig);

                // and synch the data model on server side with
                // new message user owner and new message y position
                // data.idxUser = usr.idx;
                // data.from = usr.id;
                this._trigger('onMoveMessage', event, {id: data.id, from: usr.id, y: msg.tbox().y});
                this._updateLayout();
            }
             this._hideTrash();
        },

        _evKeyUp: function (event) {

            if (!this._active)
                return true;

            // NOTE:
            // The previous sibling message might not be the real previous message.
            // The next sibling message might not be the real next message.
            // This because when you rearrange messages the order could be changed.

            // create temporary list of items
            var items = [];
            var self = this;
            this._messagesLayer.each(function(i, children) {
                var box = this.tbox()
                var item = {x: box.x, y: box.y, msg: this, current: this == self._messageSelected};
                items[i] = item;
            });
            if (items.length == 0)
                return true;

            // sort by x and y position
            items.sort(function(a, b) {
                if (a.y == b.y) return a.x - b.x;
                return a.y - b.y;
            });
            // get current selected message index position
            var idxSelected = undefined;
            for(var i = 0, l = items.length; i < l; i++) {
                if (!items[i].current) continue;
                idxSelected = i;
                break;
            }

            var msg;
            switch (event.keyCode) {
                case 36: //$.ui.keyCode.HOME:
                    msg = items[0].msg;
                    this._selectMessage(msg, true);
                    return false;

                case 35: //$.ui.keyCode.END:
                    msg = items[items.length-1].msg;
                    this._selectMessage(msg, true);
                    return false;

                case 38: //$.ui.keyCode.UP:
                    if (idxSelected > 0)
                        msg = items[--idxSelected].msg;
                    if (msg)
                        this._selectMessage(msg, true);
                    return false;

                case 40: //$.ui.keyCode.DOWN:
                    if (idxSelected < items.length-1)
                        msg = items[++idxSelected].msg;
                    if (msg)
                        this._selectMessage(msg, true);
                    return false;

                case 46: //$.ui.keyCode.DELETE:
                    this._deleteMessage(event);
                    return false;

                default:
                    return true;
            }
        },

        _deleteMessage: function (event) {

            if (!this._messageSelected) return;

            // find next or previous focusable message
            var msg = this._messageSelected.next();
            if (!msg)
                msg = this._messageSelected.previous();

            var data = this._messageSelected.remember('obj');
            this._trigger('onDeleteMessage', event, data);

            // delete SVG message element
            var index = this._messageSelected.remember('obj').idx;
            this._messageSelected.remove();
            this._messageSelected = null;
            this._messages.splice(index, 1);

            // select and highlight next or previous message
            this._selectMessage(msg, true);
            this._updateLayout();
        },

        _selectMessage: function (msg, fire) {
            this._highlightMessage(msg);
            this._scrollToView(msg);
            if (fire)
                this._fireMessageSelected(msg);
        },

        _highlightMessage: function (msg) {

            if (this._messageSelected) {
                this._messageSelected.get(0)
                    .removeClass('ctracker-selected')
                    .removeClass('ctracker-active')
                    .unfilter();
                this._messageSelected = null;
            }

            if (msg) {
                this._messageSelected = msg;

                msg.get(0).addClass('ctracker-selected');
                if (this._active)
                    msg.get(0)
                        .addClass('ctracker-active')
                        .filter(function(add) {
                            var blur = add.gaussianBlur(5);
                            add.blend(add.source, blur)
                        })
            }
        },

        _fireMessageSelected: function (msg) {
            if (!msg) return;
            var data = msg.remember('obj');
            this._trigger('onSelectedMessage', event, data);
        },

        /** Scroll to view message if not visible */
        _scrollToView: function (msg) {
            if (!msg) return;

            var bbox = msg.tbox();
            var l = this.$view.scrollLeft();
            var w = this.$view.width();
            if (l > bbox.x)
                this.$view.scrollLeft(bbox.x - 10);
            else if ((l + w) < bbox.x2)
                this.$view.scrollLeft(bbox.x2 - w + 10);

            var t = this.$view.scrollTop();
            var h = this.$view.height();

            if ((t + + this._hHeader) > bbox.y)
                this.$view.scrollTop(bbox.y - this._hHeader - 10);
            else if ((t + h) < bbox.y2)
                this.$view.scrollTop(bbox.y2 - h + 10);
        },

        _gotFocus: function () {
            $(this.element).find('.ctracker-focus').focus();
        },

        _mousePoint: function (event) {
            if (this._point == null)
                this._point = this._svg.node.createSVGPoint();
            var touches = event.changedTouches && event.changedTouches[0] || event;
            this._point.x = touches.clientX;
            this._point.y = touches.clientY;
            return this._point.matrixTransform(this._svg.node.getScreenCTM().inverse());
        },

        /**
         * From mouse point get the closest user
         */
        _closestUser: function (mousePoint) {

            var selected, diff = null;
            $.each(this._users, function (i, user) {
                var val = (user.x < mousePoint.x) ? mousePoint.x - user.x : user.x - mousePoint.x;
                if (!diff || val < diff) {
                    diff = val;
                    selected = user;
                }
            });
            return selected;
        },

        /**
         * Show trash in the bottom right corner
         * Trash will be visible only when I drag and drop a message
         */
        _showTrash: function () {
            if (this._trash) return;

            var x = this.$view.width() + this.$view.scrollLeft() - 44 - this._wScrollbar;
            var y = this.$view.height() + this.$view.scrollTop() - 52;

            this._trash = this._bodyLayer.image('images/trash_empty.png', 48, 48)
                .style('cursor:pointer').move(x, y).opacity(0.4);
        },

        _hideTrash: function () {
            if (this._trash)
                this._trash.remove();
            this._trash = undefined;
        },

        _isMouseOverTrash: function (mousePoint) {
            var isOver = this._trash.inside(mousePoint.x, mousePoint.y);
            if (this._trash)
                this._trash.opacity(isOver ? 1 : 0.4);
            return isOver;
        }
    });
}(jQuery));
